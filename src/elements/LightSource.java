package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface of light source
 */
public interface LightSource {
   /**
    * get Intensity function
    * @param point3D point 3D
    * @return the intensity of light source in point3D
    */
   public Color getIntensity(Point3D point3D);

   /**
    * get light vector
    * @param point3D point 3D
    * @return the vector of the light
    */
   public Vector getL(Point3D point3D);

   /**
    * get Distance function
    * @param point3D point 3D
    * @return the distance between the point and the
    */
   double getDistance(Point3D point3D);
}
