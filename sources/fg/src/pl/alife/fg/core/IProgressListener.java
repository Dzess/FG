package pl.alife.fg.core;

public interface IProgressListener {
    void update(String message, EvolutionStateHelper individual);
}
