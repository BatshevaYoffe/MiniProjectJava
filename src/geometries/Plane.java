package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class Plan (point in vector vertical space).
 */
public class Plane implements Geometry{
    final Point3D _q0;
    final Vector _normal;

    /**
     * constructor that get 3 points and calculate the normal, if 2 of the point coming together or on the same line=> then an exception will be thrown
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     *
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0 = p1;
        Vector U = p2.subtract(p1);//vector from p1 towards p2
        Vector V = p3.subtract(p1);//vector from p1 towards p2

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
        return _normal;
    }

    /**

     * @return
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }

    @Override
    public List<Point3D> findIntsersection(Ray ray) {
        Point3D p0 = ray.getP0();
        Vector v=ray.getDir();

        Vector N=_normal;


        if(_q0.equals(p0)){
            return null;
        }
        //numerator
        double Nq0_p0=alignZero(N.dotProduct(_q0.subtract(p0)));
        //denominator:
        double nv=alignZero(N.dotProduct(v));

        //the ray parallel to the plane
        if(isZero(nv)){
            return null;
        }

        double t=alignZero(Nq0_p0/nv);
        if(t>0){
            Point3D p= ray.getPoint(t);
            return (List.of(p));
        }

        return null;
    }




}
