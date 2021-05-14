package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight  extends Light implements LightSource{

    private final Point3D _position;
    private double _kc =1;
    private double _kl =0;
    private double _kq =0;

    public double getKc() {
        return _kc;
    }

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

    public double getKl() {
        return _kl;
    }


    public double getKq() {
        return _kq;
    }

    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        _position = position;
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        double d = point3D.distance(_position);
        double attenuationFactor = 1d/(_kc + _kl *d +_kq * d *d);
        return _intensity.scale(attenuationFactor);
    }

    @Override
    public Vector getL(Point3D point3D) {
        return point3D.subtract(_position).normalize();
    }
}