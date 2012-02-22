package functiongenerator.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import ec.gp.GPIndividual;
import functiongenerator.core.Engine;
import functiongenerator.core.EvolutionStateHelper;
import functiongenerator.core.IProgressListener;

@SuppressWarnings("serial")
public class ProgressDialog extends JDialog implements IProgressListener,
		ActionListener {

	private JPanel jContentPane = null;
	private JProgressBar progressBar = null;
	private JTextArea textLog = null;
	private JButton buttonCancel = null;
	private JPanel panelButtons = null;
	private JScrollPane textLogContainer = null;
	private boolean result = true;
	private Engine engine;

	public boolean getResult() {
		return result;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	/**
	 * @param owner
	 */
	public ProgressDialog(Frame owner) {
		super(owner, ModalityType.TOOLKIT_MODAL);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(550, 250);
		this.setMinimumSize(new Dimension(550, 250));
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setName("ProgressDialog");
		this.setTitle("Progress...");
		this.setContentPane(getJContentPane());
		this.setModal(false);
		this.setWindowAtScreenCenter();
	}

	private void setWindowAtScreenCenter() {

		int widthWindow = this.getWidth();
		int heightWindow = this.getHeight();

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width / 2) - (widthWindow / 2); // Center horizontally.
		int Y = (screen.height / 2) - (heightWindow / 2); // Center vertically.

		this.setBounds(X, Y, widthWindow, heightWindow);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getProgressBar(), BorderLayout.NORTH);
			jContentPane.add(getTextLogContainer(), BorderLayout.CENTER);
			jContentPane.add(getPanelButtons(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes progressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setPreferredSize(new Dimension(148, 20));
		}
		return progressBar;
	}

	/**
	 * This method initializes textLog
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTextLog() {
		if (textLog == null) {
			textLog = new JTextArea();
			textLog.setEditable(false);
			textLog.setLineWrap(false);
		}
		return textLog;
	}

	/**
	 * This method initializes buttonCancel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonCancel() {
		if (buttonCancel == null) {
			buttonCancel = new JButton();
			buttonCancel.setText("Cancel");
			buttonCancel.setHorizontalAlignment(SwingConstants.CENTER);
			buttonCancel.addActionListener(this);
		}
		return buttonCancel;
	}

	/**
	 * This method initializes panelButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panelButtons = new JPanel();
			panelButtons.setPreferredSize(new Dimension(83, 35));
			panelButtons.setLayout(flowLayout);
			panelButtons.add(getButtonCancel(), null);
		}
		return panelButtons;
	}

	@Override
	public void update(String message, EvolutionStateHelper helper) {
		double done = helper.getCompletedPercent();
		progressBar.setValue((int) done);
		textLog.append(message);
		JScrollBar scroll = textLogContainer.getVerticalScrollBar();
		scroll.setValue(scroll.getMaximum());
	}

	/**
	 * This method initializes textLogContainer
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getTextLogContainer() {
		if (textLogContainer == null) {
			textLogContainer = new JScrollPane();
			textLogContainer.setPreferredSize(new Dimension(3, 12));
			textLogContainer.setViewportView(getTextLog());
		}
		return textLogContainer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancel")) {
			result = false;
			engine.cancel();
			this.setVisible(false);
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
