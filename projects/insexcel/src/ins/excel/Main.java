package ins.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import ins.lib.math.util.Convert;
import ins.lib.math.Quaternion;
import ins.lib.math.Vector3;
import ins.lib.net.PacketHeader;

import jxl.Workbook;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Main {
    private Enumeration<CommPortIdentifier> portList;
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private BufferedInputStream in;
    
    private ArrayList<Vector3> accelList = new ArrayList<Vector3>();
    private ArrayList<Quaternion> rotList = new ArrayList<Quaternion>();
    
    private WritableWorkbook workbook;
	private WritableSheet sheet;
	
	private String commPort;
	private String fileName;
	private int numSamples;

	public Main(String commPort) {
		this(commPort, "data", 0);
	}
	
	public Main(String commPort, String fileName) {
		this(commPort, fileName, 0);
	}
	
    public Main(String commPort, String fileName, int numSamples) {
		this.commPort = commPort;
		this.fileName = fileName;
		this.numSamples = numSamples;
		
		portList = CommPortIdentifier.getPortIdentifiers();
		
		boolean portFound = false;
		while (portList.hasMoreElements()) {
		    portId = (CommPortIdentifier) portList.nextElement();
	
		    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	
				if (portId.getName().equals(commPort)) {
				    System.out.println("Found port " + commPort);
		
				    portFound = true;
		
				    try {
				    	serialPort = (SerialPort) portId.open("Main", 2000);
				    } catch (PortInUseException e) {
				    	System.out.println("Port in use.");
						continue;
				    } 
		
					try {
					  	in = new BufferedInputStream(serialPort.getInputStream());
					} catch (IOException e) {}
			
					try {
					  	serialPort.setSerialPortParams(9600, 
									       SerialPort.DATABITS_8, 
									       SerialPort.STOPBITS_1, 
									       SerialPort.PARITY_NONE);
					} catch (UnsupportedCommOperationException e) {}
				
			
					try {
					   	serialPort.notifyOnOutputEmpty(true);
					} catch (Exception e) {
						System.out.println("Error setting event notification");
						System.out.println(e.toString());
						System.exit(-1);
					}
			
					try {
						System.out.println("Listening..");
						
						int sampleCount = 0;
						int exceptionCount = 0;
						
						byte b = (byte) PacketHeader.VN100_PING;

						
					   	while (true) {
					   		b = (byte) in.read();
					   		switch (b) {
						   		case PacketHeader.VN100_PING:
						   			continue;
						   		case PacketHeader.VN100_DATA:						   								   			
						   		// read data string length
						   			byte[] lBuffer = new byte[4];
						   			in.read(lBuffer);
						   			int length = Convert.byteArrayToInt(lBuffer);
						   									   			
						   			// read data string
						   			byte[] strBuffer = new byte[length];
						   			in.read(strBuffer);
						   			String data = new String(strBuffer);
						   			
						   			String[] dataArray = data.split(",");
						   			
						   			float distanceX = Float.parseFloat(dataArray[0]);
						   			float distanceXY = Float.parseFloat(dataArray[1]);
						   			float distanceXYZ = Float.parseFloat(dataArray[2]);
						   			
						   			System.out.println("Distance X: " + distanceX);
						   			//System.out.println("Distance XY: " + distanceXY);
						   			//System.out.println("Distance XYZ: " + distanceXYZ);
						   			
						   			break;
						   		case PacketHeader.VN100_EXCEPTION:
						   			exceptionCount++;
						   			System.out.println("VN100 Exception (Total: " + exceptionCount + ")");
						   			break;
					   		}
					   	}
					   	
					   	//System.out.println("Writing..");
					   	//writeToExcel();
					   	
					   	//System.out.println("Terminating..");
					   	//close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	
		if (!portFound) {
		    System.out.println("port " + commPort + " not found.");
		}
    }
    
    private void close() throws IOException {
    	in.close();
	   	serialPort.close();
		System.exit(0);
    }
    
    
    private boolean processData() throws IOException {
    	// read data string length
		byte[] lBuffer = new byte[4];
		in.read(lBuffer);
		int length = Convert.byteArrayToInt(lBuffer);
			
		// read data string
		byte[] strBuffer = new byte[length];
		in.read(strBuffer);
		String data = new String(strBuffer);
		String[] a = data.split(";");
		
		try {
			// parse data
			Vector3 accel = new Vector3(Float.parseFloat(a[0]), Float.parseFloat(a[1]), Float.parseFloat(a[2]));
			Quaternion rot = new Quaternion(Float.parseFloat(a[3]), Float.parseFloat(a[4]), Float.parseFloat(a[5]), Float.parseFloat(a[6]));
			
			// save data
			accelList.add(accel);
			rotList.add(rot);
			
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
    }
    
    private void writeToExcel() {
    	System.out.println("Writing data..");
        
        try {
        	File file = new File(fileName + ".xls");
        	int index = 1;
        	while (file.exists()) {
        		index++;
        		file = new File(fileName + index + ".xls");
        	}
        	
			workbook = Workbook.createWorkbook(file);
			sheet = workbook.createSheet("Data", 0);

			sheet.addCell(new Label(0, 0, "Accel X"));
			sheet.addCell(new Label(1, 0, "Accel Y"));
			sheet.addCell(new Label(2, 0, "Accel Z"));
			sheet.addCell(new Label(3, 0, "Total Accel"));
			sheet.addCell(new Label(4, 0, "Yaw"));
			sheet.addCell(new Label(5, 0, "Pitch"));
			sheet.addCell(new Label(6, 0, "Roll"));
			
			for (int i = 0; i < numSamples; i++) {
				sheet.addCell(
						new Number(
								0,
								i + 1,
								accelList.get(i).x
						)
				);
				sheet.addCell(
						new Number(
								1,
								i + 1,
								accelList.get(i).y
						)
				);
				sheet.addCell(
						new Number(
								2,
								i + 1,
								accelList.get(i).z
						)
				);
				sheet.addCell(
						new Number(
								3,
								i + 1,
								Math.abs(accelList.get(i).x) + Math.abs(accelList.get(i).y) + Math.abs(accelList.get(i).z)
						)
				);
				sheet.addCell(
						new Number(
								4,
								i + 1,
								rotList.get(i).getYaw()
						)
				);
				sheet.addCell(
						new Number(
								5,
								i + 1,
								rotList.get(i).getPitch()
						)
				);
				sheet.addCell(
						new Number(
								6,
								i + 1,
								rotList.get(i).getRoll()
						)
				);
			}
			
			insertTable();
			
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
    }
    
    private void insertTable() throws RowsExceededException, WriteException {
    	// Table cols
		sheet.addCell(new Label(9, 0, "Accel X"));
		sheet.addCell(new Label(10, 0, "Accel Y"));
		sheet.addCell(new Label(11, 0, "Accel Z"));
		sheet.addCell(new Label(12, 0, "Total Accel"));
		sheet.addCell(new Label(13, 0, "Yaw"));
		sheet.addCell(new Label(14, 0, "Pitch"));
		sheet.addCell(new Label(15, 0, "Roll"));
		
		// Table rows
		sheet.addCell(new Label(8, 1, "Min"));
		sheet.addCell(new Label(8, 2, "Max"));
		sheet.addCell(new Label(8, 3, "Avg"));
		sheet.addCell(new Label(8, 4, "Stdev"));
		
		// Min
		sheet.addCell(new Formula(9, 1, "MIN(A:A)"));
		sheet.addCell(new Formula(10, 1, "MIN(B:B)"));
		sheet.addCell(new Formula(11, 1, "MIN(C:C)"));
		sheet.addCell(new Formula(12, 1, "MIN(D:D)"));
		sheet.addCell(new Formula(13, 1, "MIN(E:E)"));
		sheet.addCell(new Formula(14, 1, "MIN(F:F)"));
		sheet.addCell(new Formula(15, 1, "MIN(G:G)"));
		
		// Max
		sheet.addCell(new Formula(9, 2, "MAX(A:A)"));
		sheet.addCell(new Formula(10, 2, "MAX(B:B)"));
		sheet.addCell(new Formula(11, 2, "MAX(C:C)"));
		sheet.addCell(new Formula(12, 2, "MAX(D:D)"));
		sheet.addCell(new Formula(13, 2, "MAX(E:E)"));
		sheet.addCell(new Formula(14, 2, "MAX(F:F)"));
		sheet.addCell(new Formula(15, 2, "MAX(G:G)"));
		
		// Stdev
		sheet.addCell(new Formula(9, 4, "STDEV(A:A)"));
		sheet.addCell(new Formula(10, 4, "STDEV(B:B)"));
		sheet.addCell(new Formula(11, 4, "STDEV(C:C)"));
		sheet.addCell(new Formula(12, 4, "STDEV(D:D)"));
		sheet.addCell(new Formula(13, 4, "STDEV(E:E)"));
		sheet.addCell(new Formula(14, 4, "STDEV(F:F)"));
		sheet.addCell(new Formula(15, 4, "STDEV(G:G)"));
    }
    
    public static void main(String[] args) throws Exception {
    	/*if (args.length == 3) {
    		new Main(args[0], args[1], Integer.parseInt(args[2]));
    	} else if (args.length == 2) {
    		new Main(args[0], args[1]);
    	} else if (args.length == 1) {
    		new Main(args[0]);
    	} else {
    		throw new Exception("Invalid arguments.");
    	}*/
    }
}

