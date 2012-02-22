package pl.alife.fg.testing.providers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ec.gp.GPNode;
import functiongenerator.core.gp.providers.RuntimeOperationProvider;

public class SingleRuntimeOperationProviderTest extends RuntimeOperationProviderTestBase {

    @Test
    public void creating_integer_class() throws Exception {
        RuntimeOperationProvider provider = new RuntimeOperationProvider(Integer.class, true);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(RuntimeOperationProvider.ATTR_VALUE, 5);

        provider.setParameters(params);

        List<Class<? extends GPNode>> classes = provider.getOperations();
        int r1 = evaluateInteger(classes.get(0));
        Assert.assertEquals(5, r1);

    }

    @Test
    public void creating_double_class() throws Exception {
        RuntimeOperationProvider provider = new RuntimeOperationProvider(Double.class, true);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(RuntimeOperationProvider.ATTR_VALUE, 2.0);

        provider.setParameters(params);

        List<Class<? extends GPNode>> classes = provider.getOperations();
        Double r1 = evaluateDouble(classes.get(0));
        Assert.assertEquals(2.0, r1);
    }

}
