package org.sunspotworld.math;

public class Vector3 {
    public static Vector3 ZERO = new Vector3(0, 0, 0);
    public static Vector3 ONE = new Vector3(1, 1, 1);
    public static Vector3 UNIT_X = new Vector3(1, 0, 0);
    public static Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public static Vector3 UNIT_Z = new Vector3(0, 0, 1);

    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
}
