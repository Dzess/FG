package pl.alife.fg.testing.rt;

import junit.framework.Assert;

import org.junit.Test;

import pl.alife.fg.core.gp.rt.IntegerValueRuntimeFunctionGenerator;


/**
 * Test if the class was loaded to the JVM class pool. Unit level.
 * 
 * @author Piotr Jessa
 * 
 */
public class ValueRuntieFunctionGeneratorTest {

    @Test
    public void loads_throws_no_exceptions() {
        int value = 5;
        String expected = "pl.alife.fg.core.gp.functions.real.Value_";
        IntegerValueRuntimeFunctionGenerator rtGen = new IntegerValueRuntimeFunctionGenerator(value);

        String name = rtGen.generateClassAndLoad().getCanonicalName();
        Assert.assertTrue(name.contains(expected));
    }
}
