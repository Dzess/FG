package functiongenerator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import functiongenerator.core.Engine;
import functiongenerator.handlers.CommandHandler;
import functiongenerator.ui.MainDialog;
import functiongenerator.ui.ProgressDialog;
import functiongenerator.ui.ResultsDialog;

/**
 * What actually runs the code of function generator. The most of command
 * handlers simply invoke the {@link FGRunnable.run} method from this class.
 * 
 * 
 * <br/>
 * Use {@link FGRunnable.main} method to run this program as the standalone
 * application.
 */
public class FGRunnable implements Runnable {

	static private final Log logger = LogFactory.getLog(CommandHandler.class);

	private MainDialog mainDlg;
	private ProgressDialog progressDlg;
	private ResultsDialog resultsDlg;

	private final IWorkbenchWindow window;

	/**
	 * Starts the Function Generator program without explicit threading options.
	 * For the ease of the debugging this option should be used.
	 * 
	 * @param args
	 */
	static public void main(String[] args) {

		FGRunnable runner = new FGRunnable(null);
		runner.run();
	}

	public FGRunnable(IWorkbenchWindow window) {
		this.window = window;
		
	}

	@Override
	public void run() {
		Locale.setDefault(Locale.ENGLISH);

		mainDlg = null;
		progressDlg = null;
		resultsDlg = null;
		try {

			logger.info("Starting wizard");
			mainDlg = new MainDialog(null);

			// loop is used
			// to maintain working even after the computations
			// only way to exit from this loop is to hit cancel button or X in jDialog
			while (true) {
				logger.info("Restarting wizard");
				mainDlg.resetState();
				mainDlg.setVisible(true);

				if (mainDlg.getResult()) {

					Engine engine = new Engine();

					engine.setSettings(mainDlg.getSettings());
					engine.setPoints(mainDlg.getPoints());

					progressDlg = new ProgressDialog(null);
					progressDlg.setVisible(true);
					progressDlg.setEngine(engine);

					engine.addListener(progressDlg);

					logger.debug("Running the algorithm");
					String code = engine.run();

					progressDlg.dispose();

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
				MessageDialog.openError(window.getShell(), "FunctionGenerator", ex.getLocalizedMessage());
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
		}
	}
}