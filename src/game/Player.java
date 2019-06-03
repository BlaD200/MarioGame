package game;

import IO.Input;
import graphics.AnimatedSprite;
import display.Display;
import game.level.Level;
import game.level.Tile;
import graphics.Sprite;
import graphics.TextureAtlas;
import utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADING = 1;
    public static final int ANIMATION_TIME = 10;
    private Heading heading;
    private Map<Heading, AnimatedSprite> spriteMap;
    private float scale;
    private float speed;
    private float gravity;
    private float jumpPower;
    private int jumpCount;

    public Player(float x, float y, float scale, float speed, float gravity, float jumpPower, TextureAtlas atlas) {
        super(EntityType.Player, x, y);

        heading = Heading.FRONT_RIGHT;
        spriteMap = new HashMap<Heading, AnimatedSprite>();
        this.scale = scale;
        this.speed = speed;
        this.gravity = gravity;
        this.jumpPower = jumpPower;
        //        this.jumpCount = jumpCount;

        //        for (Heading h : Heading.values()) {
        //            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, (int) (SPRITE_SCALE * scale));
        //            Sprite sprite = new Sprite(sheet, scale);
        //            spriteMap.put(h, new AnimatedSprite(sprite));
        //        }

        AnimatedSprite animatedSprite = new AnimatedSprite(scale, 4, ANIMATION_TIME);
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            animatedSprite.addSprite(atlas.cut(18 + i * 16 + i, 34, 16, 16), j);
        }
        animatedSprite.addSprite(animatedSprite.getSprite(1), 3);
        spriteMap.put(Heading.EAST, animatedSprite);

        animatedSprite = new AnimatedSprite(scale, 4, ANIMATION_TIME);
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            animatedSprite.addSprite(Utils.mirrorEntity(atlas.cut(18 + i * 16 + i, 34, 16, 16)), j);
        }
        animatedSprite.addSprite(animatedSprite.getSprite(1), 3);
        spriteMap.put(Heading.WEST, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(atlas.cut(1, 34, 16, 16), scale));
        spriteMap.put(Heading.FRONT_RIGHT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(Utils.mirrorEntity(atlas.cut(1, 34, 16, 16)), scale));
        spriteMap.put(Heading.FRONT_LEFT, animatedSprite);

    }


    @Override
    public void update(Input input) {

        float newX = x;
        float newY = y;
        float newXOffset = Level.getOffsetX();

        Heading newHeading;

        if (input.getKey(KeyEvent.VK_RIGHT)) {
            newX += speed;
            newHeading = Heading.EAST;
            if (newHeading != heading) {
                heading = newHeading;
                spriteMap.get(heading).resetAnimation();
            }
        } else if (input.getKey(KeyEvent.VK_LEFT)) {
            if (x >= Game.WIDTH / 2.5)
                newXOffset -= speed;
            else
                newX += speed;
            heading = Heading.EAST;
        }
        if (input.getKey(KeyEvent.VK_LEFT)){
            newX -= speed;
            newHeading = Heading.WEST;
            if (newHeading != heading) {
                heading = newHeading;
                spriteMap.get(heading).resetAnimation();
            }
        } else {
            if (heading == Heading.EAST) {
                heading = Heading.FRONT_RIGHT;
            } else if (heading == Heading.WEST)
                heading = Heading.FRONT_LEFT;
        }
        if (input.getKey(KeyEvent.VK_SPACE)) {
            if (newY <= Game.HEIGHT - SPRITE_SCALE * scale) {
                newY -= jumpPower;
            }
        }

        newY += gravity;

        if (newX < 0) {
            newX = 0;
        }

        if (newY >= Game.HEIGHT - SPRITE_SCALE * scale) {
            newY = Game.HEIGHT - SPRITE_SCALE * scale;
        }

        Level.setOffsetX(newXOffset);
        x = newX;
        y = newY;
    }


    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }


    private enum Heading {
        NORTH(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        FRONT_RIGHT(0, 0, 0, 0),
        FRONT_LEFT(0, 0, 0, 0);

        private int x, y, h, w;


        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }


        protected BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }

}