package AdbUtilPkg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
class AdbUtil_Main_Layout extends JFrame {
	/**
	 * 
	 */	
	public static boolean DEBUG_MODE_ON = false;
	public static String mCurrentTabName = null;
	
	private static JPanel mPanel1 = new JPanel();
	private static JPanel mPanel2 = new JPanel();
	private static JPanel mPanel3 = new JPanel();
	private static JPanel mPanel4 = new JPanel();
	private static JPanel mPanel5 = new JPanel();
//	private static JPanel mPanel6 = new JPanel();
	private static JPanel mPanel7 = new JPanel();
	
	private JLabel myLabel;
	
	private static AdbUtil_Definition 	mLayoutInfo = new AdbUtil_Definition();
	private static AdbUtil_Execute 		mExecute = new AdbUtil_Execute();
	
	private static FileHandler iniFile = new FileHandler();	
	private static List<String> readList;
	private static List<String> writeList = new ArrayList<String>();
	
	//progress
	//final static JProgressBar aJProgressBar = new JProgressBar(0, 20);
	
	AdbUtil_LogCat tab1;
	AdbUtil_ApkTools tab2;
	AdbUtil_MonkeyTest tab3;
	AdbUtil_ShellCommands tab4;
	AdbUtil_Profiling tab5;
//	AdbUtil_ANR tab6;
	AdbUtil_FirmwareControl tab7;
	
	JTabbedPane tabPane = new JTabbedPane();

	public AdbUtil_Main_Layout() {
	  super("Adb Utility");
	  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  setSize(mLayoutInfo.TOTAL_SIZE_WIDTH, mLayoutInfo.TOTAL_SIZE_HEIGHT);	  
	  //setDefaultCloseOperation(EXIT_ON_CLOSE);
	  
	  mPanel1.setLayout(new BoxLayout(mPanel1, BoxLayout.Y_AXIS));
	  mPanel2.setLayout(new BoxLayout(mPanel2, BoxLayout.Y_AXIS));
	  mPanel3.setLayout(new BoxLayout(mPanel3, BoxLayout.Y_AXIS));
	  mPanel4.setLayout(new BoxLayout(mPanel4, BoxLayout.Y_AXIS));
	  mPanel5.setLayout(new BoxLayout(mPanel5, BoxLayout.Y_AXIS));
//	  mPanel6.setLayout(new BoxLayout(mPanel6, BoxLayout.Y_AXIS));
	  mPanel7.setLayout(new BoxLayout(mPanel7, BoxLayout.Y_AXIS));
	  
	  tabPane.addTab("Logcat", null, mPanel1, "adb logcat commands");
	  tabPane.addTab("Apk Util", null, mPanel2, "relative apk handling");
	  tabPane.addTab("Monkey Test", null, mPanel3, "monkey test util");
	  tabPane.addTab("Adb Shell", null, mPanel4, "adb shell commands");
	  tabPane.addTab("System Monitoring", null, mPanel5, "profiling");
//	  tabPane.addTab("ANR", null, mPanel6, "ANR occurupt");
	  tabPane.addTab("FirmwareControl", null, mPanel7, "Firmware Control");
	  
	  createIniFileHandle();
	  
	  tab1 = new AdbUtil_LogCat(getContentPane(), mPanel1, mExecute);
	  tab2 = new AdbUtil_ApkTools(getContentPane(), mPanel2, mExecute);
	  tab3 = new AdbUtil_MonkeyTest(getContentPane(), mPanel3, mExecute);
	  tab4 = new AdbUtil_ShellCommands(getContentPane(), mPanel4, mExecute);
	  tab5 = new AdbUtil_Profiling(getContentPane(), mPanel5, mExecute);
//	  tab6 = new AdbUtil_ANR(getContentPane(), mPanel6, mExecute);
	  tab7 = new AdbUtil_FirmwareControl(getContentPane(), mPanel7, mExecute);
	  
	  tabPane.addChangeListener(new ChangeListener() {
	      public void stateChanged(ChangeEvent evt) {
	        JTabbedPane pane = (JTabbedPane) evt.getSource();

	        int sel = pane.getSelectedIndex();
	        if (sel == 0)	{
	        	setCurrentTabName("AdbUtil_LogCat");
	        	setSize(mLayoutInfo.TOTAL_SIZE_WIDTH, mLayoutInfo.TOTAL_SIZE_HEIGHT);
	        } else if (sel == 1)	{
	        	setCurrentTabName("AdbUtil_ApkTools");
	        	setSize(mLayoutInfo.TOTAL_SIZE_WIDTH, mLayoutInfo.TOTAL_SIZE_HEIGHT+100);
	        } else if (sel == 2)	{
	        	setCurrentTabName("AdbUtil_MonkeyTest");
	        	setSize(mLayoutInfo.TOTAL_SIZE_WIDTH, mLayoutInfo.TOTAL_SIZE_HEIGHT);
	        } else if (sel == 3)	{
	        	setCurrentTabName("AdbUtil_ShellCommands");
	        	setSize(mLayoutInfo.TOTAL_BIG_SIZE_WIDTH + 40, mLayoutInfo.TOTAL_BIG_SIZE_HEIGHT);
	        	resultFileClear();
	        } else if (sel == 4)	{
	        	setCurrentTabName("AdbUtil_Profiling");
	        	setSize(mLayoutInfo.TOTAL_BIGBIG_SIZE_WIDTH, mLayoutInfo.TOTAL_BIGBIG_SIZE_HEIGHT);
	        	resultFileClear();
/*	        } else if (sel == 5)	{
	        	setCurrentTabName("AdbUtil_ANR");
	        	setSize(mLayoutInfo.TOTAL_BIGBIG_SIZE_WIDTH, mLayoutInfo.TOTAL_BIGBIG_SIZE_HEIGHT+90);
	        	resultFileClear();
	        } else if (sel == 6)	{*/
	        } else if (sel == 5)	{
	        	setCurrentTabName("AdbUtil_FirmwareContol");
	        	setSize(mLayoutInfo.TOTAL_SIZE_WIDTH, mLayoutInfo.TOTAL_SIZE_HEIGHT+60);
	        	resultFileClear();
	        }
	      }
	    });

	  //----------------------------------- Label -----------------------------------
	  myLabel = new JLabel("  Created by suker (½´ÄÉ¸£)  v1.7.2       since January 2011");
	  myLabel.setFont(new Font(null, mLayoutInfo.BOLD_STYLE, mLayoutInfo.TITLE_FONT_SIZE));
	  myLabel.setForeground(Color.BLUE);
	  myLabel.setVisible(true);
	  getContentPane().add(myLabel, BorderLayout.SOUTH);
	  //----------------------------------- Progress -----------------------------------
//	  aJProgressBar.setStringPainted(true);
//	  getContentPane().add(aJProgressBar, BorderLayout.SOUTH);
//	  aJProgressBar.setVisible(true);	  
	  //--------------------------------------------------------------------------------
	  setCurrentTabName("AdbUtil_LogCat");
	  getContentPane().add(tabPane, BorderLayout.CENTER);
	  
	  
	}
	private static void setCurrentTabName(String name)	{
		mCurrentTabName = name;		
	}
	public static String getCurrentTabName()	{
		return mCurrentTabName;
	}
	
	public static AdbUtil_Definition getDefinitionInstance()	{
		return mLayoutInfo;
	}
	
	private void createIniFileHandle() {
		try {
			readList = iniFile.readFile(AdbUtil_INIFile.INI_FILE_PATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private void __finallyIniFileWrite()	{
		tab1.__onDestoy();
		tab2.__onDestoy();
		tab3.__onDestoy();
		tab4.__onDestoy();
		tab5.__onDestoy();
//		tab6.__onDestoy();
		tab7.__onDestoy();
		
		try {
			iniFile.writeFile(writeList, AdbUtil_INIFile.INI_FILE_PATH);
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
	
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			__finallyIniFileWrite();
			
			if (mExecute.process != null)	{
				mExecute.process.destroy();
			}
			System.exit(0);
		}
	}
	
	private void resultFileClear()	{
		String text = "cmd.exe~~/C~~copy~~/Y~~"+ AdbUtil_INIFile.RESULT_DUMMY_FILE_PATH + "~~" + AdbUtil_INIFile.RESULT_FILE_PATH;						   
		String[] cmdList = text.split("~~");
		try {
			mExecute.runProcessSimple(cmdList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static JProgressBar getProgressBarComp()	{
//    	return aJProgressBar;
//    }
}

public class AdbUtil_Main	{
	public static void main( String args[] )
	{
		final AdbUtil_Main_Layout mainFrame = new AdbUtil_Main_Layout();
		AdbUtil_Menu menuBar = new AdbUtil_Menu();
		menuBar.makeMenu();
		mainFrame.setJMenuBar(menuBar.getJMenuBarInstance());
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.addWindowFocusListener(new WindowAdapter() {
			public void windowGainedFocus(WindowEvent e){
				mainFrame.repaint();
			}
		});
	}
}