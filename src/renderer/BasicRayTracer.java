package renderer;

import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * BasicRayTracer extends RayTracerBase
 */
public class BasicRayTracer extends RayTracerBase {
    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10; //Conditions for stopping a recursive function, by number of loops
    private static final double MIN_CALC_COLOR_K = 0.001; //Conditions for stopping a recursive function, by The contribution of light to a point

    /**
     * BasicRayTracer constructor
     *
     * @param scene scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Look for cuts between the ray and the 3D model of the scene
     *
     * @param ray ray
     * @return the color
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
     * The operation will schedule an auxiliary operation
     *
     * @param geoPoint point3D and geometries
     * @param ray      ray
     * @return color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }

    /**
     * @param geoPoint the geometry point3D
     * @param ray      ray
     * @param level    recursion depth
     * @param k        Discount factor
     * @return color
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = (geoPoint.geometry.getEmission());
        color = color.add(calcLocalEffects(geoPoint, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    /**
     * calc the global effects of the light on the point
     *
     * @param geoPoint the geometry point3D
     * @param v        ray vector- direction
     * @param level    recursion depth
     * @param k        Discount factor
     * @return color
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        double kkr = k * material.kR;
        //Conditions for stopping a recursive function, by The contribution of light to a point.
        //if the effect of light on the point color is minimal then stop with the recursion
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(geoPoint.point, v, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        //Conditions for stopping a recursive function, by The contribution of light to a point.
        //if the effect of light on the point color is minimal then stop with the recursion
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint.point, v, n), level, material.kT, kkt));
        return color;
    }

    /**
     * refraction
     *
     * @param point point3D
     * @param v     ray vector
     * @param n     normal
     * @return new ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {

        return new Ray(point, v, n);
    }

    /**
     * reflection
     *
     * @param point point 3D
     * @param v     ray vector
     * @param n     normal
     * @return new ray,r = v - 2 *( v ∙ n )* n
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(v.dotProduct(n) * 2));
        return new Ray(point, r, n);
    }

    /**
     * calc Global Effect
     *
     * @param ray   ray
     * @param level recursion depth
     * @param kx    material kR/kT
     * @param kkx   Discount factor*kr/kt
     * @return color
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) {
            return _scene.backgroundColor.scale(kx);
        }
        Color temp = calcColor(gp, ray, level - 1, kkx);
        return temp.scale(kx);
    }

    /**
     * find Closest Intersection geo point
     *
     * @param ray ray
     * @return the closest intersection geoPoint
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPointList = _scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(geoPointList);
    }


    /**
     * calc Local Effects
     *
     * @param intersection geometry and intersection point
     * @param ray          the ray that cuts the geoPoint(intersection)
     * @param k
     * @return color of the point
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
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
                //if (unshaded(lightSource, intersection)) {
                double ktr = transparency(lightSource, intersection);
                //if the effect of light on the point color is minimal then stop with the recursion
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     * function that get geo point and check if the point unshaded or not
     * check if there are geometries between the point and the light source
     *
     * @param lightSource light source
     * @param geoPoint    point3D and geometry
     * @return if the point unshaded or not
     */
    private boolean unshaded(LightSource lightSource, GeoPoint geoPoint) {
        Point3D point3D = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point3D);
        Ray lightRay = new Ray(point3D, lightSource, n);

        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point3D));
        if (intersections == null) {
            return true;
        }

        double lightDistance = lightSource.getDistance(geoPoint.point);
        //check every intersection
        for (GeoPoint gp : intersections) {
            //if there is intersection point between the light to the geometries && geometries kt=0 in the intersection point =>the geoPoint shaded
            if ((gp.point.distance(geoPoint.point) - lightDistance <= 0) && gp.geometry.getMaterial().kT == 0) {
                return false;
            }

        }
        return true;
    }

    /**
     * calc specular effect
     *
     * @param ks             specular factor
     * @param l              vector from light
     * @param n              normal to object
     * @param v              vector from camera
     * @param nShininess     the shininess
     * @param lightIntensity light Intensity color
     * @return color effect specular
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n)))).normalize();
        double minusVR = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));
    }

    /**
     * calc diffuse color
     *
     * @param kd             diffusive factor
     * @param nl
     * @param lightIntensity light Intensity color
     * @return color effect
     */
    private Color calcDiffusive(double kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd * Math.abs(nl));
    }

    /**
     * Function for calculating the level of shading at a point.
     * We would like to get partial shading in case the body or bodies that block the light source from the point have transparency to one degree or another * @param lightSource
     *
     * @param lightSource light
     * @param geoPoint    point3D and geometry
     * @return the level of shading at a point.
     */
    private double transparency(LightSource lightSource, GeoPoint geoPoint) {
        Point3D point3D = geoPoint.point;
        Vector n = geoPoint.geometry.getNormal(point3D);
        Ray lightRay = new Ray(point3D, lightSource, n);

        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(point3D));
        if (intersections == null) {
            return 1.0;
        }
        double ktr = 1.0;
        double lightDistance = lightSource.getDistance(geoPoint.point);
        //check every intersection
        for (GeoPoint gp : intersections) {
            //if there is intersection point between the light to the geometries =>0
            if ((gp.point.distance(geoPoint.point) - lightDistance <= 0)) {
                ktr *= gp.geometry.getMaterial().getKt();
                if (ktr < MIN_CALC_COLOR_K) {
                    return 0.0;
                }
            }

        }
        return ktr;
    }
}
