package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import javax.swing.plaf.basic.BasicArrowButton;
import java.util.List;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class 3D body Tube
 */
public class Tube extends Geometry {
    final Ray _axisRay;
    final double _radius;

    /**
     * getNormal override function
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D P0=_axisRay.getP0();
        Vector V=_axisRay.getDir();

        Vector P0_P= point.subtract(P0);
        double t= Util.alignZero(P0_P.dotProduct(V));

        if(Util.isZero(t)){
            return P0_P.normalize();
        }
        Point3D O=P0.add(V.scale(t));
        Vector O_P= point.subtract(O);
        return O_P.normalize();
    }


    /**
     *constructor that get Ray and radius
     * @param axisRay the Tube Ray
     * @param radius the Tube radius
     */
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    public Ray getAxis() {
        return _axisRay;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }
}
