package insexcel;

public class Main {

	public Main() {
		ComPort COMReader = new ComPort("COM6");
        COMReader.start();
	}

	public static void main(String[] args) {
		new Main();
	}

}
