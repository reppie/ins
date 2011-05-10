package ins.spot;

import ins.io.Packets;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.util.Utils;

public class Main extends MIDlet implements ISwitchListener  {
	public static final int PORT = 67;
	public static final String BASESTATION_ADDRESS = "0014.4F01.0000.37A4";
	
	public static final LEDColor DEFAULT_COLOR = LEDColor.ORANGE;
	
	private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    private ITriColorLED[] led = new ITriColorLED[8];
	
	private boolean running;
	
	private RadiogramConnection conn;
	private Datagram packet;
	
	public void switchPressed(SwitchEvent se) {
		
	}

	public void switchReleased(SwitchEvent se) {
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		
	}

	protected void pauseApp() {
		
	}

	protected void startApp() throws MIDletStateChangeException {
		for(int i = 0; i < 8; i++) {
			led[i] = leds.getLED(i);
			led[i].setColor(LEDColor.ORANGE);
			led[i].setOn();
			Utils.sleep(100);
		}
		led[0].setColor(LEDColor.GREEN);
		
		running = true;
		
		try {
			conn = (RadiogramConnection) Connector.open("radiogram://" + BASESTATION_ADDRESS + ":" + PORT);
			led[1].setColor(LEDColor.GREEN);
			packet = (Datagram) conn.newDatagram(5);
			led[2].setColor(LEDColor.GREEN);
			
			while (running) {
				//sendPing();
				Utils.sleep(1000);
			}
		} catch (IOException e) {
			led[3].setColor(LEDColor.RED);
		} finally {
			try {
				conn.close();
			} catch (IOException e) {
				led[4].setColor(LEDColor.RED);
			}
		}
	}

	private void sendPing() throws IOException {
		packet.reset();
		packet.writeByte(Packets.SEND_PING);
		packet.writeInt(0);
		conn.send(packet);
	}

	private void setColor(LEDColor color) {
		for (int i = 0; i < led.length; i++) {
			led[i].setColor(color);
		}
	}
}
