package pl.alife.fg.core.gp.rt;

import java.util.LinkedList;
import java.util.List;

import pl.alife.fg.core.gp.data.IntegerData;
import pl.alife.fg.core.gp.functions.NullaryOperation;

import ec.gp.GPFunctionSet;
import ec.gp.GPNode;

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
 * <p>
 * Typed for the {@linkplain Integer}.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class IntegerValueRuntimeFunctionGenerator extends RuntimeFunctionGenerator {

    static private final String TEMPLATE = "public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {"
            + " IntegerData rd = ((IntegerData) (input)); " + " rd.Y = %value; }";

    private final List<Class<?>> usedClasses;

    private final int value;

    /**
     * Initializes the instance of
     * {@linkplain IntegerValueRuntimeFunctionGenerator} class. The generated
     * class will act as literal {@linkplain GPNode}.
     * 
     * @param value
     *            : the value that will be returned with this literal
     */
    public IntegerValueRuntimeFunctionGenerator(int value) {
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
        String current = new String(TEMPLATE);
        return current.replace("%value", Integer.toString(value));
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
