package elements;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;

import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * class for integration tests between the formation of beams from a camera along the calculation of cuts of a beam with geometric bodies
 */
public class CameraIntegrationTest {
    /**
     * Checks whether the shape has the correct number of intsersection points
     * @param geometry the shape
     * @param camera
     * @param intsersectionNum the number of points that intersection with the shape
     */
    void TestCamera(Geometry geometry,Camera camera,int intsersectionNum){
        List<Point3D> allPoints=null;
        //goes over every pixel in the view plane and add the Intersection points between the ray from the pixel to the shape
        for (int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRayThroughPixel(3, 3, j, i);

                List<Point3D> list = geometry.findIntersections(ray);
                if(list!=null){
                    if(allPoints==null){
                        allPoints=new LinkedList<>();
                    }
                    allPoints.addAll(list);
                }
            }
        }
        //if there are not intersection with the shape then count=0
        int count=0;
        if(allPoints!=null) {
            count=allPoints.size();
        }
        assertEquals(count,intsersectionNum,"wrong number of Intersection");
    }

    /**
     * Test in intersection with camera and sphere
     */
    @Test
    void testSphereCamera(){
        //Two intersection points, the sphere is the view plane
        Sphere sphere=new Sphere(1, new Point3D(0,0,-3));

        Camera camera=new Camera(Point3D.ZERO,new Vector(0,0,-1),new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        TestCamera(sphere,camera,2);

        //18 intersection points, the sphere intersection the view plane
        sphere=new Sphere(2.5, new Point3D(0,0,-2.5));
        camera=new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        TestCamera(sphere,camera,18);

        //10 intersection points, the sphere intersection the view plane
        sphere=new Sphere(2, new Point3D(0,0,-2));
        camera=new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0)).setDistance(1).setViewPlaneSize(3,3);
        TestCamera(sphere,camera,10);

        //9 intersection points
        camera=new Camera(Point3D.ZERO,new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        sphere=new Sphere(4, new Point3D(0,0,-2));
        TestCamera(sphere,camera,9);

        //Zero intersection points,the sphere before the view plane
        sphere=new Sphere(0.5, new Point3D(0,0,1));
        TestCamera(sphere,camera,0);
    }

    /**
     * test plane with camera
     */
    @Test
    void testPlaneCamera() {
        Camera camera=new Camera(new Point3D(0,0,2),new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);

        //9 intersection points,the plane parallel to the view plane
        Plane plane=new Plane(new Point3D(0,1,0),new Point3D(-1,0,0),new Point3D(1,0,0));
        TestCamera(plane,camera,9);

        //9 intersection points
        camera=new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);
        plane=new Plane(new Point3D(0, 0, -5), new Vector(0, 1, 2));
        TestCamera(plane,camera,9);

        //6 intersection points
        plane=new Plane(new Point3D(0, 0, -5), new Vector(0, 1, 1));
        TestCamera(plane,camera,6);
    }

    /**
     * test triangle with camera
     */
    @Test
    void testTriangleCamera() {
        Camera  camera=new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1).setViewPlaneSize(3,3);

        //One intersection point
        Triangle triangle=new Triangle(new Point3D(0, 1, -2),new Point3D(1, -1, -2),new Point3D(-1, -1, -2));
        TestCamera(triangle,camera,1);

        //Two intersection point
        triangle=new Triangle(new Point3D(0, 20, -2),new Point3D(1, -1, -2),new Point3D(-1, -1, -2));
        TestCamera(triangle,camera,2);
    }
}
