package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends  PointLight {

    private  final Vector centerDirection;

    public SpotLight(Color intensity, Point3D position, Vector centerDirection) {
        super(intensity, position);
        this.centerDirection = centerDirection.normalized();
    }

    @Override
    public Color getIntensity(Point3D point3D) {
        Vector l = getL(point3D);
        Color intensity =  super.getIntensity(point3D);

        return intensity.scale(Math.max(0,l.dotProduct(centerDirection)));
    }
}
