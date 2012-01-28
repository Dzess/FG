package functiongenerator.core.gp.providers;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import ec.gp.GPNode;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.functions.integer.Add;

/**
 * Describes the provider of the sample classes such as {@linkplain Add} etc.
 * Which are hardcoded in the structure of this project.
 * 
 * @author Piotr Jessa
 * 
 */
public class SimpleOperationProvider implements IOperationProvider {

	private final Class<? extends GPNode> cls;
	private final String name;
	private final String comennt;
	private final TreeMap<String, Class<?>> map;
	private final TreeMap<String, String> defaultParameters;

	private final boolean isEnabled;

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, boolean isEnabled) {
		this(cls, name, "", isEnabled);
	}

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, String comment, boolean isEnabled) {
		this.isEnabled = isEnabled;
		this.comennt = comment;
		this.name = name;
		this.cls = cls;

		this.map = new TreeMap<String, Class<?>>();
		this.defaultParameters = new TreeMap<String, String>();
	}

	@Override
	public Class<? extends GPNode> getOperation() {
		return cls;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getComment() {
		return comennt;
	}

	@Override
	public SortedMap<String, Class<?>> getParameters() {
		return map;
	}

	@Override
	public void setParameters(Map<String, Object> params) {
		// do nothing no parameters for this class
	}

	@Override
	public boolean isEnableByDefault() {
		return isEnabled;
	}

	@Override
	public SortedMap<String, String> getParametersDefault() {
		return defaultParameters;
	}

}
