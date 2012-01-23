package functiongenerator.testing.rtfunctions;

import junit.framework.Assert;

import org.junit.Test;

import functiongenerator.core.gp.rtfuntions.ValueRuntimeFunctionGenerator;

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
		String expected = "functiongenerator.core.gp.functions.real.Value_0";
		ValueRuntimeFunctionGenerator rtGen = new ValueRuntimeFunctionGenerator(value);

		String name = rtGen.generateClassAndLoad();
		Assert.assertEquals(expected, name);
	}
}
