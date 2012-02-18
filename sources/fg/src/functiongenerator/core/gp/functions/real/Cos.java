package functiongenerator.core.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.functions.UnaryOperation;

public class Cos extends UnaryOperation {

	@Override
	public String toString() {
		return "Math.cos";
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		rd.Y = Math.cos(rd.Y);
	}

}
