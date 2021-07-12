package renderer;

import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class project2Test {
    /**
     * Project 2 image
     */
    @Test
    public void project2() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, 1300), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500).setDistance(1000);
        scene.geometries.add(//floor
                new Plane(new Point3D(0, -200, 0), new Point3D(1, -200, 1), new Point3D(-200, -200, 1))
                        .setEmission(new Color(0, 143, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(40))
                ,
                new Plane(new Point3D(0, 0, -300), new Point3D(1, 1, -300), new Point3D(3, 4, -300))
                        .setEmission(new Color(200, 255, 200))
                //, new Polygon(new Point3D(-20,-190,36), new Point3D(-106, -190, 31),new Point3D(-106,-190,0), new Point3D(17,-190, 2)).setEmission(Color.BLACK)
                //BUS
                , new Polygon(new Point3D(-300, -20, 10), new Point3D(50, -20, 10), new Point3D(50, -170, 10), new Point3D(-300, -170, 10)).setEmission(new Color(255, 0, 0))
                , new Polygon(new Point3D(-300, -20, -20), new Point3D(50, -20, -20), new Point3D(50, -170, -20), new Point3D(-300, -170, -20)).setEmission(new Color(100, 200, 0))
                , new Polygon(new Point3D(-300, -170, 10), new Point3D(-300, -170, -20), new Point3D(-300, -20, -20), new Point3D(-300, -20, 10)).setEmission(new Color(255, 0, 0))
                , new Polygon(new Point3D(-300, -20, 10), new Point3D(-300, -20, -20), new Point3D(50, -20, -20), new Point3D(50, -20, 10)).setEmission(new Color(255, 0, 0))
                , new Polygon(new Point3D(50, -170, 10), new Point3D(50, -170, -20), new Point3D(50, -20, -20), new Point3D(50, -20, 10)).setEmission(new Color(255, 0, 0))

                //windows
                , new Polygon(new Point3D(-280, -30, 11), new Point3D(-220, -30, 11), new Point3D(-220, -50, 11), new Point3D(-280, -50, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-200, -30, 11), new Point3D(-140, -30, 11), new Point3D(-140, -50, 11), new Point3D(-200, -50, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-120, -30, 11), new Point3D(-60, -30, 11), new Point3D(-60, -50, 11), new Point3D(-120, -50, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-40, -30, 11), new Point3D(20, -30, 11), new Point3D(20, -50, 11), new Point3D(-40, -50, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-280, -70, 11), new Point3D(-190, -70, 11), new Point3D(-190, -120, 11), new Point3D(-280, -120, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-170, -70, 11), new Point3D(-80, -70, 11), new Point3D(-80, -120, 11), new Point3D(-170, -120, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-60, -70, 11), new Point3D(30, -70, 11), new Point3D(30, -168, 11), new Point3D(-60, -168, 11))
                        .setMaterial(new Material().setKt(0.4).setKd(0.3))
                , new Polygon(new Point3D(-20, -70, 11.5), new Point3D(-10, -70, 11.5), new Point3D(-10, -168, 11.5), new Point3D(-20, -168, 11.5)).setEmission(new Color(255, 0, 0))
                //way
                , new Polygon(new Point3D(-400, -199, -100), new Point3D(600, -199, -100), new Point3D(600, -199, 100), new Point3D(-400, -199, 100)).setEmission(new Color(153, 153, 153))
                , new Polygon(new Point3D(-340, -198, 0), new Point3D(-310, -198, 0), new Point3D(-310, -198, 10), new Point3D(-340, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-300, -198, 0), new Point3D(-270, -198, 0), new Point3D(-270, -198, 10), new Point3D(-300, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-260, -198, 0), new Point3D(-230, -198, 0), new Point3D(-230, -198, 10), new Point3D(-260, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-220, -198, 0), new Point3D(-190, -198, 0), new Point3D(-190, -198, 10), new Point3D(-220, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-180, -198, 0), new Point3D(-150, -198, 0), new Point3D(-150, -198, 10), new Point3D(-180, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-140, -198, 0), new Point3D(-110, -198, 0), new Point3D(-110, -198, 10), new Point3D(-140, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-100, -198, 0), new Point3D(-70, -198, 0), new Point3D(-70, -198, 10), new Point3D(-100, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-60, -198, 0), new Point3D(-30, -198, 0), new Point3D(-30, -198, 10), new Point3D(-60, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(-20, -198, 0), new Point3D(10, -198, 0), new Point3D(10, -198, 10), new Point3D(-20, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(20, -198, 0), new Point3D(50, -198, 0), new Point3D(50, -198, 10), new Point3D(20, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(60, -198, 0), new Point3D(90, -198, 0), new Point3D(90, -198, 10), new Point3D(60, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(100, -198, 0), new Point3D(130, -198, 0), new Point3D(130, -198, 10), new Point3D(100, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(140, -198, 0), new Point3D(170, -198, 0), new Point3D(170, -198, 10), new Point3D(140, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(180, -198, 0), new Point3D(210, -198, 0), new Point3D(210, -198, 10), new Point3D(180, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(220, -198, 0), new Point3D(250, -198, 0), new Point3D(250, -198, 10), new Point3D(220, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(260, -198, 0), new Point3D(290, -198, 0), new Point3D(290, -198, 10), new Point3D(260, -198, 10)).setEmission(new Color(255, 255, 255))
                , new Polygon(new Point3D(300, -198, 0), new Point3D(330, -198, 0), new Point3D(330, -198, 10), new Point3D(300, -198, 10)).setEmission(new Color(255, 255, 255))

                //tamror
                , new Polygon(new Point3D(137, 44, -100), new Point3D(153, 31, -100), new Point3D(153, -200, -100), new Point3D(137, -200, -100))
                        .setEmission(new Color(175, 127, 0))
                , new Triangle(new Point3D(145, 60, -99), new Point3D(105, -10, -99), new Point3D(185, -10, -99)).setEmission(new Color(255, 255, 255))
                , new Triangle(new Point3D(145, 80, -99), new Point3D(85, -20, -99), new Point3D(205, -20, -99)).setEmission(new Color(255, 0, 0))
                , new Polygon(new Point3D(140, 30, -98), new Point3D(140, 5, -98), new Point3D(133, 5, -98), new Point3D(133, 30, -98))
                , new Polygon(new Point3D(157, 25, -98), new Point3D(157, 0, -98), new Point3D(150, 0, -98), new Point3D(150, 25, -98))
                , new Triangle(new Point3D(153.5, 35, -98), new Point3D(160, 20, -98), new Point3D(147, 20, -98))
                , new Triangle(new Point3D(136.5, -5, -98), new Point3D(130, 10, -98), new Point3D(143, 10, -98))

                //galgalim
                , new Sphere(20, new Point3D(-230, -180, -20))
                , new Sphere(20, new Point3D(-230, -180, -10))
                , new Sphere(20, new Point3D(-20, -180, -20))
                , new Sphere(20, new Point3D(-20, -180, -10))
                //panas
                , new Sphere(10, new Point3D(50, -150, -10))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setKt(0.9)//
                                .setShininess(10).setKs(0.5).setKd(0.5)),

                //rocks
                new Sphere(30, new Point3D(220, -200, 200)) //
                        .setEmission(new Color(127, 127, 127)),
                new Sphere(20, new Point3D(240, -200, 250)) //
                        .setEmission(new Color(100, 100, 100)),
                new Sphere(30, new Point3D(-120, -200, 300)) //
                        .setEmission(new Color(127, 127, 127)),
                new Sphere(20, new Point3D(-40, -200, 350)) //
                        .setEmission(new Color(100, 100, 100)),
                new Sphere(10, new Point3D(0, -200, 200)) //
                        .setEmission(new Color(127, 127, 127)),
                new Sphere(20, new Point3D(-100, -200, 400)) //
                        .setEmission(new Color(100, 100, 100)),
                new Sphere(20, new Point3D(200, -200, 400)) //
                        .setEmission(new Color(100, 100, 100)),
                new Sphere(10, new Point3D(100, -200, -200)) //
                        .setEmission(new Color(127, 127, 127)),
                 new Sphere(30, new Point3D(100, -200, 450)) //
                .setEmission(new Color(127, 127, 127)),
                new Sphere(20, new Point3D(60, -200, 400)) //
                        .setEmission(new Color(102, 102, 102))



                //sun
                , new Sphere(50, new Point3D(-130, 200, -100))
                        .setMaterial(new Material().setKt(0.5)//
                                .setShininess(10).setKs(0.5).setKd(0.5))
                        .setEmission(new Color(100, 200, 0))


        );

        //scene.lights.add( //
        //        new DirectionalLight(new Color(100, 200, 0), new Vector(-167, 124, 0)));
        scene.lights.add(new PointLight(new Color(100, 200, 0), new Point3D(50, -150, -10)));
        //scene.lights.add(new SpotLight(new Color(100,200,0),new Point3D(-130, 110, 0),new Vector()))
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-130, 200, -100), new Vector(13, -108, -100))
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 0, 0), new Point3D(-130, 200, -100), new Vector(13, 108, -100))
                        .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add(
                new DirectionalLight(new Color(100, 200, 0), new Vector(300, -140, -200)));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("project2Regular", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        //render.set_isAdaptiveSupersampling(true);
        //render.setAntialiasing(true);
        render.setDebugPrint();
        render.setMultithreading(3);
        render.renderImage();
        render.writeToImage();
    }
}
