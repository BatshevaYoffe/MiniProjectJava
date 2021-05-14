package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;
import primitives.Material;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class Scene {
    private final String _name;
    public Geometries geometries;

    public AmbientLight ambientLight=new AmbientLight();
    public Color backgroundColor =Color.BLACK;
    public List<LightSource> lights = new LinkedList<>();

    /**
     * A builder that gets the name of the scene (only) that will also build an empty collection of bodies for model D3.
     * @param name ,the scene name
     */
    public Scene(String name){
        _name=name;
        geometries=new Geometries();
    }

    /**
     * set function for ambientLight
     * @param ambientLight
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight=ambientLight;
        return this;
    }

    /**
     * set function for color
     * @param color
     * @return the scene
     */
    public Scene setBackground(Color color) {
        this.backgroundColor =color;
        return this;
    }

    /**
     * set function for geometries
     * @param geometries
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return  this;
    }

}
