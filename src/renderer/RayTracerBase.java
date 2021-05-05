package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected final Scene _scene;

    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}
