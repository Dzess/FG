package functiongenerator.core.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.functions.NullaryOperation;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;

public class X0 extends NullaryOperation {

	@Override
	public String toString() {
		return this.getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		DoubleData rd = ((DoubleData) (input));
		rd.Y = (Double) ((AbstractRegressionProblem) problem).X[0];
	}

}
