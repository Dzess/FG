package functiongenerator.core.gp.providers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
	private final List<Class<? extends GPNode>> list;
	private String name;
	private String comennt;

	private final TreeMap<String, Class<?>> map;
	private final TreeMap<String, Object> defaultParameters;

	private boolean isEnabled;

	public SimpleOperationProvider() {
		this(null, null, false);
	}

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, boolean isEnabled) {
		this(cls, name, "", isEnabled);
	}

	public SimpleOperationProvider(Class<? extends GPNode> cls, String name, String comment, boolean isEnabled) {
		this.isEnabled = isEnabled;
		this.comennt = comment;
		this.name = name;
		this.cls = cls;
		this.list = new LinkedList<Class<? extends GPNode>>();
		this.list.add(cls);

		this.map = new TreeMap<String, Class<?>>();
		this.defaultParameters = new TreeMap<String, Object>();
	}

	public Class<? extends GPNode> getNodeClass() {
		return this.cls;
	}

	@Override
	public List<Class<? extends GPNode>> getOperations() {
		return list;
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
	public SortedMap<String, Class<?>> getParametersTypes() {
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
	public SortedMap<String, Object> getParametersDefault() {
		return defaultParameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cls == null) ? 0 : cls.hashCode());
		result = prime * result + ((comennt == null) ? 0 : comennt.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof SimpleOperationProvider)) {
			return false;
		}
		SimpleOperationProvider other = (SimpleOperationProvider) obj;
		if (cls == null) {
			if (other.cls != null) {
				return false;
			}
		} else if (!cls.equals(other.cls)) {
			return false;
		}
		if (comennt == null) {
			if (other.comennt != null) {
				return false;
			}
		} else if (!comennt.equals(other.comennt)) {
			return false;
		}
		if (isEnabled != other.isEnabled) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public Map<String, Object> getParameters() {
		return new HashMap<String, Object>();
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setComment(String newComment) {
		comennt = newComment;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
