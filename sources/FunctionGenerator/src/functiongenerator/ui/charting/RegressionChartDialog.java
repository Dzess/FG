package functiongenerator.ui.charting;

import java.awt.Dimension;

import javax.swing.JDialog;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RefineryUtilities;

import functiongenerator.core.IProgressListener;
import functiongenerator.ui.charting.data.IDataSetProvider;
import functiongenerator.ui.charting.data.SimpleDataSetProvider;
import functiongenerator.ui.charting.makers.IChartMaker;
import functiongenerator.ui.charting.makers.SimpleChartMaker;

/**
 * Displays the chart of the symbolic regression in the form of the
 * {@linkplain JDialog}.
 * 
 * <p>
 * Main method used mainly for testing.
 * </p>
 * 
 * @author Piotr Jessa
 * 
 */
@SuppressWarnings("serial")
public class RegressionChartDialog extends JDialog implements IProgressListener {

	static private final Dimension PREFFERED_SIZE = new Dimension(800, 600);
	
	private final IChartMaker chartMaker;
	private final IDataSetProvider dataSetProvider;

	public RegressionChartDialog(final IDataSetProvider dataSetProvider, final IChartMaker chartMaker) {

		this.dataSetProvider = dataSetProvider;
		this.chartMaker = chartMaker;

		final CategoryDataset dataset = dataSetProvider.getDataSet();

		final JFreeChart chart = chartMaker.createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);

		setContentPane(chartPanel);

		setPreferredSize(PREFFERED_SIZE);
		setMinimumSize(PREFFERED_SIZE);
	}

	static public void main(String[] args) {

		final RegressionChartDialog window = new RegressionChartDialog(new SimpleDataSetProvider(), new SimpleChartMaker());
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);
	}

	@Override
	public void update(double done, String message) {
		// TODO Auto-generated method stub

	}
}
