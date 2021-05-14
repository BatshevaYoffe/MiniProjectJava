package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements  LightSource {

    private final Vector _direction;

    /**
     *
     * @param intensity light color
     * @param direction common direction of the Light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D point3D) {
        return _direction;
    }
}
