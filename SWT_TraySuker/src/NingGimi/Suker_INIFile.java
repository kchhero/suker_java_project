package NingGimi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Combo;

public class Suker_INIFile {
	private final static int		FILE_PATH_LENGTH = 256;
	public static final String 		INI_FILE_PATH = "ini\\config.ini";
	public static final String 		TABLE_FILE_PATH = "ini\\table.cfg";
	
	public static List<String> readFile(String filename) throws Exception
	{
		String line = null;
		List<String> records = new ArrayList<String>();
				
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
				
		while ((line = bufferedReader.readLine()) != null)
		{
			records.add(line);
		}

		bufferedReader.close();
		return records;
	}
	
	public static String readIniFile(List<String> list, String key)	{
		String temp = null;
		String[] seperate;
		String result = "";
		int	listSize = list.size();
		
		for (int i = 0; i < listSize; i++)	{
			temp = list.get(i);
			seperate = temp.split("=#");
			
			if (seperate.length != 2)
				continue;
			
			if (seperate[0].toString().equals(key))	{
				result = seperate[1].toString();
				break;
			}	
		}
		return result;
	}
	public static void writeFile(List<String> val, String filename) throws IOException
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
	public static void writeIniFile(String key, String value, List<String> writeList)	{
		writeList.add(key + "=#" + value);		
	}
	
	public static String makeComboList(Combo combo)	{
		StringBuilder	resultText = null;
		String			result = null;
				
		Combo value = combo;
		int	itemsCnt = value.getItemCount();
		int i = 0;	        

		resultText = null;
		if (itemsCnt != 0)	{
			resultText = new StringBuilder(itemsCnt * FILE_PATH_LENGTH);
			for (i = 0; i < itemsCnt-1; i++)	{
				if (value.getItems()[i].toString().length() > 0)	{
					resultText.append(value.getItems()[i].toString());					
					resultText.append(",");
				}					
			}
			if (value.getItems()[i].toString().length() > 0)	{
				resultText.append(value.getItems()[i].toString());
			}
		}
		if (resultText != null)
			result = resultText.toString();
		else
			result = "";

		return result;
	}
	
	public static List<String> makeComboList(List<Combo> comboList)	{
		StringBuilder	resultText = null;
		List<String>	result = new ArrayList<String>();
		int j = 0;
		
		for(Iterator<Combo> it = comboList.iterator() ; it.hasNext() ; )
		{
		        Combo value = it.next();
		        int	itemsCnt = value.getItemCount();
		        int i = 0;	        
		        
		        resultText = null;
		        if (itemsCnt != 0)	{
			        resultText = new StringBuilder(itemsCnt * FILE_PATH_LENGTH);
					for (i = 0; i < itemsCnt-1; i++)	{
						if (value.getItems()[i].toString().length() > 0)	{
							resultText.append(value.getItems()[i].toString());					
							resultText.append(",");
						}					
					}
					if (value.getItems()[i].toString().length() > 0)	{
						resultText.append(value.getItems()[i].toString());
					}
		        }
		        if (resultText != null)
					result.add(j++, resultText.toString());
		        else
		        	result.add(j++, "");
		}
		
		
		return result;
	}
}
