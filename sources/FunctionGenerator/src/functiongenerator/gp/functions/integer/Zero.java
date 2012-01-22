package functiongenerator.gp.functions.integer;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.IntegerData;

public class Zero extends functiongenerator.gp.functions.real.Zero {

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

		IntegerData rd = ((IntegerData) (input));
		rd.Y = 0;
	}

}
