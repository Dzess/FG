package functiongenerator.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;

/**
 * Models the list of the available functions. Mainly used for viewing purposes
 * of this. Showing different edition possibilities for each of the
 * {@linkplain IOperationProvider}.
 * 
 * <p>
 * Manages the {@code isEditable()} property of cell basing on the additional
 * parameters passed via {@linkplain IOperationProvider}.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
@SuppressWarnings("serial")
public class OperationsTableModel extends AbstractTableModel {

	static private final String[] CONSTANT_CAPTIONS = new String[] { "", "Operation", "Comment" };
	static private final String NA_VALUE = "N/A";

	static private final Log logger = LogFactory.getLog(OperationsTableModel.class);

	private List<String> captions;
	private List<Object[]> rows = new ArrayList<Object[]>();
	private Map<IOperationProvider, Map<String, Object>> parameters;

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

		this.parameters = new HashMap<IOperationProvider, Map<String, Object>>();

		for (IOperationProvider provider : factory.getAvaliable()) {
			addRow(provider);

			// TODO: add some information about defaults - might be useful when
			// other values are needed
			Map<String, Object> parametersMapPerProvider = new TreeMap<String, Object>();
			this.parameters.put(provider, parametersMapPerProvider);
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

		for (int i = 0; i < maxParameters; i++) {

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

		int offset = CONSTANT_CAPTIONS.length;
		int parameterIndex = col - offset;

		if (parameterIndex >= 0) {
			IOperationProvider provider = factory.getAvaliable().get(row);

			// get from the position the key value pair
			List<Entry<String, Class<?>>> params = new ArrayList<Entry<String, Class<?>>>(provider.getParameters().entrySet());
			Entry<String, Class<?>> e = params.get(parameterIndex);

			// check type
			try {
				if (e.getValue() == Integer.class) {

					Integer i = Integer.parseInt(value.toString());
					Map<String, Object> toBeSetParams = this.parameters.get(provider);
					toBeSetParams.put(e.getKey(), i);

					provider.setParameters(toBeSetParams);

				}
				// NOTE: add another data types here

			} catch (NumberFormatException exp) {
				logger.warn("The illegal format for number", exp);
				return;
			}
		}

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

	public List<IOperationProvider> getSelectedOperations() {
		List<IOperationProvider> selected = new ArrayList<IOperationProvider>();

		int index = 0;
		for (Object[] row : rows) {

			// if checked
			if ((Boolean) row[0]) {
				IOperationProvider provider = factory.getAvaliable().get(index);
				selected.add(provider);
			}
			index++;
		}
		return selected;
	}

}
