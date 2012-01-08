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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

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
	private Text textFiled;
	
	public SamplePopupDialog(Shell parent) {
		super(parent, SWT.RESIZE, true, true, // persist size
				false, // but not location
				true, true, 
				"Function Generator",
				"Enter command to get function running");
		
		this.table = new Table(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(1, false));
		
		textFiled = new Text(composite, SWT.BORDER);
		textFiled.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO: put the enter code getting here and retriving the
			}
		});
		textFiled.setForeground(SWTResourceManager.getColor(211, 211, 211));
		textFiled.setText("Please enter here ");
		textFiled.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		List list = new List(composite, SWT.NONE);
		list.setItems(new String[] {"Sample item 1"});
		list.setBackground(SWTResourceManager.getColor(211, 211, 211));
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
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
