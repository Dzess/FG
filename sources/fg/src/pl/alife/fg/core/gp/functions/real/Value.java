package pl.alife.fg.core.gp.functions.real;

import pl.alife.fg.core.gp.data.DoubleData;
import pl.alife.fg.core.gp.functions.NullaryOperation;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Represents any numeric value of type {@linkplain Double}.
 * 
 * @author Piotr Jessa
 * 
 */
public class Value extends NullaryOperation {

    private final double value;

    public Value(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {
        DoubleData rd = ((DoubleData) (input));
        rd.Y = value;
    }

}