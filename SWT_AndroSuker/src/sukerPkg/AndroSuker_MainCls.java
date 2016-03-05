package sukerPkg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

class AndroSuker_Main_Layout implements AndroSuker_Definition, Observer {
	public static String 				mCurrentTabName = null;
	private static AndroSuker_Execute 	mExecute = new AndroSuker_Execute();
	private static AndroSuker_Font 		mFont;	
	private static FileHandler 	iniFile = new FileHandler();
	private static List<String> readList;
	private static List<String> writeList = new ArrayList<String>();
	
	private TabFolder tabFolder;	
	private Label myLabel;	
	  
	private AndroSuker_TabItem_LogCatCls tab0 = null;
	private AndroSuker_TabItem_MonkeyTest tab1 = null;
	private AndroSuker_TabItem_ShellCommands tab2 = null;
	private AndroSuker_TabItem_Profiling tab3 = null;
	private AndroSuker_TabItem_AdbCommands tab4 = null;
	private AndroSuker_TabItem_SerialComm tab5 = null;
	private AndroSuker_TabItem_ScreenCapture tab6 = null;
	private AndroSuker_TabItem_FastBoot tab7 = null;
	private AndroSuker_TabItem_RamDumpParser tab8 = null;
	
	private final int TAB_MAX_NUM = 9;
	private HashMap<String, Boolean> tabVisibleHashMap;
	private HashMap<String, String> tabTitleTextHashMap;
	private HashMap<String, String> tabToolTipTextHashMap;
	private Shell	 	mainShell;
	
	public static AndroSuker_Font getFontInstance()	{
		return mFont;
	}
		
	public AndroSuker_Main_Layout(final Shell shell, HashMap<String, Boolean> map) {
		setmExecute(mExecute);
		mainShell = shell;
		
		mFont = new AndroSuker_Font(shell.getDisplay());		 
		
		tabFolder = new TabFolder(shell, SWT.NONE);
		
		createIniFileHandle();
		
		//----------------------------------- Tab Visible ------------------------------
		tabVisibleHashMap = map;
		tabTitleTextHashMap = new HashMap<String, String>();
		tabToolTipTextHashMap = new HashMap<String, String>();
		tabInfoInit();
		//----------------------------------- Tab Visible ------------------------------
		if (tabVisibleHashMap.get("tab0"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab0"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab0"));		
			tab0 = new AndroSuker_TabItem_LogCatCls(tabFolder, getmExecute());			
			tabItem.setControl(AndroSuker_TabItem_LogCatCls.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab0 - " + tabToolTipTextHashMap.get("tab0") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab1"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab1"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab1"));
			tab1 = new AndroSuker_TabItem_MonkeyTest(tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_MonkeyTest.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab1 - " + tabToolTipTextHashMap.get("tab1") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab2"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab2"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab2"));		
			tab2 = new AndroSuker_TabItem_ShellCommands(tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_ShellCommands.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab2 - " + tabToolTipTextHashMap.get("tab2") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab3"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab3"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab3"));		
			tab3 = new AndroSuker_TabItem_Profiling(tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_Profiling.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab3 - " + tabToolTipTextHashMap.get("tab3") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab4"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab4"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab4"));		
			tab4 = new AndroSuker_TabItem_AdbCommands(shell, tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_AdbCommands.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab4 - " + tabToolTipTextHashMap.get("tab4") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab5"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab5"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab5"));		
			tab5 = new AndroSuker_TabItem_SerialComm(shell, tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_SerialComm.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab5 - " + tabToolTipTextHashMap.get("tab5") + "\n");
			}
		}
		
		if (tabVisibleHashMap.get("tab6"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab6"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab6"));		
			tab6 = new AndroSuker_TabItem_ScreenCapture(shell, tabFolder);
			tabItem.setControl(AndroSuker_TabItem_ScreenCapture.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab6 - " + tabToolTipTextHashMap.get("tab6") + "\n");
			}
		}
		if (tabVisibleHashMap.get("tab7"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab7"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab7"));		
			tab7 = new AndroSuker_TabItem_FastBoot(shell, tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_FastBoot.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab7 - " + tabToolTipTextHashMap.get("tab7") + "\n");
			}
		}
		if (tabVisibleHashMap.get("tab8"))	{
			TabItem tabItem = new TabItem(tabFolder, SWT.FILL);
			tabItem.setText(tabTitleTextHashMap.get("tab8"));
			tabItem.setToolTipText(tabToolTipTextHashMap.get("tab8"));		
			tab8 = new AndroSuker_TabItem_RamDumpParser(shell, tabFolder, getmExecute());
			tabItem.setControl(AndroSuker_TabItem_RamDumpParser.getInstance());
			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
				System.out.print("tab8 - " + tabToolTipTextHashMap.get("tab8") + "\n");
			}
		}
		tabFolder.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));	  
		tabFolder.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TabItem item = (TabItem) event.item;

				if (item.getText().equals(tabTitleTextHashMap.get("tab0")))	{
					setCurrentTabName(tab0.getCurrentClsName());
					shell.setSize(ADBLOG_SIZE_WIDTH, ADBLOG_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab0 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab1")))	{
					setCurrentTabName(tab1.getCurrentClsName());
					shell.setSize(TOTAL_SIZE_WIDTH, TOTAL_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab1 - changed\n");
					}
				}				
				else if (item.getText().equals(tabTitleTextHashMap.get("tab2")))	{
					setCurrentTabName(tab2.getCurrentClsName());
					resultFileClear(AndroSuker_INIFile.RESULT_FILE_PATH_SHELLCMD);
					shell.setSize(TOTAL_BIG_SIZE_WIDTH, TOTAL_BIG_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab2 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab3")))	{
					setCurrentTabName(tab3.getCurrentClsName());
					//resultFileClear(AndroSuker_INIFile.RESULT_FILE_PATH_PROF);
					//resultFileClear(AndroSuker_INIFile.RESULT_FILE_PATH_PROCSTAT);					
					shell.setSize(PROFILNING_SIZE_WIDTH, PROFILNING_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab3 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab4")))	{
					setCurrentTabName(tab4.getCurrentClsName());
					resultFileClear(AndroSuker_INIFile.RESULT_FILE_PATH_DEVICE);
					shell.setSize(TOTAL_SIZE_WIDTH, FIRMWARE_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab4 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab5")))	{
					setCurrentTabName(tab5.getCurrentClsName());
					shell.setSize(SERIALCOMM_SIZE_WIDTH, SERIALCOMM_SIZE_HEIGHT);
					AndroSuker_TabItem_SerialComm.start();
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab5 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab6")))	{
					setCurrentTabName(tab6.getCurrentClsName());
					shell.setSize(SCREENCAPTURE_SIZE_WIDTH, SCREENCAPTURE_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab6 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab7")))	{
					setCurrentTabName(tab7.getCurrentClsName());
					shell.setSize(FASTBOOT_SIZE_WIDTH, FASTBOOT_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab7 - changed\n");
					}
				}
				else if (item.getText().equals(tabTitleTextHashMap.get("tab8")))	{
					setCurrentTabName(tab8.getCurrentClsName());
					shell.setSize(RAMDUMPPARSER_SIZE_WIDTH, RAMDUMPPARSER_SIZE_HEIGHT);
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.print("tab8 - changed\n");
					}
				}
				else
					shell.setSize(TOTAL_SIZE_WIDTH, TOTAL_SIZE_HEIGHT);
			}
		});

	  //----------------------------------- Label -----------------------------------
	  myLabel = new Label(shell, SWT.BOTTOM);
	  myLabel.setText("  Created by suker (슈케르) version " + VERSION + "       since Sep. 2012");
	  myLabel.setFont(mFont.mFont_Arial_Bold);
	  myLabel.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));	  
	  myLabel.setVisible(true);
	  myLabel.setLayoutData(new BorderLayout.BorderData(BorderLayout.SOUTH));
	  
	  //tabFolder.setSelection(tabItem0);
	}
	
	private void tabInfoInit()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			for (int i = 0; i < TAB_MAX_NUM; i++)	{
				resultStr = AndroSuker_INIFile.readIniFile(readList, "MAIN_TAB_VISIBLE_SETTING"+i);
				if (resultStr.equals("true"))	{
					tabVisibleHashMap.put("tab"+i, true);
				} else {
					tabVisibleHashMap.put("tab"+i, false);
				}
				
				switch (i) {
					case 0 : tabTitleTextHashMap.put("tab"+i, "Logcat");
							 tabToolTipTextHashMap.put("tab"+i, "adb logcat commands");	break;
					case 1 : tabTitleTextHashMap.put("tab"+i, "Monkey Test"); 
							 tabToolTipTextHashMap.put("tab"+i, "One click monkey test");	break;
					case 2 : tabTitleTextHashMap.put("tab"+i, "Shell Commands");
							 tabToolTipTextHashMap.put("tab"+i, "Target Infomations show using shell commands");	break;
					case 3 : tabTitleTextHashMap.put("tab"+i, "Profiling");
							 tabToolTipTextHashMap.put("tab"+i, "System Monitoring");	break;
					case 4 : tabTitleTextHashMap.put("tab"+i, "AdbCommands");
							 tabToolTipTextHashMap.put("tab"+i, "adb commands");	break;
					case 5 : tabTitleTextHashMap.put("tab"+i, "SerialComm");
							 tabToolTipTextHashMap.put("tab"+i, "Serial Communication");	break;
					case 6 : tabTitleTextHashMap.put("tab"+i, "ScreenCapture");
							 tabToolTipTextHashMap.put("tab"+i, "Screen Capture from device");	break;
					case 7 : tabTitleTextHashMap.put("tab"+i, "FastBoot");
							 tabToolTipTextHashMap.put("tab"+i, "fastboot commands");	break;
					case 8 : tabTitleTextHashMap.put("tab"+i, "RamDumpParser");
					 		 tabToolTipTextHashMap.put("tab"+i, "RamDump Parser Easy");	break;
					default : tabTitleTextHashMap.put("tab"+i, "N/A");
							  tabToolTipTextHashMap.put("tab"+i, "N/A");
							  break;
				}
			}
		}
	}
	private void tabVisiblilitySave()	{
		writeList = AndroSuker_Main_Layout.getWriteFileList();

		for (int i = 0; i < TAB_MAX_NUM; i++)	{
			if (tabVisibleHashMap.get("tab"+i))	{
				AndroSuker_INIFile.writeIniFile("MAIN_TAB_VISIBLE_SETTING"+i, "true", writeList);
			} else {
				AndroSuker_INIFile.writeIniFile("MAIN_TAB_VISIBLE_SETTING"+i, "false", writeList);
			}
		}		
	}
	
	private static void setCurrentTabName(String name)	{
		mCurrentTabName = name;		
	}
	public static String getCurrentTabName()	{
		return mCurrentTabName;
	}
	
	private void createIniFileHandle() {
		try {
			readList = iniFile.readFile(AndroSuker_INIFile.INI_FILE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void __finallyIniFileWrite()	{
		tabVisiblilitySave();
		if (tab0 != null)	{
			tab0.__onFinally();
		}
		if (tab1 != null)	{
			tab1.__onFinally();
		}
		if (tab2 != null)	{
			tab2.__onFinally();
		}
		if (tab3 != null)	{
			tab3.__onFinally();
		}
		if (tab4 != null)	{
			tab4.__onFinally();
		}
		if (tab5 != null)	{
			tab5.__onFinally();
		}
		if (tab6 != null)	{
			tab6.__onFinally();
		}
		if (tab7 != null)	{
			tab7.__onFinally();
		}
		if (tab8 != null)	{
			tab8.__onFinally();
		}
		if (AndroSuker_Debug.DEBUG_MODE_ON)	{
			System.out.print("__finallyIniFileWrite complete\n");
		}
		
		try {
			iniFile.writeFile(writeList, AndroSuker_INIFile.INI_FILE_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static FileHandler getIniFileHandle()	{
		return iniFile;
	}
	public static List<String> getReadFileList()	{
		return readList;
	}
	public static List<String> getWriteFileList()	{
		return writeList;
	}
	
	private void resultFileClear(String resultFilePath)	{
		try {
			AndroSuker_INIFile.copyFile(AndroSuker_INIFile.RESULT_DUMMY_FILE_PATH, resultFilePath);
		} catch (IOException e) {
			AndroSuker_Debug.debugMsgDlg(mExecute, "Error", "File I/O Error...\n 1. adb.exe 또는 AndroSuker.exe에서 monitoring/result_dummy.txt의 handle을 잡고 있는 상태일 수 있음.\n" +
					"2. USB cable을 disconnect후 다시 연결해보세요.\n" +
					"3. 확인을 누르면 adb server reconnect 합니다.", 1);
		}
		
		/*String text = "cmd.exe~~/C~~xcopy~~/Y~~/C~~"+ AndroSuker_INIFile.RESULT_DUMMY_FILE_PATH + "~~" + resultFilePath; 
		String[] cmdList = text.split("~~");		
		try {
			getmExecute().runProcessSimple(cmdList);
		} catch (IOException e) {
			e.printStackTrace();
			AndroSuker_Debug.debugMsgDlg(mExecute, "Error", "File I/O Error...\n 1. adb.exe 또는 AndroSuker.exe에서 monitoring/result.txt의 handle을 잡고 있는 상태일 수 있음.\n" +
					"2. USB cable을 disconnect후 다시 연결해보세요.\n" +
					"3. 확인을 누르면 adb server reconnect 합니다.", 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}
	/*public void resultFileClear()	{
		File file = new File(AndroSuker_INIFile.RESULT_FILE_PATH);
		file.setWritable(true);
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("waiting...");
			fileWriter.close();
		} catch (IOException e) {          
			MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.ICON_ERROR | SWT.OK);
			mb.setText("Error");
			mb.setMessage("File I/O Error...\n 1. adb.exe 또는 AndroSuker.exe에서 monitoring/result.txt의 handle을 잡고 있는 상태일 수 있음.\n 해당 process를 닫고 다시 시도해보세요.\n" +
					"2. USB cable을 disconnect후 다시 연결해보세요.");
			mb.open();
			return;
		}
	}*/
	
	public static AndroSuker_Execute getmExecute() {
		return mExecute;
	}

	public static void setmExecute(AndroSuker_Execute mExecute) {
		AndroSuker_Main_Layout.mExecute = mExecute;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		tabVisibleHashMap = (HashMap<String, Boolean>)arg;		
		MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.ICON_INFORMATION | SWT.OK);
		mb.setText("Alert!");
		mb.setMessage("Program을 다시 실행시켜주세요. OK를 누르세요.");
		int buttonID = mb.open();
		switch(buttonID) {
		case SWT.OK:
			mainShell.close();
			break;
		}
	}
}

class BorderLayout extends Layout {
	// Region constants.
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int CENTER = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;

	/**
	 * Indicates the region that a control belongs to.
	 *  
	 */
	public static class BorderData {
		public int region = CENTER; // default.

		public BorderData() {
		}

		public BorderData(int region) {
			this.region = region;
		}
	}

	// Controls in all the regions.
	public Control[] controls = new Control[5];

	// Cached sizes.
	Point[] sizes;

	// Preferred width and height
	int width;
	int height;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Layout#computeSize(org.eclipse.swt.widgets.Composite,
	 *      int, int, boolean)
	 */
	protected Point computeSize(
			Composite composite,
			int wHint,
			int hHint,
			boolean flushCache) {

		if (sizes == null || flushCache == true)
			refreshSizes(composite.getChildren());
		int w = wHint;
		int h = hHint;
		if (w == SWT.DEFAULT)
			w = width;
		if (h == SWT.DEFAULT)
			h = height;

		return new Point(w, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Layout#layout(org.eclipse.swt.widgets.Composite,
	 *      boolean)
	 */
	protected void layout(Composite composite, boolean flushCache) {
		if (flushCache || sizes == null)
			refreshSizes(composite.getChildren());

		Rectangle clientArea = composite.getClientArea();

		// Enough space for all.
		if (controls[NORTH] != null) {
			controls[NORTH].setBounds(
					clientArea.x,
					clientArea.y,
					clientArea.width,
					sizes[NORTH].y);
		}
		if (controls[SOUTH] != null) {
			controls[SOUTH].setBounds(
					clientArea.x,
					clientArea.y + clientArea.height - sizes[SOUTH].y,
					clientArea.width,
					sizes[SOUTH].y);
		}
		if (controls[WEST] != null) {
			controls[WEST].setBounds(
					clientArea.x,
					clientArea.y + sizes[NORTH].y,
					sizes[WEST].x,
					clientArea.height - sizes[NORTH].y - sizes[SOUTH].y);
		}
		if (controls[EAST] != null) {
			controls[EAST].setBounds(
					clientArea.x + clientArea.width - sizes[EAST].x,
					clientArea.y + sizes[NORTH].y,
					sizes[EAST].x,
					clientArea.height - sizes[NORTH].y - sizes[SOUTH].y);
		}
		if (controls[CENTER] != null) {
			controls[CENTER].setBounds(
					clientArea.x + sizes[WEST].x,
					clientArea.y + sizes[NORTH].y,
					clientArea.width - sizes[WEST].x - sizes[EAST].x,
					clientArea.height - sizes[NORTH].y - sizes[SOUTH].y);
		}

	}

	private void refreshSizes(Control[] children) {
		for (int i = 0; i < children.length; i++) {
			Object layoutData = children[i].getLayoutData();
			if (layoutData == null || (!(layoutData instanceof BorderData)))
				continue;
			BorderData borderData = (BorderData) layoutData;
			if (borderData.region < 0 || borderData.region > 4) // Invalid.
				continue;
			controls[borderData.region] = children[i];
		}

		width = 0;
		height = 0;

		if (sizes == null)
			sizes = new Point[5];

		for (int i = 0; i < controls.length; i++) {
			Control control = controls[i];
			if (control == null) {
				sizes[i] = new Point(0, 0);
			} else {
				sizes[i] = control.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
			}
		}

		width = Math.max(width, sizes[NORTH].x);
		width =
				Math.max(width, sizes[WEST].x + sizes[CENTER].x + sizes[EAST].x);
		width = Math.max(width, sizes[SOUTH].x);

		height =
				Math.max(Math.max(sizes[WEST].y, sizes[EAST].y), sizes[CENTER].y)
				+ sizes[NORTH].y
				+ sizes[SOUTH].y;
	}
}

public class AndroSuker_MainCls	implements AndroSuker_Definition {
	static Shell shell;
	static HashMap<String, Boolean> tabVisibleHashMap;
	
	public static void main( String args[] )
	{
		Display display = new Display();
		try	{
		    shell = new Shell(display, SWT.TITLE | SWT.MIN | SWT.CLOSE);
		    tabVisibleHashMap = new HashMap<String, Boolean>();
		    try	{
			    final AndroSuker_Main_Layout mainFrame = new AndroSuker_Main_Layout(shell, tabVisibleHashMap);
			    AndroSuker_Menu menu = new AndroSuker_Menu();
				menu.makeMenu(mainFrame, shell, tabVisibleHashMap);
				
			    shell.setLayout(new BorderLayout());
			    shell.setImage(new Image(display, "icon\\image.png"));
			    shell.setText("Andro Suker Tool");
			    	    
			    ShellListener shellListener = new ShellListener()
			    {
					@Override
					public void shellActivated(ShellEvent arg0) {
						// TODO Auto-generated method stub				
					}
					@Override
					public void shellClosed(ShellEvent arg0) {
						// TODO Auto-generated method stub
						mainFrame.__finallyIniFileWrite();
					}
					@Override
					public void shellDeactivated(ShellEvent arg0) {
						// TODO Auto-generated method stub				
					}
					@Override
					public void shellDeiconified(ShellEvent arg0) {
						// TODO Auto-generated method stub				
					}
					@Override
					public void shellIconified(ShellEvent arg0) {
						// TODO Auto-generated method stub				
					}
			    };
			    
			    shell.addShellListener(shellListener);
			    
			    shell.pack();
			    shell.open();
			    shell.setSize(TOTAL_SIZE_WIDTH, TOTAL_SIZE_HEIGHT);
			    
			    while (!shell.isDisposed()) {
			      if (!display.readAndDispatch()) {
			        display.sleep();
			      }
			    }
		    } finally	{
				if (AndroSuker_Main_Layout.getmExecute().process != null)	{
					AndroSuker_Main_Layout.getmExecute().process.destroy();
				}
				if (!shell.isDisposed())	{
					shell.dispose();
				}
		    }
		} finally	{
		    display.dispose();
		}
		System.exit(0);
	}
	public static Shell getShellInstance()	{
		return shell;
	}
}