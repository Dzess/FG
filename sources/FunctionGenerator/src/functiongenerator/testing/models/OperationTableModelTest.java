package functiongenerator.testing.models;

import org.junit.Test;

import functiongenerator.core.gp.IOperationProviderFactory;
import functiongenerator.core.gp.providers.factories.IntegerOperationProviderFactory;
import functiongenerator.core.gp.providers.factories.RealOperationProviderFactory;
import functiongenerator.ui.OperationsTableModel;

/**
 * Integration level testing of the {@linkplain OperationsTableModel}. With
 * already existing classes for {@linkplain IOperationProviderFactory}.
 * 
 * @author Piotr Jessa
 * 
 */
public class OperationTableModelTest {

	private OperationsTableModel model;
	private IOperationProviderFactory factory;

	@Test(expected = IllegalArgumentException.class)
	public void model_throws_exception_without_factory() {
		new OperationsTableModel(null);
	}

	@Test
	public void model_works_well_with_real_operations_provider() {
		factory = new RealOperationProviderFactory();
		model = new OperationsTableModel(factory);
	}

	@Test
	public void model_works_well_with_integer_operations_provider() {
		factory = new IntegerOperationProviderFactory();
		model = new OperationsTableModel(factory);
	}	
}
