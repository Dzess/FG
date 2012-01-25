package functiongenerator.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import ec.EvolutionState;
import ec.Evolve;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import ec.simple.SimpleStatistics;
import ec.util.Log;
import ec.util.LogRestarter;
import ec.util.ParameterDatabase;
import functiongenerator.core.gp.functions.BinaryOperation;
import functiongenerator.core.gp.functions.NullaryOperation;
import functiongenerator.core.gp.functions.UnaryOperation;
import functiongenerator.core.gp.functions.real.Max;
import functiongenerator.core.gp.functions.real.Min;
import functiongenerator.core.gp.functions.real.Pow;
import functiongenerator.core.gp.functions.real.ProtectedDiv;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;

/**
 * Responsible for running the GP algorithm.
 * 
 * <br/>
 * 
 * TODO: describe more about the internal doings of this code
 */
public class Engine {

	static private final org.apache.commons.logging.Log logger = LogFactory.getLog(Engine.class);

	private List<Number[]> points = new ArrayList<Number[]>();
	private Settings settings;
	private List<IProgressListener> listeners = new ArrayList<IProgressListener>();

	private volatile boolean cancel = false;

	public List<Number[]> getPoints() {
		return points;
	}

	public void setPoints(List<Number[]> points) {
		this.points = points;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public void addListener(IProgressListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IProgressListener listener) {
		listeners.remove(listener);
	}

	private void updateProgress(int currentGen, String output) {
		for (IProgressListener listener : listeners) {
			listener.update(((double) (currentGen * 100)) / (double) this.getSettings().getGenerations(), output);
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
			if (idx < 0)
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
					|| node instanceof functiongenerator.core.gp.functions.integer.ProtectedDiv
					|| node instanceof functiongenerator.core.gp.functions.integer.Min
					|| node instanceof functiongenerator.core.gp.functions.integer.Max) {
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

	/**
	 * 
	 * @return Java code template.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String run() throws FileNotFoundException, IOException {
		cancel = false;

		int numberOfXes = points.get(0).length - 1;

		ProblemType type = null;
		if (points.get(0)[0] instanceof Double) {
			logger.info("Problem is REAL numbered");
			type = ProblemType.DOUBLE;
		} else {
			logger.info("Problem is INTEGER numbered");
			type = ProblemType.INTEGER;
		}

		ParameterDatabase db = getSettings().generateParameterDatabase(numberOfXes, type);
		EvolutionState state = Evolve.initialize(db, 0);
		state.startFresh();

		AbstractRegressionProblem problem = (AbstractRegressionProblem) state.evaluator.p_problem;
		problem.setPoints(points);
		problem.setMaxTreeDepth(getSettings().getMaxTreeDepth());

		StringWriter writer = new StringWriter();
		StringBuffer buffer = writer.getBuffer();
		// TODO: change the sample output log for the org.apache.commons.logging
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

		if (cancel) {
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
