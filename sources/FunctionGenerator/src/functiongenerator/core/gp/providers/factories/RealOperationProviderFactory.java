package functiongenerator.core.gp.providers.factories;

import java.util.ArrayList;
import java.util.List;

import functiongenerator.core.gp.functions.real.*;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;

import functiongenerator.core.gp.problem.RealRegressionProblem;
import functiongenerator.core.gp.providers.SimpleOperationProvider;

/**
 * Provides the possible operations for the {@linkplain RealRegressionProblem}
 * in the form of {@linkplain IOperationProvider}.
 * 
 * <p>
 * This class is the <i>extension point</i> for adding new types of operations
 * to the program.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class RealOperationProviderFactory implements IOperationProviderFactory {

	private final List<IOperationProvider> avaliable;

	public RealOperationProviderFactory() {
		avaliable = new ArrayList<IOperationProvider>();

		// NOTE: add here any types of providers
		// NOTE: add here next supported operations
		avaliable.add(new SimpleOperationProvider(Add.class, "Add", true));
		avaliable.add(new SimpleOperationProvider(Sub.class, "Subtract", true));
		avaliable.add(new SimpleOperationProvider(Mul.class, "Mulitply", true));
		avaliable.add(new SimpleOperationProvider(Div.class, "Divide", false));
		avaliable.add(new SimpleOperationProvider(ProtectedDiv.class, "Protected Divide", "Returns 0 when divisor is 0.", true));
		avaliable.add(new SimpleOperationProvider(Min.class, "Min", false));
		avaliable.add(new SimpleOperationProvider(Max.class, "Max", false));

		// real alike
		avaliable.add(new SimpleOperationProvider(Exp.class, "Exp", false));
		avaliable.add(new SimpleOperationProvider(Pow.class, "Pow", false));
		avaliable.add(new SimpleOperationProvider(Log.class, "Log", false));
		avaliable.add(new SimpleOperationProvider(ProtectedLog.class, "Protected Log", "Returns 0 when argument is less or equal 0.", false));
		avaliable.add(new SimpleOperationProvider(Sin.class, "Sin", false));
		avaliable.add(new SimpleOperationProvider(Cos.class, "Cos", false));

	}

	@Override
	public List<IOperationProvider> getAvaliable() {
		return avaliable;
	}

	// public static OperationsTableModel getReal() {
	// OperationsTableModel model = new OperationsTableModel();
	//
	// model.addRow(true, "Add", Add.class.getName(), "");
	// model.addRow(true, "Sub", Sub.class.getName(), "");
	// model.addRow(true, "Mul", Mul.class.getName(), "");
	// model.addRow(false, "Div", Div.class.getName(), "");
	// model.addRow(true, "ProtectedDiv", ProtectedDiv.class.getName(),
	// "Returns 0 when divisor is 0.");
	// model.addRow(false, "Exp", Exp.class.getName(), "");
	// model.addRow(false, "Pow", Pow.class.getName(), "");
	// model.addRow(false, "Log", Log.class.getName(), "");
	// model.addRow(false, "ProtectedLog", ProtectedLog.class.getName(),
	// "Returns 0 when argument is less or equal 0.");
	// model.addRow(false, "Min", Min.class.getName(), "");
	// model.addRow(false, "Max", Max.class.getName(), "");
	// model.addRow(false, "Sin", Sin.class.getName(), "");
	// model.addRow(false, "Cos", Cos.class.getName(), "");
	//
	// return model;
	// }
	//
}
