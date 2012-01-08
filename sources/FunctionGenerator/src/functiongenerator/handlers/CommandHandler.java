package functiongenerator.handlers;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import functiongenerator.engine.Engine;
import functiongenerator.ui.MainDialog;
import functiongenerator.ui.ProgressDialog;
import functiongenerator.ui.ResultsDialog;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class CommandHandler extends AbstractHandler {

	static private final Log logger = LogFactory.getLog(CommandHandler.class);

	static private ExecutorService pool = Executors.newCachedThreadPool();

	/**
	 * The constructor.
	 */
	public CommandHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);

		logger.debug("Starting the execution of the Command Hanlder");
		pool.execute(new Runnable() {
			
			private MainDialog mainDlg;
			private ProgressDialog progressDlg;
			private ResultsDialog resultsDlg;

			
			@Override
			public void run() {
				Locale.setDefault(Locale.ENGLISH);

				mainDlg = null;
				progressDlg = null;
				resultsDlg = null;
				try {
					
					logger.info("Starting wizard");
					mainDlg = new MainDialog(null);

					while (true) {
						logger.info("Restarting wizard");
						mainDlg.resetState();
						mainDlg.setVisible(true);

						if (mainDlg.getResult()) {

							Engine engine = new Engine();
							engine.setGenerations(mainDlg.getGenerations());
							engine.setPopulationSize(mainDlg
									.getPopulationSize());
							engine.setOperations(mainDlg.getOperations());
							engine.setPoints(mainDlg.getPoints());
							engine.setMaxTreeDepth(mainDlg.getMaxTreeDepth());

							progressDlg = new ProgressDialog(null);
							progressDlg.setVisible(true);
							progressDlg.setEngine(engine);

							engine.addListener(progressDlg);

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
					MessageDialog.openError(window.getShell(),"FunctionGenerator", ex.getLocalizedMessage());
					logger.error(ex);
				} finally {
					if (mainDlg != null)
						mainDlg.dispose();
					if (progressDlg != null)
						progressDlg.dispose();
				}
			}
		});

		return null;
	}
}
