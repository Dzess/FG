package functiongenerator.engine.loaders;

import java.io.File;
import java.io.IOException;

import functiongenerator.engine.Engine;

/**
 * Specifies the interface for saving the whole state of the engine.
 * 
 * Uses the {@linkplain Engine} objects for the data state preserving, because
 * they are UI agnostic.
 * 
 * @author Piotr Jessa
 */
public interface IStateLoader {

	/**
	 * Creates the {@linkplain Engine} instance basing on the data read.
	 * 
	 * @param file
	 *            : file in which the engine state is preserved
	 * @return New instance of engine with all valid data set.
	 * @throws IOException
	 */
	public Engine loadFromFile(File file) throws IOException;

	/**
	 * Saves the engine into file.
	 * 
	 * @param file
	 *            : where to be saved
	 * @param engine
	 *            : the model of the experiment
	 * @throws IOException
	 */
	public void saveToFile(File file, Engine engine) throws IOException;
}
