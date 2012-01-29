package functiongenerator.testing.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ec.gp.GPNode;
import functiongenerator.core.gp.providers.RangeRuntimeOperationProvider;

public class RangeRuntimeOperationProviderTest extends RuntimeOperationProviderTestBase {

	@Test
	public void three_different__double_classes_are_generated_and_loaded() throws Exception {
		RangeRuntimeOperationProvider provider = new RangeRuntimeOperationProvider(Double.class, true);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RangeRuntimeOperationProvider.ATTR_START, 0.9);
		params.put(RangeRuntimeOperationProvider.ATTR_STOP, 4.0);
		params.put(RangeRuntimeOperationProvider.ATTR_STEP, 1.1);

		provider.setParameters(params);

		List<Class<? extends GPNode>> classes = provider.getOperations();

		Double r1 = evaluateDouble(classes.get(0));
		Assert.assertEquals(0.9, r1);

		Double r2 = evaluateDouble(classes.get(1));
		Assert.assertEquals(2.0, r2);

		Double r3 = evaluateDouble(classes.get(2));
		Assert.assertEquals(3.1, r3);

	}

	@Test
	public void three_different_int_classes_are_generated_and_loaded() throws Exception {
		RangeRuntimeOperationProvider provider = new RangeRuntimeOperationProvider(Integer.class, true);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(RangeRuntimeOperationProvider.ATTR_START, 0);
		params.put(RangeRuntimeOperationProvider.ATTR_STOP, 3);
		params.put(RangeRuntimeOperationProvider.ATTR_STEP, 1);

		provider.setParameters(params);

		List<Class<? extends GPNode>> classes = provider.getOperations();

		int r1 = evaluateInteger(classes.get(0));
		Assert.assertEquals(0, r1);

		int r2 = evaluateInteger(classes.get(1));
		Assert.assertEquals(1, r2);

		int r3 = evaluateInteger(classes.get(2));
		Assert.assertEquals(2, r3);
	}
}
