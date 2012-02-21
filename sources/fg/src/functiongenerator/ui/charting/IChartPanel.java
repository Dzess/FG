package functiongenerator.ui.charting;

import java.awt.Component;

import ec.gp.GPIndividual;

/**
 * Identifies the each of the charts that can be showed with
 * {@linkplain ChartsHolderDialog}
 */
public interface IChartPanel {

	/**
	 * The drawn content
	 */
	public Component getComponent();

	/**
	 * Header of the current chart
	 */
	public String getTitle();

	/**
	 * Recalculate all the stuff with this chart and draw it.
	 * 
	 * @param individual
	 *            : currently the best solution
	 */
	public void redraw(GPIndividual individual);

}