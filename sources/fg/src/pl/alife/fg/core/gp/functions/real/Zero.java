package pl.alife.fg.core.gp.functions.real;

import pl.alife.fg.core.gp.data.DoubleData;
import pl.alife.fg.core.gp.functions.NullaryOperation;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Zero extends NullaryOperation {

    @Override
    public String toString() {
        return "0";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        DoubleData rd = ((DoubleData) (input));
        rd.Y = 0;
    }

}
