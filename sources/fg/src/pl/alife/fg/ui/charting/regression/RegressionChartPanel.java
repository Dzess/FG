package pl.alife.fg.ui.charting.regression;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;

import pl.alife.fg.core.EvolutionStateHelper;
import pl.alife.fg.ui.charting.IChartPanel;
import pl.alife.fg.ui.charting.regression.data.IDataSetProvider;
import pl.alife.fg.ui.charting.regression.makers.IChartMaker;
import pl.alife.fg.ui.printing.TreeToStringTranslator;

import ec.gp.GPIndividual;
import ec.gp.GPTree;

/**
 * Displays the chart of the symbolic regression in the form of the
 * {@linkplain JDialog}.
 * 
 */
@SuppressWarnings("serial")
public class RegressionChartPanel extends JPanel implements IChartPanel {

    static private final Log logger = LogFactory.getLog(RegressionChartPanel.class);

    private static final String NO_DATA_AVALIABLE = "No visualization avaliable";

    private final IChartMaker chartMaker;
    private final IDataSetProvider dataSetProvider;

    private ChartPanel chartPanel;
    private JTextArea textArea;

    private String title;

    private EvolutionStateHelper state;

    public RegressionChartPanel(String title, final IDataSetProvider dataSetProvider, final IChartMaker chartMaker) {

        this.dataSetProvider = dataSetProvider;
        this.chartMaker = chartMaker;
        this.title = title;

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

        setPreferredSize(new Dimension(400, 300));

        setLayout(new BorderLayout(0, 0));

        add(chartPanel, BorderLayout.CENTER);
        add(textArea, BorderLayout.NORTH);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public void redraw() {

        try {
            JFreeChart chart = null;
            GPIndividual individual = state.getBesIndividual();
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
        } finally {
            repaint();
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void update(EvolutionStateHelper state) {
        this.state = state;
    }
}
