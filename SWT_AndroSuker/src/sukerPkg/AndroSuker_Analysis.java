package sukerPkg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

class AndroSuker_AnalysisRapid implements AndroSuker_Definition	{
	static StringBuilder resultText;
	static Shell thisShell;
	static long	delayed;
	
	public static boolean resultAnalysis(final Shell shell, FileHandler resultFile, final StyledText component, final String f, final long d)	{
		thisShell = shell;
		List<String> resultList = null;
		delayed = d;
		boolean ret = false;
		
		if (resultFile != null)	{
			String filePath = f;
			
			try {
				resultList = resultFile.readFile(filePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ret = false;
			}
	
			if (resultList != null){
				int resultSize = resultList.size();
				
				if (resultSize > 0)	{
					resultText = new StringBuilder(resultSize * TEXT_LINE_WIDTH);
					for (int i = 0; i < resultSize; i++)	{
						resultText.append(resultList.get(i));
						if (resultList.get(i).length() >= 0)	{
							if (i < resultSize-1)	{
								if (resultList.get(i+1).length() == 0)	{
									resultText.append("\n");
								}
							}
						}						
					}
					if (resultText.length() > 1)	{
				        AsyncTaskRun.asyncTextOutProcOneShotRapid(thisShell, component, resultText, delayed);
				        //timer.scheduleAtFixedRate(asyncLogTextTimer, 10, null);
					}
					ret = true;
				} else {
					ret = false;
				}
			}
			return ret;
		}
		else	{
			String text = f;
			if (text.length() > 1)	{	
		        AsyncTaskRun.asyncTextOutProcOneShot(thisShell, component, text, delayed);
		        //timer.scheduleAtFixedRate(asyncLogTextTimer, 10, null);
			}
		}
		return ret;
	}
}

public class AndroSuker_Analysis {
	static String resultText;
	static Shell thisShell;
	static long	delayed;
	
	public static void resultAnalysis(final Shell shell, FileHandler resultFile, final StyledText component, final String f, final long d)	{
		thisShell = shell;
		List<String> resultList = null;
		delayed = d;
		
		if (resultFile != null)	{
			String filePath = f;
			
			try {
				resultList = resultFile.readFile(filePath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			if (resultList != null){
				resultText = "";
				for (int i = 0; i < resultList.size(); i++)	{
					resultText += resultList.get(i);
					if (resultList.get(i).length() > 0)
						resultText += "\n";
				}
				if (resultText.length() > 1)	{
			        AsyncTaskRun.asyncTextOutProcOneShot(thisShell, component, resultText, delayed);
			        //timer.scheduleAtFixedRate(asyncLogTextTimer, 10, null);
				}
			}
		}
		else	{
			String text = f;
			if (text.length() > 1)	{	
		        AsyncTaskRun.asyncTextOutProcOneShot(thisShell, component, text, delayed);
		        //timer.scheduleAtFixedRate(asyncLogTextTimer, 10, null);
			}
		}
	}
	public static int resultAnalysisRealTime(int nLastReadLine, FileHandler resultFile, Text component, String filePath)	{
		List<String> 	resultList = null;
		String 			resultText;
		int				readlinenum = nLastReadLine;
		int				i;
		
		try {
			resultList = resultFile.readFile(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (resultList != null){
			resultText = "";
			
			if (nLastReadLine > 0)	{
				for (i = readlinenum; i < resultList.size(); i++)	{				
					resultText = resultList.get(i);					
					component.append(resultText);					
					if (resultList.get(i).length() > 0)	{						
						component.append("\n");
						component.append(getCurrentLogTime());	
						component.append("  ");
					}
				}
				component.redraw();//.repaint();
			}	else	{
				component.setText("");
				for (i = 0; i < resultList.size(); i++)	{				
					resultText = resultList.get(i);
					component.append(resultText);					
					if (resultList.get(i).length() > 0)
						component.append("\n");
				}				
				component.redraw();//repaint();
			}
			if (readlinenum < resultList.size())
				readlinenum = i;
			else
				;//System.out.print("error!"); //error or pending
			
			component.setFocus();//.setFocusable(true);
		}
		return readlinenum;
	}
	private static String getCurrentLogTime()	{
		String timeStr;
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		timeStr = format.format(now);
				 
		return timeStr;
	}
}

class DelayAnalysis extends TimerTask implements AndroSuker_Definition
{
	String[] CmdsList;
	AndroSuker_Execute mDelayExe;
	String mClassName;
	Text mTrace = null;
	
	public DelayAnalysis(String[] cmds, AndroSuker_Execute mE, String clsName, Text trace){
		CmdsList = cmds;
		mDelayExe = mE;
		mClassName = clsName;
		mTrace = trace;
	}
    public void run()
    {
    	if (CmdsList == null)	{
    		if (mClassName.equals("sukerPkg.AndroSuker_TabItem_ShellCommands"))
    			AndroSuker_TabItem_ShellCommands.resultAnalysis();
    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_Profiling"))
    			AndroSuker_TabItem_Profiling.resultAnalysis(false);
    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_Profiling2"))
    			AndroSuker_TabItem_Profiling.resultAnalysis(true);
    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_Profiling3"))
    			AndroSuker_TabItem_Profiling.resultAnalysis(true);
    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_AdbCommands"))	{
    			AndroSuker_TabItem_AdbCommands.resultAnalysis();
    			//AndroSuker_TabItem_AdbCommands.finallyAdbKill();
    		}
    		else	{
    			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
	    			if (mTrace != null)
	    				mTrace.setText("CmdsList == null");
    			}
    		}
    	}
    	else	{
    		try {
    			mDelayExe.runProcessSimple(CmdsList);
    			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
	    			if (mTrace != null)
	    				mTrace.setText("going...");
    			}
			} catch (IOException e) {
				e.printStackTrace();
				if (AndroSuker_Debug.DEBUG_MODE_ON)	{
					if (mTrace != null)
						mTrace.setText(e.getMessage());
				}
			}  catch (InterruptedException e) {
				e.printStackTrace();
				if (AndroSuker_Debug.DEBUG_MODE_ON)	{
					if (mTrace != null)
						mTrace.setText(e.getMessage());
				}
			}
			finally {
				if (mClassName.equals("sukerPkg.AndroSuker_TabItem_ShellCommands"))
					AndroSuker_TabItem_ShellCommands.resultAnalysis();
	    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_Profiling"))
	    			AndroSuker_TabItem_Profiling.resultAnalysis(false);
	    		else if (mClassName.equals("sukerPkg.AndroSuker_TabItem_Profiling2"))
	    			AndroSuker_TabItem_Profiling.resultAnalysis(true);
	    		else	{
	    			if (AndroSuker_Debug.DEBUG_MODE_ON)	{
		    			if (mTrace != null)
		    				mTrace.setText("going...2");
	    			}
	    		}
			}
    	}    
    }    
}

class AsyncTaskRun	{
	final static Timer timer = new Timer();
	static Shell			thisShell;
	static String 			resultText;
	static StringBuilder 	resultTextRapid;
	static StyledText		styledText;
	static long				delayed;
	static long				period;
	static TimerTask 		asyncLogTextTimer = null;
	
	public static void asyncTextOutProcOneShot(Shell shell, StyledText comp, String text, long d)	{
		thisShell = shell;
		resultText = text;
		styledText = comp;
		delayed = d;
		
		if (resultTextRapid != null && resultTextRapid.length() > 0)
			resultTextRapid.delete(0, resultTextRapid.length());
		
		asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {
		            	if (resultText.length() > 1)	{
		                	if (styledText != null && styledText != null && !styledText.isDisposed())	{
		                		styledText.setText(resultText);
		                	}
		            	}
		            }
		        });
		    }
		};
		timer.schedule(asyncLogTextTimer, delayed);
	}

	public void asyncTextOutProcRepeat(Shell shell, StyledText comp, String text, long d, long p)	{
		thisShell = shell;
		resultText = text;
		styledText = comp;
		delayed = d;
		period = p;
		
		if (resultTextRapid != null && resultTextRapid.length() > 0)
			resultTextRapid.delete(0, resultTextRapid.length());
		
		asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {
		            	if (resultText.length() > 1)	{
		                	if (styledText != null && styledText != null && !styledText.isDisposed())	{
		                		styledText.setText(resultText);
		                	}
		            	}
		            }
		        });
		    }
		};
		timer.scheduleAtFixedRate(asyncLogTextTimer, delayed, period);
	}
	
	public static void asyncTextOutProcOneShotRapid(Shell shell, StyledText comp, StringBuilder text, long d)	{
		thisShell = shell;
		resultText = "";
		styledText = comp;
		delayed = d;
		resultTextRapid = text;
				
		asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {
		            	if (resultTextRapid.length() > 1)	{
		                	if (styledText != null && styledText != null && !styledText.isDisposed())	{
		                		styledText.setText(resultTextRapid.toString());
		                	}
		            	}
		            }
		        });
		    }
		};
		timer.schedule(asyncLogTextTimer, delayed);
	}
}