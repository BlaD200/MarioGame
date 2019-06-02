package graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class AnimatedSprite {

    private int animationTime;
    private int nowTime;
    private int spriteCount;
    private float scale;

    private Sprite[] sprites;
    private Sprite sprite;
    private int spriteNumber;


    public AnimatedSprite(Sprite sprite){
        this.sprite = sprite;
        this.scale = sprite.getScale();
        this.animationTime = 0;
        this.spriteCount = 1;
        this.sprites = new Sprite[]{sprite};
    }

    public AnimatedSprite(float scale, int spriteCount, int animationTime) {
        this.scale = scale;
        this.animationTime = animationTime;
        this.spriteCount = spriteCount;
        this.sprites = new Sprite[spriteCount];
    }


    public AnimatedSprite(SpriteSheet sheet, float scale, int spriteCount, int animationTime) {
        this(scale, spriteCount, animationTime);

        sprites = new Sprite[spriteCount];
        for (int i = 0; i < spriteCount; i++) {
            sprites[i] = new Sprite(sheet.getSprite(i), scale);
        }
        sprite = sprites[0];
    }


    public AnimatedSprite(BufferedImage[] images, float scale, int spriteCount, int animationTime) {
        this(scale, spriteCount, animationTime);

        sprites = new Sprite[spriteCount];
        for (int i = 0; i < spriteCount; i++) {
            sprites[i] = new Sprite(images[i], scale);
        }
        sprite = sprites[0];
    }


    public void render(Graphics2D g, float x, float y) {
        if (sprite != null) {
            sprite.render(g, x, y);

            nowTime++;
            if (nowTime == animationTime) {
                nowTime = 0;
                getNextImage();
            }
        }
    }


    public void resetAnimation(){
        nowTime = 0;
        sprite = sprites[0];
        spriteNumber = 0;
    }


    public Sprite getSprite(int index) throws IndexOutOfBoundsException{
        return sprites[index];
    }


    public void addSprite(Sprite sprite) {
        sprites = Arrays.copyOf(sprites, spriteCount + 1);
        sprites[spriteCount++] = sprite;
        if (spriteCount == 1){
            this.sprite = sprites[0];
        }
    }


    public void addSprite(BufferedImage image) {
        addSprite(new Sprite(image, scale));
    }


    public void addSprite(Sprite sprite, int index) throws IndexOutOfBoundsException {
        sprites[index] = sprite;
        if (this.sprite == null)
            this.sprite = sprites[index];
    }


    public void addSprite(BufferedImage image, int index) throws IndexOutOfBoundsException {
        addSprite(new Sprite(image, scale), index);
    }


    public void removeSprite(int index) throws IndexOutOfBoundsException {
        sprites[index] = null;

        for (int i = 0; i < sprites.length; i++) {
            int j = (sprites.length - 1 > i) ? i + 1 : -1;

            if (j == -1)
                break;
            else
                sprites[i] = sprites[j];

            sprites = Arrays.copyOf(sprites, sprites.length - 1);
            spriteCount--;
        }
    }


    private void getNextImage() {
        sprite = sprites[spriteNumber++ % spriteCount];
    }

}
