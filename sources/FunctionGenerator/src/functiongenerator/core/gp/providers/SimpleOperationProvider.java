package functiongenerator.core.gp.providers;

import java.util.HashMap;
import java.util.Map;

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
	private final HashMap<String, Class<?>> map;

	private final boolean isEnabled;

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, boolean isEnabled) {
		this(cls, name, "", isEnabled);
	}

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, String comment, boolean isEnabled) {
		this.isEnabled = isEnabled;
		this.comennt = comment;
		this.name = name;
		this.cls = cls;

		this.map = new HashMap<String, Class<?>>();
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
	public Map<String, Class<?>> getParameters() {
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

}
