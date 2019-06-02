package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResourceLoader {

    public static final String	PATH	= "res\\textures\\";

    public static BufferedImage loadImage(String fileName) {

        BufferedImage image = null;

        try {

            image = ImageIO.read(new File(PATH + fileName));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public static Font loadFont() {
        Font fontMario = null;
        try {
            fontMario = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File("res/FontMario.ttf")));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return fontMario;
    }

}