package functiongenerator.gp.problem;

import java.util.ArrayList;
import java.util.List;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import functiongenerator.gp.data.DoubleData;

public abstract class AbstractRegressionProblem extends GPProblem implements SimpleProblemForm {

	/**
	 * Current X0.
	 */
	public Number[] X;

	/**
	 * List of x,f(x) pairs.
	 */
	protected List<Number[]> points;

	protected int maxTreeDepth = 7;
	protected int maxNodes = 127;

	protected GPData output;

	public List<Number[]> getPoints() {
		return points;
	}

	public void setPoints(List<Number[]> points) {
		/*
		 * points = new ArrayList<double[]>(); for (Double[] d : pairs) { points.add(new double[] { d[0], d[1] }); }
		 * this.points = points;
		 */
		this.points = points;
	}

	public int getMaxTreeDepth() {
		return maxTreeDepth;
	}

	public void setMaxTreeDepth(int maxTreeDepth) {
		this.maxTreeDepth = maxTreeDepth;
		this.maxNodes = (int) Math.pow(2, maxTreeDepth) - 1;
	}

	@Override
	public Object clone() {
		AbstractRegressionProblem copy = (AbstractRegressionProblem) super.clone();
		copy.output = (GPData) this.output.clone();
		copy.X = null;
		return copy;
	}
}
