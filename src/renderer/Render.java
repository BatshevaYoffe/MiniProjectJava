package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Render Creates from the scene the color matrix of the image
 */
public class Render {
    private ImageWriter _imageWriter;

    private Camera _camera;
    private RayTracerBase _rayTracer;

    /**
     * changing
     * set function for imageWriter Updating
     * @param imageWriter
     * @return Object itself for threading
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * set function for camera Updating
     * @param cam
     * @return Object itself for threading
     */
    public Render setCamera(Camera cam) {
        _camera = cam;
        return this;
    }

    /**
     * set function for basicRayTracer Updating
     * @param basicRayTracer
     * @return Object itself for threading
     */
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    /**
     *
     */
    public void writeToImage() {
        //if(_imageWriter==null){
        //    throw new  MissingResourcesException("the image writer is NULL");
        //}
        //sublimation
        _imageWriter.writeToImage();
    }

    /**
     * That in all fields a non-empty value was entered
     */
    public void renderImage() {
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        // loop on viewPlane pixels
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                //For each pixel will be built a ray and for each ray we will get a color from the horn comb. Does the color women in the appropriate pixel of the image maker
                Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                Color color = _rayTracer.traceRay(ray);
                _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Create a grid of lines similar to what was done in the test in the first step.
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if(_imageWriter==null){
           throw new MissingResourceException("imagewriter is null",ImageWriter.class.getName(),"");
        }
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }
}

