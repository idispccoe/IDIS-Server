package hello;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.*;
import java.util.*;

public class SimpleRead implements Runnable, SerialPortEventListener {
    static CommPortIdentifier portId;
    static Enumeration portList;

    static InputStream inputStream;
    static OutputStream outputStream;
    static SerialPort serialPort;
    Thread readThread;
    
    private static String currentStatus = "";

    public SimpleRead() {
        try {
            serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
        } catch (PortInUseException e) {System.out.println(e);}
        try {
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {System.out.println(e);}
	try {
            serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {System.out.println(e);}
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {System.out.println(e);}
        readThread = new Thread(this);
        readThread.start();
    }
    public static void sendMotorStartCmd(){
    	try {
			outputStream.write("1".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendMotorStopCmd(){
    	try {
			outputStream.write("2".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendResetCmd(){
    	try {
			outputStream.write("3".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendWaterLevelCmd(){
    	try {
			outputStream.write("4".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void resetCurrentStatus(){
    	currentStatus = "";
    }
    
    public static String getCurrentStatus(){
    	return currentStatus;
    }
    
    public static void sendWaterFlowMeterCmd(){
    	try {
			outputStream.write("5".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendSensor1Cmd(){
    	try {
			outputStream.write("6".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void sendSensor2Cmd(){
    	try {
			outputStream.write("7".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void run() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {System.out.println(e);}
    }

    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[1];
            try {            	
            	String currRead = "";
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                    currRead = new String(readBuffer);
                    if(currRead!=null){
                    	if(currRead.equals("*")){
                    		currentStatus = "";
                    	}
                    	else{
                    		currentStatus += currRead;
                    	}
                    }
                }
            } catch (IOException e) {System.out.println(e);}
            break;
        }
    }

}