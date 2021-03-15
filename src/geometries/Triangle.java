package geometries;

import primitives.Point3D;

/**
 * Class Triangle that extends from Polygon
 */
public class Triangle extends Polygon{
    /**
     *constructor that get 3 points and operates the father constructor
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Triangle(Point3D p1,Point3D p2,Point3D p3) {
        super(p1,p2,p3);
    }

}
