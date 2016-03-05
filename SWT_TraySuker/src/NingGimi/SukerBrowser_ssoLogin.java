package NingGimi;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class SukerBrowser_ssoLogin {
	private Display display;
	protected Shell 	shell;
	protected Shell 	shell2;
	private String 		url = "http://sso.lge.com/";
	private Browser 	browser = null;
	
	private String		userId;
	private String		passWord;
	
	private BrowserThread	brThread;
	
	/**
	 * @throws InterruptedException 
	 * @wbp.parser.entryPoint
	 */
	SukerBrowser_ssoLogin(Display d, String arg0, String arg1)	{
		display = d;
		userId = arg0;
		passWord = arg1;
		
		brThread = new BrowserThread();
		brThread.start();
	}

	class BrowserThread extends Thread {
		@Override
		public void run()	{			
			display.asyncExec(new Runnable()	{
				public void run()	{
					shell = new Shell();
					shell.setText("Browser");
					createContents(shell);
					shell.open();

					browser.setUrl(url);
				}
			});
		}	
	}

	
	final void setTitle(String title) {
		this.shell.setText(title);
	}

	private void createContents(Shell shell) {
		shell.setLayout(new FillLayout());
		browser = new Browser(shell, SWT.NONE);
		browser.addLocationListener(new org.eclipse.swt.browser.LocationListener()
		{
			public void changed(LocationEvent e) {
				setTitle("LG sso for Suker");
				browser.execute("document.getElementById('user').value = " + "'" + userId + "';");
				browser.execute("document.getElementById('password').value = " + "'" + passWord + "';");
				browser.execute("loginSso();");
				browser.removeLocationListener(this);
			}
			public void changing(LocationEvent e) {
			}
		});
		browser.addOpenWindowListener(new OpenWindowListener() {
			@Override
			public void open(WindowEvent event) {
				// TODO Auto-generated method stub				
			}
			
		});
	}
}