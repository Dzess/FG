package functiongenerator.ui.charting.makers;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * Class responsible for rendering chart basing on the data provided by the
 * {@linkplain RegressionProblem} and the solution provided by symbolic
 * regression.
 * 
 * @author Piotr Jessa
 * 
 */
public class RegressionChartMaker implements IChartMaker {

	@Override
	public JFreeChart createChart(AbstractDataset dataset) {
		
		XYDataset xyDataset = (XYDataset) dataset;
		
		JFreeChart chart = drawChart(xyDataset);
		
		return chart;
	}

	private JFreeChart drawChart(XYDataset xyDataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Current fitness", // chart title
				"X", // x axis label
				"Y", // y axis label
				xyDataset, // data
				PlotOrientation.VERTICAL,
				true, // include legend
				true, // tooltips
				false // urls
				);
		return chart;
	}

	@Override
	public JFreeChart emptyChart() {
		// create empty data set
		XYDataset xyDataset = new DefaultXYDataset();
		
		JFreeChart chart = drawChart(xyDataset);
		
		return chart;
	}

}
