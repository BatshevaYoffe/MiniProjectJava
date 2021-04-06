package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Sphere Test class
 */
class SphereTest {
    /**
     * test method for Sphere get normal
     *  create new Sphere with point and radius and check if the normal is good
     */
    @Test
    void testGetNormal() {
        Sphere sphere=new Sphere(new Point3D(0,0,1),1.0);
        assertEquals(new Vector(0,0,1),sphere.getNormal(new Point3D(0,0,2)),"Bad sphere normal");
    }
}