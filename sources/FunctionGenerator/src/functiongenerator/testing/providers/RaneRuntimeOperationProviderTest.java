package functiongenerator.testing.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ec.gp.GPNode;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.providers.RangeRuntimeOperationProvider;

public class RaneRuntimeOperationProviderTest {

	@Test
	public void three_different_classes_are_generated_and_loaded() throws Exception {
		RangeRuntimeOperationProvider provider = new RangeRuntimeOperationProvider(Double.class, true);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RangeRuntimeOperationProvider.ATTR_START, 0.9);
		params.put(RangeRuntimeOperationProvider.ATTR_STOP, 4.0);
		params.put(RangeRuntimeOperationProvider.ATTR_STEP, 1.1);

		provider.setParameters(params);

		List<Class<? extends GPNode>> classes = provider.getOperations();
		for (Class<? extends GPNode> cls : classes) {
			System.out.println(cls.getCanonicalName());
			GPNode node = cls.newInstance();
			DoubleData data = new DoubleData();
			node.eval(null, 0, data, null, null, null);
			
			System.out.println("Data after eval: ");
			System.out.println(data.Y);
		}
	}
}
