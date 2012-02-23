package pl.alife.fg.ui.charting.regression.makers;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;

public interface IChartMaker {

    public abstract JFreeChart createChart(final AbstractDataset dataset);

    public abstract JFreeChart emptyChart();

}