/*
 * SunSpotApplication.java
 *
 * Created on 15-feb-2011 15:30:09;
 */

package org.sunspotworld;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.IEEEAddress;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.sunspotworld.math.Vector3;
import org.sunspotworld.vectornav.VN100;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class SunSpotApplication extends MIDlet {

    private VN100 vn;

    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start();   // monitor the USB (if connected) and recognize commands from host

        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Radio Address: " + IEEEAddress.toDottedHex(ourAddr));

        // VectorNav
        vn = VN100.getInstance();
        try {
            System.out.println();
            System.out.println("Model Number: " + vn.getModelNumber());
            System.out.println("Serial Number: " + vn.getSerialNumber());
            System.out.println("Hardware Revision: " + vn.getHardwareRevision());
            System.out.println("Firmware Version: " + vn.getFirmwareVersion());
            System.out.println("Baud Rate: " + vn.getBaudRate());


            boolean running = true;

            long previousTime = System.currentTimeMillis();
            long currentTime = 0;
            long deltaTime = 0;
            long elapsedTime = 0;

            float position = 0;
            float velocity = 0;

            while (running) {
                currentTime = System.currentTimeMillis();
                deltaTime = currentTime - previousTime;
                elapsedTime += deltaTime;
                previousTime = currentTime;

                Vector3 accel = new Vector3(vn.getAcceleration());

                if (accel.x < 1 && accel.x > -1)
                    accel.x = 0;

                accel.divide(1000000);

                position += (velocity * deltaTime) + (0.5 * accel.x * (deltaTime * deltaTime));
                velocity += accel.x * deltaTime;

                if (elapsedTime > 1000) {
                    System.out.println();
                    System.out.println("Accel (x): " + (accel.x * 1000000));
                    System.out.println("Position: " + position);
                    System.out.println("Velocity: " + (velocity * 1000));
                    elapsedTime = 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true the MIDlet must cleanup and release all resources.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
