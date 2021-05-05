package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase {
    public  BasicRayTracer(Scene scene){
        super(scene);
    }
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersection=_scene.geometries.findIntsersection(ray);
        if(intersection==null){
            return _scene.backGroudColor;
        }
        Point3D closestPoint=ray.getClosestPoint(intersection);
        return calcColor(closestPoint);
    }

    private Color calcColor(Point3D closestPoint) {
        return _scene.ambientLight.getIntensity();
    }
}
