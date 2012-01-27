package functiongenerator.core.gp.providers;

import java.util.HashMap;
import java.util.Map;

import ec.gp.GPNode;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.rt.RuntimeFunctionGenerator;
import functiongenerator.core.gp.rt.ValueRuntimeFunctionGenerator;

/**
 * Describes the provider of the provider of classes which are generated at
 * runtime mainly by the some subclass of the
 * {@linkplain RuntimeFunctionGenerator}.
 * 
 * <h5>
 * Parameters</h5>
 * <ul>
 * <li><i>Value</i> : the generated value of the literal</li>
 * </ul>
 * 
 * @author Piotr Jessa
 * 
 */
public class RuntimeOperationProvider implements IOperationProvider {

	static public final String ATTR_VALUE = "Value";

	private final Map<String, Class<?>> parameters;
	private Map<String, Object> values;
	private RuntimeFunctionGenerator funtionGenerator;

	private final boolean isEnableByDefault;

	public RuntimeOperationProvider(Class<?> typeOfData, boolean isEnableByDefault) {

		this.isEnableByDefault = isEnableByDefault;
		this.parameters = new HashMap<String, Class<?>>();
		this.parameters.put(ATTR_VALUE, typeOfData);

		this.values = new HashMap<String, Object>();
	}

	@Override
	public Class<? extends GPNode> getOperation() {
		return this.funtionGenerator.generateClassAndLoad();
	}

	@Override
	public String getName() {
		return "Literal Value";
	}

	@Override
	public String getComment() {
		return "Places the literal value as the Node";
	}

	@Override
	public Map<String, Class<?>> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, Object> params) {
		values = params;

		if (!values.containsKey(ATTR_VALUE)) {
			throw new IllegalArgumentException("The passed params map has to have " + ATTR_VALUE);
		}

		// FIXME: extend this code to support both real data and integers
		Integer numberValue = (Integer) values.get(ATTR_VALUE);
		this.funtionGenerator = new ValueRuntimeFunctionGenerator(numberValue);

	}

	@Override
	public boolean isEnableByDefault() {
		return isEnableByDefault;
	}

}
