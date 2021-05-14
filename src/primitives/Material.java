package primitives;

public class Material {
    public double kD = 0; // for diffuse
    public double kS = 0; // for  specular
    public int nShininess = 0;

    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
