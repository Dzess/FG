package functiongenerator.core;


public interface IProgressListener {
	void update(String message, EvolutionStateHelper individual);
}
