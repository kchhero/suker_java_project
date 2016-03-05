package AdbUtilPkg;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class AdbUtil_Profiling extends JFrame implements ActionListener, ChangeListener {
	private final static int	MAX_LISTUP_SIZE = 10;
	private static boolean l_createFirstData = false;
	
	private static FileHandler resultFile = new FileHandler();
	private static AdbUtil_Execute mExe;	
	private Timer timerProfile = null;
	private List<String> readList;
	private List<String> writeList;
	private String mHelpTip = null;
	
	private static JPanel currentPanel;
	private static JTextField edit_processName1;
	private static JTextField edit_processName2;
	private static JTextField edit_processName3;
	private static JTextArea mPage5Text = new JTextArea();
	private JScrollPane mPage5ScrollBar;
	private JButton adbShell_ProcMonitoring;	//프로세스 모니터링(adb shell top -m 20 -n 20 -s cpu [-t 쓰레드])
	private JButton adbShell_ProfileReset;	
	private static AdbUtil_Graph graph;
	private static List<String> mNoticeCpu;	
	
	private static List<String> analysisPIDList = new ArrayList<String>(); 
	private static List<String> analysisCPUList = new ArrayList<String>();
	private static List<String> analysisNameList = new ArrayList<String>();
	
	private static JTextField edit_trace;
	
	public AdbUtil_Profiling(Container container, JPanel panel, AdbUtil_Execute	mExecute)	{
		currentPanel = panel;
		initHelpTipSet();
		createPage(panel);
		initPage();		
		mExe = mExecute;
		
		mNoticeCpu = new ArrayList<String>();		
		mNoticeCpu.add(0, "0");
		mNoticeCpu.add(1, "0");
		mNoticeCpu.add(2, "0");
	}
	private static void initProfiling()	{
		graph = new AdbUtil_Graph(300000, analysisNameList);
		drawSetDataGraph();
		startProfiling();
	}
	private void reCreateProfilingChart()	{
		stopProfiling();
		graph.drawDestroy();
		
		l_createFirstData = false;
		//drawSetDataGraph();
	}
	private static void startProfiling()	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		
		analysisPIDList.clear();
		analysisCPUList.clear();
		analysisNameList.clear();

		AdbUtil_Graph.setSubPanelBounds();
		graph.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		graph.setBounds(layout.CHART_START_POS_X,
						layout.CHART_START_POS_Y,
						layout.CHART_WIDTH,
						layout.CHART_HEIGHT);
		currentPanel.add(graph);
		graph.drawStart(graph);
	}
	private void stopProfiling()	{
		analysisPIDList.clear();
		analysisCPUList.clear();
		analysisNameList.clear();
		
		if (graph != null)
			graph.drawStop();
	}
	
	public void __onDestoy()	{
		l_createFirstData = false;
		
		writeList = AdbUtil_Main_Layout.getWriteFileList();		
		if (edit_processName1.getText().length() <= 1)
			edit_processName1.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE1", edit_processName1.getText(), writeList);
		
		if (edit_processName2.getText().length() <= 1)
			edit_processName2.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE2", edit_processName2.getText(), writeList);
		
		if (edit_processName3.getText().length() <= 1)
			edit_processName3.setText("none");
		AdbUtil_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE3", edit_processName3.getText(), writeList);
		
		if (timerProfile != null)	{
			timerProfile.cancel();
			timerProfile.purge();
			timerProfile = null;
		}
		stopProfiling();
		if (graph != null)
			graph.drawDestroy();
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AdbUtil_Main_Layout.getReadFileList();
	
		if (readList != null){
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE1");
			edit_processName1.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE2");
			edit_processName2.setText(resultStr);
			
			resultStr = AdbUtil_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE3");
			edit_processName3.setText(resultStr);
		}
	}
	
	private void createPage(JPanel panel)	{
		AdbUtil_Definition layout = AdbUtil_Main_Layout.getDefinitionInstance();
		panel.setLayout(null);
		
		//------------------------------------------------------------------------------------
		adbShell_ProcMonitoring = new JButton();
		adbShell_ProcMonitoring.setText("System Monitoring Start...");
		adbShell_ProcMonitoring.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		adbShell_ProcMonitoring.setBounds(layout.BUTTON_FOR_SHELL_PROCMONITOR_START_POS_X,
										  layout.BUTTON_FOR_SHELL_PROCMONITOR_START_POS_Y,
										  layout.BUTTON_FOR_SHELL_PROCMONITOR_WIDTH,
										  layout.BUTTON_FOR_SHELL_PROCMONITOR_HEIGHT);
		adbShell_ProcMonitoring.addActionListener(this);
		adbShell_ProcMonitoring.setFocusable(false);
		panel.add(adbShell_ProcMonitoring);		
		//------------------------------------------------------------------------------------
		adbShell_ProfileReset = new JButton();
		adbShell_ProfileReset.setText("Plot Reset");
		adbShell_ProfileReset.setFont(new Font(null, layout.BOLD_STYLE, layout.BUTTON_FONT_SIZE));
		adbShell_ProfileReset.setBounds(layout.BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_X,
										layout.BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_Y,
										layout.BUTTON_FOR_SHELL_PROFILE_RESET_WIDTH,
										layout.BUTTON_FOR_SHELL_PROFILE_RESET_HEIGHT);
		adbShell_ProfileReset.addActionListener(this);
		adbShell_ProfileReset.setEnabled(false);

		panel.add(adbShell_ProfileReset);
		//------------------------------------------------------------------------------------
		JLabel label_processName1 = new JLabel("notice process name 1");		
		label_processName1.setFont(new Font(null, layout.BOLD_STYLE,	layout.TITLE_FONT_SIZE));
		label_processName1.setBackground(Color.red);
		label_processName1.setOpaque(true);
		label_processName1.setHorizontalAlignment(JLabel.CENTER);
		label_processName1.setVerticalAlignment(JLabel.CENTER);
		label_processName1.setBounds(layout.LABEL5_1_SHELL_START_POS_X,
								  	 layout.LABEL5_1_SHELL_START_POS_Y,
								  	 layout.LABEL5_1_SHELL_WIDTH,
								  	 layout.LABEL5_1_SHELL_HEIGHT);
		panel.add(label_processName1);

		edit_processName1 = new JTextField();
		edit_processName1.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE-1));
		edit_processName1.setBounds(layout.EDIT5_1_SHELL_START_POS_X,
								    layout.EDIT5_1_SHELL_START_POS_Y,
								    layout.EDIT5_1_SHELL_WIDTH,
								    layout.EDIT5_1_SHELL_HEIGHT);
		edit_processName1.setEditable(true);
		panel.add(edit_processName1);
		//------------------------------------------------------------------------------------
		JLabel label_processName2 = new JLabel("notice process name 2");
		label_processName2.setFont(new Font(null, layout.BOLD_STYLE,	layout.TITLE_FONT_SIZE));
		label_processName2.setBackground(Color.green);
		label_processName2.setOpaque(true);
		label_processName2.setHorizontalAlignment(JLabel.CENTER);
		label_processName2.setVerticalAlignment(JLabel.CENTER);
		label_processName2.setBounds(layout.LABEL5_2_SHELL_START_POS_X,
								  	 layout.LABEL5_2_SHELL_START_POS_Y,
								  	 layout.LABEL5_2_SHELL_WIDTH,
								  	 layout.LABEL5_2_SHELL_HEIGHT);
		panel.add(label_processName2);
		
		edit_processName2 = new JTextField();
		edit_processName2.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE-1));		
		edit_processName2.setBounds(layout.EDIT5_2_SHELL_START_POS_X,
								    layout.EDIT5_2_SHELL_START_POS_Y,
								    layout.EDIT5_2_SHELL_WIDTH,
								    layout.EDIT5_2_SHELL_HEIGHT);
		edit_processName2.setEditable(true);
		panel.add(edit_processName2);
		//------------------------------------------------------------------------------------
		JLabel label_processName3 = new JLabel("notice process name 3");
		label_processName3.setFont(new Font(null, layout.BOLD_STYLE,	layout.TITLE_FONT_SIZE));
		label_processName3.setBackground(Color.yellow);
		label_processName3.setOpaque(true);
		label_processName3.setHorizontalAlignment(JLabel.CENTER);
		label_processName3.setVerticalAlignment(JLabel.CENTER);
		label_processName3.setBounds(layout.LABEL5_3_SHELL_START_POS_X,
								  	 layout.LABEL5_3_SHELL_START_POS_Y,
								  	 layout.LABEL5_3_SHELL_WIDTH,
								  	 layout.LABEL5_3_SHELL_HEIGHT);
		panel.add(label_processName3);
		
		edit_processName3 = new JTextField();
		edit_processName3.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE-1));
		edit_processName3.setBounds(layout.EDIT5_3_SHELL_START_POS_X,
								    layout.EDIT5_3_SHELL_START_POS_Y,
								    layout.EDIT5_3_SHELL_WIDTH,
								    layout.EDIT5_3_SHELL_HEIGHT);
		edit_processName3.setEditable(true);
		panel.add(edit_processName3);
		//------------------------------------------------------------------------------------
		mPage5Text.setLineWrap(true);
		mPage5Text.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE-2));
		mPage5Text.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {				
			}
			public void focusLost(FocusEvent e) {
			}
			@SuppressWarnings("unused")
			void displayMessage(String prefix, FocusEvent e) {
			}
		});		
		mPage5ScrollBar = new JScrollPane(mPage5Text);
		mPage5ScrollBar.setWheelScrollingEnabled(true);
		mPage5ScrollBar.setBounds(layout.LOG5_SHELL_START_POS_X,
								  layout.LOG5_SHELL_START_POS_Y,
								  layout.LOG5_SHELL_WIDTH,
								  layout.LOG5_SHELL_HEIGHT);
		panel.add(mPage5ScrollBar);
		//------------------------------------------------------------------------------------
		if (AdbUtil_Main_Layout.DEBUG_MODE_ON)	{
			edit_trace = new JTextField();
			edit_trace.setFont(new Font(null, layout.NORMAL_STYLE, layout.EDIT_FONT_SIZE+1));
			edit_trace.setBounds(layout.TRACE_EDIT_START_POS_X,
									   layout.TRACE_EDIT_START_POS_Y,
									   layout.TRACE_EDIT_WIDTH,
									   layout.TRACE_EDIT_HEIGHT);
			edit_trace.setEditable(false);
			panel.add(edit_trace);
		}
		//------------------------------------------------------------------------------------
		JLabel label_help = new JLabel(mHelpTip);
		label_help.setFont(new Font(null, layout.NORMAL_STYLE,	layout.TITLE_FONT_SIZE));
		label_help.setBackground(Color.darkGray);
		label_help.setForeground(Color.white);
		label_help.setOpaque(true);
		label_help.setHorizontalAlignment(JLabel.LEFT);
		label_help.setVerticalAlignment(JLabel.CENTER);
		label_help.setBounds(layout.LABEL5_HELP_START_POS_X,
							 layout.LABEL5_HELP_START_POS_Y,
							 layout.LABEL5_HELP_WIDTH,
							 layout.LABEL5_HELP_HEIGHT);
		panel.add(label_help);
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.adbShell_ProcMonitoring) {
			//adb shell top : 프로세스 모니터링(adb shell top -m 20 -n 20 -s cpu [-t 쓰레드])
			if (adbShell_ProcMonitoring.getText().equals("System Monitoring Start..."))	{					
				adbShell_ProcMonitoring.setText("System Monitoring Stop...");
				
				String text = "cmd.exe~~/C~~adb~~shell~~top~~-m~~10~~-n~~1~~-d~~1~~-s~~cpu~~>~~" + AdbUtil_INIFile.RESULT_FILE_PATH;						   
				String[] cmdList = text.split("~~");
				if (cmdList != null)	{
					if (timerProfile == null)	{
						timerProfile = new Timer();
					}
					if (AdbUtil_Main_Layout.DEBUG_MODE_ON)
						timerProfile.schedule(new DelayAnalysis(cmdList, mExe, "AdbUtil_Profiling", edit_trace), 500, 1000);
					else
						timerProfile.schedule(new DelayAnalysis(cmdList, mExe, "AdbUtil_Profiling", null), 500, 1000);
				}
				if (l_createFirstData)	{
					startProfiling();					
					
					if (graph.isVisible() == false)	{
						graph.setVisible(true);
						graph.invalidate();
					}
				}
				adbShell_ProfileReset.setEnabled(false);
				edit_processName1.setEditable(false);
				edit_processName2.setEditable(false);
				edit_processName3.setEditable(false);
			} else	{				
				timerProfile.purge();
				timerProfile.cancel();
				timerProfile = null;
				if (mExe.process != null)	{
					mExe.process.destroy();
				}
				adbShell_ProcMonitoring.setText("System Monitoring Start...");	
				stopProfiling();
				adbShell_ProfileReset.setEnabled(true);
				edit_processName1.setEditable(true);
				edit_processName2.setEditable(true);
				edit_processName3.setEditable(true);
			}
		}	else if (e.getSource() == this.adbShell_ProfileReset)	{
			if (adbShell_ProfileReset.isEnabled() == true)	{
				graph.setVisible(false);
				invalidate();
				reCreateProfilingChart();
				adbShell_ProfileReset.setEnabled(false);
				edit_processName1.setEditable(true);
				edit_processName2.setEditable(true);
				edit_processName3.setEditable(true);
				mNoticeCpu.set(0, "0");
				mNoticeCpu.set(1, "0");
				mNoticeCpu.set(2, "0");
			}
		}
	}
	public static void resultAnalysis()	{
		List<String> resultList = null;		 
		String resultText;
		String[] analysisText = null;
		List<String> swapStr = new ArrayList<String>();
		
		try {
			resultList = resultFile.readFile(AdbUtil_INIFile.RESULT_FILE_PATH);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (resultList != null){
			resultText = "";
			for (int i = 0; i < resultList.size(); i++)	{
				resultText += resultList.get(i);
				if (resultList.get(i).length() > 0)	{					
					resultText += "\n";
				}
			}
			if (resultText.length() > 1)	{
				mPage5Text.setText(resultText);
				mPage5Text.setFocusable(true);
			}
			
			//graph
			int j = 0;
			int t = 0;
			boolean bCanListUpStart = false;
						
			for (int i = 5; i < resultList.size(); i++)	{
				resultText = resultList.get(i);		
				if (resultList.get(i).length() > 0)	{
					analysisText = resultText.split(" ");
					for (int k = 0; k < analysisText.length; k++)	{
						if (!(analysisText[k].equals(" ") || analysisText[k].equals("")))	{
							swapStr.add(t, analysisText[k].toString());
							t++;
						}
					}
				}

				if (swapStr != null && swapStr.size() > 0)	{
					if (swapStr.get(0).equals("PID"))	{
						bCanListUpStart = true;						
					}
					else if (j < MAX_LISTUP_SIZE && bCanListUpStart == true)	{
						String temp = swapStr.get(1).toString().trim();
						String tempResult = temp.replace("%", "");
						if (edit_processName1.getText().trim().equals(swapStr.get(8).toString().trim()))	{
							mNoticeCpu.set(0, tempResult);
						} else if (edit_processName2.getText().trim().equals(swapStr.get(8).toString().trim()))	{
							mNoticeCpu.set(1, tempResult);
						} else if (edit_processName3.getText().trim().equals(swapStr.get(8).toString().trim()))	{
							mNoticeCpu.set(2, tempResult);
						} else	{
							analysisPIDList.add(j, swapStr.get(0).toString().trim());	//PID
							//% 빼기
							analysisCPUList.add(j, tempResult);	//CPU						
							analysisNameList.add(j, swapStr.get(8).toString().trim());	//pkg Name
							
							j++;
						}
					}
				}
				t = 0;
				swapStr.clear();
			}
			if (analysisPIDList.size()>0 && analysisCPUList.size()>0 && analysisNameList.size()>0)	{
				drawSetDataGraph();
			}
		}
	}
	private static void drawSetDataGraph()	{		
		if (!l_createFirstData)	{
			l_createFirstData = true;
			initProfiling();
			return ;
		}
		
		AdbUtil_Graph.setFeedData(analysisCPUList, mNoticeCpu);
	}
	private void initHelpTipSet()	{
		mHelpTip = " ** 우측 상단에 tracing하고 싶은 process name을 입력하면 해당 색상으로 graph가 표시됨.  " +				   
				   "** 다른 색상으로 표시되는 graph는 현재 cpu 점유율이 가장 높은 상위 3개 process 값.";
	}
}
