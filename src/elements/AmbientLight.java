package elements;

import primitives.Color;

public class AmbientLight {
    private final Color _intensity;

    public AmbientLight(Color Ia, double Ka) {
        _intensity=Ia.scale(Ka);

    }

    public Color getIntensity(){
        return _intensity;
    }

}
