package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class RayTest {
    /**
     * test for the closet point in list to the ray
     */
    @Test
    void getClosestPoint() {
        Ray ray=new Ray(new Point3D(0,0,2),new Vector(1,0,-2));
        List<Point3D> list = new LinkedList<Point3D>();
        Point3D p1=new Point3D(0.5,0,1);
        Point3D p2=new Point3D(1,0,0);
        Point3D p3=new Point3D(1.5,0,-1);
        list.add(p2);
        list.add(p1);
        list.add(p3);

        // TC01: נקודה באמצע הרשימה היא הקרובה לתחילת הקרן
        assertEquals(p1, ray.getClosestPoint(list),"נקודה באמצע הרשימה היא הקרובה לתחילת הקרן");

        // TC02: רשימה ריקה
        List<Point3D> list2 = new LinkedList<Point3D>();
        assertNull(ray.getClosestPoint(list2), "try again");

        // TC03: הנקודה הראשונה היא הכי קרובה לתחילת הקרן
        list2.add(p1);
        list2.add(p2);
        list2.add(p3);

        assertEquals(p1, ray.getClosestPoint(list2),"נקודה ראשונה הרשימה היא הקרובה לתחילת הקרן");

        // TC04: הנקודה האחרונה היא הכי קרובה לתחילת הקרן
        List<Point3D> list3 = new LinkedList<Point3D>();
        list3.add(p2);
        list3.add(p3);
        list3.add(p1);

        assertEquals(p1, ray.getClosestPoint(list3),"נקודה אחרונה הרשימה היא הקרובה לתחילת הקרן");
    }
}