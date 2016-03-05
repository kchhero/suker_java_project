package SukerEditor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class SukerEditor_AboutDialog extends Dialog {

	protected Object result;
	protected Shell shlAbout;
	protected int	mStyle;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SukerEditor_AboutDialog(Shell parent, int style) {
		super(parent, style);
		mStyle = style;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAbout.open();
		shlAbout.layout();
		Display display = getParent().getDisplay();
		while (!shlAbout.isDisposed()) {
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
		shlAbout = new Shell(getParent(), mStyle);
		shlAbout.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		shlAbout.setSize(415, 222);
		shlAbout.setText("SukerEditor 0.8");
		shlAbout.setLayout(new FormLayout());
		
		Label lblNewLabel = new Label(shlAbout, SWT.WRAP | SWT.CENTER);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(0, 22);
		fd_lblNewLabel.left = new FormAttachment(0, 34);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel.setFont(SWTResourceManager.getFont("�޸ո���T", 14, SWT.BOLD));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblNewLabel.setText("Suker Editor 0.8");
		
		Label lblNewLabel_2 = new Label(shlAbout, SWT.NONE);
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.top = new FormAttachment(0, 65);
		fd_lblNewLabel_2.left = new FormAttachment(0, 34);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel_2.setText("Date 2012-05-01");
		
		Label lblNewLabel_3 = new Label(shlAbout, SWT.NONE);
		FormData fd_lblNewLabel_3 = new FormData();
		fd_lblNewLabel_3.top = new FormAttachment(0, 82);
		fd_lblNewLabel_3.left = new FormAttachment(0, 34);
		lblNewLabel_3.setLayoutData(fd_lblNewLabel_3);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel_3.setText("Rebuild : N/A");
		
		Label lblNewLabel_4 = new Label(shlAbout, SWT.NONE);
		FormData fd_lblNewLabel_4 = new FormData();
		fd_lblNewLabel_4.top = new FormAttachment(0, 99);
		fd_lblNewLabel_4.left = new FormAttachment(0, 34);
		lblNewLabel_4.setLayoutData(fd_lblNewLabel_4);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel_4.setText("Original version : none");
		
		Label lblNewLabel_1 = new Label(shlAbout, SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(0, 150);
		fd_lblNewLabel_1.left = new FormAttachment(0, 34);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("����ü", 10, SWT.BOLD));
		lblNewLabel_1.setText("Creator : Suker (Choonghyun.jeon@lge.com)");
		
		Label lblNewLabel_5 = new Label(shlAbout, SWT.NONE);
		lblNewLabel_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		FormData fd_lblNewLabel_5 = new FormData();
		fd_lblNewLabel_5.top = new FormAttachment(lblNewLabel_4, 6);
		fd_lblNewLabel_5.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		lblNewLabel_5.setLayoutData(fd_lblNewLabel_5);
		lblNewLabel_5.setText("description : suker\uAC00 \uB9CC\uB4E0 \uCCAB\uBC88\uC9F8 editor");
		

	}
}
