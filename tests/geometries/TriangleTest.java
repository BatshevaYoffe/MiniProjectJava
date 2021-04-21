package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Triangle Test class
 */
class TriangleTest {
    /**
     * test method for Triangle get normal
     * create new Triangle with 3 points and check if the Triangle normal is good
     */
    @Test
    void testGetNormal() {
        Triangle p = new Triangle(new Point3D(1, 0, 0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
        double sqrt1 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt1, sqrt1, sqrt1), p.getNormal(new Point3D(0, 0, 1)), "Bad normal to Triangle");

    }

    @Test
    public void testFindIntersections() {
        Triangle triangle=new Triangle(new Point3D(0,0,1),new Point3D(0,0,-1),new Point3D(2,0,0));
        Plane plane=new Plane(new Point3D(0,0,1),new Point3D(0,0,-1),new Point3D(2,0,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Inside triangle (1 points)
        Point3D p=new Point3D(1,0,0);
        List<Point3D> result1=triangle.findIntsersection(new Ray(new Point3D(0,-1,0),new Vector(1,1,0)));
        assertEquals( 1, result1.size(),"Wrong number of points");
        assertEquals(List.of(p), result1,"Bad Intersection");

        // TC02: Outside against edge (0 points)
        assertEquals(List.of(new Point3D(-1,0,0)),plane.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(-1,2,0))),"Bad Intersection with plan");
        assertNull(triangle.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(-1,2,0))), "Bad Intersection");

        // TC03: Outside against vertex (0 points)
        assertEquals(List.of(new Point3D(3,0,0)),plane.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(3,2,0))),"Bad Intersection with plan");
        assertNull(triangle.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(3,2,0))), "Bad Intersection");

        // =============== Boundary Values Tests ==================
        // TC11: On edge(0 points)
        assertEquals(List.of(new Point3D(1,0,0.5)),plane.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(1,2,0.5))),"Bad Intersection with plan");
        assertNull(triangle.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(1,2,0.5))), "Bad Intersection");

        // TC12: In vertex(0 points)
        assertEquals(List.of(new Point3D(0,0,1)),plane.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(0,2,1))),"Bad Intersection with plan");
        assertNull(triangle.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(0,2,1))), "Bad Intersection");

        // TC13: On edge's continuation(0 points)
        assertEquals(List.of(new Point3D(3,0,-0.5)),plane.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(3,2,-0.5))),"Bad Intersection with plan");
        assertNull(triangle.findIntsersection(new Ray(new Point3D(0,-2,0),new Vector(3,2,-0.5))), "Bad Intersection");



    }
}