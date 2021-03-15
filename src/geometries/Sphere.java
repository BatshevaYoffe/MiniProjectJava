package geometries;

import primitives.Point3D;

/**
 *Sphere Class
 */
public class Sphere {
    private Point3D _center;
    private double _radius;

    /**
     * constructor
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }
}
