package layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import control.ControlDialogYesNo;
import control.ControlFiles;
import definition.Config;

public class LayoutPopup {
	private static Shell g_shell;
	
	public static void listPopupBox(final Shell shell, final Tree tree)	{
		g_shell = shell;

		final TreeEditor editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		
		Menu popupMenu = new Menu(tree);
		
		/*MenuItem refreshTree = new MenuItem(popupMenu, SWT.NONE);
		refreshTree.setText("Refresh Tree");
		*/
	    MenuItem editItem = new MenuItem(popupMenu, SWT.NONE);
	    editItem.setText("Edit");
	    
	    MenuItem deleteItem = new MenuItem(popupMenu, SWT.NONE);
	    deleteItem.setText("Delete");

	    /*Menu newMenu = new Menu(popupMenu);
	    newItem.setMenu(newMenu);

	    MenuItem shortcutItem = new MenuItem(newMenu, SWT.NONE);
	    shortcutItem.setText("Shortcut");
	    MenuItem iconItem = new MenuItem(newMenu, SWT.NONE);
	    iconItem.setText("Icon");*/
	    
	    /*refreshTree.addSelectionListener(new SelectionListener() {	    	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
			@Override
			public void widgetSelected(SelectionEvent e) {	
				removeAndRefresh(Config.workingTree);
			}
	    });
	    */
	    editItem.addSelectionListener(new SelectionListener() {	    	
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
			@Override
			public void widgetSelected(SelectionEvent e) {	
				// Create a text field to do the editing
				final Text text = new Text(tree, SWT.NONE);
				text.setText(Config.currentTreeItem.getText());
				text.selectAll();
				text.setFocus();
				text.setBackground(LayoutColor.getColor("gray"));
				text.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent event) {
						if (event.keyCode == SWT.CR)
							textEditingRun(text);
					}
				});
				text.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent event) {
						textEditingRun(text);
					}
				});
						
				editor.setEditor(text, Config.currentTreeItem);
			}
	    });
	    deleteItem.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {				
			}
			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean ret = false;
				String preFixStr = "";
				String fileName = null;
				final TreeItem selectionItem = Config.currentTreeItem;
				TreeItem[] childrenItem = selectionItem.getItems();

				if (hasChildItem(childrenItem) == false)	{
					//has no child item
					if (hasParentItem(selectionItem)){
						preFixStr = getParentItemName(selectionItem);
						fileName = preFixStr + "_" + selectionItem.getText();
					}	else	{
						fileName = selectionItem.getText();
					}
					//delete current item
					ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
					removeAndRefresh(Config.workingTree);
				}	else	{
					ret = ControlDialogYesNo.YesNoDialog(g_shell, "exist child item", "really remove?");
					if (ret)	{
						//delete all child items
						for (int i = 0; i < childrenItem.length; i++)	{
							fileName = selectionItem.getText() + "_" + childrenItem[i].getText();
							ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
						}
						//delete current item
						fileName = selectionItem.getText();
						ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
						
						//tree items refresh
						removeAndRefresh(Config.workingTree);
					}	else	{
						;
					}									
				}
			}
	    });
	    
	    tree.setMenu(popupMenu);
	}
	
	private static void textEditingRun(final Text text)	{
		String preFixStr = "";
		String fileName = null;
		String oldFileName = null;
		final TreeItem selectionItem = Config.currentTreeItem;
		TreeItem[] childrenItem = selectionItem.getItems();

		if (selectionItem.getText().equals(text.getText()))	{
			;	//not changed
		}	else	{							
			if (hasChildItem(childrenItem) == false)	{				
				//has no child item
				if (hasParentItem(selectionItem)){	//sub
					preFixStr = getParentItemName(selectionItem);
					oldFileName = preFixStr + "_" + selectionItem.getText();
					fileName = preFixStr + "_" + text.getText();								
				}	else	{	//main
					oldFileName = selectionItem.getText();
					fileName = text.getText();
				}
				ControlFiles.copyFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt",
									Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
				ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt");
			}	else	{
				oldFileName = selectionItem.getText();
				fileName = text.getText();
				ControlFiles.copyFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt",
									Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
				ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt");
				
				for (int i = 0; i < childrenItem.length; i++)	{
					oldFileName = selectionItem.getText() + "_" + childrenItem[i].getText();
					fileName = text.getText() + "_" + childrenItem[i].getText();
					ControlFiles.copyFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt",
										Config.CurrentWorkingDirectory + "\\" + fileName  + ".txt");
					ControlFiles.deleteFile(Config.CurrentWorkingDirectory + "\\" + oldFileName  + ".txt");
				}								
			}
		}
		selectionItem.setText(text.getText());
		text.dispose();
	}

	private static void removeAndRefresh(final Tree tree)	{
		tree.removeAll();
		LayoutMain.createTree(tree);
		tree.redraw();	
	}
	
	private static String getParentItemName(final TreeItem item)	{
		return item.getParentItem().getText();
	}
	
	public static Boolean hasParentItem(final TreeItem item)	{
		Boolean ret = false;
		if (item.getParentItem() != null)
			ret = true;
		
		return ret;
	}
	
	public static Boolean hasChildItem(final TreeItem[] items)	{
		Boolean retHasChild = false;
		
		if (items.length == 0)	{
			retHasChild = false;
		}	else	{
			retHasChild = true;
		}
		return retHasChild;			
	}
}