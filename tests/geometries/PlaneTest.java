package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * plane test class
 */
class PlaneTest {
    /**
     * test method for plane get normal
     * create new plane with 3 points and check if the normal good
     */
    @Test
    void testGetNormal() {

        Plane p=new Plane(new Point3D(1,0,0),new Point3D(0,1,0),new Point3D(0,0,1));
        double sqrt = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt, sqrt, sqrt), p.getNormal(new Point3D(0, 0, 1)), "Bad normal to plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane=new Plane(new Point3D(-1,0,0),new Point3D(0,-1,0), new Point3D(1,0,0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane(1 points)
        List<Point3D>result = plane.findIntersections(new Ray(new Point3D(0, 0, -1), new Vector(0, 2, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point3D(0, 2, 0)), result,"Ray intersects the plane");

        // TC02: Ray does not intersect the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 1),new Vector(0,2,1))), " Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================
        // ---------Ray is parallel to the plane---------
        // TC11: Ray is parallel to the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 0, 2),new Vector(0,1,0))), " Ray is parallel to the plane");

        // TC12: Ray is parallel, include the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point3D(0, -1, 0),new Vector(0,1,0))), " Ray does not intersect in plane");

        // ---------Ray is orthogonal to the plane---------
        // TC13: Ray is orthogonal to the plane, before the plane
        assertEquals(List.of(new Point3D(0,1,0)),plane.findIntersections(new Ray(new Point3D(0, 1, -1),new Vector(0,0,1))), "Bad plane intersection");

        // TC14: Ray is orthogonal to the plane, in the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 2, 0),new Vector(0,0,1))), "Must not be plane intersection");

        // TC15: Ray is orthogonal to the plane, after the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 1, 1),new Vector(0,0,1))), "Must not be plane intersection");

        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane
        assertNull(plane.findIntersections(new Ray(new Point3D(0, 1, 0),new Vector(0,-1,1))), "Must not be plane intersection");

        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
        assertNull(plane.findIntersections(new Ray(plane._q0,new Vector(0,-1,1))), "Must not be plane intersection");

    }
}