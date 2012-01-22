package functiongenerator.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.functions.UnaryOperation;

public class Log extends UnaryOperation {

	@Override
	public String toString() {
		return "Math.log";
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		rd.Y = Math.log(rd.Y);
	}

}
