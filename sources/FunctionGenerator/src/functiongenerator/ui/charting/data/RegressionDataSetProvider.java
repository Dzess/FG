package functiongenerator.ui.charting.data;

import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.gp.GPTree;
import functiongenerator.core.Engine;
import functiongenerator.core.gp.data.DoubleData;
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

			problem.X = point;
			double Y = (Double) point[point.length - 1];
			DoubleData output = new DoubleData();

			// run the evaluation for single X
			root.eval(null, 0, output, null, individual, problem);

			double YEstimated = output.Y;

			// only
			series.add(point[0], Y);
			esitmated.add(point[0], YEstimated);
		}

		XYSeriesCollection collection = new XYSeriesCollection();
		collection.addSeries(series);
		collection.addSeries(esitmated);

		return collection;
	}

}
