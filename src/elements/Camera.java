package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * camera class
 */
public class Camera {
    //point of the camera location
    private final Point3D _p0;
    //Vectors of the camera direction
    final private Vector _vTo;
    final private Vector _vUp;
    //vRight=vTo*vUp
    final private Vector _vRight;
    private double _width;
    private double _height;
    private double _distance;

    /**
     * Builder with parameters for position values  two vectors of direction - forward and up
     *
     * @param p0  point of the camera location
     * @param vTo vTo vector
     * @param vUp vUp vector
     */
    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        //Make sure the vectors are vertical
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vTo and vUp not orthogonal");
        }
        _p0 = p0;
        //Make sure the vectors are normalized
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        //create the V-right vector
        _vRight = _vTo.crossProduct(_vUp);
    }

    //get functions
    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    public double getDistance() {
        return _distance;
    }

//setters using method chaining

    /**
     * set View Plane Size
     * @param width camera width
     * @param height camera height
     * @return "this": camera current instance
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * set Distance function
     * @param distance the distance
     * @return the object
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * construct Ray Through Pixel
     * @param nX represents the number of columns (row width)
     * @param nY represents the number of rows
     * @param j  columns index
     * @param i  row index
     * @return Ray through pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        //Image center
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        //Ratio (pixel width & height)
        double Ry = _height / nY;
        double Rx = _width / nX;
        //Pixel[i,j] center
        Point3D Pij = Pc;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(Yi));
        }

        Vector Vij = Pij.subtract(_p0);
        return new Ray(_p0, Vij);
    }


}
