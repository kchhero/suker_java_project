package sukerPkg;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

public class AndroSuker_TabItem_RamDumpParser implements AndroSuker_Definition {
	private static AndroSuker_Execute 	mExe;
	private static Composite 			RDP_composite;	
	
	private List<String> 	readList;
	private List<String> 	writeList;
	private Text 			QPSTSaharaPath;
	private Text 			RDPPath;
	private Text 			DumpFilesPath;
	private Text 			VmlinuxPath;
	
	private Button			btnQPSTSaharaBrowser;
	private Button			btnQPSTSaharaCopy;
	private Button			btnRamDumpParserBrowser;
	private Button			btnRamDumpFilesBrowser;
	private Button			btnRamDumpParserVmlinuxBrowser;
	private Button			btnOpenDumpFolder;
	private Button			btnRun;
	private Button			btnGoT32;
	
	private final int		RAMDUMPPARSER_BAT_BROWSER = 800;
	private final int		RAMDUMPPARSER_DUMPFILES_BROWSER = 801;
	private final int		RAMDUMPPARSER_VMLINUX_BROWSER = 802;
	private final int		RAMDUMPPARSER_OPEN_FOLDER = 803;
	private final int		RAMDUMPPARSER_RUN = 804;
	private final int		RAMDUMPPARSER_QPST_SAHARA_BROWSER = 805;
	private final int		RAMDUMPPARSER_QPST_SAHARA_COPY = 806;
	private final int		RAMDUMPPARSER_GO_T32 = 807;
	
	public AndroSuker_TabItem_RamDumpParser(Shell shell, TabFolder tabFolder, AndroSuker_Execute mExecute)	{
		createPage(tabFolder);
		mExe = mExecute;
		initPage();
	}

	public static Composite getInstance()	{
		return RDP_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	public void __onFinally()	{	
		writeList = AndroSuker_Main_Layout.getWriteFileList();
				
		AndroSuker_INIFile.writeIniFile("RAMDUMPPARSER_QPST_SAHARA_PATH", QPSTSaharaPath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("RAMDUMPPARSER_BAT_PATH", RDPPath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("RAMDUMPPARSER_DUMPFILES_PATH",	DumpFilesPath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("RAMDUMPPARSER_VMLINUX_PATH", VmlinuxPath.getText(), writeList);
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "RAMDUMPPARSER_QPST_SAHARA_PATH");			
			if (resultStr.length() > 0)
				QPSTSaharaPath.setText(resultStr);			
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "RAMDUMPPARSER_BAT_PATH");			
			if (resultStr.length() > 0)
				RDPPath.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "RAMDUMPPARSER_DUMPFILES_PATH");			
			if (resultStr.length() > 0)
				DumpFilesPath.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "RAMDUMPPARSER_VMLINUX_PATH");
			if (resultStr.length() > 0)
				VmlinuxPath.setText(resultStr);
		}
	}
	
	@SuppressWarnings("unused")
	private void createPage(TabFolder tabFolder)	{		
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		RDP_composite = new Composite(tabFolder, SWT.FILL);
		RDP_composite.setLayout(new GridLayout(2, false));
		
		new Label(RDP_composite, SWT.NONE);
		new Label(RDP_composite, SWT.NONE);
		
		//--------------------------------######### New RamDumpParse Additions ########--------------------------------
		//layout2		
		Label lblNewLabel_5 = new Label(RDP_composite, SWT.NONE);
		lblNewLabel_5.setText("QPST Sahara Location");
		new Label(RDP_composite, SWT.NONE);
		
		QPSTSaharaPath = new Text(RDP_composite, SWT.BORDER);
		QPSTSaharaPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnQPSTSaharaBrowser = new Button(RDP_composite, SWT.NONE);
		GridData gd_button23 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button23.widthHint = 50;
		btnQPSTSaharaBrowser.setLayoutData(gd_button23);
		btnQPSTSaharaBrowser.setText("...");
		btnQPSTSaharaBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_QPST_SAHARA_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		new Label(RDP_composite, SWT.NONE);

		btnQPSTSaharaCopy = new Button(RDP_composite, SWT.NONE);
		btnQPSTSaharaCopy.setText("Copy to MyDump Loc.");
		btnQPSTSaharaCopy.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_QPST_SAHARA_COPY);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		//--------------------------------#########	RamDump Parser location ##########--------------------------------
		Label lblNewLabel = new Label(RDP_composite, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_lblNewLabel.heightHint = 21;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("RamDump Parser Location");
		new Label(RDP_composite, SWT.NONE);
		
		RDPPath = new Text(RDP_composite, SWT.BORDER);
		RDPPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnRamDumpParserBrowser = new Button(RDP_composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton.widthHint = 50;
		btnRamDumpParserBrowser.setLayoutData(gd_btnNewButton);
		btnRamDumpParserBrowser.setText("...");
		btnRamDumpParserBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_BAT_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		Label lblNewLabel_2 = new Label(RDP_composite, SWT.NONE);
		new Label(RDP_composite, SWT.NONE);
		
		//--------------------------------#########	Dump Files location ##########--------------------------------
		Label lblNewLabel_4 = new Label(RDP_composite, SWT.NONE);
		GridData gd_lblNewLabel_4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_4.heightHint = 19;
		lblNewLabel_4.setLayoutData(gd_lblNewLabel_4);
		lblNewLabel_4.setText("Dump Files Location");
		new Label(RDP_composite, SWT.NONE);
		
		DumpFilesPath = new Text(RDP_composite, SWT.BORDER);
		DumpFilesPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnRamDumpFilesBrowser = new Button(RDP_composite, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 50;
		btnRamDumpFilesBrowser.setLayoutData(gd_button);
		btnRamDumpFilesBrowser.setText("...");
		btnRamDumpFilesBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_DUMPFILES_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		Label lblNewLabel_3 = new Label(RDP_composite, SWT.NONE);
		new Label(RDP_composite, SWT.NONE);
		
		//--------------------------------#########	Vmlinux images location ##########--------------------------------
		Label lblNewLabel_1 = new Label(RDP_composite, SWT.NONE);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_lblNewLabel_1.heightHint = 19;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText("Vmlinux Location");
		new Label(RDP_composite, SWT.NONE);
		
		VmlinuxPath = new Text(RDP_composite, SWT.BORDER);
		VmlinuxPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnRamDumpParserVmlinuxBrowser = new Button(RDP_composite, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 50;
		btnRamDumpParserVmlinuxBrowser.setLayoutData(gd_btnNewButton_1);
		btnRamDumpParserVmlinuxBrowser.setText("...");
		btnRamDumpParserVmlinuxBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_VMLINUX_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		new Label(RDP_composite, SWT.NONE);
		
		//--------------------------------######### Open Folder & Run ########--------------------------------
		btnOpenDumpFolder = new Button(RDP_composite, SWT.NONE);
		btnOpenDumpFolder.setText("Open Dump Folder");
		btnOpenDumpFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_OPEN_FOLDER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnRun = new Button(RDP_composite, SWT.NONE);
		GridData gd_btnNewButton_3 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_3.heightHint = 50;
		gd_btnNewButton_3.widthHint = 420;
		btnRun.setLayoutData(gd_btnNewButton_3);
		btnRun.setText("Run");
		btnRun.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_RUN);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnGoT32 = new Button(RDP_composite, SWT.NONE);
		GridData gd_btnNewButton_4 = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_4.heightHint = 50;
		gd_btnNewButton_4.widthHint = 50;
		btnGoT32.setLayoutData(gd_btnNewButton_4);
		btnGoT32.setText("Go T32");
		btnGoT32.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(RAMDUMPPARSER_GO_T32);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//new Label(RDP_composite, SWT.NONE);
		
		//RDP_composite2 = RDP_composite;		
	}

	
	public void _actionPerformed(int action) {
		if (RAMDUMPPARSER_BAT_BROWSER == action) {
			String temp = null;
			AndroSuker_DirDialog ramDumpParserDlg;
			ramDumpParserDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_FILE, FILE_TYPE_NONE);
			temp = ramDumpParserDlg.getFilePath();
			if (temp != null)
				RDPPath.setText(temp);
		} else if (RAMDUMPPARSER_DUMPFILES_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog ramDumpParserDlg;
			ramDumpParserDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_DIR, FILE_TYPE_NONE);
			temp = ramDumpParserDlg.getDir();
			if (temp != null)
				DumpFilesPath.setText(temp);
		} else if (RAMDUMPPARSER_VMLINUX_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog ramDumpParserDlg;
			ramDumpParserDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_FILE, FILE_TYPE_NONE);
			temp = ramDumpParserDlg.getFilePath();
			if (temp != null)
				VmlinuxPath.setText(temp);
		} else if (RAMDUMPPARSER_OPEN_FOLDER == action)	{
			String	dirName = DumpFilesPath.getText();
			
		    File file = new File(dirName); // assuming that path is not empty
		    
		    if (AndroSuker_DirDialog.existFileOrPath(file))	{  
		    	try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		    }  else	{
		    	MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
		        mb.setText("Warning");
		        mb.setMessage("no exist directory path");
		        mb.open();
		    }
		} else if (RAMDUMPPARSER_RUN == action)	{
			String path1 = "";
			String path2 = "";
			String path3 = "";
			String path4 = "";
			
			if (RDPPath.getText().trim().length() > 0){
				path1 = RDPPath.getText();
				path1 = path1.replace('\\','/');
			}
			if (DumpFilesPath.getText().trim().length() > 0){
				path2 = DumpFilesPath.getText();
				path2 = path2.replace('\\','/');
			}
			if (VmlinuxPath.getText().trim().length() > 0){
				path3 = VmlinuxPath.getText();
				path3 = path3.replace('\\','/');
			}
			if (path1.length() < 1 || path2.length() < 1 || path3.length() < 1)
				return ;
		    
			int lastIndex = path1.lastIndexOf("/"); 
			path4 = path1.substring(lastIndex+1);
			path1 = path1.substring(0, lastIndex);
			if (path4.length() < 1)	{
				MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
		        mb.setText("Error");
		        mb.setMessage("not match ramdump parser batch file name");
		        mb.open();			
				return ;
			}
			
		    String cmd = "cmd.exe~~/K~~start~~perl~~script/ramdumpParserEasy.pl~~"				
				+ path1
				+ "~~"
				+ path2
				+ "~~"
				+ path3
		    	+ "~~"
		    	+ path4;
		    
			String[] cmdList = cmd.split("~~");
			try {
				mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if (RAMDUMPPARSER_QPST_SAHARA_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog ramDumpParserDlg;
			ramDumpParserDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_DIR, FILE_TYPE_NONE);
			temp = ramDumpParserDlg.getDir();
			if (temp != null)
				QPSTSaharaPath.setText(temp);
		} else if (RAMDUMPPARSER_QPST_SAHARA_COPY == action)	{
			String	srcdirName = QPSTSaharaPath.getText();			
		    File copyDirectory = new File(srcdirName); // assuming that path is not empty
		    
		    String	tardirName = DumpFilesPath.getText();			
		    File targetDirectory = new File(tardirName); // assuming that path is not empty
		    
		    folderCopyAllFiles(copyDirectory, targetDirectory);
		} else if (RAMDUMPPARSER_GO_T32 == action)	{
			String path1 = "";
			String path2 = "";
			
			if (RDPPath.getText().trim().length() > 0){
				path1 = RDPPath.getText();
				path1 = path1.replace('\\','/');
			}
			
			int lastIndex = path1.lastIndexOf("/");			
			path1 = path1.substring(0, lastIndex);
			path2 = "launch_t32.bat";
			
			if (path1.length() < 1)
				return ;
			
			String cmd = "cmd.exe~~/C~~start~~perl~~script/ramdumpParserEasy.pl~~" 
					+ path1
					+ "~~"
					+ path2;
			
			String[] cmdList = cmd.split("~~");
			try {
				mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void folderCopyAllFiles(File copyDirectory, File targetDirectory) {
		if (copyDirectory.isDirectory()) {
            if(!targetDirectory.exists()){
                targetDirectory.mkdir();
            }

            btnQPSTSaharaCopy.setText("Copying...");
            btnQPSTSaharaCopy.setEnabled(false);
            String[] children = copyDirectory.list();
            for (int i=0; i<children.length; i++) {
            	folderCopyAllFiles(new File(copyDirectory, children[i]), 
                        new File(targetDirectory, children[i]));
            }
            AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", "Good!", SWT.OK);
            btnQPSTSaharaCopy.setText("Copy to MyDump Loc.");
            btnQPSTSaharaCopy.setEnabled(true);
        } else	{
			try {
	            FileInputStream fis = new FileInputStream(copyDirectory);
	
				BufferedInputStream bis = new BufferedInputStream(fis, 1024*10);
	            
	            FileOutputStream fos = new FileOutputStream(targetDirectory);
	
				BufferedOutputStream bos = new BufferedOutputStream(fos, 1024*10);
	            
	            int len= 0; byte[] buf = new byte[1024*10];
	            
	            while((len = bis.read(buf)) != -1) {
	                 bos.write(buf, 0, len);
	                 bos.flush();
	            }
	            bis.close();
	            bos.close();
	         
	        } catch(FileNotFoundException fn) {
	            fn.printStackTrace();
	            
	        } catch(IOException ie) {
	            ie.printStackTrace();
	        }
        }
	}
}

