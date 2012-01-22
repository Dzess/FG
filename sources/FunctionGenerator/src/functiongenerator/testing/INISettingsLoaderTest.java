package functiongenerator.testing;

import java.io.FileNotFoundException;
import java.util.IllegalFormatException;

import junit.framework.Assert;

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

	@Before
	public void set_up() {
		loader = new INISettingsLoader();
	}

	@Test(expected = FileNotFoundException.class)
	public void test_loading_non_existing_file() throws Exception {
		file = getFile("some_non_existing_file");
		loader.loadFromFile(file);
	}

	@Test(expected = IllegalFormatException.class)
	public void test_loading_inproper_file() throws Exception {
		file = getFile("sample_0.csv");
		loader.loadFromFile(file);
	}

	@Test
	public void test_loading_empty_file_should_set_defaults() throws Exception {
		file = getFile("empty.csv");
		result = loader.loadFromFile(file);

		Assert.assertEquals(Settings.getDefault(), result);
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
	public void test_saving_and_loading_cycle() {
		// TODO: wirte the test for loading and reading the file
		Assert.fail("Not yet implemented");
	}

}
