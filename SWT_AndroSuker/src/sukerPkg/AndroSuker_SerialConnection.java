/*
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */
package sukerPkg;
import javax.comm.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import java.io.*;
import java.util.Observable;
import java.util.TooManyListenersException;

/**
A class that handles the details of a serial connection. Reads from one 
TextArea and writes to a second TextArea. 
Holds the state of the connection.
 */
public class AndroSuker_SerialConnection implements SerialPortEventListener, CommPortOwnershipListener
{
	private AndroSuker_SerialParameters parameters;
	private OutputStream os;
	private InputStream is;

	private CommPortIdentifier portId;
	private SerialPort sPort;

	private boolean open;
		
	protected String 	response;
	private boolean 	isEchoStr;
	private int			atcmd_purpose;

	public String		returnStr;
	public final int	RECEIVE_OPERATOR_TYPE__EXPECTED_MODEM_ECHO = 1001;
	public final int	RECEIVE_OPERATOR_TYPE__NEED_RESPONSE = 1002;
	public final int	SERIALCOMM_PURPOSE_LGATCMD = 2001;
	public final int	SERIALCOMM_PURPOSE_GENERAL = 2002;

	private SerialCommDetect					observerCommPort;
	
	public AndroSuker_SerialConnection(AndroSuker_TabItem_SerialComm parent,
			AndroSuker_SerialParameters parameters,
			Shell shell)
	{
		this.parameters = parameters;
		this.open = false;
		this.isEchoStr = false;
		this.atcmd_purpose = SERIALCOMM_PURPOSE_GENERAL;
		this.returnStr = "";
		
		observerCommPort = new SerialCommDetect();
		observerCommPort.addObserver(parent);
	}

	public void setATCMDpurpose(int setValue)	{
		this.atcmd_purpose = setValue;
	}
	public int getATCMDpurpose()	{
		return this.atcmd_purpose;
	}
	
	public void openConnection() throws SerialConnectionException {
		try {
			portId = CommPortIdentifier.getPortIdentifier(parameters.getPortName());
		} catch (NoSuchPortException e) {
			throw new SerialConnectionException(e.getMessage());
		}

		try {
			sPort = (SerialPort)portId.open("AndroSuker SerialConnection", 10000);
		} catch (PortInUseException e) {
			throw new SerialConnectionException(e.getMessage());
		}

		try {
			setConnectionParameters();
		} catch (SerialConnectionException e) {	
			sPort.close();
			throw e;
		}

		// Open the input and output streams for the connection. If they won't
		// open, close the port before throwing an exception.
		try {		   
			is = sPort.getInputStream();
			os = sPort.getOutputStream();
		} catch (IOException e) {
			sPort.close();
			throw new SerialConnectionException("Error opening i/o streams");
		}

		// Add this object as an event listener for the serial port.
		try {
			sPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			sPort.close();
			throw new SerialConnectionException("too many listeners added");
		}

		// Set notifyOnDataAvailable to true to allow event driven input.
		sPort.notifyOnDataAvailable(true);

		// Set notifyOnBreakInterrup to allow event driven break handling.
		sPort.notifyOnBreakInterrupt(true);

		// Set receive timeout to allow breaking out of polling loop during
		// input handling.
		try {
			sPort.enableReceiveTimeout(30);
		} catch (UnsupportedCommOperationException e) {
		}

		// Add ownership listener to allow ownership event handling.
		portId.addPortOwnershipListener(this);

		open = true;
	}

	public void setConnectionParameters() throws SerialConnectionException {
		int oldBaudRate = sPort.getBaudRate();
		int oldDatabits = sPort.getDataBits();
		int oldStopbits = sPort.getStopBits();
		int oldParity   = sPort.getParity();
		sPort.getFlowControlMode();

		// Set connection parameters, if set fails return parameters object
		// to original state.
		try {
			sPort.setSerialPortParams(parameters.getBaudRate(),
										parameters.getDatabits(),
										parameters.getStopbits(),
										parameters.getParity());
		} catch (UnsupportedCommOperationException e) {
			parameters.setBaudRate(oldBaudRate);
			parameters.setDatabits(oldDatabits);
			parameters.setStopbits(oldStopbits);
			parameters.setParity(oldParity);
			throw new SerialConnectionException("Unsupported parameter");
		}

		// Set flow control.
		try {
			sPort.setFlowControlMode(parameters.getFlowControlIn() | parameters.getFlowControlOut());
		} catch (UnsupportedCommOperationException e) {
			throw new SerialConnectionException("Unsupported flow control");
		}
	}

	/**
    Close the port and clean up associated elements.
	 */
	public void closeConnection() {
		// If port is alread closed just return.
		if (!open) {
			return;
		}

		// Check to make sure sPort has reference to avoid a NPE.
		if (sPort != null) {
			try {
				// close the i/o streams.
				os.close();
				is.close();
			} catch (IOException e) {
				System.err.println(e);
			}

			// Close the port.
			sPort.close();

			// Remove the ownership listener.
			portId.removePortOwnershipListener(this);
		}

		open = false;
	}

	/**
    Send a one second break signal.
	 */
	public void sendBreak() {
		sPort.sendBreak(1500);
	}

	/**
    Reports the open status of the port.
    @return true if port is open, false if port is closed.
	 */
	public boolean isOpen() {
		return open;
	}

	/**
    Handles SerialPortEvents. The two types of SerialPortEvents that this
    program is registered to listen for are DATA_AVAILABLE and BI. During 
    DATA_AVAILABLE the port buffer is read until it is drained, when no more
    data is availble and 30ms has passed the method returns. When a BI
    event occurs the words BREAK RECEIVED are written to the messageAreaIn.
	 */

	public void serialEvent(SerialPortEvent e) {
		// Create a StringBuffer and int to receive input data.
		StringBuffer inputBuffer = new StringBuffer();
		int newData = 0;

		// Determine type of event.
		switch (e.getEventType()) {

		// Read data until -1 is returned. If \r is received substitute
		// \n for correct newline handling.
		case SerialPortEvent.DATA_AVAILABLE:
			if (atcmd_purpose == SERIALCOMM_PURPOSE_LGATCMD)	{				
				while (newData != -1) {
					try {						
						newData = is.read();
						if (newData == -1) {
							break;
						}						
						if (newData != 0x0D)	{
							inputBuffer.append((char)newData);
						}
					} catch (IOException ex) {
						MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
						mb.setText("Warning");
						mb.setMessage("com port 연결이 x같군요!!");
						mb.open();
						return;
					}
				}
			} else if (atcmd_purpose == SERIALCOMM_PURPOSE_GENERAL)	{
				while (newData != -1) {
					try {
						newData = is.read();
						if (newData == -1) {
							break;
						}
						if ('\r' == (char)newData) {
							inputBuffer.append('\n');
						} else {
							inputBuffer.append((char)newData);
						}
					} catch (IOException ex) {
						MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
						mb.setText("Warning");
						mb.setMessage("com port 연결이 x같군요!!");
						mb.open();
						return;
					}
				}
			}

			// Append received data to messageAreaIn.
			//System.out.print(new String(inputBuffer));
			setReturnString(new String(inputBuffer));
			observerCommPort.action(true);
			break;			
		case SerialPortEvent.BI:	// If break event append BREAK RECEIVED message.
			System.out.println("\n--- BREAK RECEIVED ---\n");
		}
	}
	public void setReturnString(String str){
		returnStr = str;
	}
	public String getReturnString(){		
		return returnStr;
	}
	
	public void setIsEchoStr(boolean b)	{
		this.isEchoStr = b;
	}
	public boolean getIsEchoStr()	{
		return this.isEchoStr;
	}

	public void sendEvent(String s) throws IOException
	{
		if (os == null)	{
			MessageBox mb = new MessageBox(AndroSuker_MainCls.getShellInstance(), SWT.OK);
			mb.setText("Error");
			mb.setMessage("아직 com port가 연결되지 않았습니다.!!");
			mb.open();
			return ;
		}
		if (s.length() > 0)	{
			int i;
			byte[] bb = s.getBytes();

			if (atcmd_purpose == SERIALCOMM_PURPOSE_LGATCMD)	{
				os.write(bb, 0, s.length());
				os.write((int)0x0D);
				sendBreak();
			}
			else {
				Byte newCharacter;
				for (i = 0; i < s.length(); i++)	{			   
					newCharacter = bb[i];
					try {				
						os.write((int)newCharacter);					
					} catch (IOException e) {
						System.err.println("OutputStream write error: " + e);
					}
				}
				os.write((int)0x0D);
			}			
		}
	}

	/**
    Handles ownership events. If a PORT_OWNERSHIP_REQUESTED event is
    received a dialog box is created asking the user if they are 
    willing to give up the port. No action is taken on other types
    of ownership events.
	 */
	public void ownershipChange(int type) {
		if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
			;//nothing to do.
		}
	}
	
	public static short BytesToShort(byte Value)
	{
		short newValue = 0;

		newValue |= (((short)Value) << 8) & 0xFF00;
				
		return newValue;
	}
	
	public static short[] BytesToShort(byte[] Value, int length)
	{
		short[] newValue = new short[length*2];
		byte[] temp = Value;
		int i;
		
		for (i = 0; i < length; i++)
		{
			newValue[i] |= (((short)temp[i]) << 8) & 0xFF00;
		}
		
		return newValue;
	}
}


class SerialCommDetect extends Observable	{
	public void action(boolean b)	{
		setChanged();
		notifyObservers(b);
	}
}

@SuppressWarnings("serial")
class SerialConnectionException extends Exception {

	/**
	 * Constructs a <code>SerialConnectionException</code>
	 * with the specified detail message.
	 *
	 * @param   s   the detail message.
	 */
	public SerialConnectionException(String str) {
		super(str);
	}

	/**
	 * Constructs a <code>SerialConnectionException</code>
	 * with no detail message.
	 */
	public SerialConnectionException() {
		super();
	}
}