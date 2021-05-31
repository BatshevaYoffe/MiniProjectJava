package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for point light
 */
public class PointLight extends Light implements LightSource {

    private final Point3D _position;
    //Discount coefficients with default values
    private double _kc = 1;
    private double _kl = 0;
    private double _kq = 0;

    //concatenation setters
    public PointLight setKc(double kc) {
        _kc = kc;
        return this;
    }

    public PointLight setKl(double kl) {
        _kl = kl;
        return this;
    }

    public PointLight setKq(double kq) {
        _kq = kq;
        return this;
    }

    //getters
    public double getKc() {
        return _kc;
    }

    public double getKl() {
        return _kl;
    }

    public double getKq() {
        return _kq;
    }

    /**
     * constructor for point light
     *
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * get function
     *
     * @param point3D
     * @return the intensity of the point
     */
    @Override
    public Color getIntensity(Point3D point3D) {
        double d = point3D.distance(_position);
        double attenuationFactor = 1d / (_kc + _kl * d + _kq * d * d);
        return _intensity.scale(attenuationFactor);
    }

    /**
     * @param point3D
     * @return the vector from the light to the point
     */
    @Override
    public Vector getL(Point3D point3D) {
        return point3D.subtract(_position).normalize();
    }

    /**
     * @param point3D
     * @return the distance between the Lighting source to the point
     */
    @Override
    public double getDistance(Point3D point3D) {
        return _position.distance(point3D);
    }
}