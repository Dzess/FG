package functiongenerator.core.gp.providers;

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
public class SimpleClassOperationProvider implements IOperationProvider {

	@Override
	public Class<? extends GPNode> getOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Class<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParameters(Map<String, Object> params) {
		// TODO Auto-generated method stub

	}

}
