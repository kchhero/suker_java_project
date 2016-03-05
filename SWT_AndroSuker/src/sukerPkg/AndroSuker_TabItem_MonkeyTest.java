package sukerPkg;

import java.io.IOException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;

public class AndroSuker_TabItem_MonkeyTest implements AndroSuker_Definition {
	private static AndroSuker_Execute mExe;
	private static Composite MonkeyTest_composite;
	
	private List<String> readList;
	private Button 		adbMonkey;	
	private Text 		edit_monkey_seed;
	private Text 		edit_monkey_throttle;
	private Text 		edit_monkey_testcount;
	private Text 		edit_monkey_packageName;
	
	private Combo  		combo_pct_TouchEvent;
	private Combo  		combo_pct_MotionEvent;
	private Combo  		combo_pct_MajorEvent;
	private Combo  		combo_pct_SysKeysEvent;
	private Combo  		combo_pct_AppSwitchEvent;
	private Combo  		combo_pct_AnyEvent;

	private final int MONKEY_TEST_RUN =	200;
	
	public AndroSuker_TabItem_MonkeyTest(TabFolder tabFolder, AndroSuker_Execute mExecute) {
		createPage(tabFolder);
		mExe = mExecute;
		initPage();
	}
	
	public static Composite getInstance()	{
		return MonkeyTest_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	Main Frame ##########--------------------------------
		MonkeyTest_composite = new Composite(tabFolder, SWT.FILL);
		GridLayout gl = new GridLayout();
		gl.numColumns = 7;
		gl.verticalSpacing = 10;
		gl.marginHeight = 25;
		MonkeyTest_composite.setLayout(gl);

		//------------------------------######### label ##########---------------------------------
		Label label_millsecond = new Label(MonkeyTest_composite, SWT.NONE);
		label_millsecond.setText("< millisecond >    ");		
		GridData gridData_label0 = new GridData();
		gridData_label0.horizontalAlignment = GridData.END;
		gridData_label0.horizontalSpan = 3;
		gridData_label0.verticalIndent = 5;
		label_millsecond.setLayoutData(gridData_label0);
		
		Label label_FreqPer = new Label(MonkeyTest_composite, SWT.NONE);
		label_FreqPer.setText("      < Frequency Percentage (%) >");		
		GridData gridData_label1 = new GridData();
		gridData_label1.horizontalAlignment = GridData.CENTER ;
		gridData_label1.horizontalSpan = 4;
		gridData_label1.verticalIndent = 5;
		label_FreqPer.setLayoutData(gridData_label1);
		
		//------------------------------######### Row 1 ##########---------------------------------
		GridData gridData_1_1 = new GridData();
		gridData_1_1.horizontalAlignment = GridData.BEGINNING;
		gridData_1_1.horizontalSpan = 1;
		Label label_EventSeed = new Label(MonkeyTest_composite, SWT.NONE);
		label_EventSeed.setText("Event Seed");
		label_EventSeed.setLayoutData(gridData_1_1);
		
		GridData gridData_1_2 = new GridData();
		gridData_1_2.horizontalAlignment = GridData.END;
		gridData_1_2.horizontalSpan = 2;
		gridData_1_2.widthHint = EDIT_MONKEY_EVENT_WIDTH;		
		edit_monkey_seed = new Text(MonkeyTest_composite, SWT.SINGLE | SWT.BORDER);
		edit_monkey_seed.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					if (edit_monkey_seed.getText().trim().matches("none")) {
						edit_monkey_seed.setText("");
					}
				}
				public void focusLost(FocusEvent e) {
				}
				@SuppressWarnings("unused")
				void displayMessage(String prefix, FocusEvent e) {
				}
			});
		edit_monkey_seed.setLayoutData(gridData_1_2);
		
		GridData gridData_1_3 = new GridData();
		gridData_1_3.horizontalAlignment = GridData.END;
		gridData_1_3.horizontalSpan = 2;
		gridData_1_3.horizontalIndent = EDIT_GAP_LABEL;
		Label label0 = new Label(MonkeyTest_composite, SWT.NONE);
		label0.setText("TouchScreen Event frequency");
		label0.setLayoutData(gridData_1_3);
		
		GridData gridData_1_4 = new GridData();
		gridData_1_4.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData_1_4.horizontalSpan = 2;
		gridData_1_4.horizontalIndent = LABEL_GAP_COMBO;
		gridData_1_4.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_TouchEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_TouchEvent.setLayoutData(gridData_1_4);
		
		//------------------------------######### Row 2 ##########---------------------------------
		GridData gridData_2_1 = new GridData();
		gridData_2_1.horizontalAlignment = GridData.BEGINNING;
		gridData_2_1.horizontalSpan = 1;
		Label label_EventDelay = new Label(MonkeyTest_composite, SWT.NONE);
		label_EventDelay.setText("Event delay : Throttle");
		label_EventDelay.setLayoutData(gridData_2_1);		
		
		GridData gridData_2_2 = new GridData();
		gridData_2_2.horizontalAlignment = GridData.END;
		gridData_2_2.horizontalSpan = 2;
		gridData_2_2.widthHint = EDIT_MONKEY_EVENT_WIDTH;
		edit_monkey_throttle = new Text(MonkeyTest_composite, SWT.SINGLE | SWT.BORDER);
		edit_monkey_throttle.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_monkey_throttle.getText().trim().matches("none")) {
					edit_monkey_throttle.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		edit_monkey_throttle.setLayoutData(gridData_2_2);
		
		GridData gridData_2_3 = new GridData();
		gridData_2_3.horizontalAlignment = GridData.END;
		gridData_2_3.horizontalSpan = 2;
		gridData_2_3.horizontalIndent = EDIT_GAP_LABEL;
		Label label1 = new Label(MonkeyTest_composite, SWT.NONE);
		label1.setText("Drag Event frequency");
		label1.setLayoutData(gridData_2_3);
		
		GridData gridData_2_4 = new GridData();
		gridData_2_4.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData_2_4.horizontalSpan = 2;
		gridData_2_4.horizontalIndent = LABEL_GAP_COMBO;
		gridData_2_4.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_MotionEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_MotionEvent.setLayoutData(gridData_2_4);
		
		//------------------------------######### Row 3 ##########---------------------------------
		GridData gridData_3_1 = new GridData();
		gridData_3_1.horizontalAlignment = GridData.BEGINNING;
		gridData_3_1.horizontalSpan = 1;
		Label label_EventNum = new Label(MonkeyTest_composite, SWT.LEFT);
		label_EventNum.setText("Number of event");
		label_EventNum.setLayoutData(gridData_3_1);
		
		GridData gridData_3_2 = new GridData();
		gridData_3_2.horizontalAlignment = GridData.END;
		gridData_3_2.horizontalSpan = 2;
		gridData_3_2.widthHint = EDIT_MONKEY_EVENT_WIDTH;
		edit_monkey_testcount = new Text(MonkeyTest_composite, SWT.SINGLE | SWT.BORDER);
		edit_monkey_testcount.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_monkey_testcount.getText().trim().matches("none")) {
					edit_monkey_testcount.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		edit_monkey_testcount.setLayoutData(gridData_3_2);
		
		GridData gridData_3_3 = new GridData();
		gridData_3_3.horizontalAlignment = GridData.END;
		gridData_3_3.horizontalSpan = 2;
		gridData_3_3.horizontalIndent = EDIT_GAP_LABEL;
		Label label2 = new Label(MonkeyTest_composite, SWT.LEFT);
		label2.setText("TouchPad Event frequency");
		label2.setLayoutData(gridData_3_3);
		
		GridData gridData_3_4 = new GridData();
		gridData_3_4.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData_3_4.horizontalSpan = 2;
		gridData_3_4.horizontalIndent = LABEL_GAP_COMBO;
		gridData_3_4.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_MajorEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_MajorEvent.setLayoutData(gridData_3_4);
		
		//------------------------------######### Row 4 ##########---------------------------------
		GridData gridData_4_1 = new GridData();
		gridData_4_1.horizontalAlignment = GridData.BEGINNING;
		gridData_4_1.horizontalSpan = 1;
		Label label_PkgName = new Label(MonkeyTest_composite, SWT.NONE);
		label_PkgName.setText("Package Name");
		label_PkgName.setLayoutData(gridData_4_1);
		
		GridData gridData_4_2 = new GridData();
		gridData_4_2.horizontalAlignment = GridData.END;
		gridData_4_2.horizontalSpan = 2;
		gridData_4_2.widthHint = EDIT_MONKEY_EVENT_WIDTH;
		edit_monkey_packageName = new Text(MonkeyTest_composite, SWT.SINGLE | SWT.BORDER);
		edit_monkey_packageName.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_monkey_packageName.getText().trim().matches("none")) {
					edit_monkey_packageName.setText("");
				}
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		edit_monkey_packageName.setLayoutData(gridData_4_2);
		
		GridData gridData_4_3 = new GridData();
		gridData_4_3.horizontalAlignment = GridData.END;
		gridData_4_3.horizontalSpan = 2;
		gridData_4_3.horizontalIndent = EDIT_GAP_LABEL;
		Label label3 = new Label(MonkeyTest_composite, SWT.NONE);
		label3.setText("System Event frequency");
		label3.setLayoutData(gridData_4_3);
		
		GridData gridData_4_4 = new GridData();
		gridData_4_4.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData_4_4.horizontalSpan = 2;
		gridData_4_4.horizontalIndent = LABEL_GAP_COMBO;
		gridData_4_4.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_SysKeysEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_SysKeysEvent.setLayoutData(gridData_4_4);
		
		//------------------------------######### Row 5 ##########---------------------------------
		GridData gridData_5_1 = new GridData();
		gridData_5_1.horizontalAlignment = GridData.END;
		gridData_5_1.horizontalSpan = 5;
		Label label4 = new Label(MonkeyTest_composite, SWT.NONE);
		label4.setText("New App. occur Event frequency");
		label4.setLayoutData(gridData_5_1);
		
		GridData gridData_5_2 = new GridData();
		gridData_5_2.horizontalAlignment = GridData.END;
		gridData_5_2.horizontalSpan = 2;
		gridData_5_2.horizontalIndent = LABEL_GAP_COMBO;
		gridData_5_2.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_AppSwitchEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_AppSwitchEvent.setLayoutData(gridData_5_2);		
		
		//------------------------------######### Row 6 ##########---------------------------------
		GridData gridData_6_1 = new GridData();
		gridData_6_1.horizontalAlignment = GridData.END;
		gridData_6_1.horizontalSpan = 5;		
		Label label5 = new Label(MonkeyTest_composite, SWT.NONE);
		label5.setText("Any. Event frequency");
		label5.setLayoutData(gridData_6_1);
			
		GridData gridData_6_2 = new GridData();
		gridData_6_2.horizontalAlignment = GridData.END;
		gridData_6_2.horizontalSpan = 2;
		gridData_6_2.horizontalIndent = LABEL_GAP_COMBO;
		gridData_6_2.widthHint = COMBO_MONKEY_WIDTH;
		combo_pct_AnyEvent = new Combo(MonkeyTest_composite, SWT.READ_ONLY);
		combo_pct_AnyEvent.setLayoutData(gridData_6_2);
		
		initComboBox(combo_pct_TouchEvent);
		initComboBox(combo_pct_MotionEvent);
		initComboBox(combo_pct_MajorEvent);
		initComboBox(combo_pct_SysKeysEvent);
		initComboBox(combo_pct_AppSwitchEvent);
		initComboBox(combo_pct_AnyEvent);

		//------------------------------######### Run Button ##########---------------------------------
		adbMonkey = new Button(MonkeyTest_composite, SWT.PUSH);
		adbMonkey.setText("adb Monkey Test Run");
		GridData gridData_btn0 = new GridData();
		gridData_btn0.horizontalSpan = 2;
		gridData_btn0.horizontalAlignment = GridData.BEGINNING;
		gridData_btn0.widthHint = BUTTON_FOR_ADB_MONKEY_WIDTH;
		gridData_btn0.heightHint = BUTTON_FOR_ADB_MONKEY_HEIGHT;
		adbMonkey.setLayoutData(gridData_btn0);
		adbMonkey.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(MONKEY_TEST_RUN);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(MONKEY_TEST_RUN);
		      }
		    });
	}
	
	public void __onFinally()	{
		List<String> writeList;
		
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		
		if (edit_monkey_seed.getText().length() <= 1)
			edit_monkey_seed.setText("");
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_SEED", edit_monkey_seed.getText(), writeList);
		
		if (edit_monkey_throttle.getText().length() <= 1)
			edit_monkey_throttle.setText("");
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_THROTTLE", edit_monkey_throttle.getText(), writeList);
		
		if (edit_monkey_testcount.getText().length() <= 1)
			edit_monkey_testcount.setText("");
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_TESTCOUNT", edit_monkey_testcount.getText(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PKGNAME", edit_monkey_packageName.getText(), writeList);
		
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_TOUCHEVENT", ""+combo_pct_TouchEvent.getSelectionIndex(), writeList);
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_MOTIONEVENT", ""+combo_pct_MotionEvent.getSelectionIndex(), writeList);
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_MAJOREVENT", ""+combo_pct_MajorEvent.getSelectionIndex(), writeList);
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_SYSKEYSEVENT", ""+combo_pct_SysKeysEvent.getSelectionIndex(), writeList);		
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_APPSWITCHEVENT", ""+combo_pct_AppSwitchEvent.getSelectionIndex(), writeList);		
		AndroSuker_INIFile.writeIniFile("ADB_MONKEY_PCT_ANYEVENT", ""+combo_pct_AnyEvent.getSelectionIndex(), writeList);
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();

		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_SEED");
			edit_monkey_seed.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_THROTTLE");
			edit_monkey_throttle.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_TESTCOUNT");
			edit_monkey_testcount.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PKGNAME");
			edit_monkey_packageName.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_TOUCHEVENT");
			if (resultStr.length() > 0)
				combo_pct_TouchEvent.select(Integer.parseInt(resultStr));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_MOTIONEVENT");
			if (resultStr.length() > 0)
				combo_pct_MotionEvent.select(Integer.parseInt(resultStr));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_MAJOREVENT");
			if (resultStr.length() > 0)
				combo_pct_MajorEvent.select(Integer.parseInt(resultStr));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_SYSKEYSEVENT");
			if (resultStr.length() > 0)
				combo_pct_SysKeysEvent.select(Integer.parseInt(resultStr));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_APPSWITCHEVENT");
			if (resultStr.length() > 0)
				combo_pct_AppSwitchEvent.select(Integer.parseInt(resultStr));
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_ANYEVENT");
			if (resultStr.length() > 0)
				combo_pct_AnyEvent.select(Integer.parseInt(resultStr));
		}
	}

	private void _actionPerformed(int action) {
		if (MONKEY_TEST_RUN == action)	{
			try {
				String monkey_cmd = "cmd.exe~~/C~~start~~adb~~shell~~monkey";
				String temp = null;
				
				temp = edit_monkey_packageName.getText();
				if (edit_monkey_packageName.getText().length() > 0)	{
					if (!temp.equals("none"))	{
						monkey_cmd += ("~~-p~~" + temp);
					}
				}
				temp = null;
				
				if (edit_monkey_seed.getText().length() > 0)	{
					temp = edit_monkey_seed.getText();					
					if (Integer.parseInt(temp) > 0)	{
						monkey_cmd += ("~~-s~~" + temp);
					}
				}
				temp = null;
				
				if (edit_monkey_throttle.getText().length() > 0)	{
					temp = edit_monkey_throttle.getText();
					if (Integer.parseInt(temp) > 0)	{
						monkey_cmd += ("~~--throttle~~" + temp);
					}
				}
				temp = null;				
				
				if (edit_monkey_testcount.getText().length() > 0)	{
					temp = edit_monkey_testcount.getText();
					if (Integer.parseInt(temp) > 0)	{
						monkey_cmd += ("~~-v~~" + temp);
					}
				}
				temp = null;
				
				temp = (String) combo_pct_TouchEvent.getItem(combo_pct_TouchEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-touch~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_MotionEvent.getItem(combo_pct_MotionEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-motion~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_MajorEvent.getItem(combo_pct_MajorEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-majornav~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_SysKeysEvent.getItem(combo_pct_SysKeysEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-syskeys~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_AppSwitchEvent.getItem(combo_pct_AppSwitchEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-appswitch~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_AnyEvent.getItem(combo_pct_AnyEvent.getSelectionIndex());
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-anyevent~~" + temp);
				}
				temp = null;

				String[] monkey_cmdList = monkey_cmd.split("~~");
				
				mExe.runProcessSimple(monkey_cmdList);
				//mExecute.runProcess(cmdList);
			} catch (IOException e1) {
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "예상치못한 error가 발생했습니다. \nInterruptedException!! \n suker에게 문의하세요.", SWT.OK);
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "예상치못한 error가 발생했습니다. \nInterruptedException!! \n suker에게 문의하세요.", SWT.OK);
				e1.printStackTrace();
			}
		}
	}
	
	private void initComboBox(Combo combo)
	{
		combo.add("default"); combo.add("5");	combo.add("10");	combo.add("15");
		combo.add("20");	combo.add("25");	combo.add("30");	combo.add("35");
		combo.add("35");	combo.add("35");	combo.add("35");	combo.add("35");
		combo.add("35");	combo.add("40");	combo.add("45");	combo.add("50");
		combo.add("55");	combo.add("60");	combo.add("65");	combo.add("70");
		combo.add("75");	combo.add("80");	combo.add("85");	combo.add("90");
		combo.add("95");	combo.add("99");
	}
}
