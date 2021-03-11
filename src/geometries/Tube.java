package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * 3D body Tube
 */
public class Tube {
    Ray _axisRay;
    double _radius;

    /**
     *constructor that get Ray and radius
     * @param axisRay the Tube Ray
     * @param radius the Tube radius
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     *
     * @param point3D
     * @return null
     */
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
