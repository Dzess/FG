package pl.put.mio.fg.sample.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import pl.put.mio.fg.sample.ic.SamplePopupDialog;

public class SamplePopupHandler extends AbstractHandler {
	
	private IWorkbenchWindow window;
	private SamplePopupDialog control;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindow(event);
		if (window == null) {
			return null;
		}
		
//		control = new SamplePopupDialog(window, 1, true, false, true, false, "Sample View", "Info Test alalalalalala");
//		control.open();
		
		return null;
	}

}
