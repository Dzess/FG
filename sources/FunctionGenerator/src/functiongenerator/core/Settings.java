package functiongenerator.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ec.util.ParameterDatabase;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.data.IntegerData;
import functiongenerator.gp.functions.BinaryOperation;
import functiongenerator.gp.functions.NullaryOperation;
import functiongenerator.gp.functions.UnaryOperation;
import functiongenerator.gp.problem.IntegerRegressionProblem;
import functiongenerator.gp.problem.RealRegressionProblem;

/**
 * Describes all the settings used for configuring the {@linkplain Engine}
 * object.
 * 
 * <p>
 * All the settings apart from the data for the current problem. This is <b>GUI
 * agnostic</b> representation of the experiment to be surveyed.
 * </p>
 * 
 * <p>
 * The template for the ECJ configuration file is read from the file:
 * <i>template.params</i>. File is located in the <i>core</i> package.
 * </p>
 * 
 * <h4>
 * Default values:</h4>
 * 
 * <ul>
 * <li>population size - 200</li>
 * <li>max tree depth - 7</li>
 * <li>generations - 50</li>
 * </ul>
 * 
 * 
 */
public class Settings {
	private List<Class<?>> operations = new ArrayList<Class<?>>();

	private int popSize = 200;
	private int generations = 50;
	private int maxTreeDepth = 7;

	static private final String TEMPLATE_FILENAME = "template.params";
	static private final Log logger = LogFactory.getLog(Settings.class);

	private final ParameterDatabase parameters;

	public Settings() throws IOException {
		parameters = new ParameterDatabase(Engine.class.getResourceAsStream(TEMPLATE_FILENAME));
	}

	/**
	 * Generates the format for the experiment basing on the internal state of
	 * this object. Generates the setting for two possible problems:
	 * 
	 * <ul>
	 * <li>{@linkplain IntegerRegressionProblem}</li>
	 * <li>{@linkplain RealRegressionProblem}</li>
	 * </ul>
	 * 
	 * @param numberOfXes
	 *            : the number of X variables
	 * @param problemType
	 *            : the {@linkplain ProblemType} specifying what kind of problem
	 *            is being prepared
	 * @return : the new instance of {@linkplain ParameterDatabase} class with
	 *         all the values set.
	 */
	public ParameterDatabase generateParameterDatabase(int numberOfXes, ProblemType problemType) {

		if (numberOfXes < 1) {
			throw new IllegalArgumentException("The number of X varables must be at least one");
		}

		ParameterDatabase db = (ParameterDatabase) parameters.clone();

		// add function set
		db.setProperty("gp.fs.0.size", "" + (operations.size() + numberOfXes));

		if (problemType == ProblemType.DOUBLE) {
			db.setProperty("eval.problem", RealRegressionProblem.class.getName());
			db.setProperty("eval.problem.data", DoubleData.class.getName());

			// set the number of X variables
			for (int i = 0; i < numberOfXes; ++i) {
				db.setProperty("gp.fs.0.func." + i, "functiongenerator.gp.functions.real.X" + i);
				db.setProperty("gp.fs.0.func." + i + ".nc", "nc0");
			}
		} else if (problemType == ProblemType.INTEGER) {
			db.setProperty("eval.problem", IntegerRegressionProblem.class.getName());
			db.setProperty("eval.problem.data", IntegerData.class.getName());

			// set the number of X variables
			for (int i = 0; i < numberOfXes; ++i) {
				db.setProperty("gp.fs.0.func." + i, "functiongenerator.gp.functions.integer.X" + i);
				db.setProperty("gp.fs.0.func." + i + ".nc", "nc0");
			}
		} else {
			throw new IllegalArgumentException("No element for problem type: '" + problemType + "'");
		}

		int count = operations.size();
		for (int i = 0; i < count; ++i) {
			Class<?> op = operations.get(i);
			db.setProperty("gp.fs.0.func." + (i + numberOfXes), op.getName());
			if (NullaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numberOfXes) + ".nc", "nc0");
			else if (UnaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numberOfXes) + ".nc", "nc1");
			else if (BinaryOperation.class.isAssignableFrom(op))
				db.setProperty("gp.fs.0.func." + (i + numberOfXes) + ".nc", "nc2");
		}

		// set popSize and generations
		db.setProperty("pop.subpop.0.size", "" + popSize);
		db.setProperty("generations", "" + generations);

		// and max tree depth
		db.setProperty("gp.koza.xover.maxdepth", "" + maxTreeDepth);
		db.setProperty("gp.koza.mutate.maxdepth", "" + maxTreeDepth);
		db.setProperty("gp.koza.half.min-depth", "" + Math.min(2, maxTreeDepth));
		db.setProperty("gp.koza.half.max-depth", "" + maxTreeDepth);

		logger.debug("Paramter DB file file");
		logger.debug(db.toString());

		return db;
	}

	/*
	 * Getters & Setter
	 */

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
}
