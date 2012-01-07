package functiongenerator.gp.functions.real;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.gp.data.DoubleData;
import functiongenerator.gp.functions.NullaryOperation;

public class Four extends NullaryOperation {

	@Override
	public String toString() {
		return "4";
	}

	@Override
	public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
			Problem problem) {
		
		DoubleData rd = ((DoubleData) (input));
		rd.Y = 4;
	}

}
