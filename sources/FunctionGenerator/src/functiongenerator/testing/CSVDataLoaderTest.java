package functiongenerator.testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import functiongenerator.ui.PointsTableModel;
import functiongenerator.ui.loaders.data.CSVDataLoader;

/**
 * Integration level. Works against file system.
 * 
 * @author Piotr Jessa
 * 
 */
public class CSVDataLoaderTest {

	private CSVDataLoader loader;
	private File file;
	private PointsTableModel result;

	static private String getLocation(String fileName) {
		return "resources\\" + fileName;
	}

	static private File getFile(String fileName) {
		return new File(getLocation(fileName));
	}

	@Before
	public void set_up() {
		loader = new CSVDataLoader();
	}

	@Test(expected = FileNotFoundException.class)
	public void test_loading_non_exitsting_file() throws IOException {
		file = getFile("some_none_existing_file");
		loader.loadFromFile(file, Integer.class);
	}

	@Test
	public void test_loading_empty_file_should_pass() throws IOException {
		file = getFile("empty.csv");
		result = loader.loadFromFile(file, Integer.class);

		Assert.assertEquals(0, result.getRowCount());

		// two columns are default value
		Assert.assertEquals(2, result.getColumnCount());
	}

	@Test
	public void test_loading_sample_0_file_integers() throws IOException {
		file = getFile("sample_0.csv");
		result = loader.loadFromFile(file, Integer.class);

		Assert.assertEquals(6, result.getRowCount());
		Assert.assertEquals(2, result.getColumnCount());

		for (Number[] a : result.getRows()) {
			System.out.println("Line:");
			for (Number n : a) {
				System.out.print(n);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

	@Test
	public void test_loading_sample_0_file_doubles() throws IOException {
		file = getFile("sample_0.csv");
		result = loader.loadFromFile(file, Double.class);

		Assert.assertEquals(6, result.getRowCount());
		Assert.assertEquals(2, result.getColumnCount());
	}

	@Test
	public void test_loading_sample_2_file_two_Xes() throws IOException {
		file = getFile("sample_2.csv");
		result = loader.loadFromFile(file, Integer.class);

		Assert.assertEquals(6, result.getRowCount());
		Assert.assertEquals(3, result.getColumnCount());

	}
}
