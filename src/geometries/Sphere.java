package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere Class
 */
public class Sphere extends Geometry {
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
     * sphere constructor
     * @param center
     * @param radius
     */
    public Sphere(Point3D center, double radius) {
        _center = center;
        _radius = radius;
    }

    /**
     * find geo points intersections with sphere
     * @param ray
     * @return list of geo points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
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
            Point3D p1=ray.getPoint(t1);
            Point3D p2=ray.getPoint(t2);
            return (List.of( new GeoPoint(this,p1),new GeoPoint(this,p2)));
        }
        if(t1>0){
            Point3D p1=ray.getPoint(t1);
            return (List.of(new GeoPoint(this,p1)));
        }
        if(t2>0){
            Point3D p2=ray.getPoint(t2);
            return (List.of(new GeoPoint(this,p2)));
        }

        return null;
    }
    
}
