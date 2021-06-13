package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * spot light class
 */
public class SpotLight extends PointLight {

    private final Vector centerDirection;

    /**
     * spot light constructor
     *
     * @param intensity intensity
     * @param position position
     * @param centerDirection center Direction
     */
    public SpotLight(Color intensity, Point3D position, Vector centerDirection) {
        super(intensity, position);
        this.centerDirection = centerDirection.normalized();
    }

    /**
     * get Intensity function
     *
     * @param point3D point 3D
     * @return the intensity of the color on the point
     */
    @Override
    public Color getIntensity(Point3D point3D) {
        Vector l = getL(point3D);
        Color intensity = super.getIntensity(point3D);

        return intensity.scale(Math.max(0, l.dotProduct(centerDirection)));
    }
}
