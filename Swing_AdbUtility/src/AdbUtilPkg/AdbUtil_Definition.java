package AdbUtilPkg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;

public class AdbUtil_Definition {
	public static int EDIT_MAX_GET_STRING			= 512;

	public static int LINE_GAP						= 15;

	public final int TOTAL_SIZE_WIDTH				= 680;
	public final int TOTAL_SIZE_HEIGHT				= 440;
	public final int TOTAL_BIG_SIZE_WIDTH			= 1000;
	public final int TOTAL_BIG_SIZE_HEIGHT			= 700;
	public final int TOTAL_BIGBIG_SIZE_WIDTH		= 1100;
	public final int TOTAL_BIGBIG_SIZE_HEIGHT		= 630;
	
	public final int PROFILE_CHART_SIZE_WIDTH		= 610;
	public final int PROFILE_CHART_SIZE_HEIGHT		= 400;
	
	public final int DEFAULT_POS_X					= 20;
	public final int DEFAULT_POS_Y					= 25;
	
	public final int BOLD_STYLE						= Font.BOLD;
	public final int ITALYIC_STYLE					= Font.ITALIC;
	public final int NORMAL_STYLE					= Font.PLAIN;
	
	public final int CHECK_FONT_SIZE				= 13;
	public final int TITLE_FONT_SIZE				= 12;
	public final int EDIT_FONT_SIZE					= 13;
	public final int BUTTON_FONT_SIZE				= 13;
	
	public final int LABEL_HEIGHT					 = 25;
	public final int EDIT_HEIGHT					 = 25;
	public final int BUTTON_HEIGHT					 = 25;
	
	public final Paint[] GRAPH_COLOR_IMPORTANT = {Color.red, Color.green, Color.yellow};
	public final Paint[] GRAPH_COLOR = {Color.blue, Color.lightGray, Color.cyan};
	//------------------------------------------ Page 1 --------------------------------------------------
	public final int LABEL1_START_POS_X				= DEFAULT_POS_X;
	public final int LABEL1_START_POS_Y				= DEFAULT_POS_Y;
	public final int LABEL1_WIDTH					= 120;
	public final int LABEL1_HEIGHT					= LABEL_HEIGHT;
	
	public final int EDIT1_START_POS_X				= DEFAULT_POS_X;
	public final int EDIT1_START_POS_Y				= LABEL1_START_POS_Y + LABEL1_HEIGHT + 5;
	public final int EDIT1_WIDTH					= 200;
	public final int EDIT1_HEIGHT					= EDIT_HEIGHT;
	
		public final int LABEL1_1_START_POS_X			= EDIT1_START_POS_X + EDIT1_WIDTH + 10;
		public final int LABEL1_1_START_POS_Y			= LABEL1_START_POS_Y;
		public final int LABEL1_1_WIDTH					= 170;
		public final int LABEL1_1_HEIGHT				= LABEL_HEIGHT;

		public final int EDIT1_1_START_POS_X			= EDIT1_START_POS_X + EDIT1_WIDTH + 10;
		public final int EDIT1_1_START_POS_Y			= EDIT1_START_POS_Y;
		public final int EDIT1_1_WIDTH					= 100;
		public final int EDIT1_1_HEIGHT					= EDIT_HEIGHT;
	
		public final int LABEL1_2_START_POS_X			= EDIT1_1_START_POS_X + LABEL1_1_WIDTH + 10;
		public final int LABEL1_2_START_POS_Y			= LABEL1_START_POS_Y;
		public final int LABEL1_2_WIDTH					= 220;
		public final int LABEL1_2_HEIGHT				= LABEL_HEIGHT;

		public final int EDIT1_2_START_POS_X			= EDIT1_1_START_POS_X + LABEL1_1_WIDTH + 10;
		public final int EDIT1_2_START_POS_Y			= EDIT1_START_POS_Y;
		public final int EDIT1_2_WIDTH					= 220;
		public final int EDIT1_2_HEIGHT					= EDIT_HEIGHT;

		
	public final int LABEL2_START_POS_X				= DEFAULT_POS_X;
	public final int LABEL2_START_POS_Y				= EDIT1_START_POS_Y + EDIT1_HEIGHT + LINE_GAP;
	public final int LABEL2_WIDTH					= 450;
	public final int LABEL2_HEIGHT					= LABEL_HEIGHT;
	
	public final int EDIT2_START_POS_X				= DEFAULT_POS_X;
	public final int EDIT2_START_POS_Y				= LABEL2_START_POS_Y + LABEL2_HEIGHT + 5;
	public final int EDIT2_WIDTH					= 520;
	public final int EDIT2_HEIGHT					= EDIT_HEIGHT;

	public final int LABEL3_START_POS_X				= DEFAULT_POS_X;
	public final int LABEL3_START_POS_Y				= EDIT2_START_POS_Y + EDIT2_HEIGHT + LINE_GAP;
	public final int LABEL3_WIDTH					= 350;
	public final int LABEL3_HEIGHT					= LABEL_HEIGHT;
	
	public final int EDIT3_START_POS_X				= DEFAULT_POS_X;
	public final int EDIT3_START_POS_Y				= LABEL3_START_POS_Y + LABEL3_HEIGHT + 5;
	public final int EDIT3_WIDTH					= EDIT2_WIDTH;
	public final int EDIT3_HEIGHT					= EDIT_HEIGHT;

	public final int EDIT_BUTTON_BETWEEN_GAP		= 35;

	public final int BUTTON_FOR_EDIT1_START_POS_X	= EDIT1_START_POS_X + EDIT1_WIDTH + EDIT_BUTTON_BETWEEN_GAP;
	public final int BUTTON_FOR_EDIT1_START_POS_Y	= EDIT1_START_POS_Y;
	public final int BUTTON_FOR_EDIT1_WIDTH			= 50;
	public final int BUTTON_FOR_EDIT1_HEIGHT		= BUTTON_HEIGHT;

	public final int BUTTON_FOR_DIR_PATH_START_POS_X	= EDIT2_START_POS_X + EDIT2_WIDTH + 5;
	public final int BUTTON_FOR_DIR_PATH_START_POS_Y	= EDIT2_START_POS_Y;
	public final int BUTTON_FOR_DIR_PATH_WIDTH			= BUTTON_FOR_EDIT1_WIDTH;
	public final int BUTTON_FOR_DIR_PATH_HEIGHT			= BUTTON_HEIGHT;

	public final int BUTTON_FOR_DIR_OPEN_START_POS_X	= BUTTON_FOR_DIR_PATH_START_POS_X;
	public final int BUTTON_FOR_DIR_OPEN_START_POS_Y	= BUTTON_FOR_DIR_PATH_START_POS_Y + BUTTON_FOR_DIR_PATH_HEIGHT + 5;
	public final int BUTTON_FOR_DIR_OPEN_WIDTH			= 120;
	public final int BUTTON_FOR_DIR_OPEN_HEIGHT			= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_EDIT3_START_POS_X	= EDIT3_START_POS_X + EDIT3_WIDTH + EDIT_BUTTON_BETWEEN_GAP;
	public final int BUTTON_FOR_EDIT3_START_POS_Y	= EDIT3_START_POS_Y;
	public final int BUTTON_FOR_EDIT3_WIDTH			= BUTTON_FOR_EDIT1_WIDTH;
	public final int BUTTON_FOR_EDIT3_HEIGHT		= BUTTON_HEIGHT;

	//Radio Button1 -----------------------------------------------------------------------------------------
	public final int RADIO_BTN1_START_POS_X			= DEFAULT_POS_X;
	public final int RADIO_BTN1_START_POS_Y			= EDIT3_START_POS_Y + EDIT3_HEIGHT + LINE_GAP;
	public final int RADIO_BTN1_WIDTH				= 150;
	public final int RADIO_BTN1_HEIGHT				= 20;
	//Radio Button2 -----------------------------------------------------------------------------------------
	public final int RADIO_BTN2_START_POS_X			= DEFAULT_POS_X;
	public final int RADIO_BTN2_START_POS_Y			= RADIO_BTN1_START_POS_Y + RADIO_BTN1_HEIGHT + 5;
	public final int RADIO_BTN2_WIDTH				= 150;
	public final int RADIO_BTN2_HEIGHT				= 20;
	//Radio Button3 -----------------------------------------------------------------------------------------
	public final int RADIO_BTN3_START_POS_X			= DEFAULT_POS_X;
	public final int RADIO_BTN3_START_POS_Y			= RADIO_BTN2_START_POS_Y + RADIO_BTN2_HEIGHT + 5;
	public final int RADIO_BTN3_WIDTH				= 150;
	public final int RADIO_BTN3_HEIGHT				= 20;
	//Radio Button4 -----------------------------------------------------------------------------------------
	public final int RADIO_BTN4_START_POS_X			= DEFAULT_POS_X;
	public final int RADIO_BTN4_START_POS_Y			= RADIO_BTN3_START_POS_Y + RADIO_BTN2_HEIGHT + 5;
	public final int RADIO_BTN4_WIDTH				= 150;
	public final int RADIO_BTN4_HEIGHT				= 20;
		
	//CheckBox1 ---------------------------------------------------------------------------------------------
	public final int CHECKBOX1_START_POS_X			= RADIO_BTN1_START_POS_X + RADIO_BTN1_WIDTH + 10;
	public final int CHECKBOX1_START_POS_Y			= RADIO_BTN1_START_POS_Y;
	public final int CHECKBOX1_WIDTH				= 150;
	public final int CHECKBOX1_HEIGHT				= 20;
	//CheckBox2 ---------------------------------------------------------------------------------------------
	public final int CHECKBOX2_START_POS_X			= CHECKBOX1_START_POS_X;
	public final int CHECKBOX2_START_POS_Y			= CHECKBOX1_START_POS_Y + CHECKBOX1_HEIGHT + 10;
	public final int CHECKBOX2_WIDTH				= 250;
	public final int CHECKBOX2_HEIGHT				= 20;
	//-------------------------------------------------------------------------------------------------------
	
	public final int BUTTON_FOR_INIT_START_POS_X	= CHECKBOX2_START_POS_X + CHECKBOX2_WIDTH;
	public final int BUTTON_FOR_INIT_START_POS_Y	= CHECKBOX1_START_POS_Y;
	public final int BUTTON_FOR_INIT_WIDTH			= 110;
	public final int BUTTON_FOR_INIT_HEIGHT			= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_RUN_START_POS_X		= BUTTON_FOR_INIT_START_POS_X + BUTTON_FOR_INIT_WIDTH + 8;
	public final int BUTTON_FOR_RUN_START_POS_Y		= BUTTON_FOR_INIT_START_POS_Y;
	public final int BUTTON_FOR_RUN_WIDTH			= 100;
	public final int BUTTON_FOR_RUN_HEIGHT			= BUTTON_HEIGHT;
	//----------------------------------------------------------------------------------------------------
	
	//------------------------------------------ Page 2 --------------------------------------------------
	public final int BUTTON_FOR_ADB_REMOUNT_START_POS_X		= DEFAULT_POS_X;
	public final int BUTTON_FOR_ADB_REMOUNT_START_POS_Y		= DEFAULT_POS_Y;
	public final int BUTTON_FOR_ADB_REMOUNT_WIDTH			= 180;
	public final int BUTTON_FOR_ADB_REMOUNT_HEIGHT			= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_ADB_STARTSERVER_START_POS_X		= DEFAULT_POS_X + BUTTON_FOR_ADB_REMOUNT_WIDTH + 15;
	public final int BUTTON_FOR_ADB_STARTSERVER_START_POS_Y		= BUTTON_FOR_ADB_REMOUNT_START_POS_Y;
	public final int BUTTON_FOR_ADB_STARTSERVER_WIDTH			= 180;
	public final int BUTTON_FOR_ADB_STARTSERVER_HEIGHT			= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_ADB_REBOOT_START_POS_X		= BUTTON_FOR_ADB_STARTSERVER_START_POS_X + BUTTON_FOR_ADB_STARTSERVER_WIDTH + 20;
	public final int BUTTON_FOR_ADB_REBOOT_START_POS_Y		= DEFAULT_POS_Y - 10;
	public final int BUTTON_FOR_ADB_REBOOT_WIDTH			= 220;
	public final int BUTTON_FOR_ADB_REBOOT_HEIGHT			= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_ADB_DEVICES_START_POS_X		= BUTTON_FOR_ADB_REBOOT_START_POS_X;
	public final int BUTTON_FOR_ADB_DEVICES_START_POS_Y		= BUTTON_FOR_ADB_REBOOT_START_POS_Y + BUTTON_FOR_ADB_REBOOT_HEIGHT + 3;
	public final int BUTTON_FOR_ADB_DEVICES_WIDTH			= BUTTON_FOR_ADB_REBOOT_WIDTH;
	public final int BUTTON_FOR_ADB_DEVICES_HEIGHT			= BUTTON_HEIGHT;

	//Remove package =========================================================================================
	public final int LABEL2_1_REMOVE_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL2_1_REMOVE_START_POS_Y	= BUTTON_FOR_ADB_DEVICES_START_POS_Y + BUTTON_FOR_ADB_DEVICES_HEIGHT + LINE_GAP;
	public final int LABEL2_1_REMOVE_WIDTH			= 250;
	public final int LABEL2_1_REMOVE_HEIGHT			= LABEL_HEIGHT;
	
	public final int EDIT2_1_REMOVE_START_POS_X		= DEFAULT_POS_X;
	public final int EDIT2_1_REMOVE_START_POS_Y		= LABEL2_1_REMOVE_START_POS_Y + LABEL2_1_REMOVE_HEIGHT + 3;
	public final int EDIT2_1_REMOVE_WIDTH			= 200;
	public final int EDIT2_1_REMOVE_HEIGHT			= EDIT_HEIGHT;
	
	public final int BUTTON_FOR_ADB_REMOVEPACKAGE_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_ADB_REMOVEPACKAGE_START_POS_Y	= EDIT2_1_REMOVE_START_POS_Y + EDIT2_1_REMOVE_HEIGHT + 5;
	public final int BUTTON_FOR_ADB_REMOVEPACKAGE_WIDTH			= 150;
	public final int BUTTON_FOR_ADB_REMOVEPACKAGE_HEIGHT		= BUTTON_HEIGHT;
	//reinstall package ======================================================================================
	public final int LABEL2_1_REINSTALL_START_POS_X		= DEFAULT_POS_X + LABEL2_1_REMOVE_WIDTH + 5;
	public final int LABEL2_1_REINSTALL_START_POS_Y		= LABEL2_1_REMOVE_START_POS_Y;
	public final int LABEL2_1_REINSTALL_WIDTH			= 300;
	public final int LABEL2_1_REINSTALL_HEIGHT			= LABEL_HEIGHT;
	
	public final int EDIT2_1_REINSTALL_START_POS_X		= LABEL2_1_REINSTALL_START_POS_X;
	public final int EDIT2_1_REINSTALL_START_POS_Y		= LABEL2_1_REINSTALL_START_POS_Y + LABEL2_1_REINSTALL_HEIGHT + 3;
	public final int EDIT2_1_REINSTALL_WIDTH			= 350;
	public final int EDIT2_1_REINSTALL_HEIGHT			= EDIT_HEIGHT;
	
	public final int BUTTON_FOR_ADB_REINSTALLPATH_START_POS_X	= LABEL2_1_REINSTALL_START_POS_X;
	public final int BUTTON_FOR_ADB_REINSTALLPATH_START_POS_Y	= EDIT2_1_REINSTALL_START_POS_Y + EDIT2_1_REINSTALL_HEIGHT + 5;;
	public final int BUTTON_FOR_ADB_REINSTALLPATH_WIDTH			= 50;
	public final int BUTTON_FOR_ADB_REINSTALLPATH_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_ADB_REINSTALL_START_POS_X	= BUTTON_FOR_ADB_REINSTALLPATH_START_POS_X + BUTTON_FOR_ADB_REINSTALLPATH_WIDTH + 5;
	public final int BUTTON_FOR_ADB_REINSTALL_START_POS_Y	= EDIT2_1_REINSTALL_START_POS_Y + EDIT2_1_REINSTALL_HEIGHT + 5;
	public final int BUTTON_FOR_ADB_REINSTALL_WIDTH			= 150;
	public final int BUTTON_FOR_ADB_REINSTALL_HEIGHT		= BUTTON_HEIGHT;
	
	public final int CHECKBOX_FOR_ADB_REINSTALL_START_POS_X	= BUTTON_FOR_ADB_REINSTALL_START_POS_X + BUTTON_FOR_ADB_REINSTALL_WIDTH + 5;
	public final int CHECKBOX_FOR_ADB_REINSTALL_START_POS_Y	= BUTTON_FOR_ADB_REINSTALL_START_POS_Y;
	public final int CHECKBOX_FOR_ADB_REINSTALL_WIDTH		= 80;
	public final int CHECKBOX_FOR_ADB_REINSTALL_HEIGHT		= BUTTON_HEIGHT;
	//========================================================================================================
	//========================================================================================================
		
	//uninstall package ======================================================================================
	public final int LABEL2_1_UNINSTALL_START_POS_X		= DEFAULT_POS_X;
	public final int LABEL2_1_UNINSTALL_START_POS_Y		= BUTTON_FOR_ADB_REMOVEPACKAGE_START_POS_Y + BUTTON_FOR_ADB_REMOVEPACKAGE_HEIGHT + LINE_GAP + 10;
	public final int LABEL2_1_UNINSTALL_WIDTH			= 300;
	public final int LABEL2_1_UNINSTALL_HEIGHT			= LABEL_HEIGHT;
	
	public final int EDIT2_2_UNINSTALL_START_POS_X		= DEFAULT_POS_X;
	public final int EDIT2_2_UNINSTALL_START_POS_Y		= LABEL2_1_UNINSTALL_START_POS_Y + LABEL2_1_UNINSTALL_HEIGHT + 3;
	public final int EDIT2_2_UNINSTALL_WIDTH			= 200;
	public final int EDIT2_2_UNINSTALL_HEIGHT			= EDIT_HEIGHT;
	
	public final int BUTTON_FOR_ADB_UNINSTALL_START_POS_X	= DEFAULT_POS_X + EDIT2_2_UNINSTALL_WIDTH + 10;
	public final int BUTTON_FOR_ADB_UNINSTALL_START_POS_Y	= EDIT2_2_UNINSTALL_START_POS_Y;
	public final int BUTTON_FOR_ADB_UNINSTALL_WIDTH			= 150;
	public final int BUTTON_FOR_ADB_UNINSTALL_HEIGHT		= BUTTON_HEIGHT;
	
	//user package install ===================================================================================
	public final int BUTTON_FOR_ADB_DOWNPACK_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_ADB_DOWNPACK_START_POS_Y	= BUTTON_FOR_ADB_UNINSTALL_START_POS_Y +BUTTON_FOR_ADB_UNINSTALL_HEIGHT + 10;
	public final int BUTTON_FOR_ADB_DOWNPACK_WIDTH			= 200;
	public final int BUTTON_FOR_ADB_DOWNPACK_HEIGHT			= BUTTON_HEIGHT;
	
	public final int LOG4_APK_TOOL_START_POS_X			= 5;
	public final int LOG4_APK_TOOL_START_POS_Y			= BUTTON_FOR_ADB_DOWNPACK_START_POS_Y + BUTTON_FOR_ADB_DOWNPACK_HEIGHT + 10;
	public final int LOG4_APK_TOOL_WIDTH				= TOTAL_SIZE_WIDTH - 20;
	public final int LOG4_APK_TOOL_HEIGHT				= 100;
	//========================================================================================================

	//------------------------------------------ Page 3 --------------------------------------------------
	//monkey edit ======================================================================================
	public final int LABEL3_1_MONKEY_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL3_1_MONKEY_START_POS_Y	= DEFAULT_POS_Y + LABEL_HEIGHT + 5;
	public final int LABEL3_1_MONKEY_WIDTH			= 130;
	public final int LABEL3_1_MONKEY_HEIGHT			= LABEL_HEIGHT;
	public final int EDIT3_1_MONKEY_START_POS_X		= LABEL3_1_MONKEY_START_POS_X + LABEL3_1_MONKEY_WIDTH + 5;
	public final int EDIT3_1_MONKEY_START_POS_Y		= LABEL3_1_MONKEY_START_POS_Y;
	public final int EDIT3_1_MONKEY_WIDTH			= 80;
	public final int EDIT3_1_MONKEY_HEIGHT			= EDIT_HEIGHT;

	public final int LABEL3_0_MONKEY_START_POS_X	= EDIT3_1_MONKEY_START_POS_X;
	public final int LABEL3_0_MONKEY_START_POS_Y	= DEFAULT_POS_Y;
	public final int LABEL3_0_MONKEY_WIDTH			= 130;
	public final int LABEL3_0_MONKEY_HEIGHT			= LABEL_HEIGHT;
	
	public final int LABEL3_2_MONKEY_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL3_2_MONKEY_START_POS_Y	= LABEL3_1_MONKEY_START_POS_Y + LABEL3_1_MONKEY_HEIGHT + 5;
	public final int LABEL3_2_MONKEY_WIDTH			= 130;
	public final int LABEL3_2_MONKEY_HEIGHT			= LABEL_HEIGHT;
	public final int EDIT3_2_MONKEY_START_POS_X		= DEFAULT_POS_X + LABEL3_2_MONKEY_WIDTH + 5;
	public final int EDIT3_2_MONKEY_START_POS_Y		= LABEL3_2_MONKEY_START_POS_Y;
	public final int EDIT3_2_MONKEY_WIDTH			= 80;
	public final int EDIT3_2_MONKEY_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_3_MONKEY_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL3_3_MONKEY_START_POS_Y	= LABEL3_2_MONKEY_START_POS_Y + LABEL3_2_MONKEY_HEIGHT + 5;
	public final int LABEL3_3_MONKEY_WIDTH			= 130;
	public final int LABEL3_3_MONKEY_HEIGHT			= LABEL_HEIGHT;
	public final int EDIT3_3_MONKEY_START_POS_X		= DEFAULT_POS_X + LABEL3_3_MONKEY_WIDTH + 5;
	public final int EDIT3_3_MONKEY_START_POS_Y		= LABEL3_3_MONKEY_START_POS_Y;
	public final int EDIT3_3_MONKEY_WIDTH			= 80;
	public final int EDIT3_3_MONKEY_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_4_MONKEY_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL3_4_MONKEY_START_POS_Y	= LABEL3_3_MONKEY_START_POS_Y + LABEL3_3_MONKEY_HEIGHT + 5;
	public final int LABEL3_4_MONKEY_WIDTH			= 130;
	public final int LABEL3_4_MONKEY_HEIGHT			= LABEL_HEIGHT;
	public final int EDIT3_4_MONKEY_START_POS_X		= DEFAULT_POS_X + LABEL3_4_MONKEY_WIDTH + 5;
	public final int EDIT3_4_MONKEY_START_POS_Y		= LABEL3_4_MONKEY_START_POS_Y;
	public final int EDIT3_4_MONKEY_WIDTH			= 150;
	public final int EDIT3_4_MONKEY_HEIGHT			= EDIT_HEIGHT;
	
	//money pct ---------------------------------------------------------------------------------------
	public final int LABEL3_0_MONKEY_PCT_START_POS_X	= EDIT3_4_MONKEY_START_POS_X + EDIT3_4_MONKEY_WIDTH + 100;
	public final int LABEL3_0_MONKEY_PCT_START_POS_Y	= DEFAULT_POS_Y;
	public final int LABEL3_0_MONKEY_PCT_WIDTH			= 200;
	public final int LABEL3_0_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	
	public final int LABEL3_1_MONKEY_PCT_START_POS_X	= EDIT3_4_MONKEY_START_POS_X + EDIT3_4_MONKEY_WIDTH + 30;
	public final int LABEL3_1_MONKEY_PCT_START_POS_Y	= LABEL3_0_MONKEY_PCT_START_POS_Y + LABEL3_0_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_1_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_1_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_1_MONKEY_PCT_START_POS_X	= LABEL3_1_MONKEY_PCT_START_POS_X + LABEL3_1_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_1_MONKEY_PCT_START_POS_Y	= LABEL3_1_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_1_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_1_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_2_MONKEY_PCT_START_POS_X	= LABEL3_1_MONKEY_PCT_START_POS_X;
	public final int LABEL3_2_MONKEY_PCT_START_POS_Y	= LABEL3_1_MONKEY_PCT_START_POS_Y + COMBO3_1_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_2_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_2_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_2_MONKEY_PCT_START_POS_X	= LABEL3_2_MONKEY_PCT_START_POS_X + LABEL3_2_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_2_MONKEY_PCT_START_POS_Y	= LABEL3_2_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_2_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_2_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_3_MONKEY_PCT_START_POS_X	= LABEL3_2_MONKEY_PCT_START_POS_X;
	public final int LABEL3_3_MONKEY_PCT_START_POS_Y	= LABEL3_2_MONKEY_PCT_START_POS_Y + COMBO3_2_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_3_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_3_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_3_MONKEY_PCT_START_POS_X	= LABEL3_3_MONKEY_PCT_START_POS_X + LABEL3_3_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_3_MONKEY_PCT_START_POS_Y	= LABEL3_3_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_3_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_3_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_4_MONKEY_PCT_START_POS_X	= LABEL3_3_MONKEY_PCT_START_POS_X;
	public final int LABEL3_4_MONKEY_PCT_START_POS_Y	= LABEL3_3_MONKEY_PCT_START_POS_Y + COMBO3_3_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_4_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_4_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_4_MONKEY_PCT_START_POS_X	= LABEL3_4_MONKEY_PCT_START_POS_X + LABEL3_4_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_4_MONKEY_PCT_START_POS_Y	= LABEL3_4_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_4_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_4_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_5_MONKEY_PCT_START_POS_X	= LABEL3_4_MONKEY_PCT_START_POS_X;
	public final int LABEL3_5_MONKEY_PCT_START_POS_Y	= LABEL3_4_MONKEY_PCT_START_POS_Y + COMBO3_4_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_5_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_5_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_5_MONKEY_PCT_START_POS_X	= LABEL3_5_MONKEY_PCT_START_POS_X + LABEL3_5_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_5_MONKEY_PCT_START_POS_Y	= LABEL3_5_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_5_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_5_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	
	public final int LABEL3_6_MONKEY_PCT_START_POS_X	= LABEL3_5_MONKEY_PCT_START_POS_X;
	public final int LABEL3_6_MONKEY_PCT_START_POS_Y	= LABEL3_5_MONKEY_PCT_START_POS_Y + COMBO3_5_MONKEY_PCT_HEIGHT + 5;
	public final int LABEL3_6_MONKEY_PCT_WIDTH			= 150;
	public final int LABEL3_6_MONKEY_PCT_HEIGHT			= LABEL_HEIGHT;
	public final int COMBO3_6_MONKEY_PCT_START_POS_X	= LABEL3_6_MONKEY_PCT_START_POS_X + LABEL3_6_MONKEY_PCT_WIDTH + 5;
	public final int COMBO3_6_MONKEY_PCT_START_POS_Y	= LABEL3_6_MONKEY_PCT_START_POS_Y;
	public final int COMBO3_6_MONKEY_PCT_WIDTH			= 150;
	public final int COMBO3_6_MONKEY_PCT_HEIGHT			= EDIT_HEIGHT;
	//monkey test Button =================================================================================
	public final int BUTTON_FOR_ADB_MONKEY_START_POS_X		= DEFAULT_POS_X;
	public final int BUTTON_FOR_ADB_MONKEY_START_POS_Y		= LABEL3_6_MONKEY_PCT_START_POS_Y + 2;
	public final int BUTTON_FOR_ADB_MONKEY_WIDTH			= 200;
	public final int BUTTON_FOR_ADB_MONKEY_HEIGHT			= BUTTON_HEIGHT;
	//----------------------------------------------------------------------------------------------------
	
	//------------------------------------------ Page 4 --------------------------------------------------
	//====================================================================================================
	public final int BUTTON_FOR_SHELL_KERNELV_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_KERNELV_START_POS_Y	= DEFAULT_POS_Y;
	public final int BUTTON_FOR_SHELL_KERNELV_WIDTH			= 250;
	public final int BUTTON_FOR_SHELL_KERNELV_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_PROCINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_PROCINFO_START_POS_Y	= BUTTON_FOR_SHELL_KERNELV_START_POS_Y + BUTTON_FOR_SHELL_KERNELV_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_PROCINFO_WIDTH		= 250;
	public final int BUTTON_FOR_SHELL_PROCINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_MEMINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_MEMINFO_START_POS_Y	= BUTTON_FOR_SHELL_PROCINFO_START_POS_Y + BUTTON_FOR_SHELL_PROCINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_MEMINFO_WIDTH			= 250;
	public final int BUTTON_FOR_SHELL_MEMINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_HARDDISK_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_HARDDISK_START_POS_Y	= BUTTON_FOR_SHELL_MEMINFO_START_POS_Y + BUTTON_FOR_SHELL_MEMINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_HARDDISK_WIDTH		= 250;
	public final int BUTTON_FOR_SHELL_HARDDISK_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_BOOTMSG_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_BOOTMSG_START_POS_Y	= BUTTON_FOR_SHELL_HARDDISK_START_POS_Y + BUTTON_FOR_SHELL_HARDDISK_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_BOOTMSG_WIDTH			= 250;
	public final int BUTTON_FOR_SHELL_BOOTMSG_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_RUNNINGPROCINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_RUNNINGPROCINFO_START_POS_Y	= BUTTON_FOR_SHELL_BOOTMSG_START_POS_Y + BUTTON_FOR_SHELL_BOOTMSG_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_RUNNINGPROCINFO_WIDTH			= 250;
	public final int BUTTON_FOR_SHELL_RUNNINGPROCINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_ENVSETUPINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_ENVSETUPINFO_START_POS_Y	= BUTTON_FOR_SHELL_RUNNINGPROCINFO_START_POS_Y + BUTTON_FOR_SHELL_RUNNINGPROCINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_ENVSETUPINFO_WIDTH		= 250;
	public final int BUTTON_FOR_SHELL_ENVSETUPINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_MEMPROCSINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_MEMPROCSINFO_START_POS_Y	= BUTTON_FOR_SHELL_ENVSETUPINFO_START_POS_Y + BUTTON_FOR_SHELL_ENVSETUPINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_MEMPROCSINFO_WIDTH		= 250;
	public final int BUTTON_FOR_SHELL_MEMPROCSINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_PROCMEMDBINFO_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_PROCMEMDBINFO_START_POS_Y	= BUTTON_FOR_SHELL_MEMPROCSINFO_START_POS_Y + BUTTON_FOR_SHELL_MEMPROCSINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_PROCMEMDBINFO_WIDTH		= 250;
	public final int BUTTON_FOR_SHELL_PROCMEMDBINFO_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_MEMMAP_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_MEMMAP_START_POS_Y	= BUTTON_FOR_SHELL_PROCMEMDBINFO_START_POS_Y + BUTTON_FOR_SHELL_PROCMEMDBINFO_HEIGHT + LINE_GAP;
	public final int BUTTON_FOR_SHELL_MEMMAP_WIDTH			= 250;
	public final int BUTTON_FOR_SHELL_MEMMAP_HEIGHT			= BUTTON_HEIGHT;

	//----------------------------------------------------------------------------------------------------
	public final int LABEL4_1_SHELL_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL4_1_SHELL_START_POS_Y	= BUTTON_FOR_SHELL_MEMMAP_START_POS_Y + BUTTON_FOR_SHELL_MEMMAP_HEIGHT + LINE_GAP;
	public final int LABEL4_1_SHELL_WIDTH		= 150;
	public final int LABEL4_1_SHELL_HEIGHT		= LABEL_HEIGHT;
	
	public final int EDIT4_1_SHELL_START_POS_X	= DEFAULT_POS_X;
	public final int EDIT4_1_SHELL_START_POS_Y	= LABEL4_1_SHELL_START_POS_Y + LABEL4_1_SHELL_HEIGHT + 5;
	public final int EDIT4_1_SHELL_WIDTH		= 100;
	public final int EDIT4_1_SHELL_HEIGHT		= LABEL_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_KERNELLOG_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_KERNELLOG_START_POS_Y	= EDIT4_1_SHELL_START_POS_Y + EDIT4_1_SHELL_HEIGHT + LINE_GAP + 5;
	public final int BUTTON_FOR_SHELL_KERNELLOG_WIDTH		= 260;
	public final int BUTTON_FOR_SHELL_KERNELLOG_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_Y	= BUTTON_FOR_SHELL_KERNELLOG_START_POS_Y + BUTTON_FOR_SHELL_KERNELLOG_HEIGHT + 5;
	public final int BUTTON_FOR_SHELL_KERNELLOG_RESUME_WIDTH		= 125;
	public final int BUTTON_FOR_SHELL_KERNELLOG_RESUME_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_KERNELLOG_PAUSE_START_POS_X	= BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_X + BUTTON_FOR_SHELL_KERNELLOG_RESUME_WIDTH + 10;
	public final int BUTTON_FOR_SHELL_KERNELLOG_PAUSE_START_POS_Y	= BUTTON_FOR_SHELL_KERNELLOG_RESUME_START_POS_Y;
	public final int BUTTON_FOR_SHELL_KERNELLOG_PAUSE_WIDTH			= 125;
	public final int BUTTON_FOR_SHELL_KERNELLOG_PAUSE_HEIGHT		= BUTTON_HEIGHT;
	
	public final int BUTTON_FOR_SHELL_KERNELLOG_DIR_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_KERNELLOG_DIR_START_POS_Y	= BUTTON_FOR_SHELL_KERNELLOG_PAUSE_START_POS_Y + BUTTON_FOR_SHELL_KERNELLOG_PAUSE_HEIGHT + 5;
	public final int BUTTON_FOR_SHELL_KERNELLOG_DIR_WIDTH		= 200;
	public final int BUTTON_FOR_SHELL_KERNELLOG_DIR_HEIGHT		= BUTTON_HEIGHT;
	
	public final int LOG4_SHELL_START_POS_Y		= DEFAULT_POS_Y;
	public final int LOG4_SHELL_WIDTH			= 680 + 40;
	public final int LOG4_SHELL_HEIGHT			= 550;
	public final int LOG4_SHELL_START_POS_X		= TOTAL_BIG_SIZE_WIDTH - LOG4_SHELL_WIDTH + 10;
	
	//------------------------------------------ Page 5 --------------------------------------------------
	//====================================================================================================
	public final int BUTTON_FOR_SHELL_PROCMONITOR_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_SHELL_PROCMONITOR_START_POS_Y	= DEFAULT_POS_Y;
	public final int BUTTON_FOR_SHELL_PROCMONITOR_WIDTH			= 370;
	public final int BUTTON_FOR_SHELL_PROCMONITOR_HEIGHT		= BUTTON_HEIGHT+5;
	
	public final int BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_X	= BUTTON_FOR_SHELL_PROCMONITOR_START_POS_X + BUTTON_FOR_SHELL_PROCMONITOR_WIDTH + 40;
	public final int BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_Y	= BUTTON_FOR_SHELL_PROCMONITOR_START_POS_Y;
	public final int BUTTON_FOR_SHELL_PROFILE_RESET_WIDTH		= 150;
	public final int BUTTON_FOR_SHELL_PROFILE_RESET_HEIGHT		= BUTTON_HEIGHT+5;
			
	public final int EDIT5_1_SHELL_START_POS_X	= BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_X + BUTTON_FOR_SHELL_PROFILE_RESET_WIDTH + 30;
	public final int EDIT5_1_SHELL_START_POS_Y	= BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_Y;
	public final int EDIT5_1_SHELL_WIDTH		= 150;
	public final int EDIT5_1_SHELL_HEIGHT		= BUTTON_FOR_SHELL_PROFILE_RESET_HEIGHT;
	
	public final int LABEL5_1_SHELL_START_POS_X	= EDIT5_1_SHELL_START_POS_X;
	public final int LABEL5_1_SHELL_START_POS_Y	= EDIT5_1_SHELL_START_POS_Y - LABEL_HEIGHT - 1;
	public final int LABEL5_1_SHELL_WIDTH		= 150-1;
	public final int LABEL5_1_SHELL_HEIGHT		= LABEL_HEIGHT;
	
	public final int EDIT5_2_SHELL_START_POS_X	= EDIT5_1_SHELL_START_POS_X + EDIT5_1_SHELL_WIDTH + 5;
	public final int EDIT5_2_SHELL_START_POS_Y	= BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_Y;
	public final int EDIT5_2_SHELL_WIDTH		= 150;
	public final int EDIT5_2_SHELL_HEIGHT		= BUTTON_FOR_SHELL_PROFILE_RESET_HEIGHT;
	
	public final int LABEL5_2_SHELL_START_POS_X	= EDIT5_2_SHELL_START_POS_X;
	public final int LABEL5_2_SHELL_START_POS_Y	= EDIT5_2_SHELL_START_POS_Y - LABEL_HEIGHT - 1;
	public final int LABEL5_2_SHELL_WIDTH		= 150-1;
	public final int LABEL5_2_SHELL_HEIGHT		= LABEL_HEIGHT;
			
	public final int EDIT5_3_SHELL_START_POS_X	= EDIT5_2_SHELL_START_POS_X + EDIT5_2_SHELL_WIDTH + 5;
	public final int EDIT5_3_SHELL_START_POS_Y	= BUTTON_FOR_SHELL_PROFILE_RESET_START_POS_Y;
	public final int EDIT5_3_SHELL_WIDTH		= 150;
	public final int EDIT5_3_SHELL_HEIGHT		= BUTTON_FOR_SHELL_PROFILE_RESET_HEIGHT;

	public final int LABEL5_3_SHELL_START_POS_X	= EDIT5_3_SHELL_START_POS_X;
	public final int LABEL5_3_SHELL_START_POS_Y	= EDIT5_3_SHELL_START_POS_Y - LABEL_HEIGHT - 1;
	public final int LABEL5_3_SHELL_WIDTH		= 150-1;
	public final int LABEL5_3_SHELL_HEIGHT		= LABEL_HEIGHT;
	
	public final int LOG5_SHELL_START_POS_X		= DEFAULT_POS_X;
	public final int LOG5_SHELL_START_POS_Y		= BUTTON_FOR_SHELL_PROCMONITOR_START_POS_Y + BUTTON_FOR_SHELL_PROCMONITOR_HEIGHT + 25;
	public final int LOG5_SHELL_WIDTH			= BUTTON_FOR_SHELL_PROCMONITOR_WIDTH;
	public final int LOG5_SHELL_HEIGHT			= 400;
	
	public final int CHART_START_POS_X		= LOG5_SHELL_START_POS_X + LOG5_SHELL_WIDTH + 40;	
	public final int CHART_START_POS_Y		= LOG5_SHELL_START_POS_Y;
	public final int CHART_WIDTH			= PROFILE_CHART_SIZE_WIDTH;
	public final int CHART_HEIGHT			= LOG5_SHELL_HEIGHT;
	
	public final int LABEL5_HELP_START_POS_X	= LOG5_SHELL_START_POS_X;
	public final int LABEL5_HELP_START_POS_Y	= LOG5_SHELL_START_POS_Y + LOG5_SHELL_HEIGHT + 15;
	public final int LABEL5_HELP_WIDTH			= LOG5_SHELL_WIDTH + CHART_WIDTH + 50;
	public final int LABEL5_HELP_HEIGHT			= LABEL_HEIGHT+1;
	//------------------------------------------ Page 6 --------------------------------------------------
	//====================================================================================================
	public final int BUTTON_FOR_ANR1_START_POS_X	= DEFAULT_POS_X;
	public final int BUTTON_FOR_ANR1_START_POS_Y	= DEFAULT_POS_Y;
	public final int BUTTON_FOR_ANR1_WIDTH			= 200;
	public final int BUTTON_FOR_ANR1_HEIGHT			= BUTTON_HEIGHT+5;
	
	public final int BUTTON_FOR_ANR2_START_POS_X	= BUTTON_FOR_ANR1_START_POS_X + BUTTON_FOR_ANR1_WIDTH + 30;
	public final int BUTTON_FOR_ANR2_START_POS_Y	= DEFAULT_POS_Y;
	public final int BUTTON_FOR_ANR2_WIDTH			= 200;
	public final int BUTTON_FOR_ANR2_HEIGHT			= BUTTON_HEIGHT+5;
		
	public final int LOG6_ANR_START_POS_X			= DEFAULT_POS_X;
	public final int LOG6_ANR_START_POS_Y			= BUTTON_FOR_ANR1_START_POS_Y + BUTTON_FOR_ANR1_HEIGHT + 20;
	public final int LOG6_ANR_WIDTH					= TOTAL_BIGBIG_SIZE_WIDTH - LOG6_ANR_START_POS_X - LOG6_ANR_START_POS_X;
	public final int LOG6_ANR_HEIGHT				= TOTAL_BIGBIG_SIZE_HEIGHT - 110;
		
	public final int LABEL6_1_SHELL_START_POS_Y		= BUTTON_FOR_ANR1_START_POS_Y + 15;
	public final int LABEL6_1_SHELL_WIDTH			= 150;
	public final int LABEL6_1_SHELL_HEIGHT			= LABEL_HEIGHT;
	public final int LABEL6_1_SHELL_START_POS_X		= LOG6_ANR_START_POS_X + LOG6_ANR_WIDTH - LABEL6_1_SHELL_WIDTH;
	//------------------------------------------ Page 7 --------------------------------------------------
	//====================================================================================================
	public final int LABEL7_1_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL7_1_START_POS_Y	= DEFAULT_POS_Y;
	public final int LABEL7_1_WIDTH			= 200;
	public final int LABEL7_1_HEIGHT		= LABEL_HEIGHT+5;
	
	public final int EDIT7_1_START_POS_X	= DEFAULT_POS_X;
	public final int EDIT7_1_START_POS_Y	= LABEL7_1_START_POS_Y + LABEL7_1_HEIGHT + 5;
	public final int EDIT7_1_WIDTH			= 400;
	public final int EDIT7_1_HEIGHT			= EDIT_HEIGHT+5;
	
	public final int ADD_BTN_START_POS_X	= EDIT7_1_START_POS_X + EDIT7_1_WIDTH + 15;
	public final int ADD_BTN_START_POS_Y	= EDIT7_1_START_POS_Y;
	public final int ADD_BTN_WIDTH			= 80;
	public final int ADD_BTN_HEIGHT			= BUTTON_HEIGHT;
	
	public final int LABEL7_2_START_POS_X	= DEFAULT_POS_X;
	public final int LABEL7_2_START_POS_Y	= EDIT7_1_START_POS_Y + EDIT7_1_HEIGHT + 15;
	public final int LABEL7_2_WIDTH			= 200;
	public final int LABEL7_2_HEIGHT		= LABEL_HEIGHT+5;
	
	public final int COMBO_START_POS_X		= LABEL7_2_START_POS_X;
	public final int COMBO_START_POS_Y		= LABEL7_2_START_POS_Y + LABEL7_2_HEIGHT + 5;
	public final int COMBO_WIDTH			= 400;
	public final int COMBO_HEIGHT			= EDIT_HEIGHT+5;
	
	public final int RUN_BTN_START_POS_X	= COMBO_START_POS_X + COMBO_WIDTH + 15;
	public final int RUN_BTN_START_POS_Y	= COMBO_START_POS_Y;
	public final int RUN_BTN_WIDTH			= 80;
	public final int RUN_BTN_HEIGHT			= BUTTON_HEIGHT;

	public final int DEL_BTN_START_POS_X	= RUN_BTN_START_POS_X + RUN_BTN_WIDTH + 5;
	public final int DEL_BTN_START_POS_Y	= RUN_BTN_START_POS_Y;
	public final int DEL_BTN_WIDTH			= 100;
	public final int DEL_BTN_HEIGHT			= BUTTON_HEIGHT;
	
	public final int ROOT_BTN_START_POS_X	= ADD_BTN_START_POS_X;
	public final int ROOT_BTN_START_POS_Y	= ADD_BTN_START_POS_Y - ADD_BTN_HEIGHT - 5;
	public final int ROOT_BTN_WIDTH			= 100;
	public final int ROOT_BTN_HEIGHT		= BUTTON_HEIGHT;
	
	public final int REMOUNT_BTN_START_POS_X	= ROOT_BTN_START_POS_X + ROOT_BTN_WIDTH + 5;
	public final int REMOUNT_BTN_START_POS_Y	= ROOT_BTN_START_POS_Y;
	public final int REMOUNT_BTN_WIDTH			= 100;
	public final int REMOUNT_BTN_HEIGHT			= BUTTON_HEIGHT;
	
	public final int LOG7_APK_TOOL_START_POS_X	= 5;
	public final int LOG7_APK_TOOL_START_POS_Y	= RUN_BTN_START_POS_Y + RUN_BTN_HEIGHT + 100;
	public final int LOG7_APK_TOOL_WIDTH		= TOTAL_SIZE_WIDTH - 20;
	public final int LOG7_APK_TOOL_HEIGHT		= 100;
	
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
