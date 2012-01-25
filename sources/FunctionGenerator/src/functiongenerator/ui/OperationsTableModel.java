package functiongenerator.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;
import functiongenerator.core.gp.functions.real.Add;
import functiongenerator.core.gp.functions.real.Cos;
import functiongenerator.core.gp.functions.real.Div;
import functiongenerator.core.gp.functions.real.Exp;
import functiongenerator.core.gp.functions.real.Log;
import functiongenerator.core.gp.functions.real.Max;
import functiongenerator.core.gp.functions.real.Min;
import functiongenerator.core.gp.functions.real.Mul;
import functiongenerator.core.gp.functions.real.Pow;
import functiongenerator.core.gp.functions.real.ProtectedDiv;
import functiongenerator.core.gp.functions.real.ProtectedLog;
import functiongenerator.core.gp.functions.real.Sin;
import functiongenerator.core.gp.functions.real.Sub;
import functiongenerator.core.gp.functions.real.Value;

/**
 * Models the list of the available functions. Mainly used for viewing purposes
 * of this. Showing different edition possibilities for each of the
 * {@linkplain IOperationProvider}.
 * 
 * @author Piotr Jessa
 * 
 */
public class OperationsTableModel extends AbstractTableModel {

	private String[] captions = new String[] { "", "Operation", "Full name", "Comment" };
	private List<Object[]> rows = new ArrayList<Object[]>();

	private final IOperationProviderFactory factory;

	@Override
	public int getColumnCount() {
		return captions.length;
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
		return col == 0;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		rows.get(row)[col] = value;
		fireTableCellUpdated(row, col);
	}

	@Override
	public Class getColumnClass(int c) {
		return c == 0 ? Boolean.class : String.class;
	}

	public void addRow(Boolean checked, String operation, String fullname, String comment) {
		int idx = rows.size();
		rows.add(new Object[] { checked, operation, fullname, comment });
		fireTableRowsInserted(idx, idx);
	}

	public void removeRow(int row) {
		rows.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public String getColumnName(int col) {
		return captions[col];
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

	protected OperationsTableModel(IOperationProviderFactory factory) {
		this.factory = factory;
	}	
}
