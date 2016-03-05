package layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class LayoutColor {
	static Display mainDisplay;
	public LayoutColor(final Display display)	{
		mainDisplay = display;
	}
	
	public static Color getColor(String colorName)	{		
		if (colorName.equals("red"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_RED);
		} else if (colorName.equals("blue"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_BLUE);
		} else if (colorName.equals("gray"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_GRAY);
		} else if (colorName.equals("yellow"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_YELLOW);
		} else if (colorName.equals("green"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_GREEN);
		} else if (colorName.equals("white"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_WHITE);
		} else if (colorName.equals("black"))	{
			return mainDisplay.getSystemColor(SWT.COLOR_BLACK);
		}
		return null;
	}
	
	public static Color getRGBColor(int R, int G, int B){
		Color color = new Color(mainDisplay, R, G, B);
		return color;		
	}
}
