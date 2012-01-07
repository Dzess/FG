package functiongenerator.loaders;

import java.io.File;
import java.io.IOException;

import functiongenerator.ui.PointsTableModel;

public interface ILoader {
	public PointsTableModel loadFromFile(File file, Class<?> fieldType)
			throws IOException;
}
