package primitives;

import static primitives.Point3D.ZERO;

/**
 * Class Vector is a fundamental object in geometry with direction and size, defined by the end point (when the starting point -
 *          First the axes).
 */
public class Vector {
    Point3D _head;

    /**
     * Vector constructor who gets 3 double numbers
     * @param x value for X Coordinate of the head Point
     * @param y value for Y Coordinate of the head Point
     * @param z value for Z Coordinate of the head Point
     */
    public Vector(double x, double y, double z) {

        this(new Point3D(x, y, z));
    }

    /**
     *Vector multiplication
     * @param vector Vector multiplied by the current vector
     * @return new vector that stands for the two existing vectors
     */
    public Vector crossProduct(Vector vector) {
        double u1=_head._x.coord;
        double u2=_head._y.coord;
        double u3=_head._z.coord;
        double v1=vector._head._x.coord;
        double v2=vector._head._y.coord;
        double v3=vector._head._z.coord;

        return new Vector(new Point3D(
                u2*v3-u3*v2,
                u3*v1-u1*v3,
                u1*v2-u2*v1
        ));
    }

    /**
     * Scalar product
     * @param vector Vector multiplied by the scalar product of the current vector
     * @return Scalar product
     */
    public double dotProduct(Vector vector) {

        double x=_head._x.coord*vector._head._x.coord;
        double y=_head._y.coord*vector._head._y.coord;
        double z=_head._z.coord*vector._head._z.coord;

        return x+y+z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * primary constructor for vector that get point
     * @param head the point
     */
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("head of vector can not be point (0,0,0)");
        }
        _head = new Point3D(head._x.coord,head._y.coord, head._z.coord);
    }

    /**
     * get accessor for head Point
     * @return head
     */
    public Point3D getHead() {

        return _head;
    }

    /**
     * subtraction of vectors
     * @param v
     * @return new Vector(u-v)
     */
    public Vector subtract(Vector v) {
        double x = _head._x.coord - v._head._x.coord;
        double y = _head._y.coord - v._head._y.coord;
        double z = _head._z.coord - v._head._z.coord;

        return new Vector(new Point3D(x, y, z));
    }

    /**
     * Calculate the length of the vector squared
     * @return
     */
    public double lengthSquared() {
        double xx = _head._x.coord * _head._x.coord;
        double yy = _head._y.coord * _head._y.coord;
        double zz = _head._z.coord * _head._z.coord;

        return (xx + yy + zz);
    }

    /**
     * Calculating the length of the vector and using lengthSquared function
     * @return length of the vector
     */
    public double length() {

        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizing the current Vector
     * @return
     */
    public Vector normalize() {

        double len = this.length();
        if (len == 0) {
            throw new ArithmeticException("cannot divide by zero");
        }
        double x = _head._x.coord / len;
        double y = _head._y.coord / len;
        double z = _head._z.coord / len;

        Point3D newhead = new Point3D(x, y, z);
        if (ZERO.equals(newhead)) {
            throw new IllegalArgumentException("point (0,0,0)");
        }
        this._head = newhead;
        return this;
    }

    /**
     * A normalization operation that returns a new normalized vector in the same direction as the original vector (using normalize function)
     * @return
     */
    public Vector normalized() {
        Vector result=new Vector(_head);
        result.normalize();
        return result;
    }

    /**
     * Vector multiplication by scale and return new vector
     * @param scale the number that doubled the vector
     * @return new vector
     */
    public Vector scale(double scale){
        if(Double.compare(scale,0d)== 0){
            throw new IllegalArgumentException("scaling factor is zero");
        }
        return new Vector(new Point3D(scale*_head._x.coord,scale*_head._y.coord,scale*_head._z.coord));
    }
}
