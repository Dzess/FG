/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
 */

package ec.es;

import ec.EvolutionState;
import ec.Individual;
import ec.SelectionMethod;
import ec.util.Parameter;

/* 
 * ESSelection.java
 * 
 * Created: Thu Sep  7 19:08:19 2000
 * By: Sean Luke
 */

/**
 * ESSelection is a special SelectionMethod designed to be used with
 * evolutionary strategies-type breeders.
 * 
 * <p>
 * To do evolution strategies evolution, the breeding pipelines should contain
 * at least one ESSelection selection method. While a child is being generated
 * by the pipeline, the ESSelection object will return a parent from the pool of
 * mu parents. The particular parent is chosen round-robin, so all the parents
 * will have an equal number of children. It's perfectly fine to have more than
 * one ESSelection object in the tree, or to call the same one repeatedly during
 * the course of generating a child; all such objects will consistently return
 * the same parent. They only increment to the nex parent in the pool of mu
 * parents after the child has been created from the pipeline. You can also mix
 * ESSelection operators with other operators (like Tournament Selection). But
 * you ought to have <b>at least one</b> ESSelection operator in the pipeline --
 * else it wouldn't be Evolution Strategies, would it?
 * 
 * <p>
 * <b>Default Base</b><br>
 * es.select
 * 
 * @author Sean Luke
 * @version 1.0
 */

public class ESSelection extends SelectionMethod {
    public static final String P_ESSELECT = "select";

    public Parameter defaultBase() {
        return ESDefaults.base().push(P_ESSELECT);
    }

    // MuCommaLambdaBreeder expects us to set the count to nonzero to indicate
    // our existence
    public void prepareToProduce(final EvolutionState state, final int subpopulation, final int thread) {
        super.prepareToProduce(state, subpopulation, thread);
        if (!(state.breeder instanceof MuCommaLambdaBreeder))
            state.output
                    .fatal("ESSelection was handed a Breeder that's not either MuCommaLambdaBreeder or MuCommaPlusLambdaBreeder.");
        MuCommaLambdaBreeder breeder = (MuCommaLambdaBreeder) (state.breeder);

        breeder.count[thread] = 1;
    }

    public int produce(final int subpopulation, final EvolutionState state, final int thread) {
        if (!(state.breeder instanceof MuCommaLambdaBreeder))
            state.output
                    .fatal("ESSelection was handed a Breeder that's not either MuCommaLambdaBreeder or MuCommaPlusLambdaBreeder.");
        MuCommaLambdaBreeder breeder = (MuCommaLambdaBreeder) (state.breeder);

        // determine my position in the array
        int pos = (breeder.lambda[subpopulation] % state.breedthreads == 0 ? breeder.lambda[subpopulation]
                / state.breedthreads : breeder.lambda[subpopulation] / state.breedthreads + 1)
                * thread + breeder.count[thread]; // note
                                                  // integer
                                                  // division

        // determine the parent
        int parent = pos / breeder.mu[subpopulation]; // note integer division

        // increment our count
        // breeder.count[thread]++;

        return parent;
    }

    public int produce(final int min, final int max, final int start, final int subpopulation, final Individual[] inds,
            final EvolutionState state, final int thread) {
        if (min > 1) // uh oh
            state.output.fatal("ESSelection used, but it's being asked to produce more than one individual.");
        if (!(state.breeder instanceof MuCommaLambdaBreeder))
            state.output
                    .fatal("ESSelection was handed a Breeder that's not either MuCommaLambdaBreeder or MuCommaPlusLambdaBreeder.");
        MuCommaLambdaBreeder breeder = (MuCommaLambdaBreeder) (state.breeder);

        // determine my position in the array
        int pos = (breeder.lambda[subpopulation] % state.breedthreads == 0 ? breeder.lambda[subpopulation]
                / state.breedthreads : breeder.lambda[subpopulation] / state.breedthreads + 1)
                * thread + breeder.count[thread]; // note
                                                  // integer
                                                  // division

        // determine the parent
        int parent = pos / (breeder.lambda[subpopulation] / breeder.mu[subpopulation]); // note
                                                                                        // outer
                                                                                        // integer
                                                                                        // division

        // increment our count
        // breeder.count[thread]++;

        // and so we return the parent
        inds[start] = state.population.subpops[subpopulation].individuals[parent];

        // and so we return the parent
        return 1;
    }
}
