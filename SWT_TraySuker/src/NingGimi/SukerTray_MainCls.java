package NingGimi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;

public class SukerTray_MainCls {
	private Display display;
	protected Shell shell;
	private Text text_id;
	private Text text_password;
	private CCombo combo_alphaBlending;
	
	private int				alphaValue = 255;
	private final Boolean 	DEBUG_MODE = false;
	private Table 			tableLauncher;
	private Group 			groupExpanded;
	private Label 			lblInfo;
	
	private SukerTray_TableManager	tableManager;
	private SukerTray_Launcher		launchManager;
	
	private final int	SHELLSIZE_NORMALWIDTH = 500;
	private final int	SHELLSIZE_NORMALHEIGHT = 155;
	private final int	SHELLSIZE_EXPANDEDWIDTH = 600;
	private final int	SHELLSIZE_EXPANDEDHEIGHT = 530;
	private final int	GROUPSIZE_WIDTH = 470;
	private final int	GROUPSIZE_HEIGHT = 350;
	
	public static void main(String[] args) {
		try {
			SukerTray_MainCls window = new SukerTray_MainCls();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws Exception {
		display = Display.getDefault();
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
				__onSaveConfiguration();
				__onSaveTableContents();
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
	    shell.setAlpha(alphaValue);
	    new Label(shell, SWT.NONE);
	    tableManager.createTableItems(getTableTitle(), getTableContents());
	    
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {					
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		System.exit(0);
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(SHELLSIZE_NORMALWIDTH, SHELLSIZE_NORMALHEIGHT);
		//shell.setSize(500, 500);
		shell.setImage(new Image(display, "icon\\image.png"));
		shell.setText("Suker Tray AnHuiNanGunHyang");
		shell.setLayout(new GridLayout(5, false));
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setText("  ");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("sso ID");
		
		text_id = new Text(shell, SWT.BORDER);
		GridData gd_text_id = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_id.widthHint = 205;
		text_id.setLayoutData(gd_text_id);
		
		Button btnSSOLogin = new Button(shell, SWT.NONE);
		btnSSOLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				new SukerBrowser_ssoLogin(display, text_id.getText(), text_password.getText());
			}
		});
		btnSSOLogin.setText("SSO login");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Password");
		
		text_password = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		GridData gd_text_password = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_text_password.widthHint = 205;
		text_password.setLayoutData(gd_text_password);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Alpha");
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		
		Button btnAdvance = new Button(shell, SWT.NONE);
		btnAdvance.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (shell.getSize().y == SHELLSIZE_NORMALHEIGHT)	{
					groupExpanded.setVisible(true);
					shell.setSize(SHELLSIZE_EXPANDEDWIDTH, SHELLSIZE_EXPANDEDHEIGHT);
					lblInfo.setText("테이블 내용 수정은 마우스 우클릭으로...");
				} else	{
					groupExpanded.setVisible(false);
					shell.setSize(SHELLSIZE_NORMALWIDTH, SHELLSIZE_NORMALHEIGHT);
					lblInfo.setText("==> Quick Launch Box 열기");
				}
			}
		});
		btnAdvance.setText("Advance");
		
		lblInfo = new Label(shell, SWT.NONE);
		lblInfo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblInfo.setText("==> Quick Launch Box 열기");
		
		combo_alphaBlending = new CCombo(shell, SWT.BORDER);
		combo_alphaBlending.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		combo_alphaBlending.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectIndex = combo_alphaBlending.getSelectionIndex();
				String selectStr = combo_alphaBlending.getItem(selectIndex);
				alphaValue = Integer.parseInt(selectStr);
				shell.setAlpha(alphaValue);
			}
		});
		new Label(shell, SWT.NONE);
		new Label(shell, SWT.NONE);
		
		groupExpanded = new Group(shell, SWT.NONE);
		groupExpanded.setLayout(new GridLayout(2, false));
		GridData gd_groupExpanded = new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 3);
		gd_groupExpanded.heightHint = GROUPSIZE_HEIGHT;
		gd_groupExpanded.widthHint = GROUPSIZE_WIDTH;
		groupExpanded.setVisible(false);
		groupExpanded.setLayoutData(gd_groupExpanded);
		
		Label lblCurrentSelection = new Label(groupExpanded, SWT.NONE);
		launchManager = new SukerTray_Launcher(lblCurrentSelection);
		new Label(groupExpanded, SWT.NONE);
		
			    tableLauncher = new Table(groupExpanded, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI | SWT.HIDE_SELECTION);
			    tableLauncher.setFont(SWTResourceManager.getFont("굴림", 10, SWT.NORMAL));
			    tableLauncher.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			    tableLauncher.setHeaderVisible(true);
			    tableLauncher.setLinesVisible(true);
			    tableManager = new SukerTray_TableManager(tableLauncher, launchManager);
			    
			    Group group = new Group(groupExpanded, SWT.NONE);
			    group.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false, 1, 1));
			    group.setLayout(new GridLayout(1, false));
			    
			    Button btnDo = new Button(group, SWT.NONE);
			    btnDo.setFont(SWTResourceManager.getFont("굴림", 10, SWT.BOLD));
			    btnDo.addSelectionListener(new SelectionAdapter() {
			    	@Override
			    	public void widgetSelected(SelectionEvent e) {
			    		launchManager.run();
			    	}
			    });
			    btnDo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			    btnDo.setBounds(0, 0, 77, 22);
			    btnDo.setText("Do");
			    
			    @SuppressWarnings("unused")
				Label label = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
			    
			    Button btnNew = new Button(group, SWT.NONE);
			    btnNew.addSelectionListener(new SelectionAdapter() {
			    	@Override
			    	public void widgetSelected(SelectionEvent e) {
			    		tableManager.addTableItem();
			    	}
			    });
			    btnNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			    btnNew.setBounds(0, 0, 77, 22);
			    btnNew.setText("Add");
			    
			    Button btnDel = new Button(group, SWT.NONE);
			    btnDel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
			    btnDel.addSelectionListener(new SelectionAdapter() {
			    	@Override
			    	public void widgetSelected(SelectionEvent e) {
			    		tableManager.deleteTableItem();
			    	}
			    });
			    btnDel.setText("Del");
			    
			    @SuppressWarnings("unused")
				Label label_1 = new Label(group, SWT.SEPARATOR | SWT.HORIZONTAL);
			    
			    Button btnNewButton = new Button(group, SWT.NONE);
			    GridData gd_btnNewButton = new GridData(SWT.CENTER, SWT.BOTTOM, false, false, 1, 1);
			    gd_btnNewButton.widthHint = 50;
			    gd_btnNewButton.heightHint = 45;
			    btnNewButton.setLayoutData(gd_btnNewButton);
			    btnNewButton.setText("UP");
			    btnNewButton.addSelectionListener(new SelectionAdapter() {
			    	@Override
			    	public void widgetSelected(SelectionEvent e) {
			    		tableManager.goUpTableItem();
			    	}
			    });			    
			    
			    Button btnNewButton_1 = new Button(group, SWT.NONE);
			    GridData gd_btnNewButton_1 = new GridData(SWT.CENTER, SWT.BOTTOM, false, false, 1, 1);
			    gd_btnNewButton_1.widthHint = 50;
			    gd_btnNewButton_1.heightHint = 45;
			    btnNewButton_1.setLayoutData(gd_btnNewButton_1);
			    btnNewButton_1.setText("Down");
			    btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			    	@Override
			    	public void widgetSelected(SelectionEvent e) {
			    		tableManager.goDownTableItem();
			    	}
			    });			    
			    
		new Label(shell, SWT.NONE);
		for (int i = 150; i < 256; i++)	{
			combo_alphaBlending.add(String.valueOf(i));
		}
	}
	
	private void initWindowSetting() throws Exception {
		List<String> readList;

		readList = Suker_INIFile.readFile(Suker_INIFile.INI_FILE_PATH);		

		if (readList != null){
			String alphaStr;
			text_id.setText(Suker_INIFile.readIniFile(readList, "TRAY_SSO_ID"));
			if (DEBUG_MODE)
				text_password.setText(Suker_INIFile.readIniFile(readList, "TRAY_PASSWORD"));
			else
				text_password.setText("");
			alphaStr = Suker_INIFile.readIniFile(readList, "TRAY_ALAPHA");
			alphaValue = Integer.parseInt(alphaStr);
			combo_alphaBlending.setText(alphaStr);
		}
	}	
	
	private List<String> getTableTitle() throws Exception {
		List<String> readList;
		List<String> tableList = new ArrayList<String>();
		int listSize = 0;
		
		readList = Suker_INIFile.readFile(Suker_INIFile.TABLE_FILE_PATH);		
		listSize = readList.size();
		
		if (readList != null){
			for (int i = 0; i < listSize; i++)	{
				tableList.add(Suker_INIFile.readIniFile(readList, "TRAY_TABLE_TITLE_"+i));
			}			
		}
		return tableList;
	}
	private List<String> getTableContents() throws Exception {
		List<String> readList;
		List<String> tableList = new ArrayList<String>();
		int listSize = 0;
		
		readList = Suker_INIFile.readFile(Suker_INIFile.TABLE_FILE_PATH);		
		listSize = readList.size();
		
		if (readList != null){
			for (int i = 0; i < listSize; i++)	{
				tableList.add(Suker_INIFile.readIniFile(readList, "TRAY_TABLE_CONTENT_"+i));
			}			
		}
		return tableList;
	}
	
	public void __onSaveConfiguration()	{
		List<String> 	writeList = new ArrayList<String>();
		String			tempStr;

		tempStr = text_id.getText();		
		if (tempStr.length() <= 1 || tempStr == null)
			tempStr = "";
		Suker_INIFile.writeIniFile("TRAY_SSO_ID", tempStr, writeList);
		
		tempStr = text_password.getText();
		if (tempStr.length() <= 1 || tempStr == null)
			tempStr = "";
		
		if (DEBUG_MODE)
			Suker_INIFile.writeIniFile("TRAY_PASSWORD", tempStr, writeList);
		else
			Suker_INIFile.writeIniFile("TRAY_PASSWORD", "", writeList);
						
		tempStr = String.valueOf(alphaValue);
		if (tempStr.length() <= 1 || tempStr == null)
			tempStr = "";
		Suker_INIFile.writeIniFile("TRAY_ALAPHA", tempStr, writeList);
		
		__finallyIniFileWrite(writeList, Suker_INIFile.INI_FILE_PATH);
	}
	
	public void __onSaveTableContents()	{
		List<String> 	writeList = new ArrayList<String>();
		List<String>	titleList;
		List<String>	contentsList;
		String			tempStr;
		int				size;
		
		titleList = tableManager.getTableTitleListing();
		contentsList = tableManager.getTableContentsListing();
		
		size = titleList.size();
		for (int i = 0; i < size; i++)	{
			tempStr = titleList.get(i);
			if (tempStr != null)				
				Suker_INIFile.writeIniFile("TRAY_TABLE_TITLE_"+i, tempStr, writeList);
		}
		size = contentsList.size();
		for (int i = 0; i < size; i++)	{
			tempStr = contentsList.get(i);
			if (tempStr != null)				
				Suker_INIFile.writeIniFile("TRAY_TABLE_CONTENT_"+i, tempStr, writeList);
		}		
		__finallyIniFileWrite(writeList, Suker_INIFile.TABLE_FILE_PATH);
	}
	
	private void __finallyIniFileWrite(List<String> writeList, String filePath)	{		
		try {
			Suker_INIFile.writeFile(writeList, filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
