package pl.alife.fg.testing.models;

import org.junit.Test;

import pl.alife.fg.core.gp.IOperationProviderFactory;
import pl.alife.fg.core.gp.providers.factories.IntegerOperationProviderFactory;
import pl.alife.fg.core.gp.providers.factories.RealOperationProviderFactory;
import pl.alife.fg.ui.OperationsTableModel;


/**
 * Integration level testing of the {@linkplain OperationsTableModel}. With
 * already existing classes for {@linkplain IOperationProviderFactory}.
 * 
 * @author Piotr Jessa
 * 
 */
public class OperationTableModelTest {

    private IOperationProviderFactory factory;

    @Test(expected = IllegalArgumentException.class)
    public void model_throws_exception_without_factory() {
        new OperationsTableModel(null);
    }

    @Test
    public void model_works_well_with_real_operations_provider() {
        factory = new RealOperationProviderFactory();
        new OperationsTableModel(factory);
    }

    @Test
    public void model_works_well_with_integer_operations_provider() {
        factory = new IntegerOperationProviderFactory();
        new OperationsTableModel(factory);
    }
}
