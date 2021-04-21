package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Triangle that extends from Polygon
 */
public class Triangle extends Polygon{

    /**
     *
     * @param point3D
     * @return
     */
    //public Vector getNormal(Point3D point3D) {
     //   return null;
    //}
    /**
     *constructor that get 3 points and operates the father constructor
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3) {
        super(p1,p2,p3);
    }

    @Override
    public List<Point3D> findIntsersection(Ray ray) {
        return super.findIntsersection(ray);
    }
}
