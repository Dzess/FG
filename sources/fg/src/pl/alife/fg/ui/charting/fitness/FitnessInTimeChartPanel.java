package pl.alife.fg.ui.charting.fitness;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pl.alife.fg.core.EvolutionStateHelper;
import pl.alife.fg.ui.charting.IChartPanel;


/**
 * Displays the overall aggregated fitness over already passed populations.
 * 
 * This class saves the already found best fitness
 */
@SuppressWarnings("serial")
public class FitnessInTimeChartPanel extends JPanel implements IChartPanel {

    private static final String TITLE = "Quality in Time";
    private final XYLineAndShapeRenderer renderer;

    private final ChartPanel chartPanel;
    private XYSeries series;

    public FitnessInTimeChartPanel() {
        this.renderer = new XYLineAndShapeRenderer();
        this.series = new XYSeries("Fitness");
        this.chartPanel = new ChartPanel(this.getEmptyChart());

        setLayout(new BorderLayout(0, 0));

        add(chartPanel);
        chartPanel.setMouseZoomable(false);
        chartPanel.setReshowDelay(100);
    }

    private JFreeChart drawChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Fitness", // chart
                "Generations", // x axis label
                "Standardized Fitness", // y axis label
                dataset, PlotOrientation.VERTICAL, false, true, false);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(renderer);

        return chart;
    }

    private JFreeChart getEmptyChart() {
        XYDataset dataset = new DefaultXYDataset();
        return drawChart(dataset);
    }

    private JFreeChart getChart() {

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return drawChart(dataset);
    }

    private void updateChart(EvolutionStateHelper helper) {

        double fitness = helper.getFitness();
        int generation = helper.getGeneration();

        // the series does not change through time so use field value
        series.add(generation, fitness);
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void redraw() {
        // helper is ignored because it should be called with update method first
        chartPanel.setChart(this.getChart());
    }

    @Override
    public void update(EvolutionStateHelper state) {
        this.updateChart(state);
    }
}
