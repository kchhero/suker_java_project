package sukerPkg;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;


public class AndroSuker_TabItem_LogCatCls implements AndroSuker_Definition{
	private static AndroSuker_Execute mExe;
	private static Composite LogCat_composite;
	
	private List<String> readList;
	private List<String> writeList;
	
	private Button dirBtn;
	private Button dirOpenBtn;
	private Button initBtn;
	private Button logcatRun_btn;

	private Text edit_filteroption;
	private Text edit_filterrange;
	private Text edit_filtername;
	private Text edit_directoryname;
	private Text edit_filename;
	
	@SuppressWarnings("unused")
	private ButtonGroup radioGroup;
	private Button radioBtn_Kernel;
	private Button radioBtn_Main;
	private Button radioBtn_System;
	private Button radioBtn_Radio;
	private Button radioBtn_Events;
	private String logType = "";
	
	private Button checkbox_SilentOtherLogs;
	private Button checkbox_displayLog;

	private String strSilentOtherLogs;
	private String strStdoutValue;
	
	enum eTAB_LOGCAT	{
		LOGCAT_RUN,
		LOGCAT_INIT,
		LOGCAT_DIR_PATH,
		LOGCAT_OPEN_FOLDER,
		LOGCAT_RADIO_KERNEL,
		LOGCAT_RADIO_MAIN,
		LOGCAT_RADIO_SYSTEM,
		LOGCAT_RADIO_RADIO,
		LOGCAT_RADIO_EVENT,
		LOGCAT_CHECK_OTHER,
		LOGCAT_CHECK_DISPLAY
	};
	
	public AndroSuker_TabItem_LogCatCls(TabFolder tabFolder, AndroSuker_Execute	mExecute)	{
		createPage(tabFolder);
		mExe = mExecute;
		initPage();
	}

	public static Composite getInstance()	{
		return LogCat_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		LogCat_composite = new Composite(tabFolder, SWT.FILL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 5;
		gl.verticalSpacing = 10;
	    gl.marginHeight = 25;
		LogCat_composite.setLayout(gl);
					
		//------------------------------######### label 1 ##########---------------------------------
		Label label_FilterOption = new Label(LogCat_composite, SWT.BOTTOM);
		label_FilterOption.setText("Filter Option :");		
		GridData gridData_label0 = new GridData();
		gridData_label0.horizontalAlignment = SWT.BEGINNING;
		gridData_label0.horizontalSpan = 2;
		gridData_label0.verticalIndent = 5;
		gridData_label0.widthHint = LABEL_FILTER_OPTION_WIDTH;
		gridData_label0.heightHint = LABEL_FILTER_OPTION_HEIGHT;
		label_FilterOption.setLayoutData(gridData_label0);
		
		//------------------------------######### label 2 ##########---------------------------------
		Label label_FilterRange = new Label(LogCat_composite, SWT.BOTTOM);
		label_FilterRange.setText("Filter Range : V,D,I,W,E,F,S");
		GridData gridData_label1 = new GridData();
		gridData_label1.horizontalAlignment = SWT.BEGINNING;
		gridData_label1.horizontalSpan = 1;
		gridData_label1.verticalIndent = 5;
		gridData_label1.widthHint = LABEL_FILTER_RANGE_WIDTH;
		gridData_label1.heightHint = LABEL_FILTER_RANGE_HEIGHT;
		label_FilterRange.setLayoutData(gridData_label1);
		
		//------------------------------######### label 3 ##########---------------------------------
		Label label_FilterName = new Label(LogCat_composite, SWT.BOTTOM);
		label_FilterName.setText("Filter name : ex)LGHome, CameraApp");
		GridData gridData_label2 = new GridData();
		gridData_label2.horizontalAlignment = SWT.BEGINNING;
		gridData_label2.horizontalSpan = 2;
		gridData_label2.verticalIndent = 5;
		gridData_label2.widthHint = LABEL_FILTER_NAME_WIDTH;
		gridData_label2.heightHint = LABEL_FILTER_NAME_HEIGHT;
		label_FilterName.setLayoutData(gridData_label2);
		
		//------------------------------######### editor 1 ##########---------------------------------
		edit_filteroption = new Text(LogCat_composite, SWT.TOP | SWT.SINGLE | SWT.BORDER);
		GridData gridData_editor0 = new GridData();
		gridData_editor0.horizontalAlignment = SWT.BEGINNING;
		gridData_editor0.verticalAlignment = SWT.TOP;
		gridData_editor0.verticalIndent = -5;
		gridData_editor0.horizontalSpan = 2;
		gridData_editor0.widthHint = EDIT_FILTER_OPTION_WIDTH;
		gridData_editor0.heightHint = EDIT_FILTER_OPTION_HEIGHT;
		edit_filteroption.setLayoutData(gridData_editor0);
		
		//------------------------------######### editor 2 ##########---------------------------------
		edit_filterrange = new Text(LogCat_composite, SWT.TOP | SWT.SINGLE | SWT.BORDER);
		GridData gridData_editor1 = new GridData();
		gridData_editor1.horizontalAlignment = SWT.BEGINNING;
		gridData_editor1.verticalAlignment = SWT.TOP;
		gridData_editor1.verticalIndent = -5;			
		gridData_editor1.horizontalSpan = 1;
		gridData_editor1.widthHint = EDIT_FILTER_RANGE_WIDTH;
		gridData_editor1.heightHint = EDIT_FILTER_RANGE_HEIGHT;
		edit_filterrange.setLayoutData(gridData_editor1);
		
		//------------------------------######### editor 3 ##########---------------------------------
		edit_filtername = new Text(LogCat_composite, SWT.TOP | SWT.SINGLE | SWT.BORDER);
		GridData gridData_editor2 = new GridData();
		gridData_editor2.horizontalAlignment = SWT.BEGINNING;
		gridData_editor2.verticalAlignment = SWT.TOP;
		gridData_editor2.verticalIndent = -5;	
		gridData_editor2.horizontalSpan = 2;
		gridData_editor2.widthHint = EDIT_FILTER_NAME_WIDTH;
		gridData_editor2.heightHint = EDIT_FILTER_NAME_HEIGHT;
		edit_filtername.setLayoutData(gridData_editor2);
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

		//------------------------------######### Dir path ##########---------------------------------
		Label label_DirPath = new Label(LogCat_composite, SWT.NONE);
		label_DirPath.setText("Directory path for save the log file : (none => save to the <Current Folder>)");
		GridData gridData_label3 = new GridData();
		gridData_label3.horizontalAlignment = SWT.BEGINNING;
		gridData_label3.horizontalSpan = 5;
		gridData_label3.widthHint = LABEL_DIR_PATH_WIDTH;
		gridData_label3.heightHint = LABEL_DIR_PATH_HEIGHT;
		label_DirPath.setLayoutData(gridData_label3);
		
		edit_directoryname = new Text(LogCat_composite, SWT.TOP | SWT.SINGLE | SWT.BORDER);
		GridData gridData_editor3 = new GridData();
		gridData_editor3.horizontalAlignment = SWT.BEGINNING;
		gridData_editor3.verticalIndent = -10;	
		gridData_editor3.horizontalSpan = 4;
		gridData_editor3.widthHint = EDIT_DIR_PATH_WIDTH;
		gridData_editor3.heightHint = EDIT_DIR_PATH_HEIGHT;
		edit_directoryname.setLayoutData(gridData_editor3);

		dirBtn = new Button(LogCat_composite, SWT.PUSH);
		dirBtn.setText("...");
		GridData gridData_btn0 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData_btn0.horizontalSpan = 1;
		gridData_btn0.horizontalAlignment = SWT.END;
		gridData_btn0.widthHint = BUTTON_FOR_DIR_PATH_WIDTH;
		gridData_btn0.heightHint = BUTTON_FOR_DIR_PATH_HEIGHT;
		dirBtn.setLayoutData(gridData_btn0);
		dirBtn.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_DIR_PATH);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		dirOpenBtn = new Button(LogCat_composite, SWT.PUSH);
		dirOpenBtn.setText("open folder");
		GridData gridData_btn1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gridData_btn1.horizontalSpan = 5;
		gridData_btn1.horizontalAlignment = SWT.END;
		gridData_btn1.widthHint = BUTTON_FOR_DIR_OPEN_WIDTH;
		gridData_btn1.heightHint = BUTTON_FOR_DIR_OPEN_HEIGHT;
		dirOpenBtn.setLayoutData(gridData_btn1);		
		dirOpenBtn.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_OPEN_FOLDER);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
				
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
		
		//------------------------------######### Log File Label & Editor ##########---------------------------------
		Label label_LogFile = new Label(LogCat_composite, SWT.NONE);
		label_LogFile.setText("Log File name : (none => <Current Date>.txt)");
		GridData gridData_label4 = new GridData();
		gridData_label4.horizontalAlignment = SWT.BEGINNING;
		gridData_label4.verticalIndent = 5;
		gridData_label4.horizontalSpan = 5;
		gridData_label4.widthHint = LABEL_LOG_FILE_WIDTH;
		gridData_label4.heightHint = LABEL_LOG_FILE_HEIGHT;
		label_LogFile.setLayoutData(gridData_label4);
		
		edit_filename = new Text(LogCat_composite, SWT.TOP | SWT.SINGLE | SWT.BORDER);
		GridData gridData_editor4 = new GridData();
		gridData_editor4.horizontalAlignment = SWT.BEGINNING;
		gridData_editor4.verticalIndent = -5;	
		gridData_editor4.horizontalSpan = 5;
		gridData_editor4.widthHint = EDIT_LOG_FILE_WIDTH;
		gridData_editor4.heightHint = EDIT_LOG_FILE_HEIGHT;
		edit_filename.setLayoutData(gridData_editor4);
	
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

		//------------------------------######### Radio Group ##########---------------------------------
		GridData gridData_radioBtn = new GridData();
		gridData_radioBtn.horizontalAlignment = SWT.BEGINNING;
		gridData_radioBtn.horizontalSpan = 1;
		
		Composite radioBtn = new Composite (LogCat_composite, SWT.NO_RADIO_GROUP);
		radioBtn.setLayout (new RowLayout (SWT.VERTICAL));
		
		radioBtn_Kernel = new Button (radioBtn, SWT.RADIO);
		radioBtn_Kernel.setText("Kernel log <Default>");
		radioBtn_Kernel.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(eTAB_LOGCAT.LOGCAT_RADIO_KERNEL);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		radioBtn_Main = new Button (radioBtn, SWT.RADIO);
		radioBtn_Main.setText("Main log");
		radioBtn_Main.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(eTAB_LOGCAT.LOGCAT_RADIO_MAIN);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
				  
		radioBtn_System = new Button (radioBtn, SWT.RADIO);
		radioBtn_System.setText("System log");
		radioBtn_System.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(eTAB_LOGCAT.LOGCAT_RADIO_SYSTEM);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
				
		radioBtn_Radio = new Button(radioBtn, SWT.RADIO);
		radioBtn_Radio.setText("Radio log");		
		radioBtn_Radio.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(eTAB_LOGCAT.LOGCAT_RADIO_RADIO);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		radioBtn_Events = new Button(radioBtn, SWT.RADIO);
		radioBtn_Events.setText("Event log");
		radioBtn_Events.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(eTAB_LOGCAT.LOGCAT_RADIO_EVENT);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		radioBtn.setLayoutData(gridData_radioBtn);
		
		//------------------------------######### CheckBox Group ##########---------------------------------
		GridData gridData_checkBtn = new GridData();
		gridData_checkBtn.horizontalAlignment = SWT.END;
		gridData_checkBtn.horizontalSpan = 2;
		
		Composite checkBtn = new Composite (LogCat_composite, SWT.NO_RADIO_GROUP);
		checkBtn.setLayout (new RowLayout (SWT.VERTICAL));
		
		checkbox_SilentOtherLogs = new Button(checkBtn, SWT.CHECK);				
		checkbox_SilentOtherLogs.setText("Silent other logs");
		checkbox_SilentOtherLogs.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_CHECK_OTHER);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		checkbox_displayLog = new Button(checkBtn, SWT.CHECK);
		checkbox_displayLog.setText("Simultaneously with file saveing");
		checkbox_displayLog.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_CHECK_DISPLAY);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		checkBtn.setLayoutData(gridData_checkBtn);
		
		//------------------------------######### Function Button Group ##########---------------------------------
		GridData gridData_FnBtn = new GridData();
		gridData_FnBtn.horizontalAlignment = SWT.END;
		gridData_FnBtn.horizontalSpan = 1;
		
		Composite FnBtn = new Composite (LogCat_composite, SWT.NO_RADIO_GROUP);
		FnBtn.setLayout (new RowLayout (SWT.VERTICAL));

		initBtn = new Button(FnBtn, SWT.PUSH);
		initBtn.setText("Initialize");
		initBtn.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_INIT);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });

		logcatRun_btn = new Button(FnBtn, SWT.PUSH);
		logcatRun_btn.setText("Run");
		logcatRun_btn.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(eTAB_LOGCAT.LOGCAT_RUN);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		FnBtn.setLayoutData(gridData_FnBtn);
	}

	public void __onFinally()	{
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		
		if (edit_directoryname.getText().length() <= 1)
			edit_directoryname.setText("none");
		AndroSuker_INIFile.writeIniFile("LOGCAT_DIR", edit_directoryname.getText(), writeList);
				
		if (edit_filename.getText().length() <= 1)
			edit_filename.setText("none");
		AndroSuker_INIFile.writeIniFile("LOGCAT_FILE", edit_filename.getText(), writeList);
		
		if (edit_filteroption.getText().length() <= 1)
			edit_filteroption.setText("-v time");
		AndroSuker_INIFile.writeIniFile("LOGCAT_FILTEROPTION", edit_filteroption.getText(), writeList);
		
		if (edit_filterrange.getText().length() <= 1)
			edit_filterrange.setText("I");
		AndroSuker_INIFile.writeIniFile("LOGCAT_FILTERRANGE", edit_filterrange.getText(), writeList);
		
		if (edit_filtername.getText().length() <= 1)
			edit_filtername.setText("Your Apk Name");
		AndroSuker_INIFile.writeIniFile("LOGCAT_FILTERNAME", edit_filtername.getText(), writeList);
				
		AndroSuker_INIFile.writeIniFile("LOGCAT_SILENTOTHERLOGS", strSilentOtherLogs, writeList);
		AndroSuker_INIFile.writeIniFile("LOGCAT_SIMIL", strStdoutValue, writeList);
		AndroSuker_INIFile.writeIniFile("LOGCAT_TYPE", logType, writeList);		
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_DIR");
			edit_directoryname.setText(resultStr);
						
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_FILE");
			edit_filename.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_FILTEROPTION");
			edit_filteroption.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_FILTERRANGE");
			edit_filterrange.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_FILTERNAME");
			edit_filtername.setText(resultStr);
			
			strSilentOtherLogs = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_SILENTOTHERLOGS");
			if (strSilentOtherLogs.equals("true"))	{
				checkbox_SilentOtherLogs.setSelection(true);
				edit_filtername.setEditable(true);
			}
			else	{
				checkbox_SilentOtherLogs.setSelection(false);
				edit_filtername.setEditable(false);
			}
			
			strStdoutValue = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_SIMIL");
			if (strStdoutValue.equals("true"))
				checkbox_displayLog.setSelection(true);
			else
				checkbox_displayLog.setSelection(false);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "LOGCAT_TYPE");
			logType = resultStr;
			
			if (resultStr.equals("kernel"))	{
				radioBtn_Kernel.setSelection(true);
			}
			else if (resultStr.equals("main"))	{
				radioBtn_Main.setSelection(true);
			}
			else if (resultStr.equals("system"))	{
				radioBtn_System.setSelection(true);
			}			
			else if (resultStr.equals("-b radio"))	{
				radioBtn_Radio.setSelection(true);
			}
			else if (resultStr.equals("-b events"))	{
				radioBtn_Events.setSelection(true);
			}			
		}	else	{
			initTabValues();
		}
	}
	
	
	public void _actionPerformed(eTAB_LOGCAT action)	{
		switch(action)
		{
			case LOGCAT_RUN :
				int nOptionSplitCnt = 0;
				String regex = "~~";
				String[] tempList;
				String filterName = "none";
				String dirPath = "none";
				String fileName = "none";
				String filterRange = "V";
	
				tempList = edit_filteroption.getText().trim().split("[ ]+");
				nOptionSplitCnt = tempList.length;
	
				if (checkbox_displayLog.isEnabled()) {
					strStdoutValue = "true";
				} else {
					strStdoutValue = "false";
				}
				if (edit_filename.getText().trim().length() > 0){
					fileName = edit_filename.getText().trim();
				}
				if (edit_directoryname.getText().trim().length() > 0){
					dirPath = edit_directoryname.getText();
					dirPath = dirPath.replace('\\','/');
				}
				
				if (edit_filtername.getText().trim().length() > 0)	{
					if (edit_filtername.getText().trim().matches("Your Apk Name")) {
						filterName = "none";
					} else {
						filterName = edit_filtername.getText().trim();
					}
				}
				
				if (logType.equals("kernel"))	{
					try {
						String text = "cmd.exe~~/K~~start~~perl~~script/kernelLogUtil.pl~~"
						// "perl&&logcatUtil.pl&&"
							+ dirPath + "~~"
							+ fileName + "~~"
							+ filterName + "~~"
							+ strStdoutValue;
						String[] cmdList = text.split(regex);
		
						mExe.runProcessSimple(cmdList);
						//mExecute.runProcess(cmdList);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} else {
					/*if (edit_filtername.getText().trim().length() > 0)	{
						if (edit_filtername.getText().trim().matches("Your Apk Name")) {
							filterName = "none";
						} else {
							filterName = edit_filtername.getText().trim();
						}
					}*/
		
					if (checkbox_SilentOtherLogs.isEnabled()) {
						strSilentOtherLogs = "true";
					} else {
						strSilentOtherLogs = "false";
					}					
					
					if (edit_filterrange.getText().trim().length() > 0){
						filterRange = edit_filterrange.getText().trim();
					}
		
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
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				break;
	
			case LOGCAT_INIT :
				initTabValues();
				break;
	
			case LOGCAT_DIR_PATH :
				String temp = null;
				AndroSuker_DirDialog LogCatDlg;
				LogCatDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_DIR, FILE_TYPE_NONE);
				temp = LogCatDlg.getDir();
				if (temp != null)
					edit_directoryname.setText(temp);
				break;
				
			case LOGCAT_OPEN_FOLDER :
				String	dirName = edit_directoryname.getText().trim();
				
				if (dirName.equals("none") || dirName.length() < 1)	{
					File file = null;
					if (logType.equals("kernel")) {
						file = new File("kernellog_out"); // assuming that path is not empty
					} else {
						file = new File("logcat_out"); // assuming that path is not empty
					}
			    	try {
						Desktop.getDesktop().open(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}	else	{
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
				}
				break;
				
			case LOGCAT_CHECK_OTHER :
				if (checkbox_SilentOtherLogs.getSelection()) {
					strSilentOtherLogs = "true";
					edit_filtername.setEditable(true);
				} else {
					edit_filtername.setEditable(false);
					strSilentOtherLogs = "false";
				}
				break;
				
			case LOGCAT_CHECK_DISPLAY :
				if (checkbox_displayLog.getSelection()) {
					strStdoutValue = "true";
				} else {
					strStdoutValue = "false";
				}
				break;
			
			case LOGCAT_RADIO_KERNEL :
				logType = "kernel";
				radioBtn_Kernel.setSelection(true);
				radioBtn_Main.setSelection(false);
				radioBtn_System.setSelection(false);
				radioBtn_Radio.setSelection(false);
				radioBtn_Events.setSelection(false);
				break;
				
			case LOGCAT_RADIO_MAIN :
				logType = "main";
				radioBtn_Kernel.setSelection(false);
				radioBtn_Main.setSelection(true);
				radioBtn_System.setSelection(false);
				radioBtn_Radio.setSelection(false);
				radioBtn_Events.setSelection(false);
				break;
				
			case LOGCAT_RADIO_SYSTEM :
				logType = "system";
				radioBtn_Kernel.setSelection(false);
				radioBtn_Main.setSelection(false);
				radioBtn_System.setSelection(true);
				radioBtn_Radio.setSelection(false);
				radioBtn_Events.setSelection(false);
				break;
				
			case LOGCAT_RADIO_RADIO :
				logType = "-b radio";
				radioBtn_Kernel.setSelection(false);
				radioBtn_Main.setSelection(false);
				radioBtn_System.setSelection(false);
				radioBtn_Radio.setSelection(true);
				radioBtn_Events.setSelection(false);
				break;
				
			case LOGCAT_RADIO_EVENT :
				logType = "-b events";
				radioBtn_Kernel.setSelection(false);
				radioBtn_Main.setSelection(false);
				radioBtn_System.setSelection(false);
				radioBtn_Radio.setSelection(false);
				radioBtn_Events.setSelection(true);
				break;
		}
	}
	
	private void initTabValues()	{
		edit_filteroption.setText("-v time");
		edit_filterrange.setText("I");
		edit_filtername.setText("Your Apk Name");
		edit_directoryname.setText("none");
		edit_filename.setText("none");
		checkbox_SilentOtherLogs.setSelection(true);
		checkbox_displayLog.setSelection(true);
		strSilentOtherLogs = "true";
		strStdoutValue = "true";
		radioBtn_Kernel.setSelection(true);
		radioBtn_Main.setSelection(false);
		radioBtn_System.setSelection(false);
		radioBtn_Radio.setSelection(false);
		radioBtn_Events.setSelection(false);
	}
}