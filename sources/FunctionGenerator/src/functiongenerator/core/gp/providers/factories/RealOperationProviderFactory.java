package functiongenerator.core.gp.providers.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.internal.handlers.ReuseEditorTester;

import javassist.bytecode.analysis.Analyzer;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;
import functiongenerator.core.gp.problem.RealRegressionProblem;

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
