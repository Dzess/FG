package pl.put.mio.fg.sample.ic;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * Sample class for implementing the advanced popup dialog.
 * See implementation of QuickAccessDialog to get more info about 
 * this.
 * 
 * @author Piotr Jessa
 *
 */
public class SamplePopupDialog extends PopupDialog {

	private IWorkbenchWindow window;
	protected Table table ;
	
	public SamplePopupDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistBounds,
			boolean showDialogMenu, boolean showPersistActions,
			String titleText, String infoText) {
		super(parent, SWT.RESIZE, true, true, // persist size
				false, // but not location
				true, true, 
				"Title Text",
				"Info text");
		
		this.table = new Table(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		
		// TODO: put internal layout of the controls here
		
		return composite;
	}
	
	@Override
	protected Point getDefaultLocation(Point initialSize) {
		Point size = new Point(400, 400);
		Rectangle parentBounds = getParentShell().getBounds();
		int x = parentBounds.x + parentBounds.width / 2 - size.x / 2;
		int y = parentBounds.y + parentBounds.height / 2 - size.y / 2;
		return new Point(x, y);
	}
	
	@Override
	protected Point getDefaultSize() {
		GC gc = new GC(table);
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
		int x = Dialog.convertHorizontalDLUsToPixels(fontMetrics, 300);
		if (x < 350)
			x = 350;
		int y = Dialog.convertVerticalDLUsToPixels(fontMetrics, 270);
		if (y < 420)
			y = 420;
		return new Point(x, y);
	}
}
