package sukerPkg;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AndroSuker_DirDialog implements AndroSuker_Definition {
	DirectoryDialog mDirDialog;
	FileDialog		mFileDialog;
	String fileFilterPath = "C:\\";
    String selectedDir;
    String selectedFilePath;
    Label label;
    
	public AndroSuker_DirDialog(Shell shell, int mode, int type)	{
		selectedDir = null;
		label = new Label(shell, SWT.BORDER | SWT.WRAP);
	    label.setText("Select a dir/file by clicking the buttons below.");
	    
		if (mode == MODE_DIR)	{		    
		    mDirDialog = new DirectoryDialog(shell);
			mDirDialog.setFilterPath(selectedDir);
			mDirDialog.setMessage("Please select a directory and click OK");
	        
	        String dir = mDirDialog.open();
	        if(dir != null) {
	          label.setText("Selected dir: " + dir);
	          selectedDir = dir;
	        }
		} else {        
			mFileDialog = new FileDialog(shell, SWT.OPEN);
			//mFileDialog.setText("Open");
			mFileDialog.setFilterPath(fileFilterPath);
			
			String[] filterExt;
			if (type == FILE_TYPE_EXE)
				filterExt = new String[]{"*.exe", "*.*"};
			else //type == FILE_TYPE_NONE
				filterExt = new String[]{"*.*"};
	        mFileDialog.setFilterExtensions(filterExt);
	        
	        String selected = mFileDialog.open();
	        if (selected != null)
	        	selectedFilePath = selected;
		}
	}
	
	public String getDir() {
		return selectedDir;
	}
	public String getFilePath() {
		return selectedFilePath;
	}
//	public String getFile() {
//		mDirDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
//		mDirDialog.showOpenDialog(container);
//
//		return mDirDialog.getSelectedFile().getPath();
//	}
	
	public static boolean existFileOrPath(File validStr)	{
		return validStr.exists();
	}
}
