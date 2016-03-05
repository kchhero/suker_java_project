package sukerPkg;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;

public class AndroSuker_TabItem_ShellCommands implements AndroSuker_Definition {
	private static Composite ShellCommand_composite;
	private static AndroSuker_Execute mExe;
	private static FileHandler resultFile = new FileHandler();
	
	private Button adbShell_KernelVer;	//커널 버전
	private Button adbShell_ProcInfo;	//프로세서 정보, CPU타입, 모델 제조사 등
	private Button adbShell_MemInfo;	//메모리 정보, 실제 메모리 및 가상 메모리
	private Button adbShell_HardDisk;	//하드디스크
	//private Button adbShell_BootMsgView;
	private Button adbShell_RunningProcInfo;	//실행중인 프로세스 정보
	
	private static String mCurrentHandlingFilePath;
	
	private final int ADBSHELL_KERVER = 300;
	private final int ADBSHELL_PROCINFO = 301;
	private final int ADBSHELL_MEMINFO = 302;
	private final int ADBSHELL_DISKUSAGE = 303;
	//private final int ADBSHELL_BOOTMSG = 304;
	private final int ADBSHELL_RUNNIGPROCINFO = 305;
	private final int ADBSHELL_SYSCONFINFO = 306;
	//private final int ADBSHELL_PROCMEMINFODETAIL = 307;

	private Timer 				timer = new Timer();
	private static StyledText 	infoText;
	private String 				info_highlight_keyword;
	
	public AndroSuker_TabItem_ShellCommands(TabFolder tabFolder, AndroSuker_Execute mExecute) {
		createPage(tabFolder);
		mExe = mExecute;
		mCurrentHandlingFilePath = AndroSuker_INIFile.RESULT_FILE_PATH_SHELLCMD;
	}
	
	public void __onFinally()	{
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
		mExe.killProcess();
	}
	
	public static Composite getInstance()	{
		return ShellCommand_composite;
	}
	public String getCurrentClsName()	{		
		return this.getClass().getName();
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	Main Frame ##########--------------------------------
		ShellCommand_composite = new Composite(tabFolder, SWT.NONE);//SWT.FILL_WINDING);
		GridLayout gl = new GridLayout(5,false);
		gl.verticalSpacing = 10;
		gl.marginHeight = 25;
		ShellCommand_composite.setLayout(gl);
		
		//------------------------------------------------------------------------------------
		adbShell_KernelVer = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_KernelVer = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_KernelVer.widthHint = 175;
		adbShell_KernelVer.setLayoutData(gd_adbShell_KernelVer);
		adbShell_KernelVer.setText("Kernel version");		
		adbShell_KernelVer.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_KERVER);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_KERVER);
		      }
		    });
		//------------------------------------------------------------------------------------
		adbShell_ProcInfo = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_ProcInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_ProcInfo.widthHint = 175;
		adbShell_ProcInfo.setLayoutData(gd_adbShell_ProcInfo);
		adbShell_ProcInfo.setText("System Info");
		adbShell_ProcInfo.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_PROCINFO);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_PROCINFO);
		      }
		    });
		//------------------------------------------------------------------------------------
		adbShell_MemInfo = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_MemInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_MemInfo.widthHint = 175;
		adbShell_MemInfo.setLayoutData(gd_adbShell_MemInfo);
		adbShell_MemInfo.setText("Memory Info");
		adbShell_MemInfo.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_MEMINFO);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_MEMINFO);
		      }
		    });
		//------------------------------------------------------------------------------------
		adbShell_HardDisk = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_HardDisk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_HardDisk.widthHint = 175;
		adbShell_HardDisk.setLayoutData(gd_adbShell_HardDisk);
		adbShell_HardDisk.setText("Disk Usage");
		adbShell_HardDisk.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_DISKUSAGE);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_DISKUSAGE);
		      }
		    });
		//------------------------------------------------------------------------------------
		/*adbShell_BootMsgView = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_BootMsgView = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_BootMsgView.widthHint = 175;
		adbShell_BootMsgView.setLayoutData(gd_adbShell_BootMsgView);
		adbShell_BootMsgView.setText("Booting Message View");
		adbShell_BootMsgView.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_BOOTMSG);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_BOOTMSG);
		      }
		    });*/
		//------------------------------------------------------------------------------------
		adbShell_RunningProcInfo = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_RunningProcInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_RunningProcInfo.widthHint = 175;
		adbShell_RunningProcInfo.setLayoutData(gd_adbShell_RunningProcInfo);
		adbShell_RunningProcInfo.setText("Running Process Info");
		adbShell_RunningProcInfo.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_RUNNIGPROCINFO);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_RUNNIGPROCINFO);
		      }
		    });
		//------------------------------------------------------------------------------------
		Button adbShell_EnvSetupInfo = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_EnvSetupInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_EnvSetupInfo.widthHint = 175;
		adbShell_EnvSetupInfo.setLayoutData(gd_adbShell_EnvSetupInfo);
		adbShell_EnvSetupInfo.setText("System Configuration");
		adbShell_EnvSetupInfo.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_SYSCONFINFO);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_SYSCONFINFO);
		      }
		    });
		//------------------------------------------------------------------------------------
		/*Button adbShell_MemInfoForEachProc = new Button(ShellCommand_composite, SWT.NONE);
		GridData gd_adbShell_MemInfoForEachProc = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_adbShell_MemInfoForEachProc.widthHint = 175;
		adbShell_MemInfoForEachProc.setLayoutData(gd_adbShell_MemInfoForEachProc);
		adbShell_MemInfoForEachProc.setText("Process Memory Info Detail");
		adbShell_MemInfoForEachProc.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_PROCMEMINFODETAIL);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  _actionPerformed(ADBSHELL_PROCMEMINFODETAIL);
		      }
		    });*/
		
		//------------------------------------------------------------------------------------
		infoText = new StyledText(ShellCommand_composite, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		infoText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5, 1));
	    infoText.addLineStyleListener(new LineStyleListener() {
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
	      });
	}
	
	private StyleRange getHighlightStyle(int startOffset, int length) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		//styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
		return styleRange;
	}

	public void _actionPerformed(int action) {		
		if (ADBSHELL_KERVER == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/version~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }
			}
		} else if (ADBSHELL_PROCINFO == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/cpuinfo~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }			
			}
		} else if (ADBSHELL_MEMINFO == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~cat~~/proc/meminfo~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }
			}
		} else if (ADBSHELL_DISKUSAGE == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~df~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }
			}
		} /*else if (ADBSHELL_BOOTMSG == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~dmesg~~>~~" + mCurrentHandlingFilePath;
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 800); }
			}
		}*/ else if (ADBSHELL_RUNNIGPROCINFO == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~ps~~-p~~-t~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }
			}
		} else if (ADBSHELL_SYSCONFINFO == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~set~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 500); }
			}
		} /*else if (ADBSHELL_PROCMEMINFODETAIL == action) {
			String text = "cmd.exe~~/C~~adb~~shell~~procrank~~>~~" + mCurrentHandlingFilePath;						   
			String[] cmdList = text.split("~~");
			
			if (cmdList != null)	{
				try {	mExe.runProcessSimple(cmdList);	}
				catch (IOException e1) {e1.printStackTrace();}
				catch (InterruptedException e1) {e1.printStackTrace();}
				finally { timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 1800); }
			}
		}*/
	}
	
	public static void resultAnalysis()	{
		boolean ret = true;
		//AndroSuker_Analysis.resultAnalysis(AndroSuker_MainCls.getShellInstance(), resultFile, infoText, mCurrentHandlingFilePath, 50);		
		ret = AndroSuker_AnalysisRapid.resultAnalysis(AndroSuker_MainCls.getShellInstance(), resultFile, infoText, mCurrentHandlingFilePath, 50);
		if (ret == false)	{
			AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning!","Adb가 정상적으로 실행되지 않았거나 debugging mode로 연결 되지 않았습니다.", SWT.OK);
			AndroSuker_Debug.adbRestartServer(mExe);
		}
	}
}

/*class LogThread extends Thread {
	StyledText log;
	AndroSuker_Execute exe;
	private static Timer timer = null;
	
	public LogThread(StyledText l, AndroSuker_Execute e)	{
		log = l;
		exe = e;
	}
    public void run() {
    	if (timer == null)	{
    		timer = new Timer();
    		timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 2000, 1000);
    	}	else	{
    		timer.schedule(new DelayAnalysis(null,null,getCurrentClsName(), null), 2000, 1000);
    	}
    }
    public static void TimerStop()	{
    	if (timer != null)	{
	    	timer.cancel();
	    	timer.purge();
	    	timer = null;
    	}
    }
}*/