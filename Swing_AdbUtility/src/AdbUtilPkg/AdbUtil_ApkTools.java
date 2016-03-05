package AdbUtilPkg;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AdbUtil_ApkTools extends JFrame implements ActionListener, ChangeListener {
	private static AdbUtil_Execute mExe;
	private AdbUtil_Dialog mApkDlg;
	private JFileChooser mDirDialog;
	private List<String> readList;
	private List<String> writeList;
	
	private JButton adbRemount;
	private JButton adbStartServer;
	private JButton adbReboot;
	private JButton adbDevices;	
	private JButton adbRemovePackage;
	private JButton adbReInstallPath;
	private JButton adbReInstall;
	private JButton adbUninstall;
	private JTextField edit_removePackage;
	private JTextField edit_reinstall;
	private JTextField edit_uninstall;
	private JCheckBox reInstallCheck;	
	private String strReinstallCheckStatus;
	private JButton adbPackageDownload;
	private String mUserDownloadPath = null;
	
	private static JTextArea mLogText = new JTextArea();
	private static JScrollPane mLogTextScroll;
	private static String mResultAfterProcess;
	
//	private static Thread stepper;
	
	public AdbUtil_ApkTools(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{
		mDirDialog = new JFileChooser(".");
		mApkDlg = new AdbUtil_Dialog(container, mDirDialog);
		createPage(panel);
		mExe = mExecute;
		initPage();
	}
	
	public void __onDestoy()	{
		writeList = AdbUtil_Main_Layout.getWriteFileList();
		
		if (edit_removePackage.getText().length() <= 1)
			edit_removePackage.setText("none");
		AdbUtil_INIFile.writeIniFile("ADBUTIL_RM_APKNAME", edit_removePackage.getText(), writeList);
		
		if (edit_uninstall.getText().length() <= 1)
			edit_uninstall.setText("none");
		AdbUtil_INIFile.writeIniFile("ADBUTIL_UNINSTALL_APKNAME", edit_uninstall.getText(), writeList);
		
		if (edit_reinstall.getText().length() <= 1)
			edit_reinstall.setText("none");
		AdbUtil_INIFile.writeIniFile("ADBUTIL_REINSTALL_PATH", edit_reinstall.getText(), writeList);		
		
		if (mUserDownloadPath.length() <= 1)
			mUserDownloadPath = "none";
		AdbUtil_INIFile.writeIniFile("ADBUTIL_USERDOWNLOAD_APK_PATH", mUserDownloadPath, writeList);
		
		AdbUtil_INIFile.writeIniFile("ADBUTIL_REINSTALLCHECK", strReinstallCheckStatus, writeList);
	}
	
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);

		adbRemount = new JButton("adb remount");
		adbRemount.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		adbRemount.setBounds(layout.BUTTON_FOR_ADB_REMOUNT_START_POS_X,
							 layout.BUTTON_FOR_ADB_REMOUNT_START_POS_Y,
							 layout.BUTTON_FOR_ADB_REMOUNT_WIDTH,
							 layout.BUTTON_FOR_ADB_REMOUNT_HEIGHT);
		adbRemount.addActionListener(this);
		panel.add(adbRemount);
		
		adbStartServer = new JButton("adb start-server");
		adbStartServer.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		adbStartServer.setBounds(layout.BUTTON_FOR_ADB_STARTSERVER_START_POS_X,
								 layout.BUTTON_FOR_ADB_STARTSERVER_START_POS_Y,
								 layout.BUTTON_FOR_ADB_STARTSERVER_WIDTH,
								 layout.BUTTON_FOR_ADB_STARTSERVER_HEIGHT);
		adbStartServer.addActionListener(this);
		panel.add(adbStartServer);
		
		adbReboot = new JButton("Device reboot");
		adbReboot.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		adbReboot.setBounds(layout.BUTTON_FOR_ADB_REBOOT_START_POS_X,
							layout.BUTTON_FOR_ADB_REBOOT_START_POS_Y,
							layout.BUTTON_FOR_ADB_REBOOT_WIDTH,
							layout.BUTTON_FOR_ADB_REBOOT_HEIGHT);
		adbReboot.addActionListener(this);
		panel.add(adbReboot);

		adbDevices = new JButton("connect Device List");
		adbDevices.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		//adbDevices.setBounds(new Rectangle());
		adbDevices.setBounds(layout.BUTTON_FOR_ADB_DEVICES_START_POS_X,
							 layout.BUTTON_FOR_ADB_DEVICES_START_POS_Y,
							 layout.BUTTON_FOR_ADB_DEVICES_WIDTH,
							 layout.BUTTON_FOR_ADB_DEVICES_HEIGHT);
		adbDevices.addActionListener(this);
		panel.add(adbDevices);
		
		// remove
		// --------------------------------------------------------------------------
		JLabel label_remove = new JLabel("package name : ex)LGHome.apk");
		label_remove.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label_remove.setBounds(layout.LABEL2_1_REMOVE_START_POS_X,
				layout.LABEL2_1_REMOVE_START_POS_Y,
				layout.LABEL2_1_REMOVE_WIDTH,
				layout.LABEL2_1_REMOVE_HEIGHT);
		panel.add(label_remove);

		edit_removePackage = new JTextField();
		edit_removePackage.setFont(new Font(null, layout.ITALYIC_STYLE,
				layout.EDIT_FONT_SIZE));
		edit_removePackage.setBounds(layout.EDIT2_1_REMOVE_START_POS_X,
				layout.EDIT2_1_REMOVE_START_POS_Y,
				layout.EDIT2_1_REMOVE_WIDTH,
				layout.EDIT2_1_REMOVE_HEIGHT);
		edit_removePackage.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_removePackage.getText().trim().matches("none")) {
					edit_removePackage.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		panel.add(edit_removePackage);

		adbRemovePackage = new JButton("remove package");
		adbRemovePackage.setFont(new Font(null, layout.BOLD_STYLE,
												layout.BUTTON_FONT_SIZE));
		adbRemovePackage.setBounds(layout.BUTTON_FOR_ADB_REMOVEPACKAGE_START_POS_X,
								   layout.BUTTON_FOR_ADB_REMOVEPACKAGE_START_POS_Y,
								   layout.BUTTON_FOR_ADB_REMOVEPACKAGE_WIDTH,
								   layout.BUTTON_FOR_ADB_REMOVEPACKAGE_HEIGHT);
		adbRemovePackage.addActionListener(this);
		panel.add(adbRemovePackage);

		// reinstall
		// -------------------------------------------------------------------------
		JLabel label_reinstall = new JLabel(
				"Please select file for reinstall .apk");
		label_reinstall.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label_reinstall.setBounds(layout.LABEL2_1_REINSTALL_START_POS_X,
				layout.LABEL2_1_REINSTALL_START_POS_Y,
				layout.LABEL2_1_REINSTALL_WIDTH,
				layout.LABEL2_1_REINSTALL_HEIGHT);
		panel.add(label_reinstall);

		edit_reinstall = new JTextField();
		edit_reinstall.setFont(new Font(null, layout.ITALYIC_STYLE,
				layout.EDIT_FONT_SIZE));
		edit_reinstall.setBounds(layout.EDIT2_1_REINSTALL_START_POS_X,
				layout.EDIT2_1_REINSTALL_START_POS_Y,
				layout.EDIT2_1_REINSTALL_WIDTH,
				layout.EDIT2_1_REINSTALL_HEIGHT);
		edit_reinstall.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_reinstall.getText().trim().matches("none")) {
					edit_reinstall.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		panel.add(edit_reinstall);

		adbReInstallPath = new JButton("...");
		adbReInstallPath.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		adbReInstallPath.setBounds(
				layout.BUTTON_FOR_ADB_REINSTALLPATH_START_POS_X,
				layout.BUTTON_FOR_ADB_REINSTALLPATH_START_POS_Y,
				layout.BUTTON_FOR_ADB_REINSTALLPATH_WIDTH,
				layout.BUTTON_FOR_ADB_REINSTALLPATH_HEIGHT);
		adbReInstallPath.addActionListener(this);
		panel.add(adbReInstallPath);

		adbReInstall = new JButton("install execute");
		adbReInstall.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		adbReInstall.setBounds(
				layout.BUTTON_FOR_ADB_REINSTALL_START_POS_X,
				layout.BUTTON_FOR_ADB_REINSTALL_START_POS_Y,
				layout.BUTTON_FOR_ADB_REINSTALL_WIDTH,
				layout.BUTTON_FOR_ADB_REINSTALL_HEIGHT);
		adbReInstall.addActionListener(this);
		panel.add(adbReInstall);

		reInstallCheck = new JCheckBox("reinstall");
		reInstallCheck.setSize(15, 15);
		reInstallCheck.setFont(new Font(null, layout.BOLD_STYLE, layout.CHECK_FONT_SIZE));
		reInstallCheck.setBounds(layout.CHECKBOX_FOR_ADB_REINSTALL_START_POS_X,
								 layout.CHECKBOX_FOR_ADB_REINSTALL_START_POS_Y,
								 layout.CHECKBOX_FOR_ADB_REINSTALL_WIDTH,
								 layout.CHECKBOX_FOR_ADB_REINSTALL_HEIGHT);
		panel.add(reInstallCheck);
		reInstallCheck.addActionListener(this);
		// uninstall
		// --------------------------------------------------------------------------
		JLabel label_uninstall = new JLabel("uninstall : ex)com.lge.launcher");
		label_uninstall.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label_uninstall.setBounds(layout.LABEL2_1_UNINSTALL_START_POS_X,
				layout.LABEL2_1_UNINSTALL_START_POS_Y,
				layout.LABEL2_1_UNINSTALL_WIDTH,
				layout.LABEL2_1_UNINSTALL_HEIGHT);
		panel.add(label_uninstall);

		edit_uninstall = new JTextField();
		edit_uninstall.setFont(new Font(null, layout.ITALYIC_STYLE,
				layout.EDIT_FONT_SIZE));
		edit_uninstall.setBounds(layout.EDIT2_2_UNINSTALL_START_POS_X,
				layout.EDIT2_2_UNINSTALL_START_POS_Y,
				layout.EDIT2_2_UNINSTALL_WIDTH,
				layout.EDIT2_2_UNINSTALL_HEIGHT);
		edit_uninstall.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_uninstall.getText().trim().matches("none")) {
					edit_uninstall.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		panel.add(edit_uninstall);

		adbUninstall = new JButton("Uninstall");
		adbUninstall.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		adbUninstall.setBounds(
				layout.BUTTON_FOR_ADB_UNINSTALL_START_POS_X,
				layout.BUTTON_FOR_ADB_UNINSTALL_START_POS_Y,
				layout.BUTTON_FOR_ADB_UNINSTALL_WIDTH,
				layout.BUTTON_FOR_ADB_UNINSTALL_HEIGHT);
		adbUninstall.addActionListener(this);
		panel.add(adbUninstall);
		//------------------------------------------------------------------------------------
		mLogText.setLineWrap(true);
		mLogText.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		mLogText.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		mLogTextScroll = new JScrollPane(mLogText);
		mLogTextScroll.setWheelScrollingEnabled(true);
		mLogTextScroll.setBounds(layout.LOG4_APK_TOOL_START_POS_X,
								 layout.LOG4_APK_TOOL_START_POS_Y,
								 layout.LOG4_APK_TOOL_WIDTH,
								 layout.LOG4_APK_TOOL_HEIGHT);
		panel.add(mLogTextScroll);		
		//------------------------------------------------------------------------------------
		
		/*adbPackageDownload = new JButton("User's .apk download ...");
		adbPackageDownload.setFont(new Font(null, layout.BOLD_STYLE,
												  layout.BUTTON_FONT_SIZE));
		adbPackageDownload.setBounds(layout.BUTTON_FOR_ADB_DOWNPACK_START_POS_X,
								  	 layout.BUTTON_FOR_ADB_DOWNPACK_START_POS_Y,
								  	 layout.BUTTON_FOR_ADB_DOWNPACK_WIDTH,
								  	 layout.BUTTON_FOR_ADB_DOWNPACK_HEIGHT);
		adbPackageDownload.addActionListener(this);
		panel.add(adbPackageDownload);*/
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AdbUtil_Main_Layout.getReadFileList();
	
		if (readList != null){
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADBUTIL_RM_APKNAME");
			edit_removePackage.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADBUTIL_UNINSTALL_APKNAME");
			edit_uninstall.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADBUTIL_REINSTALL_PATH");
			edit_reinstall.setText(resultStr);
			
			mUserDownloadPath = AdbUtil_INIFile.readIniFile(readList, "ADBUTIL_USERDOWNLOAD_APK_PATH");
			
			strReinstallCheckStatus = AdbUtil_INIFile.readIniFile(readList, "ADBUTIL_REINSTALLCHECK");
			if (strReinstallCheckStatus == null)	{
				reInstallCheck.setSelected(true);
			}	else	{
				if (strReinstallCheckStatus.equals("true"))
					reInstallCheck.setSelected(true);
				else
					reInstallCheck.setSelected(false);
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.adbRemount) {
			String text = "cmd.exe~~/K~~start~~perl~~script\\adbCommand.pl~~RestartServer";				
			String[] cmdList = text.split("~~");
			try {
				mExe.runProcessSimple(cmdList);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		} else if (e.getSource() == this.adbReInstallPath) {
			edit_reinstall.setText(mApkDlg.getFile());
		} else if (e.getSource() == this.reInstallCheck)	{ 
			if (reInstallCheck.isSelected()) {
				strReinstallCheckStatus = "true";
			} else {
				strReinstallCheckStatus = "false";
			}
		} else	{
			String[] cmdList = null;
			String text = null;
			
			if (e.getSource() == this.adbReboot)	{
					logTextClear();
					text = "cmd.exe~~/C~~adb~~reboot";
			}	else if (e.getSource() == this.adbStartServer)	{
					logTextClear();
					text = "cmd.exe~~/C~~adb~~start-server";
			}	else if (e.getSource() == this.adbDevices)	{
					logTextClear();
					text = "cmd.exe~~/C~~adb~~devices";
			}	else if (e.getSource() == this.adbRemovePackage)	{
					logTextClear();
					text = "cmd.exe~~/C~~adb~~shell~~rm~~/system/app/"
								+ this.edit_removePackage.getText().trim();
			}	else if (e.getSource() == this.adbUninstall)	{
					logTextClear();
					text = "cmd.exe~~/C~~adb~~uninstall~~"
								+ this.edit_uninstall.getText().trim();
			} 	else if (e.getSource() == this.adbReInstall)	{
					logTextClear();
					if (strReinstallCheckStatus.equals("true"))	{
						text = "cmd.exe~~/C~~adb~~install~~-r~~"
								+ this.edit_reinstall.getText().trim();
					}	else	{
						text = "cmd.exe~~/C~~adb~~install~~"
							+ this.edit_reinstall.getText().trim();
					}
			}	else if (e.getSource() == this.adbPackageDownload)	{
					logTextClear();
					if (mUserDownloadPath.equals("none"))	{
						mUserDownloadPath = mApkDlg.getFile();
					}					
					if (mUserDownloadPath != null) {
						text = "cmd.exe~~/C~~start~~adb~~install~~"	+ mUserDownloadPath.trim();												
					}
			}	
			
			if (text != null)	{
				cmdList = text.split("~~");
			}
			
			if (cmdList != null)	{
				try {
					//createStepper();
					mExe.runProcess(cmdList, this.getClass().getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static void callbackDoneExecuteProcess() {
		mResultAfterProcess = mExe.getResultMessages();
		mLogText.setText(mResultAfterProcess);
		mLogText.repaint();
	}	
	public static void callbackFailExecuteProcess() {		
		mLogText.setText("failed!!");
		mLogText.repaint();
	}
	private void logTextClear()	{
		mLogText.setText(null);
		mLogText.repaint();
	}
//	private static void createStepper()	{
//		AdbUtil_Main_Layout.getProgressBarComp().setVisible(true);
//	    stepper = new BarThread(AdbUtil_Main_Layout.getProgressBarComp());
//	    stepper.start();
//	}
}
