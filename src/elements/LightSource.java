package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface of light sorse
 */
public interface LightSource {
   /**
    *
    * @param point3D
    * @return the intensity of light source in point3D
    */
   public Color getIntensity(Point3D point3D);

   /**
    *
    * @param point3D
    * @return the vector of the light
    */
   public Vector getL(Point3D point3D);

   /**
    *
    * @param point3D
    * @return
    */
   double getDistance(Point3D point3D);
}
