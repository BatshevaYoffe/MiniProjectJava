package elements;

import primitives.Color;

class Light {
    //Fill lighting intensity
    protected final Color _intensity;

    /**
     * constructor that Initializing
     * @param intensity
     */
    Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * get function
     * @return the value of the ambient lighting intensity
     */
    public Color getIntensity(){
        return _intensity;
    }
}
