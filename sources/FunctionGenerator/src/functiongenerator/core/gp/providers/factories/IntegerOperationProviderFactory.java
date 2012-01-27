package functiongenerator.core.gp.providers.factories;

import java.util.ArrayList;
import java.util.List;

import functiongenerator.core.gp.functions.integer.*;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;

import functiongenerator.core.gp.problem.IntegerRegressionProblem;
import functiongenerator.core.gp.providers.RuntimeOperationProvider;
import functiongenerator.core.gp.providers.SimpleOperationProvider;

/**
 * Provides the possible operations for the
 * {@linkplain IntegerRegressionProblem} in the form of
 * {@linkplain IOperationProvider}.
 * 
 * <p>
 * This class is the <i>extension point</i> for adding new types of operations
 * to the program.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class IntegerOperationProviderFactory implements IOperationProviderFactory {

	private final List<IOperationProvider> avaliable;

	public IntegerOperationProviderFactory() {
		avaliable = new ArrayList<IOperationProvider>();

		// NOTE: add here next supported operations
		avaliable.add(new SimpleOperationProvider(Add.class, "Add", true));
		avaliable.add(new SimpleOperationProvider(Sub.class, "Subtract", true));
		avaliable.add(new SimpleOperationProvider(Mul.class, "Mulitply", true));
		avaliable.add(new SimpleOperationProvider(Div.class, "Divide", false));
		avaliable.add(new SimpleOperationProvider(ProtectedDiv.class, "Protected Divide", "Returns 0 when divisor is 0.", true));
		avaliable.add(new SimpleOperationProvider(Min.class, "Min", false));
		avaliable.add(new SimpleOperationProvider(Max.class, "Max", false));

		// bitwise
		avaliable.add(new SimpleOperationProvider(And.class, "And", "Bitwise And", false));
		avaliable.add(new SimpleOperationProvider(Or.class, "Or", "Bitwise Or", false));
		avaliable.add(new SimpleOperationProvider(Xor.class, "Xor", "Bitwise Xor", false));
		avaliable.add(new SimpleOperationProvider(Not.class, "Not", "Bitwise Not", false));

		// additional literals
		avaliable.add(new RuntimeOperationProvider(Integer.class, false));

		// TODO: make better composite of creating multiple literals

	}

	@Override
	public List<IOperationProvider> getAvaliable() {
		return avaliable;
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
