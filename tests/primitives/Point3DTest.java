package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class Point3DTest {
    Point3D p1 = new Point3D(1, 2, 3);
    Point3D p2 = new Point3D(-2,-4,-6);


    @Test
    void testAdd() {
        if (!Point3D.ZERO.equals(p1.add(new Vector(-1, -2, -3))))
            out.println("ERROR: Point + Vector does not work correctly");
    }

    @Test
    void testSubtract() {
        if (!new Vector(1, 1, 1).equals(new Point3D(2, 3, 4).subtract(p1)))
            out.println("ERROR: Point - Point does not work correctly");
    }

    @Test
    void testDistanceSquared() {
        if(!isZero(p1.distanceSquared(p2)-126))
            out.println("ERROR: distanceSquared() wrong value");
    }

    @Test
    void testDistance() {
        if(!isZero(p1.distanceSquared(p2)-126))
            out.println("ERROR: distanceSquared() wrong value");
        if(!isZero(p1.distance(p2)-Math.sqrt(126)))
            out.println("ERROR: distance() wrong value");
    }
}