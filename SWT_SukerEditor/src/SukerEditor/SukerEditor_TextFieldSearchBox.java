package SukerEditor;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SukerEditor_TextFieldSearchBox extends Dialog {

	protected Object result;
	protected Shell shlFindKeyword;
	private Text textFind;
	private Text text_1;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SukerEditor_TextFieldSearchBox(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlFindKeyword.open();
		shlFindKeyword.layout();
		Display display = getParent().getDisplay();
		while (!shlFindKeyword.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlFindKeyword = new Shell(getParent(), SWT.DIALOG_TRIM);
		shlFindKeyword.setSize(339, 138);
		shlFindKeyword.setText("Find keyword");
		shlFindKeyword.setLayout(new GridLayout(4, true));
		
		Label lblFind = new Label(shlFindKeyword, SWT.NONE);
		lblFind.setText("Find:");
		
		textFind = new Text(shlFindKeyword, SWT.BORDER);
		GridData gd_textFind = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_textFind.widthHint = 230;
		textFind.setLayoutData(gd_textFind);
		
		Label lblNewLabel_1 = new Label(shlFindKeyword, SWT.NONE);
		lblNewLabel_1.setEnabled(false);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("TBD");
		
		text_1 = new Text(shlFindKeyword, SWT.BORDER);
		text_1.setEnabled(false);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		new Label(shlFindKeyword, SWT.NONE);
		
		Button btnFind = new Button(shlFindKeyword, SWT.NONE);
		btnFind.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SukerEditor_TextFieldControl.setSearchKeyword(textFind.getText());
				//shlFindKeyword.dispose();
			}
		});
		btnFind.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnFind.setText("Find");
		
		Button btnClose = new Button(shlFindKeyword, SWT.NONE);
		btnClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlFindKeyword.close();
			}
		});
		btnClose.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnClose.setText("Close");

	}
}
