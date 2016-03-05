package sukerPkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.swt.SWT;

public class AndroSuker_INIFile {
	public static final String RESULT_FILE_PATH_PROF = "monitoring\\result_Profiling.txt";
	public static final String RESULT_FILE_PATH_PROCSTAT = "monitoring\\result_CoreUsage.txt";
	public static final String RESULT_FILE_PATH_PROCSTAT2 = "monitoring\\result_CoreClock.txt";
	public static final String RESULT_FILE_PATH_PROCSTAT3 = "monitoring\\result_CoreThermal.txt";
	public static final String RESULT_FILE_PATH_DEVICE = "monitoring\\result_relatedDevice.txt";
	public static final String RESULT_FILE_PATH_SHELLCMD = "monitoring\\result_ShellCommand.txt";
	public static final String RESULT_DUMMY_FILE_PATH = "monitoring\\result_dummy.txt";
	public static final String INI_FILE_PATH = "ini\\AndroSuker.ini";

	public static String readIniFile(List<String> list, String key)	{
		String temp = "";
		String[] seperate;
		String result = "";
		
		for (int i = 0; i < list.size(); i++)	{
			temp = list.get(i);
			seperate = temp.split("=#");
			
			if (seperate.length > 2)
				return "";
			
			if (seperate[0].toString().equals(key))	{
				 if (seperate.length == 1)
					 return "";
				 else
					 result = seperate[1].toString();
				 break;
			}	
		}
		return result;
	}
		
	public static void writeIniFile(String key, String value, List<String> writeList)	{
		writeList.add(key + "=#" + value);		
	}
		
	public static void deleteFile(String targetFilePath)	{
		File targetPath = new File(targetFilePath);
		if (!targetPath.exists())
			return ;
		
		targetPath.delete();
	}
	
	public static void copyFile(String srcFilePath, String destFilePath) throws IOException	{
		FileReader fileSrc = null;
	    FileWriter fileDest = null;

	    try {
	    	fileSrc = new FileReader(srcFilePath);
	    	fileDest = new FileWriter(destFilePath);

	    	int c;
	    	while ((c = fileSrc.read()) != -1) {
	    		fileDest.write(c);
	    	}
	    } finally {
	    	if (fileSrc != null) {
	    		fileSrc.close();
	    	}
	    	if (fileDest != null) {
	    		fileDest.close();
	    	}
	    }
	}
	
	
	//never using
	public static void __OLD__copyFile(String srcFilePath, String destFilePath) throws IOException	{
		File fileSrc = new File(srcFilePath);
		File fileDest = new File(destFilePath);
		InputStream in = null;
		OutputStream out = null;
		
		try	{
			if (!fileSrc.exists())	{
				return ; //no exist srcFile
			}
			if (fileDest.exists())	{
				fileDest.delete();	//있으면 일단 지우고..
				if (!fileDest.createNewFile())	{
					AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "Destination File Create Fail!!", SWT.OK);
					return ;
				}
			}
			
			in = new FileInputStream(srcFilePath);
			out = new FileOutputStream(destFilePath);
			int byteread = in.available();
			byte[] buffer = new byte[byteread];
			//int bytedata = in.read(buffer);
			out.write(buffer);
		} catch(IOException ioe)	{			
		} finally	{
			try	{
				if(in != null)	{
					in.close();
				}
				if(out != null)	{
					out.flush();
					out.close();
				}
			} catch(IOException ioe)	{
			}
		}
		return ;
	}
}
