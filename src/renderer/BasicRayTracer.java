package renderer;

import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * BasicRayTracer constructor
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Look for cuts between the ray and the 3D model of the scene
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersection = _scene.geometries.findGeoIntersections(ray);
        //if there are not intersection return The background color of the scene
        if (intersection == null) {
            return _scene.backgroundColor;
        }
        //The point closest to the beginning of the foundation will be found
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersection);
        //the point color
        return calcColor(closestPoint, ray);
    }

    /**
     * Returns the fill / ambient lighting color of the scene
     * @param closestPoint point
     * @return color
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {

        return _scene.ambientLight.getIntensity()
                .add(closestPoint.geometry.getEmission())
                .add(calcLocalEffects(closestPoint, ray));
    }

    /**
     * @param intersection
     * @param ray
     * @return color of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);//
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl ) == sign(nv )
                // if (unshaded(lightSource, intersection)) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                //}
            }
        }
        return color;
    }

    /**
     *
     * @param lightSource
     * @param intersection
     * @return
     */
    //  private boolean unshaded(LightSource lightSource, GeoPoint intersection) {

    //    Point3D point3D=intersection.point;
    //  Vector l=lightSource.getL(point3D).scale(-1).normalized();
    //Vector n=intersection.geometry.getNormal(point3D);
    //Ray lightRay=new Ray(point3D,l,n,DELTA);

    //}

    /**
     * calc specular effect
     * @param ks specular factor
     * @param l vector from light
     * @param n normal to object
     * @param v vector from camera
     * @param nShininess
     * @param lightIntensity
     * @return color effect specular
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        double minusVR = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));
    }

    /**
     * calc diffuse color
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return color effect
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(n)));
    }
}
