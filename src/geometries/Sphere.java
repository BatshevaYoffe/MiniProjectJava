package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Sphere Class
 */
public class Sphere extends Geometry {
    final Point3D _center;
    final double _radius;

    /**
     * @param point3D point 3D
     * @return normal vector
     */
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
     *
     * @param radius of the sphere
     * @param center of the sphere
     */
    public Sphere(double radius, Point3D center) {
        _radius = radius;
        _center = center;
    }

    /**
     * find geo points intersections with sphere
     *
     * @param ray ray
     * @return list of geo points
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.getP0();
        Point3D O = _center;
        Vector V = ray.getDir();

        Vector U = O.subtract(p0);
        double tm = alignZero(V.dotProduct(U));
        if (_center.equals(p0)) {   // p0 == _center
            return List.of(new GeoPoint(this, (ray.getPoint(this._radius))));
        }

        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= _radius) {
            return null;
        }
        double dSquared = (tm == 0) ? U.lengthSquared() : U.lengthSquared() - tm * tm;
        double thSquared = alignZero(this._radius * this._radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        //double th = Math.sqrt(_radius * _radius - d * d);

        double t1 = tm - th;
        double t2 = tm + th;
        double t1dist = alignZero(maxDistance - t1);
        double t2dist = alignZero(maxDistance - t2);

        if (t1 <= 0 && t2 <= 0) {
            return null;
        }

        if (t1 > 0 && t2 > 0) {
            if (t1dist > 0 && t2dist > 0) {
                return List.of(
                        new GeoPoint(this, (ray.getPoint(t1))),
                        new GeoPoint(this, (ray.getPoint(t2)))); //P1 , P2
            } else if (t1dist > 0) {
                return List.of(
                        new GeoPoint(this, (ray.getPoint(t1))));
            } else if (t2dist > 0) {
                return List.of(
                        new GeoPoint(this, (ray.getPoint(t2))));
            }
        }

        if ((t1 > 0) && (t1dist > 0))
            return List.of(new GeoPoint(this, (ray.getPoint(t1))));
        else if ((t2 > 0) && (t2dist > 0))
            return List.of(new GeoPoint(this, (ray.getPoint(t2))));
        return null;


//        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point3D p1 = ray.getPoint(t1);
//            Point3D p2 = ray.getPoint(t2);
//            return (List.of(new GeoPoint(this, p1), new GeoPoint(this, p2)));
//        }
//        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
//            Point3D p1 = ray.getPoint(t1);
//            return (List.of(new GeoPoint(this, p1)));
//        }
//        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
//            Point3D p2 = ray.getPoint(t2);
//            return (List.of(new GeoPoint(this, p2)));
//        }
//
//        return null;
    }

}
