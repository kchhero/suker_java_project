package sukerPkg;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class AndroSuker_AlertDialog extends Dialog {

	protected Object result;
	protected Shell shlAlert;
	protected int	mStyle;

	private Label lblMsg;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AndroSuker_AlertDialog(Shell parent, int style, String title) {
		super(parent, style);
		mStyle = style;
		setText(title);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open(String msg) {
		createContents(msg);
		shlAlert.open();
		shlAlert.layout();
		Display display = getParent().getDisplay();
		while (!shlAlert.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(String msg) {
		shlAlert = new Shell(getParent(), mStyle);
		
		shlAlert.setSize(307, 165);
		shlAlert.setText(getText());
		shlAlert.setLayout(new GridLayout(2, false));
		new Label(shlAlert, SWT.NONE);

		lblMsg = new Label(shlAlert, SWT.CENTER);
		lblMsg.setAlignment(SWT.CENTER);
		lblMsg.setFont(SWTResourceManager.getFont("±¼¸²", 12, SWT.NORMAL));
		GridData gd_lblError = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblError.widthHint = 234;
		gd_lblError.heightHint = 104;
		lblMsg.setLayoutData(gd_lblError);
		lblMsg.setText(msg);
		new Label(shlAlert, SWT.NONE);
		
		Button btnOKButton = new Button(shlAlert, SWT.NONE);
		btnOKButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnOKButton.setText("OK");		
		btnOKButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				shlAlert.close();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});		
	}
}
