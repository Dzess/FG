package functiongenerator.ui.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import functiongenerator.ui.PointsTableModel;

/**
 * Loads the Comma Separated Values file with the data to the program.
 * 
 * TODO: add various options to data handling
 * 
 * @author Piotr Jessa
 * 
 */
public class CSVDataLoader implements IDataLoader {

	public CSVDataLoader(){
		
	}
	
	@Override
	public PointsTableModel loadFromFile(File file, Class<?> fieldType) throws IOException {

		PointsTableModel model = new PointsTableModel(fieldType);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				HashMap<Integer, Number> myRow = new HashMap<Integer, Number>();

				while (model.getColumnCount() < parts.length)
					model.addX();

				for (int i = 0; i < parts.length - 1; ++i) {
					if (fieldType.equals(Double.class))
						myRow.put(i, Double.parseDouble(parts[i]));
					else
						myRow.put(i, (int) Double.parseDouble(parts[i]));
				}

				if (fieldType.equals(Double.class))
					myRow.put(-1, Double.parseDouble(parts[parts.length - 1]));
				else
					myRow.put(-1,
							(int) Double.parseDouble(parts[parts.length - 1]));

				model.addRow(myRow);
			}

		} finally {
			if (reader != null)
				reader.close();
		}

		if (model.getColumnCount() == 1)
			model.addX();

		return model;
	}

	@Override
	public void saveToFile(File file, PointsTableModel model) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);

			List<Number[]> rows = model.getRows();
			for (Number[] row : rows) {
				for (Number n : row) {
					writer.write(n + ",");
				}
				writer.append('\n');
			}

		} finally {
			if (writer != null)
				writer.close();
		}
	}

}
