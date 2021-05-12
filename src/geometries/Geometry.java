package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
/**
 * interface for all the geometries that have a normal from them
 */
public abstract class Geometry implements Intersectable{
   protected Color _emission = Color.BLACK;

    public Color getEmission() {
        return _emission;
    }

    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point3D point);
}
