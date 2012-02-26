package pl.alife.fg.core.gp.functions.integer;

import pl.alife.fg.core.gp.data.IntegerData;
import pl.alife.fg.core.gp.problem.AbstractRegressionProblem;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

public class X0 extends pl.alife.fg.core.gp.functions.real.X0 {

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        IntegerData rd = ((IntegerData) (input));
        rd.Y = (Integer) ((AbstractRegressionProblem) problem).X[0];
    }

}
