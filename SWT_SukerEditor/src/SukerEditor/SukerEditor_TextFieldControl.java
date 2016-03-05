package SukerEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Shell;

public class SukerEditor_TextFieldControl {
	private Shell				currentShell;
	private StyledText			sText;
	private String[]			keywords = {"","","",""};	
	private Boolean				isOpendSearchBox;
	
	private List<String> 		undoStack;
	private List<String> 		redoStack;
	private static String		searchKeyword;
	private boolean 			ignoreUndo = false;
	private final int			MAX_STACK_SIZE = 200;
	private int					hotKey;
	
	SukerEditor_TextFieldControl(Shell shell, StyledText t)	{
		currentShell = shell;
		sText = t;
		isOpendSearchBox = false;
		undoStack = new LinkedList<String>();
		redoStack = new LinkedList<String>();
		
		sText.setKeyBinding('k' | SWT.MOD1, ST.DELETE_NEXT);
	    sText.setKeyBinding('4' | SWT.MOD1 | SWT.MOD2, ST.LINE_END);
		sText.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {	
				if ((e.stateMask & SWT.CTRL) == SWT.CTRL)	{
					hotKey = SWT.CTRL;
					System.out.println("press ctrl key");
				} else if ((e.stateMask & SWT.ALT) == SWT.ALT)	{
					hotKey = SWT.ALT;
					System.out.println("press ctrl key");
				}
				if((hotKey == SWT.CTRL) && (e.keyCode == 'f'))	//Ctrl+F 구현
                {
					if (isOpendSearchBox == false)	{
						isOpendSearchBox = true;
						SukerEditor_TextFieldSearchBox searchBox = new SukerEditor_TextFieldSearchBox(currentShell, 0);
						searchBox.open();
						isOpendSearchBox = false;
					}
					hotKey = 0;
					System.out.println("find : " + e.keyCode);
                }
				else if ((hotKey == SWT.CTRL) && (e.keyCode == 'a'))	{
					sText.selectAll();
					hotKey = 0;
					moveCursorToEnd();
					System.out.println("selectAll : " + e.keyCode);
				}
				else if ((hotKey == SWT.CTRL) && (e.keyCode == 'z'))	{
					undo();
					hotKey = 0;
					System.out.println("undo : " + e.keyCode);
				}
				else if ((hotKey == SWT.ALT) && (e.keyCode == 'z'))	{
					redo();
					hotKey = 0;
					System.out.println("redo : " + e.keyCode);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if((hotKey == SWT.CTRL) && (e.keyCode == 'c'))	{
					sText.copy();
					hotKey = 0;
					System.out.println("copy : " + e.keyCode);
				}
				else if ((hotKey == SWT.CTRL) && (e.keyCode == 'v'))	{
					sText.paste();
					hotKey = 0;
					System.out.println("paste : " + e.keyCode);
				}
			}			
		});
		sText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent event) {
				if(keywords == null || keywords.length == 0 || (keywords[0].length() < 2 && keywords[1].length() < 2 && keywords[2].length() < 2 && keywords[3].length() < 2)) {
					event.styles = new StyleRange[0];
					return;
				}

				String line = event.lineText;
				int cursor = -1;

				LinkedList<StyleRange> list = new LinkedList<StyleRange>();
				for (int i = 0; i < keywords.length; i++)	{
					if (keywords[i] != null && keywords[i].length() > 1)	{
						while( (cursor = line.indexOf(keywords[i], cursor+1)) >= 0) {
							list.add(getHighlightStyle(event.lineOffset+cursor, keywords[i].length(), currentShell, i));
						}
		
						event.styles = (StyleRange[]) list.toArray(new StyleRange[list.size()]);
					}
				}
			}
		});
		
		
		sText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				// Update the status bar
				;//nothing
/*				updateStatus();

				// Update the comments
				if (lineStyleListener != null) {
					lineStyleListener.refreshMultilineComments(st.getText());
					st.redraw();
				}
*/			}
		});

		sText.addExtendedModifyListener(
				new ExtendedModifyListener() {
					public void modifyText(ExtendedModifyEvent event){
						String currText = sText.getText();
						String newText = currText.substring(event.start, event.start + event.length);
						if( newText != null && newText.length() > 0 ){
							if( undoStack.size() == MAX_STACK_SIZE ){
								undoStack.remove( undoStack.size() - 1 );
							}
							undoStack.add(0, newText);
						}
					} } );
	}
	
	public void refresh()	{
		sText.redraw();
	}
	public void setSearchBoxStatus(Boolean status)	{
		isOpendSearchBox = status;
	}
	
	public static void setSearchKeyword(final String searchStr)	{
		searchKeyword = searchStr;
		System.out.println("searchStr = "+searchStr);
	}
	public String getTextContents()		{
		System.out.println("getTextContents");
		return sText.getText();
	}
	public StyledText getCurrentTextHandle()	{
		System.out.println("getCurrentTextHandle");
		return sText;
	}
	
	public void setKeywords(String[] keyStr)	{
		keywords[0] = keyStr[0];
		keywords[1] = keyStr[1];
		keywords[2] = keyStr[2];
		keywords[3] = keyStr[3];
	}
	
	private static StyleRange getHighlightStyle(int startOffset, int length, Shell shell, int index) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		switch(index)
		{
		case 0 : styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
				 break;
		case 1 : styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_GREEN);
		 		 break;
		case 2 : styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_CYAN);
		 		 break;
		case 3 : styleRange.background = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE);
				 styleRange.foreground = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);
		 		 break;
		}
		return styleRange;
	}
	
	private void undo(){
		if( undoStack.size() > 0 ){
			String lastEdit = (String)undoStack.remove(0);
			int editLength = lastEdit.length();
			String currText = sText.getText();
			int startReplaceIndex = currText.length() - editLength;
			sText.replaceTextRange(startReplaceIndex, editLength, "");
			redoStack.add(0, lastEdit);    
		}
	}

	/**
	 * F2 눌렀을 때 실행 : 다시실행
	 */
	private void redo(){
		if( redoStack.size() > 0 ){
			String text = (String)redoStack.remove(0);
			moveCursorToEnd();
			sText.append(text);
			moveCursorToEnd();
		}
	}
	private void moveCursorToEnd(){
		sText.setCaretOffset(sText.getText().length());
	}
}

class TextChange {	
	private int start;				// The starting offset of the change	
	private int length;				// The length of the change
	String replacedText;			// The replaced text

	public TextChange(int start, int length, String replacedText) {
		this.start = start;
		this.length = length;
		this.replacedText = replacedText;
	}

	public int getStart() {
		return start;
	}
	public int getLength() {
		return length;
	}
	public String getReplacedText() {
		return replacedText;
	}
}

class SyntaxData {
	private String extension;
	private Collection keywords;
	private String punctuation;
	private String comment;
	private String multiLineCommentStart;
	private String multiLineCommentEnd;

	public SyntaxData(String extension) {
		this.extension = extension;
	}
	public String getExtension() {
		return extension;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Collection getKeywords() {
		return keywords;
	}
	public void setKeywords(Collection keywords) {
		this.keywords = keywords;
	}
	public String getMultiLineCommentEnd() {
		return multiLineCommentEnd;
	}
	public void setMultiLineCommentEnd(String multiLineCommentEnd) {
		this.multiLineCommentEnd = multiLineCommentEnd;
	}
	public String getMultiLineCommentStart() {
		return multiLineCommentStart;
	}
	public void setMultiLineCommentStart(String multiLineCommentStart) {
		this.multiLineCommentStart = multiLineCommentStart;
	}
	public String getPunctuation() {
		return punctuation;
	}
	public void setPunctuation(String punctuation) {
		this.punctuation = punctuation;
	}
}

/**
 * This class manages the syntax coloring and styling data
 */
class SyntaxManager {
	// Lazy cache of SyntaxData objects
	private static Map data = new Hashtable();

	/**
	 * Gets the syntax data for an extension
	 */
	public static synchronized SyntaxData getSyntaxData(String extension) {
		// Check in cache
		SyntaxData sd = (SyntaxData) data.get(extension);
		if (sd == null) {
			// Not in cache; load it and put in cache
			sd = loadSyntaxData(extension);
			if (sd != null)
				data.put(sd.getExtension(), sd);
		}
		return sd;
	}

	/**
	 * Loads the syntax data for an extension
	 * 
	 * @param extension
	 *          the extension to load
	 * @return SyntaxData
	 */
	private static SyntaxData loadSyntaxData(String extension) {
		SyntaxData sd = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle("examples.ch11." + extension);
			sd = new SyntaxData(extension);
			sd.setComment(rb.getString("comment"));
			sd.setMultiLineCommentStart(rb.getString("multilinecommentstart"));
			sd.setMultiLineCommentEnd(rb.getString("multilinecommentend"));

			// Load the keywords
			Collection keywords = new ArrayList();
			for (StringTokenizer st = new StringTokenizer(rb.getString("keywords"), " "); st.hasMoreTokens();) {
				keywords.add(st.nextToken());
			}
			sd.setKeywords(keywords);

			// Load the punctuation
			sd.setPunctuation(rb.getString("punctuation"));
		} catch (MissingResourceException e) {
			// Ignore
		}
		return sd;
	}
}
