package game;

import IO.Input;
import display.Display;
import game.entity.Enemy;
import game.entity.EntityType;
import game.entity.Player;
import game.entity.Walker;
import game.level.Level;
import graphics.Sprite;
import graphics.TextureAtlas;
import menu.Menu;
import utils.Time;
import java.awt.*;

public class Game implements Runnable {

    public static int           width  = 1280;
    public static int           height = 720;
    public static final String	TITLE			= "Mario";
    public static final int		CLEAR_COLOR		= 0xff6B8BFE;
    public static final int		NUM_BUFFERS		= 3;

    public static final float	UPDATE_RATE		= 60.0f;
    public static final float	UPDATE_INTERVAL	= Time.SECOND / UPDATE_RATE;
    public static final long	IDLE_TIME		= 1;

    public static final String LVL_TEXTURES_ATLAS_FILE_NAME = "lvl_textures.png";
    public static final String PLAYER_TEXTURES_ATLAS_FILE_NAME = "character_textures.png";
    public static final String OBJECT_ATLAS_FILE_NAME = "object_textures.png";
    public static final String ENEMY_ATLAS_FILE_NAME = "enemies_atlas.png";

    public static final boolean IS_DEBUG = false;

    private boolean				running;
    private Thread				gameThread;
    private Graphics2D			graphics;
    private Input				input;
    private TextureAtlas        lvlAtlas;
    private TextureAtlas        objectAtlas;
    private TextureAtlas        enemyAtlas;
    private Level               level;
    private boolean paused = false;

    public Game(Menu menu, int lives) {
        running = false;

        input = new Input();
        lvlAtlas = new TextureAtlas(LVL_TEXTURES_ATLAS_FILE_NAME);
        objectAtlas = new TextureAtlas(OBJECT_ATLAS_FILE_NAME);
        enemyAtlas = new TextureAtlas(ENEMY_ATLAS_FILE_NAME);
        level = new Level(lvlAtlas, objectAtlas, input);

        TextureAtlas playerAtlas = new TextureAtlas(PLAYER_TEXTURES_ATLAS_FILE_NAME);
        Player player = new Player(50, 300, 1.9f, 3, 1.5f, 5, 18,
                playerAtlas, menu, Game.this, lives, level);
        level.addEntity(player);

        Sprite enemySprite = new Sprite(enemyAtlas.cut(0, 16, 16, 16), 2);
        Sprite bigEnemySprite = new Sprite(enemyAtlas.cut(0, 16, 16, 16), 32);

        String levelNum = Level.getLevel();
        switch (levelNum) {
            case "Level_1.lvl":
                Enemy enemy0 = new Enemy(EntityType.Enemy, enemySprite, 600,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy0);
                Enemy enemy1 = new Enemy(EntityType.Enemy, enemySprite, 900,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy1);
                Enemy enemy2 = new Enemy(EntityType.Enemy, enemySprite, 1200,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy2);
                Enemy enemy3 = new Enemy(EntityType.Enemy, enemySprite, 1500,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy3);
                Enemy enemy4 = new Enemy(EntityType.Enemy, enemySprite, 3000,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy4);
                Enemy enemy5 = new Enemy(EntityType.Enemy, enemySprite, 3800,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy5);
                Enemy enemy6 = new Enemy(EntityType.Enemy, enemySprite, 4200,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy6);
                Enemy enemy7 = new Enemy(EntityType.Enemy, enemySprite, 4650,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy7);
                Enemy enemy8 = new Enemy(EntityType.Enemy, enemySprite, 5450,Game.height - 100, 32,32, 2, 5);
                level.addEntity(enemy8);
                break;
            case "Level_2.lvl":
                Enemy lvl2Enemy0 = new Enemy(EntityType.Enemy, enemySprite, 600,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl2Enemy0);
                Enemy lvl2Enemy1 = new Enemy(EntityType.Enemy, enemySprite, 1000,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl2Enemy1);
                Enemy lvl2Enemy2 = new Enemy(EntityType.Enemy, enemySprite, 1700,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl2Enemy2);
                Enemy lvl2Enemy3 = new Enemy(EntityType.Enemy, enemySprite, 2170,Game.height - 300, 32,32, 3, 5);
                level.addEntity(lvl2Enemy3);
                Enemy lvl2Enemy4 = new Enemy(EntityType.Enemy, enemySprite, 3300,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl2Enemy4);
                Enemy lvl2Enemy5 = new Enemy(EntityType.Enemy, enemySprite, 3800,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl2Enemy5);
                Enemy lvl2Enemy6 = new Enemy(EntityType.Enemy, enemySprite, 4500,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl2Enemy6);
                Enemy lvl2Enemy8 = new Enemy(EntityType.Enemy, enemySprite, 5400,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl2Enemy8);
                break;
            case "Level_3.lvl":
                Enemy lvl3Enemy0 = new Enemy(EntityType.Enemy, enemySprite, 600,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy0);
                Enemy lvl3Enemy1 = new Enemy(EntityType.Enemy, enemySprite, 1000,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy1);
                Enemy lvl3Enemy2 = new Enemy(EntityType.Enemy, enemySprite, 1700,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy2);
                Enemy lvl3Enemy3 = new Enemy(EntityType.Enemy, enemySprite, 2170,Game.height - 300, 32,32, 1, 5);
                level.addEntity(lvl3Enemy3);
                Enemy lvl3Enemy4 = new Enemy(EntityType.Enemy, enemySprite, 3300,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy4);
                Enemy lvl3Enemy5 = new Enemy(EntityType.Enemy, enemySprite, 3800,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy5);
                Enemy lvl3Enemy6 = new Enemy(EntityType.Enemy, enemySprite, 4500,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy6);
                Enemy lvl3Enemy8 = new Enemy(EntityType.Enemy, enemySprite, 5400,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl3Enemy8);
                break;
            case "Level_4.lvl":
                Enemy lvl4Enemy0 = new Enemy(EntityType.Enemy, enemySprite, 300,Game.height - 100, 32,32, 3, 5);
                level.addEntity(lvl4Enemy0);
                Enemy lvl4Enemy1 = new Enemy(EntityType.Enemy, enemySprite, 1150,Game.height - 100, 32,32, 1, 5);
                level.addEntity(lvl4Enemy1);
                Enemy lvl4Enemy2 = new Enemy(EntityType.Enemy, enemySprite, 3500,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy2);
                Enemy lvl4Enemy3 = new Enemy(EntityType.Enemy, enemySprite, 3600,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy3);
                Enemy lvl4Enemy4 = new Enemy(EntityType.Enemy, enemySprite, 3700,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy4);
                Enemy lvl4Enemy5 = new Enemy(EntityType.Enemy, enemySprite, 3800,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy5);
                Enemy lvl4Enemy6 = new Enemy(EntityType.Enemy, enemySprite, 3900,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy6);
                Enemy lvl4Enemy8 = new Enemy(EntityType.Enemy, enemySprite, 4000,30, 32,32, 4, 5);
                level.addEntity(lvl4Enemy8);
                Enemy lvl4Enemy9 = new Enemy(EntityType.Enemy, bigEnemySprite, 4900,Game.height - 570, 1024,1024, 0, 0);
                level.addEntity(lvl4Enemy9);
                break;
        }

        Display.create(width, height, TITLE, CLEAR_COLOR, NUM_BUFFERS, this, menu, lives);

        graphics = Display.getGraphics();
        Display.addInputListener(input);
    }


    public static void setSize(Dimension d){
        width = (int) d.getWidth();
        height = (int) d.getHeight();
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {

        if (running)
            return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public synchronized void stop() {

        if (!running)
            return;

        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanUp();

    }

    private void update() {
        if (!paused)
            level.update();
        else
            input = new Input();
    }

    private void render() {
        if (!paused) {
            Display.clear();
            level.render(graphics);
            Display.swapBuffers();
        }
    }

    public void run() {
        if (!paused) {
            int fps = 0;
            int upd = 0;
            int updl = 0;

            long count = 0;

            float delta = 0;

            long lastTime = Time.get();
            while (running) {
                long now = Time.get();
                long elapsedTime = now - lastTime;
                lastTime = now;

                count += elapsedTime;

                boolean render = false;
                delta += (elapsedTime / UPDATE_INTERVAL);
                while (delta > 1) {
                    update();
                    upd++;
                    delta--;
                    if (render) {
                        updl++;
                    } else {
                        render = true;
                    }
                }

                if (render) {
                    render();
                    fps++;
                } else {
                    try {
                        Thread.sleep(IDLE_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (count >= Time.SECOND) {
                    Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                    upd = 0;
                    fps = 0;
                    updl = 0;
                    count = 0;
                }

            }
        }
    }

    private void cleanUp() {
        Display.destroy();
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public void exit() {
    }
}