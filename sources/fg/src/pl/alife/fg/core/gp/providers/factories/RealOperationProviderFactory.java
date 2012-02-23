package pl.alife.fg.core.gp.providers.factories;

import java.util.ArrayList;
import java.util.List;

import pl.alife.fg.core.gp.IOperationProvider;
import pl.alife.fg.core.gp.IOperationProviderFactory;
import pl.alife.fg.core.gp.functions.real.Add;
import pl.alife.fg.core.gp.functions.real.Cos;
import pl.alife.fg.core.gp.functions.real.Div;
import pl.alife.fg.core.gp.functions.real.Exp;
import pl.alife.fg.core.gp.functions.real.Log;
import pl.alife.fg.core.gp.functions.real.Max;
import pl.alife.fg.core.gp.functions.real.Min;
import pl.alife.fg.core.gp.functions.real.Mul;
import pl.alife.fg.core.gp.functions.real.Pow;
import pl.alife.fg.core.gp.functions.real.ProtectedDiv;
import pl.alife.fg.core.gp.functions.real.ProtectedLog;
import pl.alife.fg.core.gp.functions.real.Sin;
import pl.alife.fg.core.gp.functions.real.Sub;
import pl.alife.fg.core.gp.problem.RealRegressionProblem;
import pl.alife.fg.core.gp.providers.RangeRuntimeOperationProvider;
import pl.alife.fg.core.gp.providers.RuntimeOperationProvider;
import pl.alife.fg.core.gp.providers.SimpleOperationProvider;


/**
 * Provides the possible operations for the {@linkplain RealRegressionProblem}
 * in the form of {@linkplain IOperationProvider}.
 * 
 * <p>
 * This class is the <i>extension point</i> for adding new types of operations
 * to the program.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class RealOperationProviderFactory implements IOperationProviderFactory {

    private final List<IOperationProvider> avaliable;

    public RealOperationProviderFactory() {
        avaliable = new ArrayList<IOperationProvider>();

        // NOTE: add here any types of providers
        // NOTE: add here next supported operations
        avaliable.add(new SimpleOperationProvider(Add.class, "Add", true));
        avaliable.add(new SimpleOperationProvider(Sub.class, "Subtract", true));
        avaliable.add(new SimpleOperationProvider(Mul.class, "Mulitply", true));
        avaliable.add(new SimpleOperationProvider(Div.class, "Divide", false));
        avaliable.add(new SimpleOperationProvider(ProtectedDiv.class, "Protected Divide",
                "Returns 0 when divisor is 0.", true));
        avaliable.add(new SimpleOperationProvider(Min.class, "Min", false));
        avaliable.add(new SimpleOperationProvider(Max.class, "Max", false));

        // real alike
        avaliable.add(new SimpleOperationProvider(Exp.class, "Exp", false));
        avaliable.add(new SimpleOperationProvider(Pow.class, "Pow", false));
        avaliable.add(new SimpleOperationProvider(Log.class, "Log", false));
        avaliable.add(new SimpleOperationProvider(ProtectedLog.class, "Protected Log",
                "Returns 0 when argument is less or equal 0.", false));
        avaliable.add(new SimpleOperationProvider(Sin.class, "Sin", false));
        avaliable.add(new SimpleOperationProvider(Cos.class, "Cos", false));

        // additional literals
        avaliable.add(new RuntimeOperationProvider(Double.class, false));
        avaliable.add(new RangeRuntimeOperationProvider(Double.class, false));

    }

    @Override
    public List<IOperationProvider> getAvaliable() {
        return avaliable;
    }

}
