package functiongenerator.gp.functions.integer;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.IntegerData;

public class Max extends functiongenerator.gp.functions.real.Max {

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		int result;
		IntegerData rd = ((IntegerData) (input));

		children[0].eval(state, thread, input, stack, individual, problem);
		result = rd.Y;

		children[1].eval(state, thread, input, stack, individual, problem);
		rd.Y = Math.max(result, rd.Y);
	}
}
