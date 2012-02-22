package functiongenerator.core.gp.functions.integer;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import functiongenerator.core.gp.data.IntegerData;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;

public class X4 extends X0 {
    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual,
            Problem problem) {

        IntegerData rd = ((IntegerData) (input));
        rd.Y = (Integer) ((AbstractRegressionProblem) problem).X[4];
    }
}
