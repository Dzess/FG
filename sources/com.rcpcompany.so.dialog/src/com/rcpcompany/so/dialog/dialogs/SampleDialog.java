package com.rcpcompany.so.dialog.dialogs;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SampleDialog extends TitleAreaDialog {
	private Text mytext;

	public SampleDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		setMessage("Enter something below...");
		setTitle("My Title");
		final Composite top = (Composite) super.createDialogArea(parent);

		final Composite composite = new Composite(top, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Label lblLabel = new Label(composite, SWT.NONE);
		lblLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLabel.setText("Label");

		mytext = new Text(composite, SWT.BORDER);
		mytext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return top;
	}
}
