package geometries;

import primitives.Point3D;

public class Sphere {
    Point3D _center;
    double _radius;

    /**
     *
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }
}
