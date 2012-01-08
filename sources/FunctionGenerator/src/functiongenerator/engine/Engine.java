package functiongenerator.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ec.EvolutionState;
import ec.Evolve;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import ec.simple.SimpleStatistics;
import ec.util.Log;
import ec.util.LogRestarter;
import ec.util.ParameterDatabase;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.data.IntegerData;
import functiongenerator.gp.functions.BinaryOperation;
import functiongenerator.gp.functions.NullaryOperation;
import functiongenerator.gp.functions.UnaryOperation;
import functiongenerator.gp.functions.real.Max;
import functiongenerator.gp.functions.real.Min;
import functiongenerator.gp.functions.real.Pow;
import functiongenerator.gp.functions.real.ProtectedDiv;
import functiongenerator.gp.problem.AbstractRegressionProblem;
import functiongenerator.gp.problem.IntegerRegressionProblem;
import functiongenerator.gp.problem.RealRegressionProblem;

public class Engine {

	private List<Number[]> points = new ArrayList<Number[]>();
	private List<Class<?>> operations = new ArrayList<Class<?>>();
	
	// FIXME: those data should be quite the visible ones ?
	private int popSize = 200;
	private int generations = 50;
	private int maxTreeDepth = 7;
	private ParameterDatabase parameters;
	private List<IProgressListener> listeners = new ArrayList<IProgressListener>();
	private volatile boolean cancel = false;

	public Engine() throws FileNotFoundException, IOException {
		parameters = new ParameterDatabase(Engine.class.getResourceAsStream("template.params"));
	}

	public List<Number[]> getPoints() {
		return points;
	}

	public void setPoints(List<Number[]> points) {
		this.points = points;
	}

	public List<Class<?>> getOperations() {
		return operations;
	}

	public void setOperations(List<Class<?>> operations) {
		if (operations == null)
			throw new IllegalArgumentException("operations");
		this.operations = operations;
	}

	public int getPopSize() {
		return popSize;
	}

	public void setPopulationSize(int popSize) {
		this.popSize = popSize;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public int getMaxTreeDepth() {
		return maxTreeDepth;
	}

	public void setMaxTreeDepth(int maxTreeDepth) {
		this.maxTreeDepth = maxTreeDepth;
	}

	public void addListener(IProgressListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IProgressListener listener) {
		listeners.remove(listener);
	}

	private void updateProgress(int currentGen, String output) {
		for (IProgressListener listener : listeners) {
			listener.update(((double) (currentGen * 100)) / (double) this.generations, output);
		}
	}

	private String generateClassTemplate(String func, String comment) throws IOException {
		try {
			InputStream template = Engine.class.getResourceAsStream("ClassTemplate.java.tpl");
			BufferedReader reader = new BufferedReader(new InputStreamReader(template));
			StringBuilder builder = new StringBuilder();

			for (String line; (line = reader.readLine()) != null;) {
				builder.append(line);
				builder.append('\n');
			}

			int idx = builder.indexOf("/*functionCode*/");
			if (idx < 0)
				throw new RuntimeException("/*functionCode*/ not found in class template!");
			builder.replace(idx, idx + "/*functionCode*/".length(), func);

			idx = builder.indexOf("/*comment*/");
			if(idx < 0)
				throw new RuntimeException("/*comment*/ not found in class template!");
			builder.replace(idx, idx + "/*comment*/".length(), comment);
			
			String type = points.get(0)[0] instanceof Double ? "double" : "int";

			while ((idx = builder.indexOf("/*type*/")) >= 0) {
				builder.replace(idx, idx + "/*type*/".length(), type);
			}

			StringBuilder argumentsBuilder = new StringBuilder();
			for (int i = 0; i < points.get(0).length - 1; ++i) {
				argumentsBuilder.append(type + " x");
				argumentsBuilder.append(i);
				argumentsBuilder.append(',');
			}
			argumentsBuilder.deleteCharAt(argumentsBuilder.length() - 1);
			idx = builder.indexOf("/*arguments*/");
			if (idx < 0)
				throw new RuntimeException("/*arguments*/ not found in class template!");
			builder.replace(idx, idx + "/*arguments*/".length(), argumentsBuilder.toString());

			return builder.toString();
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	private String translateTree(GPTree tree) {
		StringBuilder builder = new StringBuilder();
		processNode(builder, tree.child);
		return builder.toString();
	}

	private void processNode(StringBuilder builder, GPNode node) {
		if (node instanceof BinaryOperation) {
			if (node instanceof ProtectedDiv || node instanceof Pow || node instanceof Min || node instanceof Max
					|| node instanceof functiongenerator.gp.functions.integer.ProtectedDiv
					|| node instanceof functiongenerator.gp.functions.integer.Min
					|| node instanceof functiongenerator.gp.functions.integer.Max) {
				builder.append(node.toString());
				builder.append('(');
				processNode(builder, node.children[0]);
				builder.append(", ");
				processNode(builder, node.children[1]);
				builder.append(')');
			} else {
				builder.append('(');
				processNode(builder, node.children[0]);
				builder.append(") ");
				builder.append(node.toString());
				builder.append(" (");
				processNode(builder, node.children[1]);
				builder.append(')');
			}
		} else if (node instanceof UnaryOperation) {
			builder.append(node.toString());
			builder.append('(');
			processNode(builder, node.children[0]);
			builder.append(')');
		} else if (node instanceof NullaryOperation) {
			builder.append(node.toString());
		} else {
			throw new RuntimeException("Unknown node type: " + node.getClass().getName());
		}
	}

	private ParameterDatabase initializeDB() {
		ParameterDatabase db = (ParameterDatabase) parameters.clone();

		int numX = points.get(0).length - 1;

		// add function set
		db.setProperty("gp.fs.0.size", "" + (operations.size() + numX));

		if (points.get(0)[0] instanceof Double) {
			db.setProperty("eval.problem", RealRegressionProblem.class.getName());
			db.setProperty("eval.problem.data", DoubleData.class.getName());

			for (int i = 0; i < numX; ++i) {
				db.setProperty("gp.fs.0.func." + i, "functiongenerator.gp.functions.real.X" + i);
				db.setProperty("gp.fs.0.func." + i + ".nc", "nc0");
			}
		} else {
			db.setProperty("eval.problem", IntegerRegressionProblem.class.getName());
			db.setProperty("eval.problem.data", IntegerData.class.getName());

			for (int i = 0; i < numX; ++i) {
				db.setProperty("gp.fs.0.func." + i, "functiongenerator.gp.functions.integer.X" + i);
				db.setProperty("gp.fs.0.func." + i + ".nc", "nc0");
			}
		}

		int count = operations.size();
		for (int i = 0; i < count; ++i) {
			Class<?> op = operations.get(i);
			db.setProperty("gp.fs.0.func." + (i + numX), op.getName());
			if (NullaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numX) + ".nc", "nc0");
			else if (UnaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numX) + ".nc", "nc1");
			else if (BinaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numX) + ".nc", "nc2");
		}

		// set popSize and generations
		db.setProperty("pop.subpop.0.size", "" + popSize);
		db.setProperty("generations", "" + generations);
		// and max tree depth
		db.setProperty("gp.koza.xover.maxdepth", "" + maxTreeDepth);
		db.setProperty("gp.koza.mutate.maxdepth", "" + maxTreeDepth);
		db.setProperty("gp.koza.half.min-depth", "" + Math.min(2, maxTreeDepth));
		db.setProperty("gp.koza.half.max-depth", "" + maxTreeDepth);

		return db;
	}

	/**
	 * 
	 * @return Java code template.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String run() throws FileNotFoundException, IOException {
		cancel = false;

		EvolutionState state = Evolve.initialize(initializeDB(), 0);
		state.startFresh();

		AbstractRegressionProblem problem = (AbstractRegressionProblem) state.evaluator.p_problem;
		problem.setPoints(points);
		problem.setMaxTreeDepth(maxTreeDepth);

		StringWriter writer = new StringWriter();
		StringBuffer buffer = writer.getBuffer();
		state.output.addLog(writer, new LogRestarter() {

			@Override
			public Log restart(Log l) throws IOException {
				return l;
			}

			@Override
			public Log reopen(Log l) throws IOException {
				return l;
			}
		}, true, false);

		/* the big loop */
		int result = EvolutionState.R_NOTDONE;
		while (result == EvolutionState.R_NOTDONE && !cancel) {
			result = state.evolve();

			updateProgress(state.generation, buffer.toString());
			buffer.delete(0, buffer.length());
		}

		

		if (cancel){
			state.finish(result);
			Evolve.cleanup(state);
			return null;
		}

		SimpleStatistics stat = (SimpleStatistics) state.statistics;
		GPIndividual ind = (GPIndividual) stat.best_of_run[0];
		String template = generateClassTemplate(translateTree(ind.trees[0]), ind.fitness.fitnessToStringForHumans());
		
		state.finish(result);
		Evolve.cleanup(state);
		
		return template;
	}

	public void cancel() {
		cancel = true;
	}
}
