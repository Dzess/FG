package functiongenerator.testing.providers;

import ec.gp.GPNode;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.data.IntegerData;

public class RuntimeOperationProviderTestBase {

	public RuntimeOperationProviderTestBase() {
		super();
	}

	protected Double evaluateDouble(Class<? extends GPNode> cls) throws Exception {
		GPNode node = cls.newInstance();
		DoubleData data = new DoubleData();
		node.eval(null, 0, data, null, null, null);
	
		return data.Y;
	}

	protected Integer evaluateInteger(Class<? extends GPNode> cls) throws Exception {
		GPNode node = cls.newInstance();
		IntegerData data = new IntegerData();
		node.eval(null, 0, data, null, null, null);
	
		return data.Y;
	}

}