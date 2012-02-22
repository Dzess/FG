package functiongenerator.ui.charting.regression.data;

import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import functiongenerator.core.Engine;
import functiongenerator.core.gp.data.DoubleData;
import functiongenerator.core.gp.data.IntegerData;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;

/**
 * Calculates the function value based on the symbolic regression provided by
 * the {@linkplain Engine} object.
 * 
 * <p>
 * This code might be boilerplate because of the ChartableStatistics which
 * should be present in ECJ.
 * </p>
 * <p>
 * Drawing on the only one X is done. When the number of Xes is equal to 3 or
 * more than no charting is available.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
public class RegressionDataSetProvider implements IDataSetProvider {

    private final AbstractRegressionProblem problem;

    public RegressionDataSetProvider(AbstractRegressionProblem problem) {
        this.problem = (AbstractRegressionProblem) problem.clone();
    }

    @Override
    public AbstractDataset getDataSet(GPIndividual individual) {

        GPTree tree = individual.trees[0];
        GPNode root = tree.child;

        XYSeries series = new XYSeries("Real");
        XYSeries esitmated = new XYSeries("Estimated");

        for (Number[] point : problem.getPoints()) {

            // perform checking that we are operating on 2d chart
            if (point.length > 2) {
                throw new IllegalStateException("Operation not supported");
            }

            problem.X = point;

            if (point[0].getClass() == Double.class) {

                Double Y = (Double) point[point.length - 1];

                // run the evaluation for single X
                DoubleData output = new DoubleData();
                root.eval(null, 0, output, null, individual, problem);
                Double YEstimated = output.Y;

                series.add(point[0], Y);
                esitmated.add(point[0], YEstimated);
            } else {

                Integer Y = (Integer) point[point.length - 1];

                IntegerData output = new IntegerData();
                root.eval(null, 0, output, null, individual, problem);
                Integer YEstimated = output.Y;

                series.add(point[0], Y);
                esitmated.add(point[0], YEstimated);
            }
        }

        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series);
        collection.addSeries(esitmated);

        return collection;
    }

}
