package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 *Sphere Class
 */
public class Sphere implements Geometry{
    private Point3D _center;
    private double _radius;

    @Override
    public Vector getNormal(Point3D point3D) {
        if(point3D.equals(_center)){
            throw new IllegalArgumentException("point can not be equals to the sphere center");
        }
        Vector O_P=point3D.subtract(_center);
        return O_P.normalize();
    }

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
