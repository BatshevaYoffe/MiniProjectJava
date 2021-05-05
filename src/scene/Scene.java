package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Point3D;
import primitives.Color;

import java.awt.*;

public class Scene {
    private final String _name;
    public Geometries geometries;

    public AmbientLight ambientLight;
    public Color backGroudColor;
    public Scene(String name){
        _name=name;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight=ambientLight;
        return this;
    }

    public Scene setBackground(Color color) {
        this.backGroudColor=color;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return  this;
    }

}
