package AdbUtilPkg;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JButton;
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
public class AdbUtil_ShellCommands extends JFrame implements ActionListener, ChangeListener {
	private static AdbUtil_Execute mExe;
	private static FileHandler resultFile = new FileHandler();
	
	private JButton adbShell_KernelVer;	//커널 버전
	private JButton adbShell_ProcInfo;	//프로세서 정보, CPU타입, 모델 제조사 등
	private JButton adbShell_MemInfo;	//메모리 정보, 실제 메모리 및 가상 메모리
	private JButton adbShell_HardDisk;	//하드디스크
	private JButton adbShell_BootMsgView;
	private JButton adbShell_RunningProcInfo;	//실행중인 프로세스 정보
	private JButton adbShell_EnvSetupInfo;	
	private JButton adbShell_MemInfoForEachProc;
	private JButton adbShell_ProcMemAndDBInfoView;
	private JButton adbShell_MemoryMapProc;
	private JTextField edit_shell_PID;
	
	private static String mCurrentHandlingFilePath;
	
	//-------------------------- kernel log START -----------------------------
	private JButton 		adbShell_KernelLog;
	private JButton 		adbShell_KernelLog_Resume;
	private JButton 		adbShell_KernelLog_Pause;
	private JButton 		adbShell_KernelLog_OpenDir;
	private static int		mLastReadLineNum;
	private LogThread 		mLogThread = null;
	private final int		KERNEL_LOG_VIEW_RUNNING = 1;
	private final int		KERNEL_LOG_VIEW_PAUSE = 2;
	private final int		KERNEL_LOG_VIEW_STOP = 3;
	
	private int				KERNEL_LOG_VIEW_STATE = KERNEL_LOG_VIEW_STOP;
	//------------------------- kernel log END -------------------------------
	
	private Timer 				timer = new Timer();
	private static JTextArea 	mPage4Text = new JTextArea();
	private JScrollPane 		mPage4ScrollBar;
	
	public AdbUtil_ShellCommands(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{		
		createPage(panel);
		mExe = mExecute;
		mCurrentHandlingFilePath = null;
		mLastReadLineNum = 0;
	}
	
	public void __onDestoy()	{
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
		stopKernelLogThread();
		mExe.killProcess();
	}
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);
		
		//------------------------------------------------------------------------------------
		adbShell_KernelVer = new JButton();
		create_JButtons(panel, adbShell_KernelVer, "Kernel version",
						layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE, layout.BUTTON_FOR_SHELL_KERNELV_START_POS_X,
						layout.BUTTON_FOR_SHELL_KERNELV_START_POS_Y,
						layout.BUTTON_FOR_SHELL_KERNELV_WIDTH,
						layout.BUTTON_FOR_SHELL_KERNELV_HEIGHT);		
		//------------------------------------------------------------------------------------
		adbShell_ProcInfo = new JButton();
		create_JButtons(panel, adbShell_ProcInfo, "System Info", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_PROCINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_PROCINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_PROCINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_PROCINFO_HEIGHT);			
		//------------------------------------------------------------------------------------
		adbShell_MemInfo = new JButton();
		create_JButtons(panel, adbShell_MemInfo, "Memory Info", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_MEMINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_MEMINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_MEMINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_MEMINFO_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_HardDisk = new JButton();
		create_JButtons(panel, adbShell_HardDisk, "Disk Usage", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_HARDDISK_START_POS_X,
						layout.BUTTON_FOR_SHELL_HARDDISK_START_POS_Y,
						layout.BUTTON_FOR_SHELL_HARDDISK_WIDTH,
						layout.BUTTON_FOR_SHELL_HARDDISK_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_BootMsgView = new JButton();
		create_JButtons(panel, adbShell_BootMsgView, "Booting Message View", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_BOOTMSG_START_POS_X,
						layout.BUTTON_FOR_SHELL_BOOTMSG_START_POS_Y,
						layout.BUTTON_FOR_SHELL_BOOTMSG_WIDTH,
						layout.BUTTON_FOR_SHELL_BOOTMSG_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_RunningProcInfo = new JButton();
		create_JButtons(panel, adbShell_RunningProcInfo, "Running Process Info", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_RUNNINGPROCINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_RUNNINGPROCINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_RUNNINGPROCINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_RUNNINGPROCINFO_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_EnvSetupInfo = new JButton();
		create_JButtons(panel, adbShell_EnvSetupInfo, "System Configuration", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_ENVSETUPINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_ENVSETUPINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_ENVSETUPINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_ENVSETUPINFO_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_MemInfoForEachProc = new JButton();
		create_JButtons(panel, adbShell_MemInfoForEachProc, "Process Memory Info Detail", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_MEMPROCSINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_MEMPROCSINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_MEMPROCSINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_MEMPROCSINFO_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_ProcMemAndDBInfoView = new JButton();
		create_JButtons(panel, adbShell_ProcMemAndDBInfoView, "Process Memory & DB Info", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_PROCMEMDBINFO_START_POS_X,
						layout.BUTTON_FOR_SHELL_PROCMEMDBINFO_START_POS_Y,
						layout.BUTTON_FOR_SHELL_PROCMEMDBINFO_WIDTH,
						layout.BUTTON_FOR_SHELL_PROCMEMDBINFO_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_MemoryMapProc = new JButton();
		create_JButtons(panel, adbShell_MemoryMapProc, "Process Memory Map", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_MEMMAP_START_POS_X,
						layout.BUTTON_FOR_SHELL_MEMMAP_START_POS_Y,
						layout.BUTTON_FOR_SHELL_MEMMAP_WIDTH,
						layout.BUTTON_FOR_SHELL_MEMMAP_HEIGHT);
		//------------------------------------------------------------------------------------
		adbShell_KernelLog = new JButton();
		create_JButtons(panel, adbShell_KernelLog, "Kernel Log View Start", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
				layout.BUTTON_FOR_SHELL_KERNELLOG_START_POS_X,
				layout.BUTTON_FOR_SHELL_KERNELLOG_START_POS_Y,
				layout.BUTTON_FOR_SHELL_KERNELLOG_WIDTH,
				layout.BUTTON_FOR_SHELL_KERNELLOG_HEIGHT);
		adbShell_KernelLog_Resume = new JButton();
		create_JButtons(panel, adbShell_KernelLog_Resume, "Resume", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
				layout.BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_X,
				layout.BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_Y,
				layout.BUTTON_FOR_SHELL_KERNELLOG_RESUME_WIDTH,
				layout.BUTTON_FOR_SHELL_KERNELLOG_RESUME_HEIGHT);
		adbShell_KernelLog_Resume.setEnabled(false);
		adbShell_KernelLog_Pause = new JButton();
		create_JButtons(panel, adbShell_KernelLog_Pause, "Pause", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
				layout.BUTTON_FOR_SHELL_KERNELLOG_PAUSE_START_POS_X,
				layout.BUTTON_FOR_SHELL_KERNELLOG_PAUSE_START_POS_Y,
				layout.BUTTON_FOR_SHELL_KERNELLOG_PAUSE_WIDTH,
				layout.BUTTON_FOR_SHELL_KERNELLOG_PAUSE_HEIGHT);
		adbShell_KernelLog_Pause.setEnabled(false);
		
		adbShell_KernelLog_OpenDir = new JButton();
		create_JButtons(panel, adbShell_KernelLog_OpenDir, "Open Folder", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_SHELL_KERNELLOG_DIR_START_POS_X,
						layout.BUTTON_FOR_SHELL_KERNELLOG_DIR_START_POS_Y,
						layout.BUTTON_FOR_SHELL_KERNELLOG_DIR_WIDTH,
						layout.BUTTON_FOR_SHELL_KERNELLOG_DIR_HEIGHT);
		//------------------------------------------------------------------------------------
		mPage4Text.setLineWrap(true);
		mPage4Text.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		mPage4Text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});
		mPage4ScrollBar = new JScrollPane(mPage4Text);
		mPage4ScrollBar.setAlignmentX(LEFT_ALIGNMENT);				
		mPage4ScrollBar.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener ()
			{
				public void adjustmentValueChanged(AdjustmentEvent e)	{
					if (KERNEL_LOG_VIEW_STATE == KERNEL_LOG_VIEW_RUNNING)
						e.getAdjustable().setValue(e.getAdjustable().getMaximum()*2);
				}
			}
		);
		
		mPage4ScrollBar.setAutoscrolls(true);
		mPage4ScrollBar.setWheelScrollingEnabled(true);
		mPage4ScrollBar.setBounds(layout.LOG4_SHELL_START_POS_X,
								  layout.LOG4_SHELL_START_POS_Y,
								  layout.LOG4_SHELL_WIDTH,
								  layout.LOG4_SHELL_HEIGHT);
		panel.add(mPage4ScrollBar);
		
		//------------------------------------------------------------------------------------
		JLabel label4_1 = new JLabel("PID :");
		label4_1.setFont(new Font(null, layout.NORMAL_STYLE, layout.TITLE_FONT_SIZE));
		label4_1.setBounds(layout.LABEL4_1_SHELL_START_POS_X,
						   layout.LABEL4_1_SHELL_START_POS_Y,
						   layout.LABEL4_1_SHELL_WIDTH,
						   layout.LABEL4_1_SHELL_HEIGHT);
		panel.add(label4_1);
		
		edit_shell_PID = new JTextField();
		edit_shell_PID.setFont(new Font(null, layout.BOLD_STYLE, layout.EDIT_FONT_SIZE));
		edit_shell_PID.setBounds(layout.EDIT4_1_SHELL_START_POS_X,
								 layout.EDIT4_1_SHELL_START_POS_Y,
								 layout.EDIT4_1_SHELL_WIDTH,
								 layout.EDIT4_1_SHELL_HEIGHT);
		panel.add(edit_shell_PID);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() != this.adbShell_KernelLog) && (e.getSource() != this.adbShell_KernelLog_Pause)
				&& (e.getSource() != this.adbShell_KernelLog_Resume))	{
			KERNEL_LOG_VIEW_STATE = KERNEL_LOG_VIEW_STOP;
			adbShell_KernelLog.setEnabled(true);
			adbShell_KernelLog_Resume.setEnabled(false);
			adbShell_KernelLog_Pause.setEnabled(false);			
			stopKernelLogThread();
		}
		
		if (e.getSource() == this.adbShell_KernelVer) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/version~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_ProcInfo) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;

			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/cpuinfo~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }			
			}
		} else if (e.getSource() == this.adbShell_MemInfo) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/meminfo~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_HardDisk) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~df~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_BootMsgView) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~dmesg~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_RunningProcInfo) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~ps~~-p~~-t~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_EnvSetupInfo) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~set~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 800); }
			}
		} else if (e.getSource() == this.adbShell_MemInfoForEachProc) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			String text = "cmd.exe~~/C~~adb~~shell~~procrank~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1800); }
			}
		} else if (e.getSource() == this.adbShell_ProcMemAndDBInfoView) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			if (edit_shell_PID.getText().length() < 1)	{
				JOptionPane.showMessageDialog(this, "PID값을 넣어주세요", "Warning!", JOptionPane.WARNING_MESSAGE);
				return ;
			}			
			String text = "cmd.exe~~/C~~adb~~shell~~dumpsys~~meminfo~~";					   
			text += edit_shell_PID.getText().trim();
			text += "~~>~~";
			text += mCurrentHandlingFilePath;
			
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");			
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_MemoryMapProc) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_FILE_PATH;
			
			if (edit_shell_PID.getText().length() < 1)	{
				JOptionPane.showMessageDialog(this, "PID값을 넣어주세요", "Warning!", JOptionPane.WARNING_MESSAGE);
				return ;
			}
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/";
			text += edit_shell_PID.getText().trim();
			text += "/maps~~>~~";
			text += mCurrentHandlingFilePath;
			
			String[] cmdList = text.split("~~");
			mPage4Text.setText(" \n");
			mPage4Text.setText("loading...");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 1200); }
			}
		} else if (e.getSource() == this.adbShell_KernelLog) {
			mCurrentHandlingFilePath = AdbUtil_INIFile.RESULT_KERNELLOG_FILE_PATH;
			try {
				AdbUtil_INIFile.copyFile(AdbUtil_INIFile.RESULT_KERNELLOG_FILE_PATH,
										 AdbUtil_INIFile.RESULT_KERNELLOG_OLD_FILE_PATH);
				AdbUtil_INIFile.deleteFile(AdbUtil_INIFile.RESULT_KERNELLOG_FILE_PATH);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if (KERNEL_LOG_VIEW_STATE == KERNEL_LOG_VIEW_STOP)	{
				mLastReadLineNum = 0;
				String text = "cmd.exe~~/C~~start~~perl~~script\\adbKernelLogging.pl~~" + mCurrentHandlingFilePath;
				
				String[] cmdList = text.split("~~");
				mPage4Text.setText("loading...");
				mPage4Text.repaint();
				
				if (cmdList != null)	{
					try {
						mExe.runProcessSimple(cmdList);
						runKernelLogDraw();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				adbShell_KernelLog.setEnabled(false);
				adbShell_KernelLog_Resume.setEnabled(true);
				adbShell_KernelLog_Pause.setEnabled(true);
				KERNEL_LOG_VIEW_STATE = KERNEL_LOG_VIEW_RUNNING;
			}
		} else if (e.getSource() == this.adbShell_KernelLog_Resume) {
			if (KERNEL_LOG_VIEW_STATE == KERNEL_LOG_VIEW_PAUSE)	{
				runKernelLogDraw();
				KERNEL_LOG_VIEW_STATE = KERNEL_LOG_VIEW_RUNNING;
			}
		} else if (e.getSource() == this.adbShell_KernelLog_Pause) {
			if (KERNEL_LOG_VIEW_STATE == KERNEL_LOG_VIEW_RUNNING)	{
				stopKernelLogThread();
				KERNEL_LOG_VIEW_STATE = KERNEL_LOG_VIEW_PAUSE;
			}		
		} else if (e.getSource() == this.adbShell_KernelLog_OpenDir)	{
			try {
				Desktop.getDesktop().open(new File("kernelLog_out"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
		
	private void runKernelLogDraw()	{
		if (mLogThread == null)	{
			mLogThread = new LogThread(mPage4Text, mExe);
		
			mLogThread.start();
	        try{ 
	        	mLogThread.join(); 
	        } catch(InterruptedException ie) { 
	            ie.printStackTrace(); 
	        }
		}	else	{
			mLogThread.run();
		}        
	}
	private void stopKernelLogThread()	{
		if (mLogThread != null)	{
			LogThread.TimerStop();
			mLogThread.interrupt();			
		}
		AdbUtil_INIFile.deleteFile(AdbUtil_INIFile.RESULT_KERNELLOG_FILE_PATH);
	}
	
	private void create_JButtons(JPanel jp, JButton jB, String title, int fontStyle, int fontSize,
			 int startX, int startY, int width, int height)
	{
		if (jp != null && jB != null)	{		
			jB.setText(title);
			jB.setFont(new Font(null, fontStyle, fontSize));
			jB.setBounds(startX, startY, width, height);
			jB.addActionListener(this);
			jp.add(jB);
		}
	}
	public static void resultAnalysis()	{
		if (mCurrentHandlingFilePath.equals(AdbUtil_INIFile.RESULT_KERNELLOG_FILE_PATH))	{
			if (resultFile.isExistFile(mCurrentHandlingFilePath))	{				
				mLastReadLineNum = AdbUtil_Analysis.resultAnalysisRealTime(mLastReadLineNum, resultFile, mPage4Text, mCurrentHandlingFilePath);
			}
		}
		else
			AdbUtil_Analysis.resultAnalysis(resultFile, mPage4Text, mCurrentHandlingFilePath);
	}
}

class LogThread extends Thread {
	JTextArea log;
	AdbUtil_Execute exe;
	private static Timer timer = null;
	
	public LogThread(JTextArea l, AdbUtil_Execute e)	{
		log = l;
		exe = e;
	}
    public void run() {
    	if (timer == null)	{
    		timer = new Timer();
    		timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 2000, 1000);
    	}	else	{
    		timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ShellCommands", null), 2000, 1000);
    	}
    }
    public static void TimerStop()	{
    	if (timer != null)	{
	    	timer.cancel();
	    	timer.purge();
	    	timer = null;
    	}
    }
}