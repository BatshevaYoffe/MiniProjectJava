package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase {
    /**
     *
     * @param scene
     */
    public  BasicRayTracer(Scene scene){
        super(scene);
    }

    /**
     * Look for cuts between the ray and the 3D model of the scene
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersection=_scene.geometries.findIntsersection(ray);
        //if there are not intersection return The background color of the scene
        if(intersection==null){
            return _scene.backGroudColor;
        }
        //The point closest to the beginning of the foundation will be found
        Point3D closestPoint=ray.getClosestPoint(intersection);
        //the point color
        return calcColor(closestPoint);
    }

    /**
     * Returns the fill / ambient lighting color of the scene
     * @param closestPoint point
     * @return color
     */
    private Color calcColor(Point3D closestPoint) {
        return _scene.ambientLight.getIntensity();
    }
}
