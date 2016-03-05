package NingGimi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class SukerTray_TableManager {
	private 	Table				tableManager;
	private		SukerTray_Launcher	launchManager;
	
	private		TableColumn 		numColumn;
	private		TableColumn 		titleColumn;
	private		TableColumn 		contentsColumn;
	
	private		int			currentTableSize;
	private static int 		selectIndexOfItem = 1;
	
	public SukerTray_TableManager(Table t,  SukerTray_Launcher launchH) {
		this.tableManager = t;
		this.launchManager = launchH;
	}
	public void addTableItem()	{
		TableItem item = new TableItem(tableManager, SWT.NONE);
		item.setText(0, ""+currentTableSize);
		item.setText(1, "");
		item.setText(2, "");
		currentTableSize++;
	}
	public void deleteTableItem()	{
		int index = -1;
		
		index = tableManager.getSelectionIndex();
		if (index != -1)	{
			tableManager.remove(index);
			currentTableSize--;
			TableItem[] item = tableManager.getItems();
			for (int i = 0; i < item.length; i++)	{
				item[i].setText(0, ""+i);
			}
		}
	}
	public void goUpTableItem()	{
		int index = -1;
		
		index = tableManager.getSelectionIndex();
		if (index > 0)	{			
			TableItem targetItem = tableManager.getItem(index);
			TableItem upItem = tableManager.getItem(index-1);
			
			String upItemText1 = upItem.getText(1);
			String upItemText2 = upItem.getText(2);
			
			upItem.setText(1, targetItem.getText(1));
			upItem.setText(2, targetItem.getText(2));
			targetItem.setText(1, upItemText1);
			targetItem.setText(2, upItemText2);
			
			tableManager.setSelection(index-1);
			tableManager.setFocus();
			launchManager.setLaunchOrder(upItem.getText(2));
		}
	}
	
	public void goDownTableItem()	{
		int index = -1;
		
		index = tableManager.getSelectionIndex();
		if (index < tableManager.getItemCount()-1)	{			
			TableItem targetItem = tableManager.getItem(index);
			TableItem downItem = tableManager.getItem(index+1);
			
			String downItemText1 = downItem.getText(1);
			String downItemText2 = downItem.getText(2);
			
			downItem.setText(1, targetItem.getText(1));
			downItem.setText(2, targetItem.getText(2));
			targetItem.setText(1, downItemText1);
			targetItem.setText(2, downItemText2);
			
			tableManager.setSelection(index+1);
			tableManager.setFocus();
			launchManager.setLaunchOrder(downItem.getText(2));
		}
	}
	
	public List<String> getTableTitleListing()	{
		List<String>	titleList = new ArrayList<String>();
		for (int i = 0; i < currentTableSize; i++){
			titleList.add(tableManager.getItem(i).getText(1));
		}
		
		return titleList;
	}
	public List<String> getTableContentsListing()	{
		List<String>	ContentsList = new ArrayList<String>();
		for (int i = 0; i < currentTableSize; i++){
			ContentsList.add(tableManager.getItem(i).getText(2));
		}
		
		return ContentsList;
	}
	public void createTableItems(List<String> listTitle, List<String> listContent)	{
		String[] titles = { "No.", "Title", "Contents" };
		int listSize = listTitle.size()/2;
		
		currentTableSize = listSize;
		
		numColumn = new TableColumn(tableManager, SWT.NONE);
		numColumn.setText(titles[0]);
		numColumn.pack();

		titleColumn = new TableColumn(tableManager, SWT.NONE);
		titleColumn.setText(titles[1]);
		titleColumn.setWidth(90);
		//titleColumn.pack();
		
		contentsColumn = new TableColumn(tableManager, SWT.NONE);
		contentsColumn.setText(titles[2]);
		contentsColumn.setWidth(350);
		
		for (int i = 0; i < listSize; i++) {
			TableItem item = new TableItem(tableManager, SWT.NONE);
			item.setText(0, ""+i);
			item.setText(1, listTitle.get(i));
			item.setText(2, listContent.get(i));
		}
		
		final TableEditor editor = new TableEditor(tableManager);
	    editor.horizontalAlignment = SWT.LEFT;
	    editor.grabHorizontal = true;
	    
	    tableManager.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	        	Control oldEditor = editor.getEditor();
				if (oldEditor != null)
					oldEditor.dispose();
	        }
	    });
	    tableManager.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 3)	{	//right-click
					Point pt = new Point(e.x, e.y);
					TableItem item = tableManager.getItem(pt);				
					if (item == null)
						return;
					
					for (int i = 1; i < 3; i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							selectIndexOfItem = i;//tableManager.indexOf(item);						
						}
					}				
					
					Text newEditor = new Text(tableManager, SWT.NONE);
					newEditor.setText(item.getText(selectIndexOfItem));
					newEditor.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent me) {
							Text text = (Text) editor.getEditor();
							editor.getItem().setText(selectIndexOfItem, text.getText());
						}
					});
					newEditor.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {	
							if (e.character == SWT.CR) {						    
								Control oldEditor = editor.getEditor();
								if (oldEditor != null)
									oldEditor.dispose();
							}
						}

						@Override
						public void keyReleased(KeyEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					});
					newEditor.selectAll();
					newEditor.setFocus();
					editor.setEditor(newEditor, item, selectIndexOfItem);
				} else	{
					Point pt = new Point(e.x, e.y);
					TableItem item = tableManager.getItem(pt);
					if (item == null)	{
						Control oldEditor = editor.getEditor();
						if (oldEditor != null)
							oldEditor.dispose();
					
						return;
					}
					
					launchManager.setLaunchOrder(item.getText(2));
				}
			}			
			@Override
			public void mouseDoubleClick(MouseEvent e)
			{
				if (e.button != 3)	{	//right-click
					Point pt = new Point(e.x, e.y);
					TableItem item = tableManager.getItem(pt);
					if (item == null)	{
						Control oldEditor = editor.getEditor();
						if (oldEditor != null)
							oldEditor.dispose();
					
						return;
					}
					
					launchManager.setLaunchOrder(item.getText(2));
					launchManager.run();
				}
			}
			@Override
			public void mouseDown(MouseEvent e)
			{
			}
	    });
	    
	    /*tableManager.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {	
				if ((e.stateMask & SWT.CR) == SWT.CR)	{
					hotKey = SWT.CTRL;
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.println("press ctrl key");
					}
				} else if ((e.stateMask & SWT.ALT) == SWT.ALT)	{
					hotKey = SWT.ALT;
					if (AndroSuker_Debug.DEBUG_MODE_ON)	{
						System.out.println("press ctrl key");
					}
				}
				if ((hotKey == SWT.CTRL) && (e.keyCode == 'a'))	{
					mLogText.selectAll();
					hotKey = 0;
					mLogText.setCaretOffset(mLogText.getText().length());
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });*/
	}
}
