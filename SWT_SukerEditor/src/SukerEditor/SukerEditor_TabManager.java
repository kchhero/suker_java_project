package SukerEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class SukerEditor_TabManager {
	private Shell						currentShell;
	private List<CTabItem>				tabMain;
	private List<MenuItem>				menuItemInstance;
	private List<String> 				workedFilePathList = new ArrayList<String>();
	private HashMap<String, Boolean> 	workingStatusHashMap = new HashMap<String, Boolean>();
	private HashMap<String, String> 	workingTabNameHashMap = new HashMap<String, String>();
	private HashMap<String, Boolean> 	workingTabEditedHashMap = new HashMap<String, Boolean>();
	private HashMap<String, SukerEditor_TextFieldControl> workingTabStyledTextHashMap = new HashMap<String, SukerEditor_TextFieldControl>();
	private int							newTabCount;
	private int							totalTabCount;
	private CTabItem 					tbtmNew;
	private SukerEditor_FileControl		fileHandler;
	
	private int							currentActiveTabIndex;
	
	SukerEditor_TabManager(Shell shell, List<CTabItem> tab, SukerEditor_FileControl f, List<MenuItem> m)	{
		currentShell = shell;
		newTabCount = 0;
		totalTabCount = 0;
		this.tabMain = tab;		
		this.fileHandler = f;
		this.menuItemInstance = m;
	}
	/*public List<CTabItem> InitTabList(CTabFolder parent, List<String> openFilePath)	{			
		return tabMain;
	}
	public List<CTabItem> getTabList()	{
		return tabMain;
	}*/
	
	public void setActiveTabIndex(int index)	{
		currentActiveTabIndex = index;
	}
	public int getActiveTabIndex()	{
		return currentActiveTabIndex;
	}
	//-------------------------------------------- add -----------------------------------------------
	public CTabItem addTabList(CTabFolder parent)	{		
		return addTabList(parent, "NewText"+(newTabCount++)+" *", "", false);
	}	
	public CTabItem addTabList(final CTabFolder parent, final String fullPath, final String textInitValue, final Boolean status)	{
		String tabName;
		if (status == false)
			tabName = fullPath;
		else	{
			tabName = SukerEditor_FileControl.getOnlyFileName(fullPath);
			//setWorkedFilePathList(fullPath);
		}
		
		tbtmNew = new CTabItem(parent, SWT.CLOSE);
		tbtmNew.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		tbtmNew.setShowClose(true);
		tbtmNew.setText(tabName);
		tbtmNew.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				CTabItem tabItem = (CTabItem) event.widget;
				removeTabInList(tabItem.getText());
				tabMain.remove(event.widget);
				setActiveTabIndex(parent.getSelectionIndex());
			}
		});
		
		StyledText styledTextNew = new StyledText(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		SukerEditor_TextFieldControl styledTextNewControl = new SukerEditor_TextFieldControl(currentShell, styledTextNew);
		styledTextNew.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		styledTextNew.setTopMargin(5);
		styledTextNew.setRightMargin(10);
		styledTextNew.setLeftMargin(5);
		if (textInitValue == null || textInitValue.length() < 1)
			styledTextNew.setText("");
		else
			styledTextNew.setText(textInitValue);
		styledTextNew.setFocus();
		styledTextNew.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent event) {
				StyledText sText = (StyledText) event.widget;
				CTabFolder tabFolder = (CTabFolder)(sText.getParent());
				CTabItem tabItem = tabFolder.getSelection();
				if (getHashMapForCurrentWorkingEdited(tabItem.getText()))	{
					updateHashMapForCurrentWorkingEdited(tabItem, false);
					System.out.println("updateHashMapForCurrentWorkingEdited false");
				}
			}
		});
		
		tbtmNew.setControl(styledTextNew);
		
		this.tabMain.add(tbtmNew);
		this.addHashMapForCurrentWorkingFiles(fullPath, tabName, status, styledTextNewControl);
		this.totalTabCount++;
		
		menuItemEnabledCheck();
		return tbtmNew;
	}	
	private void addHashMapForCurrentWorkingFiles(String fullPath, String tabItemName, Boolean status, SukerEditor_TextFieldControl newText)	{
		if (this.workingStatusHashMap == null)
			System.out.println("hash null!!");

		this.workingStatusHashMap.put(tabItemName, status);
		this.workingTabNameHashMap.put(tabItemName, fullPath);
		this.workingTabEditedHashMap.put(tabItemName, true);
		this.workingTabStyledTextHashMap.put(tabItemName, newText);
	}
	
	//-------------------------------------------- remove -----------------------------------------------
	public void removeTabInList(String tabItemName)	{
		this.removeHashMapForCurrentWorkingFiles(tabItemName);
		--(this.totalTabCount);
		menuItemEnabledCheck();
	}
	public void removeAllTabInList()	{
		int size = tabMain.size();
		for(int i = 0; i < size; i++)	{
			tabMain.get(0).getControl().dispose();
			tabMain.get(0).dispose();
		}
		tabMain.clear();
		removeAllHashMapForCurrentWorkingFiles();
		this.totalTabCount = 0;
		menuItemEnabledCheck();
	}
	private void removeHashMapForCurrentWorkingFiles(String tabItemName)	{
		if (this.workingStatusHashMap == null || this.workingTabNameHashMap == null || this.workingTabEditedHashMap == null || this.workingTabStyledTextHashMap == null)
			System.out.println("hash null!!");
		
		this.workingStatusHashMap.remove(tabItemName);
		this.workingTabNameHashMap.remove(tabItemName);
		this.workingTabEditedHashMap.remove(tabItemName);
		this.workingTabStyledTextHashMap.remove(tabItemName);
	}
	private void removeAllHashMapForCurrentWorkingFiles()	{
		if (this.workingStatusHashMap == null || this.workingTabNameHashMap == null || this.workingTabEditedHashMap == null || this.workingTabStyledTextHashMap == null)
			System.out.println("hash null!!");
		
		this.workingStatusHashMap.clear();
		this.workingTabNameHashMap.clear();
		this.workingTabEditedHashMap.clear();
		this.workingTabStyledTextHashMap.clear();
	}
	
	//-------------------------------------------- update -----------------------------------------------
	public void updateTabStatusInList(CTabItem tabItem, String fullPath, String tabItemOldName, String tabItemNewName, Boolean status, SukerEditor_TextFieldControl newText)	{
		tabItem.setText(tabItemNewName);		
		this.updateHashMapForCurrentWorkingFiles(fullPath, tabItemOldName, tabItemNewName, true, newText);
	}
	private void updateHashMapForCurrentWorkingFiles(String fullPath, String tabItemOldName, String tabItemNewName, Boolean status, SukerEditor_TextFieldControl newText)	{
		if (this.workingStatusHashMap == null || this.workingTabNameHashMap == null || this.workingTabEditedHashMap == null || this.workingTabStyledTextHashMap == null)
			System.out.println("hash null!!");
		
		this.workingStatusHashMap.remove(tabItemOldName);
		this.workingStatusHashMap.put(tabItemNewName, status);
		
		this.workingTabNameHashMap.remove(tabItemOldName);
		this.workingTabNameHashMap.put(tabItemNewName, fullPath);
		
		this.workingTabEditedHashMap.remove(tabItemOldName);
		this.workingTabEditedHashMap.put(tabItemNewName, true);
		
		this.workingTabStyledTextHashMap.remove(tabItemOldName);
		this.workingTabStyledTextHashMap.put(tabItemNewName, newText);
	}	
	
	public List<String> getWorkdedFilePathList()	{
		return workedFilePathList;
	}
	public void setWorkedFilePathList(String path)	{
		this.workedFilePathList.add(path);
	}
	public void refreshWorkedFilePathList()	{		
		int size = this.workedFilePathList.size();
		
		for (int i = 0; i < size; i++)	{
			this.workedFilePathList.remove(0);
		}
	
        Collection<String> coll = this.workingTabNameHashMap.values();
        for(Iterator<String> it = coll.iterator() ; it.hasNext() ; )
		{
        	String value = it.next();
        	if (value != null && value.length() > 1)	{
        		this.workedFilePathList.add(value);
        	}
		}
	}
	
	public Boolean getHashMapForCurrentWorkingFilesStatus(String tabItemName)	{
		if (this.workingStatusHashMap == null)
			System.out.println("hash null!!");

		return workingStatusHashMap.get(tabItemName);		
	}
	
	public String getHashMapForCurrentWorkingFilesPath(String tabItemName)	{
		if (this.workingTabNameHashMap == null)
			System.out.println("hash null!!");

		return workingTabNameHashMap.get(tabItemName);		
	}
	public Boolean getHashMapForCurrentWorkingEdited(String tabItemName)	{
		if (this.workingTabEditedHashMap == null)
			System.out.println("hash null!!");

		return workingTabEditedHashMap.get(tabItemName);		
	}
	
	public void updateHashMapForCurrentWorkingEdited(CTabItem tabItem, Boolean status)	{
		if (this.workingTabEditedHashMap == null)
			System.out.println("hash null!!");

		this.workingTabEditedHashMap.remove(tabItem.getText());
		this.workingTabEditedHashMap.put(tabItem.getText(), status);
		
		if (status)
			tabItem.setFont(SWTResourceManager.getFont("Courier New", 10, SWT.NORMAL));
		else
			tabItem.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.BOLD));
	}
	
	public SukerEditor_TextFieldControl getHashMapForCurrentWorkingStyledText(String tabItemName)	{
		if (this.workingTabStyledTextHashMap == null)
			System.out.println("hash null!!");

		return workingTabStyledTextHashMap.get(tabItemName);		
	}
	
	public void createDragTarget(final CTabFolder parent)	{
		/** drag drop support **/
		int ops = DND.DROP_COPY | DND.DROP_LINK | DND.DROP_DEFAULT;
		final FileTransfer fTransfer = FileTransfer.getInstance();
		final ImageTransfer iTransfer = ImageTransfer.getInstance();
		final TextTransfer tTransfer = TextTransfer.getInstance();
		
		Transfer[] transfers = new Transfer[] { fTransfer, iTransfer, tTransfer };
		DropTarget target = new DropTarget(parent, ops);
		target.setTransfer(transfers);

		target.addDropListener(new DropTargetAdapter() {
			@Override
			public void drop(DropTargetEvent event) {
				if (event.data instanceof String[]) {
					String[] filenames = (String[]) event.data;
					for (int i = 0; i< filenames.length; i++){
						String tabName;
						String textInitValue = null;
						
						tabName = SukerEditor_FileControl.getOnlyFileName(filenames[i]);
						try {
							textInitValue = fileHandler.readFileByBurst(filenames[i]);
						} catch (Exception e) {
							e.printStackTrace();
						}
						parent.setSelection(addTabList(parent, tabName, textInitValue, true));
						SukerEditor_StatusBarDisplay.updateName(filenames[i]);
					}
				} 
				/*else if (event.data instanceof ImageData) {
					Image i = new Image(Display.getCurrent(), (ImageData) event.data);
					//lblImage.setImage(i);
				}*/
			}

			public void dragEnter(DropTargetEvent event) {
				System.out.println("drag enter");
				event.detail = DND.DROP_COPY;
			}
		});
	}
	
	public int getTotalOpenedTabItemCount()	{
		return this.totalTabCount;
	}
	
	
	public void menuItemEnabledCheck()	{
		if (this.totalTabCount > 1)	{
			;//nothing
		} else if (this.totalTabCount > 0)	{			
			for(Iterator<MenuItem> it = menuItemInstance.iterator() ; it.hasNext() ; )
			{
			        MenuItem value = it.next();
			        if (!value.isDisposed())
			        	value.setEnabled(true);
			}
		} else	{
			for(Iterator<MenuItem> it = menuItemInstance.iterator() ; it.hasNext() ; )
			{
			        MenuItem value = it.next();
			        if (!value.isDisposed())
			        	value.setEnabled(false);
			}
		}
	}	        
}

/*
 * Iterator<Integer> iter = hm.keySet().iterator();
 
     while(iter.hasNext())
     {
         int nCount    = iter.next();
         System.out.println(hm.get(nCount));
 */