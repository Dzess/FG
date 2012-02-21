package functiongenerator.core;

import ec.gp.GPIndividual;

public interface IProgressListener {
	void update(double done, String message, GPIndividual individual);
}
