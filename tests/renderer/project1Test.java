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

import java.util.LinkedList;
import java.util.List;

/**
 * tests for project 1
 * supersampling
 */
public class project1Test {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     * basicRenderTwoColorTest with supersampling
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(50, new Point3D(0, 0, -100)),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test3", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.setAntialiasing(true);//supersampling
        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Project 1 image
     */
    @Test
    public void projectsecond() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500).setDistance(1000);
        scene.geometries.add( //girl
                new Sphere(25, new Point3D(0, -65, 0)), //
                new Polygon(new Point3D(0, -65, 0), new Point3D(30, -155, -60), new Point3D(-30, -155, -60)),
                new Polygon(new Point3D(0, -65, 0), new Point3D(30, -155, 0), new Point3D(0, -155, 0)),
                new Polygon(new Point3D(0, -65, 0), new Point3D(0, -155, 0), new Point3D(-30, -155, 0)),
              //  new Polygon(new Point3D(30, -155, -60), new Point3D(0, -155, 0), new Point3D(-30, -155, -60)),
                new Polygon(new Point3D(-10, -155, 0), new Point3D(-15, -155, 0), new Point3D(-12, -200, 0)),
                new Polygon(new Point3D(10, -155, 0), new Point3D(15, -155, 0), new Point3D(12, -200, 0)),
                new Plane(new Point3D(0, 0, -300), new Point3D(1, 1, -300), new Point3D(3, 4, -300))
                        .setEmission(new Color(200, 255, 200)),
                new Plane(new Point3D(0, -200, 0), new Point3D(1, -200, 1), new Point3D(-200, -200, 1))
                        .setEmission(new Color(195, 147, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Sphere(50, new Point3D(-130, 110, 0))
                        .setMaterial(new Material().setKt(0.9)//
                                .setShininess(10).setKs(0.5).setKd(0.5)));
        scene.geometries.add(//tree
                new Sphere(30, new Point3D(184, 44, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Sphere(30, new Point3D(162, 7, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Sphere(30, new Point3D(111, 50, 20))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Sphere(30, new Point3D(152, 73, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Sphere(30, new Point3D(154, 55, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Sphere(30, new Point3D(119, 17, 0))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6)),
                new Polygon(new Point3D(137, 44, 21), new Point3D(153, 31, 31), new Point3D(153, -200, 31), new Point3D(137, -200, 21))
                        .setEmission(new Color(175, 127, 0)),
                //rocks
                new Sphere(30, new Point3D(220, -200, -200)) //
                        .setEmission(new Color(127, 127, 127)),
                new Sphere(20, new Point3D(240, -200, -150)) //
                        .setEmission(new Color(100, 100, 100))
        );

        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-130, 165, 0), new Vector(13, -108, 0))
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 0, 0), new Point3D(-130, 110, 0), new Vector(13, 108, 0))
                        .setKl(1E-5).setKq(1.5E-7));

        scene.lights.add( //
                new DirectionalLight(new Color(100, 200, 0), new Vector(-862, 44, -978)));


        Render render = new Render(). //
                setImageWriter(new ImageWriter("projectsecond", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.setAntialiasing(true);
        render.renderImage();
        render.writeToImage();
    }

}
