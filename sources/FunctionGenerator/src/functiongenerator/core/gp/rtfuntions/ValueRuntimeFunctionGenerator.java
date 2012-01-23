package functiongenerator.core.gp.rtfuntions;

import java.util.LinkedList;
import java.util.List;

import ec.gp.GPFunctionSet;
import ec.gp.GPNode;
import functiongenerator.core.gp.data.IntegerData;
import functiongenerator.core.gp.functions.NullaryOperation;

/**
 * Generates the class (binary data in JVM meaning) with the structure allowable
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

	static private final String TEMPLATE = "public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {"
			+ " IntegerData rd = ((IntegerData) (input)); " + " rd.Y = %value; }";

	private final List<Class<?>> usedClasses;

	private final int value;

	public ValueRuntimeFunctionGenerator(int value) {
		this.value = value;
		this.usedClasses = new LinkedList<Class<?>>();
		this.usedClasses.add(IntegerData.class);
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
	protected String getToStringReturnedValue() {

		return Integer.toString(value);
	}

	@Override
	protected List<Class<?>> getUsedClasses() {
		return usedClasses;
	}
}
