package functiongenerator.testing.models;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.providers.factories.IntegerOperationProviderFactory;
import functiongenerator.ui.OperationsTableModel;

/**
 * Tests the {@linkplain OperationsTableModel} against the problem the factory
 * of {@linkplain IntegerOperationProviderFactory}
 * 
 * @author Piotr Jessa
 * 
 */
public class OperationTableModelWithIntegerTest {

	private OperationsTableModel model;
	private IntegerOperationProviderFactory factory;

	@Before
	public void set_up() {
		factory = new IntegerOperationProviderFactory();
		model = new OperationsTableModel(factory);
	}

	@Test
	public void operations_has_6_columns() {
		Assert.assertEquals(6, model.getColumnCount());
	}

	@Test
	public void column_zero_is_always_editable() {
		int col = 0;
		for (int row = 0; row < model.getRowCount(); row++) {
			Assert.assertTrue(model.isCellEditable(row, col));
		}
	}

	@Test
	public void columns_one_up_to_three_are_no_editable() {
		for (int col = 1; col < 3; col++) {
			for (int row = 0; row < model.getRowCount(); row++) {
				Assert.assertFalse(model.isCellEditable(row, col));
			}
		}
	}

	/***
	 * On the row where {@linkplain IOperationProvider} shows the non empty map
	 * of parameters, the edition should be possible.
	 * 
	 * Otherwise not.
	 */
	@Test
	public void some_rows_are_editable_on_extended_columns() {

		int columnOffset = 3;

		int row = 0;
		for (IOperationProvider provider : factory.getAvaliable()) {

			for (int i = 0; i < provider.getParametersTypes().size(); i++) {

				boolean result = model.isCellEditable(row, columnOffset + i);
				Assert.assertTrue("The cell is not editable", result);

			}
			row++;
		}
	}
}
