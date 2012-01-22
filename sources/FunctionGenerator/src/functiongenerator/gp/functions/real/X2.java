package functiongenerator.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.problem.AbstractRegressionProblem;

public class X2 extends X0 {
	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		DoubleData rd = ((DoubleData) (input));
		rd.Y = (Double) ((AbstractRegressionProblem) problem).X[2];
	}
}
