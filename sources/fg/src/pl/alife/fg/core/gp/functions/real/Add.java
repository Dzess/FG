package pl.alife.fg.core.gp.functions.real;

import pl.alife.fg.core.gp.data.DoubleData;
import pl.alife.fg.core.gp.functions.BinaryOperation;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class Add extends BinaryOperation {

    public String toString() {
        return "+";
    }

    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        double result;
        DoubleData rd = ((DoubleData) (input));

        children[0].eval(state, thread, input, stack, individual, problem);
        result = rd.Y;

        children[1].eval(state, thread, input, stack, individual, problem);
        rd.Y = result + rd.Y;
    }
}