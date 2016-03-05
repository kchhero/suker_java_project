package sukerPkg;

import java.util.HashMap;
import java.util.WeakHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AndroSuker_SetupDialog extends Dialog {
	protected boolean result;
	protected Shell shlSetup;
	protected int	mStyle;
	private WeakHashMap<String, Boolean> resultHashMap = new WeakHashMap<String, Boolean>();	//suker 2011.09.24
	private Button btnCheckButton_0;
	private Button btnCheckButton_1;
	private Button btnCheckButton_2;
	private Button btnCheckButton_3;
	private Button btnCheckButton_4;
	private Button btnCheckButton_5;
	private Button btnCheckButton_6;
	private Button btnCheckButton_7;
	private Button btnCheckButton_8;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public AndroSuker_SetupDialog(Shell parent, int style) {
		super(parent, style);
		mStyle = style;
		setText("SWT Dialog");
		result = false;
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public HashMap<String, Boolean> open(HashMap<String, Boolean> map) {
		initSetupValues(map);
		createContents();
		shlSetup.open();
		shlSetup.layout();
		
		AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Warning!", " tab enable 정보를 변경하면 \n ini/AndroSuker.ini가 변경되므로 기존의 setting 정보가 \n 삭제될 수 있습니다. ", SWT.OK);
		
		Display display = getParent().getDisplay();
		while (!shlSetup.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		map.put("tab0", resultHashMap.get("tab0"));
		map.put("tab1", resultHashMap.get("tab1"));
		map.put("tab2", resultHashMap.get("tab2"));
		map.put("tab3", resultHashMap.get("tab3"));
		map.put("tab4", resultHashMap.get("tab4"));
		map.put("tab5", resultHashMap.get("tab5"));		
		map.put("tab6", resultHashMap.get("tab6"));
		map.put("tab7", resultHashMap.get("tab7"));
		map.put("tab8", resultHashMap.get("tab8"));
		map.put("OK", resultHashMap.get("OK"));

		return map;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlSetup = new Shell(getParent(), mStyle);
		shlSetup.setSize(258, 417);
		shlSetup.setText("Setup");
		shlSetup.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(shlSetup, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		lblNewLabel.setText("Select tab enable");
		
		Group group = new Group(shlSetup, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_group.heightHint = 303;
		gd_group.widthHint = 238;
		group.setLayoutData(gd_group);
		
		btnCheckButton_0 = new Button(group, SWT.CHECK);
		btnCheckButton_0.setBounds(10, 21, 167, 16);
		btnCheckButton_0.setText("Tab - Logcat");
		btnCheckButton_0.setSelection(resultHashMap.get("tab0"));
		btnCheckButton_0.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_0.getSelection())
		    		  resultHashMap.put("tab0", true);
		    	  else
		    		  resultHashMap.put("tab0", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_1 = new Button(group, SWT.CHECK);
		btnCheckButton_1.setBounds(10, 53, 209, 16);
		btnCheckButton_1.setText("Tab - Monkey Test");
		btnCheckButton_1.setSelection(resultHashMap.get("tab1"));
		btnCheckButton_1.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_1.getSelection())
		    		  resultHashMap.put("tab1", true);
		    	  else
		    		  resultHashMap.put("tab1", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_2 = new Button(group, SWT.CHECK);
		btnCheckButton_2.setBounds(10, 84, 167, 16);
		btnCheckButton_2.setText("Tab - Shell Commands");
		btnCheckButton_2.setSelection(resultHashMap.get("tab2"));
		btnCheckButton_2.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_2.getSelection())
		    		  resultHashMap.put("tab2", true);
		    	  else
		    		  resultHashMap.put("tab2", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_3 = new Button(group, SWT.CHECK);
		btnCheckButton_3.setBounds(10, 115, 167, 16);
		btnCheckButton_3.setText("Tab - Profiling");
		btnCheckButton_3.setSelection(resultHashMap.get("tab3"));
		btnCheckButton_3.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_3.getSelection())
		    		  resultHashMap.put("tab3", true);
		    	  else
		    		  resultHashMap.put("tab3", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_4 = new Button(group, SWT.CHECK);
		btnCheckButton_4.setBounds(10, 147, 167, 16);
		btnCheckButton_4.setText("Tab - AdbCommands");
		btnCheckButton_4.setSelection(resultHashMap.get("tab4"));
		btnCheckButton_4.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_4.getSelection())
		    		  resultHashMap.put("tab4", true);
		    	  else
		    		  resultHashMap.put("tab4", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_5 = new Button(group, SWT.CHECK);
		btnCheckButton_5.setBounds(10, 181, 167, 16);
		btnCheckButton_5.setText("Tab - SerialComm");
		btnCheckButton_5.setSelection(resultHashMap.get("tab5"));
		btnCheckButton_5.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_5.getSelection())
		    		  resultHashMap.put("tab5", true);
		    	  else
		    		  resultHashMap.put("tab5", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_6 = new Button(group, SWT.CHECK);
		btnCheckButton_6.setBounds(10, 215, 167, 16);
		btnCheckButton_6.setText("Tab - ScreenCapture");
		btnCheckButton_6.setSelection(resultHashMap.get("tab6"));
		btnCheckButton_6.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_6.getSelection())
		    		  resultHashMap.put("tab6", true);
		    	  else
		    		  resultHashMap.put("tab6", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_7 = new Button(group, SWT.CHECK);
		btnCheckButton_7.setBounds(10, 249, 167, 16);
		btnCheckButton_7.setText("Tab - FastBoot");
		btnCheckButton_7.setSelection(resultHashMap.get("tab7"));
		btnCheckButton_7.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_7.getSelection())
		    		  resultHashMap.put("tab7", true);
		    	  else
		    		  resultHashMap.put("tab7", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		btnCheckButton_8 = new Button(group, SWT.CHECK);
		btnCheckButton_8.setBounds(10, 283, 167, 16);
		btnCheckButton_8.setText("Tab - Ramdump");
		btnCheckButton_8.setSelection(resultHashMap.get("tab8"));
		btnCheckButton_8.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  if (btnCheckButton_8.getSelection())
		    		  resultHashMap.put("tab8", true);
		    	  else
		    		  resultHashMap.put("tab8", false);
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		Button btnOKButton = new Button(shlSetup, SWT.NONE);
		GridData gd_btnNewButton = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnNewButton.widthHint = 121;
		btnOKButton.setLayoutData(gd_btnNewButton);
		btnOKButton.setText("OK");
		btnOKButton.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  resultHashMap.put("OK", true);
		    	  shlSetup.close();
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
		
		Button btnCancelButton_1 = new Button(shlSetup, SWT.NONE);
		btnCancelButton_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnCancelButton_1.setText("Cancel");
		btnCancelButton_1.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent event) {
		    	  resultHashMap.put("OK", false);
		    	  shlSetup.close();
		      }
		      public void widgetDefaultSelected(SelectionEvent event) {
		      }
		    });
	}
	
	private void initSetupValues(HashMap<String, Boolean> map)	{
		resultHashMap.put("tab0", map.get("tab0"));
		resultHashMap.put("tab1", map.get("tab1"));
		resultHashMap.put("tab2", map.get("tab2"));
		resultHashMap.put("tab3", map.get("tab3"));
		resultHashMap.put("tab4", map.get("tab4"));
		resultHashMap.put("tab5", map.get("tab5"));
		resultHashMap.put("tab6", map.get("tab6"));
		resultHashMap.put("tab7", map.get("tab7"));
		resultHashMap.put("tab8", map.get("tab8"));
		resultHashMap.put("OK", false);
	}
}
