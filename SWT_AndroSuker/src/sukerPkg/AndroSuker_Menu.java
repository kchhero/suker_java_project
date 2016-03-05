package sukerPkg;

import java.util.HashMap;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class AndroSuker_Menu	{
	private HashMap<String, Boolean> retHashMap;
	
	public HashMap<String, Boolean> getTabVisibility()	{
		return retHashMap;
	}
	public void setTabVisibility(HashMap<String, Boolean> map)	{
		retHashMap = map;
	}
	
	public void makeMenu(AndroSuker_Main_Layout parent, Shell shell, HashMap<String, Boolean> map)
	{
		Menu mainMenu = new Menu(shell, SWT.BAR);
		
		MenuItem file = new MenuItem(mainMenu, SWT.CASCADE);
		file.setText("File");
		
		//------------------------- File -----------------------------
		Menu filemenu = new Menu(shell, SWT.DROP_DOWN);
	    file.setMenu(filemenu);
	    //MenuItem openItem = new MenuItem(filemenu, SWT.PUSH);
	    //openItem.setText("Open");
	    MenuItem exitItem = new MenuItem(filemenu, SWT.PUSH);
	    exitItem.setText("E&xit");
  
	    //openItem.addSelectionListener(new MenuItemListener(shell));
	    exitItem.addSelectionListener(new MenuItemListener(shell));

	    //------------------------- Setup -----------------------------
	    MenuItem setup = new MenuItem(mainMenu, SWT.CASCADE);
	    setup.setText("Setup");
  
		Menu setupmenu = new Menu(shell, SWT.DROP_DOWN);
		setup.setMenu(setupmenu);
	    
	    MenuItem setupItem = new MenuItem(setupmenu, SWT.PUSH);
	    setupItem.setText("Tab setup...");

	    setupItem.addSelectionListener(new MenuItemListener(parent, shell, map));

	    //------------------------- About -----------------------------
	    MenuItem about = new MenuItem(mainMenu, SWT.CASCADE);
	    about.setText("Help");
  
		Menu aboutmenu = new Menu(shell, SWT.DROP_DOWN);
		about.setMenu(aboutmenu);
	    
	    MenuItem aboutItem = new MenuItem(aboutmenu, SWT.PUSH);
	    aboutItem.setText("About");

	    aboutItem.addSelectionListener(new MenuItemListener(shell));
	    
		shell.setMenuBar(mainMenu);
	}
}

class MenuItemListener extends SelectionAdapter {
	Shell localShell;
	HashMap<String, Boolean> tabVisibilityMap;
	private tabChangeDetect tabChangeObserver;
	
	MenuItemListener(Shell shell)	{
		localShell = shell;
	}
	MenuItemListener(AndroSuker_Main_Layout parent, Shell shell, HashMap<String, Boolean> map)	{
		localShell = shell;
		tabVisibilityMap = map;
		tabChangeObserver = new tabChangeDetect();
		tabChangeObserver.addObserver(parent);
	}
	public void widgetSelected(SelectionEvent event) {
		if (((MenuItem) event.widget).getText().equals("E&xit")) {
			localShell.close();
		} else if (((MenuItem) event.widget).getText().equals("Tab setup...")) {
			HashMap<String, Boolean> ret;
			AndroSuker_SetupDialog setupDlg = new AndroSuker_SetupDialog(localShell, SWT.DIALOG_TRIM  | SWT.APPLICATION_MODAL);
			ret = setupDlg.open(tabVisibilityMap);	
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (ret.get("OK"))	{
				tabChangeObserver.action(ret);
			}
		} else if (((MenuItem) event.widget).getText().equals("About")) {
			AndroSuker_AboutDialog aboutDlg = new AndroSuker_AboutDialog(localShell, SWT.DIALOG_TRIM  | SWT.APPLICATION_MODAL);
			aboutDlg.open();
		}
	}
}

class tabChangeDetect extends Observable	{
	public void action(HashMap<String, Boolean> map)	{
		setChanged();
		notifyObservers(map);
	}
}