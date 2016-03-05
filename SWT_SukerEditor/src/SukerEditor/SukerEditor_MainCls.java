package SukerEditor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;


public class SukerEditor_MainCls {

	protected static Shell shell;
	protected static CTabFolder tabEditFrame;
	
	private String		strOpenFolderPath;
	private String 		strSaveFolderPath;
	private ComboViewer comboViewer;
	private Combo 		cbKeyWord;
	
	private List<MenuItem>			mMenuItemList = new ArrayList<MenuItem>();
	private List<CTabItem> 			mTabItemList = new ArrayList<CTabItem>();
	
	private SukerEditor_TabManager			tabManager;	
	private SukerEditor_FileControl			fileHandler;
	private SukerEditor_MenuDlg 			menuDlg;
	
	private Text text1;
	private Text text2;
	private Text text3;
	private Text text4;
	
	private Label lblCurrentFilePathName;
	
	private Menu menu_Popup;
	private MenuItem mntmPopupMenu_Save;
	private MenuItem mntmPopupMenu_SaveAs;
	private MenuItem mntmPopupMenu_Close;
	private MenuItem mntmPopupMenu_CloseAll;
	
	private StyledTextPrintOptions 		styledTextOptions;
	private Printer 					printer;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SukerEditor_MainCls window = new SukerEditor_MainCls();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 * @throws Exception 
	 */
	public void open() throws Exception {
		Display display = Display.getDefault();
		createContents();
		initWindowSetting();
		
		ShellListener shellListener = new ShellListener()
	    {
			@Override
			public void shellActivated(ShellEvent arg0) {
				// TODO Auto-generated method stub				
			}
			@Override
			public void shellClosed(ShellEvent arg0) {
				tabManager.refreshWorkedFilePathList();
				__onDestoy();
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
	    
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		if (printer != null)
			printer.dispose();
		
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {				
		shell = new Shell();
		shell.setSize(1200, 800);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		{
			//---------------------------------------------------Upper Group----------------------------------------------------------
			Group grpKeywordInput = new Group(shell, SWT.NONE);
			grpKeywordInput.setLayoutData(BorderLayout.NORTH);
			grpKeywordInput.setLayout(new GridLayout(8, false));
			
			Label lblSearchKeywords = new Label(grpKeywordInput, SWT.NONE);
			lblSearchKeywords.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
			lblSearchKeywords.setText("Search Keywords");
			
			Button btnTextClear = new Button(grpKeywordInput, SWT.NONE);
			btnTextClear.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					text1.setText("");
					text2.setText("");
					text3.setText("");
					text4.setText("");
				}
			});
			btnTextClear.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
			btnTextClear.setText("Text Field Clear");
			
			Button btnComboClear = new Button(grpKeywordInput, SWT.NONE);
			btnComboClear.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					cbKeyWord.removeAll();
				}
			});
			btnComboClear.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			btnComboClear.setText("ComboBox Clear");
			
			Button btnDo = new Button(grpKeywordInput, SWT.NONE);			
			btnDo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String[]	textList = {text1.getText(), text2.getText(), text3.getText(), text4.getText()};
					updateComboList(textList);
					SukerEditor_TextFieldControl textControl;
					textControl = tabManager.getHashMapForCurrentWorkingStyledText(tabEditFrame.getSelection().getText());
					textControl.setKeywords(textList);
					textControl.refresh();
					
					textControl.getCurrentTextHandle().setFocus();
				}
			});
			btnDo.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 2));
			btnDo.setText("Search Go");
			
			Button btnCheckButton_ShowLineNum = new Button(grpKeywordInput, SWT.CHECK);
			btnCheckButton_ShowLineNum.setEnabled(false);
			btnCheckButton_ShowLineNum.setText("Show Line Number");
			
			Button btnNewButton = new Button(grpKeywordInput, SWT.NONE);
			btnNewButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					tabEditFrame.setSelection(tabManager.addTabList(tabEditFrame));
					tabManager.setActiveTabIndex(tabEditFrame.getSelectionIndex());	
					SukerEditor_StatusBarDisplay.updateName("");
				}
			});
			btnNewButton.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 2));
			btnNewButton.setText("NewTab");
			
			text1 = new Text(grpKeywordInput, SWT.BORDER);
			text1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			text2 = new Text(grpKeywordInput, SWT.BORDER);
			text2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			text3 = new Text(grpKeywordInput, SWT.BORDER);
			text3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			text4 = new Text(grpKeywordInput, SWT.BORDER);
			text4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			/*ModifyListener listener = new ModifyListener() {
				public void modifyText(ModifyEvent e) {					
				}
			};

			text1.addModifyListener(listener);
			text2.addModifyListener(listener);
			text3.addModifyListener(listener);
			text4.addModifyListener(listener);*/
			    
			comboViewer = new ComboViewer(grpKeywordInput, SWT.NONE);
			cbKeyWord = comboViewer.getCombo();
			cbKeyWord.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					int text1Length = text1.getText().length();
					int text2Length = text2.getText().length();
					int text3Length = text3.getText().length();
					int text4Length = text4.getText().length();
					int selectIndex = cbKeyWord.getSelectionIndex();
					String selectStr = cbKeyWord.getItem(selectIndex);

					if (text1Length < 1)
						text1.setText(selectStr);
					else if (text2Length < 1)
						text2.setText(selectStr);
					else if (text3Length < 1)
						text3.setText(selectStr);
					else if (text4Length < 1)
						text4.setText(selectStr);
					else
						;
				}
			});
			cbKeyWord.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			Button btnCheckButton = new Button(grpKeywordInput, SWT.CHECK);
			btnCheckButton.setEnabled(false);
			btnCheckButton.setText("TBD");
		}
		
		lblCurrentFilePathName = new Label(shell, SWT.NONE);
		lblCurrentFilePathName.setLayoutData(BorderLayout.SOUTH);
		lblCurrentFilePathName.setText("CurrentFilePath");
		
		//---------------------------------------------------TabFolder----------------------------------------------------------
		tabEditFrame = new CTabFolder(shell, SWT.BORDER);// | SWT.CLOSE | SWT.FLAT);
		tabEditFrame.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));		
		tabEditFrame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 3)	{	//right-click					
					CTabFolder tabFolder = (CTabFolder)e.widget;
					CTabItem tabItem = tabFolder.getItem(new Point(e.x, e.y));
					
					if (tabItem == null)	{
						mntmPopupMenu_Save.setEnabled(false);
						mntmPopupMenu_SaveAs.setEnabled(false);
						mntmPopupMenu_Close.setEnabled(false);			
					}
					else	{
						mntmPopupMenu_Save.setEnabled(true);
						mntmPopupMenu_SaveAs.setEnabled(true);
						mntmPopupMenu_Close.setEnabled(true);
						tabFolder.setSelection(tabItem);
						if (!tabManager.getHashMapForCurrentWorkingFilesStatus(tabItem.getText()))
							SukerEditor_StatusBarDisplay.updateName("");							
						else
							SukerEditor_StatusBarDisplay.updateName(tabManager.getHashMapForCurrentWorkingFilesPath(tabItem.getText()));
					}
				} else	{				//left-click
					int currentIndex = tabEditFrame.getSelectionIndex();
					if (tabManager.getActiveTabIndex() != currentIndex)	{
						CTabItem tabItem = tabEditFrame.getSelection();
						tabManager.setActiveTabIndex(currentIndex);
						if (!tabManager.getHashMapForCurrentWorkingFilesStatus(tabItem.getText()))
							SukerEditor_StatusBarDisplay.updateName("");							
						else
							SukerEditor_StatusBarDisplay.updateName(tabManager.getHashMapForCurrentWorkingFilesPath(tabItem.getText()));
					}
				}			
			}			
		});
		tabEditFrame.setLayoutData(BorderLayout.CENTER);
		tabEditFrame.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		tabEditFrame.setSimple(false);
		tabEditFrame.setUnselectedImageVisible(false);
		tabEditFrame.setUnselectedCloseVisible(false);
		tabEditFrame.setFocus();

/*		CTabItem tbtmDefault = new CTabItem(tabEditFrame, SWT.CLOSE);
		tbtmDefault.setFont(SWTResourceManager.getFont("±¼¸²", 10, SWT.BOLD));
		tbtmDefault.setShowClose(true);
		tbtmDefault.setText("Default");

		StyledText styledText_default = new StyledText(tabEditFrame, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		styledText_default.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
		styledText_default.setTopMargin(5);
		styledText_default.setRightMargin(10);
		styledText_default.setLeftMargin(5);
		styledText_default.setText("test test");
		styledText_default.setBlockSelection(true);
		tbtmDefault.setControl(styledText_default);		
		mTabItemList.add(tbtmDefault);*/
		
		//---------------------------------------------------Final----------------------------------------------------------
		fileHandler = new SukerEditor_FileControl();
		tabManager = new SukerEditor_TabManager(shell, mTabItemList, fileHandler, mMenuItemList);
		tabManager.createDragTarget(tabEditFrame);
		menuDlg = new SukerEditor_MenuDlg();
		new SukerEditor_StatusBarDisplay(lblCurrentFilePathName);
		
		//-----------------------------------------------------------------------------------------------------------------
		//---------------------------------------------------Popup Menu----------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------
		menu_Popup = new Menu(tabEditFrame);
		//menu_Popup.setEnabled(false);
		tabEditFrame.setMenu(menu_Popup);
		
		mntmPopupMenu_Save = new MenuItem(menu_Popup, SWT.NONE);
		mntmPopupMenu_Save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveTab();
			}
		});
		mntmPopupMenu_Save.setText("Save");
		
		mntmPopupMenu_SaveAs = new MenuItem(menu_Popup, SWT.NONE);
		mntmPopupMenu_SaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveAsTab();
			}
		});
		mntmPopupMenu_SaveAs.setText("Save As ...");
		
		new MenuItem(menu_Popup, SWT.SEPARATOR);
		
		mntmPopupMenu_Close = new MenuItem(menu_Popup, SWT.NONE);
		mntmPopupMenu_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabManager.removeTabInList(tabEditFrame.getSelection().getText());
				tabEditFrame.getSelection().getControl().dispose();
				tabEditFrame.getSelection().dispose();
			}
		});
		mntmPopupMenu_Close.setText("Close");
		
		mntmPopupMenu_CloseAll = new MenuItem(menu_Popup, SWT.NONE);
		mntmPopupMenu_CloseAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabManager.removeAllTabInList();
			}
		});
		mntmPopupMenu_CloseAll.setText("Close All");
		tabManager.setActiveTabIndex(-1);
		
		//-----------------------------------------------------------------------------------------------------------------
		//---------------------------------------------------Menu Bar------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_File = new Menu(mntmFile);
		mntmFile.setMenu(menu_File);
		
		MenuItem mntmFile_Open = new MenuItem(menu_File, SWT.NONE);
		mntmFile_Open.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String temp;
				temp = menuDlg.OpenFile(strOpenFolderPath);
				
				if (temp != null && temp.length() > 1)
					strOpenFolderPath = temp;
				
				String textInitValue = null;				
				
				try {
					textInitValue = fileHandler.readFileByBurst(strOpenFolderPath);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				tabEditFrame.setSelection(tabManager.addTabList(tabEditFrame, strOpenFolderPath, textInitValue, true));
				SukerEditor_StatusBarDisplay.updateName(strOpenFolderPath);
			}
		});
		mntmFile_Open.setText("Open File...             Ctrl+O");	
		mntmFile_Open.setAccelerator(SWT.CTRL + 'o');
		
		new MenuItem(menu_File, SWT.SEPARATOR);
		
		MenuItem mntmFile_Close = new MenuItem(menu_File, SWT.NONE);
		mntmFile_Close.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabManager.removeTabInList(tabEditFrame.getSelection().getText());
				tabEditFrame.getSelection().getControl().dispose();
				tabEditFrame.getSelection().dispose();
			}
		});
		mntmFile_Close.setEnabled(false);
		mntmFile_Close.setText("Close                     Ctrl+Q");
		mntmFile_Close.setAccelerator(SWT.CTRL + 'q');
		mMenuItemList.add(mntmFile_Close);
		
		MenuItem mntmFile_CloseAll = new MenuItem(menu_File, SWT.NONE);
		mntmFile_CloseAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tabManager.removeAllTabInList();
			}
		});
		mntmFile_CloseAll.setEnabled(false);
		mntmFile_CloseAll.setText("Close All                Ctrl+Alt+Q");
		mMenuItemList.add(mntmFile_CloseAll);
		
		new MenuItem(menu_File, SWT.SEPARATOR);
		
		MenuItem mntmFile_Save = new MenuItem(menu_File, SWT.NONE);
		mntmFile_Save.setEnabled(false);
		mntmFile_Save.setAccelerator(SWT.CTRL + 's');
		mntmFile_Save.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveTab();
			}
		});
		mntmFile_Save.setText("Save                      Ctrl+S");
		mMenuItemList.add(mntmFile_Save);
		
		MenuItem mntmFile_SaveAs = new MenuItem(menu_File, SWT.NONE);
		mntmFile_SaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveAsTab();
			}
		});
		mntmFile_SaveAs.setEnabled(false);
		mntmFile_SaveAs.setText("Save As...");
		mMenuItemList.add(mntmFile_SaveAs);
		
		new MenuItem(menu_File, SWT.SEPARATOR);
		
		MenuItem mntmFile_Print = new MenuItem(menu_File, SWT.NONE);
		mntmFile_Print.setEnabled(false);
		mntmFile_Print.setText("Print...                    Ctrl+P");
		mMenuItemList.add(mntmFile_Print);
		
		new MenuItem(menu_File, SWT.SEPARATOR);
		
		MenuItem mntmFile_Exit = new MenuItem(menu_File, SWT.NONE);
		mntmFile_Exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		mntmFile_Exit.setText("E&xit");
		mntmFile_Exit.setAccelerator(SWT.CTRL + 'x');
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");	
		
		Menu menu_Help = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_Help);
		
		MenuItem mntmHelp_About = new MenuItem(menu_Help, SWT.NONE);
		mntmHelp_About.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SukerEditor_AboutDialog aboutDlg = new SukerEditor_AboutDialog(shell, SWT.DIALOG_TRIM  | SWT.APPLICATION_MODAL);
				aboutDlg.open();
			}
		});
		mntmHelp_About.setText("About");
	}
	
	private void saveTab()	{
		CTabItem currentItem = tabEditFrame.getSelection();
		String itemName = currentItem.getText();
		Boolean isNewTab = !(tabManager.getHashMapForCurrentWorkingFilesStatus(itemName));
		
		if (isNewTab)	{
			String temp = menuDlg.SaveFile(strSaveFolderPath);
			if (temp != null && temp.length() > 1)	{
				SukerEditor_TextFieldControl sTextTemp;
				strSaveFolderPath = temp;
				sTextTemp = tabManager.getHashMapForCurrentWorkingStyledText(itemName);
				tabManager.updateTabStatusInList(currentItem, strSaveFolderPath, itemName, SukerEditor_FileControl.getOnlyFileName(strSaveFolderPath), true, sTextTemp);
				fileHandler.saveFileForText(shell, strSaveFolderPath, ((StyledText)currentItem.getControl()).getText());
				tabManager.updateHashMapForCurrentWorkingEdited(currentItem, true);
			}
		}	else	{
			String fullPath = tabManager.getHashMapForCurrentWorkingFilesPath(itemName);
			fileHandler.saveFileForText(shell, fullPath, ((StyledText)currentItem.getControl()).getText());
			tabManager.updateHashMapForCurrentWorkingEdited(currentItem, true);
		}
	}
	private void saveAsTab()	{
		CTabItem currentItem = tabEditFrame.getSelection();
		String itemName = currentItem.getText();
		
		String temp = menuDlg.SaveFile(strSaveFolderPath);				
		if (temp != null && temp.length() > 1)	{
			SukerEditor_TextFieldControl sTextTemp;
			strSaveFolderPath = temp;
			sTextTemp = tabManager.getHashMapForCurrentWorkingStyledText(itemName);
			tabManager.updateTabStatusInList(currentItem, strSaveFolderPath, itemName, SukerEditor_FileControl.getOnlyFileName(strSaveFolderPath), true, sTextTemp);
			fileHandler.saveFileForText(shell, strSaveFolderPath, ((StyledText)currentItem.getControl()).getText());
			tabManager.updateHashMapForCurrentWorkingEdited(currentItem, true);
		}
	}

	/*public void print() {
		SukerEditor_TextFieldControl sTextTemp;	
		CTabItem currentItem = tabEditFrame.getSelection();
		String itemName = currentItem.getText();
		sTextTemp = tabManager.getHashMapForCurrentWorkingStyledText(itemName);
		
		styledTextOptions = new StyledTextPrintOptions();
		styledTextOptions.footer = StyledTextPrintOptions.SEPARATOR + StyledTextPrintOptions.PAGE_TAG
	        + StyledTextPrintOptions.SEPARATOR + "Confidential";
	    
		if (printer == null)
			printer = new Printer();
		styledTextOptions.header = StyledTextPrintOptions.SEPARATOR + filename + StyledTextPrintOptions.SEPARATOR;
		(sTextTemp.getCurrentTextHandle()).print(printer, styledTextOptions).run();		
	}*/

	public static Shell getShellInstance() {
		return shell;
	}
	
	private void initWindowSetting() throws Exception	{
		String resultStr = "";
		List<String> readList;
		String[] comboFillList;		
		
		strOpenFolderPath = "";
		strSaveFolderPath = "";
		
		readList = SukerEditor_INIFile.readFile(SukerEditor_INIFile.INI_FILE_PATH);		

		if (readList != null){
			strSaveFolderPath = SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_SAVE_FOLDER_PATH");
			strOpenFolderPath = SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_OPEN_FOLDER_PATH");
			
			resultStr = SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_KEYWORDS");
			if (resultStr != null && resultStr.length() > 1)	{
				comboFillList = resultStr.split(",");
				comboViewer.add(comboFillList);
				//cbKeyWord.select(0);					
			}
			
			/*if (SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_COMBOMULTISELECT").equals("true"))
				comboSelectMultiOn = true;
			else
				comboSelectMultiOn = false;
			btnCheckButton.setSelection(comboSelectMultiOn);	*/		
			
			int workedFileCount;
			String	temp = SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_WORKEDFILECOUNT");
			if (temp == null || temp.length() < 1)
				workedFileCount = 0;
			else
				workedFileCount = Integer.parseInt(temp);
			
			for(int i = 0; i < workedFileCount; i++)	{
				resultStr = SukerEditor_INIFile.readIniFile(readList, "LOGEDITOR_WORKEDFILEPATHLIST"+i);
				if (resultStr != null && resultStr.length() > 1)	{								
					tabManager.setWorkedFilePathList(resultStr);
				}
			}
		}
	}	
	
	public void __onDestoy()	{
		List<String> 	writeList = new ArrayList<String>();
		String			comboStr;
		
		if (strSaveFolderPath.length() <= 1)
			strSaveFolderPath = "";
		SukerEditor_INIFile.writeIniFile("LOGEDITOR_SAVE_FOLDER_PATH", fileHandler.getOnlyFolderName(strSaveFolderPath), writeList);
		
		if (strOpenFolderPath.length() <= 1)
			strOpenFolderPath = "";
		SukerEditor_INIFile.writeIniFile("LOGEDITOR_OPEN_FOLDER_PATH", fileHandler.getOnlyFolderName(strOpenFolderPath), writeList);
						
		//comboStr = LogViewer_INIFile.makeComboList(mComboList);
		comboStr = SukerEditor_INIFile.makeComboList(cbKeyWord);
		
		if (comboStr.length() > 0)
			SukerEditor_INIFile.writeIniFile("LOGEDITOR_KEYWORDS", comboStr, writeList);
		
		/*if (comboSelectMultiOn)
			SukerEditor_INIFile.writeIniFile("LOGEDITOR_COMBOMULTISELECT", "true", writeList);
		else
			SukerEditor_INIFile.writeIniFile("LOGEDITOR_COMBOMULTISELECT", "false", writeList);*/
		
		int workedFilePathListSize = tabManager.getWorkdedFilePathList().size();
		SukerEditor_INIFile.writeIniFile("LOGEDITOR_WORKEDFILECOUNT", String.valueOf(workedFilePathListSize), writeList);

		for (int i = 0; i < workedFilePathListSize; i++)	{
			String temp = "";
			temp = tabManager.getWorkdedFilePathList().get(i).toString();
			if (temp.length() > 0)
				SukerEditor_INIFile.writeIniFile("LOGEDITOR_WORKEDFILEPATHLIST"+i, temp, writeList);
		}		
		
		__finallyIniFileWrite(writeList);
	}
	
	private void __finallyIniFileWrite(List<String> writeList)	{		
		try {
			SukerEditor_INIFile.writeFile(writeList, SukerEditor_INIFile.INI_FILE_PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	private void updateComboList(String[] textList)	{
		List<String>	tempStrList = new ArrayList<String>();
		int				comboSize = this.cbKeyWord.getItemCount();
		int				nonEmptyTextCnt = textList.length;
		
		for(int i = 0; i < comboSize; i++)	{
			tempStrList.add(i, this.cbKeyWord.getItem(i));
		}
		
		for(int i = 0; i < nonEmptyTextCnt; i++)	{
			if (textList[i].length() > 0)	{
				if (!tempStrList.contains(textList[i]))	{
					this.cbKeyWord.add(textList[i]);
				}
			}
		}
	}
}
