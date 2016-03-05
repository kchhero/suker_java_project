package sukerPkg;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.comm.CommPortIdentifier;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class AndroSuker_TabItem_SerialComm implements AndroSuker_Definition, Observer {
	private static Composite 			SerialComm_composite;	
	
	private List<String> 	readList;
	private List<String> 	writeList;	
	private Text 			addCommandEdit;	
	private Table 			CmdTable;
	private int 			values_cnt;
	private StyledText 		mLogText;
	private String 			info_highlight_keyword;
	private String			mCurrentCmdStr;
	
	private Button 			btnOpenPort;
	private Button 			btnClosePort;
	private Button 			btnRun;
	private Button 			btnClearText;
	private Button 			btnComPortScan;
	
	private Group 			comboGroup;
	private static Combo  	combo_atcmd_port;
	private static Combo  	combo_atcmd_baudrate;
	private static Combo  	combo_atcmd_data;
	private static Combo  	combo_atcmd_parity;
	private static Combo  	combo_atcmd_stop;
	private static Combo  	combo_atcmd_flowcontrol;
	
	private Button 			btnCheckButton;
	
	private final int		ATCMD_BTN_OPENPORT = 500;
	private final int		ATCMD_BTN_CLOSEPORT = 501;
	private final int		ATCMD_BTN_ADD = 502;
	private final int		ATCMD_BTN_DEL = 503;
	private final int		ATCMD_BTN_DO = 504;
	private final int		ATCMD_BTN_CLEARTEXT = 505;
	private final int		ATCMD_BTN_SCAN = 506;
	
	private final int		ATCMD_CHK_BTN = 600;
	
	private final int		MAX_TABLE_CNT = 30;
	private static Shell	thisShell;
		
	private static AndroSuker_SerialParameters	serialParameters;
	private static AndroSuker_SerialConnection	serialConnection;
	
	private boolean			lock_myself;
	public boolean			isPossibleReadFromSerial;
	
	public AndroSuker_TabItem_SerialComm(Shell shell, TabFolder tabFolder, AndroSuker_Execute mExecute)	{
		thisShell = shell;
		createPage(tabFolder);
		
		lock_myself = false;
		isPossibleReadFromSerial = false;
		
		serialParameters = new AndroSuker_SerialParameters();
		serialConnection = new AndroSuker_SerialConnection(this, serialParameters, thisShell);
				
		btnClosePort.setEnabled(false);
		btnRun.setEnabled(false);
		mCurrentCmdStr = "";
		
		initParameters();
		initPage();
	}
	
	public static void start()	{
		if (!serialConnection.isOpen())	{
			listPortChoices();			
		}
	}

	public static Composite getInstance()	{
		return SerialComm_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	public void __onFinally()	{	
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		TableItem[] cmdTableItems = CmdTable.getItems();
		
		if (btnCheckButton.getSelection())
			AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_LGATCMD_CHECKBOX", "true", writeList);
		else
			AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_LGATCMD_CHECKBOX", "false", writeList);
		
		AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_BAUTRATE", 	serialParameters.getBaudRateIndexToString(combo_atcmd_baudrate.getSelectionIndex()), writeList);
		AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_DATABIT", 		serialParameters.getDatabitsIndexToString(combo_atcmd_data.getSelectionIndex()), writeList);
		AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_PARITY", 		serialParameters.getParityIndexToString(combo_atcmd_parity.getSelectionIndex()), writeList);
		AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_STOPBIT", 		serialParameters.getStopIndexToString(combo_atcmd_stop.getSelectionIndex()), writeList);
		AndroSuker_INIFile.writeIniFile("ATCMD_SETUP_FLOWCONTROL",	serialParameters.getFlowControlIndexToString(combo_atcmd_flowcontrol.getSelectionIndex()), writeList);
		
		AndroSuker_INIFile.writeIniFile("ATCMD_VALUE_CNT", Integer.toString(CmdTable.getItemCount()), writeList);
				
		for (int i = 0; i < values_cnt; i++)	{
			AndroSuker_INIFile.writeIniFile("ATCMD_VALUE_"+i, cmdTableItems[i].getText(), writeList);
		}
		
		serialCommTask.stopTimer();
		
		serialConnection.closeConnection();
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_LGATCMD_CHECKBOX");
			if (resultStr.equals("true"))	{
				btnCheckButton.setSelection(true);
				serialConnection.setATCMDpurpose(serialConnection.SERIALCOMM_PURPOSE_LGATCMD);
			}
			else	{
				btnCheckButton.setSelection(false);
				serialConnection.setATCMDpurpose(serialConnection.SERIALCOMM_PURPOSE_GENERAL);
			}
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_BAUTRATE");			
			if (resultStr.length() > 0)
				combo_atcmd_baudrate.select(serialParameters.getBaudRateStringToIndex(resultStr));
			else
				combo_atcmd_baudrate.select(serialParameters.getBaudRateStringToIndex("9600"));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_DATABIT");
			if (resultStr.length() > 0)
				combo_atcmd_data.select(serialParameters.getDatabitsStringToIndex(resultStr));
			else
				combo_atcmd_data.select(serialParameters.getDatabitsStringToIndex("8"));

			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_PARITY");
			if (resultStr.length() > 0)
				combo_atcmd_parity.select(serialParameters.getParityStringToIndex(resultStr));
			else
				combo_atcmd_parity.select(serialParameters.getParityStringToIndex("None"));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_STOPBIT");
			if (resultStr.length() > 0)
				combo_atcmd_stop.select(serialParameters.getStopbitsStringToIndex(resultStr));
			else
				combo_atcmd_stop.select(serialParameters.getStopbitsStringToIndex("1"));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_SETUP_FLOWCONTROL");
			if (resultStr.length() > 0)
				combo_atcmd_flowcontrol.select(serialParameters.getFlowControlStringToIndex(resultStr));
			else
				combo_atcmd_flowcontrol.select(serialParameters.getFlowControlStringToIndex("None"));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_VALUE_CNT");
			if (resultStr.length() > 0)
				values_cnt = Integer.parseInt(resultStr);
			else
				values_cnt = 0;

			for (int i = 0; i < values_cnt; i++)	{
				TableItem item = new TableItem(CmdTable, SWT.FILL);
				resultStr = AndroSuker_INIFile.readIniFile(readList, "ATCMD_VALUE_"+i);
				item.setText(resultStr);
			}
		    CmdTable.setSelection(0);
		}
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		SerialComm_composite = new Composite(tabFolder, SWT.FILL);
		SerialComm_composite.setLayout(new GridLayout(5, false));
	    
	    Label lblNewLabel = new Label(SerialComm_composite, SWT.NONE);
		lblNewLabel.setText("Send Event");
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		
		addCommandEdit = new Text(SerialComm_composite, SWT.BORDER);
		addCommandEdit.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1));
		new Label(SerialComm_composite, SWT.NONE);
		addCommandEdit.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
				
		//------------------------------######### Group1 ##########---------------------------------
		Group group_btn = new Group(SerialComm_composite, SWT.NONE);
		GridData gd_group_btn = new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 2);
		gd_group_btn.heightHint = 97;
		group_btn.setLayoutData(gd_group_btn);
		
		//------------------------------######### add button ##########---------------------------------
		Button btnAdd = new Button(group_btn, SWT.NONE);		
		btnAdd.setBounds(4, 14, 88, 24);
		btnAdd.setText("Add to table");
		btnAdd.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_ADD);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
				
		//------------------------------######### del button ##########---------------------------------
		Button btnDelItem = new Button(group_btn, SWT.NONE);
		btnDelItem.setBounds(4, 47, 88, 24);
		btnDelItem.setText("Item delete");
		btnDelItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_DEL);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});		
				
		//------------------------------######### run button ##########---------------------------------
		btnRun = new Button(group_btn, SWT.NONE);
		btnRun.setFont(SWTResourceManager.getFont("굴림", 9, SWT.BOLD));
		btnRun.setBounds(4, 80, 88, 24);
		btnRun.setText("Do");
		new Label(SerialComm_composite, SWT.NONE);
		btnRun.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_DO);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//------------------------------######### table ##########---------------------------------
		CmdTable = new Table(SerialComm_composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.TOP, true, true, 2, 1);
		gd_table.heightHint = 70;
		CmdTable.setLayoutData(gd_table);
		CmdTable.setHeaderVisible(false);
		CmdTable.setLinesVisible(false);				
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		
		//------------------------------######### Text ##########---------------------------------
		mLogText = new StyledText(SerialComm_composite, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		mLogText.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		mLogText.setDoubleClickEnabled(false);
		GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 6);
		gd_styledText.heightHint = 192;
		mLogText.setLayoutData(gd_styledText);
		mLogText.setText("");
		mLogText.setEnabled(false);
		mLogText.setEditable(false);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		new Label(SerialComm_composite, SWT.NONE);
		
		mLogText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent event) {
				if(info_highlight_keyword == null || info_highlight_keyword.length() == 0) {
					event.styles = new StyleRange[0];
					return;
				}

				String line = event.lineText;
				int cursor = -1;

				LinkedList<StyleRange> list = new LinkedList<StyleRange>();
				while( (cursor = line.indexOf(info_highlight_keyword, cursor+1)) >= 0) {
					list.add(getHighlightStyle(event.lineOffset+cursor, info_highlight_keyword.length()));
				}

				event.styles = (StyleRange[]) list.toArray(new StyleRange[list.size()]);
			}
		});
		mLogText.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent event){				
				if (serialConnection.getIsEchoStr() || lock_myself)	{
					return ;
				}

				String currText = mLogText.getText();
		        String newText = currText.substring(event.start, event.start + event.length);
		        	
				if (newText.equals("\r\n"))	{
					if (!serialConnection.isOpen())	{
						AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning", "아직 com port가 연결되지 않았습니다.!!", SWT.OK);
				        return ;
					}
					
					if (mCurrentCmdStr.length() <= 0)						
						return ;
					
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.println("======== a 1 ========");
					}

					lock_myself = true;
					serialCommTask.asyncSendEvent(thisShell, serialConnection, mLogText, mCurrentCmdStr.trim(), 100);
					mCurrentCmdStr = "";
				} else {
					mCurrentCmdStr = mLogText.getLine(mLogText.getLineCount()-1);
				}				
			} 
		});
		
		//------------------------------######### port button ##########---------------------------------
		btnOpenPort = new Button(SerialComm_composite, SWT.NONE);
		btnOpenPort.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 1, 1));
		btnOpenPort.setText("Open port");
		btnOpenPort.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_OPENPORT);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnClosePort = new Button(SerialComm_composite, SWT.NONE);
		btnClosePort.setText("Close port");
		btnClosePort.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_CLOSEPORT);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});				
		
		//------------------------------######### Group2 ##########---------------------------------
		comboGroup = new Group(SerialComm_composite, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_group.heightHint = 177;
		gd_group.widthHint = 172;
		comboGroup.setLayoutData(gd_group);
		
		combo_atcmd_port = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_port.setBounds(80, 25, 86, 20);
		combo_atcmd_port.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		    	  serialParameters.setPortName(combo_atcmd_port.getSelectionIndex());
		      }
	    });
		    
		combo_atcmd_baudrate = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_baudrate.setBounds(80, 51, 86, 20);
		
		combo_atcmd_data = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_data.setBounds(80, 77, 86, 20);
		
		combo_atcmd_parity = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_parity.setBounds(80, 105, 86, 20);
		
		combo_atcmd_stop = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_stop.setBounds(80, 131, 86, 20);
		
		combo_atcmd_flowcontrol = new Combo(comboGroup, SWT.NONE);
		combo_atcmd_flowcontrol.setBounds(80, 157, 86, 20);
		
		Label lbl_atcmd_port = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_port.setBounds(13, 28, 61, 12);
		lbl_atcmd_port.setText("Port");
		
		Label lbl_atcmd_baudrate = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_baudrate.setBounds(13, 51, 61, 12);
		lbl_atcmd_baudrate.setText("Baud rate");
		
		Label lbl_atcmd_data = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_data.setBounds(13, 77, 61, 12);
		lbl_atcmd_data.setText("Data");
		
		Label lbl_atcmd_parity = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_parity.setBounds(13, 105, 61, 12);
		lbl_atcmd_parity.setText("Parity");
		
		Label lbl_atcmd_stop = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_stop.setBounds(13, 131, 61, 12);
		lbl_atcmd_stop.setText("Stop");
		
		Label lbl_atcmd_flowcontrol = new Label(comboGroup, SWT.NONE);
		lbl_atcmd_flowcontrol.setBounds(13, 157, 61, 12);
		lbl_atcmd_flowcontrol.setText("Flow control");
		
		btnClearText = new Button(SerialComm_composite, SWT.NONE);
		GridData gd_btnNewButton_5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_5.widthHint = 127;
		btnClearText.setLayoutData(gd_btnNewButton_5);
		btnClearText.setText("Clear Text");		
		btnClearText.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_CLEARTEXT);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnCheckButton = new Button(SerialComm_composite, SWT.CHECK);
		btnCheckButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		btnCheckButton.setText("LG ATCMD");
		new Label(SerialComm_composite, SWT.NONE);
		btnCheckButton.setSelection(true);
		btnCheckButton.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ATCMD_CHK_BTN);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnComPortScan = new Button(SerialComm_composite, SWT.NONE);
		btnComPortScan.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnComPortScan.setText("Com Port Scan");
		btnComPortScan.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_SCAN);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
	}
	
	private StyleRange getHighlightStyle(int startOffset, int length) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		return styleRange;
	}
	
	public void _actionPerformed(int action) {
		if (ATCMD_BTN_OPENPORT == action) {			
		    try {
		    	setSerialParameters();
		    	serialConnection.openConnection();
		    	
		    } catch (SerialConnectionException e2) {	    
		    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "com port 연결 실패 !!", SWT.OK);
		    	return;
		    }
		    mLogText.setEnabled(true);
		    mLogText.setEditable(true);
		    btnOpenPort.setEnabled(false);
		    btnClosePort.setEnabled(true);
		    setEnableCombo(false);
		    btnRun.setEnabled(true);
		    btnComPortScan.setEnabled(false);
		    mLogText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		} else if (ATCMD_BTN_CLOSEPORT == action)	{
			mLogText.setEnabled(false);
			mLogText.setEditable(false);
			btnOpenPort.setEnabled(true);
			btnClosePort.setEnabled(false);
			setEnableCombo(true);
			btnRun.setEnabled(false);
			btnComPortScan.setEnabled(true);
			serialConnection.closeConnection();
			mLogText.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		} else if (ATCMD_BTN_ADD == action)	{
			if (values_cnt == MAX_TABLE_CNT)	{
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning", "더이상 추가할 수 없습니다. max 50개임", SWT.OK);
			}			
			if (addCommandEdit.getText().length() > 1)	{				
				TableItem item = new TableItem(CmdTable, SWT.NONE, values_cnt);
			    item.setText(addCommandEdit.getText().toString());			    
				values_cnt++;
				addCommandEdit.setText("");
			}
		} else if (ATCMD_BTN_DEL == action)	{
			CmdTable.remove(CmdTable.getSelectionIndices());			
			values_cnt--;
			CmdTable.update();
		} else if (ATCMD_BTN_DO == action)	{
			TableItem[] selectItem = CmdTable.getSelection();
			
			lock_myself = true;
			serialCommTask.asyncSendEvent(thisShell, serialConnection, mLogText, selectItem[0].getText(), 100);
			mCurrentCmdStr = "";			
		} else if (ATCMD_BTN_CLEARTEXT == action)	{
			mLogText.setText("");
			mCurrentCmdStr = "";
		} else if (ATCMD_BTN_SCAN == action)	{			
			listPortChoices();
			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", "Scan 완료!", SWT.OK);
		} else if (ATCMD_CHK_BTN == action)	{
			if (btnCheckButton.getSelection())	{
				btnCheckButton.setSelection(true);
				serialConnection.setATCMDpurpose(serialConnection.SERIALCOMM_PURPOSE_LGATCMD);
			}
			else	{
				btnCheckButton.setSelection(false);
				serialConnection.setATCMDpurpose(serialConnection.SERIALCOMM_PURPOSE_GENERAL);
			}
		}
	}
	
	private static void initParameters()
	{
		combo_atcmd_baudrate.add("300");
		combo_atcmd_baudrate.add("2400");
		combo_atcmd_baudrate.add("9600");
		combo_atcmd_baudrate.add("14400");
		combo_atcmd_baudrate.add("28800");
		combo_atcmd_baudrate.add("38400");
		combo_atcmd_baudrate.add("57600");
		combo_atcmd_baudrate.add("152000");
		
		combo_atcmd_data.add("5");  
		combo_atcmd_data.add("6");
		combo_atcmd_data.add("7");
		combo_atcmd_data.add("8");

		combo_atcmd_parity.add("None");
		combo_atcmd_parity.add("Even");
		combo_atcmd_parity.add("Odd");
			    
		combo_atcmd_stop.add("1");
		combo_atcmd_stop.add("1.5");
		combo_atcmd_stop.add("2");
		
		combo_atcmd_flowcontrol.add("None");
		combo_atcmd_flowcontrol.add("Xon/Xoff");
		combo_atcmd_flowcontrol.add("RTS/CTS");
	}
	
	private void setEnableCombo(boolean available)	{
		combo_atcmd_port.setEnabled(available);
		combo_atcmd_baudrate.setEnabled(available);
		combo_atcmd_data.setEnabled(available);
		combo_atcmd_parity.setEnabled(available);
		combo_atcmd_stop.setEnabled(available);
		combo_atcmd_flowcontrol.setEnabled(available);
	}

	static void listPortChoices() {
		if (combo_atcmd_port != null)	{
			combo_atcmd_port.removeAll();
		}
		
		Enumeration<?> pList = CommPortIdentifier.getPortIdentifiers();

		while (pList.hasMoreElements()) {
			CommPortIdentifier cpi = (CommPortIdentifier)pList.nextElement();
			serialParameters.setPortInfoHash(cpi.getName(), cpi, combo_atcmd_port);
			// System.out.println("Port " + cpi.getName());
		}
		combo_atcmd_port.select(-1);	 
	}

	private static void setSerialParameters()	{
		serialParameters.setBaudRate(serialParameters.getBaudRateIndexToString(combo_atcmd_baudrate.getSelectionIndex()));
		serialParameters.setDatabits(serialParameters.getDatabitsIndexToString(combo_atcmd_data.getSelectionIndex()));
		serialParameters.setParity(serialParameters.getParityIndexToString(combo_atcmd_parity.getSelectionIndex()));
		serialParameters.setStopbits(serialParameters.getStopIndexToString(combo_atcmd_stop.getSelectionIndex()));
		serialParameters.setFlowControlIn(serialParameters.getFlowControlIndexToString(combo_atcmd_flowcontrol.getSelectionIndex()));
		serialParameters.setFlowControlOut(serialParameters.getFlowControlIndexToString(combo_atcmd_flowcontrol.getSelectionIndex()));
	}

	@Override
	public void update(Observable o, Object arg) {
		serialCommTask.asyncReceiveReturnStr(thisShell, serialConnection, mLogText, 200);
		lock_myself = false;
	}
}

class serialCommTask	{
	final static Timer timer = new Timer();
	static Shell			thisShell;
	static StyledText		styledText;
	static boolean			ret;
	static AndroSuker_SerialConnection sC;
	static String			sendStr;
	static boolean			isLGATCMD;
	
	public static void asyncSendEvent(Shell shell, AndroSuker_SerialConnection serialConnection, StyledText msgText, String s, long d)	{
		thisShell = shell;
		sC = serialConnection;
		styledText = msgText;
		sendStr = s;
		
		TimerTask asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {		    	
				    	try {				    		
							sC.sendEvent(sendStr);
						} catch (IOException e) {
							e.printStackTrace();
						}
		            }
		        });
		    }
		};
		timer.schedule(asyncLogTextTimer, d);
	}    
	
	public static void asyncReceiveReturnStr(Shell shell, AndroSuker_SerialConnection serialConnection, StyledText msgText, long d)	{
		thisShell = shell;
		sC = serialConnection;
		styledText = msgText;		
		
		TimerTask asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		    		public void run() {
		    			if (sC.getReturnString().length() > 0)	{
			    			sC.setIsEchoStr(true);
			    			styledText.append(sC.getReturnString());
			    			styledText.setCaretOffset(styledText.getText().length());
			    			styledText.setFocus();
			    			styledText.selectAll();
			    			styledText.setSelection(styledText.getCharCount());
			    			sC.setIsEchoStr(false);
			    			sC.setReturnString("");
		    			}
		    		}
		    	});
		    }
		};
		timer.schedule(asyncLogTextTimer, d);
	}
	
	public static void stopTimer()	{
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
	}
}
