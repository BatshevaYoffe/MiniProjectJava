package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Geometries implements Intersectable {

    /**
     *
     */
    private List<Intersectable> _intersectables = new LinkedList<>();

    /**
     *
     * @param list non limited array of Geometry implementing Intersectable
     */
    public Geometries(Intersectable... list) {
        add(list);
    }

    public void add(Intersectable... list) {
        Collections.addAll(_intersectables, list);
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable geo : _intersectables) {
            List<GeoPoint> geoPoints = geo.findGeoIntersections(ray);
            if (geoPoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }

}
