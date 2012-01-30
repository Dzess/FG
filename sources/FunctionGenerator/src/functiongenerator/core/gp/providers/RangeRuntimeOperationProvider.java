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
 * Provider of classes (multiple) generated using
 * {@linkplain RuntimeFunctionGenerator}. This class is capable of creating
 * range of literal for {@linkplain NullaryOperation}.
 * 
 * 
 * <h5>
 * Parameters:</h5>
 * <ul>
 * <li><i>Starting value</i> : being of the range of literals</li>
 * <li><i>Stopping value</i> : end of the range of literals</li>
 * <li><i>Step</i> : the step for creating such ranges</li>
 * 
 * </ul>
 * 
 * @author Piotr Jessa
 * 
 */
public class RangeRuntimeOperationProvider implements IOperationProvider {

	static public final String ATTR_START = "Start Value";
	static public final String ATTR_STOP = "Stop Value";
	static public final String ATTR_STEP = "Step";

	private final SortedMap<String, Class<?>> parameters;
	private final SortedMap<String, Object> defaultParameters;
	private final boolean isEnableByDefault;
	private final Class<? extends Number> type;

	private List<RuntimeFunctionGenerator> fgs;
	private Map<String, Object> values;

	/**
	 * 
	 * @param typeOfData
	 *            : {@code Integer.class} or {@code Double.class}
	 */
	public RangeRuntimeOperationProvider(Class<? extends Number> typeOfData, boolean isEnabledByDefault) {

		this.type = typeOfData;

		this.parameters = new TreeMap<String, Class<?>>();
		this.parameters.put(ATTR_START, typeOfData);
		this.parameters.put(ATTR_STOP, typeOfData);
		this.parameters.put(ATTR_STEP, typeOfData);

		this.isEnableByDefault = isEnabledByDefault;

		// default values
		this.defaultParameters = new TreeMap<String, Object>();
		if (type == Integer.class) {
			defaultParameters.put(ATTR_START, -10);
			defaultParameters.put(ATTR_STOP, 10);
			defaultParameters.put(ATTR_STEP, 1);
		} else if (type == Double.class) {
			defaultParameters.put(ATTR_START, -10.0);
			defaultParameters.put(ATTR_STOP, 10.0);
			defaultParameters.put(ATTR_STEP, 1.0);
		}
		else{
			throw new IllegalArgumentException("The type of data must be Integer or Double");
		}

		setParameters(defaultParameters);
	}

	@Override
	public List<Class<? extends GPNode>> getOperations() throws ClassNotFoundException, IllegalArgumentException {

		List<Class<? extends GPNode>> result = new LinkedList<Class<? extends GPNode>>();
		for (RuntimeFunctionGenerator fg : fgs) {

			result.add(fg.generateClassAndLoad());
		}

		return result;
	}

	@Override
	public String getName() {
		return "Range Literals";
	}

	@Override
	public String getComment() {
		return "Generates the range of literal nodes";
	}

	@Override
	public boolean isEnableByDefault() {
		return isEnableByDefault;
	}

	@Override
	public SortedMap<String, Class<?>> getParametersTypes() {
		return parameters;
	}

	@Override
	public void setParameters(Map<String, Object> params) {

		this.values = params;
		this.fgs = new LinkedList<RuntimeFunctionGenerator>();

		// existence constraints
		if ((!params.containsKey(ATTR_STOP)) || (!params.containsKey(ATTR_START)) || (!params.containsKey(ATTR_STEP))) {
			throw new IllegalArgumentException("The one of three parameters missing");
		}

		if (type.equals(Integer.class)) {

			Integer startValue = (Integer) params.get(ATTR_START);
			Integer stopValue = (Integer) params.get(ATTR_STOP);
			Integer stepValue = (Integer) params.get(ATTR_STEP);

			if (stepValue < 1) {
				throw new IllegalArgumentException("The step cannot be less than one");
			}

			Integer currentValue = startValue;
			while (stopValue > currentValue) {

				RuntimeFunctionGenerator fg = new IntegerValueRuntimeFunctionGenerator(currentValue);
				fgs.add(fg);
				currentValue += stepValue;
			}

		} else if (type.equals(Double.class)) {
			Double startValue = (Double) params.get(ATTR_START);
			Double stopValue = (Double) params.get(ATTR_STOP);
			Double stepValue = (Double) params.get(ATTR_STEP);

			if (stepValue <= 0) {
				throw new IllegalArgumentException("The step cannot be less than zero");
			}

			Double currentValue = startValue;
			while (stopValue > currentValue) {

				RuntimeFunctionGenerator fg = new DoubleValueRuntimeFunctionGenerator(currentValue);
				fgs.add(fg);
				currentValue += stepValue;
			}
		} else {
			throw new IllegalArgumentException("No type found !");
		}
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
		if (!(obj instanceof RangeRuntimeOperationProvider)) {
			return false;
		}
		RangeRuntimeOperationProvider other = (RangeRuntimeOperationProvider) obj;
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

}
