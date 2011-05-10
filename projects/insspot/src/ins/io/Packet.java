package ins.io;

import java.io.IOException;

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnImpl;

public class Packet extends Radiogram {

	public Packet(int size, RadiogramConnImpl connection) {
		super(size, connection);
	}
	
	public String readLine() throws IOException {
		int stringLength = readInt();
		
		byte[] bytes = new byte[stringLength];
		this.readFully(bytes);
		
		return new String(bytes);
	}
	
	public String readString() throws IOException {
		return readLine();
	}

	public void writeBytes(String arg0) throws IOException { }
	
}
