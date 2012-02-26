package pl.alife.fg.core.gp.functions.integer;

import pl.alife.fg.core.gp.data.IntegerData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Zero extends pl.alife.fg.core.gp.functions.real.Zero {

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        IntegerData rd = ((IntegerData) (input));
        rd.Y = 0;
    }

}
