package AdbUtilPkg;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AdbUtil_ANR extends JFrame implements ActionListener, ChangeListener {
	public static final String	traceFilePath = "anr\\traces.txt";
	private static AdbUtil_Execute mExe;
	private static FileHandler resultFile = new FileHandler();
	private static String mFeedbackAnrPull = null;
	
	private JButton adbANR_GetANRTracesText;
	private JButton adbANR_TraceFileDirOpen;
	
	private static Timer timer = new Timer();
	private static JTextArea mPage6Text = new JTextArea();
	private JScrollPane mPage6ScrollBar;
	
	public AdbUtil_ANR(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{		
		createPage(panel);
		mExe = mExecute;
	}
	
	public void __onDestoy()	{
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
	}
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);
		
		//------------------------------------------------------------------------------------
		adbANR_GetANRTracesText = new JButton();
		create_JButtons(panel, adbANR_GetANRTracesText, "ANR Trace loading...", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_ANR1_START_POS_X,
						layout.BUTTON_FOR_ANR1_START_POS_Y,
						layout.BUTTON_FOR_ANR1_WIDTH,
						layout.BUTTON_FOR_ANR1_HEIGHT);
		//------------------------------------------------------------------------------------
		adbANR_TraceFileDirOpen = new JButton();
		create_JButtons(panel, adbANR_TraceFileDirOpen, "Traces.txt open folder", layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE,
						layout.BUTTON_FOR_ANR2_START_POS_X,
						layout.BUTTON_FOR_ANR2_START_POS_Y,
						layout.BUTTON_FOR_ANR2_WIDTH,
						layout.BUTTON_FOR_ANR2_HEIGHT);
		//------------------------------------------------------------------------------------
		JLabel label6_1 = new JLabel("<ANR stack trace log>");
		label6_1.setFont(new Font(null, layout.BOLD_STYLE, layout.TITLE_FONT_SIZE+1));
		label6_1.setBounds(layout.LABEL6_1_SHELL_START_POS_X,
						   layout.LABEL6_1_SHELL_START_POS_Y,
						   layout.LABEL6_1_SHELL_WIDTH,
						   layout.LABEL6_1_SHELL_HEIGHT);
		panel.add(label6_1);
		//------------------------------------------------------------------------------------
		mPage6Text.setLineWrap(true);
		mPage6Text.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE-1));
		mPage6Text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});		
		mPage6ScrollBar = new JScrollPane(mPage6Text);
		mPage6ScrollBar.setWheelScrollingEnabled(true);
		mPage6ScrollBar.setBounds(layout.LOG6_ANR_START_POS_X,
								  layout.LOG6_ANR_START_POS_Y,
								  layout.LOG6_ANR_WIDTH,
								  layout.LOG6_ANR_HEIGHT);
		panel.add(mPage6ScrollBar);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.adbANR_GetANRTracesText) {
			String text = "cmd.exe~~/C~~adb~~shell~~ls~~/data/anr/";						   
			String[] cmdList = text.split("~~");
			try {
				mExe.runProcess(cmdList, this.getClass().getName());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == this.adbANR_TraceFileDirOpen) {
			try {
				Desktop.getDesktop().open(new File("anr"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
		AdbUtil_Analysis.resultAnalysis(resultFile, mPage6Text, traceFilePath);
	}
	public static void callbackDoneExecuteProcess() {
		mFeedbackAnrPull = mExe.getResultMessages();
		
		if (mFeedbackAnrPull != null)	{
			if (mFeedbackAnrPull.length() < 1)	{	//mFeedbackAnrPull.matches(".*No.*") == true ||	//나중에.
				mPage6Text.setText("No such file or directory : anr trace.txt 파일 없음.");
				mPage6Text.invalidate();
			}	else	{
				String text = "cmd.exe~~/C~~adb~~pull~~/data/anr/traces.txt~~./anr";						   
				String[] cmdList = text.split("~~");
				mPage6Text.setText(" \n");
				mPage6Text.setText("loading...");
				if (cmdList != null)	{
					try {	mExe.runProcessSimple(cmdList);	}
					catch (IOException e1) {e1.printStackTrace();}
					catch (InterruptedException e1) {e1.printStackTrace();}
					finally { timer.schedule(new DelayAnalysis(null,null,"AdbUtil_ANR", null), 700); }
				}
			}			
		}	else	{
			return ;
		}
	}
}