package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tube Test class
 */
class TubeTest {
    /**
     *  test method for Tube get normal
     *  create new Tube with Ray(point,vector) and radius
     *  create new vector with the normal tube
     *  check if the normal is orthogonal to the tube
     */
    @Test
    void testGetNormal() {
        Tube tube = new Tube( new Ray(new Point3D(0, 0, 1), new Vector(0, -1, 0)),1.0);

        Vector normal = tube.getNormal(new Point3D(0, 0.5, 2)).normalize();

        double dotProduct = normal.dotProduct(tube.getAxis().getDir());
        assertEquals(0d, dotProduct, "normal is not orthogonal to the tube");

        boolean firstnormal = new Vector(0, 0, 1).equals(normal);
        boolean secondtnormal = new Vector(0, 0, -1).equals(normal);

        assertTrue(firstnormal || secondtnormal, "Bad normal to tube");

        assertEquals(new Vector(0, 0, 1), normal, "Bad normal to tube");
    }
}