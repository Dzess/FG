package functiongenerator.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import functiongenerator.core.Settings;
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

		// TODO: write asserting the list of operations
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

		Settings settings = new Settings();

		settings.setGenerations(generations);

		settings.setMaxTreeDepth(maxTreeDepth);

		settings.setPopulationSize(popSize);

		loader.saveToFile(targetFile, settings);

		result = loader.loadFromFile(targetFile);

		Assert.assertEquals(settings, result);
	}

}
