package pl.alife.fg.core.gp.functions.integer;

import pl.alife.fg.core.gp.data.IntegerData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class ProtectedDiv extends pl.alife.fg.core.gp.functions.real.ProtectedDiv {

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        int result;
        IntegerData rd = ((IntegerData) (input));

        children[0].eval(state, thread, input, stack, individual, problem);
        result = rd.Y;

        children[1].eval(state, thread, input, stack, individual, problem);
        if (rd.Y != 0)
            rd.Y = result / rd.Y;
    }
}
