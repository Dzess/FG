package functiongenerator.core.gp.providers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ec.gp.GPNode;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.functions.NullaryOperation;
import functiongenerator.core.gp.rt.DoubleValueRuntimeFunctionGenerator;
import functiongenerator.core.gp.rt.IntegerValueRuntimeFunctionGenerator;
import functiongenerator.core.gp.rt.RuntimeFunctionGenerator;

/**
 * Provider of classes which are generated at runtime mainly by the some
 * subclass of the {@linkplain RuntimeFunctionGenerator}. Though the class
 * created is {@linkplain NullaryOperation}.
 * 
 * <p>
 * This class creates only one class for operation literal. For creating
 * multiple values at once use {@linkplain RangeRuntimeOperationProvider}.
 * </p>
 * 
 * <h5>
 * Parameters:</h5>
 * <ul>
 * <li><i>Value</i> : the generated value of the literal</li>
 * </ul>
 * 
 * @author Piotr Jessa
 * 
 */
public class RuntimeOperationProvider implements IOperationProvider {

	static public final String ATTR_VALUE = "Value";

	private final SortedMap<String, Class<?>> parameters;
	private final SortedMap<String, Object> defaultParameters;

	private Map<String, Object> values;

	private RuntimeFunctionGenerator fg;

	private boolean isEnableByDefault;

	private final Class<? extends Number> type;
	
	/**
	 * 
	 * @param typeOfData
	 *            : {@code Integer.class} or {@code Double.class}
	 */
	public RuntimeOperationProvider(Class<? extends Number> typeOfData, boolean isEnableByDefault) {

		this.type = typeOfData;
		this.isEnableByDefault = isEnableByDefault;
		this.parameters = new TreeMap<String, Class<?>>();
		this.parameters.put(ATTR_VALUE, typeOfData);

		this.defaultParameters = new TreeMap<String, Object>();

		if (typeOfData == Integer.class) {
			this.defaultParameters.put(ATTR_VALUE, 1);
		} else if (typeOfData == Double.class) {
			this.defaultParameters.put(ATTR_VALUE, 1.0);
		} else {
			throw new IllegalArgumentException("The type of data must be Integer or Double");
		}

		setParameters(defaultParameters);
	}

	public Class<? extends Number> getOperationType(){
		return this.type;
	}
	
	@Override
	public List<Class<? extends GPNode>> getOperations() throws ClassNotFoundException, IllegalArgumentException {

		// check if all parameters are set
		if (this.fg == null)
			throw new IllegalArgumentException("The the value parameter is missing");

		List<Class<? extends GPNode>> list = new LinkedList<Class<? extends GPNode>>();
		list.add(fg.generateClassAndLoad());
		return list;
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
	public SortedMap<String, Class<?>> getParametersTypes() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, Object> params) {
		values = params;

		if (!values.containsKey(ATTR_VALUE)) {
			throw new IllegalArgumentException("The passed params map has to have " + ATTR_VALUE);
		}

		if (type.equals(Integer.class)) {
			Integer numberValue = (Integer) values.get(ATTR_VALUE);
			this.fg = new IntegerValueRuntimeFunctionGenerator(numberValue);
		} else if (type.equals(Double.class)) {
			Double numberValu = (Double) values.get(ATTR_VALUE);
			this.fg = new DoubleValueRuntimeFunctionGenerator(numberValu);
		} else {
			throw new IllegalArgumentException("The class cannot be found");
		}
	}

	@Override
	public boolean isEnableByDefault() {
		return isEnableByDefault;
	}

	@Override
	public SortedMap<String, Object> getParametersDefault() {
		return defaultParameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isEnableByDefault ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RuntimeOperationProvider)) {
			return false;
		}
		RuntimeOperationProvider other = (RuntimeOperationProvider) obj;
		if (isEnableByDefault != other.isEnableByDefault) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		if (values == null) {
			if (other.values != null) {
				return false;
			}
		} else if (!values.equals(other.values)) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getParameters() {
		return values;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		isEnableByDefault = isEnabled;
	}
}
