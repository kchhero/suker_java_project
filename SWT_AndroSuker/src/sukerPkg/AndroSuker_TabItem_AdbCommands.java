package sukerPkg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class AndroSuker_TabItem_AdbCommands implements AndroSuker_Definition {
	private static AndroSuker_Execute 	mExe;
	private static Composite 			AdbCommands_composite;	
	private static StyledText 			mLogText;	
	private static String 				mResultAfterProcess;
	private static FileHandler 			resultFile = new FileHandler();	
	private static String 				mCurrentHandlingFilePath;
	
	private List<String> 	readList;
	private List<String> 	writeList;	
	private Text 			addCommandEdit;	
	private Table 			CmdTable;
	private int 			values_cnt;
	
	private String 			info_highlight_keyword;
	
	private final int		ADBFIRMWARE_ROOT = 500;
	private final int		ADBFIRMWARE_REMOUNT = 501;
	private final int		ADBFIRMWARE_ADD = 502;
	private final int		ADBFIRMWARE_RUN = 503;
	private final int		ADBFIRMWARE_DEL = 504;
	
	private final int		MAX_TABLE_CNT = 1000;
	private Timer 			timer = new Timer();
	//private static Process	adbProcess = null;
	//private static Shell	thisShell;
	private int				hotKey;
		
	public AndroSuker_TabItem_AdbCommands(Shell shell, TabFolder tabFolder, AndroSuker_Execute mExecute)	{
		//thisShell = shell;
		createPage(tabFolder);
		mExe = mExecute;    
		initPage();
		mCurrentHandlingFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_DEVICE;
	}

	public static Composite getInstance()	{
		return AdbCommands_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	public void __onFinally()	{
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		TableItem[] cmdTableItems = CmdTable.getItems();
		
		AndroSuker_INIFile.writeIniFile("FC_VALUE_CNT", Integer.toString(CmdTable.getItemCount()), writeList);
				
		for (int i = 0; i < values_cnt; i++)	{
			AndroSuker_INIFile.writeIniFile("FC_VALUE_"+i, cmdTableItems[i].getText(), writeList);
		}
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
		mExe.killProcess();
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FC_VALUE_CNT");
			if (resultStr.length() > 0)
				values_cnt = Integer.parseInt(resultStr);
			else
				values_cnt = 0;

			for (int i = 0; i < values_cnt; i++)	{
				TableItem item = new TableItem(CmdTable, SWT.FILL);
				resultStr = AndroSuker_INIFile.readIniFile(readList, "FC_VALUE_"+i);
				item.setText(resultStr);
			}
		    CmdTable.setSelection(0);
		}
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		AdbCommands_composite = new Composite(tabFolder, SWT.FILL);
	    AdbCommands_composite.setLayout(new GridLayout(6, false));
	    
		//------------------------------######### label 1 ##########---------------------------------
	    Label lblAddCommand = new Label(AdbCommands_composite, SWT.NONE);
		GridData gd_lblAddCommand = new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1);
		gd_lblAddCommand.heightHint = 23;
		gd_lblAddCommand.widthHint = 114;
		lblAddCommand.setLayoutData(gd_lblAddCommand);
		lblAddCommand.setText("add Command");
		new Label(AdbCommands_composite, SWT.NONE);
		
		//------------------------------######### btn group 1 ##########---------------------------------
		Button btnRoot = new Button(AdbCommands_composite, SWT.NONE);
		GridData gd_btnRoot = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnRoot.heightHint = 32;
		btnRoot.setLayoutData(gd_btnRoot);
		btnRoot.setText("adb root");		
		btnRoot.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBFIRMWARE_ROOT);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		Button btnRemount = new Button(AdbCommands_composite, SWT.NONE);
		GridData gd_btnRemount = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnRemount.heightHint = 32;
		btnRemount.setLayoutData(gd_btnRemount);
		btnRemount.setText("adb remount");
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);		
		btnRemount.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBFIRMWARE_REMOUNT);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		 
		//------------------------------######### edit command ##########---------------------------------
		addCommandEdit = new Text(AdbCommands_composite, SWT.BORDER);
		GridData gd_addCommandEdit = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_addCommandEdit.heightHint = 22;
		addCommandEdit.setLayoutData(gd_addCommandEdit);
		new Label(AdbCommands_composite, SWT.NONE);
		addCommandEdit.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});

		//------------------------------######### add button ##########---------------------------------
		Button btnAdd = new Button(AdbCommands_composite, SWT.NONE);
		GridData gd_btnAdd = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAdd.heightHint = 31;
		btnAdd.setLayoutData(gd_btnAdd);
		btnAdd.setText("Add to table");
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ADBFIRMWARE_ADD);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//------------------------------######### label 2 ##########---------------------------------
		Label lblSelectCommand = new Label(AdbCommands_composite, SWT.NONE);
		lblSelectCommand.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSelectCommand.setText("select Command");
		new Label(AdbCommands_composite, SWT.NONE);
		
		//------------------------------######### del button ##########---------------------------------
		Button btnDelItem = new Button(AdbCommands_composite, SWT.NONE);
		GridData gd_btnDelItem = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnDelItem.heightHint = 34;
		btnDelItem.setLayoutData(gd_btnDelItem);
		btnDelItem.setText("Item delete");
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		btnDelItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ADBFIRMWARE_DEL);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});		
		
		//------------------------------######### table ##########---------------------------------
		CmdTable = new Table(AdbCommands_composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_table.heightHint = 120;
		CmdTable.setLayoutData(gd_table);
		CmdTable.setHeaderVisible(false);
		CmdTable.setLinesVisible(false);
		new Label(AdbCommands_composite, SWT.NONE);
		
		//------------------------------######### run button ##########---------------------------------
		Button btnRun = new Button(AdbCommands_composite, SWT.NONE);
		GridData gd_btnRun = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_btnRun.widthHint = 138;
		gd_btnRun.heightHint = 45;
		btnRun.setLayoutData(gd_btnRun);
		btnRun.setText("Run");
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);
		new Label(AdbCommands_composite, SWT.NONE);		
		btnRun.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ADBFIRMWARE_RUN);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//------------------------------------------------------------------------------------
		mLogText = new StyledText(AdbCommands_composite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);		
		mLogText.setDoubleClickEnabled(false);
		GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, false, 6, 2);
		gd_styledText.heightHint = 305;//115;
		mLogText.setLayoutData(gd_styledText);
		
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
		
		mLogText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {	
				if ((e.stateMask & SWT.CTRL) == SWT.CTRL)	{
					hotKey = SWT.CTRL;
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.println("press ctrl key");
					}
				} else if ((e.stateMask & SWT.ALT) == SWT.ALT)	{
					hotKey = SWT.ALT;
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.println("press ctrl key");
					}
				}
				if ((hotKey == SWT.CTRL) && (e.keyCode == 'a'))	{
					mLogText.selectAll();
					hotKey = 0;
					mLogText.setCaretOffset(mLogText.getText().length());
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub				
			}
		});
	}
	
	private StyleRange getHighlightStyle(int startOffset, int length) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		//styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		return styleRange;
	}
	
	public void _actionPerformed(int action) {
		if (ADBFIRMWARE_RUN == action) {
			TableItem[] selectItem = CmdTable.getSelection();			
			String text;						   
			String[] cmdList;
			String[] cmdListBody;
			
			logTextClear();			
			cmdListBody = selectItem[0].getText().split("[ ]+");
			
			if (cmdListBody[0].equals("cat") || cmdListBody[0].equals("ls"))	{
				/*text = "cmd.exe~~/c~~adb~~shell~~" + selectItem[0].getText() + "~~>~~" + mCurrentHandlingFilePath;
				cmdList = text.split("~~");
				String text2 = "cmd.exe~~/c~~chmod~~666~~" + mCurrentHandlingFilePath;
				String[] cmdList2 = text2.split("~~");*/
				text = "perl~~script/adbShellCmd.pl" + "~~" + selectItem[0].getText() + "~~" + mCurrentHandlingFilePath;
				//cmd.exe~~/c~~echo~~off~~start~~perl~~script/adbShellCmd.pl
				cmdList = text.split("~~");
				
				try {
					/*mExe.runProcessSimple(cmdList2);
					adbProcess = mExe.runProcessSimpleEx(cmdList);*/
					mExe.runProcessSimple(cmdList);			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 1500);
				}
			}
			else	{
				text = "cmd.exe~~/C~~adb~~shell~~" + selectItem[0].getText();
				cmdList = text.split("~~");
				if (cmdList != null)	{
					try {
						mExe.runProcess(cmdList, getCurrentClsName());
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else if (ADBFIRMWARE_ADD == action)	{
			if (values_cnt == MAX_TABLE_CNT)	{
				MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
		        mb.setText("Warning");
		        mb.setMessage("더이상 추가할 수 없습니다. max 1000개");
		        mb.open();
			}			
			if (addCommandEdit.getText().length() > 1)	{				
				TableItem item = new TableItem(CmdTable, SWT.NONE); //, values_cnt);
			    item.setText(addCommandEdit.getText().toString());			    
				values_cnt++;
				addCommandEdit.setText("");
			}
		} else if (ADBFIRMWARE_DEL == action)	{
			CmdTable.remove(CmdTable.getSelectionIndices());			
			values_cnt--;
			CmdTable.update();
		} else if (ADBFIRMWARE_ROOT == action)	{
			//String cmd = "cmd.exe~~/C~~start~~adb~~root";
			String cmd1 = "cmd.exe~~/C~~adb~~wait-for-device~~";
			String[] cmdList1 = cmd1.split("~~");
			String cmd2 = "cmd.exe~~/C~~adb~~root~~";			
			String[] cmdList2 = cmd2.split("~~");			
			
			try {
				mExe.runProcess(cmdList1, getCurrentClsName());
				//mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				mExe.runProcess(cmdList2, getCurrentClsName());
				//mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}	
		} else if (ADBFIRMWARE_REMOUNT == action)	{
			String cmd1 = "cmd.exe~~/C~~adb~~wait-for-device~~";
			String[] cmdList1 = cmd1.split("~~");
			String cmd2 = "cmd.exe~~/C~~adb~~remount~~";
			//String cmd = "cmd.exe~~/C~~start~~adb~~remount";
			String[] cmdList2 = cmd2.split("~~");
			
			try {
				mExe.runProcess(cmdList1, getCurrentClsName());
				//mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				mExe.runProcess(cmdList2, getCurrentClsName());
				//mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void callbackDoneExecuteProcess() {		
		mResultAfterProcess = mExe.getResultMessages();
		AsyncTaskRun.asyncTextOutProcOneShot(AndroSuker_MainCls.getShellInstance(), mLogText, mResultAfterProcess, 500);
	}	
	public static void callbackFailExecuteProcess() {
		AsyncTaskRun.asyncTextOutProcOneShot(AndroSuker_MainCls.getShellInstance(), mLogText, "failed!!", 500);
	}
	private void logTextClear()	{
		AsyncTaskRun.asyncTextOutProcOneShot(AndroSuker_MainCls.getShellInstance(), mLogText, "", 500);
	}
	public static void resultAnalysis()	{
		AndroSuker_AnalysisRapid.resultAnalysis(AndroSuker_MainCls.getShellInstance(), resultFile, mLogText, mCurrentHandlingFilePath, 200);		
	}
	/*public static void finallyAdbKill()	{
		final Timer timer = new Timer();
		TimerTask asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {
		            	mExe.killProcess(adbProcess);
		            }
		        });
		    }
		};
		timer.schedule(asyncLogTextTimer, 3000);
	}*/
}