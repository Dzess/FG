package functiongenerator.ui.loaders.settings;

import java.io.File;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Wini;

import functiongenerator.core.Settings;

/**
 * Writes and loads the settings of the experiment into INI file format.
 * 
 * @author Piotr Jessa
 * 
 */
public class INISettingsLoader implements ISettingsLoader {

	static private final Log logger = LogFactory.getLog(INISettingsLoader.class);

	static private final String MAX_TREE_DEPTH = "MaxTreeDepth";
	static private final String POPULATION_SIZE = "PopulationSize";
	static private final String GENERATIONS = "Generations";

	static private final String BASIC_SECTION = "Basic";
	static private final String OPERATION_SECTION = "Operations";

	@Override
	public Settings loadFromFile(File file) throws IOException {
		Settings s = Settings.getDefault();

		Wini wini = new Wini(file);

		int generations = wini.get(BASIC_SECTION, GENERATIONS, int.class);
		int popSize = wini.get(BASIC_SECTION, POPULATION_SIZE, int.class);
		int maxTreeDepth = wini.get(BASIC_SECTION, MAX_TREE_DEPTH, int.class);

		if (generations != 0) {
			s.setGenerations(generations);
		}

		if (popSize != 0) {
			s.setPopulationSize(popSize);
		}

		if (maxTreeDepth != 0) {
			s.setMaxTreeDepth(maxTreeDepth);
		}
		
		// TODO: write info about operations
		return s;
	}

	@Override
	public void saveToFile(File file, Settings settings) throws IOException {

		if (file.exists()) {
			file.delete();
		}

		file.createNewFile();

		Wini wini = new Wini(file);

		wini.put(BASIC_SECTION, GENERATIONS, settings.getGenerations());
		wini.put(BASIC_SECTION, POPULATION_SIZE, settings.getPopSize());
		wini.put(BASIC_SECTION, MAX_TREE_DEPTH, settings.getMaxTreeDepth());

		// TODO: write the code for setting the operations

		wini.store();
	}
}