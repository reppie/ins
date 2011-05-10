package ins.math;

public class Vector3 {
    public static Vector3 ZERO = new Vector3(0, 0, 0);
    public static Vector3 ONE = new Vector3(1, 1, 1);
    public static Vector3 UNIT_X = new Vector3(1, 0, 0);
    public static Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public static Vector3 UNIT_Z = new Vector3(0, 0, 1);

    public float x;
    public float y;
    public float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(float[] a) {
        this.x = a[0];
        this.y = a[1];
        this.z = a[2];
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void set(float f) {
        this.x = f;
        this.y = f;
        this.z = f;
    }

    public void divide(float f) {
        this.x /= f;
        this.y /= f;
        this.z /= f;
    }

    public boolean greaterThan(float f) {
        if (this.x > f || this.y > f || this.z > f)
            return true;
        return false;
    }

    public boolean lessThan(float f) {
        if (this.x < f || this.y < f || this.z < f)
            return true;
        return false;
    }

    public String toString() {
        return this.x + ";" + this.y + ";" + this.z;
    }
}
