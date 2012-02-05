package functiongenerator.ui.charting.makers;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

public interface IChartMaker {

	public abstract JFreeChart createChart(final CategoryDataset dataset);

}