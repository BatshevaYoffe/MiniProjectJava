package primitives;

import elements.LightSource;

import static geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * Class Ray is the set of points on a straight line that are on one side relatively
 * To a given point on the line called the beginning of the foundation. Defined by point and direction (unit vector)
 */
public class Ray {
    //Size of first moving rays for shading rays
    private static final double DELTA = 0.1;

    private final Point3D _p0;
    private final Vector _dir;

    /**
     * constructor.
     *
     * @param point3D point 3D
     * @param lightSource light Source
     * @param n
     */
    public Ray(Point3D point3D, LightSource lightSource, Vector n) {
        Vector l = lightSource.getL(point3D).scale(-1);
        Vector _delta = n.scale(n.dotProduct(l) > 0 ? DELTA : -DELTA);
        _p0 = point3D.add(_delta);
        _dir = l;

    }

    /**
     * constructor
     * the new ray Minimal distance from the original point
     * @param point3D
     * @param dir
     * @param n
     */
    public Ray(Point3D point3D, Vector dir, Vector n) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA);
        Point3D point = point3D.add(delta);
        _dir = dir;
        _p0 = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    /**
     * contractor (normalized the vector)
     *
     * @param p0  point
     * @param dir vector that not necessarily normalized
     */
    public Ray(Point3D p0, Vector dir) {
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * @return the point
     */
    public Point3D getP0() {
        return _p0;
    }

    /**
     * @return the ray vector
     */
    public Vector getDir() {
        return new Vector(_dir._head);
    }

    /**
     * calculate point on ray P=P_0+t∙v
     *
     * @param t
     * @return point
     */
    public Point3D getPoint(double t) {
        Point3D p;
        p = _p0.add(_dir.scale(t));
        return p;
    }

    /**
     * @param intersection Collection of points
     * @return the point closest to the beginning of the ray
     */
    public Point3D getClosestPoint(List<Point3D> intersection) {
        Point3D result = null;
        //if there are not intersection
        if (intersection == null) {
            return null;
        }
        //the smallest distance
        double distance = Double.MAX_VALUE;
        for (Point3D p : intersection) {
            double newDis = p.distance(_p0);
            if (newDis < distance) {
                distance = newDis;
                result = p;
            }
        }

        return result;
    }

    /**
     * find the closest geoPoint to Ray origin
     *
     * @param geoPointList
     * @return closest geoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        GeoPoint result = null;
        //if there are not intersection
        if (geoPointList == null) {
            return null;
        }
        //the smallest distance
        double distance = Double.MAX_VALUE;
        for (var gp : geoPointList) {
            double newDis = gp.point.distance(_p0);
            if (newDis < distance) {
                distance = newDis;
                result = gp;
            }
        }

        return result;
    }
}
