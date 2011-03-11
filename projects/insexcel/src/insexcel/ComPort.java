package insexcel;

import java.io.*;
import java.util.*;
import gnu.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

public class ComPort extends Thread {
    
	WritableWorkbook workbook;
	WritableSheet sheet;
	
	private Enumeration	      portList;
    private CommPortIdentifier portId;
    private String	      messageString = "Hello, world!";
    private SerialPort	      serialPort;
    private InputStream        inputStream;
    private BufferedInputStream bInputStream;
    private byte[] buffer = new byte[1024];
    private String dataString;
    private String[] dataArr = new String[3];
    private String defaultPort;
    private String partString;
    private Pattern pattern = Pattern.compile("<MEASUREMENT>(.*?)</MEASUREMENT>", Pattern.DOTALL);
    
    public ArrayList<String> x = new ArrayList<String>();
    public ArrayList<String> y = new ArrayList<String>();
    public ArrayList<String> z = new ArrayList<String>();
    public ArrayList<String> yaw = new ArrayList<String>();
    public ArrayList<String> pitch = new ArrayList<String>();
    public ArrayList<String> roll = new ArrayList<String>();

    public ComPort(String port) {
	if (!port.equals("")) {
	    defaultPort = port;
	}
        else {
            defaultPort = "COM6";
        }
    }

    private void initialize() {
        boolean portFound = false;
        portList = CommPortIdentifier.getPortIdentifiers();
        
		while (portList.hasMoreElements()) {
		    portId = (CommPortIdentifier) portList.nextElement();
		    
		    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		    	
				if (portId.getName().equals(defaultPort)) {
				    System.out.println("Found port " + defaultPort);
				    portFound = true;
				    
				    try {
				    	serialPort = (SerialPort) portId.open("SimpleWrite", 2000);
				    } catch (PortInUseException e) {
				    	System.out.println("Port in use.");
				    	continue;
				    }
				    
				    try {
				    	inputStream = serialPort.getInputStream();
		                bInputStream = new BufferedInputStream(inputStream);
				    } catch (IOException e) {}
				    
				    try {
				    	serialPort.setSerialPortParams(
				    			115200,
				    			SerialPort.DATABITS_8,
				    			SerialPort.STOPBITS_1,
				    			SerialPort.PARITY_NONE
				    	);
				    } catch (UnsupportedCommOperationException e) {}
				}
		    }
		}
		
		if (!portFound) {
		    System.out.println("port " + defaultPort + " not found.");
		}
    }

    @Override
    public void run() {
    	System.out.println("Initializing system..");
    	
        initialize();
        
        System.out.println("Reading data..");
        
        while(x.size() < 100) {
            try {
                readSerial();
                System.out.println("\t" + x.size() + "/100");
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("System couldn't sleep or encountered a problem while reading.");
            }
        }
        
        System.out.println("Writing data..");
        
        try {
			workbook = Workbook.createWorkbook(new File("data.xls"));
			sheet = workbook.createSheet("Data", 0);

			addStuffz(0, "Accel X", x);
			addStuffz(1, "Accel Y", y);
			addStuffz(2, "Accel Z", z);
			//addStuffz(3, "Yaw", yaw);
			//addStuffz(4, "Pitch", pitch);
			//addStuffz(5, "Roll", roll);

			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
		System.out.println("Terminating system..");
    }
    
    private void addStuffz(int col, String title, ArrayList<String> list) throws RowsExceededException, NumberFormatException, WriteException {
    	int index = 0;
    	sheet.addCell(new Label(col, index, title));
    	index++;
    	while (index <= list.size()) {
    		sheet.addCell(
    				new Number(
    						col,
    						index,
    						Float.valueOf(list.get(index - 1).trim()).floatValue()
    				)
    		);
    		index++;
    	}
    }

    private void readSerial() {
        try {
            int availableBytes = bInputStream.available();
            System.out.println(availableBytes + "Sunspot sucks");
            if (availableBytes > 1) {
            	System.out.println("Sunspot sucks");
            	// Read the serial port
                bInputStream.read(buffer, 0, availableBytes);
                // Print it out
                dataString = new String(buffer, 0, availableBytes);
                parseData(dataString);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void parseData(String dataString) {
        Matcher matcher = pattern.matcher(dataString);
        showData(matcher);
    }

    public void showData(Matcher matcher) {

        if(matcher.find())
        {
            if(partString != null) {
                partString = null;
            }
            dataString = matcher.group(1);
            dataArr = dataString.split(";");
            
            x.add(dataArr[0]);
            y.add(dataArr[1]);
            z.add(dataArr[2]);
            //yaw.add(dataArr[3]);
            //pitch.add(dataArr[4]);
            //roll.add(dataArr[5]);
        }
        else {
            if(partString == null) {
                partString = dataString;
            }
            else {
                partString += dataString;
                dataString = partString;
                parseData(dataString);
            }
        }
    }

    public void close() {
        serialPort.close();
        System.exit(1);
    }

}
