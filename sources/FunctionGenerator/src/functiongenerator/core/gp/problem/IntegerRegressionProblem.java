package functiongenerator.core.gp.problem;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.koza.KozaFitness;
import functiongenerator.core.gp.data.IntegerData;

public class IntegerRegressionProblem extends AbstractRegressionProblem {

	private static final long serialVersionUID = 1L;

	public IntegerRegressionProblem() {
		super();
		output = new IntegerData();
	}

	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
		if (!ind.evaluated) { // don't bother reevaluating

			float error = 1.0f;
			float fitness = 0.0f;
			int hits = 0;

			for (Number[] point : points) {
				this.X = point;
				int Y = (Integer) point[point.length - 1];

				try {
					((GPIndividual) ind).trees[0].child.eval(state, threadnum, output, stack, ((GPIndividual) ind), this);

					error = (float) Math.pow(Y - ((IntegerData) output).Y, 2); // square
																				// error

				} catch (ArithmeticException ex) {
					error = Float.MAX_VALUE / points.size(); // set max error
																// for this test
				}

				if (error < 1)
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
