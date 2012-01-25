package functiongenerator.core.gp.providers.factories;

import java.util.List;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;
import functiongenerator.core.gp.problem.IntegerRegressionProblem;

/**
 * Provides the possible operations for the
 * {@linkplain IntegerRegressionProblem} in the form of
 * {@linkplain IOperationProvider}.
 * 
 * @author Piotr Jessa
 * 
 */
public class IntegerOperationProviderFactory implements IOperationProviderFactory {

	@Override
	public List<IOperationProvider> getAvaliable() {
		// TODO Auto-generated method stub
		return null;
	}

	// public static OperationsTableModel getInteger() {
		// OperationsTableModel model = new OperationsTableModel();
		//
		// model.addRow(true, "Add",
		// functiongenerator.core.gp.functions.integer.Add.class.getName(), "");
		// model.addRow(true, "Sub",
		// functiongenerator.core.gp.functions.integer.Sub.class.getName(), "");
		// model.addRow(true, "Mul",
		// functiongenerator.core.gp.functions.integer.Mul.class.getName(), "");
		// model.addRow(false, "Div",
		// functiongenerator.core.gp.functions.integer.Div.class.getName(), "");
		// model.addRow(true, "ProtectedDiv",
		// functiongenerator.core.gp.functions.integer.ProtectedDiv.class.getName(),
		// "Returns 0 when divisor is 0.");
		//
		// model.addRow(false, "Min",
		// functiongenerator.core.gp.functions.integer.Min.class.getName(), "");
		// model.addRow(false, "Max",
		// functiongenerator.core.gp.functions.integer.Max.class.getName(), "");
		// model.addRow(false, "And",
		// functiongenerator.core.gp.functions.integer.And.class.getName(),
		// "Bitwise and.");
		// model.addRow(false, "Or",
		// functiongenerator.core.gp.functions.integer.Or.class.getName(),
		// "Bitwise or.");
		// model.addRow(false, "Xor",
		// functiongenerator.core.gp.functions.integer.Xor.class.getName(),
		// "Bitwise xor.");
		// model.addRow(false, "Not",
		// functiongenerator.core.gp.functions.integer.Not.class.getName(),
		// "Bitwise not.");
		//
		// return model;
		// }
}
