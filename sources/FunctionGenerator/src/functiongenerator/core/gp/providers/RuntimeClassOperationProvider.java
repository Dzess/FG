package functiongenerator.core.gp.providers;

import java.util.Map;

import ec.gp.GPNode;
import functiongenerator.core.gp.IOperationProvider;
import functiongenerator.core.gp.rt.RuntimeFunctionGenerator;

/**
 * Describes the provider of the provider of classes which are generated at
 * runtime mainly by the some subclass of the
 * {@linkplain RuntimeFunctionGenerator}.
 * 
 * @author Piotr Jessa
 * 
 */
public class RuntimeClassOperationProvider implements IOperationProvider {

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
