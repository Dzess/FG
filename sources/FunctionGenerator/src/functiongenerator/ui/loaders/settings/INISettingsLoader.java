package functiongenerator.ui.loaders.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Wini;

import functiongenerator.core.Settings;
import functiongenerator.core.gp.IOperationProvider;

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

	static private final String OPERATIONS_SIZE = "Operations Size";

	static private final String OPERATION_CLASS = "Operation Class";

	private static final String OPERATION_NAME = "Operation Name";

	private static final String OPERATION_COMMENT = "Operation Comment";

	private static final String OPERATION_SELECTED = "Operation Selected";

	private static final String OPERATION_MAP = "Operation Parmeters";

	private int operationCounter;

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

		
		// reading the operations
		operationCounter = wini.get(OPERATION_SECTION, OPERATIONS_SIZE, int.class);

		if (operationCounter != 0) {
			List<IOperationProvider> operations = new ArrayList<IOperationProvider>();

			while (operationCounter > 0) {

				IOperationProvider op = loadOperation();
				operations.add(op);

				operationCounter--;
			}

			s.setOperations(operations);
		}

		return s;
	}

	private IOperationProvider loadOperation() {
		// TODO Auto-generated method stub
		return null;
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

		operationCounter = 0;
		for (IOperationProvider provider : settings.getOperations()) {
			saveOperation(wini, provider);
			operationCounter++;
		}

		wini.put(OPERATION_SECTION, OPERATIONS_SIZE, operationCounter);

		wini.store();
	}

	/**
	 * Gets section name for the current operation
	 */
	private String getOperationID() {
		return OPERATION_SECTION + "." + operationCounter;
	}

	private void saveOperation(Wini wini, IOperationProvider provider) {

		// get the type of this provider:
		Class<? extends IOperationProvider> cls = provider.getClass();
		String className = cls.getCanonicalName();

		wini.put(getOperationID(), OPERATION_CLASS, className);

		wini.put(getOperationID(), OPERATION_NAME, provider.getName());

		wini.put(getOperationID(), OPERATION_COMMENT, provider.getComment());

		wini.put(getOperationID(), OPERATION_SELECTED, provider.isEnableByDefault());

		wini.put(getOperationID(), OPERATION_MAP, provider.getParameters());

	}
}