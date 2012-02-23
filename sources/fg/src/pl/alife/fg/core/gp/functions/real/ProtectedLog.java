package pl.alife.fg.core.gp.functions.real;

import pl.alife.fg.core.gp.data.DoubleData;
import pl.alife.fg.core.gp.functions.UnaryOperation;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class ProtectedLog extends UnaryOperation {

    @Override
    public String toString() {
        return "protectedLog";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        DoubleData rd = ((DoubleData) (input));

        children[0].eval(state, thread, input, stack, individual, problem);
        if (rd.Y > 0)
            rd.Y = Math.log(rd.Y);
        else
            rd.Y = 0;

    }

}
