package sukerPkg;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class AndroSuker_TabItem_FastBoot implements AndroSuker_Definition {
	private static AndroSuker_Execute 	mExe;
	private static Composite 			Fastboot_composite;	
	
	private List<String> 	readList;
	private List<String> 	writeList;	
	private Text 			fastbootPath;	
	private Text 			imagesPath;
	private Text 			modelInfoStr;
	private Text			srcFilePath;
	private Text			srcFilePath2;
	private Text			destFilePath;
	private Table 			CmdTable;	
	private int 			values_cnt;
	private int 			modelInfoIndex;
	
	private Button			btnFastbootBrowser;
	private Button			btnImagesBrowser;
	private Button			btnOpenScriptFolder;
	private Button			btnAddTable;
	private Button			btnDelTable;
	private Button			btnSelectTable;
	private Button			btnSrcLocation;
	private Button			btnSrcLocation2;
	private Button			btnDestLocation;
	private Button			btnCopySrcToDest;
	private Button			btnCopySrcToDest2;
	private Button			btnEnterBootloader;
	private Button			btnCheckButton;
	
	private Button			btnOrder1;
	private Button			btnOrder2;
	private Button			btnOrder3;
	private Button			btnOrder4;
	private Button			btnOrder5;
	private Button			btnOrder6;
	private Button			btnOrder7;
	private Button			btnOrder8;
	private Button			btnOrder9;
	private Button			btnOrder10;
	
	private static Combo  	combo_partition_info;
	
	private final int		FASTBOOT_FASTBOOT_BROWSER = 700;
	private final int		FASTBOOT_IMAGES_BROWSER = 701;
	private final int		FASTBOOT_OPEN_SCRIPT_FOLDER = 702;
	private final int		FASTBOOT_ADD_TABLE = 703;
	private final int		FASTBOOT_DEL_TABLE = 704;
	private final int		FASTBOOT_SELECT_TABLE = 705;
	private final int		FASTBOOT_ORDER_1 = 706;
	private final int		FASTBOOT_ORDER_2 = 707;
	private final int		FASTBOOT_ORDER_3 = 708;
	private final int		FASTBOOT_ORDER_4 = 709;
	private final int		FASTBOOT_ORDER_5 = 710;
	private final int		FASTBOOT_ORDER_6 = 711;
	private final int		FASTBOOT_ORDER_7 = 712;
	private final int		FASTBOOT_ORDER_8 = 713;
	private final int		FASTBOOT_ORDER_9 = 714;
	private final int		FASTBOOT_ORDER_10 = 715;
	
	private final int		FASTBOOT_SRC_BROWSER = 716;
	private final int		FASTBOOT_DEST_BROWSER = 717;
	private final int		FASTBOOT_COPYFILE = 718;
	private final int		FASTBOOT_MODE_ENTER = 719;
	
	private final int		FASTBOOT_COPYFILE2 = 720;
	private final int		FASTBOOT_SRC_BROWSER2 = 721;
	
	private final int		FASTBOOT_CHECKBOX_DIR = 722;
	
	private final int		MAX_TABLE_CNT = 20;
	private String			tempDestLocDir="";
	
	public AndroSuker_TabItem_FastBoot(Shell shell, TabFolder tabFolder, AndroSuker_Execute mExecute)	{
		//thisShell = shell;
		createPage(tabFolder);
		mExe = mExecute;
		initParameters();
		initPage();
	}

	public static Composite getInstance()	{
		return Fastboot_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	public void __onFinally()	{	
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		TableItem[] cmdTableItems = CmdTable.getItems();
		
		AndroSuker_INIFile.writeIniFile("FASTBOOT_EXE_PATH", 	fastbootPath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("FASTBOOT_IMAGES_PATH", imagesPath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("FASTBOOT_SRC_PATH", 	srcFilePath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("FASTBOOT_SRC_PATH2", 	srcFilePath2.getText(), writeList);		
		AndroSuker_INIFile.writeIniFile("FASTBOOT_DEST_PATH",   destFilePath.getText(), writeList);
		AndroSuker_INIFile.writeIniFile("FASTBOOT_VALUE_CNT", Integer.toString(CmdTable.getItemCount()), writeList);

		for (int i = 0; i < values_cnt; i++)	{
			AndroSuker_INIFile.writeIniFile("FASTBOOT_VALUE_"+i, cmdTableItems[i].getText(), writeList);
		}
		
		AndroSuker_INIFile.writeIniFile("FASTBOOT_LAST_CHOICE_MODEL_INDEX", String.valueOf(modelInfoIndex), writeList);
		
		if (btnCheckButton.getSelection())
			AndroSuker_INIFile.writeIniFile("FASTBOOT_CHECKBOX_STATUS", "TRUE", writeList);
		else
			AndroSuker_INIFile.writeIniFile("FASTBOOT_CHECKBOX_STATUS", "FALSE", writeList);		
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_EXE_PATH");			
			if (resultStr.length() > 0)
				fastbootPath.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_IMAGES_PATH");
			if (resultStr.length() > 0)
				imagesPath.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_SRC_PATH");			
			if (resultStr.length() > 0)
				srcFilePath.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_SRC_PATH2");			
			if (resultStr.length() > 0)
				srcFilePath2.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_DEST_PATH");
			if (resultStr.length() > 0)
				destFilePath.setText(resultStr);
						
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_VALUE_CNT");
			if (resultStr.length() > 0)
				values_cnt = Integer.parseInt(resultStr);
			else
				values_cnt = 0;

			for (int i = 0; i < values_cnt; i++)	{
				TableItem item = new TableItem(CmdTable, SWT.FILL);
				resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_VALUE_"+i);
				item.setText(resultStr);
			}
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_LAST_CHOICE_MODEL_INDEX");
			if (resultStr.length() > 0)	{
				modelInfoIndex = Integer.parseInt(resultStr);
				if (CmdTable.getItemCount() > 0 && modelInfoIndex != -1)
					modelInfoStr.setText(CmdTable.getItem(modelInfoIndex).getText());
			} else {
				modelInfoIndex = -1;
				modelInfoStr.setText("");
			}
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "FASTBOOT_CHECKBOX_STATUS");
			if (resultStr.equals("TRUE"))	{
				btnCheckButton.setSelection(true);
				btnDestLocation.setEnabled(false);
				destFilePath.setEnabled(false);
				tempDestLocDir = destFilePath.getText();
				destFilePath.setText(imagesPath.getText());
			}
			else	{
				btnCheckButton.setSelection(false);
				btnDestLocation.setEnabled(true);
				destFilePath.setEnabled(true);
				destFilePath.setText(tempDestLocDir);
			}
		}
	}
	
	private void createPage(TabFolder tabFolder)	{	
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		Fastboot_composite = new Composite(tabFolder, SWT.FILL);
		Fastboot_composite.setLayout(new GridLayout(9, false));
	    
		//--------------------------------#########	fastboot.exe location ##########--------------------------------
		Label lblNewLabel = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("fastboot.exe  location");
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		
		fastbootPath = new Text(Fastboot_composite, SWT.BORDER);
		GridData gd_fastbootPath = new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1);
		gd_fastbootPath.widthHint = 530;
		fastbootPath.setLayoutData(gd_fastbootPath);
		
		btnFastbootBrowser = new Button(Fastboot_composite, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gd_btnNewButton.widthHint = 60;
		btnFastbootBrowser.setLayoutData(gd_btnNewButton);
		btnFastbootBrowser.setText("...");
		btnFastbootBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_FASTBOOT_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		//--------------------------------#########	binary images location ##########--------------------------------
		Label lblNewLabel_1 = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_1.setText("binary images  location");
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);

		imagesPath = new Text(Fastboot_composite, SWT.BORDER);
		GridData gd_imagesPath = new GridData(SWT.FILL, SWT.CENTER, true, false, 7, 1);
		gd_imagesPath.widthHint = 530;
		imagesPath.setLayoutData(gd_imagesPath);
		
		btnImagesBrowser = new Button(Fastboot_composite, SWT.NONE);
		GridData gd_btnBrowser = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnBrowser.widthHint = 60;
		btnImagesBrowser.setLayoutData(gd_btnBrowser);
		btnImagesBrowser.setText("...");
		btnImagesBrowser.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_IMAGES_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		//new Label(Fastboot_composite, SWT.NONE);
		
		btnCheckButton = new Button(Fastboot_composite, SWT.CHECK);
		btnCheckButton.setText("equal of Dest Dir");
		btnCheckButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_CHECKBOX_DIR);				
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		new Label(Fastboot_composite, SWT.NONE);
		
		//--------------------------------######### Copy file src to dest ########--------------------------------
		Label lblNewLabel_4 = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel_4.setText("server\uC5D0\uC11C build\uB41C \uC774\uBBF8\uC9C0 file\uC744 \uB0B4 PC\uB85C copy");
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		
		Label lblNewLabel_2 = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Src1");
		
		srcFilePath = new Text(Fastboot_composite, SWT.BORDER);
		GridData gd_srcFilePath = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
		gd_srcFilePath.widthHint = 515;
		srcFilePath.setLayoutData(gd_srcFilePath);
		
		
		btnSrcLocation = new Button(Fastboot_composite, SWT.NONE);
		btnSrcLocation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnSrcLocation.setText("...");
		btnSrcLocation.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_SRC_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		//new Label(Fastboot_composite, SWT.NONE);
		
		btnCopySrcToDest = new Button(Fastboot_composite, SWT.NONE);
		GridData gd_btnNewButton_10 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_10.widthHint = 60;
		btnCopySrcToDest.setLayoutData(gd_btnNewButton_10);
		btnCopySrcToDest.setText("Copy1");
		btnCopySrcToDest.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_COPYFILE);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//--------------------------------#########	##########--------------------------------
		Label lblNewLabel_99 = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel_99.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_99.setText("Src2");
		
		srcFilePath2 = new Text(Fastboot_composite, SWT.BORDER);
		GridData gd_srcFilePath2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
		gd_srcFilePath2.widthHint = 515;
		srcFilePath2.setLayoutData(gd_srcFilePath2);

		btnSrcLocation2 = new Button(Fastboot_composite, SWT.NONE);
		btnSrcLocation2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnSrcLocation2.setText("...");
		btnSrcLocation2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_SRC_BROWSER2);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		//new Label(Fastboot_composite, SWT.NONE);
		
		btnCopySrcToDest2 = new Button(Fastboot_composite, SWT.NONE);
		GridData gd_btnNewButton_11 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_11.widthHint = 60;
		btnCopySrcToDest2.setLayoutData(gd_btnNewButton_11);
		btnCopySrcToDest2.setText("Copy2");
		btnCopySrcToDest2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_COPYFILE2);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
				
		//--------------------------------#########	##########--------------------------------		
		Label lblNewLabel_3 = new Label(Fastboot_composite, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("Dest");
		
		destFilePath = new Text(Fastboot_composite, SWT.BORDER);
		destFilePath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		btnDestLocation = new Button(Fastboot_composite, SWT.NONE);
		btnDestLocation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnDestLocation.setText("...");
		btnDestLocation.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_DEST_BROWSER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnEnterBootloader = new Button(Fastboot_composite, SWT.NONE);
		btnEnterBootloader.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		btnEnterBootloader.setText("Go Fastboot mode");
		btnEnterBootloader.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_MODE_ENTER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//--------------------------------#########	table control group ##########--------------------------------
		Group group_1 = new Group(Fastboot_composite, SWT.NONE);
		GridData gd_group_1 = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		gd_group_1.heightHint = 24;
		gd_group_1.widthHint = 302;
		group_1.setLayoutData(gd_group_1);
		
		btnAddTable = new Button(group_1, SWT.NONE);
		btnAddTable.setBounds(10, 13, 90, 22);
		btnAddTable.setText("Add to table");
		btnAddTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ADD_TABLE);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnDelTable = new Button(group_1, SWT.NONE);
		btnDelTable.setBounds(108, 13, 90, 22);
		btnDelTable.setText("Item delete");
		btnDelTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_DEL_TABLE);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnSelectTable = new Button(group_1, SWT.NONE);
		btnSelectTable.setBounds(206, 13, 90, 22);
		btnSelectTable.setText("Select");
		btnSelectTable.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_SELECT_TABLE);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});			
		
		//--------------------------------#########	order group ##########--------------------------------
		Group group = new Group(Fastboot_composite, SWT.NONE);
		GridData gd_group = new GridData(SWT.LEFT, SWT.FILL, false, false, 7, 3);
		gd_group.heightHint = 316;
		gd_group.widthHint = 384;
		group.setLayoutData(gd_group);
		
		btnOrder1 = new Button(group, SWT.BOLD);
		btnOrder1.setAlignment(SWT.LEFT);
		btnOrder1.setBounds(23, 22, 351, 22);
		btnOrder1.setText("  1. Full Download");
		btnOrder1.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_1);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder2 = new Button(group, SWT.BOLD);
		btnOrder2.setAlignment(SWT.LEFT);
		btnOrder2.setBounds(23, 50, 351, 22);
		btnOrder2.setText("  2. emmc_appsboot");
		btnOrder2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_2);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder3 = new Button(group, SWT.BOLD);
		btnOrder3.setText("  3. boot.img");
		btnOrder3.setAlignment(SWT.LEFT);
		btnOrder3.setBounds(23, 78, 351, 22);
		btnOrder3.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_3);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder4 = new Button(group, SWT.BOLD);
		btnOrder4.setText("  4. system.img");
		btnOrder4.setAlignment(SWT.LEFT);
		btnOrder4.setBounds(23, 106, 351, 22);
		btnOrder4.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_4);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder5 = new Button(group, SWT.BOLD);
		btnOrder5.setText("  5. recovery.img");
		btnOrder5.setAlignment(SWT.LEFT);
		btnOrder5.setBounds(23, 134, 351, 22);
		btnOrder5.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_5);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder6 = new Button(group, SWT.BOLD);
		btnOrder6.setText("  6. modem - NON_HLOS.bin");
		btnOrder6.setAlignment(SWT.LEFT);
		btnOrder6.setBounds(23, 162, 351, 22);
		btnOrder6.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_6);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder7 = new Button(group, SWT.BOLD);
		btnOrder7.setText("  7. qualcomm bootloader");
		btnOrder7.setAlignment(SWT.LEFT);
		btnOrder7.setBounds(23, 190, 351, 22);
		btnOrder7.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_7);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder8 = new Button(group, SWT.BOLD);
		btnOrder8.setText("  8. erase partition");
		btnOrder8.setAlignment(SWT.LEFT);
		btnOrder8.setBounds(23, 218, 351, 22);
		btnOrder8.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_8);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder9 = new Button(group, SWT.BOLD);
		btnOrder9.setText("  *  erase userdata and cache");
		btnOrder9.setAlignment(SWT.LEFT);
		btnOrder9.setBounds(23, 246, 351, 22);
		btnOrder9.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_9);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		btnOrder10 = new Button(group, SWT.BOLD);
		btnOrder10.setText("  ** device reboot");
		btnOrder10.setAlignment(SWT.LEFT);
		btnOrder10.setBounds(23, 274, 351, 22);
		btnOrder10.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_ORDER_10);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		//--------------------------------#########	select model info text ##########--------------------------------
		modelInfoStr = new Text(Fastboot_composite, SWT.BORDER);		
		GridData gd_text_2 = new GridData(SWT.LEFT, SWT.TOP, true, false, 2, 1);
		gd_text_2.widthHint = 317;
		modelInfoStr.setLayoutData(gd_text_2);
		
		//--------------------------------#########	model info table ##########--------------------------------
		CmdTable = new Table(Fastboot_composite, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.TOP, true, true, 2, 1);
		gd_table.heightHint = 209;
		gd_table.widthHint = 317;
		CmdTable.setLayoutData(gd_table);
		CmdTable.setHeaderVisible(false);
		CmdTable.setLinesVisible(false);		
		
		//--------------------------------#########	open script folder button ##########--------------------------------
		btnOpenScriptFolder = new Button(Fastboot_composite, SWT.NONE);
		btnOpenScriptFolder.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnOpenScriptFolder.setText("open script folder");
		btnOpenScriptFolder.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(FASTBOOT_OPEN_SCRIPT_FOLDER);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		new Label(Fastboot_composite, SWT.NONE);
		
		//--------------------------------#########	partition info ##########--------------------------------
		Label lblPartition = new Label(Fastboot_composite, SWT.NONE);
		GridData gd_lblPartition = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_lblPartition.widthHint = 150;
		lblPartition.setLayoutData(gd_lblPartition);
		lblPartition.setText("<Partition select for erase>");

		combo_partition_info = new Combo(Fastboot_composite, SWT.NONE);
		combo_partition_info.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	private static void initParameters()
	{
		combo_partition_info.add("aboot");
		combo_partition_info.add("rpm");
		combo_partition_info.add("tz");
		combo_partition_info.add("sbl3");
		combo_partition_info.add("sbl2");
		combo_partition_info.add("sbl1");
		combo_partition_info.add("modem");
		combo_partition_info.add("boot");
		combo_partition_info.add("system");
		combo_partition_info.add("userdata");
		combo_partition_info.add("persist");
		combo_partition_info.add("recovery");
		combo_partition_info.add("cache");
	}
	
	private static String getStrPartitionInfo() {
		int index = combo_partition_info.getSelectionIndex();
		
		if (index != -1)
			return (String)combo_partition_info.getItem(index);
		else
			return null;
	}
	
	public void _actionPerformed(int action) {
		if (FASTBOOT_FASTBOOT_BROWSER == action) {
			String temp = null;
			AndroSuker_DirDialog fastBootDlg;
			fastBootDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_FILE, FILE_TYPE_EXE);
			temp = fastBootDlg.getFilePath();
			if (temp != null)
				fastbootPath.setText(temp);
		} else if (FASTBOOT_IMAGES_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog fastBootDlg;
			fastBootDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_DIR, FILE_TYPE_NONE);
			temp = fastBootDlg.getDir();
			if (temp != null)
				imagesPath.setText(temp);
		} else if (FASTBOOT_SRC_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog fastBootDlg;
			fastBootDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_FILE, FILE_TYPE_NONE);
			temp = fastBootDlg.getFilePath();
			if (temp != null)
				srcFilePath.setText(temp);
		}  else if (FASTBOOT_SRC_BROWSER2 == action)	{
			String temp = null;
			AndroSuker_DirDialog fastBootDlg;
			fastBootDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_FILE, FILE_TYPE_NONE);
			temp = fastBootDlg.getFilePath();
			if (temp != null)
				srcFilePath2.setText(temp);
		} else if (FASTBOOT_DEST_BROWSER == action)	{
			String temp = null;
			AndroSuker_DirDialog fastBootDlg;
			fastBootDlg = new AndroSuker_DirDialog(AndroSuker_MainCls.getShellInstance(), MODE_DIR, FILE_TYPE_NONE);
			temp = fastBootDlg.getDir();
			if (temp != null)
				destFilePath.setText(temp);
		} else if (FASTBOOT_COPYFILE == action)	{
			File srcFile = new File(srcFilePath.getText()); // assuming that path is not empty		    
		    if (!AndroSuker_DirDialog.existFileOrPath(srcFile))	{  
		    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Fail!", "Not exist file!", SWT.OK);
		    }
		    File destFile = new File(destFilePath.getText()); // assuming that path is not empty		    
		    if (!AndroSuker_DirDialog.existFileOrPath(destFile))	{  
		    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Fail!", "inapposite directory path!", SWT.OK);
		    }
		    
			String cmd = "cmd.exe~~/K~~copy~~" + srcFilePath.getText() + "~~" + destFilePath.getText();
					
			String[] cmdList = cmd.split("~~");
			try {
				mExe.runProcessSimple(cmdList);
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", "Good!", SWT.OK);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if (FASTBOOT_COPYFILE2 == action)	{
			File srcFile = new File(srcFilePath2.getText()); // assuming that path is not empty		    
		    if (!AndroSuker_DirDialog.existFileOrPath(srcFile))	{  
		    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Fail!", "Not exist file!", SWT.OK);
		    }
		    File destFile = new File(destFilePath.getText()); // assuming that path is not empty		    
		    if (!AndroSuker_DirDialog.existFileOrPath(destFile))	{  
		    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Fail!", "inapposite directory path!", SWT.OK);
		    }
		    
			String cmd = "cmd.exe~~/K~~copy~~" + srcFilePath2.getText() + "~~" + destFilePath.getText();
					
			String[] cmdList = cmd.split("~~");
			try {
				mExe.runProcessSimple(cmdList);
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", "Good!", SWT.OK);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if (FASTBOOT_MODE_ENTER == action) {					
			String cmd = "cmd.exe~~/K~~adb~~reboot~~bootloader";		
			String[] cmdList = cmd.split("~~");
			
			try {
				mExe.runProcessSimple(cmdList);//, getCurrentClsName());
				//mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}		
		} else if (FASTBOOT_OPEN_SCRIPT_FOLDER == action)	{
			String	dirName = "script";
						
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
		} else if (FASTBOOT_ADD_TABLE == action)	{
			if (values_cnt == MAX_TABLE_CNT)	{
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning", "더이상 추가할 수 없습니다. max 20개임", SWT.OK);
			}			
			if (modelInfoStr.getText().length() > 0)	{				
				TableItem item = new TableItem(CmdTable, SWT.NONE);
			    item.setText(modelInfoStr.getText().toString());	    
				values_cnt++;
				modelInfoStr.setText("");
			}
		} else if (FASTBOOT_DEL_TABLE == action)	{
			CmdTable.remove(CmdTable.getSelectionIndices());			
			values_cnt--;
			CmdTable.update();
		} else if (FASTBOOT_SELECT_TABLE == action)	{
			TableItem[] selectItem = CmdTable.getSelection();
			if (selectItem.length > 0)	{
				modelInfoStr.setText(selectItem[0].getText());
				modelInfoIndex = CmdTable.getSelectionIndex();
			}
		} else if (FASTBOOT_CHECKBOX_DIR == action)	{
			if (btnCheckButton.getSelection()) {
				btnDestLocation.setEnabled(false);
				destFilePath.setEnabled(false);
				tempDestLocDir = destFilePath.getText();
				destFilePath.setText(imagesPath.getText());
			} else {
				btnDestLocation.setEnabled(true);
				destFilePath.setEnabled(true);
				destFilePath.setText(tempDestLocDir);
			}
		} else if (FASTBOOT_ORDER_1 == action)	{
			launchDownload(1);
		} else if (FASTBOOT_ORDER_2 == action)	{
			launchDownload(2);
		} else if (FASTBOOT_ORDER_3 == action)	{
			launchDownload(3);
		} else if (FASTBOOT_ORDER_4 == action)	{
			launchDownload(4);
		} else if (FASTBOOT_ORDER_5 == action)	{
			launchDownload(5);
		} else if (FASTBOOT_ORDER_6 == action)	{
			launchDownload(6);
		} else if (FASTBOOT_ORDER_7 == action)	{
			launchDownload(7);
		} else if (FASTBOOT_ORDER_8 == action)	{
			launchDownload(8);	//erase partition
		} else if (FASTBOOT_ORDER_9 == action)	{
			launchDownload(90);
		} else if (FASTBOOT_ORDER_10 == action)	{
			launchDownload(100);
		}
	}
	
	private void launchDownload(int order)	{
		String path1 = "";
		String path2 = "";
		
		if (fastbootPath.getText().trim().length() > 0){
			path1 = fastbootPath.getText();
			path1 = path1.replace('\\','/');
		}
		if (imagesPath.getText().trim().length() > 0){
			path2 = imagesPath.getText();
			path2 = path2.replace('\\','/');
		}
		if (path1.length() < 1 || path2.length() < 1)
			return ;
				
		String cmd = "cmd.exe~~/K~~start~~perl~~script/fastbootWorking.pl~~"				
				+ path1
				+ "~~"
				+ path2
				+ "~~"
				+ modelInfoStr.getText().trim()
				+ "~~"
				+ order;
		
		if (order == 8){
			String partitionInfo = getStrPartitionInfo(); 
			if (partitionInfo != null)
				cmd = cmd + "~~" + partitionInfo;
			else	{
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning!", "erase시 partition을 선택해야만합니다.", SWT.OK);
				return ;
			}
		}
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

