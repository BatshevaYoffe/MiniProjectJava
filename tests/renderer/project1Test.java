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

    @Test
    public void miniProject1() {
        Camera cam = new Camera(new Point3D(1000, 0, 0), new Vector(-1000, 0, 0), new Vector(0, 0, 1000))
                .setDistance(1500)
                .setViewPlaneSize(1000, 1000);

        Scene scene1 = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene1.geometries.add(new Sphere(40, new Point3D(0, 100, 100)),
                new Polygon(new Point3D(0, 100, 100), new Point3D(0, 50, -40), new Point3D(0, 150, -40))
                        .setEmission(new Color(Color.BLUE)));

        //scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        scene1.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(0, 0, 200), new Vector(0, 100, -300)) //
                        .setKl(1E-5).setKq(1.5E-7));


        ImageWriter imageWriter = new ImageWriter("miniProject1", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(cam) //
                .setRayTracer(new BasicRayTracer(scene1));

        //render.setAntialiasing(true);
        render.renderImage();
        render.writeToImage();


    }


    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void projectsecond() {
        Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500).setDistance(1000);
        camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 500).setDistance(1000);
        scene.geometries.add( //
                new Sphere(40, new Point3D(0, 0, 0)) //
                        .setEmission(new Color(java.awt.Color.BLUE)), //
                //.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Polygon(new Point3D(0, -30, 0), new Point3D(60, -140, -60), new Point3D(-60, -140, -60))
                        .setEmission(new Color(java.awt.Color.PINK)),//
                new Polygon(new Point3D(0, -30, 0), new Point3D(60, -140, 0), new Point3D(0, -140, 0))
                        .setEmission(new Color(java.awt.Color.PINK)),//
                new Polygon(new Point3D(0, -30, 0), new Point3D(0, -140, 0), new Point3D(-60, -140, 0))
                        .setEmission(new Color(127, 0, 127)),//
                new Polygon(new Point3D(60, -140, -60), new Point3D(0, -140, 0), new Point3D(-60, -140, -60))
                        .setEmission(new Color(java.awt.Color.green)),//
                new Polygon(new Point3D(-20, -140, 0), new Point3D(-30, -140, 0), new Point3D(-25, -200, 0)),
                new Polygon(new Point3D(20, -140, 0), new Point3D(30, -140, 0), new Point3D(25, -200, 0)),
                new Plane(new Point3D(0, 0, -300), new Point3D(1, 1, -300), new Point3D(3, 4, -300))
                        .setEmission(new Color(200, 255, 200)),
                //  .setMaterial(new Material().setKr(0)),
                new Plane(new Point3D(0, -200, 0), new Point3D(1, -200, 1), new Point3D(-200, -200, 1))
                        .setEmission(new Color(195, 147, 0)),
                // .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30).setKt(0).setKr(0.5)),
                new Sphere(50, new Point3D(-130, 110, 0))
                        //   .setEmission(new Color(java.awt.Color.yellow))
                        .setMaterial(new Material().setKt(0.9)//
                                .setShininess(10).setKs(0.5).setKd(0.5)));
        scene.geometries.add(
                //tree
                new Sphere(30, new Point3D(184, 44, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.1)),
                new Sphere(30, new Point3D(162, 7, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.1)),
                new Sphere(30, new Point3D(100, 50, 20))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.1)),
                new Sphere(30, new Point3D(152, 73, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.1)),
                new Sphere(30, new Point3D(154, 55, 30))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.1)),
                new Sphere(30, new Point3D(119, 17, 0))
                        .setEmission(new Color(100, 200, 0))
                        .setMaterial(new Material().setShininess(10).setKs(1).setKd(0.6).setKr(0.3)),
                new Polygon(new Point3D(137, 44, 21), new Point3D(153, 31, 31), new Point3D(153, -200, 31), new Point3D(137, -200, 21))
                        .setEmission(new Color(175, 127, 0)),
                //rocks
                new Sphere(30, new Point3D(220, -200, -200)) //
                        .setEmission(new Color(127, 127, 127)),
                // .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                new Sphere(20, new Point3D(240, -200, -150)) //
                        .setEmission(new Color(100, 100, 100))
                .setMaterial(new Material().setKr(0.5))
        );
        //scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-130, 165, 0), new Vector(13, -108, 0))
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new SpotLight(new Color(400, 0, 0), new Point3D(-130, 110, 0), new Vector(13, 108, 0))
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add( //
                new DirectionalLight(new Color(100, 200, 0), new Vector(-862, 44, -978)));
        //   scene.lights.add(new DirectionalLight(new Color(400, 240, 0), new Vector(0, -2000, -1000)));
        scene.lights.add(new PointLight(new Color(400, 240, 0),new Point3D(220, -200, -150)));


        Render render = new Render(). //
                setImageWriter(new ImageWriter("projectsecond", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        //render.setAntialiasing(true);
        render.renderImage();
        render.writeToImage();
    }

}
