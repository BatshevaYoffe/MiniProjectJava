package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Sphere sphere=new Sphere(1.0, new Point3D(0,0,1));
        assertEquals(new Vector(0,0,1),sphere.getNormal(new Point3D(0,0,2)),"Bad sphere normal");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        Point3D p=new Point3D(1.6202518579971015,0.6115363337823195,0.4912341041857976);
        List<Point3D> result1=sphere.findIntersections(new Ray(new Point3D(1.52,0,0),new Vector(0.1,0.61,0.49)));
        assertEquals( 1, result1.size(),"Wrong number of points");
        assertEquals(List.of(p), result1,"Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point3D(-2.74403, 2.53817, 0),new Vector(5.2,-1.09,1.16))), "Sphere behind Ray");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)

        assertEquals(List.of(new Point3D(2, 0, 0)),
                sphere.findIntersections(new Ray(new Point3D(1, -1, 0), new Vector(1, 1, 0))),
                "Ray from sphere inside");
        // TC12: Ray starts at sphere and goes outside (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 1, 0))),"Ray from sphere outside");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, -2, 0), new Vector(0, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getY() > result.get(1).getY())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point3D(1, -1, 0), new Point3D(1, 1, 0)),
                result,
                "Line through O, ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2, 0, 0)), result,"Line through O, ray starts at sphere and goes inside");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(2, 0, 0)), result,"Line through O, Ray starts inside");

        // TC16: Ray starts at the center (1 points)
        //result = sphere.findIntsersection(new Ray(new Point3D(1, 0, 0), new Vector(0, 0, 1)));
        //assertEquals(1, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1, 0, 1)), result,"Line through O, Ray starts at the center");
        //assertEquals(List.of(new Point3D(1, 1, 0)),
         //       sphere.findIntsersection(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0))),
      //          "Line through O, ray from O");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))),"Ray starts at sphere and goes outside");


        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))),"Ray starts after sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 0, 0))),"Ray starts before the tangent point");

        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 0, 1), new Vector(1, 0, 0))),"Ray starts at the tangent point");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(1, 0, 0))),"Ray starts after the tangent point");

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point3D(1, 0, 2), new Vector(1, 0, 0))),"Ray's line is outside, ray is orthogonal to ray start to sphere's center line");

    }

}