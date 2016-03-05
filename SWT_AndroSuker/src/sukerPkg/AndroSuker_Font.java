package sukerPkg;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.graphics.Font;

public class AndroSuker_Font {
	public Font		mFont_Courier_Normal;
	public Font		mFont_Arial_Normal;
	public Font		mFont_Tahoma_Normal;
	public Font		mFont_MSMincho_Normal;
	public Font		mFont_ArabicT_Normal;
	
	public Font		mFont_Courier_Italic;
	public Font		mFont_Arial_Italic;
	public Font		mFont_Tahoma_Italic;
	public Font		mFont_MSMincho_Italic;
	public Font		mFont_ArabicT_Italic;

	public Font		mFont_Courier_Bold;
	public Font		mFont_Arial_Bold;
	public Font		mFont_Tahoma_Bold;
	public Font		mFont_MSMincho_Bold;
	public Font		mFont_ArabicT_Bold;
	
	AndroSuker_Font(Display display)	{
		mFont_Courier_Normal = new Font(display, "Courier New", 9, SWT.NORMAL);
		mFont_Arial_Normal = new Font(display, "Arial", 9, SWT.NORMAL);
		mFont_Tahoma_Normal = new Font(display, "Tahoma", 9, SWT.NORMAL);
		mFont_MSMincho_Normal = new Font(display, "MS Mincho", 9, SWT.NORMAL);
		mFont_ArabicT_Normal = new Font(display, "Arabic Transparent", 9, SWT.NORMAL);
		
		mFont_Courier_Italic = new Font(display, "Courier New", 9, SWT.ITALIC);
		mFont_Arial_Italic = new Font(display, "Arial", 9, SWT.ITALIC);
		mFont_Tahoma_Italic = new Font(display, "Tahoma", 9, SWT.ITALIC);
		mFont_MSMincho_Italic = new Font(display, "MS Mincho", 9, SWT.ITALIC);
		mFont_ArabicT_Italic = new Font(display, "Arabic Transparent", 9, SWT.ITALIC);
		
		mFont_Courier_Bold = new Font(display, "Courier New", 9, SWT.BOLD);
		mFont_Arial_Bold = new Font(display, "Arial", 9, SWT.BOLD);
		mFont_Tahoma_Bold = new Font(display, "Tahoma", 9, SWT.BOLD);
		mFont_MSMincho_Bold = new Font(display, "MS Mincho", 9, SWT.BOLD);
		mFont_ArabicT_Bold = new Font(display, "Arabic Transparent", 9, SWT.BOLD);
	}
}
