package elements;

import primitives.Color;

/**
 * Environmental lighting class
 */
public class AmbientLight  extends  Light{

    /**
     * constructor
     * @param Ia In light of original filling
     * @param Ka Coefficient of attenuation of filler light
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
     }

    /**
     * Default builder that initializes color=black
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

}
