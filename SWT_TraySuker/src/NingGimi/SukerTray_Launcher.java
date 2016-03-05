package NingGimi;

import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Label;

public class SukerTray_Launcher {
	private String	launchOrder;
	private Label 	statusLabel;
	
	SukerTray_Launcher(Label l)	{
		statusLabel = l;
	}
	
	public void setLaunchOrder(String s)	{
		launchOrder = s;
		statusLabel.setText(s);
		statusLabel.pack();
	}
	public void run()	{
		Program.launch(launchOrder);	
	}
	
	@SuppressWarnings("unused")
	private String replaceCharacters(String src)	{
		String result;
		
		result = src.replace("\\", "\\\\");
		
		return result;
	}
}
