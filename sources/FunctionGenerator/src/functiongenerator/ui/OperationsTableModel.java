package functiongenerator.ui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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

	static private final String[] CONSTANT_CAPTIONS = new String[] { "", "Operation", "Comment" };
	static private final String NA_VALUE = "N/A";

	private List<String> captions;
	private List<Object[]> rows = new ArrayList<Object[]>();

	private final IOperationProviderFactory factory;

	private int maxParameters;

	public OperationsTableModel(IOperationProviderFactory factory) {

		if (factory == null)
			throw new IllegalArgumentException("The factory must be presented");

		this.factory = factory;

		this.captions = new ArrayList<String>();
		for (int i = 0; i < CONSTANT_CAPTIONS.length; i++) {
			this.captions.add(CONSTANT_CAPTIONS[i]);
		}

		setColumns();
		setRows();

	}

	private void setRows() {
		// TODO: maybe the rows should be copied where they should be
		// prepare the view of the rows here
		for (IOperationProvider provider : factory.getAvaliable()) {
			addRow(provider);
		}
	}

	private void setColumns() {

		// get the longest parameters list
		int maxParameters = 0;
		for (IOperationProvider provider : factory.getAvaliable()) {
			int size = provider.getParameters().size();
			if (size > maxParameters) {
				maxParameters = size;
			}
		}

		this.maxParameters = maxParameters;

		// add the maximum elements to captions count
		for (int i = 0; i < maxParameters; i++) {
			String newCaption = "Arg " + Integer.toString(i);
			captions.add(newCaption);
		}
	}

	private void addRow(IOperationProvider provider) {

		int idx = rows.size();
		Boolean checked = provider.isEnableByDefault();
		String name = provider.getName();
		String comment = provider.getComment();

		List<Object> tableRow = new LinkedList<Object>();
		tableRow.add(checked);
		tableRow.add(name);
		tableRow.add(comment);

		List<Entry<String, String>> sortedSet = new LinkedList<Entry<String, String>>(provider.getParametersDefault().entrySet());

		for (int i = 0; i < maxParameters ; i++) {

			// try getting the default value for this parameter
			if (sortedSet.size() > i) {
				String value = sortedSet.get(i).getValue();
				tableRow.add(value);
			} else {
				// if cannot be done put default for non acceptable parameters
				tableRow.add(NA_VALUE);
			}
		}

		rows.add(tableRow.toArray());
		fireTableRowsInserted(idx, idx);
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

		if (col == 0) {
			return true;
		}

		IOperationProvider provider = factory.getAvaliable().get(row);
		int total = provider.getParameters().size();
		int offset = CONSTANT_CAPTIONS.length;

		if (col - offset < 0) {
			return false;
		}

		if (col - offset < total) {
			return true;
		}

		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		// TODO: this code logic should be more advanced
		// this code should be updating the parameters map of every provider
		rows.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return c == 0 ? Boolean.class : String.class;
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
