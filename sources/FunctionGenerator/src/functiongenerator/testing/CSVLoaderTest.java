package functiongenerator.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import functiongenerator.loaders.CSVLoader;
import functiongenerator.ui.PointsTableModel;

public class CSVLoaderTest {

	private CSVLoader loader;
	private File file;
	private PointsTableModel result;

	@Before
	public void set_up() {
		loader = new CSVLoader();
	}

	@Test(expected = FileNotFoundException.class)
	public void test_loading_non_exitsting_file() throws IOException {
		file = new File("some_none_existing_file");
		loader.loadFromFile(file, Integer.class);
	}

	@Test
	public void test_loading_empty_file_should_pass() throws IOException {
		file = new File("empty.csv");
		result = loader.loadFromFile(file, Integer.class);
		
		Assert.assertEquals(0, result.getRowCount());
	}

	@Test
	public void test_loading_sample_0_file_integers() throws IOException {
		file = new File("sample_0.csv");
		result = loader.loadFromFile(file, Integer.class);
		
		Assert.assertEquals(4, result.getRowCount());
	}

	@Test
	public void test_loading_sample_0_file_doubles() throws IOException {
		file = new File("sample_0.csv");
		result = loader.loadFromFile(file, Double.class);
		
		Assert.assertEquals(4, result.getRowCount());
	}
}
