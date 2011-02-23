
package org.sunspotworld.math;

public class Convert {
    public static final int LITTLE_ENDIAN = 0;
    public static final int MIDDLE_ENDIAN = 1;
    public static final int BIG_ENDIAN = 2;

    public static int ENDIANNESS = BIG_ENDIAN;

    public static byte[] shortToByteArray(short s) {
        byte[] bytes = new byte[2];
        for (int i = 0; i < 2; i++){
            bytes[i] = (byte) (s >>> (i * 8));
        }
        return bytes;
    }

    public static byte[] intToByteArray(int integer) {
	byte[] bytes = new byte[4];
	for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) (integer >>> (i * 8));
	}
	return bytes;
    }

    public static byte[] longToByteArray(long l) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++){
            bytes[i] = (byte) (l >>> (i * 8));
	}
	return bytes;
    }

    public static byte[] hexStringToByteArray(String h) {
	byte[] bytes = new byte[h.length()/2];
	for (int i = 0; i < h.length()/2; i++) {
            bytes[i] = ((byte) Integer.parseInt(h.substring(i*2, (i*2)+2), 16));
	}
	return bytes;
    }

    public static int byteArrayToInt(byte[] bytes) {
	int integer = 0;
	for (int i = 0; i < 4; i++) {
            integer |= (bytes[i] & 0xFF) << (i << 3);
	}
	return integer;
    }

    public static int byteArrayToInt(byte[] bytes, int offset) {
	int integer = 0;
	for (int i = 0; i < 4; i++) {
            integer |= (bytes[i + offset] & 0xFF) << (i << 3);
	}
	return integer;
    }

    public static String byteArrayToHexString(byte[] bytes) {
	String string = new String();
	for (int i = 0; i < bytes.length; i++) {
            string += "[" + i + "] 0x" + Integer.toHexString(bytes[i]) + " = " + (char) bytes[i] + "\n";
	}
	return string;
    }

    public static int swapInt(int i) {
        return (i >>> 24) | (i << 24) | ((i << 8) & 0x00FF0000) | ((i >> 8) & 0x0000FF00);
    }
}
