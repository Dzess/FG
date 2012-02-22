package functiongenerator.ui.charting;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import ec.gp.GPIndividual;
import functiongenerator.core.EvolutionStateHelper;
import functiongenerator.core.IProgressListener;

/**
 * Displays the tab view with available with charts. Manages the redraw of the
 * inside elements (only performs one drawing at the time - currently selected
 * tab).
 * 
 */
@SuppressWarnings("serial")
public class ChartsHolderDialog extends JDialog implements IProgressListener {

	static private final Dimension PREFFERED_SIZE = new Dimension(800, 600);

	private final List<IChartPanel> charts;
	private final JTabbedPane tabbedPane;

	public ChartsHolderDialog(List<IChartPanel> avaliablePanels) {

		setTitle("Charts");

		this.charts = avaliablePanels;

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setPreferredSize(PREFFERED_SIZE);
		setMinimumSize(PREFFERED_SIZE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		for (IChartPanel iChartPanel : avaliablePanels) {
			Component pane = iChartPanel.getComponent();
			String title = iChartPanel.getTitle();
			tabbedPane.addTab(title, pane);
		}
	}	

	@Override
	public void update(String message, EvolutionStateHelper helper) {

		
		// let this only one chart be redrawn
		int index = tabbedPane.getSelectedIndex();
		IChartPanel panel = charts.get(index);
		panel.redraw(helper);
		
		Component component = tabbedPane.getSelectedComponent();
		component.repaint();
	}

}