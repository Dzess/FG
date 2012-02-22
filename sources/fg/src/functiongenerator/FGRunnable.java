package functiongenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import functiongenerator.core.Engine;
import functiongenerator.core.gp.problem.AbstractRegressionProblem;
import functiongenerator.handlers.CommandHandler;
import functiongenerator.ui.MainDialog;
import functiongenerator.ui.ProgressDialog;
import functiongenerator.ui.ResultsDialog;
import functiongenerator.ui.charting.ChartsHolderDialog;
import functiongenerator.ui.charting.FitnessInTimeChartPanel;
import functiongenerator.ui.charting.IChartPanel;
import functiongenerator.ui.charting.RegressionChartPanel;
import functiongenerator.ui.charting.data.IDataSetProvider;
import functiongenerator.ui.charting.data.RegressionDataSetProvider;
import functiongenerator.ui.charting.data.SimpleDataSetProvider;
import functiongenerator.ui.charting.makers.IChartMaker;
import functiongenerator.ui.charting.makers.RegressionChartMaker;
import functiongenerator.ui.charting.makers.SimpleChartMaker;

/**
 * What actually runs the code of function generator. The most of command
 * handlers simply invoke the {@link FGRunnable.run} method from this class.
 * 
 * 
 * <p>
 * Use {@link FGRunnable.main} method to run this program as the stand alone
 * application.
 * </p>
 */
public class FGRunnable implements Runnable {

	static private final Log logger = LogFactory.getLog(CommandHandler.class);

	private MainDialog mainDlg;
	private ProgressDialog progressDlg;
	private ChartsHolderDialog chartDlg;
	private ResultsDialog resultsDlg;

	private final IWorkbenchWindow window;

	/**
	 * Starts the Function Generator program without explicit threading options.
	 * For the ease of the debugging this option should be used.
	 * 
	 * @param args
	 */
	static public void main(String[] args) {

		// TODO: write getting proper look and feel
		// this part of code for selection should be integrated within eclipse
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.warn("No look and feel", e);
		}
		

		FGRunnable runner = new FGRunnable(null);
		runner.run();
	}

	public FGRunnable(IWorkbenchWindow window) {
		this.window = window;

	}

	private List<IChartPanel> produceChartPanels(Engine engine) {
		List<IChartPanel> panels = new ArrayList<IChartPanel>();

		// regression
		AbstractRegressionProblem problem = engine.getProblem();
		IDataSetProvider dataSetProvider = new RegressionDataSetProvider(
				problem);
		IChartMaker chartMaker = new RegressionChartMaker();

		panels.add(new RegressionChartPanel("Visualization", dataSetProvider,chartMaker));
		panels.add(new FitnessInTimeChartPanel());

		// NOTE: add other charting options here

		return panels;
	}

	@Override
	public void run() {
		Locale.setDefault(Locale.ENGLISH);

		mainDlg = null;
		progressDlg = null;
		resultsDlg = null;
		chartDlg = null;
		try {

			logger.info("Starting wizard");
			mainDlg = new MainDialog(null);

			// loop is used
			// to maintain working even after the computations
			// only way to exit from this loop is to hit cancel button or X in
			// jDialog
			while (true) {
				logger.info("Restarting wizard");
				mainDlg.resetState();
				mainDlg.setVisible(true);

				if (mainDlg.getResult()) {

					Engine engine = new Engine();

					engine.setSettings(mainDlg.getSettings());
					engine.setPoints(mainDlg.getPoints());

					engine.init();

					progressDlg = new ProgressDialog(null);
					progressDlg.setVisible(true);
					progressDlg.setEngine(engine);
					engine.addListener(progressDlg);

					List<IChartPanel> avaliablePanels = produceChartPanels(engine);
					chartDlg = new ChartsHolderDialog(avaliablePanels);
					chartDlg.setVisible(true);
					engine.addListener(chartDlg);

					logger.debug("Running the algorithm");
					String code = engine.run();

					progressDlg.dispose();
					chartDlg.dispose();

					if (code != null) { // not cancelled
						resultsDlg = new ResultsDialog(null);
						resultsDlg.setTemplate(code);

						// wait with return of main dialog
						// till the result dialog is disposed
						logger.debug("Waiting till the result window is disposed");
						resultsDlg.setVisible(true);
					}

				} else {
					// break from the overall loop
					logger.info("Finishing the wizard");
					break;
				}
			}
		} catch (Exception ex) {
			if (window != null) {
				MessageDialog.openError(window.getShell(), "FunctionGenerator",
						ex.getLocalizedMessage());
			}
			ex.printStackTrace();
			logger.error(ex);
		} finally {

			if (mainDlg != null) {
				mainDlg.dispose();
			}

			if (progressDlg != null) {
				progressDlg.dispose();
			}

			if (resultsDlg != null) {
				resultsDlg.dispose();
			}

			if (chartDlg != null) {
				chartDlg.dispose();
			}
		}
	}
}