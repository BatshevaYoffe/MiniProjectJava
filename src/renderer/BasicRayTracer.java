package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;
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
        List<GeoPoint> intersection=_scene.geometries.findGeoIntersections(ray);
        //if there are not intersection return The background color of the scene
        if(intersection==null){
            return _scene.backgroundColor;
        }
        //The point closest to the beginning of the foundation will be found
        GeoPoint closestPoint=ray.findClosestGeoPoint(intersection);
        //the point color
        return calcColor(closestPoint);
    }

    /**
     * Returns the fill / ambient lighting color of the scene
     * @param closestPoint point
     * @return color
     */
    private Color calcColor(GeoPoint closestPoint) {

        return _scene.ambientLight.getIntensity()
                .add(closestPoint.geometry.getEmission());
    }
}
