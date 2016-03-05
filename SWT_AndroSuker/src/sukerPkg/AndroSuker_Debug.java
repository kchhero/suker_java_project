package sukerPkg;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class AndroSuker_Debug {
	public static boolean DEBUG_MODE_ON 			= false;
	
	public static void debugMsgDlg(AndroSuker_Execute mExe, String title, String message, int mode)	{
		if (mode == 1)	{
			MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.ICON_ERROR | SWT.OK | SWT.CANCEL);
			mb.setText(title);
			mb.setMessage(message);
			int buttonID = mb.open();
			switch(buttonID) {
			case SWT.OK:
				adbRestartServer(mExe);
				break;
			case SWT.CANCEL:
				break;
			}
		} else	{
			MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.ICON_ERROR | SWT.OK);
			mb.setText(title);
			mb.setMessage(message);
			mb.open();
		}
	}
	
	public static void adbRestartServer(AndroSuker_Execute mExe)	{
		String t = "cmd.exe~~/K~~start~~perl~~script\\adbCommand.pl~~RestartServer";				
		String[] cmdListtemp = t.split("~~");
		try {
			mExe.runProcessSimple(cmdListtemp);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}

class AsyncMsgBoxDraw	{
	final static Timer 	timer = new Timer();
	static Shell			thisShell;
	static String 			titleText;
	static String 			msgText;
	static int				type;

	public static void MessageBoxDraw(Shell shell, String title, String message, int i)	{
		thisShell = shell;
		titleText = title;
		msgText = message;
		type = i;

		TimerTask asyncLogTextTimer = new TimerTask() {							 
			public void run() {
				if (thisShell.isDisposed())
					return ;

				thisShell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), type);
						mb.setText(titleText);
						mb.setMessage(msgText);
						mb.open();
					}
				});
			}
		};
		timer.schedule(asyncLogTextTimer, 50);
	}	
}
