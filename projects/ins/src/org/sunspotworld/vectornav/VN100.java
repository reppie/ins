
package org.sunspotworld.vectornav;

import com.sun.spot.peripheral.IAT91_PIO;
import com.sun.spot.peripheral.ISpiMaster;
import com.sun.spot.peripheral.PIOPin;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.external.ExternalBoard; // I/O board
import com.sun.spot.peripheral.external.ISPI; // SPI connection
import com.sun.spot.util.Properties; // Properties of the I/O board
import com.sun.spot.util.Utils;
import java.io.ByteArrayInputStream; // To convert SPI byte arrays to floats
import java.io.DataInputStream; // To convert SPI byte arrays to floats
import java.io.IOException;
import org.sunspotworld.math.Convert;

/**
 * Driver class for the VN-100 chip connection with a SunSPOT
 * Manufactured by Vectornav
 * @author Thies Keulen
 */
public class VN100 extends ExternalBoard
{
    private static VN100 singleton;     // The VN100 instance, only 1 can be created
    private ISPI spi;   // The SPI communication
    /*
     * CSR_MODE3 because CPHA=1 and CPOL=1 (See Section 3 Figure 4 UM001.pdf Datasheet at http://www.vectornav.com/support)
     * According to http://en.wikipedia.org/wiki/Serial_Peripheral_Interface_Bus this cpha and cpol combination is mode 3
     * CSR_BITS = 8, any other setting would result in error or no readings
     */
    public static final int SPI_CONFIG = (ISpiMaster.CSR_MODE3| ISpiMaster.CSR_BITS_8 | ISpiMaster.CSR_SCBR_6MHZ);
    private static final String PART_ID = "VECTOR_REV_0_0";
    private boolean debug = false; // set true to see all byte values in the transactions
    private float xOffset;
    private float yOffset;
    private float zOffset;

    private PIOPin resetPin;    // reset pin is not used currently
    private PIOPin drdyPin;     // data ready pin is pulled low by the chip if data is available

    /**
     * Constructor
     */
    private VN100()
    {
        super(PART_ID);
        init();
    }

    /**
     * The instance of the class
     * @return the instance of the VN100 class
     */
    public static VN100 getInstance()
    {
        // Check if there already is in instance of this class
        if (singleton == null)
        {
            // If not, make 1
            singleton = new VN100();
        }
        return singleton;
    }

    /**
     *
     * @return The properties of the VN-100 chip
     */
    public static Properties getInitialProperties()
    {
        Properties p = new Properties();
        p.put("PART_ID", PART_ID);
        p.put("PART_URL", "http://www.vectornav.com/vn-100-features");
        return p;
    }

    /**
     * Initialize the SPI connection and pins
     */
    protected void init()
    {
        spi = newBoardDeviceSPI(0, SPI_CONFIG); // Create new SPI connection, with the config

        // Init pins
        resetPin = new PIOPin(Spot.getInstance().getAT91_PIO(IAT91_PIO.PIOA), 1<<7, PIOPin.IO);
        drdyPin = new PIOPin(Spot.getInstance().getAT91_PIO(IAT91_PIO.PIOA), 1<<8, PIOPin.IO);

        // claim them
        resetPin.claim();
        drdyPin.claim();

        // Set what pin is doing what
        resetPin.openForOutput();
        drdyPin.openForInput();
    }

    /**
     * Calculate the offset, to be used in getRelativeAccel
     * Takes the average acceleration of 1000 samples for each axis
     * @throws Exception
     */
    public void setRestOffsets() throws Exception
    {
        // Give device time to settle
        Utils.sleep(1000);
        float xTotal = 0;
        float yTotal = 0;
        float zTotal = 0;

        // Take 1000 samples
        for(int i = 0; i < 1000; i++)
        {
            // Get the acceleration
            float[] acceleration = getAcceleration();
            float x = acceleration[0];
            float y = acceleration[1];
            float z = acceleration[2];

            // Add the values to the total
            xTotal += x;
            yTotal += y;
            zTotal += z;
        }

        // Calculate averages
        xOffset = xTotal / (float)1000.00;
        yOffset = yTotal / (float)1000.00;
        // For the z-axis the default value should be 9.81
        zOffset = (float)9.8129 - (Math.abs(zTotal / (float)1000.00));

        //System.out.println("Offsets:");
        //System.out.println("Xo: " + xOffset);
        //System.out.println("Yo: " + yOffset);
        //System.out.println("Zo: " + zOffset);
    }

    public void setInertialOffset() throws IOException
    {
        // Give device time to settle
        Utils.sleep(1000);
        float xTotal = 0;
        float yTotal = 0;
        float zTotal = 0;

        // Take 1000 samples
        for(int i = 0; i < 1000; i++)
        {
            // Get the acceleration
            float[] acceleration = getInertialAcceleration();
            float x = acceleration[0];
            float y = acceleration[1];
            float z = acceleration[2];

            // Add the values to the total
            xTotal += x;
            yTotal += y;
            zTotal += z;
        }

        // Calculate averages
        xOffset = xTotal / (float)1000.00;
        yOffset = yTotal / (float)1000.00;
        // For the z-axis the default value should be 9.81
        zOffset = (float)9.8129 - (Math.abs(zTotal / (float)1000.00));

        //System.out.println("Offsets:");
        //System.out.println("Xo: " + xOffset);
        //System.out.println("Yo: " + yOffset);
        //System.out.println("Zo: " + zOffset);
    }

    /**
     *
     * @return float array of the acceleration relative to
     * the starting values
     * @throws Exception
     */
    public float[] getRelativeAcceleration() throws Exception
    {
        // Get raw acceleration
        float[] accel = getAcceleration();
        float x = accel[0];
        float y = accel[1];
        float z = accel[2];

        // Subtract the offsets
        float[] temp = {0,0,0};
        temp[0] = (x - xOffset);
        temp[1] = (y - yOffset);
        temp[2] = (z - zOffset);

        return temp;
    }

    /**
     * Get the current acceleration in all 3 axis
     * @return float array of the raw acceleration values
     * @throws IOException
     */
    public float[] getAcceleration() throws IOException
    {
        // Create command
        byte[] command = {1,0x12,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        // Send it, the result is 16 bytes long ( 3x4 + 4 header bytes)
        byte[] result = sendCommand(command, 16);

        // First 4 bytes are header, and the values are in little-endian
        byte[] xResult = {result[7],result[6],result[5],result[4]};
        byte[] yResult = {result[11],result[10],result[9],result[8]};
        byte[] zResult = {result[15],result[14],result[13],result[12]};

        // Convert byte arrays to floats
        DataInputStream xStream = new DataInputStream(new ByteArrayInputStream(xResult));
        DataInputStream yStream = new DataInputStream(new ByteArrayInputStream(yResult));
        DataInputStream zStream = new DataInputStream(new ByteArrayInputStream(zResult));
        float x = xStream.readFloat();
        float y = yStream.readFloat();
        float z = zStream.readFloat();

        // return values
        float[] values = {x,y,z};
        return values;
    }

    /**
     * Get the current yaw, pitch and roll values
     * @return float array of the current yaw,pitch and roll (in that order) values
     * @throws IOException
     */
    public float[] getYawPitchRoll() throws IOException
    {
        // Read register 8
        byte[] command = {1,8,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 16); // Result is 16 bytes, 3x4 + 4 header

        // first 4 bytes are header, and it's little-endian
        byte[] yawResult = {result[7],result[6],result[5],result[4]};
        byte[] pitchResult = {result[11],result[10],result[9],result[8]};
        byte[] rollResult = {result[15],result[14],result[13],result[12]};

        // Convert byte arrays to floats
        DataInputStream yawStream = new DataInputStream(new ByteArrayInputStream(yawResult));
        DataInputStream pitchStream = new DataInputStream(new ByteArrayInputStream(pitchResult));
        DataInputStream rollStream = new DataInputStream(new ByteArrayInputStream(rollResult));
        float yaw = yawStream.readFloat();
        float pitch = pitchStream.readFloat();
        float roll = rollStream.readFloat();

        float[] values = {yaw,pitch,roll};

        return values;
    }

    /**
     * Get the current quaternion values
     * @return float array of the current yaw,pitch and roll (in that order) values
     * @throws IOException
     */
    public float[] getQuaternion() throws IOException
    {
        // Read register 8
        byte[] command = {1,9,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 20); // Result is 16 bytes, 3x4 + 4 header

        // first 4 bytes are header, and it's little-endian
        byte[] xResult = {result[7],result[6],result[5],result[4]};
        byte[] yResult = {result[11],result[10],result[9],result[8]};
        byte[] zResult = {result[15],result[14],result[13],result[12]};
        byte[] scalar =  {result[19],result[18],result[17],result[16]};

        // Convert byte arrays to floats
        DataInputStream xStream = new DataInputStream(new ByteArrayInputStream(xResult));
        DataInputStream yStream = new DataInputStream(new ByteArrayInputStream(yResult));
        DataInputStream zStream = new DataInputStream(new ByteArrayInputStream(zResult));
        DataInputStream scalarStream = new DataInputStream(new ByteArrayInputStream(scalar));
        float x = xStream.readFloat();
        float y = yStream.readFloat();
        float z = zStream.readFloat();
        float s = scalarStream.readFloat();

        float[] values = {x,y,z,s};

        return values;
    }

    /**
     * Get the magnetic values of all 3 axis
     * @return float array of the magnetic values
     * @throws Exception
     */
    public float[] getMagnetic() throws Exception
    {
        // Make and send command
        byte[] command = {1,0x11,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 16);

        // Get result
        byte[] xResult = {result[7],result[6],result[5],result[4]};
        byte[] yResult = {result[11],result[10],result[9],result[8]};
        byte[] zResult = {result[15],result[14],result[13],result[12]};

        // Convert byte arrays to floats
        DataInputStream xStream = new DataInputStream(new ByteArrayInputStream(xResult));
        DataInputStream yStream = new DataInputStream(new ByteArrayInputStream(yResult));
        DataInputStream zStream = new DataInputStream(new ByteArrayInputStream(zResult));
        float x = xStream.readFloat();
        float y = yStream.readFloat();
        float z = zStream.readFloat();

        float[] values = {x,y,z};

        return values;
    }

    public String getModelNumber() throws IOException
    {
        byte[] command = {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 16);

        return new String(result);
    }

    /**
     * Tune out the magnetometer
     * See section 11.2.1.3 from the datasheet
     * @throws IOException
     */
    public void tuneOutMagnetometer() throws IOException
    {
        // Create the command
        byte[] command = new byte[]{(byte)0x2,(byte)0x16,(byte)0x0,(byte)0x0,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70
        ,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x0,(byte)0x0,(byte)0xC8
        ,(byte)0x42,(byte)0x0,(byte)0x0,(byte)0xC8,(byte)0x42,(byte)0x0,(byte)0x0,(byte)0xC8,(byte)0x42,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35
        ,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35};

        // Send it
        byte[] result = sendCommand(command, 44);
    }

    /**
     * Reset the module
     * @throws IOException
     */
    public void reset() throws IOException
    {
        byte[] command = {6,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);
    }

    /**
     * Return to default settings
     * @throws IOException
     */
    public void restoreSettings() throws IOException
    {
        byte[] command = {4,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);
    }

    /**
     * Give all measurement equal weight
     * @throws IOException
     */
    public void nominal() throws IOException
    {
        byte[] command = new byte[]{(byte)0x2,(byte)0x16,(byte)0x0,(byte)0x0,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37
        ,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86
        ,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35
        ,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35};
        byte[] result = sendCommand(command, 44);
    }

    /**
     * Increase trust in the gyro's
     * See section 11.2.1.2 of the datasheet
     * @throws IOException
     */
    public void moreTrustInGyros() throws IOException
    {
        byte[] command = new byte[]{(byte)0x2,(byte)0x16,(byte)0x0,(byte)0x0,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70
        ,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0xBD,(byte)0x37,(byte)0x86
        ,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35
        ,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35};
        byte[] result = sendCommand(command, 44);
    }

    /**
     * Tune the kahlman filter to have increased confidence in the gyros,
     * while having less confidence in the accelerometers
     * See section 11.2.1.4 of the datasheet
     * @throws Exception
     */
    public void reduceVibration() throws Exception
    {
        byte[] command = new byte[]{(byte)0x2,(byte)0x16,(byte)0x0,(byte)0x0,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70
        ,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0x5F,(byte)0x70,(byte)0x89,(byte)0x30,(byte)0xBD,(byte)0x37,(byte)0x86
        ,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0xBD,(byte)0x37,(byte)0x86,(byte)0x35,(byte)0x6F,(byte)0x12,(byte)0x83,(byte)0x3A
        ,(byte)0x6F,(byte)0x12,(byte)0x83,(byte)0x3A,(byte)0x6F,(byte)0x12,(byte)0x83,(byte)0x3A};
        byte[] result = sendCommand(command, 44);
    }

    /**
     * Changes the gain/scale for the accelerometer
     * @param number . The gain/scale to set. 2G or 6G
     * @throws IOException
     */
    public void setAccelerometerGain(int number) throws IOException
    {
        // Check if input is correct
        if(number == 2)
        {
           byte[] command = {2,0x1C,0,0,0,0,0,0};
           byte[] result = sendCommand(command, 8);
        }
        else if(number == 6)
        {
            byte[] command = {2,0x1C,0,0,1,0,0,0};
            byte[] result = sendCommand(command, 8);
        }
        else
        {
            System.out.println("Enter 2 or 6");
        }
    }

    public int setBaudRate(int number) throws IOException
    {
        // 02050000 = write serial baud cmd
        // 8025000 = 9600 (litle endian)
        // 00C20100 = 115200 (little endian)
        byte[] command = {2,5,0,0,0,(byte)0xC2,(byte)0x01,0};
        //byte[] command = {2,5,0,0,(byte)0x80,(byte)0x25,0,0};
        byte[] result = sendCommand(command, 8);

        return Convert.byteArrayToInt(result, 4);
    }

    /**
     * Get the currenct baudrate
     * @throws IOException
     */
    public int getBaudRate() throws IOException
    {
        byte[] command = {1,5,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);
        return Convert.byteArrayToInt(result, 4);
    }

    public int getFirmwareVersion() throws IOException
    {
        byte[] command = {1,4,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);

        return Integer.parseInt(Integer.toHexString(result[4]&0xff), 16);
    }

    /**
     * Zero out current orientation. In the current orientation , sets yaw,pitch and roll to 0
     * @throws IOException
     */
    public void tare() throws IOException
    {
        byte[] command = {5,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);
    }

    /**
     * Get the hardware revision number
     * @return the hardware revision number
     * @throws IOException
     */
    public double getHardwareRevision() throws IOException
    {
        byte[] command = {1,2,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 8);
        // This could be tricky if number doesn't fit in 1 byte.
        return Integer.parseInt(Integer.toHexString(result[4]&0xff),16);
    }

    /**
     * Send a command to the VN100 and send a clean command after to get the result
     * @param command , The command array to be send
     * @param Resultsize , the size of the result to be expected
     * @return the byte array of the result
     * @throws IOException
     */
    private byte[] sendCommand(byte[] command,int Resultsize) throws IOException
    {
        // Let the chip know we want to send a command
        resetPin.setHigh();

        // Create a new result array
        byte[] result = new byte[Resultsize];

        // if debug mode is on. Print the command to send
        if(debug == true)
        {
            for(int i = 0; i< command.length; i++)
            {
                System.out.println("Command: " + Integer.toHexString(command[i]&0xff));
            }
        }

        // Send the command over the SPI. command is 0 after this, due to the ring buffer
        spi.sendSPICommand(command, command);

        // Wait until the data ready pin is pulled low
        // The chip tells the user that new data is available by doing this
        while (drdyPin.isHigh());

        // Send a clean command (all 0's) to get the answer of the command (Ring buffer)
        spi.sendSPICommand(result, result);

        // If debug mode is on. Print result
        if(debug == true)
        {
            for(int i = 0; i< result.length; i++)
            {
               System.out.println("Result: " + Integer.toHexString(result[i]&0xff));
            }
        }

        // If the 4th byte is not 0. Handle the error
        if(result[3] != 0)
        {
            handleError(result[3]);
        }

        return result;
    }

    /**
     * Handle any error that may occur during a request
     * @param val . The value in the 4th byte (error byte)
     * @throws IOException
     */
    private void handleError(byte val) throws IOException
    {
        // Convert byte to string
        String stringError = String.valueOf(val);
        // Convert string to int. Because switch doesn't work with string :'(
        int error = Integer.parseInt(stringError, 16);
        // Check which error it is and create a IOException accordingly
        switch(error)
        {
            case 1:
                throw new IOException("Hard Fault");
            case 2:
                throw new IOException("Serial Buffer Overflow");
            case 3:
                throw new IOException("Invalid Checksum");
            case 4:
                throw new IOException("Invalid Command");
            case 5:
                throw new IOException("Not enough parameters");
            case 6:
                throw new IOException("Too many parameters");
            case 7:
                throw new IOException("Invalid parameter");
            case 8:
                throw new IOException("Invalid register");
            case 9:
                throw new IOException("Unauthorized Access");
            case 10:
                throw new IOException("Watchdog Reset");
            case 11:
                throw new IOException("Output Buffer Overflow");
            case 12:
                throw new IOException("Insufficient Baud Rate");
        }
    }

    /**
     * Set debug mode on or off
     * @param val . True or False
     */
    public void setDebug(boolean val)
    {
        this.debug = val;
    }

    /**
     * Combine acceleration and heading into inertial acceleration
     * Needs checking, see C library of Vectornav
     * @return float array with the inertial acceleration
     * @throws IOException
     */
    public float[] getInertialAcceleration() throws IOException
    {
        float[] accelAndQuaternion = getQuaternionAndAcceleration();
        float[] q = {accelAndQuaternion[0],accelAndQuaternion[1],accelAndQuaternion[2],accelAndQuaternion[3]};
        float[] acc = {accelAndQuaternion[4],accelAndQuaternion[5],accelAndQuaternion[6]};

        float[][] a = quatToDCM(q);

        float[] inertial = new float[3];

        for(int i = 0; i < 3;i++)
        {
            inertial[i] = 0;
            for(int k = 0; k < 3; k++)
            {
                inertial[i] += a[k][i] * acc[k];
            }
        }

        return inertial;
    }

    /**
     *
     * @return Relative acceleration float array
     * @throws IOException
     */
    public float[] getRelativeInertialAcceleration() throws IOException
    {
        float[] accelAndQuaternion = getQuaternionAndAcceleration();
        float[] q = {accelAndQuaternion[0],accelAndQuaternion[1],accelAndQuaternion[2],accelAndQuaternion[3]};
        float[] acc = {accelAndQuaternion[4],accelAndQuaternion[5],accelAndQuaternion[6]};

        float[][] a = quatToDCM(q);

        float[] inertial = new float[3];

        for(int i = 0; i < 3;i++)
        {
            inertial[i] = 0;
            for(int k = 0; k < 3; k++)
            {
                inertial[i] += a[k][i] * acc[k];
            }
        }

        inertial[0] = inertial[0] - xOffset;
        inertial[1] = inertial[1] - yOffset;
        inertial[2] = inertial[2] - zOffset;

        return inertial;
    }

    public String getSerialNumber() throws IOException
    {
        byte[] command = {1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 16);

        return new String(result);
    }

    /**
     * Transforms quaternation to DCM
     * @param quat
     * @return
     */
    public float[][] quatToDCM(float[] quat)
    {
        float[] temp = new float[12];
        float[][] a = new float[3][3];

        temp[0] = quat[0]*quat[0];
        temp[1] = quat[1]*quat[1];
        temp[2] = quat[2]*quat[2];
        temp[3] = quat[3]*quat[3];

        temp[4] = quat[0]*quat[1]* (float)2.0;
        temp[5] = quat[0]*quat[2]* (float)2.0;
        temp[6] = quat[0]*quat[3]* (float)2.0;

        temp[7] = quat[1]*quat[2]* (float)2.0;
        temp[8] = quat[1]*quat[3]* (float)2.0;

        temp[9] = quat[2]*quat[3]* (float)2.0;

        temp[10] = temp[0]-temp[1];
        temp[11] = temp[3]-temp[2];

        a[0][0] = temp[10]+temp[11];
        a[1][1] = -temp[10]+temp[11];
        a[2][2] = -temp[0]-temp[1]+temp[2]+temp[3];
        a[0][1] = temp[4] + temp[9];
        a[1][0] = temp[4] - temp[9];
        a[1][2] = temp[6] + temp[7];
        a[2][1] = -temp[6] + temp[7];
        a[0][2] = temp[5] - temp[8];
        a[2][0] = temp[5] + temp[8];

        return a;
    }

    /**
     *
     * @return quaternion and acceleration
     * @throws IOException
     */
    public float[] getQuaternionAndAcceleration() throws IOException
    {
        byte[] command = {1,0x0B,0,0,0,0,0,0};
        byte[] result = sendCommand(command, 32);

         // Get result
        byte[] quaternion1 = {result[7],result[6],result[5],result[4]};
        byte[] quaternion2 = {result[11],result[10],result[9],result[8]};
        byte[] quaternion3 = {result[15],result[14],result[13],result[12]};
        byte[] quaternion4 = {result[19],result[18],result[17],result[16]};

        byte[] accelX = {result[23],result[22],result[21],result[20]};
        byte[] accelY = {result[27],result[26],result[25],result[24]};
        byte[] accelZ = {result[31],result[30],result[29],result[28]};

        // Convert byte arrays to floats
        DataInputStream qauternionStream1 = new DataInputStream(new ByteArrayInputStream(quaternion1));
        DataInputStream qauternionStream2 = new DataInputStream(new ByteArrayInputStream(quaternion2));
        DataInputStream qauternionStream3 = new DataInputStream(new ByteArrayInputStream(quaternion3));
        DataInputStream qauternionStream4 = new DataInputStream(new ByteArrayInputStream(quaternion4));
        DataInputStream xStream = new DataInputStream(new ByteArrayInputStream(accelX));
        DataInputStream yStream = new DataInputStream(new ByteArrayInputStream(accelY));
        DataInputStream zStream = new DataInputStream(new ByteArrayInputStream(accelZ));

        float q1 = qauternionStream1.readFloat();
        float q2 = qauternionStream2.readFloat();
        float q3 = qauternionStream3.readFloat();
        float q4 = qauternionStream4.readFloat();
        float x = xStream.readFloat();
        float y = yStream.readFloat();
        float z = zStream.readFloat();

        float[] values = {q1,q2,q3,q4,x,y,z};

        return values;
    }
}
