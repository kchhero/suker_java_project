package SukerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class SukerEditor_MenuDlg {
	private Shell		mShell;
	//private MenuManager	mOpen;
	private String		savePath;
	private String		openPath;
	
	SukerEditor_MenuDlg()	{
		this.mShell = SukerEditor_MainCls.getShellInstance();
	}

	public String SaveFile(String startPath)	{
		FileDialog fileDialog = new FileDialog(this.mShell, SWT.SAVE);

		if (startPath != null && startPath.length() > 1)
			fileDialog.setFilterPath(startPath);
		
		fileDialog.setFilterNames(new String[] {"Temporary Text Files (*.txt)"});
		fileDialog.setFilterExtensions(new String[] {"*.*"});
		fileDialog.setText("Select file..");
		//fileDialog.setFilterPath(folder.getRawLocation().toString());
		
		savePath = fileDialog.open();
		//System.out.print("save file path : " + savePath.toString());
		
		if (savePath == null || savePath.length() < 1)
			savePath = "";
		
		return savePath;
	}
	
	public String OpenFile(String startPath)	{
		FileDialog fileDialog = new FileDialog(this.mShell, SWT.OPEN);

		if (startPath != null && startPath.length() > 1)
			fileDialog.setFilterPath(startPath);
		
		fileDialog.setText("SukerEditor");
		fileDialog.setText("Select a file");

        openPath = fileDialog.open();
        
        if (openPath == null || openPath.length() < 1)
        	openPath = "";
        
        return openPath;
	}
	
	/*public String getSaveFolderPath()	{
		return savePath;
	}
	
	public String getOpenFolderPath()	{
		return openPath;
	}*/
}
