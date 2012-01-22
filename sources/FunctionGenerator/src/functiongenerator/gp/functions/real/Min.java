package functiongenerator.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.functions.BinaryOperation;

public class Min extends BinaryOperation {

	@Override
	public String toString() {
		return "Math.min";
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
		double result;
		DoubleData rd = ((DoubleData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.Y;

		children[1].eval(state, thread, input, stack, individual, problem);
		rd.Y = Math.min(result, rd.Y);
	}

}
