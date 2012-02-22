package functiongenerator.ui.charting.fitness;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import functiongenerator.core.EvolutionStateHelper;
import functiongenerator.ui.charting.IChartPanel;

/**
 * Displays the overall aggregated fitness over already passed populations.
 */
@SuppressWarnings("serial")
public class FitnessInTimeChartPanel extends JPanel implements IChartPanel {
	private JLabel label;

	public FitnessInTimeChartPanel() {

		label = new JLabel("Uber Control Here");
		add(label);
	}

	private static final String TITLE = "Quality in Time";

	@Override
	public Component getComponent() {
		return this;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void redraw(EvolutionStateHelper helper) {
		// actually do nothing for now
		
	}

}
