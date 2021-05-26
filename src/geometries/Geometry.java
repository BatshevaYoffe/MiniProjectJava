package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all the geometries that have a normal from them
 */
public abstract class Geometry implements Intersectable {
    protected Color _emission = Color.BLACK;
    protected Material _material = new Material();

    /**
     * get emission function
     *
     * @return the geometry emission
     */
    public Color getEmission() {

        return _emission;
    }

    /**
     * @param emission set function for emission
     * @return The object itself
     */
    public Geometry setEmission(Color emission) {
        _emission = emission;
        return this;
    }

    /**
     * get material function
     *
     * @return geometric material
     */
    public Material getMaterial() {
        return _material;
    }

    /**
     * set function
     *
     * @param material of the geometric
     * @return the object
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point3D point);
}
