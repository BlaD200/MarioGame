package graphics;

import utils.Utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {

    private float		scale;
    private BufferedImage image;

    public Sprite(BufferedImage image, float scale) {
        this.scale = scale;
        this.image = Utils.resize(image, (int) (image.getWidth() * scale),
                (int) (image.getHeight() * scale));
    }

    public Sprite(SpriteSheet sheet, float scale){
        this(sheet.getSprite(0), scale);
    }

    public void render(Graphics2D g, float x, float y) {
        g.drawImage(image, (int) (x), (int) (y), null);
    }


    public float getScale() {
        return scale;
    }
}