package elements;

import primitives.Color;

/**
 * Environmental lighting class
 */
public class AmbientLight {
    //Fill lighting intensity
    private final Color _intensity;

    /**
     * constructor
     * @param Ia In light of original filling
     * @param Ka Coefficient of attenuation of filler light
     */
    public AmbientLight(Color Ia, double Ka) {
        _intensity=Ia.scale(Ka);

    }

    /**
     * Default builder that initializes color=black
     */
    public AmbientLight() {
        _intensity = Color.BLACK;
    }

    /**
     * get function
     * @return the value of the ambient lighting intensity
     */
    public Color getIntensity(){
        return _intensity;
    }

}
