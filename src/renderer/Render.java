package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

import java.util.List;
import java.util.MissingResourceException;

/**
 * Render Creates from the scene the color matrix of the image
 */
public class Render {
    private ImageWriter _imageWriter;

    private Camera _camera;
    private RayTracerBase _rayTracer;
    //A Boolean mod that turns on and off the enhancement feature of supersampling
    private boolean _isAntialiasing = false;

    /**
     * A function that returns the improvement state whether it is on or off
     *
     * @return
     */
    public boolean isAntialiasing() {
        return _isAntialiasing;
    }

    /**
     * A variable placement function that indicates whether the supersampling feature enhancement is enabled or disabled
     *
     * @param antialiasing -A Boolean variable to indicate the status of the feature whether it is off or active
     * @return the render
     */
    public Render setAntialiasing(boolean antialiasing) {
        _isAntialiasing = antialiasing;
        return this;
    }

    /**
     * changing
     * set function for imageWriter Updating
     *
     * @param imageWriter image Writer
     * @return Object itself for threading
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    /**
     * set function for camera Updating
     *
     * @param cam camera
     * @return Object itself for threading
     */
    public Render setCamera(Camera cam) {
        _camera = cam;
        return this;
    }

    /**
     * set function for basicRayTracer Updating
     *
     * @param basicRayTracer basic Ray Tracer
     * @return Object itself for threading
     */
    public Render setRayTracer(BasicRayTracer basicRayTracer) {
        _rayTracer = basicRayTracer;
        return this;
    }

    /**
     * write To Image function
     */
    public void writeToImage() {
        if (_imageWriter == null) {
            throw new MissingResourceException("imagewriter is null", ImageWriter.class.getName(), "");
        }
//        sublimation
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
                // If the supersampling feature scattering parameter is active then we will divide each pixel into several pixels and sample additional rays to calculate the color of the dot accurately
                if (_isAntialiasing) {
                    List<Ray> rays = _camera.constructRaysThroughPixel(ray, nX, nY, j, i);
                    for (var ray1 : rays) {
                        color = color.add(_rayTracer.traceRay(ray1));

                    }
                    //The average color of the rays coming out of one pixel
                    color = color.reduce(rays.size() + 1);
                }

                _imageWriter.writePixel(j, i, color);
            }
        }
    }

    /**
     * Create a grid of lines similar to what was done in the test in the first step.
     *
     * @param interval
     * @param color
     */
    public void printGrid(int interval, Color color) {
        if (_imageWriter == null) {
            throw new MissingResourceException("imagewriter is null", ImageWriter.class.getName(), "");
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

