package AdbUtilPkg;

import java.awt.Container;
import java.io.File;

import javax.swing.JFileChooser;

public class AdbUtil_Dialog {
	private JFileChooser mDirDialog;
	private Container container;
	
	public AdbUtil_Dialog(Container parent, JFileChooser dlg)	{
		container = parent;
		mDirDialog = dlg;
	}
	
	public String getDir() {
		// String directoryInit = ".";

		mDirDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		mDirDialog.showOpenDialog(container);
		// File selFile = mDirDialog.getSelectedFile();

		return mDirDialog.getSelectedFile().getPath();
	}

	public String getFile() {
		mDirDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		mDirDialog.showOpenDialog(container);

		return mDirDialog.getSelectedFile().getPath();
	}
	
	public static boolean existFileOrPath(File validStr)	{
		return validStr.exists();
	}
}
