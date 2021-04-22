package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private List<Intersectable>_intersectables=new LinkedList<>();

    public Geometries(Intersectable... list){
        add(list);
    }

    private void add(Intersectable... list) {
        Collections.addAll(_intersectables, list);
    }

    @Override
    public List<Point3D> findIntsersection(Ray ray) {
        List<Point3D> result=null;
        for(Intersectable geo:_intersectables){
            List<Point3D> geoPoints=geo.findIntsersection(ray);
            if(geoPoints!=null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }

        return result;
    }

}
