package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

    private final static Random random = new Random();
    //Divide each pixel by x to calculate the rays surrounding the beam randomly from the center of the pixel
    final int x = 9;


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
     *
     * @param width  camera width
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
     *
     * @param distance the distance
     * @return the object
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     * construct Ray Through Pixel
     *
     * @param nX represents the number of columns (row width)
     * @param nY represents the number of rows
     * @param j  columns index
     * @param i  row index
     * @return Ray through pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        //Image center
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        Point3D Pij = getPij(Pc, _height, _width, nX, nY, j, i);

        Vector Vij = Pij.subtract(_p0);
        return new Ray(_p0, Vij);
    }

    /**
     * Auxiliary function for calculating the pixel center
     *
     * @param Pc     The center of the outer pixel/view plane, within that pixel
     * @param height The height of the outer pixel/view plane, within that pixel
     * @param width  The width of the outer pixel/view plane, within that pixel
     * @param nX     represents the number of columns (row width) of the outer pixel/view plane, within that pixel
     * @param nY     represents the number of rows of the outer pixel/view plane, within that pixel
     * @param j      columns index
     * @param i      row index
     * @return Pixel center point3D
     */
    private Point3D getPij(Point3D Pc, double height, double width, int nX, int nY, int j, int i) {
        //Ratio (pixel width & height)
        double Ry = height / nY;
        double Rx = width / nX;
        //Pixel[i,j] center
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        Point3D Pij = Pc;

        if (!isZero(Xj)) {
            Pij = Pij.add(_vRight.scale(Xj));
        }

        if (!isZero(Yi)) {
            Pij = Pij.add(_vUp.scale(Yi));
        }
        return Pij;
    }

    /**
     * construct Rays Through Pixel
     *
     * @param nX represents the number of columns (row width)
     * @param nY represents the number of rows
     * @param j  columns index
     * @param i  row index
     * @return Ray through pixel
     */
    public List<Ray> constructRaysThroughPixel(Ray ray, int nX, int nY, int j, int i) {
        //list of rays Through Pixel
        List<Ray> raysThroughPixel = new LinkedList<>();
        //List of starting point of the rays scattered in the pixel
        List<Point3D> points = new LinkedList<>();
        //Image center
        Point3D Pc = _p0.add(_vTo.scale(_distance));
        //Pixel[i,j] center
        Point3D Pij = getPij(Pc, _height, _width, nX, nY, j, i);
        points.add(Pij);

        //int x = 9;

        //Calculate the length and width of pixels in the view plan
        double Rx = _width / nX;
        double Ry = _height / nY;
        //Calculate the length and width of pixels that make up a pixel in a view plan
        double Ry1 = Ry / x;
        double Rx1 = Rx / x;
        // We will go over each part of the original pixel parts in order to calculate the rays coming out of it coming out of it
        for (int sx = 0; sx < x; sx++) {
            // We will go over each part of the pixel parts that make up a pixel in the original view plan in order to calculate the beam coming out of it.
            for (int sy = 0; sy < x; sy++) {
                Point3D Pij1 = getPij(Pij, Ry, Rx, x, x, sx, sy);
                //Calculate random numbers in the height and width ranges of the pixel in order to find a random point within the pixel
                double rx = (double) ((Math.random() * ((Rx1 * 0.5d) - (-Rx1 * 0.5d) + 1)) - Rx1 * 0.5d);
                double ry = (double) ((Math.random() * ((Ry1 * 0.5d) - (-Ry1 * 0.5d) + 1)) - Ry1 * 0.5d);

                if (!isZero(rx)) {
                    Pij1 = Pij1.add(_vRight.scale(rx));
                }

                if (!isZero(ry)) {
                    Pij1 = Pij1.add(_vUp.scale(ry));
                }

                boolean flag = false;
                //Go over the list of points where the rays pass to check that the new point does not already exist.
                for (var point : points) {
                    if (point.equals(Pij1)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    points.add(Pij1);
                    //Create ray from the point in the camera to the point in the pixel
                    Vector Vij = Pij1.subtract(_p0).normalize();
                    Ray newRay = new Ray(_p0, Vij);

                    //Add the ray to the list of rays coming out of the pixel
                    raysThroughPixel.add(newRay);

                }
            }
        }
        return raysThroughPixel;

    }

}