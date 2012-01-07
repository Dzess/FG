package functiongenerator.gp.functions.integer;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.IntegerData;
import functiongenerator.gp.problem.AbstractRegressionProblem;

public class X2 extends X0 {
	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
			Problem problem) {

		IntegerData rd = ((IntegerData) (input));
		rd.Y = (Integer)((AbstractRegressionProblem) problem).X[2];
	}
}
