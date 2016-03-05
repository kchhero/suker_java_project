package AdbUtilPkg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AdbUtil_Execute {
	Process process = null;
	String mResult;	
	StreamGobbler gb1 = null;
	StreamGobbler gb2 = null;
	String[] cmds_org;
	protected static boolean	mIsFailed = false;
	long	oldTime = 0;
    
	public void killProcess()	{
		if (process != null)	{
			process.destroy();
			process = null;
		}
	}
	public void runProcessSimple(String[] cmds) throws IOException, InterruptedException
	{
		killProcess();
		cmds_org = cmds;
		process = Runtime.getRuntime().exec(cmds);		
	}
	public void runProcess(String[] cmds, String clsName) throws IOException, InterruptedException
	{
		if (process != null)	{
			process.destroy();
			process = null;
			mIsFailed = false;
		}
		
		try {
			mResult = "";
			process = Runtime.getRuntime().exec(cmds);
							
			gb1 = new StreamGobbler(process.getInputStream());
			gb2 = new StreamGobbler(process.getErrorStream());
			
			gb1.start();
			gb2.start();
			oldTime = System.currentTimeMillis();
			
			while (true) {				
				if ((!gb1.isAlive() && !gb2.isAlive()) || mIsFailed == true) {  //두개의 스레드가 정지할면 프로세스 종료때까지 기다린다.
					System.out.println("Thread gb1 Status : "+gb1.getState());
					System.out.println("Thread gb2 Status : "+gb2.getState());				
					process.waitFor();
					mIsFailed = false;
					break;
				}	else	{
					if ((System.currentTimeMillis() - oldTime) > 8000)	{
						mIsFailed = true;
						mResult = "Please devices connection...";
						callbackProc(clsName);
						return ;
					}
					//System.out.println("Time gap : "+(System.currentTimeMillis() - oldTime));
					if (callbackProc(clsName) == false)
						return ;
				}
			}
		}catch(Exception e){
			;//getLogger().info(e.toString());
		}finally{
			if(process != null)	{				
				System.out.println("process.destroy()");
				//progressThread.progressDone();
				process.destroy();
				mIsFailed = false;
				
				if (gb1 != null)	{
					mResult += gb1.getResultRunningLog();
					if (mResult.length() < 1)
						mResult = "No exist returned message or devices connection fail...";					
				}
				
				callbackProc(clsName);
				
				gb1.init();
				gb2.init();
			}
		}
	}
	
	public void runProcessRealTime(String[] cmds, String clsName) throws IOException, InterruptedException
	{
		String result = null;
		
		killProcess();
		
		try {
			process = Runtime.getRuntime().exec(cmds);
							
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
						
			while ((result = in.readLine()) != null)	{
				mResult = result;
				if (AdbUtil_Main_Layout.getCurrentTabName().equals("AdbUtil_ShellCommands"))	{
					;//AdbUtil_ShellCommands.callbackDoneExecuteProcess();
				}				
			}				
		}catch(Exception e){
			e.printStackTrace();		
		}
	}
	
	private boolean callbackProc(String clsName)	{
		if (clsName.equals("AdbUtilPkg.AdbUtil_ApkTools"))
			AdbUtil_ApkTools.callbackDoneExecuteProcess();		
		else if (clsName.equals("AdbUtilPkg.AdbUtil_ANR"))
			AdbUtil_ANR.callbackDoneExecuteProcess();
		else if (clsName.equals("AdbUtilPkg.AdbUtil_FirmwareControl"))
			AdbUtil_FirmwareControl.callbackDoneExecuteProcess();
		else
			return false;
		
		return true;
	}
	public String getResultMessages()	{
		return mResult;
	}
}

class StreamGobbler extends Thread
{
	InputStream is;
	String temp = "";
		
	public StreamGobbler(InputStream is) {
		this.is = is;
	}
	public void run()
    {
        try {
        	InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            while (true)	{
            	if ((line = br.readLine()) != null)	{
		            temp += line.trim();
		            if (line.length() > 0)
		            	temp += "\n";
	                //System.out.println(line);
            	} else	{
            		if (AdbUtil_Main_Layout.getCurrentTabName().equals("AdbUtil_ApkTools"))	{
            			AdbUtil_ApkTools.callbackFailExecuteProcess();
            		} else if (AdbUtil_Main_Layout.getCurrentTabName().equals("AdbUtil_FirmwareControl"))	{
            			AdbUtil_FirmwareControl.callbackFailExecuteProcess();
            		}            		
            		AdbUtil_Execute.mIsFailed = true;
            		return ;            		
            	}
            }
        } catch (IOException isr) {
          System.out.println(isr);
        }        
    }
	public String getResultRunningLog()	{
		return this.temp;
	}
	public void init()	{
		is = null;
		temp = "";
	}
}

class FileHandler	{
	public List<String> readFile(String filename) throws Exception
	{
		String line = null;
		List<String> records = new ArrayList<String>();
		
		// wrap a BufferedReader around FileReader
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
		
		//use the readLine method of the BufferedReader to read one line at a time.
		//the readLine method returns null when there is nothing else to read.
		while ((line = bufferedReader.readLine()) != null)
		{
			records.add(line);
		}
		  
		// close the BufferedReader when we're done
		bufferedReader.close();
		return records;
	}
	
	//public void writeFile(String tag, String val, String filename)
	public void writeFile(List<String> val, String filename) throws IOException
	{
		BufferedWriter brOut = null;
		try {
			brOut = new BufferedWriter(new FileWriter(filename));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
		for (int i = 0; i < val.size(); i++)	{
			String wr = val.get(i);
		    try {
		    	brOut.write(wr); brOut.newLine();
		    } catch (IOException e) {
		    	System.out.println("fuck error");
		    }
		}
		brOut.close();
	}
	
	public boolean isExistFile(String filename)
	{
		File f = new File(filename);
		return f.exists();
	}
}