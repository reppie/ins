package ins.lib.math.util;

public class Convert {
	private static final int MASK = 0xFF;
	
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
    
    public static byte[] intToByteArray2(int param) {
		byte[] result = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (result.length - 1 - i) * 8;
			result[i] = (byte) ((param >>> offset) & MASK);
		}
		return result;
	}

    public static byte[] longToByteArray(long l) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++){
            bytes[i] = (byte) (l >>> (i * 8));
		}
		return bytes;
    }
    
    public static byte[] floatToByteArray(float f) {
		int i = Float.floatToRawIntBits(f);
		return intToByteArray2(i);
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
	            integer |= (bytes[i] & MASK) << (i << 3);
		}
		return integer;
    }

    public static int byteArrayToInt(byte[] bytes, int offset) {
		int integer = 0;
		for (int i = 0; i < 4; i++) {
	            integer |= (bytes[i + offset] & MASK) << (i << 3);
		}
		return integer;
    }
    
	public static float byteArrayToFloat(byte bytes[]) {
		int bits = 0;
		int i = 0;
		for (int j = 3; j >= 0; j--) {
			bits |= ((int) bytes[i] & MASK) << (j * 8);
			i++;
		}
		return Float.intBitsToFloat(bits);
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
