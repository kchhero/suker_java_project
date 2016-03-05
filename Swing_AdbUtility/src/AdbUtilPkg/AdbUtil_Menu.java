package AdbUtilPkg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class AdbUtil_Menu extends JFrame implements ActionListener {
	JMenuBar  bar;
	JMenu   file, edit, help;
	JMenuItem  fileNew, fileOpen, fileExit, About;

	public void makeMenu()
	{
		bar = new JMenuBar();
		
		file = new JMenu("File");
		file.setMnemonic('F');
  
//			fileNew = new JMenuItem("New");
//			fileNew.addActionListener(this);
//			file.add(fileNew);
//  
//			fileOpen = new JMenuItem("Open");
//			fileOpen.addActionListener(this);
//			file.add(fileOpen);
  
//			file.addSeparator();
  
		fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(this);
		file.add(fileExit);
  
		help = new JMenu("Help");
		help.setMnemonic('H');
  
		About = new JMenuItem("About");
		About.addActionListener(this);
		help.add(About);
		
		bar.add(file);
//			bar.add(edit);
		bar.add(help);
		
		setJMenuBar(bar);
	}
	
	public JMenuBar getJMenuBarInstance()	{
		if (bar != null)	{
			return bar;
		}	else {
			return null;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object o = ae.getSource();
	//	if(o == fileNew)
	//	{  
	//		;//System.out.println("New");
	//	} 
	//	if(o == fileOpen)
	//	{
	//		System.out.println("Open");
	//	} 
		if(o == fileExit)
		{
//				mExecute.process.destroy();
			System.exit(0);
		} 
		if(o == About)
		{
			JOptionPane.showMessageDialog(this,
										  "Adb command를 쉽고 간편하게 사용하기 위해 만든 툴입니다.\n문제 발생시 choonghyun.jeon@lge.com으로 메일 주세요",
										  "AdbUtility About",
										  JOptionPane.INFORMATION_MESSAGE);
		}
	}
}