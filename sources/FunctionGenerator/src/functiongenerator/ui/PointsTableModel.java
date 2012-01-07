package functiongenerator.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PointsTableModel extends AbstractTableModel {

	private List<String> captions = new ArrayList<String>();
	private List<HashMap<Integer, Number>> rows = new ArrayList<HashMap<Integer, Number>>();
	private Class<?> fieldType = Double.class;

	public PointsTableModel(Class<?> fieldType) {
		this.fieldType = fieldType;
		addX();
	}

	public PointsTableModel(Class<?> fieldType, PointsTableModel dataFrom) {
		this.fieldType = fieldType;
		this.captions = dataFrom.captions;
		for (HashMap<Integer, Number> rowFrom : dataFrom.rows) {
			HashMap<Integer, Number> myRow = new HashMap<Integer, Number>();
			for (Integer key : rowFrom.keySet()) {
				if (fieldType.equals(Double.class))
					myRow.put(key, rowFrom.get(key).doubleValue());
				else
					myRow.put(key, rowFrom.get(key).intValue());
			}
			this.rows.add(myRow);
		}
	}

	public PointsTableModel(Class<?> fieldType, File file) throws IOException {
		this.fieldType = fieldType;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				HashMap<Integer, Number> myRow = new HashMap<Integer, Number>();

				while (getColumnCount() < parts.length)
					addX();

				for (int i = 0; i < parts.length - 1; ++i) {
					if (fieldType.equals(Double.class))
						myRow.put(i, Double.parseDouble(parts[i]));
					else
						myRow.put(i, (int)Double.parseDouble(parts[i]));
				}

				if (fieldType.equals(Double.class))
					myRow.put(-1, Double.parseDouble(parts[parts.length - 1]));
				else
					myRow.put(-1, (int)Double.parseDouble(parts[parts.length - 1]));
				
				rows.add(myRow);
			}

		} finally {
			if (reader != null)
				reader.close();
		}
		
		if(getColumnCount() == 1)
			addX();
	}

	@Override
	public int getColumnCount() {
		return captions.size() + 1;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col == captions.size())
			return rows.get(row).get(-1);
		return rows.get(row).get(col);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (col == captions.size())
			rows.get(row).put(-1, (Number) value);
		else
			rows.get(row).put(col, (Number) value);
		fireTableCellUpdated(row, col);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return fieldType;
	}

	public void addRow(Number[] x, Number y) {
		int idx = rows.size();

		HashMap<Integer, Number> row = new HashMap<Integer, Number>();
		row.put(-1, y);
		for (int i = 0; i < x.length; ++i) {
			row.put(i, x[i]);
		}
		rows.add(row);
		fireTableRowsInserted(idx, idx);
	}

	public void addRow() {
		Number[] x = new Number[captions.size()];
		for (int i = 0; i < x.length; ++i)
			x[i] = 0;
		addRow(x, 0);
	}

	public void removeRow(int row) {
		rows.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void addRow(HashMap<Integer, Number> row){
		rows.add(row);
	}

	public String getColumnName(int col) {
		if (col < captions.size())
			return captions.get(col);
		return "y";
	}

	public void addX() {
		Integer id = captions.size();

		captions.add("x" + id);
		for (HashMap<Integer, Number> row : rows) {
			if (fieldType.equals(Double.class))
				row.put(id, 0.0);
			else
				row.put(id, 0);
		}
		fireTableStructureChanged();
	}

	public void removeX() {
		int id = captions.size() - 1;
		if (id == 0)
			throw new RuntimeException("Cannot remove last X!");

		captions.remove(id);

		for (HashMap<Integer, Number> row : rows) {
			row.remove(id);
		}
		fireTableStructureChanged();
	}

	public List<Number[]> getRows() {
		List<Number[]> list = new ArrayList<Number[]>();
		for (HashMap<Integer, Number> row : rows) {
			Number[] converted;
			if (fieldType.equals(Double.class)){
				converted = new Double[row.size()];
				
				for (int i = 0; i < captions.size(); ++i) {
					converted[i] = row.get(i).doubleValue();
				}
			}else{
				converted = new Integer[row.size()];
				
				for (int i = 0; i < captions.size(); ++i) {
					converted[i] = row.get(i).intValue();
				}
			}
			
			converted[captions.size()] = row.get(-1);
			list.add(converted);
		}

		return list;
	}

	public void save(File file) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);

			List<Number[]> rows = getRows();
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
