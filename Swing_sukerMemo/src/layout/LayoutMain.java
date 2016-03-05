package layout;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import control.ControlDialogYesNo;
import control.ControlFiles;
import definition.Config;

public class LayoutMain {
	SashForm sashForm;
	Group controlGroup, layoutGroup;
	StyledText styledText;
	String currentWorkingFileName = null;
	String oldWorkingFileName = null;
	Boolean currentWorkingFileModified = false;
	Boolean bFirstStartFlowDone = false;
	Boolean bDoubleClicked = false;
	Shell g_shell;
	Text searchText1;
	Text searchText2;
	Text searchText3;
	Text newItemText;
	Button newMainButton;
	Button newSubButton;
	
	//String findStr = null;
	Control[] tabList;
    
	public LayoutMain(final Display display, final Shell shell) {
		g_shell = shell;		
		//----------------------------------------------------------------		
		shell.setLayout(new GridLayout(1, false));
		shell.addShellListener(new ShellAdapter()	{
			public void shellClosed(ShellEvent e)	{
				showSaveFileYesNoDialog();
			}
		});
		//----------------------------------------------------------------
		//상단 search box -------------------------------------------------
		RowLayout upperlayout = new RowLayout(SWT.HORIZONTAL);
		upperlayout.wrap = false;
		upperlayout.fill = true;
		upperlayout.justify = false;
	    Composite upperComposite = new Composite(shell, SWT.NONE);
	    upperComposite.setLayout(upperlayout);
	    
	    //상단  search text -----------------------------------------------
	    RowData data;	    
	    data = new RowData();
	    data.width = 180;
	    newItemText = new Text(upperComposite, SWT.LEFT | SWT.BORDER);
	    newItemText.setText("");
	    newItemText.setLayoutData(data);
	    newItemText.addKeyListener(new KeyAdapter()	{
	    	public void keyPressed(KeyEvent event) {	    		
	    		if (event.keyCode == SWT.CR)	{
	    			newMainItem(newItemText.getText());
	    			newItemText.setText("");
	    		}
	    	}
	    });
	    
	    //상단  new button -----------------------------------------------
	    data = new RowData();
	    data.width = 100;
	    newMainButton = new Button(upperComposite, SWT.CENTER);
	    newMainButton.setText("New Main Item");
	    newMainButton.setLayoutData(data);
	    newMainButton.setEnabled(false);	    
	    newMainButton.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	newMainItem(newItemText.getText());
	        	newItemText.setText("");
	        }
	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	      });
	    
	    //상단  new button -----------------------------------------------
	    data = new RowData();
	    data.width = 100;
	    newSubButton = new Button(upperComposite, SWT.CENTER);
	    newSubButton.setText("New Sub Item");
	    newSubButton.setLayoutData(data);
	    newSubButton.setEnabled(false);	    
	    newSubButton.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	newSubItem(newItemText.getText());
	        	newItemText.setText("");
	        }
	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	      });
	    
	    //상단  save button -----------------------------------------------
	    /*data = new RowData();
	    data.width = 100;
	    Button saveButton = new Button(upperComposite, SWT.CENTER);
	    saveButton.setText("Save");
	    saveButton.setLayoutData(data);
	    
	    saveButton.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {				
	        	ControlFiles.saveFile(oldWorkingFileName, styledText.getText());
	        	currentWorkingFileModified = false;
	        	oldWorkingFileName = null;
	        }
	        public void widgetDefaultSelected(SelectionEvent event) {
	        }
	      });*/
	    //----------------------------------------------------------------
	    //Body Style Text
	    //----------------------------------------------------------------	    
	    Composite middleComposite = new Composite(shell, SWT.NONE);
	    middleComposite.setLayout(new FillLayout());
	    middleComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
	    sashForm = new SashForm(middleComposite, SWT.HORIZONTAL | SWT.BORDER);

	    Config.workingTree = new Tree(sashForm, SWT.SINGLE);
	    createTree(Config.workingTree);
	    initializeTree(Config.workingTree);
	    //----------------------------------------------------------------
	    //Styled Text Create
	    //----------------------------------------------------------------	    
		styledText = new StyledText(sashForm, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		styledText.setText("");
		styledText.setBackground(LayoutColor.getRGBColor(Config.styledBgColorR, Config.styledBgColorR, Config.styledBgColorR));
		styledText.setForeground(LayoutColor.getColor(Config.styledTextColor));
		
		FontData styledFontData = styledText.getFont().getFontData()[0];
	    Font font = new Font(display, styledFontData.getName(), styledFontData.getHeight()*6/5, styledFontData.getStyle());
		styledText.setFont(font);

		//----------------------------------------------------------------
	    //Fileter, Find set Create
	    //----------------------------------------------------------------
		Group filterGroup;
		filterGroup = new Group(sashForm, SWT.NONE);
		filterGroup.setText("Filter");
		GridLayout layout = new GridLayout();
		filterGroup.setLayout(layout);
		
		GridData filterWordData1 = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		filterWordData1.minimumWidth = 30;
	    searchText1 = new Text(filterGroup, SWT.WRAP | SWT.BORDER);
	    searchText1.setLayoutData(filterWordData1);	    
	    searchText1.addTraverseListener(new TraverseListener() {
	          public void keyTraversed(TraverseEvent e) {
	            if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
	              e.doit = true;
	            }
	          }
	    });
	    
	    GridData filterWordData2 = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
	    filterWordData2.minimumWidth = 30;
	    searchText2 = new Text(filterGroup, SWT.WRAP | SWT.BORDER);
	    searchText2.setLayoutData(filterWordData2);
	    searchText2.addTraverseListener(new TraverseListener() {
	          public void keyTraversed(TraverseEvent e) {
	            if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
	              e.doit = true;
	            }
	          }
	    });

	    GridData filterWordData3 = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
	    filterWordData3.minimumWidth = 30;
	    searchText3 = new Text(filterGroup, SWT.WRAP | SWT.BORDER);
	    searchText3.setLayoutData(filterWordData3);
	    searchText3.addTraverseListener(new TraverseListener() {
	          public void keyTraversed(TraverseEvent e) {
	            if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
	              e.doit = true;
	            }
	          }
	    });

	    GridData filterBtnData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
	    filterBtnData.minimumWidth = 30;
	    Button filterButton = new Button(filterGroup, SWT.CENTER);
	    filterButton.setText("Filtering");
	    filterButton.setLayoutData(filterBtnData);

	    GridData clearBtnData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
	    clearBtnData.minimumWidth = 30;
	    Button clearButton = new Button(filterGroup, SWT.CENTER);
	    clearButton.setText("Clearing");
	    clearButton.setLayoutData(clearBtnData);

	    filterButton.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	styledText.redraw();
	        }
	        public void widgetDefaultSelected(SelectionEvent event) {				
	        }
	    });
	    clearButton.addSelectionListener(new SelectionListener() {
	        public void widgetSelected(SelectionEvent event) {
	        	searchText1.setText("");
	        	searchText2.setText("");
	        	searchText3.setText("");	        	
	        	styledText.redraw();     			
	        }
	        public void widgetDefaultSelected(SelectionEvent event) {				
	        }
	    });
		//----------------------------------------------------------------
		sashForm.setWeights(new int[] { 2, 7, 2});		
		//----------------------------------------------------------------
		Composite bellowComposite = new Composite(shell, SWT.NONE);
		bellowComposite.setLayout(new RowLayout());
	    Label label = new Label(bellowComposite, SWT.PUSH);
	    label.setText("Created by suker(슈케르) ver 0.1 since March 2012");
	    //----------------------------------------------------------------	  
		StyledTextSetListener();
		//************************************************************************************
	}
	
	public static void createTree(final Tree tree) {
		// current directory check, current directory file names check and list-up ---
		String[] fileList;
		String[] parseStr;
		String[] tempStr = null;
		String oldStr = null;
		TreeItem mainItem = null;
		TreeItem subItem = null;
		
		fileList = ControlFiles.getFileNameListInDir(Config.CurrentWorkingDirectory);
		
		// parsing for file names & Fill the tree with data --------------------------
		if (fileList != null)	{
			for (int i = 0; i < fileList.length; i++) {
				tempStr = fileList[i].split("\\.");
				
				parseStr = tempStr[0].split("_");
				
				if (parseStr.length == 1)	{	//only main item exist
					mainItem = new TreeItem(tree, SWT.NONE);
					mainItem.setText(parseStr[0].toString());
					oldStr = parseStr[0].toString();
				} else	{	//have sub item					
					if (oldStr == null)	{
						mainItem = new TreeItem(tree, SWT.NONE);
						mainItem.setText(tempStr[0].toString());
					}	else	{
						if (oldStr.toString().equals(parseStr[0].toString()))	{
							subItem = new TreeItem(mainItem, SWT.NONE);
							subItem.setText(parseStr[1].toString());
						} else	{
							mainItem = new TreeItem(tree, SWT.NONE);
							mainItem.setText(tempStr[0].toString());
						}
					}
				}			
				mainItem.setExpanded(true);
			}
		}
	}
	public void initializeTree(final Tree tree) {
		//----------------------------------------------------------------------------
		Color treeColor = LayoutColor.getRGBColor(Config.treeBgColorR, Config.treeBgColorG, Config.treeBgColorB);
		tree.setBackground(treeColor);
		treeColor.dispose();
		tree.setForeground(LayoutColor.getColor(Config.treeTextColor));
		tree.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				Config.currentTreeItem = tree.getSelection()[0];
				if (LayoutPopup.hasParentItem(Config.currentTreeItem))	{
					newMainButton.setEnabled(false);
					newSubButton.setEnabled(false);
					newItemText.setEnabled(false);
				}
				else	{
					newMainButton.setEnabled(true);
					newSubButton.setEnabled(true);
					newItemText.setEnabled(true);
				}
				if (e.button == 3)	{	//right-click
					LayoutPopup.listPopupBox(g_shell,tree);
				}				
			}
			public void mouseUp(MouseEvent e) {				
			}
			public void mouseDoubleClick(MouseEvent e) {			
				//fill on the styled text area
				TreeItem item = tree.getSelection()[0];
				TreeItem parentItem = item.getParentItem();
				String itemNameStr = null;
				if (parentItem == null) {
					itemNameStr = item.getText() + ".txt";
				} else {
					itemNameStr = parentItem.getText() + "_" + item.getText() + ".txt";
				}
				bDoubleClicked = true;
				oldWorkingFileName = currentWorkingFileName;
				showSaveFileYesNoDialog();
				currentWorkingFileName = Config.CurrentWorkingDirectory + "\\" + itemNameStr;
				styledText.setText(ControlFiles.openFile(currentWorkingFileName));
				if (oldWorkingFileName == null)	{
					oldWorkingFileName = currentWorkingFileName;
				}
				
				styledText.setFocus();
			}
		});
	}
	
	private void StyledTextSetListener()	{
		styledText.addLineStyleListener(new LineStyleListener()	{
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void lineGetStyle(LineStyleEvent event) {
				if((searchText1.getText() == null || searchText1.getText().length() == 0) &&						 
					(searchText2.getText() == null || searchText2.getText().length() == 0) &&
					(searchText3.getText() == null || searchText3.getText().length() == 0)) {
					event.styles = new StyleRange[0];
					return;
				}

				String line = event.lineText;
				int cursor = -1;

				LinkedList list = new LinkedList();
				if (searchText1.getText() != null && searchText1.getText().length() > 0)	{
					while( (cursor = line.indexOf(searchText1.getText(), cursor+1)) >= 0) {
						list.add(getHighlightStyle(LayoutColor.getColor("green"), LayoutColor.getColor("black"),
													event.lineOffset+cursor, searchText1.getText().length()));
					}
				}
				if (searchText2.getText() != null && searchText2.getText().length() > 0)	{
					while( (cursor = line.indexOf(searchText2.getText(), cursor+1)) >= 0) {
						list.add(getHighlightStyle(LayoutColor.getColor("yellow"), LayoutColor.getColor("black"),
													event.lineOffset+cursor, searchText2.getText().length()));
					}
				}
				if (searchText3.getText() != null && searchText3.getText().length() > 0)	{
					while( (cursor = line.indexOf(searchText3.getText(), cursor+1)) >= 0) {
						list.add(getHighlightStyle(LayoutColor.getColor("red"), LayoutColor.getColor("black"),
													event.lineOffset+cursor, searchText3.getText().length()));
					}
				}
				event.styles = (StyleRange[]) list.toArray(new StyleRange[list.size()]);
			}
		});
		
		//************************************************************************************
		styledText.addModifyListener(new ModifyListener()	{
			@Override
			public void modifyText(ModifyEvent e) {
				if (bFirstStartFlowDone == false)	{
					bFirstStartFlowDone = true;
				}
				else	{
					if (bDoubleClicked == true)	{
						currentWorkingFileModified = false;
						bDoubleClicked = false;
					}	else	{						
						currentWorkingFileModified = true;					
					}
				}
			}			
		});
		
		//************************************************************************************
		styledText.addKeyListener(new KeyAdapter() {
		      public void keyPressed(KeyEvent event) {
		    	  if ((event.stateMask & SWT.CTRL) != 0)
		    	  {
		    		  if ((event.keyCode == 'a' || event.keyCode == 'A'))
		    			  styledText.selectAll();
		    		  else if ((event.keyCode == 'f' || event.keyCode == 'F'))	{
		    			  //findStr = LayoutInputDialog.show(g_shell, styledText); //나중에..
		    			  searchText1.setFocus();		    			  
		    		  }
		    		  else if ((event.keyCode == 's' || event.keyCode == 'S'))	{
		    			  ControlFiles.saveFile(currentWorkingFileName, styledText.getText());
		    			  currentWorkingFileModified = false;
		    		  }
		    	  }
		      }
		      public void keyReleased(KeyEvent event) {
		          if (event.keyCode == 'p' && (event.stateMask & SWT.CTRL) != 0) {
		            styledText.print();
		          }
		      }
		});
	}
	
	private StyleRange getHighlightStyle(Color colorBg, Color colorFg, int startOffset, int length) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = startOffset;
		styleRange.length = length;
		styleRange.background = colorBg;
		styleRange.foreground = colorFg;
		return styleRange;
	}
	
	private void showSaveFileYesNoDialog()	{
		if (currentWorkingFileModified == true && oldWorkingFileName != null){	//modified old file & save question
			Boolean retSaveFile = false;
			retSaveFile = ControlDialogYesNo.YesNoDialog(g_shell, "Save " + oldWorkingFileName + " files?",
					"File Saving");
			if (retSaveFile)	{
				ControlFiles.saveFile(currentWorkingFileName, styledText.getText());
			}
		}
	}
	
	private void newMainItem(String text)	{
		if(text.length() > 0)	{
    		ControlFiles.saveFile(Config.CurrentWorkingDirectory + "\\" + text + ".txt", " ");
    		appendTreeItem(text);    		
    	}
	}
	
	private void newSubItem(String text)	{		
		if(text.length() > 0)	{
			TreeItem subItem;			
			String fileName;
			fileName = Config.currentTreeItem.getText() + "_" + text;
    		ControlFiles.saveFile(Config.CurrentWorkingDirectory + "\\" + fileName + ".txt", " ");
    		subItem = new TreeItem(Config.currentTreeItem, SWT.NONE);
			subItem.setText(text);
    	}
	}
	
	private void appendTreeItem(String name)	{
		TreeItem appendmainItem = null;
		appendmainItem = new TreeItem(Config.workingTree, SWT.NONE);
		appendmainItem.setText(name);
		Config.workingTree.redraw();
	}
}