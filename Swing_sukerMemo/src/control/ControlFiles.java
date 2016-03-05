package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.channels.FileChannel;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

public class ControlFiles {
	public static void saveFile(String fileName, String content)
	{
		if (fileName == null || fileName.length() == 0)
			return ;
		
		File file = new File(fileName);
		if (!file.exists()){
			;//error
		}
		
		try {
			//FileWriter(File file, boolean append) throws IOException
			FileWriter shfileWriter = new FileWriter(file, false);
			shfileWriter.write(content.toCharArray());
			shfileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String openFile(String fileName)
	{
		final String textString;
		if (fileName == null || fileName.length() == 0)
			return "";

		File file = new File(fileName);

		if (!file.exists()){
			;//error
		}

		try	{
			FileInputStream stream = new FileInputStream(file.getPath());
			try	{
				Reader in = new BufferedReader(new InputStreamReader(stream));
				char[] readBuffer = new char[2048];
				StringBuffer buffer = new StringBuffer((int) file.length());

				int n;

				while ((n = in.read(readBuffer)) > 0)	{
					buffer.append(readBuffer, 0, n);
				}
				textString = buffer.toString();
				stream.close();
			}	catch (IOException e)	{
				return "";
			}			
		} catch (IOException e)	{
			return "";
		}
		return textString;
	}
	
	public static Boolean copyFile(String src, String dest)	{
		File sourceFile = new File( src );

		//스트림, 채널 선언
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;

		try {
			//스트림 생성
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(dest);
			//채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();

			//채널을 통한 스트림 전송
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			//자원 해제
			try{
				fcout.close();
			}catch(IOException ioe){}
			try{
				fcin.close();
			}catch(IOException ioe){}
			try{
				outputStream.close();
			}catch(IOException ioe){}
			try{
				inputStream.close();
			}catch(IOException ioe){}
		}
		return true;
	}

	public static Boolean deleteFile(String path)	{
		File f = new File(path);
	    if (f.delete())	{
	    	return true;
	    }
	    return false;
	}
	
	public static String[] getFileNameListInDir(String path)	{
		File dir = new File(path);

	    String[] children = dir.list();
	    if (children == null) {
	      System.out.println("does not exist or is not a directory");
	    } else {
	      for (int i = 0; i < children.length; i++) {
	        String filename = children[i];
	        System.out.println(filename);
	      }
	    }
	    return children;
	}
	
	public static String dirOpen(final Shell shell)	{
		DirectoryDialog dlg = new DirectoryDialog(shell);

        // Set the initial filter path according
        // to anything they've selected or typed in
        //dlg.setFilterPath(text.getText());

        // Change the title bar text
        dlg.setText("Select Directory Dialog");

        // Customizable message displayed in the dialog
        dlg.setMessage("Select a directory");

        // Calling open() will open and run the dialog.
        // It will return the selected directory, or
        // null if user cancels
        String dir = dlg.open();
        if (dir != null) {
          // Set the text box to the new selection
          return dir;
        }
        
        return null;
	}
}
