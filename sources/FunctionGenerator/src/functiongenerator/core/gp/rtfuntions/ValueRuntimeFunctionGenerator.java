package functiongenerator.core.gp.rtfuntions;

import ec.gp.GPFunctionSet;
import ec.gp.GPNode;
import functiongenerator.core.gp.functions.NullaryOperation;

/**
 * Generates the class (binary data in JVM meaning) with the strutter allowable
 * for ECJ to be used within {@linkplain GPFunctionSet} class.
 * 
 * <p>
 * Generated function is the {@linkplain NullaryOperation} with the value
 * specified by the production parameters. It behaves in the GP problem as
 * simple {@linkplain GPNode} as someone would write it in code. However this
 * <i>code</i> is generated dynamically in the JVM Heap.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class ValueRuntimeFunctionGenerator extends RuntimeFunctionGenerator {

	static private final String TEMPLATE = "";
	private final int value;

	public ValueRuntimeFunctionGenerator(int value) {
		this.value = value;

	}

	@Override
	protected Class<?> getSuperClassForOperation() {
		return NullaryOperation.class;
	}

	@Override
	protected String getEvalSourceCode() {
		return TEMPLATE.replace("%value", Integer.toString(value));
	}

	@Override
	protected String getClassName() {
		return "Value";
	}

	@Override
	protected String getToStringSourceCode() {
		return Integer.toString(value);
	}
}
