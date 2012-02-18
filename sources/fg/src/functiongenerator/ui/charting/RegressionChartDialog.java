package functiongenerator.ui.charting;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;
import org.jfree.ui.RefineryUtilities;

import ec.gp.GPIndividual;
import ec.gp.GPTree;
import functiongenerator.core.Engine;
import functiongenerator.core.IProgressListener;
import functiongenerator.ui.charting.data.IDataSetProvider;
import functiongenerator.ui.charting.data.SimpleDataSetProvider;
import functiongenerator.ui.charting.makers.IChartMaker;
import functiongenerator.ui.charting.makers.SimpleChartMaker;
import functiongenerator.ui.printing.TreeToStringTranslator;

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
	static private final Dimension PREFFERED_SIZE_TEXT_AREA = new Dimension(800, 200);
	static private final Log logger = LogFactory.getLog(RegressionChartDialog.class);
	private static final String NO_DATA_AVALIABLE = "No visualization avaliable";

	private final IChartMaker chartMaker;
	private final IDataSetProvider dataSetProvider;

	private Engine engine;

	private ChartPanel chartPanel;
	private JLayeredPane mainPanel;
	private JTextArea textArea;

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
		textArea = new JTextArea("");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setMinimumSize(PREFFERED_SIZE_TEXT_AREA);
		mainPanel.setLayer(textArea, 1);

		setPreferredSize(PREFFERED_SIZE);
		setMinimumSize(PREFFERED_SIZE);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Live Preview");
		setModalityType(ModalityType.MODELESS);

		setContentPane(mainPanel);

		mainPanel.moveToBack(textArea);
		mainPanel.setLayout(new BorderLayout(0, 0));
		mainPanel.moveToFront(chartPanel);
		mainPanel.add(chartPanel, BorderLayout.CENTER);
		mainPanel.add(textArea, BorderLayout.NORTH);
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

			// set data to the label (usually the first one ;))
			GPTree tree = individual.trees[0];
			String expression = TreeToStringTranslator.translateTree(tree);
			textArea.setText(expression);

		} catch (Exception e) {
			logger.warn("Exception during drawing chart", e);

			textArea.setText(NO_DATA_AVALIABLE);
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