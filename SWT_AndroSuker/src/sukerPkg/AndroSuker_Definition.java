package sukerPkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

import org.eclipse.swt.SWT;

interface AndroSuker_Definition {
	public final String VERSION						= "1.6.0";
	public final String UPDATE_DATE					= "2014-11-06";
	
    public final int MODE_DIR 						= 5500;
    public final int MODE_FILE 						= 5501;
    
    public final int FILE_TYPE_EXE					= 5600;
    public final int FILE_TYPE_NONE					= 5601;
    
	public static int EDIT_MAX_GET_STRING			= 512;

	public static int LINE_GAP						= 15;

	public final int TOTAL_SIZE_WIDTH				= 680;
	public final int TOTAL_SIZE_HEIGHT				= (440 + 20);
	public final int TOTAL_BIG_SIZE_WIDTH			= 1000;
	public final int TOTAL_BIG_SIZE_HEIGHT			= (700 + 20);
	
	public final int ADBLOG_SIZE_WIDTH				= TOTAL_SIZE_WIDTH;
	public final int ADBLOG_SIZE_HEIGHT				= (TOTAL_SIZE_HEIGHT + 10);
	
	public final int PROFILNING_SIZE_WIDTH			= 1300;
	public final int PROFILNING_SIZE_HEIGHT			= (700 + 20);
	
	public final int FIRMWARE_SIZE_HEIGHT			= (700 + 20);
	
	public final int PROFILE_CHART_SIZE_WIDTH		= 610;
	public final int PROFILE_CHART_SIZE_HEIGHT		= 400;
	
	public final int SERIALCOMM_SIZE_WIDTH			= 730;
	public final int SERIALCOMM_SIZE_HEIGHT			= (562 + 20);
	
	public final int SCREENCAPTURE_SIZE_WIDTH		= 610;
	public final int SCREENCAPTURE_SIZE_HEIGHT		= (580 + 20);
	
	public final int FASTBOOT_SIZE_WIDTH			= 730;
	public final int FASTBOOT_SIZE_HEIGHT			= (650 + 30);
	
	public final int RAMDUMPPARSER_SIZE_WIDTH		= 660;
	public final int RAMDUMPPARSER_SIZE_HEIGHT		= (450 + 35);
	
	public final int DEFAULT_POS_X					= 20;
	public final int DEFAULT_POS_Y					= 25;
	
	public final int BOLD_STYLE						= Font.BOLD;
	public final int ITALYIC_STYLE					= Font.ITALIC;
	public final int NORMAL_STYLE					= Font.PLAIN;
	
	public final int CHECK_FONT_SIZE				= 13;
	public final int TITLE_FONT_SIZE				= 12;
	public final int EDIT_FONT_SIZE					= 13;
	public final int BUTTON_FONT_SIZE				= 13;
	
	public final int LABEL_HEIGHT					 = 20;
	public final int EDIT_HEIGHT					 = 20;
	public final int BUTTON_HEIGHT					 = 25;
	
	public final Paint[] GRAPH_COLOR_IMPORTANT = {Color.red, Color.green, Color.yellow};
	public final Paint[] GRAPH_COLOR = {Color.blue, Color.lightGray, Color.cyan};
	public final Paint[] GRAPH_COLOR_CORE_USAGE = {Color.red, Color.green, Color.yellow, Color.CYAN};
	public final int[]   PANEL_COLOR_CORE_USAGE = {SWT.COLOR_RED, SWT.COLOR_GREEN, SWT.COLOR_YELLOW, SWT.COLOR_CYAN};
	
	public final int TEXT_LINE_WIDTH 				= 256;
	//------------------------------------------ Page 1 --------------------------------------------------
	public final int PAGE1_SIZE_WIDTH							= (TOTAL_SIZE_WIDTH-10);
	public final int PAGE1_SIZE_HEIGHT							= TOTAL_SIZE_HEIGHT;
	
	public final int LABEL_FILTER_OPTION_WIDTH					= 120;
	public final int LABEL_FILTER_OPTION_HEIGHT					= LABEL_HEIGHT;

	public final int EDIT_FILTER_OPTION_WIDTH					= 200;
	public final int EDIT_FILTER_OPTION_HEIGHT					= EDIT_HEIGHT;

	public final int LABEL_FILTER_RANGE_WIDTH					= 170;
	public final int LABEL_FILTER_RANGE_HEIGHT					= LABEL_HEIGHT;

	public final int EDIT_FILTER_RANGE_WIDTH					= 100;
	public final int EDIT_FILTER_RANGE_HEIGHT					= EDIT_HEIGHT;
	
	public final int LABEL_FILTER_NAME_WIDTH					= 250;
	public final int LABEL_FILTER_NAME_HEIGHT					= LABEL_HEIGHT;

	public final int EDIT_FILTER_NAME_WIDTH						= 200;
	public final int EDIT_FILTER_NAME_HEIGHT					= EDIT_HEIGHT;
	
	public final int LABEL_DIR_PATH_WIDTH						= 450;
	public final int LABEL_DIR_PATH_HEIGHT						= LABEL_HEIGHT;
	
	public final int EDIT_DIR_PATH_WIDTH						= 500;
	public final int EDIT_DIR_PATH_HEIGHT						= EDIT_HEIGHT;
	
	public final int BUTTON_FOR_DIR_PATH_WIDTH					= 50;
	public final int BUTTON_FOR_DIR_PATH_HEIGHT					= BUTTON_HEIGHT;

	public final int BUTTON_FOR_DIR_OPEN_WIDTH					= 120;
	public final int BUTTON_FOR_DIR_OPEN_HEIGHT					= BUTTON_HEIGHT;
	
	public final int LABEL_LOG_FILE_WIDTH						= 350;
	public final int LABEL_LOG_FILE_HEIGHT						= LABEL_HEIGHT;
	
	public final int EDIT_LOG_FILE_WIDTH						= 500;
	public final int EDIT_LOG_FILE_HEIGHT						= EDIT_HEIGHT;	
	//----------------------------------------------------------------------------------------------------
	
	//------------------------------------------ Page 2 --------------------------------------------------
	public final int LABEL_MONKEY_WIDTH							= 200;
	public final int LABEL_MONKEY_HEIGHT						= LABEL_HEIGHT;
	
	public final int EDIT_MONKEY_EVENT_WIDTH					= 120;
	public final int EDIT_MONKEY_EVENT_HEIGHT					= EDIT_HEIGHT;
	
	public final int EDIT_MONKEY_PKGNAME_WIDTH					= 120;
	public final int EDIT_MONKEY_PKGNAME_HEIGHT					= EDIT_HEIGHT;

	public final int EDIT_GAP_LABEL								= 40;
	public final int LABEL_GAP_COMBO							= 10;
	
	public final int COMBO_MONKEY_WIDTH							= 80;
	public final int COMBO_MONKEY_HEIGHT						= EDIT_HEIGHT;
		
	public final int BUTTON_FOR_ADB_MONKEY_WIDTH				= 200;
	public final int BUTTON_FOR_ADB_MONKEY_HEIGHT				= BUTTON_HEIGHT;
	
	public final int PAGE2_SIZE_WIDTH							= (TOTAL_SIZE_WIDTH-10);
	public final int PAGE2_SIZE_HEIGHT							= TOTAL_SIZE_HEIGHT;	
	//----------------------------------------------------------------------------------------------------
	
	//------------------------------------------ Page 3 --------------------------------------------------
	public final int PAGE3_SIZE_WIDTH							= (TOTAL_BIG_SIZE_WIDTH-10);
	public final int PAGE3_SIZE_HEIGHT							= (TOTAL_BIG_SIZE_HEIGHT-10);	
	
	//test ===============================================================================================
	public final int TEXT_FOR_SHELL_WIDTH			= 800;
	public final int TEXT_FOR_SHELL_HEIGHT			= 600;
	public final int TEXT_FOR_SHELL_START_POS_X		= TOTAL_BIG_SIZE_WIDTH - TEXT_FOR_SHELL_WIDTH - 20;
	public final int TEXT_FOR_SHELL_START_POS_Y		= DEFAULT_POS_Y;	
	//----------------------------------------------------------------------------------------------------
	public final int TRACE_EDIT_START_POS_X	= DEFAULT_POS_X;
	public final int TRACE_EDIT_START_POS_Y	= DEFAULT_POS_Y - 20;
	public final int TRACE_EDIT_WIDTH		= 500;
	public final int TRACE_EDIT_HEIGHT		= 18;
}
