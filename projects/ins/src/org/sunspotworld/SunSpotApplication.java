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

        // vectornav
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

            double distance = 0;
            double velocity = 0;

            while (running) {
                // init
                currentTime = System.currentTimeMillis();
                deltaTime = currentTime - previousTime;
                elapsedTime += deltaTime;
                // get acceleration from vectornav
                Vector3 acceleration = new Vector3(vn.getAcceleration());

                // hack
                if (acceleration.lessThan(1) && acceleration.greaterThan(-1))
                    acceleration.set(0);

                // calc
                double dtSeconds = deltaTime / 1000.0;
                distance += (velocity * dtSeconds)+ (0.5 * acceleration.x * (dtSeconds * dtSeconds));
                velocity += acceleration.x * dtSeconds;

                // print
                if (elapsedTime > 1000) { // magic number :D
                    System.out.println();
                    System.out.println("Acceleration: " + acceleration.toString());
                    System.out.println("Distance: " + distance);
                    System.out.println("Velocity: " + velocity);
                    elapsedTime = 0;
                }

                // reset time
                previousTime = currentTime;
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
