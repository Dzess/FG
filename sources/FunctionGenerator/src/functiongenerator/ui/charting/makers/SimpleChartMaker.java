package functiongenerator.ui.charting.makers;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;

/**
 * Class responsible for rendering chart basing on the data provided by the
 * {@linkplain RegressionProblem} and the solution provided by symbolic
 * regression.
 * 
 * @author Piotr Jessa
 * 
 */
public class SimpleChartMaker implements IChartMaker {

	/* (non-Javadoc)
	 * @see functiongenerator.ui.charting.makers.IChartMaker#createChart(org.jfree.data.category.CategoryDataset)
	 */
	@Override
	public JFreeChart createChart(final CategoryDataset dataset) {
		// create the chart...
		final JFreeChart chart = ChartFactory.createLineChart("Line Chart Demo 1", // chart
																					// title
				"Type", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);

		// customise the renderer...
		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		// renderer.setDrawShapes(true);

		renderer.setSeriesStroke(0, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 10.0f, 6.0f },
				0.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 6.0f, 6.0f },
				0.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[] { 2.0f, 6.0f },
				0.0f));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}
}
