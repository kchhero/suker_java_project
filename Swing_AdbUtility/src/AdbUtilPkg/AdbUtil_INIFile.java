package AdbUtilPkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class AdbUtil_INIFile {
	public static final String RESULT_FILE_PATH = "monitoring\\result.txt";
	public static final String RESULT_DUMMY_FILE_PATH = "monitoring\\result_dummy.txt";
	public static final String RESULT_KERNELLOG_FILE_PATH = "kernelLog_out\\result_KernelLog.txt";
	public static final String RESULT_KERNELLOG_OLD_FILE_PATH = "kernelLog_out\\result_KernelLog_old.txt";
	public static final String INI_FILE_PATH = "ini\\AdbUtil.ini";
	
	public static String readIniFile(List<String> list, String key)	{
		String temp = null;
		String[] seperate;
		String result = null;
		
		for (int i = 0; i < list.size(); i++)	{
			temp = list.get(i);
			seperate = temp.split("=#");
			
			if (seperate.length > 2)
				return "";
			
			if (seperate[0].toString().equals(key))	{
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
