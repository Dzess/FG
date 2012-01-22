package functiongenerator.gp.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.koza.KozaFitness;
import functiongenerator.gp.data.DoubleData;

public class RealRegressionProblem extends AbstractRegressionProblem {

	private static final long serialVersionUID = 1L;

	public RealRegressionProblem() {
		super();
		output = new DoubleData();
	}

	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
		if (!ind.evaluated) { // don't bother reevaluating

			float fitness = 0.0f;
			int hits = 0;

			for (Number[] point : points) {
				this.X = point;
				double Y = (Double) point[point.length - 1];

				((GPIndividual) ind).trees[0].child.eval(state, threadnum, output, stack, ((GPIndividual) ind), this);

				float error = (float) Math.pow(Y - ((DoubleData) output).Y, 2); // square
																				// error

				if (error <= 0.0001f * Math.abs(Y)) // max error at 1% of
													// expected value is treated
													// as right value
					++hits;
				else
					fitness += error;
			}

			if (Float.isNaN(fitness) || Float.isInfinite(fitness))
				fitness = Float.MAX_VALUE;

			if (fitness == 0.0f) {
				fitness = ((GPIndividual) ind).trees[0].child.numNodes(GPNode.NODESEARCH_ALL) - 1;
			} else {
				fitness += (float) maxNodes;
			}

			// the fitness better be KozaFitness!
			KozaFitness f = ((KozaFitness) ind.fitness);
			f.setStandardizedFitness(state, fitness);
			f.hits = hits;
			ind.evaluated = true;

			// actually following the specification of GPProblem description
			stack.reset();
		}
	}

}
