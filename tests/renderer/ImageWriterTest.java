package renderer;



import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering Initial image construction - a single-color image with a second-color grid of lines
 *
 */
public class ImageWriterTest {

    @Test
    void writeToImage() {
        ImageWriter imageWriter=new ImageWriter("test blue image",500,800);
        for (int i = 0; i <500 ; i++) {
            for (int j = 0; j <800 ; j++) {
                // 800/16 = 50
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
                // 500/10 = 50
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, Color.BLACK);
                }
                else {
                    imageWriter.writePixel(i, j, new Color(0d,0d,255d));
                }
            }

        }
        imageWriter.writeToImage();
    }

    //void write
}
