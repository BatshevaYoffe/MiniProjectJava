package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere Class
 */
public class Sphere implements Geometry {
    final Point3D _center;
    final double _radius;

    @Override
    public Vector getNormal(Point3D point3D) {
        if (point3D.equals(_center)) {
            throw new IllegalArgumentException("point can not be equals to the sphere center");
        }
        Vector O_P = point3D.subtract(_center);
        return O_P.normalize();
    }

    /**
     * constructor
     *
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    @Override
    public List<Point3D> findIntersection(Ray ray) {
        Point3D p0 = ray.getP0();
        Point3D O = _center;
        Vector V = ray.getDir();

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if(d>=_radius){
            return null;
        }
        double th=Math.sqrt(_radius*_radius-d*d);
        double t1=tm-th;
        double t2=tm+th;

        if(t1>0&&t2>0){
            Point3D p1=p0.add(V.scale(t1));
            Point3D p2=p0.add(V.scale(t2));
            return (List.of(p1,p2));
        }
        if(t1>0){
            Point3D p1=p0.add(V.scale(t1));
            return (List.of(p1));
        }
        if(t2>0){
            Point3D p2=p0.add(V.scale(t2));
            return (List.of(p2));
        }

        return null;

    }

}
