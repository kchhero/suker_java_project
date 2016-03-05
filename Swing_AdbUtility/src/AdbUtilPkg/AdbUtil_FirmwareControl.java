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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AdbUtil_FirmwareControl extends JFrame implements ActionListener, ChangeListener{
	private static AdbUtil_Execute mExe;
	private List<String> readList;
	private List<String> writeList;
	
	private JButton runBtn;
	private JButton addBtn;
	private JButton delBtn;
	private JButton rootBtn;
	private JButton remountBtn;
	//private JLabel outputLabel;
	private JTextField addComboTxt;	
	private JComboBox combo = new JComboBox();
	private int values_cnt;
		
	private static JTextArea mLogText = new JTextArea();
	private static JScrollPane mLogTextScroll;
	private static String mResultAfterProcess;
	
	public AdbUtil_FirmwareControl(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{		
		createPage(panel);
		mExe = mExecute;
		combo.setMaximumRowCount(30);
	    combo.setEditable(true);	    
		initPage();
	}

	public void __onDestoy()	{
		writeList = AdbUtil_Main_Layout.getWriteFileList();

		AdbUtil_INIFile.writeIniFile("FC_VALUE_CNT", Integer.toString(combo.getItemCount()), writeList);
				
		for (int i = 0; i < values_cnt; i++)	{
			AdbUtil_INIFile.writeIniFile("FC_VALUE_"+i, combo.getItemAt(i).toString(), writeList);	
		}
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AdbUtil_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AdbUtil_INIFile.readIniFile(readList, "FC_VALUE_CNT");
			values_cnt = Integer.parseInt(resultStr);
			
			for (int i = 0; i < values_cnt; i++)	{
				resultStr = AdbUtil_INIFile.readIniFile(readList, "FC_VALUE_"+i);
				combo.insertItemAt(resultStr, i);
			}				
		}	else	{
			initTabValues();
		}
	}
	
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);

		//------------------------------------------------------------------------------------
		JLabel label1 = new JLabel("add command");
		label1.setFont(new Font(null, layout.BOLD_STYLE, layout.TITLE_FONT_SIZE));
		label1.setBounds(layout.LABEL7_1_START_POS_X,
						 layout.LABEL7_1_START_POS_Y,
						 layout.LABEL7_1_WIDTH,
						 layout.LABEL7_1_HEIGHT);
		panel.add(label1);

		addComboTxt = new JTextField();
		addComboTxt.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		addComboTxt.setBounds(layout.EDIT7_1_START_POS_X,
							  layout.EDIT7_1_START_POS_Y,
							  layout.EDIT7_1_WIDTH,
							  layout.EDIT7_1_HEIGHT);			
		panel.add(addComboTxt);
		//------------------------------------------------------------------------------------
		addBtn = new JButton();
		addBtn.setText("Add");
		addBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		addBtn.setBounds(layout.ADD_BTN_START_POS_X,
						 layout.ADD_BTN_START_POS_Y,
						 layout.ADD_BTN_WIDTH,
						 layout.ADD_BTN_HEIGHT);
		addBtn.addActionListener(this);
		addBtn.setFocusable(false);
		panel.add(addBtn);		
		//------------------------------------------------------------------------------------
		JLabel label2 = new JLabel("select command");
		label2.setFont(new Font(null, layout.BOLD_STYLE, layout.TITLE_FONT_SIZE));
		label2.setBounds(layout.LABEL7_2_START_POS_X,
						 layout.LABEL7_2_START_POS_Y,
						 layout.LABEL7_2_WIDTH,
						 layout.LABEL7_2_HEIGHT);
		panel.add(label2);
		
		combo.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		combo.setBounds(layout.COMBO_START_POS_X,
						layout.COMBO_START_POS_Y,
						layout.COMBO_WIDTH,
						layout.COMBO_HEIGHT);			
		panel.add(combo);
		//------------------------------------------------------------------------------------
		runBtn = new JButton();
		runBtn.setText("Run");
		runBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		runBtn.setBounds(layout.RUN_BTN_START_POS_X,
						 layout.RUN_BTN_START_POS_Y,
						 layout.RUN_BTN_WIDTH,
						 layout.RUN_BTN_HEIGHT);
		runBtn.addActionListener(this);
		runBtn.setFocusable(false);
		panel.add(runBtn);
		
		delBtn = new JButton();
		delBtn.setText("Item Del");
		delBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		delBtn.setBounds(layout.DEL_BTN_START_POS_X,
						 layout.DEL_BTN_START_POS_Y,
						 layout.DEL_BTN_WIDTH,
						 layout.DEL_BTN_HEIGHT);
		delBtn.addActionListener(this);
		delBtn.setFocusable(false);
		panel.add(delBtn);
		
		rootBtn = new JButton();
		rootBtn.setText("adb root");
		rootBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		rootBtn.setBounds(layout.ROOT_BTN_START_POS_X,
						 layout.ROOT_BTN_START_POS_Y,
						 layout.ROOT_BTN_WIDTH,
						 layout.ROOT_BTN_HEIGHT);
		rootBtn.addActionListener(this);
		rootBtn.setFocusable(false);
		panel.add(rootBtn);
		
		remountBtn = new JButton();
		remountBtn.setText("adb remount");
		remountBtn.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		remountBtn.setBounds(layout.REMOUNT_BTN_START_POS_X,
						 layout.REMOUNT_BTN_START_POS_Y,
						 layout.REMOUNT_BTN_WIDTH,
						 layout.REMOUNT_BTN_HEIGHT);
		remountBtn.addActionListener(this);
		remountBtn.setFocusable(false);
		panel.add(remountBtn);
		//------------------------------------------------------------------------------------
		mLogText.setLineWrap(true);
		mLogText.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		mLogText.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		mLogTextScroll = new JScrollPane(mLogText);
		mLogTextScroll.setWheelScrollingEnabled(true);
		mLogTextScroll.setBounds(layout.LOG7_APK_TOOL_START_POS_X,
								 layout.LOG7_APK_TOOL_START_POS_Y,
								 layout.LOG7_APK_TOOL_WIDTH,
								 layout.LOG7_APK_TOOL_HEIGHT);
		panel.add(mLogTextScroll);		
		//------------------------------------------------------------------------------------
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.runBtn) {
			String text = "cmd.exe~~/C~~adb~~shell~~" + combo.getSelectedItem().toString();						   
			String[] cmdList = text.split("~~");
			
			logTextClear();
			
			if (cmdList != null)	{
				try {
					//createStepper();
					mExe.runProcess(cmdList, this.getClass().getName());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == this.addBtn)	{
			if (values_cnt == 29)	{
				JOptionPane.showMessageDialog(this, "더이상 추가할 수 없습니다. max 30개임", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
			
			if (addComboTxt.getText().length() > 1)	{
				combo.insertItemAt(addComboTxt.getText().toString(), values_cnt);
				values_cnt++;
				addComboTxt.setText("");
			}
		} else if (e.getSource() == this.delBtn)	{
			combo.removeItemAt(combo.getSelectedIndex());
			values_cnt--;
			combo.updateUI();
		} else if (e.getSource() == this.rootBtn)	{
			String cmd = "cmd.exe~~/C~~start~~adb~~root";
			String[] cmdList = cmd.split("~~");
			
			try {
				mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		} else if (e.getSource() == this.remountBtn)	{
			String cmd = "cmd.exe~~/C~~start~~adb~~remount";
			String[] cmdList = cmd.split("~~");
			
			try {
				mExe.runProcessSimple(cmdList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
	}
	
	public static void callbackDoneExecuteProcess() {
		mResultAfterProcess = mExe.getResultMessages();
		mLogText.setText(mResultAfterProcess);
		mLogText.repaint();
	}	
	public static void callbackFailExecuteProcess() {		
		mLogText.setText("failed!!");
		mLogText.repaint();
	}
	private void logTextClear()	{
		mLogText.setText(null);
		mLogText.repaint();
	}
	
	private void initTabValues()	{
		//edit_value.setText("");
		//edit_path.setText("");
	}
}
