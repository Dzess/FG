package pl.alife.fg.testing.providers.equality;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import pl.alife.fg.core.gp.providers.RangeRuntimeOperationProvider;


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
public class RangeRuntimeOperationProviderEqualityTest {

    @Test
    public void two_operations_are_equal() {
        RangeRuntimeOperationProvider p1 = new RangeRuntimeOperationProvider(Integer.class, true);
        RangeRuntimeOperationProvider p2 = new RangeRuntimeOperationProvider(Integer.class, true);
        Assert.assertEquals(p1, p2);

        Map<String, Object> params = p1.getParametersDefault();
        params.put(RangeRuntimeOperationProvider.ATTR_STOP, 3);

        RangeRuntimeOperationProvider p3 = new RangeRuntimeOperationProvider(Integer.class, true);
        RangeRuntimeOperationProvider p4 = new RangeRuntimeOperationProvider(Integer.class, true);
        p3.setParameters(params);
        p4.setParameters(params);

        Assert.assertEquals(p3, p4);

        // two different maps
        RangeRuntimeOperationProvider p5 = new RangeRuntimeOperationProvider(Integer.class, true);
        RangeRuntimeOperationProvider p6 = new RangeRuntimeOperationProvider(Integer.class, true);

        Map<String, Object> params2 = p5.getParametersDefault();
        params2.put(RangeRuntimeOperationProvider.ATTR_STOP, 3);

        p5.setParameters(params);
        p6.setParameters(params2);

        Assert.assertEquals(p5, p6);
    }

    @Test
    public void two_operation_are_not_equal() {
        RangeRuntimeOperationProvider p1 = new RangeRuntimeOperationProvider(Double.class, true);
        RangeRuntimeOperationProvider p2 = new RangeRuntimeOperationProvider(Integer.class, true);
        Assert.assertFalse(p1.equals(p2));

        Map<String, Object> params = p2.getParametersDefault();
        params.put(RangeRuntimeOperationProvider.ATTR_STEP, 3);

        RangeRuntimeOperationProvider p3 = new RangeRuntimeOperationProvider(Integer.class, true);
        RangeRuntimeOperationProvider p4 = new RangeRuntimeOperationProvider(Integer.class, true);
        p3.setParameters(params);

        Assert.assertFalse(p3.equals(p4));
    }
}
