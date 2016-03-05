package sukerPkg;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.RawImage;
import com.android.ddmlib.TimeoutException;

public class AndroSuker_TabItem_ScreenCapture implements AndroSuker_Definition, Observer {
	private static Composite 	ScreenCapture_composite;	
	
	private List<String> 		readList;
	private List<String> 		writeList;	

	private Text 		textWidth;
	private Text 		textHeight;
	private Text 		textFileName;
	//private int				screenWidth;
	//private int				screenHeight;
	
	private final int		ATCMD_BTN_LANDSCAPE = 500;
	private final int		ATCMD_BTN_OPENDIR = 501;
	private final int		ATCMD_BTN_CLEARSCREEN = 502;
	private final int		ATCMD_BTN_COPYTOCLIPBOARD = 503;
	private final int		ATCMD_BTN_DO = 504;

	private static Shell	thisShell;
	
	private ScreenShot		screenShot;
	private Canvas 			canvas;
	private Image 			image;
	private Image 			scaledimage;
	private	String			fileName;
	
	private String			screenShotDirName = "screenshot";
	private Clipboard 		clipboard;
	private boolean			msgVwNoti;
	
	private int				paintAreaWidth = 320;
	private int				paintAreaHeight = 480;
	
	private int				scaledWidth = paintAreaWidth;
	private int 			scaledHeight = paintAreaHeight;    
	
	public AndroSuker_TabItem_ScreenCapture(Shell shell, TabFolder tabFolder)	{
		thisShell = shell;
		createPage(tabFolder);
		screenShot = new ScreenShot(this);
		initPage();
		clipboard = new Clipboard(thisShell.getDisplay());
		
		msgVwNoti = false;
	}
	/*
	static int get_framebuffer(GGLSurface *fb) {
		int fd; void *bits;
		fd = open("/dev/graphics/fb0", O_RDWR);
		
		if(fd < 0) {
			perror("cannot open fb0"); 
			return ;
		}
	}*/

	public static Composite getInstance()	{
		return ScreenCapture_composite;
	}
	public String getCurrentClsName()	{
		return this.getClass().getName();
	}
	
	public void __onFinally()	{	
		writeList = AndroSuker_Main_Layout.getWriteFileList();
		
		AndroSuker_INIFile.writeIniFile("SCREEN_CAPTURE_WIDTH", 	textWidth.getText().trim(), writeList);
		AndroSuker_INIFile.writeIniFile("SCREEN_CAPTURE_HEIGHT", 	textHeight.getText().trim(), writeList);
		AndroSuker_INIFile.writeIniFile("SCREEN_CAPTURE_FILENAME", 	textFileName.getText().trim(), writeList);
		
		screenShot.onExit();
	}
	
	private void initPage()	{
		String resultStr = "none";
		readList = AndroSuker_Main_Layout.getReadFileList();
		
		if (readList != null){
			resultStr = AndroSuker_INIFile.readIniFile(readList, "SCREEN_CAPTURE_WIDTH");			
			if (resultStr.length() > 0)	{
				textWidth.setText(resultStr);
				//screenWidth = Integer.parseInt(resultStr);
			} else {
				textWidth.setText(String.valueOf(paintAreaWidth));
				//screenWidth = paintAreaWidth;
			}
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "SCREEN_CAPTURE_HEIGHT");			
			if (resultStr.length() > 0)	{
				textHeight.setText(resultStr);
				//screenHeight = Integer.parseInt(resultStr);
			} else {
				textHeight.setText(String.valueOf(paintAreaHeight));
				//screenHeight = paintAreaHeight;
			}
			
			resultStr = AndroSuker_INIFile.readIniFile(readList, "SCREEN_CAPTURE_FILENAME");			
			if (resultStr.length() > 0)	{
				textFileName.setText(resultStr);
			} else {
				textFileName.setText("default.png");
			}
		}
	}
	
	private void createPage(TabFolder tabFolder)	{
		//--------------------------------#########	LogCat Main Frame ##########--------------------------------
		ScreenCapture_composite = new Composite(tabFolder, SWT.FILL);
		ScreenCapture_composite.setLayout(new GridLayout(2, false));
		
		canvas = new Canvas(ScreenCapture_composite, SWT.BORDER | SWT.NO_REDRAW_RESIZE);
		GridData gd_canvas = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_canvas.widthHint = paintAreaWidth;
		gd_canvas.heightHint = paintAreaHeight;		
		canvas.setLayoutData(gd_canvas);
	    canvas.addPaintListener(new PaintListener() {
	      public void paintControl(PaintEvent e) {
	        if (image == null) {
	          e.gc.drawString("No image", 0, 0);
	        } else {
	        	int	x = 0;
	        	int y = 0;	        	
	        	x = (paintAreaWidth/2)-(scaledWidth/2);
	          //e.gc.drawImage(image, x, y);	        	
	        	e.gc.drawImage(scaledimage, x, y);	        	
	          if (msgVwNoti)	{
	        	  AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", screenShotDirName + " 폴더에 저장되었습니다.", SWT.OK);
	        	  msgVwNoti = false;
	          }
	        }
	      }
	    });
	    	
		Group group = new Group(ScreenCapture_composite, SWT.NONE);
		GridData gd_group = new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1);
		gd_group.heightHint = 263;
		group.setLayoutData(gd_group);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(10, 22, 74, 12);
		lblNewLabel.setText("Canvas ratio");
		
		textWidth = new Text(group, SWT.BORDER);
		textWidth.setBounds(86, 20, 50, 18);
		textWidth.setEnabled(false);
		
		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setBounds(139, 20, 10, 12);
		lblNewLabel_1.setText("/");
		
		textHeight = new Text(group, SWT.BORDER);
		textHeight.setBounds(150, 20, 50, 18);
		textHeight.setEnabled(false);
		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setBounds(10, 52, 61, 12);
		lblNewLabel_2.setText("FileName");
		
		textFileName = new Text(group, SWT.BORDER);
		textFileName.setBounds(86, 50, 170, 18);
		
		Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.setBounds(10, 84, 77, 22);
		btnNewButton_2.setText("OpenDir");
		btnNewButton_2.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_OPENDIR);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});	
		
		Button btnNewButton_3 = new Button(group, SWT.NONE);
		btnNewButton_3.setBounds(10, 197, 77, 22);
		btnNewButton_3.setText("ClearScreen");
		btnNewButton_3.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_CLEARSCREEN);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});	
		
		Button btnNewButton_1 = new Button(group, SWT.NONE);
		btnNewButton_1.setBounds(10, 112, 167, 22);
		btnNewButton_1.setText("Copy to ClipBoard");
		btnNewButton_1.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_COPYTOCLIPBOARD);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});	
		
		Button btnDoCapture = new Button(group, SWT.NONE);
		btnDoCapture.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnDoCapture.setFont(SWTResourceManager.getFont("굴림", 10, SWT.BOLD));
		btnDoCapture.setBounds(10, 169, 101, 22);
		btnDoCapture.setText("Do Capture");
		btnDoCapture.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_DO);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
		
		Button btnCheckButton = new Button(group, SWT.CHECK);
		btnCheckButton.setBounds(10, 241, 96, 16);
		btnCheckButton.setText("LandScape");
		btnCheckButton.setSelection(false);
		btnCheckButton.setEnabled(false);
		btnCheckButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				_actionPerformed(ATCMD_BTN_LANDSCAPE);
			}
			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
	}
	
	public void _actionPerformed(int action) {
		if (ATCMD_BTN_OPENDIR == action)	{
			File file = new File(screenShotDirName); // assuming that path is not empty
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (ATCMD_BTN_CLEARSCREEN == action)	{
			if (image != null)
				image.dispose();
			image = null;

			canvas.redraw();
		} else if (ATCMD_BTN_COPYTOCLIPBOARD == action)	{
			if (image != null) {
				ImageTransfer imageTransfer = ImageTransfer.getInstance();
				//TextTransfer textTransfer = TextTransfer.getInstance();
				clipboard.setContents(new Object[]{image.getImageData()}, new Transfer[]{imageTransfer});
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Success", "Good!", SWT.OK);
			}
		} else if (ATCMD_BTN_DO == action)	{
			//screenShot.setWidthAndHeight(screenWidth, screenHeight);
			if (textFileName.getText().trim().length() <= 0)	{
				fileName = screenShotDirName + "\\default.png";
			} else {
				fileName = screenShotDirName + "\\" + textFileName.getText().trim();
			}
			screenShot.Do_ScreenShot("-d", fileName);
			msgVwNoti = true;
		} else if (ATCMD_BTN_LANDSCAPE == action)	{
			;//
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		/*drawTask.asyncDrawingImage(thisShell, fileName, canvas, image, screenWidth, screenHeight, 200);
		drawTask.stopTimer();*/
		if (fileName != null) {
			if (image != null)
				image.dispose();
			image = null;
			try {
				image = new Image(thisShell.getDisplay(), fileName);
				
				final int orgWidth = image.getBounds().width; 
	        	final int orgHeight = image.getBounds().height; 
	        	int	nWidthRatioOfImgToPaintArea = (int)orgWidth * 1000 / paintAreaWidth;
	        	int nHeightRatioOfImgToPaintArea = (int)orgHeight * 1000 / paintAreaHeight;
	        	
	        	scaledWidth = orgWidth;
	        	scaledHeight = orgHeight; 
	        	
	        	if (nWidthRatioOfImgToPaintArea > 1000 || nHeightRatioOfImgToPaintArea > 1000)
	    		{//Need to resize the image
	    			//if (pPhnIdleVwImg->bFullView == TRUE)
	    			{
	    				if (scaledHeight > paintAreaHeight)
	    				{
	    					scaledWidth = paintAreaHeight * scaledWidth / scaledHeight;
	    					scaledHeight = paintAreaHeight;
	    				}
	    			}
	    			/*else
	    			{
	    				if (nWidthRatioOfImgToPaintArea > nHeightRatioOfImgToPaintArea)
	    				{//Make the image smaller w.r.t. width
	    					scaledWidth = paintAreaWidth;
	    					scaledHeight = scaledHeight * paintAreaWidth / scaledWidth;
	    				}
	    				else
	    				{//Make the image smaller w.r.t. height
	    					scaledWidth = scaledWidth * paintAreaHeight / scaledHeight;
	    					scaledHeight = paintAreaHeight;
	    				}
	    			}*/
	    		}
	        	scaledimage = new Image(thisShell.getDisplay(), image.getImageData().scaledTo((int)(scaledWidth),(int)(scaledHeight)));
	        	
			} catch (RuntimeException e) {
				// e.printStackTrace();
			}

			if (image == null) {
				AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "Failed to load image from file: " + fileName, SWT.OK);
				System.err.println("Failed to load image from file: " + fileName);
			}
			canvas.redraw();			
		}
	}
}

class ScreenShot {	 
	boolean device = false;
	boolean emulator = false;
	String filepath = null;
	boolean landscape = false;
	private ScreenShotDetect	observerScreenShot;
	AndroidDebugBridge 	bridge = null;
	boolean bridgeCreated = false;
	
	ScreenShot(AndroSuker_TabItem_ScreenCapture parent)	{
		observerScreenShot = new ScreenShotDetect();
		observerScreenShot.addObserver(parent);
	}

	public AndroidDebugBridge	getBridgeInstance()	{
		return bridge;
	}
	
	public void onExit()	{
		AndroidDebugBridge.disconnectBridge();
        AndroidDebugBridge.terminate();
	}
	
	public void Do_ScreenShot(String s1, String s2)	{
		String[] args = new String[]{s1, s2};
		
        if (args.length == 0) {
        	return ;//System.exit(1);
        	
        }
 
        // parse command line parameters.
        int index = 0;
        do {
            String argument = args[index++];
 
            if ("-d".equals(argument)) {
                if (emulator) {
                    printAndExit("-d conflicts with -e and -s", false /* terminate */);
                }
                device = true;
            } else if ("-l".equals(argument)) {
                landscape = true;
            } else {
                // get the filepath and break.
                filepath = argument;
                
                // should not be any other device.
                if (index < args.length) {
                    printAndExit("Too many arguments!", false /* terminate */);
                }
            }
        } while (index < args.length);
 
        if (filepath == null) {
        	return ;//System.exit(1);
        }
        
        // init the lib
        // [try to] ensure ADB is running
        String adbLocation = System.getProperty("com.android.screenshot.bindir"); //$NON-NLS-1$
        if (adbLocation != null && adbLocation.length() != 0) {
            adbLocation += File.separator + "adb"; //$NON-NLS-1$
        } else {
            adbLocation = "adb"; //$NON-NLS-1$
        }
 
        if (bridgeCreated == false)
        	AndroidDebugBridge.init(false /* debugger support */);
 
        try {
        	if (bridgeCreated == false)
        		bridge = AndroidDebugBridge.createBridge(adbLocation, true /* forceNewBridge */);
            
        	if (bridge != null && bridgeCreated == false)	{
        		bridgeCreated = true;
        	}
        	
            // we can't just ask for the device list right away, as the internal thread getting
            // them from ADB may not be done getting the first list.
            // Since we don't really want getDevices() to be blocking, we wait here manually.
            int count = 0;
            while (bridge.hasInitialDeviceList() == false) {
                try {
                    Thread.sleep(100);
                    count++;
                } catch (InterruptedException e) {
                    // pass
                }
 
                // let's not wait > 10 sec.
                if (count > 100) {
                	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", "Timeout getting device list!", SWT.OK);
                    System.err.println("Timeout getting device list!");
                    return;
                }
            }
 
            // now get the devices
            IDevice[] devices = bridge.getDevices();
 
            if (devices.length == 0) {
                printAndExit("No devices found!", true /* terminate */);
            }
 
            IDevice target = null;
 
            if (emulator || device) {
                for (IDevice d : devices) {
                    // this test works because emulator and device can't both be true at the same
                    // time.
                    if (d.isEmulator() == emulator) {
                        // if we already found a valid target, we print an error and return.
                        if (target != null) {
                            if (emulator) {
                                printAndExit("Error: more than one emulator launched!",
                                        true /* terminate */);
                            } else {
                                printAndExit("Error: more than one device connected!", true /* terminate */);
                            }
                        }
                        target = d;
                    }
                }
            } else {
                if (devices.length > 1) {
                    printAndExit("Error: more than one emulator or device available!", true /* terminate */);
                }
                target = devices[0];
            }
 
            if (target != null) {
                try {
                	if (AndroSuker_Debug.DEBUG_MODE_ON)
                		System.out.println("Taking screenshot from: " + target.getSerialNumber());
                    
                	getDeviceImage(target, filepath, landscape);
                    
                    if (AndroSuker_Debug.DEBUG_MODE_ON)
                    	System.out.println("Success.");
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                printAndExit("Could not find matching device/emulator.", true /* terminate */);
            }
        } finally {
        	//AndroidDebugBridge.disconnectBridge();
           // AndroidDebugBridge.terminate();
            observerScreenShot.action();
        }
    }
 
    /*
     * Grab an image from an ADB-connected device.
     */
    private static void getDeviceImage(IDevice device, String filepath, boolean landscape) throws IOException {
        RawImage rawImage = null;
 
        try {
            rawImage = device.getScreenshot();
        }
        catch (IOException ioe) {
            printAndExit("Unable to get frame buffer: " + ioe.getMessage(), true /* terminate */);
            return;
        } catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AdbCommandRejectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        // device/adb not available?
        if (rawImage == null)
            return;
 
        if (landscape) {
            rawImage = rawImage.getRotated();
        }
 
        // convert raw data to an Image
        BufferedImage image = new BufferedImage(rawImage.width, rawImage.height, BufferedImage.TYPE_INT_ARGB);
 
        int index = 0;
        int IndexInc = rawImage.bpp >> 3;
        for (int y = 0 ; y < rawImage.height ; y++) {
            for (int x = 0 ; x < rawImage.width ; x++) {
                int value = rawImage.getARGB(index);
                index += IndexInc;
                image.setRGB(x, y, value);
            }
        }
 
        if (!ImageIO.write(image, "png", new File(filepath))) {
            throw new IOException("Failed to find png writer");
        }
    }
  
    private static void printAndExit(String message, boolean terminate) {
    	AsyncMsgBoxDraw.MessageBoxDraw(AndroSuker_MainCls.getShellInstance(), "Error", message, SWT.OK);
        if (terminate) {        	
        	AndroidDebugBridge.disconnectBridge();
            AndroidDebugBridge.terminate();
        }
    }
}

class ScreenShotDetect extends Observable	{
	public void action()	{
		setChanged();
		notifyObservers();
	}
}

/*
class drawTask	{
	final static Timer timer = new Timer();
	static Shell			thisShell;	
	static Canvas 			cv;
	static long				delay;
	static int 	screenWidth = 0;
	static int 	screenHeight = 0;
	static Image image;
	static String	fileName;
	
	public static void asyncDrawingImage(Shell shell, String f, Canvas canvas, Image img, int width, int height, long d)	{
		thisShell = shell;
		cv = canvas;
		image = img;
		delay = d;
		screenWidth = width;
		screenHeight = height;
		fileName = f;
		
		TimerTask asyncLogTextTimer = new TimerTask() {							 
		    public void run() {
		    	if (thisShell.isDisposed())
		    		return ;
		    	
		    	thisShell.getDisplay().asyncExec(new Runnable() {
		            public void run() {		    	
				    	
		            	if (fileName != null) {
		                    if (image != null)
		                      image.dispose();
		                    image = null;
		                    try {
		                      image = new Image(thisShell.getDisplay(), fileName);
		                    } catch (RuntimeException e) {
		                      // e.printStackTrace();
		                    }

		                    if (image == null) {		                     
		                      System.err.println(
		                        "Failed to load image from file: " + fileName);
		                    }
		                    cv.redraw();
		                  }
		            	
		            }
		        });
		    }
		};
		timer.schedule(asyncLogTextTimer, d);
	}	
	
	public static void stopTimer()	{
		if (timer != null)	{
			timer.cancel();
			timer.purge();
		}
	}
}*/