package functiongenerator.ui.charting;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.LayoutManager;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.Component;

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
	static private final Log logger = LogFactory.getLog(RegressionChartDialog.class);
	private static final String NO_DATA_AVALIABLE = "No visualization avaliable";

	private final IChartMaker chartMaker;
	private final IDataSetProvider dataSetProvider;

	private Engine engine;

	private ChartPanel chartPanel;
	private JLayeredPane mainPanel;
	private JLabel textLabel;

	public RegressionChartDialog(final IDataSetProvider dataSetProvider, final IChartMaker chartMaker) {

		this.dataSetProvider = dataSetProvider;
		this.chartMaker = chartMaker;

		mainPanel = new JLayeredPane();

		// chart panel
		JFreeChart chart = chartMaker.emptyChart();
		chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		chartPanel.setMouseZoomable(false);
		chartPanel.setReshowDelay(100);

		// label
		textLabel = new JLabel("Status:");
		textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setLayer(textLabel, 1);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);

		setPreferredSize(PREFFERED_SIZE);
		setMinimumSize(PREFFERED_SIZE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Live Preview");
		setModalityType(ModalityType.MODELESS);

		setContentPane(mainPanel);
		
		mainPanel.moveToBack(textLabel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.moveToFront(chartPanel);
		mainPanel.add(chartPanel);
		mainPanel.add(textLabel);
	}

	static public void main(String[] args) {

		final RegressionChartDialog window = new RegressionChartDialog(new SimpleDataSetProvider(), new SimpleChartMaker());
		RefineryUtilities.centerFrameOnScreen(window);
		window.setVisible(true);
	}

	@Override
	public void update(double done, String message) {

		try {
			GPIndividual individual = tryGettingIndividual();
			JFreeChart chart = null;
			if (individual != null) {
				AbstractDataset dataset = dataSetProvider.getDataSet(individual);
				chart = chartMaker.createChart(dataset);
			} else {
				chart = chartMaker.emptyChart();
			}

			chartPanel.setChart(chart);
			chartPanel.setVisible(true);

		} catch (Exception e) {
			logger.warn("Exception during drawing chart", e);

			textLabel.setText(NO_DATA_AVALIABLE);
			chartPanel.setVisible(false);
		}
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
