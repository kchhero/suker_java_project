package control;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ControlDialogYesNo {
	public static Boolean YesNoDialog(final Shell shell, final String message, final String text) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION	| SWT.YES | SWT.NO);
		messageBox.setMessage(message);
		messageBox.setText(text);
		int response = messageBox.open();
		if (response == SWT.YES)
			return true;
		
		return false;
	}
}
