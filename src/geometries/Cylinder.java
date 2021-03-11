package geometries;

import primitives.Ray;

/**
 * Cylinder that extends from Tube
 */
public class Cylinder extends Tube{
    double _height;

    /**
     * constructor that get ray radius and height,
     * turns father constructor
     * @param axisRay
     * @param radius
     * @param height
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }
}
