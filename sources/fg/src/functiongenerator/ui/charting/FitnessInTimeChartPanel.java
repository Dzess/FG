package functiongenerator.ui.charting;

import java.awt.Component;

import javax.swing.JPanel;

import ec.gp.GPIndividual;
import functiongenerator.core.EvolutionStateHelper;

import javax.swing.JLabel;

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
