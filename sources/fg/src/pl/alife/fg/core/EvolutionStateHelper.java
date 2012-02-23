package pl.alife.fg.core;

import ec.EvolutionState;
import ec.gp.GPIndividual;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleStatistics;

/**
 * Helper DTO class easing the access to commonly used features of
 * {@linkplain EvolutionState}
 */
public class EvolutionStateHelper {

    private final EvolutionState state;
    private final SimpleStatistics stat;

    public EvolutionStateHelper(EvolutionState state) {
        this.state = state;
        stat = (SimpleStatistics) state.statistics;
    }

    public GPIndividual getBesIndividual() {
        return (GPIndividual) stat.best_of_run[0];
    }

    public int getGeneration() {
        return state.generation;
    }

    public double getCompletedPercent() {
        int currentGen = this.getGeneration();

        return ((double) (currentGen * 100)) / (double) state.numGenerations;
    }

    public double getFitness() {
        GPIndividual best = this.getBesIndividual();
        KozaFitness fitness = (KozaFitness) best.fitness;
        return fitness.standardizedFitness();
    }
}