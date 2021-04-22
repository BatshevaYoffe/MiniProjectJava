package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Some (but not all) shapes are cut
        Geometries geometries=new Geometries(
                new Plane(new Point3D(-1,0,0),new Point3D(0,0,1),new Point3D(3,0,0)),
                new Triangle(new Point3D(0,-2,0),new Point3D(0,0,-2),new Point3D(0,1,0)),
                new Sphere(new Point3D(0,1,0),1));
        List<Point3D> result=geometries.findIntsersection(new Ray(new Point3D(0,4,0),new Vector(0,-4,1)));
        assertEquals(3,result.size(),"Wrong number of points");
        assertEquals(List.of(new Point3D(0,0,1),new Point3D(0.0,1.8419828528814568,0.5395042867796358),new Point3D(0.0,0.5109583235891315,0.8722604191027171)), result,"Bad intersection");

        // =============== Boundary Values Tests ==================
        //TC11:Empty body collection
        Geometries geometries1=new Geometries();
        assertNull(geometries1.findIntsersection(new Ray(new Point3D(0,4,0),new Vector(0,-4,1))),"Empty body collection");

        //TC12:Only one shape is cut
        List<Point3D> result2=geometries.findIntsersection(new Ray(new Point3D(0,5,0),new Vector(2,-5,0)));
        assertEquals(1,result2.size(),"Wrong number of points");
        assertEquals(List.of(new Point3D(2,0,0)), result2,"Bad intersection");

        //TC13:No shape is cut
        assertNull(geometries.findIntsersection(new Ray(new Point3D(0,3,0),new Vector(0,1,1))),"No shape is cut");
    }
}