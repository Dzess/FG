package pl.alife.fg.testing.providers.equality;

import junit.framework.Assert;

import org.junit.Test;

import ec.gp.GPNode;
import functiongenerator.core.gp.functions.integer.Add;
import functiongenerator.core.gp.functions.integer.Mul;
import functiongenerator.core.gp.functions.integer.Sub;
import functiongenerator.core.gp.providers.SimpleOperationProvider;

/**
 * The operations must provide means for equality comparison.
 * 
 * @author Piotr Jessa
 * 
 */
public class SimpleOperationProviderEqualityTest {

    @Test
    public void two_operations_are_equal() {
        SimpleOperationProvider s1 = new SimpleOperationProvider(Add.class, "Foo", true);
        SimpleOperationProvider s2 = new SimpleOperationProvider(Add.class, "Foo", true);
        Assert.assertEquals(s1, s2);

        SimpleOperationProvider s0 = new SimpleOperationProvider(Add.class, "Foo", "", true);
        Assert.assertEquals(s1, s0);

        String name = "Foo2";
        Class<? extends GPNode> cls = Mul.class;
        String comment = "Comment1";
        SimpleOperationProvider s3 = new SimpleOperationProvider(cls, name, comment, false);
        SimpleOperationProvider s4 = new SimpleOperationProvider(cls, name, comment, false);

        Assert.assertEquals(s3, s4);

    }

    @Test
    public void two_operations_are_not_euqal() {
        SimpleOperationProvider s1 = new SimpleOperationProvider(Add.class, "Foo", true);
        SimpleOperationProvider s2 = new SimpleOperationProvider(Add.class, "Foo", false);
        Assert.assertFalse(s1.equals(s2));

        SimpleOperationProvider s3 = new SimpleOperationProvider(Add.class, "Foo", true);
        SimpleOperationProvider s4 = new SimpleOperationProvider(Add.class, "Foo1", true);
        Assert.assertFalse(s3.equals(s4));

        SimpleOperationProvider s5 = new SimpleOperationProvider(Sub.class, "Foo", true);
        SimpleOperationProvider s6 = new SimpleOperationProvider(Add.class, "Foo", true);
        Assert.assertFalse(s5.equals(s6));

        SimpleOperationProvider s7 = new SimpleOperationProvider(Add.class, "Foo", "comment 1", true);
        SimpleOperationProvider s8 = new SimpleOperationProvider(Add.class, "Foo", "comment 2", true);
        Assert.assertFalse(s7.equals(s8));
    }
}
