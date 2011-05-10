package ins.math;

import com.sun.squawk.util.MathUtils;

public class Quaternion {
    public static Quaternion IDENTITY = new Quaternion(0, 0, 0, 0);

    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;      
    }
    
    public Quaternion(float[] a) {
        this.x = a[0];
        this.y = a[1];
        this.z = a[2];
        this.w = a[3];
    }

    public Quaternion(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
    }
    
    public float getAccelerationX() {
    	return (float)(Math.sin(Math.toRadians(this.getRoll())) * Math.cos(Math.toRadians(this.getPitch())));
    }
    
    public float getAccelerationY() {
    	return (float)(Math.sin(Math.toRadians(this.getPitch())));
    }
    
    public float getYaw() {
    	return (float) Math.toDegrees(MathUtils.atan2(2*(x*y + w*z), w*w - z*z - y*y + x*x));
    }    
    
    public float getPitch() {
    	return (float) Math.toDegrees(MathUtils.asin(-2*(x*z - y*w)));
    } 
    
    public float getRoll() {
    	return (float) Math.toDegrees(MathUtils.atan2(2*(y*z + x*w), w*w + z*z - y*y + x*x));
    }
    
    public String toString() {
    	return this.x + ";" + this.y + ";" + this.z + ";" + this.w;
    }
    
}
