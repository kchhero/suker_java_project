package SukerEditor;

import org.eclipse.swt.widgets.Label;

public class SukerEditor_StatusBarDisplay {
	private static Label lblCurrentFilePathName_StatusBarName;
	
	SukerEditor_StatusBarDisplay(Label lbl)	{
		lblCurrentFilePathName_StatusBarName = lbl;
	}
	public static void updateName(String s)	{
		lblCurrentFilePathName_StatusBarName.setText(s);
	}	
}
