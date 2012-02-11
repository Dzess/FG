package functiongenerator.core.gp.providers.factories;

import java.util.ArrayList;
import java.util.List;

import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.IOperationProviderFactory;
import functiongenerator.core.gp.functions.integer.Add;
import functiongenerator.core.gp.functions.integer.And;
import functiongenerator.core.gp.functions.integer.Div;
import functiongenerator.core.gp.functions.integer.Max;
import functiongenerator.core.gp.functions.integer.Min;
import functiongenerator.core.gp.functions.integer.Mul;
import functiongenerator.core.gp.functions.integer.Not;
import functiongenerator.core.gp.functions.integer.Or;
import functiongenerator.core.gp.functions.integer.ProtectedDiv;
import functiongenerator.core.gp.functions.integer.Sub;
import functiongenerator.core.gp.functions.integer.Xor;
import functiongenerator.core.gp.problem.IntegerRegressionProblem;
import functiongenerator.core.gp.providers.RangeRuntimeOperationProvider;
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
		avaliable.add(new RangeRuntimeOperationProvider(Integer.class, false));


	}

	@Override
	public List<IOperationProvider> getAvaliable() {
		return avaliable;
	}
}
