package layout;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import control.ControlDialogYesNo;
import control.ControlFiles;
import definition.Config;

public class StartMain {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());		
				
		Config.CurrentWorkingDirectory = ".\\working";
		//Yes --> default directory
		if (Config.debugOn == false)
		{			
			Boolean retSaveFile = false;
			retSaveFile = ControlDialogYesNo.YesNoDialog(shell,
					"Working directory : " + Config.CurrentWorkingDirectory + " OK ?",
					"Choice");
		//No --> change directory
			if (!retSaveFile)	{
				String temp = null;
				temp = ControlFiles.dirOpen(shell);
				if (temp != null && temp.length() > 0)	{
					Config.CurrentWorkingDirectory = temp;
				}
			}
		}
		new LayoutColor(display);
		new LayoutMenu(display, shell);
		new LayoutMain(display, shell);
		
		Image iconImg = new Image(display,".\\icons\\icon.png");
	    
	    shell.setImage(iconImg);
		shell.setText(getResourceString("suker editor"));
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				Shell[] shells = display.getShells();
				for (int i = 0; i < shells.length; i++) {
					if (shells[i] != shell)
						shells[i].close();
				}
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	/**
	 * Gets a string from the resource bundle. We don't want to crash because of
	 * a missing String. Returns the key if not found.
	 */
	static String getResourceString(String key) {
		return key;
	}

	/**
	 * Gets a string from the resource bundle and binds it with the given
	 * arguments. If the key is not found, return the key.
	 */
	static String getResourceString(String key, Object[] args) {
		try {
			return MessageFormat.format(getResourceString(key), args);
		} catch (MissingResourceException e) {
			return key;
		} catch (NullPointerException e) {
			return "!" + key + "!";
		}
	}
}
