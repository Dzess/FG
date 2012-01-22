package functiongenerator.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

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

public class OperationsTableModel extends AbstractTableModel {

	private String[] captions = new String[] { "", "Operation", "Full name", "Comment" };
	private List<Object[]> rows = new ArrayList<Object[]>();

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

	protected OperationsTableModel() {

	}

	public static OperationsTableModel getReal() {
		OperationsTableModel model = new OperationsTableModel();

		model.addRow(true, "Add", Add.class.getName(), "");
		model.addRow(true, "Sub", Sub.class.getName(), "");
		model.addRow(true, "Mul", Mul.class.getName(), "");
		model.addRow(false, "Div", Div.class.getName(), "");
		model.addRow(true, "ProtectedDiv", ProtectedDiv.class.getName(), "Returns 0 when divisor is 0.");
		// model.addRow(true, "0", Zero.class.getName(), "0, constant.");
		// model.addRow(true, "1", One.class.getName(), "1, constant.");
		// model.addRow(true, "2", Two.class.getName(), "2, constant.");
		// model.addRow(true, "3", Three.class.getName(), "3, constant.");
		// model.addRow(true, "4", Four.class.getName(), "4, constant.");
		// model.addRow(true, "5", Five.class.getName(), "5, constant.");
		// model.addRow(true, "Value", Value.class.getName(),
		// "Value defined as constant");
		model.addRow(false, "Exp", Exp.class.getName(), "");
		model.addRow(false, "Pow", Pow.class.getName(), "");
		model.addRow(false, "Log", Log.class.getName(), "");
		model.addRow(false, "ProtectedLog", ProtectedLog.class.getName(), "Returns 0 when argument is less or equal 0.");
		model.addRow(false, "Min", Min.class.getName(), "");
		model.addRow(false, "Max", Max.class.getName(), "");
		model.addRow(false, "Sin", Sin.class.getName(), "");
		model.addRow(false, "Cos", Cos.class.getName(), "");

		return model;
	}

	public static OperationsTableModel getInteger() {
		OperationsTableModel model = new OperationsTableModel();

		model.addRow(true, "Add", functiongenerator.core.gp.functions.integer.Add.class.getName(), "");
		model.addRow(true, "Sub", functiongenerator.core.gp.functions.integer.Sub.class.getName(), "");
		model.addRow(true, "Mul", functiongenerator.core.gp.functions.integer.Mul.class.getName(), "");
		model.addRow(false, "Div", functiongenerator.core.gp.functions.integer.Div.class.getName(), "");
		model.addRow(true, "ProtectedDiv", functiongenerator.core.gp.functions.integer.ProtectedDiv.class.getName(),
				"Returns 0 when divisor is 0.");
		// model.addRow(true, "Value",
		// functiongenerator.gp.functions.integer.Value.class.getName(),
		// "Value defined as constant");
		// model.addRow(true, "0",
		// functiongenerator.gp.functions.integer.Zero.class.getName(),
		// "0, constant.");
		// model.addRow(true, "1",
		// functiongenerator.gp.functions.integer.One.class.getName(),
		// "1, constant.");
		// model.addRow(true, "2",
		// functiongenerator.gp.functions.integer.Two.class.getName(),
		// "2, constant.");
		// model.addRow(true, "3",
		// functiongenerator.gp.functions.integer.Three.class.getName(),
		// "3, constant.");
		// model.addRow(true, "4",
		// functiongenerator.gp.functions.integer.Four.class.getName(),
		// "4, constant.");
		// model.addRow(true, "5",
		// functiongenerator.gp.functions.integer.Five.class.getName(),
		// "5, constant.");
		model.addRow(false, "Min", functiongenerator.core.gp.functions.integer.Min.class.getName(), "");
		model.addRow(false, "Max", functiongenerator.core.gp.functions.integer.Max.class.getName(), "");
		model.addRow(false, "And", functiongenerator.core.gp.functions.integer.And.class.getName(), "Bitwise and.");
		model.addRow(false, "Or", functiongenerator.core.gp.functions.integer.Or.class.getName(), "Bitwise or.");
		model.addRow(false, "Xor", functiongenerator.core.gp.functions.integer.Xor.class.getName(), "Bitwise xor.");
		model.addRow(false, "Not", functiongenerator.core.gp.functions.integer.Not.class.getName(), "Bitwise not.");

		return model;
	}
}
