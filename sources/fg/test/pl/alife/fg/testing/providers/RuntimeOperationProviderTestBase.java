package pl.alife.fg.testing.providers;

import pl.alife.fg.core.gp.data.DoubleData;
import pl.alife.fg.core.gp.data.IntegerData;
import ec.gp.GPNode;

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