package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane {
    Point3D _q0;
    Vector _normal;

    /**
     * constructor that get 3 points and calculate the normal
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p2;
        Vector U = p1.subtract(p2);
        Vector V = p3.subtract(p2);

        Vector N = U.crossProduct(V);
        N.normalize();
        _normal = N;
    }
    /**
     * constructor that get point and vector(normalized the vector)
     * @param point3D the new point
     * @param vectorN the new vector
     */
    public Plane(Point3D point3D, Vector vectorN) {
        _q0 = point3D;
        _normal = vectorN.normalized();
    }

    /**
     *
     * @param point3D
     * @return
     */
    public Vector getNormal(Point3D point3D) {
        return null;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }
}
