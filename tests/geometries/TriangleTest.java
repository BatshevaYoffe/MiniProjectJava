package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Triangle Test class
 */
class TriangleTest {
    /**
     *       test method for Triangle get normal
     *       create new Triangle with 3 points and check if the Triangle normal is good
     */
    @Test
    void testGetNormal() {
        Triangle p=new Triangle(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        double sqrt = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt, sqrt, sqrt), p.getNormal(new Point3D(0, 0, 1)), "Bad normal to Triangle");

    }
}