package primitives;
/**
 * Class Point3D is a foundry object in geometry - large point 3 coordinates
 */
public class Point3D {

    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    //static Point3D for origin point (0,0,0)
    public final static Point3D ZERO=new Point3D(0d,0d,0d);

    /**
     * constructor for 3D point that gets 3 numbers
     * @param x value for creating x coordinate
     * @param y value for creating y coordinate
     * @param z value for creating z coordinate
     */
    public Point3D(double x, double y, double z) {

        _x=new Coordinate(x);
        _y=new Coordinate(y);
        _z=new Coordinate(z);
    }

    /**
     * Adding a vector to a point - Returns a new point
     * @param vector
     * @return new point
     */
    public Point3D add(Vector vector){
        double x=_x.coord+vector._head._x.coord;
        double y=_y.coord+vector._head._y.coord;
        double z=_z.coord+vector._head._z.coord;

        return new Point3D(x,y,z);
    }

    /**
     * Vector subtraction - receives a second point in the parameter
     * @param point3D
     * @return vector from the second point to the point on which the operation is performed
     */
    public Vector subtract(Point3D point3D) {
        double x=_x.coord-point3D._x.coord;
        double y=_y.coord-point3D._y.coord;
        double z=_z.coord-point3D._z.coord;

        return new Vector(x,y,z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x +","+ _y +
                 ","+_z +
                '}';
    }

    /**
     * The distance between two points squared
     * @param other
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2
     */
    public double distanceSquared(Point3D other) {
        final double x1 = _x.coord;
        final double y1 = _y.coord;
        final double z1 = _z.coord;
        final double x2 = other._x.coord;
        final double y2 = other._y.coord;
        final double z2 = other._z.coord;

        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));
    }

    /**
     * The distance between two points using distanceSquared
     * @param other
     * @return
     */
    public  double distance(Point3D other){

        return Math.sqrt(distanceSquared(other));
    }

    public double getX() {
        return _x.coord;
    }

    public double getY() {
        return _y.coord;
    }

    public double getZ() {
        return _z.coord;
    }


}
