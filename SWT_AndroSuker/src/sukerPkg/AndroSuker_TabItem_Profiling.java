package sukerPkg;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.jfree.chart.JFreeChart;
import org.jfree.experimental.chart.swt.ChartComposite;

public class AndroSuker_TabItem_Profiling implements AndroSuker_Definition {
	private final static int	MAX_LISTUP_SIZE = 8;
	private final static int	MAX_CORE_COUNT = 4;
	private static boolean 		l_createFirstData = false;
	
	private static FileHandler 			resultFile = new FileHandler();
	private static AndroSuker_Execute 	mExe;	
	private static Timer 				timerProfile = null;
	private static Timer 				timerProfile2 = null;
	private static Timer 				timerProfile3 = null;
	private static Timer 				timerProfile4 = null;
	private List<String> 				readList;
	private List<String> 				writeList;
	private String 						mHelpTip = null;
	private Combo 						comboCoreCount;
	private static int					coreCountByUser;
	
	private static Composite Profiling_composite;
	
	private static Text edit_processName1;
	private static Text edit_processName2;
	private static Text edit_processName3;
	private static StyledText mIopCmdInfoText;
	private static StyledText mCpuUsageText;
	private static StyledText mCpuCoreClockText;
	private static StyledText mCpuThermalText;
//	private String info_highlight_keyword;
	
	private static Button btnRadioGB;
	private static Button btnRadioICS;
	private static Button btnRadioJB;
	private static Button btnRadioKK;
	
	private static Button adbShell_ProcMonitoring;	//프로세스 모니터링(adb shell top -m 20 -n 20 -s cpu [-t 쓰레드])
	private static Button adbShell_ProfileReset;	
	private static AndroSuker_Graph graph;
	
	private Label[] labelCore = new Label[MAX_CORE_COUNT];
	
	private static List<Double> mNoticeCpu;	
	private static List<Double> analysisCPUList;		
	private static List<Double>	analysisCoreUsage;
	private static List<Double>	analysisCoreClock;	
	//private static List<String> analysisPIDList = new ArrayList<String>();
	//private static List<String> analysisNameList = new ArrayList<String>();
	
	private final int ADBPROFILING_RUN = 400;
	private final int ADBPROFILING_RESET = 401;
	private final int ADBPROFILING_RADIO_BTN_GB = 402;
	private final int ADBPROFILING_RADIO_BTN_ICS = 403;
	private final int ADBPROFILING_RADIO_BTN_JB = 404;
	private final int ADBPROFILING_RADIO_BTN_KK = 405;
	
	private final int[] ADB_TOP_GB_PROCNAME = {1,8};
	private final int[] ADB_TOP_ICS_PROCNAME = {1,2,9};
	private final int[] ADB_TOP_JB_PROCNAME = {1,2,8};
	private final int[] ADB_TOP_KK_PROCNAME = {1,2,8};
	//private final int[] ADB_TOP_LL_PROCNAME = {1,2,8};
	private final static int ADB_TOP_GB = 300;
	private final static int ADB_TOP_ICS = 301;
	private final static int ADB_TOP_JB = 302;
	private final static int ADB_TOP_KK = 303;
	//private final static int ADB_TOP_LL = 304;
	private static int[] CurrentTargetOS;
	private static int	 CurrentTargetOSFlag;
	
	private final static int    CPU_USAGE_LOCATION = 1;
	private final static int	CPU_USAGE_LOCATION_FOR_ONLY_GB = 0;
	
	private static String mCurrentHandlingFilePath;
	private static String mCurrentCoreUsageFilePath;
	private static String mCurrentClockFilePath;
	private static String mCurrentThermalFilePath;
	private final int     PROFILING_PERIOD = 1000;	//1.0 sec
	
	final Timer timer = new Timer();
	static List<String> swapStr = new ArrayList<String>();

	private static CpuStat cpuStat;
			
	public AndroSuker_TabItem_Profiling(TabFolder tabFolder, AndroSuker_Execute mExecute)	{
		initHelpTipSet();
		
		createPage(tabFolder);
		initPage();		
		mExe = mExecute;
		mCurrentHandlingFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_PROF;
		mCurrentCoreUsageFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_PROCSTAT;
		mCurrentClockFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_PROCSTAT2;
		mCurrentThermalFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_PROCSTAT3;
		
		mNoticeCpu = new ArrayList<Double>();
		mNoticeCpu.add(0, 0.0);
		mNoticeCpu.add(1, 0.0);
		mNoticeCpu.add(2, 0.0);
		
		analysisCPUList = new ArrayList<Double>();
		for (int i = 0; i < MAX_LISTUP_SIZE; i++)	{
			analysisCPUList.add(i, 0.0);
		}
		analysisCoreUsage = new ArrayList<Double>();
		for (int i = 0; i < MAX_CORE_COUNT; i++)	{
			analysisCoreUsage.add(i, 0.0);
		}
		analysisCoreClock = new ArrayList<Double>();
		for (int i = 0; i < MAX_CORE_COUNT; i++)	{
			analysisCoreClock.add(i, 0.0);
		}
		
		cpuStat = new CpuStat(MAX_CORE_COUNT);
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	private static void initProfiling()	{
		drawSetDataGraph();
		startProfiling();		
	}
	private void reCreateProfilingChart()	{
		stopProfiling();
		
		if (graph != null)
			graph.drawDestroy();
		
		l_createFirstData = false;
		//drawSetDataGraph();
	}
	private static void startProfiling()	{
		arrayListsReset();
		graph.drawStart(AndroSuker_MainCls.getShellInstance(), graph, coreCountByUser);
	}
	private static void stopProfiling()	{
		arrayListsReset();		
		if (graph != null)
			graph.drawStop();
	}
	
	public void __onFinally()	{
		l_createFirstData = false;
		
		writeList = AndroSuker_Main_Layout.getWriteFileList();		
		if (edit_processName1.getText().length() <= 1)
			edit_processName1.setText("none");
		AndroSuker_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE1", edit_processName1.getText(), writeList);
		
		if (edit_processName2.getText().length() <= 1)
			edit_processName2.setText("none");
		AndroSuker_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE2", edit_processName2.getText(), writeList);
		
		if (edit_processName3.getText().length() <= 1)
			edit_processName3.setText("none");
		AndroSuker_INIFile.writeIniFile("ADB_PROFILE_MONITORING_NOTICE3", edit_processName3.getText(), writeList);
		
		if (CurrentTargetOSFlag == ADB_TOP_GB)
			AndroSuker_INIFile.writeIniFile("ADB_PROFILE_OS_TYPE", "GB", writeList);
		else if (CurrentTargetOSFlag == ADB_TOP_ICS)
			AndroSuker_INIFile.writeIniFile("ADB_PROFILE_OS_TYPE", "ICS", writeList);
		else if (CurrentTargetOSFlag == ADB_TOP_JB)
			AndroSuker_INIFile.writeIniFile("ADB_PROFILE_OS_TYPE", "JB", writeList);		
		else
			AndroSuker_INIFile.writeIniFile("ADB_PROFILE_OS_TYPE", "KK/LL", writeList);
		
		AndroSuker_INIFile.writeIniFile("ADB_PROFILE_CORE_COUNT", String.valueOf(coreCountByUser), writeList);
		
		if (timerProfile != null)	{
			timerProfile.cancel();
			timerProfile.purge();
			timerProfile = null;
		}
		if (timerProfile2 != null)	{
			timerProfile2.cancel();
			timerProfile2.purge();
			timerProfile2 = null;
		}
		if (timerProfile3 != null)	{
			timerProfile3.cancel();
			timerProfile3.purge();
			timerProfile3 = null;
		}
		if (timerProfile4 != null)	{
			timerProfile4.cancel();
			timerProfile4.purge();
			timerProfile4 = null;
		}
		stopProfiling();
		if (graph != null)
			graph.drawDestroy();
		
		mExe.killProcess();
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
	
		btnRadioGB.setSelection(false);
		btnRadioICS.setSelection(false);
		btnRadioJB.setSelection(false);
		btnRadioKK.setSelection(false);
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE1");
			edit_processName1.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE2");
			edit_processName2.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_PROFILE_MONITORING_NOTICE3");
			edit_processName3.setText(resultStr);
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_PROFILE_OS_TYPE");
			if (resultStr.equals("GB"))	{
				btnRadioGB.setSelection(true);				
				CurrentTargetOS = ADB_TOP_GB_PROCNAME;
				CurrentTargetOSFlag = ADB_TOP_GB;
			}
			else if (resultStr.equals("ICS"))	{						
				btnRadioICS.setSelection(true);				
				CurrentTargetOS = ADB_TOP_ICS_PROCNAME;
				CurrentTargetOSFlag = ADB_TOP_ICS;
			}
			else if (resultStr.equals("JB"))	{			
				btnRadioJB.setSelection(true);				
				CurrentTargetOS = ADB_TOP_JB_PROCNAME;
				CurrentTargetOSFlag = ADB_TOP_JB;
			}
			else	{
				btnRadioKK.setSelection(true);
				CurrentTargetOS = ADB_TOP_KK_PROCNAME;
				CurrentTargetOSFlag = ADB_TOP_KK;
			}
						
			resultStr = AndroSuker_INIFile.readIniFile(readList, "ADB_PROFILE_CORE_COUNT");
			if (resultStr.length() < 1)
				resultStr = "1";
			setCoreCountIndex(resultStr);
		}
	}
	
	public static Composite getInstance()	{
		return Profiling_composite;
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	Main Frame ##########--------------------------------
		Profiling_composite = new Composite(tabFolder, SWT.FILL);
		GridLayout gl = new GridLayout(7, false);
		Profiling_composite.setLayout(gl);

		new Label(Profiling_composite, SWT.NONE);
		new Label(Profiling_composite, SWT.NONE);
		new Label(Profiling_composite, SWT.NONE);
						
		//------------------------------------------------------------------------------------
		Label label_processName1 = new Label(Profiling_composite, SWT.BORDER | SWT.SHADOW_NONE | SWT.CENTER);
		label_processName1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_processName1.setText("notice process name 1");		
		label_processName1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_processName1.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		
		//------------------------------------------------------------------------------------
		Label label_processName2 = new Label(Profiling_composite, SWT.BORDER | SWT.SHADOW_NONE | SWT.CENTER);
		label_processName2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_processName2.setText("notice process name 2");
		label_processName2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		
		//------------------------------------------------------------------------------------
		Label label_processName3 = new Label(Profiling_composite, SWT.BORDER | SWT.SHADOW_NONE | SWT.CENTER);
		label_processName3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_processName3.setText("notice process name 3");
		label_processName3.setBackground(SWTResourceManager.getColor(SWT.COLOR_YELLOW));
				
		//------------------------------------------------------------------------------------				
		adbShell_ProcMonitoring = new Button(Profiling_composite, SWT.NONE);
		GridData gd_btnNewButton_1 = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_btnNewButton_1.widthHint = 410;
		adbShell_ProcMonitoring.setLayoutData(gd_btnNewButton_1);
		adbShell_ProcMonitoring.setText("System Monitoring Start...");
		adbShell_ProcMonitoring.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBPROFILING_RUN);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBPROFILING_RUN);
		      }
		    });
		//------------------------------------------------------------------------------------
		adbShell_ProfileReset = new Button(Profiling_composite, SWT.NONE);
		adbShell_ProfileReset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		adbShell_ProfileReset.setText("Plot Reset");
		adbShell_ProfileReset.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBPROFILING_RESET);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBPROFILING_RESET);
		      }
		    });
				
		//------------------------------------------------------------------------------------
		edit_processName1 = new Text(Profiling_composite, SWT.BORDER);
		edit_processName1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		//------------------------------------------------------------------------------------
		edit_processName2 = new Text(Profiling_composite, SWT.BORDER);
		edit_processName2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		//------------------------------------------------------------------------------------
		edit_processName3 = new Text(Profiling_composite, SWT.BORDER);
		edit_processName3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				
		//------------------------------------------------------------------------------------
		Group grpAndroidOs = new Group(Profiling_composite, SWT.NO_RADIO_GROUP);
		grpAndroidOs.setText("Android OS");
		GridData gd_grpAndroidOs = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_grpAndroidOs.widthHint = 410;		
		grpAndroidOs.setLayoutData(gd_grpAndroidOs);		
		
		btnRadioGB = new Button(grpAndroidOs, SWT.RADIO);
		btnRadioGB.setBounds(21, 20, 48, 16);
		btnRadioGB.setText("GB");
		btnRadioGB.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(ADBPROFILING_RADIO_BTN_GB);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});

		btnRadioICS = new Button(grpAndroidOs, SWT.RADIO);
		btnRadioICS.setBounds(91, 20, 48, 16);
		btnRadioICS.setText("ICS");
		btnRadioICS.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(ADBPROFILING_RADIO_BTN_ICS);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		btnRadioJB = new Button(grpAndroidOs, SWT.RADIO);
		btnRadioJB.setBounds(164, 20, 48, 16);
		btnRadioJB.setText("JB");
		btnRadioJB.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(ADBPROFILING_RADIO_BTN_JB);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		btnRadioKK = new Button(grpAndroidOs, SWT.RADIO);
		btnRadioKK.setBounds(233, 20, 86, 16);
		btnRadioKK.setText("KK/LL");
		btnRadioKK.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				_actionPerformed(ADBPROFILING_RADIO_BTN_KK);
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		//new Label(Profiling_composite, SWT.NONE);
		
		//------------------------------------------------------------------------------------
		graph = new AndroSuker_Graph(300000, null, MAX_CORE_COUNT);
		JFreeChart chart = AndroSuker_Graph.getChartPanel();
		ChartComposite chartcomp = new ChartComposite(Profiling_composite, SWT.NONE, chart, true);
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 3);
		gd_composite.widthHint = 784;
		gd_composite.heightHint = 500;
		chartcomp.setLayoutData(gd_composite);
		
		//------------------------------------------------------------------------------------
		mIopCmdInfoText = new StyledText(Profiling_composite, SWT.BORDER);
		GridData gd_styledText = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
		gd_styledText.widthHint = 410;
		gd_styledText.heightHint = 326;		
		mIopCmdInfoText.setLayoutData(gd_styledText);				
	    /*mPage5Text.addLineStyleListener(new LineStyleListener() {
	        public void lineGetStyle(LineStyleEvent event) {
	          if(info_highlight_keyword == null || info_highlight_keyword.length() == 0) {
	            event.styles = new StyleRange[0];
	            return;
	          }
	          
	          String line = event.lineText;
	          int cursor = -1;
	          
	          LinkedList<StyleRange> list = new LinkedList<StyleRange>();
	          while( (cursor = line.indexOf(info_highlight_keyword, cursor+1)) >= 0) {
	            list.add(getHighlightStyle(event.lineOffset+cursor, info_highlight_keyword.length()));
	          }
	          
	          event.styles = (StyleRange[]) list.toArray(new StyleRange[list.size()]);
	        }
	        });*/
		
		new Label(Profiling_composite, SWT.NONE);
				
		//------------------------------------------------------------------------------------
		mCpuUsageText = new StyledText(Profiling_composite, SWT.BORDER);
		GridData styledtext2 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		styledtext2.widthHint = 200;
		mCpuUsageText.setLayoutData(styledtext2);
		
		mCpuCoreClockText = new StyledText(Profiling_composite, SWT.BORDER);
		GridData styledtext3 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		styledtext3.widthHint = 200;
		mCpuCoreClockText.setLayoutData(styledtext3);
		
		new Label(Profiling_composite, SWT.NONE);
				
		mCpuThermalText = new StyledText(Profiling_composite, SWT.BORDER);
		GridData styledtext4 = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3);
		styledtext4.widthHint = 400;
		mCpuThermalText.setLayoutData(styledtext4);
		
		new Label(Profiling_composite, SWT.NONE);
		new Label(Profiling_composite, SWT.NONE);		
		
		//------------------------------------------------------------------------------------
		labelCore[0] = new Label(Profiling_composite, SWT.SHADOW_NONE | SWT.CENTER);
		labelCore[0].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		
		labelCore[0].setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		labelCore[0].setBackground(SWTResourceManager.getColor(PANEL_COLOR_CORE_USAGE[0]));
		labelCore[0].setText("core 0");
				
		labelCore[1] = new Label(Profiling_composite, SWT.SHADOW_NONE | SWT.CENTER);
		labelCore[1].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));				
		labelCore[1].setBackground(SWTResourceManager.getColor(PANEL_COLOR_CORE_USAGE[1]));
		labelCore[1].setText("core 1");
		
		Label lblCoreCount = new Label(Profiling_composite, SWT.NONE);
		lblCoreCount.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCoreCount.setText("Core Count");
		
		new Label(Profiling_composite, SWT.NONE);		
		new Label(Profiling_composite, SWT.NONE);
		new Label(Profiling_composite, SWT.NONE);
		
		labelCore[2] = new Label(Profiling_composite, SWT.SHADOW_NONE | SWT.CENTER);
		labelCore[2].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		
		labelCore[2].setBackground(SWTResourceManager.getColor(PANEL_COLOR_CORE_USAGE[2]));
		labelCore[2].setText("core 2");
		
		labelCore[3] = new Label(Profiling_composite, SWT.SHADOW_NONE | SWT.CENTER);
		labelCore[3].setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));		
		labelCore[3].setBackground(SWTResourceManager.getColor(PANEL_COLOR_CORE_USAGE[3]));
		labelCore[3].setText("core 3");
		
		comboCoreCount = new Combo(Profiling_composite, SWT.NONE);
		comboCoreCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboCoreCount.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
		    	  coreCountByUser = comboCoreCount.getSelectionIndex() + 1;
		    	  for (int i = 0; i < coreCountByUser; i++)	{
		    		  labelCore[i].setVisible(true);
		    	  }
		    	  for (int i = coreCountByUser; i < MAX_CORE_COUNT; i++)	{
		    		  labelCore[i].setVisible(false);
		    	  }		    	  
		      }
	    });
		comboCoreCount.add("1");
		comboCoreCount.add("2");
		comboCoreCount.add("3");
		comboCoreCount.add("4");

		//------------------------------------------------------------------------------------
		Label label_help = new Label(Profiling_composite, SWT.NONE);
		label_help.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 6, 1));
		label_help.setText(mHelpTip);
	}
	
	private static void arrayListsReset()	{
		for (int i = 0; i < MAX_LISTUP_SIZE; i++)	{
			analysisCPUList.set(i, 0.0);
		}
		for (int i = 0; i < MAX_CORE_COUNT; i++)	{
			analysisCoreUsage.set(i, 0.0);
			analysisCoreClock.set(i, 0.0);
		}
	}
	
	public void _actionPerformed(int action) {
		if (ADBPROFILING_RUN == action) {
			//adb shell top : 프로세스 모니터링(adb shell top -m 20 -n 20 -s cpu [-t 쓰레드])
			if (adbShell_ProcMonitoring.getText().equals("System Monitoring Start..."))	{					
				actionStart();
			} else	{				
				actionStop();
			}
		}	
		else if (ADBPROFILING_RESET == action)	{
			if (adbShell_ProfileReset.isEnabled() == true)	{
				graph.clearXYPlots();
				reCreateProfilingChart();
				adbShell_ProfileReset.setEnabled(false);
				edit_processName1.setEditable(true);
				edit_processName2.setEditable(true);
				edit_processName3.setEditable(true);
				mNoticeCpu.set(0, 0.0);
				mNoticeCpu.set(1, 0.0);
				mNoticeCpu.set(2, 0.0);
								
				arrayListsReset();				
			}
		}
		else if (ADBPROFILING_RADIO_BTN_GB == action)	{
			CurrentTargetOS = ADB_TOP_GB_PROCNAME;
			CurrentTargetOSFlag = ADB_TOP_GB;
			btnRadioGB.setSelection(true);
			btnRadioICS.setSelection(false);
			btnRadioJB.setSelection(false);
			btnRadioKK.setSelection(false);
		}
		else if (ADBPROFILING_RADIO_BTN_ICS == action)	{
			CurrentTargetOS = ADB_TOP_ICS_PROCNAME;
			CurrentTargetOSFlag = ADB_TOP_ICS;
			btnRadioGB.setSelection(false);
			btnRadioICS.setSelection(true);
			btnRadioJB.setSelection(false);
			btnRadioKK.setSelection(false);
		}
		else if (ADBPROFILING_RADIO_BTN_JB == action)	{
			CurrentTargetOS = ADB_TOP_JB_PROCNAME;
			CurrentTargetOSFlag = ADB_TOP_JB;
			btnRadioGB.setSelection(false);
			btnRadioICS.setSelection(false);
			btnRadioJB.setSelection(true);
			btnRadioKK.setSelection(false);
		}
		else if (ADBPROFILING_RADIO_BTN_KK == action)	{
			CurrentTargetOS = ADB_TOP_KK_PROCNAME;
			CurrentTargetOSFlag = ADB_TOP_KK;
			btnRadioGB.setSelection(false);
			btnRadioICS.setSelection(false);
			btnRadioJB.setSelection(false);
			btnRadioKK.setSelection(true);
		}
	}
	
	public void actionStart()	{
		adbShell_ProcMonitoring.setText("System Monitoring Stop...");
		//-----------------------------------------------------------------------------------------------
		//CPU PID usage
		//-----------------------------------------------------------------------------------------------
		String text = "perl~~script/adbShellCmd.pl" + "~~" + String.valueOf(MAX_LISTUP_SIZE) + "~~"+ mCurrentHandlingFilePath;						   
		String[] cmdList = text.split("~~");
		/*String text = "cmd.exe~~/C~~adb~~shell~~top~~-m~~" + String.valueOf(MAX_LISTUP_SIZE) + "~~-n~~1~~-d~~1~~-s~~cpu~~>~~" + mCurrentHandlingFilePath;						   
		String[] cmdList = text.split("~~");*/
		if (cmdList != null)	{
			if (timerProfile == null)	{
				timerProfile = new Timer();
			}
			timerProfile.schedule(new DelayAnalysis(cmdList, mExe, getCurrentClsName(), null), 500, PROFILING_PERIOD);
		}
		
//		if (AndroSuker_Debug.DEBUG_MODE_ON)
//			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Debugging", "Debugging Step 1", SWT.OK);
        
		//-----------------------------------------------------------------------------------------------
		//CPU core usage
		//-----------------------------------------------------------------------------------------------		
		String cmd = "perl~~script/adbShellCmdSimple.pl" + "~~" + "cat /proc/stat" + "~~" + mCurrentCoreUsageFilePath;			
		String[] cmdList2 = cmd.split("~~");
		/*String cmd = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/stat~~>~~" + mCurrentCoreUsageFilePath;			
		String[] cmdList2 = cmd.split("~~");*/
		if (cmdList2 != null)	{
			if (timerProfile2 == null)	{
				timerProfile2 = new Timer();
			}
			timerProfile2.schedule(new DelayAnalysis(cmdList2, mExe, getCurrentClsName()+"2", null), 500, PROFILING_PERIOD);
		}		
		
//		if (AndroSuker_Debug.DEBUG_MODE_ON)
//			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Debugging", "Debugging Step 2", SWT.OK);
		
		//-----------------------------------------------------------------------------------------------
		//CPU clock
		//-----------------------------------------------------------------------------------------------
		String cmd2 = "perl~~script/adbCpuFreqCheck.pl~~" + mCurrentClockFilePath;			
		String[] cmdList3 = cmd2.split("~~");
		
		if (cmdList3 != null)	{
			if (timerProfile3 == null)	{
				timerProfile3 = new Timer();
			}
			timerProfile3.schedule(new DelayAnalysis(cmdList3, mExe, getCurrentClsName()+"3", null), 500, PROFILING_PERIOD);
		}		
		
//		if (AndroSuker_Debug.DEBUG_MODE_ON)
//			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Debugging", "Debugging Step 3", SWT.OK);
		
		//-----------------------------------------------------------------------------------------------
		//CPU clock
		//-----------------------------------------------------------------------------------------------
		String cmd_thermal = "perl~~script/adbThermalCheck.pl~~" + mCurrentThermalFilePath;			
		String[] cmdList_thermal = cmd_thermal.split("~~");
		
		if (cmdList_thermal != null)	{
			if (timerProfile4 == null)	{
				timerProfile4 = new Timer();
			}
			timerProfile4.schedule(new DelayAnalysis(cmdList_thermal, mExe, getCurrentClsName()+"3", null), 500, PROFILING_PERIOD);
		}		
		
//		if (AndroSuker_Debug.DEBUG_MODE_ON)
//			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Debugging", "Debugging Step 3", SWT.OK);

		//-----------------------------------------------------------------------------------------------
		
		if (l_createFirstData)	{
			startProfiling();					
		}
		adbShell_ProfileReset.setEnabled(false);
		edit_processName1.setEditable(false);
		edit_processName2.setEditable(false);
		edit_processName3.setEditable(false);
		btnRadioGB.setEnabled(false);
		btnRadioICS.setEnabled(false);
		btnRadioJB.setEnabled(false);
		btnRadioKK.setEnabled(false);
		comboCoreCount.setEnabled(false);
		
		//if (AndroSuker_Debug.DEBUG_MODE_ON)
			//AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Debugging", "Debugging Step3", SWT.OK);
	}
	public void actionStop()	{
		timerProfile.cancel();
		timerProfile.purge();
		timerProfile = null;
		
		timerProfile2.cancel();
		timerProfile2.purge();		
		timerProfile2 = null;
		
		timerProfile3.cancel();
		timerProfile3.purge();		
		timerProfile3 = null;
		
		timerProfile4.cancel();
		timerProfile4.purge();		
		timerProfile4 = null;
		
		if (mExe.process != null)	{
			mExe.process.destroy();
		}
		adbShell_ProcMonitoring.setText("System Monitoring Start...");	
		stopProfiling();
		adbShell_ProfileReset.setEnabled(true);
		edit_processName1.setEditable(true);
		edit_processName2.setEditable(true);
		edit_processName3.setEditable(true);
		btnRadioGB.setEnabled(true);
		btnRadioICS.setEnabled(true);
		btnRadioJB.setEnabled(true);
		btnRadioKK.setEnabled(true);
		comboCoreCount.setEnabled(true);
	}
	
	public static void resultAnalysis(boolean update)	{
		final boolean doUpdate = update;
		
		if (AndroSuker_MainCls.getShellInstance().isDisposed())
			return ;
		
		AndroSuker_MainCls.getShellInstance().getDisplay().syncExec(new Runnable() { 
			public void run(){
				if (!doUpdate)	{
					List<String> resultList = null;	 
					StringBuilder resultText;
					String[] analysisText = null;
					String tempText = "";
					
					try {
						resultList = resultFile.readFile(mCurrentHandlingFilePath);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					int resultListSize = 0;
					resultListSize = resultList.size();
					
					if (resultList != null){
						resultText = new StringBuilder(resultListSize * TEXT_LINE_WIDTH);
						for (int i = 0; i < resultListSize; i++)	{
							resultText.append(resultList.get(i));
							if (resultList.get(i).length() > 0)	{					
								resultText.append("\n\n");
							}
						}
						if (mIopCmdInfoText != null && !mIopCmdInfoText.isDisposed() && resultText.length() > 1)	{
							if (AndroSuker_Debug.DEBUG_MODE_ON)	{
	        					System.out.print("resultAnalysis print text \n");
	        				}
							mIopCmdInfoText.setText(resultText.toString());
						}
						
						//graph
						int j = 0;					
						int frontSplitLength;
						int behindSplitLength = 3;
						int debuggingCnt = 0;
																	
						boolean bCanListUpStart = false;
						boolean bProc1Exist = false;
						boolean bProc2Exist = false;
						boolean bProc3Exist = false;
						
						if (CurrentTargetOSFlag == ADB_TOP_GB)
							frontSplitLength = CurrentTargetOS[CPU_USAGE_LOCATION_FOR_ONLY_GB]+2;
						else
							frontSplitLength = CurrentTargetOS[CPU_USAGE_LOCATION]+2;
						
						resultListSize = resultList.size();
						for (int i = 5; i < resultListSize; i++)	{
							tempText = resultList.get(i);		
							if (resultList.get(i).length() > 0)	{
								int t = 0;
								analysisText = tempText.split("[ ]+");
								for (int k = 0; k < analysisText.length; k++)	{
									if (!(analysisText[k].length() <= 0))	{
										swapStr.add(t, analysisText[k].toString());
										t++;
										if (t >= frontSplitLength)
											break;
										if (AndroSuker_Debug.DEBUG_MODE_ON)	{
											debuggingCnt++;
										}
									}
								}
								for (int k = analysisText.length - behindSplitLength; k < analysisText.length; k++)	{								
									if (!(analysisText[k].length() <= 0))	{
										swapStr.add(t, analysisText[k].toString());
										t++;
										if (AndroSuker_Debug.DEBUG_MODE_ON)	{
											debuggingCnt++;
										}
									}
								}							
							}
							
							if (AndroSuker_Debug.DEBUG_MODE_ON)	{
								System.out.println("debuggingCnt = "+debuggingCnt+"\n");
							}
							
							if (swapStr != null && swapStr.size() > 0)	{
								if (swapStr.get(0).equals("PID"))	{
									bCanListUpStart = true;
								}
								else if (j < MAX_LISTUP_SIZE && bCanListUpStart == true)	{
									String temp;								
									if (CurrentTargetOSFlag == ADB_TOP_GB)
										temp = swapStr.get(CurrentTargetOS[CPU_USAGE_LOCATION_FOR_ONLY_GB]).toString().trim();
									else
										temp = swapStr.get(CurrentTargetOS[CPU_USAGE_LOCATION]).toString().trim();
																	
									String tempResult = temp.replace("%", "");
									Double uTempResult = Double.valueOf(tempResult);
									
									//CPU pid Usage
									int nameLocation = swapStr.size()-1;
	
									if (edit_processName1.getText().trim().equals(swapStr.get(nameLocation).toString().trim()))	{
										mNoticeCpu.set(0, uTempResult);
										bProc1Exist = true;
										if (AndroSuker_Debug.DEBUG_MODE_ON)
											System.out.print("process 1 : " + edit_processName1.getText() + "\n");
									} else if (edit_processName2.getText().trim().equals(swapStr.get(nameLocation).toString().trim()))	{
										mNoticeCpu.set(1, uTempResult);
										bProc2Exist = true;
										if (AndroSuker_Debug.DEBUG_MODE_ON)
											System.out.print("process 2 : " + edit_processName2.getText() + "\n");
									} else if (edit_processName3.getText().trim().equals(swapStr.get(nameLocation).toString().trim()))	{
										mNoticeCpu.set(2, uTempResult);
										bProc3Exist = true;
										if (AndroSuker_Debug.DEBUG_MODE_ON)
											System.out.print("process 3 : " + edit_processName3.getText() + "\n");
									} else	{
										if (AndroSuker_Debug.DEBUG_MODE_ON)
											System.out.print("else \n");
										//analysisPIDList.add(j, swapStr.get(0).toString().trim());	//PID
										analysisCPUList.set(j, uTempResult);	//CPU								
										//analysisNameList.add(j, swapStr.get(nameLocation).toString().trim());	//pkg Name
			
										j++;
									}
								}
							}
							swapStr.clear();
						}
						if (bCanListUpStart == true)	{
							if (bProc1Exist == false)
								mNoticeCpu.set(0, 0.0);
							if (bProc2Exist == false)
								mNoticeCpu.set(1, 0.0);
							if (bProc3Exist == false)
								mNoticeCpu.set(2, 0.0);
						}						
					}
				}
				else {	//doUpdate == true
					//CPU Usage
					if (cpuStat == null)	{
						mCpuUsageText.setText("busy wait...");
					}
					else {
						//CPU core Usage
						mCpuUsageText.setText(cpuStat.setCoreUsageInfo(mCurrentCoreUsageFilePath));
						for (int i = 0; i < cpuStat.getCoreUsageInfo().size(); i++)	{
							analysisCoreUsage.set(i, cpuStat.getCoreUsageInfo().get(i));
						}
						
						//CPU clk
						mCpuCoreClockText.setText("    cpuinfo_cur_freq    \n");
						mCpuCoreClockText.append( "------------------------");
						mCpuCoreClockText.append(cpuStat.setCoreClockInfo(mCurrentClockFilePath));
						for (int i = 0; i < cpuStat.getCoreClockInfo().size(); i++)	{
							if (cpuStat.getCoreClockInfo().size() > 4)	{
								System.out.print("result debug : " + i + "   " + cpuStat.getCoreClockInfo().size() + "\n");  
								System.out.print("result debug : " + cpuStat.getCoreClockInfo() + "\n");
							}
							analysisCoreClock.set(i, cpuStat.getCoreClockInfo().get(i));
						}
						
						if (analysisCPUList.size()>0 && analysisCoreUsage.size()>0 && analysisCoreClock.size()>0)	{
							drawSetDataGraph();
						}
						
						//Thermal Check
						mCpuThermalText.setText(cpuStat.setThermalInfo(mCurrentThermalFilePath));
					}
				}
			}}
		);		
	}
	
	private static void drawSetDataGraph()	{		
		if (!l_createFirstData)	{
			l_createFirstData = true;
			initProfiling();
			return ;
		}
		AndroSuker_Graph.setFeedData(analysisCPUList, mNoticeCpu, analysisCoreUsage, analysisCoreClock);
	}
	private void initHelpTipSet()	{
		mHelpTip = "** 우측 상단에 tracing하고 싶은 process name을 입력하면 해당 색상으로 graph가 표시됨.  " +				   
				   "** 다른 색상으로 표시되는 graph는 현재 cpu 점유율이 가장 높은 상위 3개 process 값.";
	}

	private void setCoreCountIndex(String index)	{
		comboCoreCount.select(Integer.parseInt(index)-1);
		coreCountByUser = comboCoreCount.getSelectionIndex()+1;
		for (int i = 0; i < coreCountByUser; i++)	{
			labelCore[i].setVisible(true);
		}
		for (int i = coreCountByUser; i < MAX_CORE_COUNT; i++)	{
			labelCore[i].setVisible(false);
		}	
	}
}

class CpuStat {
    private RandomAccessFile statFile;
    private RandomAccessFile statFile2;
    private RandomAccessFile statFile3;
    private CpuInfo mCpuInfoTotal;
    private ArrayList<CpuInfo> mCpuInfoList;
    private ArrayList<String> mCpuClockOnlineList = new ArrayList<String>();
    private ArrayList<Double> mCpuClockFreqList = new ArrayList<Double>();
    private ArrayList<String> mThermalList = new ArrayList<String>();
    private List<Double> usage = new ArrayList<Double>();
    private List<String> clock = new ArrayList<String>();
    private int	updateCnt = 0;
    private int maxCoreCount;
    private String onlineListTemp = "0.0";
    private StringBuffer buf = new StringBuffer();
    
    public CpuStat(int count) {
    	int i;
    	maxCoreCount = count;    	
    	for (i = 0; i < maxCoreCount; i++)	{
    		usage.add(i, 0.0);
    		clock.add(i, "");
    	}
    }

    public void update(String filename) {
        try {           
            createFile(filename);
            parseFile();
            closeFile();
        } catch (FileNotFoundException e) {
            statFile = null;
        } catch (IOException e) {
        }
    }
    public void update2(String filename) {
        try {           
            createFile2(filename);
            parseFile2();
            closeFile2();
        } catch (FileNotFoundException e) {
            statFile2 = null;
        } catch (IOException e) {
        }
    }
    public void update3(String filename) {
        try {           
            createFile3(filename);
            parseFile3();
            closeFile3();
        } catch (FileNotFoundException e) {
            statFile3 = null;
        } catch (IOException e) {
        }
    }
    private void createFile(String filename) {
        try {
			statFile = new RandomAccessFile(filename, "r");
		} catch (FileNotFoundException e) {
			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error1", "Tracing File not create or exist!!\nMaybe your device did not rooting", SWT.OK);
		}
    }
    private void createFile2(String filename) {
        try {
			statFile2 = new RandomAccessFile(filename, "r");
		} catch (FileNotFoundException e) {
			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error2", "Tracing File not create or exist!!\nMaybe your device did not rooting", SWT.OK);
		}
    }
    private void createFile3(String filename) {
        try {
			statFile3 = new RandomAccessFile(filename, "r");
		} catch (FileNotFoundException e) {
			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error3", "Tracing File not create or exist!!\nMaybe your device did not rooting", SWT.OK);
		}
    }
    public void closeFile() throws IOException {
        if (statFile != null)
            statFile.close();
    }
    public void closeFile2() throws IOException {
        if (statFile2 != null)
            statFile2.close();
    }
    public void closeFile3() throws IOException {
        if (statFile3 != null)
            statFile3.close();
    }
    
    private void parseFile() {
        if (statFile != null) {
            try {
                statFile.seek(0);
                String cpuLine = "";
                int cpuId = -1;
                boolean ret = false;
                do { 
                    cpuLine = statFile.readLine();
                    ret = parseCpuLine(cpuId, cpuLine);
                    if (ret == true)
                    	cpuId++;
                } while (cpuLine != null);
            } catch (IOException e) {
            }
        }
    }
    private void parseFile2() {
        if (statFile2 != null) {
        	clock.clear();
            try {
                statFile2.seek(0);
                String read_cpuinfo = "";
                int i = 0;
                do {
                	read_cpuinfo = statFile2.readLine();
                	if (read_cpuinfo == null)
                		return ;
                	
                	if (read_cpuinfo.length() > 0)	{
                		clock.add(i, read_cpuinfo);
                		i++;
                	}
                } while (true);
            } catch (IOException e) {
            }
        }
    }
    private void parseFile3() {
        if (statFile3 != null) {
            try {
                statFile3.seek(0);
                String read_thermalinfo = "";
                do {
                	read_thermalinfo = statFile3.readLine();
                	if (read_thermalinfo == null)
                		return ;
                	
                	if (read_thermalinfo.length() > 0)	{
                		mThermalList.add(read_thermalinfo);
                	}
                } while (true);
            } catch (IOException e) {
            }
        }
    }
    
    private boolean parseCpuLine(int cpuId, String cpuLine) {
        if (cpuLine != null && cpuLine.length() > 0) { 
            String[] parts = cpuLine.split("[ ]+");
            String cpuLabel = "cpu";
            if ( parts[0].indexOf(cpuLabel) != -1) {
                createCpuInfo(cpuId, parts);                
            }
            else
            	return false;            
        } else {
        	return false;
        }
        return true;
    }

    private void createCpuInfo(int cpuId, String[] parts) {
        if (cpuId == -1) {                      
        	if (mCpuInfoTotal == null)
        		mCpuInfoTotal = new CpuInfo();
        	mCpuInfoTotal.update(parts);
        } else {
        	updateCnt++;
            if (mCpuInfoList == null)
                mCpuInfoList = new ArrayList<CpuInfo>();
            if (cpuId < mCpuInfoList.size())
                mCpuInfoList.get(cpuId).update(parts);                        
            else {
                CpuInfo info = new CpuInfo();
                info.update(parts);
                mCpuInfoList.add(info);
            }                               
        }
    }

    public String setCoreUsageInfo(String filename) {
    	updateCnt = 0;
        update(filename);
        if (mCpuInfoList == null)
        	return "error";
        
        if (mCpuInfoList.size() > updateCnt && updateCnt > 0)	{
        	for (int i = mCpuInfoList.size()-updateCnt; i > 0; i--)	{
        		mCpuInfoList.remove(i);
        		usage.remove(i);
        	}
        }
        
        buf.setLength(0);
        buf.append("\n");
        if (mCpuInfoTotal != null) {
            buf.append(" Cpu Total : ");
            buf.append(mCpuInfoTotal.getUsage());
            buf.append("%\n");
        } 
        if (mCpuInfoList != null) {
            for (int i=0; i < mCpuInfoList.size(); i++) {
                CpuInfo info = mCpuInfoList.get(i); 
                buf.append(" Cpu Core " + i + " : ");
                buf.append(info.getUsage());
                buf.append("%\n");
                if (usage.size() < mCpuInfoList.size())
                {
                	usage.clear();
                	for (int j = 0; j < maxCoreCount; j++)	{
                		usage.add(j, 0.0);
                	}
                }
                usage.set(i, (double)info.getUsage());
            }
        }
        return buf.toString();
    }
    
	public String setCoreClockInfo(String filename) {    	
    	String temp = "";
    	buf.setLength(0);
    	buf.append("\n");
    	
    	updateCnt = 0;
        update2(filename);
        
        if (clock.size() < maxCoreCount*2) {
        	return "\nWarning : strange read device!";
        }
        
        mCpuClockFreqList.clear();
        mCpuClockOnlineList.clear();
        for (int i=0; i < maxCoreCount; i++)	{
       		mCpuClockOnlineList.add(clock.get(i));
        }
        for (int i=maxCoreCount; i < maxCoreCount*2; i++)	{
        	temp = clock.get(i);
        	if (temp.length() < 9)	{
        		mCpuClockFreqList.add(Double.valueOf(clock.get(i)));
        	} else {
        		mCpuClockFreqList.add(0.0);
        	}
        }
        
        for (int i=0; i < maxCoreCount; i++)	{
        	temp = String.valueOf(mCpuClockFreqList.get(i));
        	if (mCpuClockOnlineList.get(i).equals("1")) {
        		onlineListTemp = String.valueOf(mCpuClockFreqList.get(i));
        		if (onlineListTemp.equals("0.0"))	{
        			buf.append(" Cpu " + i + " freq : off \n");
        		} else {
        			buf.append(" Cpu " + i + " freq : " + onlineListTemp + "\n");
        		}
        	} else {
        		mCpuClockFreqList.set(i, 0.0);
        		buf.append(" Cpu " + i + " freq : off \n");
        	}
        }
             
        return buf.toString();		
    }
    
	public String setThermalInfo(String filename) {		
		buf.setLength(0);
    	buf.append("pa_therm Result ==> \n");
    	
    	mThermalList.clear();
    	updateCnt = 0;
        update3(filename);

        for (int i=0; i < mThermalList.size(); i++)	{
        	buf.append("    " + mThermalList.get(i) + "\n");
        }
        return buf.toString();
	}
	
    public List<Double> getCoreUsageInfo() {    	
    	return usage;
    }
    public List<Double> getCoreClockInfo() {    	
    	return mCpuClockFreqList;
    }
    
    public int getReadCoreCount()	{
    	return mCpuInfoList.size();
    }
    
    public class CpuInfo {
        private int  mUsage;            
        private long mLastTotal;
        private long mLastIdle;

        public CpuInfo() {
            mUsage = 0;
            mLastTotal = 0;
            mLastIdle = 0;  
        }

        private int  getUsage() {
            return mUsage;
        }

        public void update(String[] parts) {
            // the columns are:
            //
            //      0 "cpu": the string "cpu" that identifies the line
            //      1 user: normal processes executing in user mode
            //      2 nice: niced processes executing in user mode
            //      3 system: processes executing in kernel mode
            //      4 idle: twiddling thumbs
            //      5 iowait: waiting for I/O to complete
            //      6 irq: servicing interrupts
            //      7 softirq: servicing softirqs
            //
        	if (parts.length < 5)
        		return ;
        	
            long idle = Long.parseLong(parts[4], 10);
            long total = 0;
            boolean head = true;
            for (String part : parts) {
                if (head) {
                    head = false;
                    continue;
                }
                total += Long.parseLong(part, 10);
            }
            long diffIdle   =   idle - mLastIdle;
            long diffTotal  =   total - mLastTotal;
            mUsage = (int)((float)(diffTotal - diffIdle) / diffTotal * 100);
            mLastTotal = total;
            mLastIdle = idle;
            //Log.i(TAG, "CPU total=" + total + "; idle=" + idle + "; usage=" + mUsage);
        }
    }
}