package com.rcpcompany.so.dialog.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.rcpcompany.so.dialog.dialogs.SampleDialog;

public class ShowMenuHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Shell s = HandlerUtil.getActiveShellChecked(event);

		new SampleDialog(s).open();
		return null;
	}

}
