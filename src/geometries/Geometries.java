package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class
 *  Department of the association of Geometric bodies
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _intersectables = new LinkedList<>();

    /**
     * @param list non limited array of Geometry implementing Intersectable
     */
    public Geometries(Intersectable... list) {
        add(list);
    }

    public void add(Intersectable... list) {
        Collections.addAll(_intersectables, list);
    }

    /**
     * @param ray
     * @return list of geoPoints
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable geo : _intersectables) {
            //get intersections points of a particular item from _intersectables
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray);
            if (geoPoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(geoPoints);
            }
        }
        return result;
    }

}
