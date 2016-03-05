package AdbUtilPkg;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class AdbUtil_LogCat extends JFrame implements ActionListener, ChangeListener{
	private static AdbUtil_Execute mExe;
	private AdbUtil_Dialog mLogCatDlg;
	private JFileChooser mDirDialog;
	private List<String> readList;
	private List<String> writeList;
	
	private JButton dirBtn;
	private JButton dirOpenBtn;
	private JButton initBtn;
	private JButton logcatRun_btn;

	private JTextField edit_filteroption;
	private JTextField edit_filterrange;
	private JTextField edit_filtername;
	private JTextField edit_directoryname;
	private JTextField edit_filename;
	
	private ButtonGroup radioGroup;
	private JRadioButton radioBtn_Main;
	private JRadioButton radioBtn_System;
	private JRadioButton radioBtn_Radio;
	private JRadioButton radioBtn_Events;
	private String logType = "";
	
	private JCheckBox checkbox_SilentOtherLogs;
	private JCheckBox checkbox_displayLog;

	private String strSilentOtherLogs;
	private String strStdoutValue;

//	static AdbUtil_LogCat_LogTextFrame	subLog;
	
	public AdbUtil_LogCat(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{
		mDirDialog = new JFileChooser(".");
		mLogCatDlg = new AdbUtil_Dialog(container, mDirDialog);
		
//		subLog = new AdbUtil_LogCat_LogTextFrame();
		
		createPage(panel);
		mExe = mExecute;
		initPage();
	}
/*	public static AdbUtil_LogCat_LogTextFrame getSubLogFrame()	{		
		return subLog;
	}
*/
	public void __onDestoy()	{
		writeList = AdbUtil_Main_Layout.getWriteFileList();
		
		if (edit_directoryname.getText().length() <= 1)
			edit_directoryname.setText("none");
		AdbUtil_INIFile.writeIniFile("LOGCAT_DIR", edit_directoryname.getText(), writeList);
				
		if (edit_filename.getText().length() <= 1)
			edit_filename.setText("none");
		AdbUtil_INIFile.writeIniFile("LOGCAT_FILE", edit_filename.getText(), writeList);
		
		if (edit_filteroption.getText().length() <= 1)
			edit_filteroption.setText("-v time");
		AdbUtil_INIFile.writeIniFile("LOGCAT_FILTEROPTION", edit_filteroption.getText(), writeList);
		
		if (edit_filterrange.getText().length() <= 1)
			edit_filterrange.setText("I");
		AdbUtil_INIFile.writeIniFile("LOGCAT_FILTERRANGE", edit_filterrange.getText(), writeList);
		
		if (edit_filtername.getText().length() <= 1)
			edit_filtername.setText("Your Apk Name");
		AdbUtil_INIFile.writeIniFile("LOGCAT_FILTERNAME", edit_filtername.getText(), writeList);
				
		AdbUtil_INIFile.writeIniFile("LOGCAT_SILENTOTHERLOGS", strSilentOtherLogs, writeList);
		AdbUtil_INIFile.writeIniFile("LOGCAT_SIMIL", strStdoutValue, writeList);
		AdbUtil_INIFile.writeIniFile("LOGCAT_TYPE", logType, writeList);		
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AdbUtil_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_DIR");
			edit_directoryname.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_FILE");
			edit_filename.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_FILTEROPTION");
			edit_filteroption.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_FILTERRANGE");
			edit_filterrange.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_FILTERNAME");
			edit_filtername.setText(resultStr);
			
			strSilentOtherLogs = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_SILENTOTHERLOGS");
			if (strSilentOtherLogs.equals("true"))	{
				checkbox_SilentOtherLogs.setSelected(true);
				edit_filtername.setEditable(true);
			}
			else	{
				checkbox_SilentOtherLogs.setSelected(false);
				edit_filtername.setEditable(false);
			}
			
			strStdoutValue = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_SIMIL");
			if (strStdoutValue.equals("true"))
				checkbox_displayLog.setSelected(true);
			else
				checkbox_displayLog.setSelected(false);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "LOGCAT_TYPE");
			logType = resultStr;
			if (resultStr.equals("main"))	{				
				radioBtn_Main.setSelected(true);
			}
			if (resultStr.equals("system"))	{				
				radioBtn_System.setSelected(true);
			}			
			else if (resultStr.equals("-b radio"))	{
				radioBtn_Radio.setSelected(true);
			}
			else if (resultStr.equals("-b events"))	{
				radioBtn_Events.setSelected(true);
			}
			
		}	else	{
			initTabValues();
		}
	}
	
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);

		// label 1 & edit 1 ---------------------------------
		JLabel label1 = new JLabel("Filter Option :");
		label1.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label1.setBounds(layout.LABEL1_START_POS_X,
				layout.LABEL1_START_POS_Y, layout.LABEL1_WIDTH,
				layout.LABEL1_HEIGHT);
		panel.add(label1);

		edit_filteroption = new JTextField();
		edit_filteroption.setFont(new Font(null, layout.ITALYIC_STYLE,
				layout.EDIT_FONT_SIZE));
		edit_filteroption.setBounds(layout.EDIT1_START_POS_X,
				layout.EDIT1_START_POS_Y, layout.EDIT1_WIDTH,
				layout.EDIT1_HEIGHT);
		panel.add(edit_filteroption);

		// label 1-1 & edit 1-1 ---------------------------------
		JLabel label1_1 = new JLabel("Filter Range : V,D,I,W,E,F,S");
		label1_1.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label1_1.setBounds(layout.LABEL1_1_START_POS_X,
				layout.LABEL1_1_START_POS_Y, layout.LABEL1_1_WIDTH,
				layout.LABEL1_1_HEIGHT);
		panel.add(label1_1);

		edit_filterrange = new JTextField();
		edit_filterrange.setFont(new Font(null, layout.BOLD_STYLE,
									layout.EDIT_FONT_SIZE));
		edit_filterrange.setBounds(layout.EDIT1_1_START_POS_X,
								   layout.EDIT1_1_START_POS_Y, layout.EDIT1_1_WIDTH,
								   layout.EDIT1_1_HEIGHT);
		panel.add(edit_filterrange);

		// label 1-2 & edit 1-2 ---------------------------------
		JLabel label1_2 = new JLabel("Filter name : ex)LGHome, CameraApp");
		label1_2.setFont(new Font(null, layout.BOLD_STYLE,
				layout.TITLE_FONT_SIZE));
		label1_2.setBounds(layout.LABEL1_2_START_POS_X,
				layout.LABEL1_2_START_POS_Y, layout.LABEL1_2_WIDTH,
				layout.LABEL1_2_HEIGHT);
		panel.add(label1_2);

		edit_filtername = new JTextField();
		edit_filtername.setFont(new Font(null, layout.ITALYIC_STYLE,
											   layout.EDIT_FONT_SIZE));
		edit_filtername.setBounds(layout.EDIT1_2_START_POS_X,
								  layout.EDIT1_2_START_POS_Y,
								  layout.EDIT1_2_WIDTH,
								  layout.EDIT1_2_HEIGHT);
		panel.add(edit_filtername);
		edit_filtername.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_filtername.getText().trim().matches("Your Apk Name")) {
					edit_filtername.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});

		// label 2 & edit 2 --------------------------------
		JLabel label2 = new JLabel(
				"Directory path for save the log file : (none => save to the <Current Folder>");
		label2.setFont(new Font(null, layout.BOLD_STYLE, layout.TITLE_FONT_SIZE));
		label2.setBounds(layout.LABEL2_START_POS_X, layout.LABEL2_START_POS_Y, layout.LABEL2_WIDTH,
						 layout.LABEL2_HEIGHT);
		panel.add(label2);

		edit_directoryname = new JTextField();
		edit_directoryname.setFont(new Font(null, layout.ITALYIC_STYLE,
				layout.EDIT_FONT_SIZE));
		edit_directoryname.setBounds(layout.EDIT2_START_POS_X,
				layout.EDIT2_START_POS_Y, layout.EDIT2_WIDTH,
				layout.EDIT2_HEIGHT);
		panel.add(edit_directoryname);
		edit_directoryname.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_directoryname.getText().trim().matches("none"))
					edit_directoryname.setText("");
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		// --------------------------------------------------

		// label 3 & edit 3 ---------------------------------
		JLabel label3 = new JLabel("Log File name : (none => <Current Date>.txt)");
		label3.setFont(new Font(null, layout.BOLD_STYLE, layout.TITLE_FONT_SIZE));
		label3.setBounds(layout.LABEL3_START_POS_X,	layout.LABEL3_START_POS_Y, layout.LABEL3_WIDTH,
					     layout.LABEL3_HEIGHT);
		panel.add(label3);

		edit_filename = new JTextField();
		edit_filename.setFont(new Font(null, layout.ITALYIC_STYLE, layout.EDIT_FONT_SIZE));
		edit_filename.setBounds(layout.EDIT3_START_POS_X, layout.EDIT3_START_POS_Y, layout.EDIT3_WIDTH,
								layout.EDIT3_HEIGHT);
		panel.add(edit_filename);
		edit_filename.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_filename.getText().trim().matches("none"))
					edit_filename.setText("");
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		// --------------------------------------------------

		// Radio Buttons  -----------------------------------
		radioBtn_Main = new JRadioButton("Main <Default>");
		radioBtn_Main.setBounds(layout.RADIO_BTN1_START_POS_X,
								layout.RADIO_BTN1_START_POS_Y,
								layout.RADIO_BTN1_WIDTH,
								layout.RADIO_BTN1_HEIGHT);
		radioBtn_Main.setFocusable(false);
		radioBtn_Main.addActionListener(this);
				
		radioBtn_System = new JRadioButton("System log");
		radioBtn_System.setBounds(layout.RADIO_BTN2_START_POS_X,
								  layout.RADIO_BTN2_START_POS_Y,
								  layout.RADIO_BTN2_WIDTH,
								  layout.RADIO_BTN2_HEIGHT);
		radioBtn_System.setFocusable(false);
		radioBtn_System.addActionListener(this);
		
		radioBtn_Radio = new JRadioButton("Radio log");
		radioBtn_Radio.setBounds(layout.RADIO_BTN3_START_POS_X,
								 layout.RADIO_BTN3_START_POS_Y,
								 layout.RADIO_BTN3_WIDTH,
								 layout.RADIO_BTN3_HEIGHT);
		radioBtn_Radio.setFocusable(false);
		radioBtn_Radio.addActionListener(this);
		
		radioBtn_Events = new JRadioButton("Event log");
		radioBtn_Events.setBounds(layout.RADIO_BTN4_START_POS_X,
								  layout.RADIO_BTN4_START_POS_Y,
								  layout.RADIO_BTN4_WIDTH,
								  layout.RADIO_BTN4_HEIGHT);
		radioBtn_Events.setFocusable(false);
		radioBtn_Events.addActionListener(this);
		
		// Radio Buttons Group  -----------------------------
		radioGroup = new ButtonGroup();
		radioGroup.add(radioBtn_Main);
		radioGroup.add(radioBtn_System);
		radioGroup.add(radioBtn_Radio);
		radioGroup.add(radioBtn_Events);
	    
	    panel.add(radioBtn_Main);
	    panel.add(radioBtn_System);
	    panel.add(radioBtn_Radio);
	    panel.add(radioBtn_Events);
	    
		// Check Box ----------------------------------------
		checkbox_SilentOtherLogs = new JCheckBox("Silent other logs");
		checkbox_SilentOtherLogs.setFont(new Font(null, layout.BOLD_STYLE,
				layout.CHECK_FONT_SIZE));
		checkbox_SilentOtherLogs.setSize(20, 20);
		checkbox_SilentOtherLogs.setBounds(layout.CHECKBOX1_START_POS_X,
				layout.CHECKBOX1_START_POS_Y, layout.CHECKBOX1_WIDTH,
				layout.CHECKBOX1_HEIGHT);
		panel.add(checkbox_SilentOtherLogs);
		checkbox_SilentOtherLogs.addActionListener(this);

		// Check Box ----------------------------------------
		checkbox_displayLog = new JCheckBox("Simultaneously with file saveing");
		checkbox_displayLog.setSize(15, 15);
		checkbox_displayLog.setFont(new Font(null, layout.BOLD_STYLE,
				layout.CHECK_FONT_SIZE));
		checkbox_displayLog.setBounds(layout.CHECKBOX2_START_POS_X,
				layout.CHECKBOX2_START_POS_Y, layout.CHECKBOX2_WIDTH,
				layout.CHECKBOX2_HEIGHT);
		panel.add(checkbox_displayLog);
		checkbox_displayLog.addActionListener(this);

		// Buttons2 ------------------------------------------
		dirBtn = new JButton("...");
		dirBtn.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		dirBtn.setBounds(layout.BUTTON_FOR_DIR_PATH_START_POS_X,
						 layout.BUTTON_FOR_DIR_PATH_START_POS_Y,
						 layout.BUTTON_FOR_DIR_PATH_WIDTH,
						 layout.BUTTON_FOR_DIR_PATH_HEIGHT);
		dirBtn.addActionListener(this);
		panel.add(dirBtn);
		
		dirOpenBtn = new JButton("open folder");
		dirOpenBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		dirOpenBtn.setBounds(layout.BUTTON_FOR_DIR_OPEN_START_POS_X,
							 layout.BUTTON_FOR_DIR_OPEN_START_POS_Y,
							 layout.BUTTON_FOR_DIR_OPEN_WIDTH,
							 layout.BUTTON_FOR_DIR_OPEN_HEIGHT);
		dirOpenBtn.addActionListener(this);
		panel.add(dirOpenBtn);
		
		initBtn = new JButton("initialize");
		initBtn.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		initBtn.setBounds(layout.BUTTON_FOR_INIT_START_POS_X,
						  layout.BUTTON_FOR_INIT_START_POS_Y,
						  layout.BUTTON_FOR_INIT_WIDTH,
						  layout.BUTTON_FOR_INIT_HEIGHT);
		initBtn.addActionListener(this);
		panel.add(initBtn);

		logcatRun_btn = new JButton("Run");
		logcatRun_btn.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		logcatRun_btn.setBounds(layout.BUTTON_FOR_RUN_START_POS_X,
							    layout.BUTTON_FOR_RUN_START_POS_Y,
							    layout.BUTTON_FOR_RUN_WIDTH,
							    layout.BUTTON_FOR_RUN_HEIGHT);
		logcatRun_btn.addActionListener(this);
		panel.add(logcatRun_btn);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.logcatRun_btn) {
			int nOptionSplitCnt = 0;
			String regex = "~~";
			String[] tempList;
			String filterName = "none";
			String dirPath = "none";
			String fileName = "none";
			String filterRange = "V";
			
			tempList = edit_filteroption.getText().trim().split(" ");
			nOptionSplitCnt = tempList.length;

			if (checkbox_displayLog.isSelected()) {
				strStdoutValue = "true";
			} else {
				strStdoutValue = "false";
			}

			if (checkbox_SilentOtherLogs.isSelected()) {
				strSilentOtherLogs = "true";
			} else {
				strSilentOtherLogs = "false";
			}
			
			if (edit_filtername.getText().trim().length() > 0)	{
				if (edit_filtername.getText().trim().matches("Your Apk Name")) {
					filterName = "none";
				} else {
					filterName = edit_filtername.getText().trim();
				}
			}

			if (edit_directoryname.getText().trim().length() > 0){
				dirPath = edit_directoryname.getText();
				dirPath = dirPath.replace('\\','/');
			}
			if (edit_filename.getText().trim().length() > 0){
				fileName = edit_filename.getText().trim();
			}
			if (edit_filterrange.getText().trim().length() > 0){
				filterRange = edit_filterrange.getText().trim();
			}
			
			// if(e.getActionCommand().endsWith("Run")){
			try {
				String text = "cmd.exe~~/K~~start~~perl~~script/logcatUtil.pl~~"
						// "perl&&logcatUtil.pl&&"
						+ dirPath + "~~"
						+ fileName + "~~"
						+ strStdoutValue + "~~"// stdout? true or false
						+ filterName + "~~"// filter @^ALL^@ ==> all, no filter name
						+ filterRange + "~~"
						+ strSilentOtherLogs + "~~"// 다른 로그 출력할지 말지
						+ nOptionSplitCnt + "~~"
						+ logType + "~~"
						+ edit_filteroption.getText().trim();						
				
				String[] cmdList = text.split(regex);

				mExe.runProcessSimple(cmdList);
				//mExecute.runProcess(cmdList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == this.initBtn) {
			initTabValues();
		} else if (e.getSource() == this.dirBtn) {
			String temp;
			temp = mLogCatDlg.getDir();
			edit_directoryname.setText(temp);
		} else if (e.getSource() == this.dirOpenBtn) {
			String	dirName = edit_directoryname.getText().trim();
			
			if (dirName.equals("none"))	{
				File file = new File("logcat_out"); // assuming that path is not empty
		    	try {
					Desktop.getDesktop().open(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	else	{
			    File file = new File(dirName); // assuming that path is not empty
			    
			    if (AdbUtil_Dialog.existFileOrPath(file))	{  
			    	try {
						Desktop.getDesktop().open(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }  else	{
			    	JOptionPane.showMessageDialog(this, "no exist directory path","Warning", JOptionPane.WARNING_MESSAGE);
			    }
			}
		} else if (e.getSource() == this.checkbox_SilentOtherLogs) {
			if (checkbox_SilentOtherLogs.isSelected()) {
				strSilentOtherLogs = "true";
				edit_filtername.setEditable(true);
			} else {
				edit_filtername.setEditable(false);
				strSilentOtherLogs = "false";
			}
		} else if (e.getSource() == this.checkbox_displayLog) {
			if (checkbox_displayLog.isSelected()) {
				strStdoutValue = "true";
			} else {
				strStdoutValue = "false";
			}
		} else if (e.getSource() == this.radioBtn_Main)	{
			logType = "main";
			radioBtn_Main.setSelected(true);
			radioBtn_System.setSelected(false);
			radioBtn_Radio.setSelected(false);
			radioBtn_Events.setSelected(false);
		} else if (e.getSource() == this.radioBtn_System)	{
			logType = "system";
			radioBtn_Main.setSelected(false);
			radioBtn_System.setSelected(true);
			radioBtn_Radio.setSelected(false);
			radioBtn_Events.setSelected(false);
		} else if (e.getSource() == this.radioBtn_Radio)	{
			logType = "-b radio";
			radioBtn_Main.setSelected(false);
			radioBtn_System.setSelected(false);
			radioBtn_Radio.setSelected(true);
			radioBtn_Events.setSelected(false);
		} else if (e.getSource() == this.radioBtn_Events)	{
			logType = "-b events";
			radioBtn_Main.setSelected(false);
			radioBtn_System.setSelected(false);
			radioBtn_Radio.setSelected(false);
			radioBtn_Events.setSelected(true);
		}
	}
	
	private void initTabValues()	{
		edit_filteroption.setText("-v time");
		edit_filterrange.setText("I");
		edit_filtername.setText("Your Apk Name");
		edit_directoryname.setText("none");
		edit_filename.setText("none");
		checkbox_SilentOtherLogs.setSelected(true);
		checkbox_displayLog.setSelected(true);
		strSilentOtherLogs = "true";
		strStdoutValue = "true";
		radioBtn_Main.setSelected(true);
		radioBtn_System.setSelected(false);
		radioBtn_Radio.setSelected(false);
		radioBtn_Events.setSelected(false);
	}
}

/*
@SuppressWarnings("serial")
class AdbUtil_LogCat_LogTextFrame extends JFrame	{
	private Container con;
	private BorderLayout bl = new BorderLayout();
	private JButton	btn1 = new JButton("1");
	private JButton	btn2 = new JButton("2");	
	private static JTextArea mlogText = new JTextArea();
	private JScrollPane mlogsrollBar;
	
	//Rectangle bounds = new Rectangle(20, 20, 200, 200);

	public AdbUtil_LogCat_LogTextFrame()	{
		super("log");		
	    setSize(630, 430);	    	    
		createPage();
		this.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e){
				repaint();
			}
		});
	}
	
	private void createPage()	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		con = this.getContentPane();
		con.setLayout(bl);
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jp.add(btn1);
		jp.add(btn2);
				
		mlogText.setLineWrap(true);
		mlogText.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE));
		mlogText.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		mlogsrollBar = new JScrollPane(mlogText);
		mlogsrollBar.setWheelScrollingEnabled(true);
		mlogsrollBar.setBounds(0,//this.getX()+5,
							   0,//this.getY()+5,
							   600, 400);
		jp.add(mlogsrollBar);
		
		con.add("North", jp);
		mlogsrollBar.setWheelScrollingEnabled(true);
		con.add("Center", mlogsrollBar);
	}
	
	public static void updateLogText(String log)	{
		mlogText.append(log);
		//mlogText.invalidate();
		mlogText.update(mlogText.getGraphics());
	}
}*/