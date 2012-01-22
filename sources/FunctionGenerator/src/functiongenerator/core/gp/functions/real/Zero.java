package functiongenerator.core.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.functions.NullaryOperation;

public class Zero extends NullaryOperation {

	@Override
	public String toString() {
		return "0";
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		DoubleData rd = ((DoubleData) (input));
		rd.Y = 0;
	}

}
