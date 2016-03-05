package layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LayoutInputDialog {
	static String selectedText = "";
	static StyledText keywordText;
	static String keyword;
	static Button button;

	public static String show(Shell shell, final StyledText styledText) {
		final Shell childShellDlg = new Shell(shell, SWT.DIALOG_TRIM);
		childShellDlg.setText("Find Text");
		childShellDlg.setSize(250, 150);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		childShellDlg.setLayout(layout);
		childShellDlg.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				// don't dispose of the shell, just hide it for later use
				e.doit = false;
				childShellDlg.setVisible(false);
			}
		});
		Display display = childShellDlg.getDisplay();

		childShellDlg.setLayout(new GridLayout(1, false));

		// styledText = new StyledText(shell, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan = 2; 

		keywordText = new StyledText(childShellDlg, SWT.SINGLE | SWT.BORDER);
		keywordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Font font = new Font(shell.getDisplay(), "Courier New", 12, SWT.NORMAL);

		button = new Button(childShellDlg, SWT.PUSH);
		button.setText("Find");

		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				keyword = keywordText.getText();
				styledText.redraw();
			}
		});

		/*styledText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent event) {
				if(keyword == null || keyword.length() == 0) {
					event.styles = new StyleRange[0];
					return;
				}

				String line = event.lineText;
				int cursor = -1;

				LinkedList list = new LinkedList();
				while( (cursor = line.indexOf(keyword, cursor+1)) >= 0) {
					list.add(getHighlightStyle(event.lineOffset+cursor, keyword.length(), childShellDlg));
					System.out.println("cursor = " + cursor + " " + keyword );
				}

				event.styles = (StyleRange[]) list.toArray(new StyleRange[list.size()]);
			}
		});
*/
		childShellDlg.open();

		while (!childShellDlg.isDisposed() ) {
			if (!display.readAndDispatch() ) {
				display.sleep();
			}
		}

		return selectedText;
	}
}