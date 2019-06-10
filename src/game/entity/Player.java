package game.entity;

import IO.Input;
import display.Display;
import game.Game;
import game.level.Level;
import graphics.AnimatedSprite;
import graphics.Sprite;
import graphics.TextureAtlas;
import menu.Menu;
import utils.Utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Walker {

    public static final int SPRITE_SCALE = 16;
    public static final int ANIMATION_TIME = 10;
    public static final int ROTATE_SPEED = 30;

    private Animation animation;
    private Map<Animation, AnimatedSprite> spriteMap;
    private float scale;
    private float sprintSpeed;
    private float jumpPower;
    private float jumpSpeed;
    private int boostSpeed;

    private Menu menu;
    private Game game;
    private boolean start;
    private int lives;
    private ArrayList<Entity> entities;
    private Rectangle enemyRect;
    private Rectangle playerRect;
    private Rectangle playerBottomRect;
    private static AudioInputStream audioInputStream;
    private static Clip clip;


    public Player(float x, float y, float scale, float speed, float sprintSpeed, float gravity, float jumpPower,
                  TextureAtlas atlas, Menu menu, Game game, int lives, Level level) {
        super(EntityType.Player, x, y, SPRITE_SCALE * scale, SPRITE_SCALE * scale, speed);

        this.menu = menu;
        this.game = game;
        Level.setOffsetX(0);
        entities = level.getEntities();

        this.scale = scale;
        animation = Animation.FRONT_RIGHT;
        spriteMap = new HashMap<>();

        this.sprintSpeed = sprintSpeed;
        this.gravity = gravity;
        this.gravityEnabled = true;
        this.jumpPower = jumpPower;
        this.lives = lives;

        AnimatedSprite animatedSprite = new AnimatedSprite(scale, 4, ANIMATION_TIME);
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            animatedSprite.addSprite(atlas.cut(18 + i * 16 + i, 34, 16, 16), j);
        }
        animatedSprite.addSprite(animatedSprite.getSprite(1), 3);
        spriteMap.put(Animation.EAST, animatedSprite);

        animatedSprite = new AnimatedSprite(scale, 4, ANIMATION_TIME);
        for (int i = 2, j = 0; i >= 0; i--, j++) {
            animatedSprite.addSprite(Utils.mirrorEntity(atlas.cut(18 + i * 16 + i, 34, 16, 16)), j);
        }
        animatedSprite.addSprite(animatedSprite.getSprite(1), 3);
        spriteMap.put(Animation.WEST, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(atlas.cut(1, 34, 16, 16), scale));
        spriteMap.put(Animation.FRONT_RIGHT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(Utils.mirrorEntity(atlas.cut(1, 34, 16, 16)), scale));
        spriteMap.put(Animation.FRONT_LEFT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(atlas.cut(16 * 4 + 5, 34, 16, 16), scale));
        spriteMap.put(Animation.ROTATE_RIGHT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(Utils.mirrorEntity(atlas.cut(16 * 4 + 5, 34, 16, 16)), scale));
        spriteMap.put(Animation.ROTATE_LEFT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(atlas.cut(16 * 5 + 6, 34, 16, 16), scale));
        spriteMap.put(Animation.JUMP_RIGHT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(Utils.mirrorEntity(atlas.cut(16 * 5 + 6, 34, 16, 16)), scale));
        spriteMap.put(Animation.JUMP_LEFT, animatedSprite);

        animatedSprite = new AnimatedSprite(new Sprite(atlas.cut(16 * 6 + 7, 34, 16, 16), scale));
        spriteMap.put(Animation.DEATH, animatedSprite);

    }


    @Override
    public void update(Input input, Level level) {
        super.update(input, level);

        float newX = x;
        float newY = y;
        float newXOffset = Level.getOffsetX();

        Animation newAnimation;
        float speed = this.speed;

        if (input.getKey(KeyEvent.VK_X))
            speed += sprintSpeed;

        if (input.getKey(KeyEvent.VK_RIGHT) && rightClear) {
            if (boostSpeed >= 0) {
                boostSpeed = (boostSpeed >= ROTATE_SPEED) ? boostSpeed : ++boostSpeed;
                newX += speed;
                newAnimation = Animation.EAST;
                if (newAnimation != animation) {
                    animation = newAnimation;
                    spriteMap.get(animation).resetAnimation();
                }
            } else {
                boostSpeed++;
                if (leftClear)
                    newX -= speed;
                animation = Animation.ROTATE_LEFT;
            }
        } else if (input.getKey(KeyEvent.VK_LEFT) && leftClear) {
            if (boostSpeed <= 0) {
                boostSpeed = (boostSpeed >= -ROTATE_SPEED) ? --boostSpeed : boostSpeed;
                newX -= speed;
                newAnimation = Animation.WEST;
                if (newAnimation != animation) {
                    animation = newAnimation;
                    spriteMap.get(animation).resetAnimation();
                }
            } else {
                boostSpeed--;
                if (rightClear)
                    newX += speed;
                animation = Animation.ROTATE_RIGHT;
            }
        } else if (input.getKey(KeyEvent.VK_DOWN) && gravityEnabled) {
            newY += speed;
        } else {
            if (animation == Animation.EAST) {
                if (boostSpeed == 0)
                    animation = Animation.FRONT_RIGHT;
                else {
                    boostSpeed--;
                    if (rightClear)
                        newX += speed;
                    else
                        spriteMap.get(animation).resetAnimation();
                }
            } else if (animation == Animation.WEST) {
                if (boostSpeed == 0)
                    animation = Animation.FRONT_LEFT;
                else {
                    boostSpeed++;
                    if (leftClear)
                        newX -= speed;
                    else
                        spriteMap.get(animation).resetAnimation();
                }
            } else if (animation == Animation.ROTATE_RIGHT) {
                if (boostSpeed == 0) {
                    animation = Animation.FRONT_LEFT;
                } else {
                    boostSpeed--;
                    if (rightClear)
                        newX += speed;
                    else
                        spriteMap.get(animation).resetAnimation();
                }
            } else if (animation == Animation.ROTATE_LEFT) {
                if (boostSpeed == 0) {
                    animation = Animation.FRONT_RIGHT;
                } else {
                    boostSpeed++;
                    if (leftClear)
                        newX -= speed;
                    else
                        spriteMap.get(animation).resetAnimation();
                }
            }
        }
        if (input.getKey(KeyEvent.VK_SPACE) && upClear && !gravityEnabled) {
            if (jumpSpeed <= 0) {
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(
                            new File("res/sound/player/jump.wav").getAbsoluteFile());
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                } catch(Exception ex) {
                    System.out.println("Error with playing sound.");
                    ex.printStackTrace();
                }
                clip.start();
                jumpSpeed = jumpPower;
                if (animation == Animation.FRONT_RIGHT || animation == Animation.EAST) {
                    animation = Animation.JUMP_RIGHT;
                } else if (animation == Animation.FRONT_LEFT || animation == Animation.WEST) {
                    animation = Animation.JUMP_LEFT;
                }
            }
        }

        if (jumpSpeed > 0 && upClear) {
            newY -= jumpSpeed;
            jumpSpeed -= jumpPower / 40;
        } else {
            jumpSpeed = 0;
        }

        if (gravityEnabled)
            newY += gravity;

        if (newY == y) {
            if (animation == Animation.JUMP_RIGHT)
                animation = Animation.FRONT_RIGHT;
            else if (animation == Animation.JUMP_LEFT)
                animation = Animation.FRONT_LEFT;
        }

        if (newX < 0) {
            newX = 0;
            spriteMap.get(animation).resetAnimation();
            boostSpeed = 0;
            animation = Animation.FRONT_LEFT;
        }


        if (newY >= Game.height - SPRITE_SCALE * 5) {
            animation = Animation.DEATH;
            if (newY >= Game.height - SPRITE_SCALE) {
                loseLife();
            }

        }

        for (Entity entity : entities) {
            if (entity.type == EntityType.Player)
                continue;
            enemyRect = new Rectangle((int) entity.x, (int) (entity.y - entity.getHeight()), (int) entity.getWidth(),
                    (int) entity.getHeight());
            playerRect = new Rectangle((int) x, (int) (y - height), (int) width, (int) height - 2);
            playerBottomRect = new Rectangle((int) x + 2, (int) y, (int) width - 4, 2);
            if (playerBottomRect.intersects(enemyRect))
                entity.x = -100;
            else if (playerRect.intersects(enemyRect)) {
                loseLife();
            }


        }

        if (newX >= Game.width / 2.5 && x < newX) {
            newXOffset -= speed;
            if (leftClear)
                newX -= speed;
            else
                spriteMap.get(animation).resetAnimation();
        }

        Level.setOffsetX(newXOffset);
        x = newX;
        y = newY;


    }


    @Override
    public void render(Graphics2D g) {
        super.render(g);
        spriteMap.get(animation).render(g, x, y);
    }


    public float getWidth() {
        return spriteMap.get(animation).getWidth();
    }


    public float getHeight() {
        return spriteMap.get(animation).getHeight();
    }


    private enum Animation {
        EAST,
        WEST,
        FRONT_RIGHT,
        FRONT_LEFT,
        JUMP_RIGHT,
        JUMP_LEFT,
        ROTATE_RIGHT,
        ROTATE_LEFT,
        DEATH,
    }

    private void loseLife() {
        if (!start) {
            lives--;
            if (lives == 0) {
                Display.gameOver();
                Menu.setClip("res/sound/gameOver.wav");
                Game.sleep(3300);
                Display.dispose();
                Menu.setClip("res/sound/environment/overWorld.wav");
            } else {
                Menu.setClip("res/sound/marioDies.wav");
                Game.sleep(2300);
                Display.dispose();
                Game mario = new Game(menu, lives);
                mario.start();
            }
            game.stop();
            start = true;
        }
    }
}