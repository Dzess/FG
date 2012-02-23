package pl.alife.fg.core.gp.functions.integer;

import pl.alife.fg.core.gp.data.IntegerData;
import pl.alife.fg.core.gp.functions.NullaryOperation;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Value extends NullaryOperation {

    private final int value;

    public Value(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {
        IntegerData rd = ((IntegerData) (input));
        rd.Y = value;
    }

}