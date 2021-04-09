package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    List<Point3D> findIntsersection(Ray ray);

}
