package functiongenerator.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;

/**
 * Models the list of the available functions. Mainly used for viewing purposes
 * of this. Showing different edition possibilities for each of the
 * {@linkplain IOperationProvider}.
 * 
 * @author Piotr Jessa
 * 
 */
@SuppressWarnings("serial")
public class OperationsTableModel extends AbstractTableModel {

	static private final String[] CONSTANT_CAPTIONS = new String[] { "", "Operation", "Full name", "Comment" };

	private List<String> captions;
	private List<Object[]> rows = new ArrayList<Object[]>();

	private final IOperationProviderFactory factory;

	public OperationsTableModel(IOperationProviderFactory factory) {

		if (factory == null)
			throw new IllegalArgumentException("The factory must be presented");

		this.factory = factory;

		this.captions = new ArrayList<String>();
		for (int i = 0; i < CONSTANT_CAPTIONS.length; i++) {
			this.captions.add(CONSTANT_CAPTIONS[i]);
		}

		// get the longest parameters list
		int maxParameters = 0;
		for (IOperationProvider provider : factory.getAvaliable()) {
			int size = provider.getParameters().size();
			if (size > maxParameters) {
				maxParameters = size;
			}
		}

		// add the maximum elements to captions count
		for (int i = 0; i < maxParameters; i++) {
			String newCaption = "Arg " + Integer.toString(i);
			captions.add(newCaption);
		}

	}

	@Override
	public int getColumnCount() {
		return captions.size();
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		// TODO: this code should be far more advanced here
		return col == 0;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		rows.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		// TODO: this code is pretty dependent on the IOperationProvider etc
		return c == 0 ? Boolean.class : String.class;
	}

	private void addRow(Boolean checked, String operation, String fullname, String comment) {
		int idx = rows.size();
		rows.add(new Object[] { checked, operation, fullname, comment });
		fireTableRowsInserted(idx, idx);
	}

	private void removeRow(int row) {
		rows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public String getColumnName(int col) {
		return captions.get(col);
	}

	public List<String> getSelectedRows() {
		List<String> selected = new ArrayList<String>();
		for (Object[] row : rows) {
			if (((Boolean) row[0])) {
				selected.add((String) row[2]);
			}
		}
		return selected;
	}

	public List<IOperationProvider> getSelectedOperations() {
		List<IOperationProvider> selected = new ArrayList<IOperationProvider>();

		// TODO: get the base of the selected element into the abstraction of
		// operation provider

		return selected;
	}

}
