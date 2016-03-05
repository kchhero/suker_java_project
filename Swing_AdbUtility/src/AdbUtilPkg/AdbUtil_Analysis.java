package AdbUtilPkg;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AdbUtil_Analysis {
	public static void resultAnalysis(FileHandler resultFile, JTextArea component, String filePath)	{
		List<String> resultList = null;
		String resultText;
		
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
				component.setText(resultText);
				component.setFocusable(true);
			}
		}		
	}
	public static int resultAnalysisRealTime(int nLastReadLine, FileHandler resultFile, JTextArea component, String filePath)	{
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
				component.repaint();
			}	else	{
				component.setText("");
				for (i = 0; i < resultList.size(); i++)	{				
					resultText = resultList.get(i);
					component.append(resultText);					
					if (resultList.get(i).length() > 0)
						component.append("\n");
				}				
				component.repaint();
			}
			if (readlinenum < resultList.size())
				readlinenum = i;
			else
				;//System.out.print("error!"); //error or pending
			
			component.setFocusable(true);
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

class DelayAnalysis extends TimerTask
{
	String[] CmdsList;
	AdbUtil_Execute mDelayExe;
	String mClassName;
	JTextField mTrace = null;
	
	public DelayAnalysis(String[] cmds, AdbUtil_Execute mE, String clsName, JTextField trace){
		CmdsList = cmds;
		mDelayExe = mE;
		mClassName = clsName;
		mTrace = trace;
	}
    public void run()
    {
    	if (CmdsList == null)	{
    		if (mClassName.equals("AdbUtil_ShellCommands"))
    			AdbUtil_ShellCommands.resultAnalysis();
    		else if (mClassName.equals("AdbUtil_Profiling"))
    			AdbUtil_Profiling.resultAnalysis();
    		else if (mClassName.equals("AdbUtil_ANR"))
    			AdbUtil_ANR.resultAnalysis(); 		
    		else	{
    			if (AdbUtil_Main_Layout.DEBUG_MODE_ON == true)	{
	    			if (mTrace != null)
	    				mTrace.setText("CmdsList == null");
    			}
    		}
    	}
    	else	{
    		try {
    			mDelayExe.runProcessSimple(CmdsList);
    			if (AdbUtil_Main_Layout.DEBUG_MODE_ON == true)	{
	    			if (mTrace != null)
	    				mTrace.setText("going...");
    			}
			} catch (IOException e) {
				e.printStackTrace();
				if (AdbUtil_Main_Layout.DEBUG_MODE_ON == true)	{
					if (mTrace != null)
						mTrace.setText(e.getMessage());
				}
			}  catch (InterruptedException e) {
				e.printStackTrace();
				if (AdbUtil_Main_Layout.DEBUG_MODE_ON == true)	{
					if (mTrace != null)
						mTrace.setText(e.getMessage());
				}
			}
			finally {
				if (mClassName.equals("AdbUtil_ShellCommands"))
	    			AdbUtil_ShellCommands.resultAnalysis();
	    		else if (mClassName.equals("AdbUtil_Profiling"))
	    			AdbUtil_Profiling.resultAnalysis();
	    		else if (mClassName.equals("AdbUtil_ANR"))
	    			AdbUtil_ANR.resultAnalysis();	    		
	    		else	{
	    			if (AdbUtil_Main_Layout.DEBUG_MODE_ON == true)	{
		    			if (mTrace != null)
		    				mTrace.setText("going...2");
	    			}
	    		}
			}
    	}    
    }    
}
