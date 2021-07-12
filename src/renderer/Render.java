package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan with our Changes and additions
 */
public class Render {
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    private boolean _isAntialiasing = false; //A Boolean mod that turns on and off the enhancement feature of supersampling
    private boolean _isAdaptiveSupersampling = false;//A Boolean mod that turns on and off the enhancement feature of adaptive supersampling

    private final int level = 2;//the depth of recursion

    /**
     * A function that returns the improvement state whether it is on or off
     *
     * @return status of antiAliasing
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
     * A function that returns the improvement state whether it is on or off
     *
     * @return the status of adaptive super sampling
     */
    public boolean is_isAdaptiveSupersampling() {
        return _isAdaptiveSupersampling;
    }

    /**
     * A variable placement function that indicates whether the adaptive supersampling feature enhancement is enabled or disabled
     *
     * @param isAdaptiveSupersampling -A Boolean variable to indicate the status of the feature whether it is off or active
     * @return the render
     */
    public Render set_isAdaptiveSupersampling(boolean isAdaptiveSupersampling) {
        _isAdaptiveSupersampling = isAdaptiveSupersampling;
        return this;
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         * if it is -1 - the task is finished, any other value - the progress
         * percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     *
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param j  pixel's column number (pixel index in row)
     * @param i  pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int j, int i) {
        // For each pixel will be built a ray and for each ray we will get a color from the horn comb. Does the color women in the appropriate pixel of the image maker
        Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
        Color color = Color.BLACK;
        // If the supersampling feature scattering parameter is active then we will divide each pixel into several pixels and sample additional rays to calculate the color of the dot accurately
        if (_isAntialiasing) {
            color = tracer.traceRay(ray);
            List<Ray> rays = camera.constructRaysThroughPixel(ray, nX, nY, j, i);
            for (var ray1 : rays) {
                color = color.add(tracer.traceRay(ray1));

            }
            //The average color of the rays coming out of one pixel
            color = color.reduce(rays.size() + 1);
        }
        // If the adaptive supersampling feature scattering parameter is active then We will check the color in the corners of the pixel if the average in colors will be different from the color of the center point.
        if (_isAdaptiveSupersampling) {
            List<Ray> rays = camera.constructRaysThroughPixel2(nX, nY, j, i);//Returns a list of 4 rays passing through the corners of the pixel
            List<Color> colors = listOfColors(rays);
            Point3D Pc = camera.getPc(nX, nY, j, i);
            color = recursive(colors, camera.getRx(nX), camera.getRy(nY), level, Pc);

        }
        if (color.equals(Color.BLACK))
            color = tracer.traceRay(ray);

        imageWriter.writePixel(j, i, color);
    }


    /**
     * calculate the colors of 5 centers on pixel
     *
     * @param pc-the    center of the pixel(point 3D)
     * @param Rx-width  of the pixel
     * @param Ry-height of the pixel
     * @return list of colors
     */
    private List<Color> colors5Centers(Point3D pc, double Rx, double Ry) {
        List<Ray> rays = camera.colors5Centers(pc, Rx, Ry);
        List<Color> colors = new LinkedList<Color>();

        for (var ray : rays)
            colors.add(tracer.traceRay(ray));

        return colors;
    }

    /**
     * @param corners-list of the ray from the corners of the pixel
     * @param Rx-width     of the pixel
     * @param Ry-height    of the pixel
     * @param level-the    death of the recursive
     * @param Pc-the       center of the pixel
     * @return the pixel color
     * @author Naama and Batsheva(with the help of efrat klain)
     * function to to calculate the color of the pixel,
     * As long as all the pixel corners are not equal in color or we have not reached enough recursion depth we will continue to divide each pixel into 4 pixels and so on
     */
    private Color recursive(List<Color> corners, double Rx, double Ry, int level, Point3D Pc) {

        Color sumColors = Color.BLACK;
        //Stop conditions when the pixel corners are equal or we have reached deep enough in recursion
        if (level <= 0 || (corners.get(0).equals(corners.get(1)) &&
                corners.get(0).equals(corners.get(2)) &&
                corners.get(0).equals(corners.get(3)))) {
            for (var color : corners) sumColors = sumColors.add(color);
            return sumColors.reduce(4);
        }
        List<Color> centers = colors5Centers(Pc, Rx, Ry);
        //calculate the color of all the sub-pixel in recursive way
        //from all sub-pixel we calculate the color of 4 different rays,1 from the rays of the corners and another 3 from the list of the 5 centers.
        sumColors = sumColors.add(recursive(List.of(corners.get(0), centers.get(0), centers.get(1), centers.get(2)),
                Rx / 2, Ry / 2, level - 1,
                camera.getPij(Pc, Ry, Rx, 2, 2, 0, 0)));
        sumColors = sumColors.add(recursive(List.of(centers.get(0), corners.get(1), centers.get(2), centers.get(3)),
                Rx / 2, Ry / 2, level - 1,
                camera.getPij(Pc, Ry, Rx, 2, 2, 0, 1)));

        sumColors = sumColors.add(recursive(List.of(centers.get(1), centers.get(2), corners.get(2), centers.get(4)),
                Rx / 2, Ry / 2, level - 1,
                camera.getPij(Pc, Ry, Rx, 2, 2, 1, 0)));

        sumColors = sumColors.add(recursive(List.of(centers.get(2), centers.get(3), centers.get(4), corners.get(3)),
                Rx / 2, Ry / 2, level - 1,
                camera.getPij(Pc, Ry, Rx, 2, 2, 1, 1)));
        return sumColors.reduce(4);

    }

    /**
     * function to find  list of the 4 ray colors in the corners of the pixel
     *
     * @param rays-list of rays from the corners of the pixel
     * @return list of colors
     */
    private List<Color> listOfColors(List<Ray> rays) {
        List<Color> colors = new LinkedList<Color>();
        for (var ray1 : rays) {
            Color newColor = tracer.traceRay(ray1);
            colors.add(newColor);
        }
        return colors;


    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }
}
/**
 * Render Creates from the scene the color matrix of the image
 */
//public class Render {
//    private ImageWriter _imageWriter;
//
//    private Camera _camera;
//    private RayTracerBase _rayTracer;
//    //A Boolean mod that turns on and off the enhancement feature of supersampling
//    private boolean _isAntialiasing = false;
//
//    /**
//     * A function that returns the improvement state whether it is on or off
//     *
//     * @return
//     */
//    public boolean isAntialiasing() {
//        return _isAntialiasing;
//    }
//
//    /**
//     * A variable placement function that indicates whether the supersampling feature enhancement is enabled or disabled
//     *
//     * @param antialiasing -A Boolean variable to indicate the status of the feature whether it is off or active
//     * @return the render
//     */
//    public Render setAntialiasing(boolean antialiasing) {
//        _isAntialiasing = antialiasing;
//        return this;
//    }
//
//    /**
//     * changing
//     * set function for imageWriter Updating
//     *
//     * @param imageWriter image Writer
//     * @return Object itself for threading
//     */
//    public Render setImageWriter(ImageWriter imageWriter) {
//        _imageWriter = imageWriter;
//        return this;
//    }
//
//    /**
//     * set function for camera Updating
//     *
//     * @param cam camera
//     * @return Object itself for threading
//     */
//    public Render setCamera(Camera cam) {
//        _camera = cam;
//        return this;
//    }
//
//    /**
//     * set function for basicRayTracer Updating
//     *
//     * @param basicRayTracer basic Ray Tracer
//     * @return Object itself for threading
//     */
//    public Render setRayTracer(BasicRayTracer basicRayTracer) {
//        _rayTracer = basicRayTracer;
//        return this;
//    }
//
//    /**
//     * write To Image function
//     */
//    public void writeToImage() {
//        if (_imageWriter == null) {
//            throw new MissingResourceException("image writer is null", ImageWriter.class.getName(), "");
//        }
////        sublimation
//        _imageWriter.writeToImage();
//    }
//
//    /**
//     * That in all fields a non-empty value was entered
//     */
//    public void renderImage() {
//        int nX = _imageWriter.getNx();
//        int nY = _imageWriter.getNy();
//        // loop on viewPlane pixels
//        for (int i = 0; i < nY; i++) {
//            for (int j = 0; j < nX; j++) {
//                //For each pixel will be built a ray and for each ray we will get a color from the horn comb. Does the color women in the appropriate pixel of the image maker
//                Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
//                Color color = _rayTracer.traceRay(ray);
//                // If the supersampling feature scattering parameter is active then we will divide each pixel into several pixels and sample additional rays to calculate the color of the dot accurately
//                if (_isAntialiasing) {
//                    List<Ray> rays = _camera.constructRaysThroughPixel(ray, nX, nY, j, i);
//                    for (var ray1 : rays) {
//                        color = color.add(_rayTracer.traceRay(ray1));
//
//                    }
//                    //The average color of the rays coming out of one pixel
//                    color = color.reduce(rays.size() + 1);
//                }
//
//                _imageWriter.writePixel(j, i, color);
//            }
//        }
//    }
//
//    /**
//     * Create a grid of lines similar to what was done in the test in the first step.
//     *
//     * @param interval
//     * @param color
//     */
//    public void printGrid(int interval, Color color) {
//        if (_imageWriter == null) {
//            throw new MissingResourceException("image writer is null", ImageWriter.class.getName(), "");
//        }
//        int nX = _imageWriter.getNx();
//        int nY = _imageWriter.getNy();
//        for (int i = 0; i < nY; i++) {
//            for (int j = 0; j < nX; j++) {
//                if (i % interval == 0 || j % interval == 0) {
//                    _imageWriter.writePixel(j, i, color);
//                }
//            }
//        }
//    }
//}
//
