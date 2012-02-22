package pl.alife.fg.testing.providers.equality;

import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;

import functiongenerator.core.gp.providers.RuntimeOperationProvider;

/**
 * The operations should be matched by
 * 
 * <ul>
 * <li>Type of data</li>
 * <li>Parameters values</li>
 * <li>Default enablebility</li>
 * </ul>
 * 
 * @author Piotr Jessa
 * 
 */
public class RuntimeOperationProviderEqualityTest {

    @Test
    public void two_operations_are_equal() {
        RuntimeOperationProvider p1 = new RuntimeOperationProvider(Integer.class, true);
        RuntimeOperationProvider p2 = new RuntimeOperationProvider(Integer.class, true);
        Assert.assertEquals(p1, p2);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RuntimeOperationProvider.ATTR_VALUE, 3);

        RuntimeOperationProvider p3 = new RuntimeOperationProvider(Integer.class, true);
        RuntimeOperationProvider p4 = new RuntimeOperationProvider(Integer.class, true);
        p3.setParameters(params);
        p4.setParameters(params);

        Assert.assertEquals(p3, p4);

        // two different maps
        RuntimeOperationProvider p5 = new RuntimeOperationProvider(Integer.class, true);
        RuntimeOperationProvider p6 = new RuntimeOperationProvider(Integer.class, true);

        HashMap<String, Object> params2 = new HashMap<String, Object>();
        params2.put(RuntimeOperationProvider.ATTR_VALUE, 3);

        Assert.assertEquals(p5, p6);
    }

    @Test
    public void two_operations_are_not_equal() {
        RuntimeOperationProvider p1 = new RuntimeOperationProvider(Double.class, true);
        RuntimeOperationProvider p2 = new RuntimeOperationProvider(Integer.class, true);
        Assert.assertFalse(p1.equals(p2));

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RuntimeOperationProvider.ATTR_VALUE, 3);

        RuntimeOperationProvider p3 = new RuntimeOperationProvider(Integer.class, true);
        RuntimeOperationProvider p4 = new RuntimeOperationProvider(Integer.class, true);
        p3.setParameters(params);

        Assert.assertFalse(p3.equals(p4));
    }
}
