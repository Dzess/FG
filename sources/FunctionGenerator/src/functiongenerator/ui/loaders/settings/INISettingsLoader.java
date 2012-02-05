package functiongenerator.ui.loaders.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Wini;

import ec.gp.GPNode;
import functiongenerator.core.Settings;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.providers.RangeRuntimeOperationProvider;
import functiongenerator.core.gp.providers.RuntimeOperationProvider;
import functiongenerator.core.gp.providers.SimpleOperationProvider;

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

	private static final String OPERATION_NODE_CLASS = "Operation Node Class";

	private static final String OPERATION_TYPE_CLASS = "Operation Type Class";

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
		operationCounter = operationCounter - 1;

		if (operationCounter != 0) {
			List<IOperationProvider> operations = new ArrayList<IOperationProvider>();

			while (operationCounter > 0) {

				IOperationProvider op = loadOperation(wini);
				if (op != null) {
					// there was an error during loading
					operations.add(op);
				}

				operationCounter--;
			}

			s.setOperations(operations);
		}

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
		String sectionName = getOperationID();

		wini.put(getOperationID(), OPERATION_CLASS, className);

		// totally ugly switch statement for saving the proper information about
		// each of the saved classes
		// TODO: this code can be refactored out of here to be more OO
		if (cls == SimpleOperationProvider.class) {
			SimpleOperationProvider sProvider = (SimpleOperationProvider) provider;
			Class<? extends GPNode> nodeCls = sProvider.getNodeClass();

			wini.put(sectionName, OPERATION_NODE_CLASS, nodeCls.getCanonicalName());
			wini.put(sectionName, OPERATION_NAME, provider.getName());
			wini.put(sectionName, OPERATION_COMMENT, provider.getComment());

		} else if (cls == RuntimeOperationProvider.class) {
			RuntimeOperationProvider rProvider = (RuntimeOperationProvider) provider;
			Class<? extends Number> typeCls = rProvider.getOperationType();
			wini.put(sectionName, OPERATION_TYPE_CLASS, typeCls.getCanonicalName());

		} else if (cls == RangeRuntimeOperationProvider.class) {

			RangeRuntimeOperationProvider rProvider = (RangeRuntimeOperationProvider) provider;
			Class<? extends Number> typeCls = rProvider.getOperationType();
			wini.put(sectionName, OPERATION_TYPE_CLASS, typeCls.getCanonicalName());
		}

		// common operations for all the providers
		wini.put(getOperationID(), OPERATION_SELECTED, provider.isEnableByDefault());
		wini.put(getOperationID(), OPERATION_MAP, provider.getParameters());

	}

	private IOperationProvider loadOperation(Wini wini) {

		String sectionName = getOperationID();
		String clsName = wini.get(sectionName, OPERATION_CLASS, String.class);

		Boolean selected = wini.get(sectionName, OPERATION_SELECTED, boolean.class);
		Object map = wini.get(sectionName, OPERATION_MAP);

		// try loading this class name and
		try {
			Class<?> cls = Class.forName(clsName);
			IOperationProvider provider = null;
			if (cls == SimpleOperationProvider.class) {

				// read data typical for Simple Operation Provider
				String name = wini.get(sectionName, OPERATION_NAME, String.class);
				String comment = wini.get(sectionName, OPERATION_COMMENT, String.class);
				String nodeClassName = wini.get(sectionName, OPERATION_NODE_CLASS, String.class);

				Class<? extends GPNode> nodeClass = (Class<? extends GPNode>) Class.forName(nodeClassName);
				SimpleOperationProvider sProvider = new SimpleOperationProvider(nodeClass, name, comment, false);

				provider = sProvider;
			} else if (cls == RuntimeOperationProvider.class) {

				String typeClassName = wini.get(sectionName, OPERATION_TYPE_CLASS, String.class);
				Class<? extends Number> type = (Class<? extends Number>) Class.forName(typeClassName);

				RuntimeOperationProvider rProvider = new RuntimeOperationProvider(type, false);

				provider = rProvider;
			} else if (cls == RangeRuntimeOperationProvider.class) {

				String typeClassName = wini.get(sectionName, OPERATION_TYPE_CLASS, String.class);
				Class<? extends Number> type = (Class<? extends Number>) Class.forName(typeClassName);

				RangeRuntimeOperationProvider rProvider = new RangeRuntimeOperationProvider(type, false);

				provider = rProvider;
			}

			provider.setEnabled(selected);

			System.out.println(map.getClass());
			
			// creating the parameters here
			Map<String, String> textMap = (Map<String, String>) map;
			

			// using the types provided by provider perform casting
			Map<String, Object> params = new HashMap<String, Object>();
			for (Map.Entry<String, String> e : textMap.entrySet()) {
				String key = e.getKey();
				Class<?> type = provider.getParametersTypes().get(key);

			}

			provider.setParameters(params);

			return provider;
		} catch (Exception e) {
			logger.warn("Could not instatniate the operation provider", e);
			return null;
		}
	}
}