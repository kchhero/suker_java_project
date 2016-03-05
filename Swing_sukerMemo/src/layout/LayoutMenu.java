package layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class LayoutMenu {
	public LayoutMenu (final Display display, final Shell s)	{
	    final Menu m = new Menu(s, SWT.BAR);
	
	    // create a file menu and add an exit item
	    final MenuItem file = new MenuItem(m, SWT.CASCADE);
	    file.setText("&File");
	    final Menu filemenu = new Menu(s, SWT.DROP_DOWN);
	    file.setMenu(filemenu);
	    
/*	    final MenuItem openItem = new MenuItem(filemenu, SWT.CASCADE);
	    openItem.setText("&Working Directory Set...");
	   
		@SuppressWarnings("unused")
		final MenuItem separator = new MenuItem(filemenu, SWT.SEPARATOR);
*/		
	    final MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
	    exitItem.setText("E&xit");
		
	    // create a Help menu and add an about item
	    final MenuItem help = new MenuItem(m, SWT.CASCADE);
	    help.setText("&Help");
	    final Menu helpmenu = new Menu(s, SWT.DROP_DOWN);
	    help.setMenu(helpmenu);
	    final MenuItem aboutItem = new MenuItem(helpmenu, SWT.PUSH);
	    aboutItem.setText("&About");
	
	    // add action listeners for the menu items
	    // this code is the same as seen previously so it is
	    // omitted here
/*	    openItem.addSelectionListener(new SelectionListener() {	    	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
			@Override
			public void widgetSelected(SelectionEvent e) {				
			}
	    });
*/	    
	    exitItem.addSelectionListener(new SelectionListener() {	    	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				s.close();
	    		display.dispose();
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				s.close();
	    		display.dispose();
			}
	    });
	    
	    aboutItem.addSelectionListener(new SelectionListener() {	    	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				showAboutBox(s);
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				showAboutBox(s);
			}
	    });
	    s.setMenuBar(m);
	}
	
	private void showAboutBox(final Shell shell)	{
		 MessageBox box = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION);
		      // Set title
		      box.setText("suker Simple editor");
		      // Set message
		      box.setMessage("This tools is a simple editor by Suker");
		      // Open message box
		      if (box.open() == SWT.CANCEL)
		        ;
	}
}
