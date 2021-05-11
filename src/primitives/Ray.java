package primitives;

import java.util.List;

/**
 * Class Ray is the set of points on a straight line that are on one side relatively
 * To a given point on the line called the beginning of the foundation. Defined by point and direction (unit vector)
 */
public class Ray {
    private Point3D _p0;
    private Vector _dir;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }
    /**
     * contractor (normalized the vector)
     * @param p0 point
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
     * @param t
     * @return point
     */
    public Point3D getPoint(double t){
        Point3D p;
        p=_p0.add(_dir.scale(t));
        return p;
    }

    /**
     *
     * @param intersection Collection of points
     * @return the point closest to the beginning of the ray
     */
    public Point3D getClosestPoint(List<Point3D> intersection) {
        Point3D result=null;
        //if there are not intersection
        if (intersection==null){
            return null;
        }
        //the smallest distance
        double distance=Double.MAX_VALUE;
        for(Point3D p:intersection){
            double newDis=p.distance(_p0);
            if(newDis<distance){
                distance=newDis;
                result=p;
            }
        }

        return result;
    }
}
