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
     * Draws the stuff.
     */
    public void redraw();

    /**
     * Recalculate all the stuff with char but <b>not draw</b>
     * 
     * @param state
     *            : current solutions
     */
    public void update(EvolutionStateHelper state);

}