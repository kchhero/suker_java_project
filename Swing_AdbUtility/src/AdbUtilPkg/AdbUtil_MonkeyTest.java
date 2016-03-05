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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AdbUtil_MonkeyTest extends JFrame implements ActionListener, ChangeListener{
	private static AdbUtil_Execute mExe;
	private List<String> readList;
	private List<String> writeList;
	
	private JButton adbMonkey;
	
	private JTextField edit_monkey_speed;
	private JTextField edit_monkey_throttle;
	private JTextField edit_monkey_testcount;
	private JTextField edit_monkey_packageName;
	
	private JComboBox  combo_pct_TouchEvent;
	private JComboBox  combo_pct_MotionEvent;
	private JComboBox  combo_pct_MajorEvent;
	private JComboBox  combo_pct_SysKeysEvent;
	private JComboBox  combo_pct_AppSwitchEvent;
	private JComboBox  combo_pct_AnyEvent;
		
	public AdbUtil_MonkeyTest(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{
		createPage(panel);
		mExe = mExecute;
		initPage();
	}
	public void __onDestoy()	{
		writeList = AdbUtil_Main_Layout.getWriteFileList();
		
		if (edit_monkey_speed.getText().length() <= 1)
			edit_monkey_speed.setText("");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_SPEED", edit_monkey_speed.getText(), writeList);
		
		if (edit_monkey_throttle.getText().length() <= 1)
			edit_monkey_throttle.setText("");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_THROTTLE", edit_monkey_throttle.getText(), writeList);
		
		if (edit_monkey_testcount.getText().length() <= 1)
			edit_monkey_testcount.setText("");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_TESTCOUNT", edit_monkey_testcount.getText(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PKGNAME", edit_monkey_packageName.getText(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_TOUCHEVENT", ""+combo_pct_TouchEvent.getSelectedIndex(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_MOTIONEVENT", ""+combo_pct_MotionEvent.getSelectedIndex(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_MAJOREVENT", ""+combo_pct_MajorEvent.getSelectedIndex(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_SYSKEYSEVENT", ""+combo_pct_SysKeysEvent.getSelectedIndex(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_APPSWITCHEVENT", ""+combo_pct_AppSwitchEvent.getSelectedIndex(), writeList);
		
		if (edit_monkey_packageName.getText().length() <= 1)
			edit_monkey_packageName.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_MONKEY_PCT_ANYEVENT", ""+combo_pct_AnyEvent.getSelectedIndex(), writeList);
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AdbUtil_Main_Layout.getReadFileList();

		if (readList != null){
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_SPEED");
			edit_monkey_speed.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_THROTTLE");
			edit_monkey_throttle.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_TESTCOUNT");
			edit_monkey_testcount.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PKGNAME");
			edit_monkey_packageName.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_TOUCHEVENT");
			combo_pct_TouchEvent.setSelectedIndex(Integer.parseInt(resultStr));
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_MOTIONEVENT");
			combo_pct_MotionEvent.setSelectedIndex(Integer.parseInt(resultStr));
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_MAJOREVENT");
			combo_pct_MajorEvent.setSelectedIndex(Integer.parseInt(resultStr));
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_SYSKEYSEVENT");
			combo_pct_SysKeysEvent.setSelectedIndex(Integer.parseInt(resultStr));
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_APPSWITCHEVENT");
			combo_pct_AppSwitchEvent.setSelectedIndex(Integer.parseInt(resultStr));
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_MONKEY_PCT_ANYEVENT");
			combo_pct_AnyEvent.setSelectedIndex(Integer.parseInt(resultStr));
		}
	}
	private void createPage(JPanel panel)	{
		/* adbMonkey; private JButton adbPackageDownload;
		   private JTextField edit_downloadPackage;
		*/
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);

		adbMonkey = new JButton("adb Monkey Test Run");
		adbMonkey.setFont(new Font(null, layout.BOLD_STYLE,
				layout.BUTTON_FONT_SIZE));
		adbMonkey.setBounds(layout.BUTTON_FOR_ADB_MONKEY_START_POS_X,
							layout.BUTTON_FOR_ADB_MONKEY_START_POS_Y,
							layout.BUTTON_FOR_ADB_MONKEY_WIDTH,
							layout.BUTTON_FOR_ADB_MONKEY_HEIGHT);
		adbMonkey.addActionListener(this);
		panel.add(adbMonkey);
		
		//------------------------------------------------------------------------------------
		JLabel label3_0 = new JLabel("< millisecond >");
		label3_0.setFont(new Font(null, layout.BOLD_STYLE,
										layout.TITLE_FONT_SIZE));
		label3_0.setBounds(layout.LABEL3_0_MONKEY_START_POS_X,
						   layout.LABEL3_0_MONKEY_START_POS_Y,
						   layout.LABEL3_0_MONKEY_WIDTH,
						   layout.LABEL3_0_MONKEY_HEIGHT);
		panel.add(label3_0);
		
		JLabel label3_1 = new JLabel("Event Speed");
		label3_1.setFont(new Font(null, layout.BOLD_STYLE,
										layout.TITLE_FONT_SIZE));
		label3_1.setBounds(layout.LABEL3_1_MONKEY_START_POS_X,
						   layout.LABEL3_1_MONKEY_START_POS_Y,
						   layout.LABEL3_1_MONKEY_WIDTH,
						   layout.LABEL3_1_MONKEY_HEIGHT);
		panel.add(label3_1);
		
		edit_monkey_speed = new JTextField();
		edit_monkey_speed.setFont(new Font(null, layout.ITALYIC_STYLE,
												 layout.EDIT_FONT_SIZE));
		edit_monkey_speed.setBounds(layout.EDIT3_1_MONKEY_START_POS_X,
									layout.EDIT3_1_MONKEY_START_POS_Y,
									layout.EDIT3_1_MONKEY_WIDTH,
									layout.EDIT3_1_MONKEY_HEIGHT);
		edit_monkey_speed.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (edit_monkey_speed.getText().trim().matches("none")) {
					edit_monkey_speed.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
			}

			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		panel.add(edit_monkey_speed);
		//------------------------------------------------------------------------------------
		
		//------------------------------------------------------------------------------------
		JLabel label3_2 = new JLabel("Event delay : Throttle");
		label3_2.setFont(new Font(null, layout.BOLD_STYLE,
										layout.TITLE_FONT_SIZE));
		label3_2.setBounds(layout.LABEL3_2_MONKEY_START_POS_X,
						   layout.LABEL3_2_MONKEY_START_POS_Y,
						   layout.LABEL3_2_MONKEY_WIDTH,
						   layout.LABEL3_2_MONKEY_HEIGHT);
		panel.add(label3_2);
		
		edit_monkey_throttle = new JTextField();
		edit_monkey_throttle.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	layout.EDIT_FONT_SIZE));
		edit_monkey_throttle.setBounds(layout.EDIT3_2_MONKEY_START_POS_X,
									   layout.EDIT3_2_MONKEY_START_POS_Y,
									   layout.EDIT3_2_MONKEY_WIDTH,
									   layout.EDIT3_2_MONKEY_HEIGHT);
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
		panel.add(edit_monkey_throttle);
		//------------------------------------------------------------------------------------
		JLabel label3_3 = new JLabel("Number of event");
		label3_3.setFont(new Font(null, layout.BOLD_STYLE,
										layout.TITLE_FONT_SIZE));
		label3_3.setBounds(layout.LABEL3_3_MONKEY_START_POS_X,
						   layout.LABEL3_3_MONKEY_START_POS_Y,
						   layout.LABEL3_3_MONKEY_WIDTH,
						   layout.LABEL3_3_MONKEY_HEIGHT);
		panel.add(label3_3);
		
		edit_monkey_testcount = new JTextField();
		edit_monkey_testcount.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	 layout.EDIT_FONT_SIZE));
		edit_monkey_testcount.setBounds(layout.EDIT3_3_MONKEY_START_POS_X,
										layout.EDIT3_3_MONKEY_START_POS_Y,
										layout.EDIT3_3_MONKEY_WIDTH,
										layout.EDIT3_3_MONKEY_HEIGHT);
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
		panel.add(edit_monkey_testcount);
		//------------------------------------------------------------------------------------
		JLabel label3_4 = new JLabel("Package Name");
		label3_4.setFont(new Font(null, layout.BOLD_STYLE,
										layout.TITLE_FONT_SIZE));
		label3_4.setBounds(layout.LABEL3_4_MONKEY_START_POS_X,
						   layout.LABEL3_4_MONKEY_START_POS_Y,
						   layout.LABEL3_4_MONKEY_WIDTH,
						   layout.LABEL3_4_MONKEY_HEIGHT);
		panel.add(label3_4);
		
		edit_monkey_packageName = new JTextField();
		edit_monkey_packageName.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	   layout.EDIT_FONT_SIZE));
		edit_monkey_packageName.setBounds(layout.EDIT3_4_MONKEY_START_POS_X,
										  layout.EDIT3_4_MONKEY_START_POS_Y,
										  layout.EDIT3_4_MONKEY_WIDTH,
										  layout.EDIT3_4_MONKEY_HEIGHT);
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
		panel.add(edit_monkey_packageName);
		//------------------------------------------------------------------------------------
		JLabel label3_0_pct = new JLabel("< Frequency Percentage(%) >");
		label3_0_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_0_pct.setBounds(layout.LABEL3_0_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_0_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_0_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_0_MONKEY_PCT_HEIGHT);
		panel.add(label3_0_pct);
		
		JLabel label3_1_pct = new JLabel("TouchScreen Event frequency");
		label3_1_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_1_pct.setBounds(layout.LABEL3_1_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_1_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_1_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_1_MONKEY_PCT_HEIGHT);
		panel.add(label3_1_pct);
		
		combo_pct_TouchEvent = new JComboBox();
		combo_pct_TouchEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	   layout.EDIT_FONT_SIZE));
		combo_pct_TouchEvent.setBounds(layout.COMBO3_1_MONKEY_PCT_START_POS_X,
									   layout.COMBO3_1_MONKEY_PCT_START_POS_Y,
									   layout.COMBO3_1_MONKEY_PCT_WIDTH,
									   layout.COMBO3_1_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_TouchEvent);
		//------------------------------------------------------------------------------------
		JLabel label3_2_pct = new JLabel("Drag Event frequency");
		label3_2_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_2_pct.setBounds(layout.LABEL3_2_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_2_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_2_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_2_MONKEY_PCT_HEIGHT);
		panel.add(label3_2_pct);
		
		combo_pct_MotionEvent = new JComboBox();
		combo_pct_MotionEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	 layout.EDIT_FONT_SIZE));
		combo_pct_MotionEvent.setBounds(layout.COMBO3_2_MONKEY_PCT_START_POS_X,
									    layout.COMBO3_2_MONKEY_PCT_START_POS_Y,
									    layout.COMBO3_2_MONKEY_PCT_WIDTH,
									    layout.COMBO3_2_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_MotionEvent);
		//------------------------------------------------------------------------------------
		JLabel label3_3_pct = new JLabel("TouchPad Event frequency");
		label3_3_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_3_pct.setBounds(layout.LABEL3_3_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_3_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_3_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_3_MONKEY_PCT_HEIGHT);
		panel.add(label3_3_pct);
		
		combo_pct_MajorEvent = new JComboBox();
		combo_pct_MajorEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	layout.EDIT_FONT_SIZE));
		combo_pct_MajorEvent.setBounds(layout.COMBO3_3_MONKEY_PCT_START_POS_X,
									   layout.COMBO3_3_MONKEY_PCT_START_POS_Y,
									   layout.COMBO3_3_MONKEY_PCT_WIDTH,
									   layout.COMBO3_3_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_MajorEvent);
		//------------------------------------------------------------------------------------
		JLabel label3_4_pct = new JLabel("System Event frequency");
		label3_4_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_4_pct.setBounds(layout.LABEL3_4_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_4_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_4_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_4_MONKEY_PCT_HEIGHT);
		panel.add(label3_4_pct);
		
		combo_pct_SysKeysEvent = new JComboBox();
		combo_pct_SysKeysEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	   layout.EDIT_FONT_SIZE));
		combo_pct_SysKeysEvent.setBounds(layout.COMBO3_4_MONKEY_PCT_START_POS_X,
									     layout.COMBO3_4_MONKEY_PCT_START_POS_Y,
									     layout.COMBO3_4_MONKEY_PCT_WIDTH,
									     layout.COMBO3_4_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_SysKeysEvent);
		//------------------------------------------------------------------------------------
		JLabel label3_5_pct = new JLabel("New App. occur Event frequency");
		label3_5_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_5_pct.setBounds(layout.LABEL3_5_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_5_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_5_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_5_MONKEY_PCT_HEIGHT);
		panel.add(label3_5_pct);
		
		combo_pct_AppSwitchEvent = new JComboBox();
		combo_pct_AppSwitchEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												 	    layout.EDIT_FONT_SIZE));
		combo_pct_AppSwitchEvent.setBounds(layout.COMBO3_5_MONKEY_PCT_START_POS_X,
									       layout.COMBO3_5_MONKEY_PCT_START_POS_Y,
									       layout.COMBO3_5_MONKEY_PCT_WIDTH,
									       layout.COMBO3_5_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_AppSwitchEvent);
		//------------------------------------------------------------------------------------
		JLabel label3_6_pct = new JLabel("Any. Event frequency");
		label3_6_pct.setFont(new Font(null, layout.BOLD_STYLE,
											layout.TITLE_FONT_SIZE));
		label3_6_pct.setBounds(layout.LABEL3_6_MONKEY_PCT_START_POS_X,
						   	   layout.LABEL3_6_MONKEY_PCT_START_POS_Y,
						   	   layout.LABEL3_6_MONKEY_PCT_WIDTH,
						   	   layout.LABEL3_6_MONKEY_PCT_HEIGHT);
		panel.add(label3_6_pct);
		
		combo_pct_AnyEvent = new JComboBox();
		combo_pct_AnyEvent.setFont(new Font(null, layout.ITALYIC_STYLE,
												  layout.EDIT_FONT_SIZE));
		combo_pct_AnyEvent.setBounds(layout.COMBO3_6_MONKEY_PCT_START_POS_X,
									 layout.COMBO3_6_MONKEY_PCT_START_POS_Y,
									 layout.COMBO3_6_MONKEY_PCT_WIDTH,
									 layout.COMBO3_6_MONKEY_PCT_HEIGHT);
		panel.add(combo_pct_AnyEvent);
		
		initComboBox(combo_pct_TouchEvent);
		initComboBox(combo_pct_MotionEvent);
		initComboBox(combo_pct_MajorEvent);
		initComboBox(combo_pct_SysKeysEvent);
		initComboBox(combo_pct_AppSwitchEvent);
		initComboBox(combo_pct_AnyEvent);
		//------------------------------------------------------------------------------------
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.adbMonkey)	{
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
				
				temp = edit_monkey_speed.getText();					
				if (Integer.parseInt(temp) > 0)	{
					monkey_cmd += ("~~-s~~" + temp);
				}
				temp = null;
				
				temp = edit_monkey_throttle.getText();
				if (Integer.parseInt(temp) > 0)	{
					monkey_cmd += ("~~--throttle~~" + temp);
				}
				temp = null;				
				
				temp = edit_monkey_testcount.getText();
				if (Integer.parseInt(temp) > 0)	{
					monkey_cmd += ("~~-v~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_TouchEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-touch~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_MotionEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-motion~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_MajorEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-majornav~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_SysKeysEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-syskeys~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_AppSwitchEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-appswitch~~" + temp);
				}
				temp = null;
				
				temp = (String) combo_pct_AnyEvent.getSelectedItem();
				if (!temp.equals("default"))	{
					monkey_cmd += ("~~--pct-anyevent~~" + temp);
				}
				temp = null;

				String[] monkey_cmdList = monkey_cmd.split("~~");
				
				mExe.runProcessSimple(monkey_cmdList);
				//mExecute.runProcess(cmdList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private void initComboBox(JComboBox combo)
	{
		combo.addItem("default"); combo.addItem("5");	combo.addItem("10");	combo.addItem("15");
		combo.addItem("20");	combo.addItem("25");	combo.addItem("30");	combo.addItem("35");
		combo.addItem("35");	combo.addItem("35");	combo.addItem("35");	combo.addItem("35");
		combo.addItem("35");	combo.addItem("40");	combo.addItem("45");	combo.addItem("50");
		combo.addItem("55");	combo.addItem("60");	combo.addItem("65");	combo.addItem("70");
		combo.addItem("75");	combo.addItem("80");	combo.addItem("85");	combo.addItem("90");
		combo.addItem("95");	combo.addItem("99");
	}
}
