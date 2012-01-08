package functiongenerator.loaders;

import java.io.File;
import java.io.IOException;

import functiongenerator.ui.PointsTableModel;

/**
 * Describes the loading and saving interface for table data. It is
 * <b>only</b> for table data.
 * 
 * @author Piotr Jessa
 */
public interface ILoader {
	
	public PointsTableModel loadFromFile(File file, Class<?> fieldType)
			throws IOException;

	public void saveToFile(File file, PointsTableModel model)
			throws IOException;
}
