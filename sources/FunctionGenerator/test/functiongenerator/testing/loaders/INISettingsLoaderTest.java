package functiongenerator.testing.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import functiongenerator.core.Settings;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.functions.integer.Add;
import functiongenerator.core.gp.providers.RangeRuntimeOperationProvider;
import functiongenerator.core.gp.providers.RuntimeOperationProvider;
import functiongenerator.core.gp.providers.SimpleOperationProvider;
import functiongenerator.core.gp.providers.factories.IntegerOperationProviderFactory;
import functiongenerator.ui.loaders.settings.INISettingsLoader;

/**
 * Integration level. Works against file system.
 * 
 * @author Piotr Jessa
 * 
 */
public class INISettingsLoaderTest extends FileTestBase {

	private INISettingsLoader loader;
	private Settings result;
	static private final String SAVED_FILE_NAME = "target_file.ini";

	@Before
	public void set_up() {
		loader = new INISettingsLoader();
	}

	@After
	public void tear_down() {
		File f = getFile(SAVED_FILE_NAME);
		if (f.exists()) {
			f.delete();
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void test_loading_non_existing_file() throws Exception {
		file = getFile("some_non_existing_file");
		loader.loadFromFile(file);
	}

	@Test
	public void test_loading_inproper_file() throws Exception {
		file = getFile("sample_0.csv");
		result = loader.loadFromFile(file);

		Settings d = Settings.getDefault();
		Assert.assertTrue(d.equals(result));
	}

	@Test
	public void test_loading_sample_file_1() throws Exception {
		file = getFile("sample_settings.ini");
		result = loader.loadFromFile(file);

		Assert.assertEquals(10, result.getGenerations());
		Assert.assertEquals(11, result.getMaxTreeDepth());
		Assert.assertEquals(12, result.getPopSize());

	}

	@Test
	public void test_saving_and_loading_cycle() throws Exception {

		File targetFile = getFile(SAVED_FILE_NAME);

		// remove file if already exists
		if (targetFile.exists()) {
			targetFile.delete();
		}

		int generations = 1;
		int maxTreeDepth = 2;
		int popSize = 3;

		IntegerOperationProviderFactory factory = new IntegerOperationProviderFactory();
		List<IOperationProvider> operations = new ArrayList<IOperationProvider>(factory.getAvaliable());

		Settings settings = new Settings();

		settings.setGenerations(generations);

		settings.setMaxTreeDepth(maxTreeDepth);

		settings.setPopulationSize(popSize);

		settings.setOperations(operations);

		loader.saveToFile(targetFile, settings);

		result = loader.loadFromFile(targetFile);

		Assert.assertEquals(settings, result);
	}

	@Test
	public void test_saving_and_loading_operatinos() throws Exception {

		File targetFile = getFile(SAVED_FILE_NAME);

		List<IOperationProvider> operations = new ArrayList<IOperationProvider>();

		IOperationProvider p1 = new SimpleOperationProvider(Add.class, "Sample name", true);
		IOperationProvider p2 = new RuntimeOperationProvider(Integer.class, false);
		Map<String, Object> params = p2.getParametersDefault();
		params.put(RuntimeOperationProvider.ATTR_VALUE, 3);

		IOperationProvider p3 = new RangeRuntimeOperationProvider(Double.class, true);
		Map<String, Object> params2 = p3.getParametersDefault();
		params2.put(RangeRuntimeOperationProvider.ATTR_STEP, 2.0);

		operations.add(p1);
		operations.add(p2);
		operations.add(p3);

		Settings s = new Settings();
		s.setOperations(operations);

		loader.saveToFile(targetFile, s);

		result = loader.loadFromFile(targetFile);

		Assert.assertEquals(3, result.getOperations().size());

		Assert.assertTrue(result.getOperations().contains(p1));
		Assert.assertTrue(result.getOperations().contains(p2));
		Assert.assertTrue(result.getOperations().contains(p3));

	}
}
