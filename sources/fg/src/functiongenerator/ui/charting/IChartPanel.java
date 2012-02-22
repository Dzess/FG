package functiongenerator.ui.charting;

import java.awt.Component;

import functiongenerator.core.EvolutionStateHelper;

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
     * @param state
     *            : currently the best solution
     */
    public void redraw(EvolutionStateHelper state);

}