package SukerEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SukerEditor_FileControl {
	public static String getOnlyFileName(String fullPath)	{
		String fileName = null;		
		fileName = new File(fullPath).getName();
		
		return fileName;
	}
	public String getOnlyFolderName(String fullPath)	{
		String pathName = null;		
		pathName = new File(fullPath).getParent();
		
		return pathName;
	}
	public String readFileByBurst(String filepath) throws Exception	{
		int fileSize = 0;
		String retStr = "";
		
		if (filepath == null || filepath.length() < 1)
			return null;
		
		fileSize = (int)new File(filepath).length();		
		if (fileSize < 1)
			return null;
		
		StringBuilder	result = new StringBuilder(fileSize + 32);
		char[] c = new char[fileSize];
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
		bufferedReader.read(c);
		
		result.append(c);
		bufferedReader.close();
		
		retStr = result.toString();
		
		return retStr;
	}
	public List<String> readFileByLine(String filepath) throws Exception
	{
		String line = null;
		List<String> records = new ArrayList<String>();
		
		// wrap a BufferedReader around FileReader
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
		
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
	public void writeFile(List<String> val, String filepath) throws IOException
	{
		BufferedWriter brOut = null;
		try {
			brOut = new BufferedWriter(new FileWriter(filepath));
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
	
	public void findKeyWordInTheFile(boolean[] lineNumberList, String[] src, List<String> srcList) throws Exception
	{
		boolean[]	indexArray = lineNumberList;
		int		i,j = 0;
		
		for (i = 0; i < srcList.size(); i++)	{
			indexArray[i] = false;
		}
		for (i = 0; i < src.length; i++)
		{
			for (j = 0; j < srcList.size(); j++)	{
				if (indexArray[j] == false)	{
					if (srcList.get(j).contains(src[i]))	{				
						indexArray[j] = true;
					}
				}
			}
		}		
	}
	
	public void saveFileForText(Shell s, String fullPath, String text)	{
		File file = new File(fullPath);
        try {
          FileWriter fileWriter = new FileWriter(file);
          fileWriter.write(text);
          fileWriter.close();
        } catch (IOException e) {
          MessageBox messageBox = new MessageBox(s, SWT.ICON_ERROR | SWT.OK);
          messageBox.setMessage("File I/O Error.");
          messageBox.setText("Error");
          messageBox.open();
          return;
        }
	}
}

/*
String argPattern = args[0];

final String pattern = argPattern.replace(".","\\.").replace("*",".*");
System.out.println("transformed pattern = " + pattern );
for( File f : new File(".").listFiles( new FilenameFilter(){
                   public boolean accept( File dir, String name ) { 
                       return name.matches( pattern );
                   }
                })){
     System.out.println( f.getName() );
}
*/