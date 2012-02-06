package functiongenerator.ui.charting;

import java.awt.Dimension;

import javax.swing.JDialog;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;
import org.jfree.ui.RefineryUtilities;

import com.sun.codemodel.internal.JResourceFile;

import ec.gp.GPIndividual;
import functiongenerator.core.Engine;
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

	private Engine engine;

	private ChartPanel chartPanel;

	public RegressionChartDialog(final IDataSetProvider dataSetProvider, final IChartMaker chartMaker) {

		this.dataSetProvider = dataSetProvider;
		this.chartMaker = chartMaker;

		JFreeChart chart = chartMaker.emptyChart();
		chartPanel = new ChartPanel(chart);
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

		GPIndividual individual = tryGettingIndividual();
		JFreeChart chart = null;
		if (individual != null) {
			AbstractDataset dataset = dataSetProvider.getDataSet(individual);
			chart = chartMaker.createChart(dataset);
		} else {
			chart = chartMaker.emptyChart();
		}

		chartPanel.setChart(chart);
	}

	private GPIndividual tryGettingIndividual() {
		if (engine != null) {
			if (engine.getBestIndividual() != null) {
				return engine.getBestIndividual();
			}
		}
		return null;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}
}
