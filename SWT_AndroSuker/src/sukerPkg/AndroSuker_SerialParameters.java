package sukerPkg;

import java.util.HashMap;

import javax.comm.*;

import org.eclipse.swt.widgets.Combo;

/**
A class that stores parameters for serial ports. 
 */
public class AndroSuker_SerialParameters {
	private HashMap<String, CommPortIdentifier> portNameMap = new HashMap<String, CommPortIdentifier>();	//suker 2011.09.24
	private HashMap<Integer, String> portIndexMap = new HashMap<Integer, String>();	//suker 2011.09.24	
	private String portName;
	private int portIndex;	
	private int baudRate;
	private int flowControlIn;
	private int flowControlOut;
	private int databits;
	private int stopbits;
	private int parity;
	
	private int portCount;

	/**
    Default constructer. Sets parameters to no port, 9600 baud, no flow 
    control, 8 data bits, 1 stop bit, no parity.
	 */
	public AndroSuker_SerialParameters () {
		this("", 
				-1,
				9600, 
				SerialPort.FLOWCONTROL_NONE,
				SerialPort.FLOWCONTROL_NONE,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE );

	}		

	/**
    Paramaterized constructer.

    @param portName The name of the port.
    @param baudRate The baud rate.
    @param flowControlIn Type of flow control for receiving.
    @param flowControlOut Type of flow control for sending.
    @param databits The number of data bits.
    @param stopbits The number of stop bits.
    @param parity The type of parity.
	 */
	public AndroSuker_SerialParameters(String portName,
			int portIndex,
			int baudRate,
			int flowControlIn,
			int flowControlOut,
			int databits,
			int stopbits,
			int parity) {

		this.portName = portName;
		this.baudRate = baudRate;
		this.flowControlIn = flowControlIn;
		this.flowControlOut = flowControlOut;
		this.databits = databits;
		this.stopbits = stopbits;
		this.parity = parity;
		
		this.portCount = 0;
	}

	/**
    Sets port name.
    @param portName New port name.
	 */
	public void setPortName(int index) {		
		this.portName = portIndexMap.get(index);
	}
	//suker 2011.09.24
	public void setPortIndex(int index) {
		this.portIndex = index;
	}
	public int getPortIndex() {
		return this.portIndex;
	}
	//suker 2011.09.24
	public void setPortInfoHash(String name, CommPortIdentifier cpi, Combo c)	{		
		if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			portNameMap.put(name, cpi);
			portIndexMap.put(portCount, name);
			c.setEnabled(true);
			c.add(cpi.getName());
			portCount++;
		}
	}
	
	/**
    Gets port name.
    @return Current port name.
	 */
	public String getPortName() {
		return portName;
	}

	//--------------------------------------------------------------------------------------------------------------------------------
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = Integer.parseInt(baudRate);
	}
	public int getBaudRate() {
		return baudRate;
	}
	public String getBaudRateString() {
		return Integer.toString(baudRate);
	}
	public int getBaudRateStringToIndex(String s) {
		if (s.equals("300"))	{
			return 0;
		} else if (s.equals("2400"))	{
			return 1;
		} else if (s.equals("9600"))	{
			return 2;
		} else if (s.equals("14400"))	{
			return 3;
		} else if (s.equals("28800"))	{
			return 4;
		} else if (s.equals("38400"))	{
			return 5;
		} else if (s.equals("57600"))	{
			return 6;
		} else if (s.equals("152000"))	{
			return 7;
		}		
		return 2;
	}
	public String getBaudRateIndexToString(int i) {
		switch(i)	{
		case 0 : return "300";
		case 1 : return "2400";
		case 2 : return "9600";
		case 3 : return "14400";
		case 4 : return "28800";
		case 5 : return "38400";
		case 6 : return "57600";
		case 7 : return "152000";
		}
		return "9600";
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public void setFlowControlIn(int flowControlIn) {
		this.flowControlIn = flowControlIn;
	}
	public void setFlowControlIn(String flowControlIn) {
		this.flowControlIn = stringToFlow(flowControlIn);
	}
	public int getFlowControlIn() {
		return flowControlIn;
	}
	public String getFlowControlInString() {
		return flowToString(flowControlIn);
	}
	public void setFlowControlOut(int flowControlOut) {
		this.flowControlOut = flowControlOut;
	}
	public void setFlowControlOut(String flowControlOut) {
		this.flowControlOut = stringToFlow(flowControlOut);
	}
	public int getFlowControlOut() {
		return flowControlOut;
	}
	public String getFlowControlOutString() {
		return flowToString(flowControlOut);
	}
	public int getFlowControlStringToIndex(String s) {
		if (s.equals("None"))	{
			return 0;
		} else if (s.equals("Xon/Xoff"))	{
			return 1;
		} else if (s.equals("RTS/CTS"))	{
			return 2;
		}
		return 0;
	}
	public String getFlowControlIndexToString(int i) {
		switch(i)	{
		case 0 : return "None";
		case 1 : return "Xon/Xoff";
		case 2 : return "RTS/CTS";
		}
		return "None";
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public void setDatabits(int databits) {
		this.databits = databits;
	}
	public void setDatabits(String databits) {
		if (databits.equals("5")) {
			this.databits = SerialPort.DATABITS_5;
		}
		if (databits.equals("6")) {
			this.databits = SerialPort.DATABITS_6;
		}
		if (databits.equals("7")) {
			this.databits = SerialPort.DATABITS_7;
		}
		if (databits.equals("8")) {
			this.databits = SerialPort.DATABITS_8;
		}
	}
	public int getDatabits() {
		return databits;
	}
	public String getDatabitsString() {
		switch(databits) {
		case SerialPort.DATABITS_5:
			return "5";
		case SerialPort.DATABITS_6:
			return "6";
		case SerialPort.DATABITS_7:
			return "7";
		case SerialPort.DATABITS_8:
			return "8";
		default:
			return "8";
		}
	}
	public int getDatabitsStringToIndex(String s) {
		if (s.equals("5"))	{
			return 0;
		} else if (s.equals("6"))	{
			return 1;
		} else if (s.equals("7"))	{
			return 2;
		} else if (s.equals("8"))	{
			return 3;
		}
		return 3;
	}
	public String getDatabitsIndexToString(int i) {
		switch(i)	{
		case 0 : return "5";
		case 1 : return "6";
		case 2 : return "7";
		case 3 : return "8";		
		}
		return "8";
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public void setStopbits(int stopbits) {
		this.stopbits = stopbits;
	}
	public void setStopbits(String stopbits) {
		if (stopbits.equals("1")) {
			this.stopbits = SerialPort.STOPBITS_1;
		}
		if (stopbits.equals("1.5")) {
			this.stopbits = SerialPort.STOPBITS_1_5;
		}
		if (stopbits.equals("2")) {
			this.stopbits = SerialPort.STOPBITS_2;
		}
	}
	public int getStopbits() {
		return stopbits;
	}
	public String getStopbitsString() {
		switch(stopbits) {
		case SerialPort.STOPBITS_1:
			return "1";
		case SerialPort.STOPBITS_1_5:
			return "1.5";
		case SerialPort.STOPBITS_2:
			return "2";
		default:
			return "1";
		}
	}
	public int getStopbitsStringToIndex(String s) {
		if (s.equals("1"))	{
			return 0;
		} else if (s.equals("1.5"))	{
			return 1;
		} else if (s.equals("2"))	{
			return 2;
		}
		return 0;
	}
	public String getStopIndexToString(int i) {
		switch(i)	{
		case 0 : return "1";
		case 1 : return "1.5";
		case 2 : return "2";
		}
		return "1";
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------------
	public void setParity(int parity) {
		this.parity = parity;
	}
	public void setParity(String parity) {
		if (parity.equals("None")) {
			this.parity = SerialPort.PARITY_NONE;
		}
		if (parity.equals("Even")) {
			this.parity = SerialPort.PARITY_EVEN;
		}
		if (parity.equals("Odd")) {
			this.parity = SerialPort.PARITY_ODD;
		}
	}
	public int getParity() {
		return parity;
	}
	public String getParityString() {
		switch(parity) {
		case SerialPort.PARITY_NONE:
			return "None";
		case SerialPort.PARITY_EVEN:
			return "Even";
		case SerialPort.PARITY_ODD:
			return "Odd";
		default:
			return "None";
		}
	}
	public int getParityStringToIndex(String s) {
		if (s.equals("None"))	{
			return 0;
		} else if (s.equals("Even"))	{
			return 1;
		} else if (s.equals("Odd"))	{
			return 2;
		}
		return 0;
	}
	public String getParityIndexToString(int i) {
		switch(i)	{
		case 0 : return "None";
		case 1 : return "Even";
		case 2 : return "Odd";
		}
		return "None";
	}
	//--------------------------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------------------------
	/**
    Converts a <code>String</code> describing a flow control type to an
    <code>int</code> type defined in <code>SerialPort</code>.
    @param flowControl A <code>string</code> describing a flow control type.
    @return An <code>int</code> describing a flow control type.
	 */
	private int stringToFlow(String flowControl) {
		if (flowControl.equals("None")) {
			return SerialPort.FLOWCONTROL_NONE;
		}
		if (flowControl.equals("Xon/Xoff Out")) {
			return SerialPort.FLOWCONTROL_XONXOFF_OUT;
		}
		if (flowControl.equals("Xon/Xoff In")) {
			return SerialPort.FLOWCONTROL_XONXOFF_IN;
		}
		if (flowControl.equals("RTS/CTS In")) {
			return SerialPort.FLOWCONTROL_RTSCTS_IN;
		}
		if (flowControl.equals("RTS/CTS Out")) {
			return SerialPort.FLOWCONTROL_RTSCTS_OUT;
		}
		return SerialPort.FLOWCONTROL_NONE;
	}

	/**
    Converts an <code>int</code> describing a flow control type to a 
    <code>String</code> describing a flow control type.
    @param flowControl An <code>int</code> describing a flow control type.
    @return A <code>String</code> describing a flow control type.
	 */
	String flowToString(int flowControl) {
		switch(flowControl) {
		case SerialPort.FLOWCONTROL_NONE:
			return "None";
		case SerialPort.FLOWCONTROL_XONXOFF_OUT:
			return "Xon/Xoff Out";
		case SerialPort.FLOWCONTROL_XONXOFF_IN:
			return "Xon/Xoff In";
		case SerialPort.FLOWCONTROL_RTSCTS_IN:
			return "RTS/CTS In";
		case SerialPort.FLOWCONTROL_RTSCTS_OUT:
			return "RTS/CTS Out";
		default:
			return "None";
		}
	}
}
