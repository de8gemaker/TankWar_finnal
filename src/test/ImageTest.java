package test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {

    void test(){
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\CED\\Documents\\mashibing\\tank\\tank\\src\\images\\GoodTank1.png"));
            assertNotNull(image);

            BufferedImage image1 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif"));
            //BufferedImage image1 = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/bulletD.gif"));
            assertNotNull(image1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
