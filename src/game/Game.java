package game;

import IO.Input;
import display.Display;
import game.level.Level;
import graphics.TextureAtlas;
import utils.Time;

import java.awt.*;

public class Game implements Runnable {

    public static final int		WIDTH			= 800;
    public static final int		HEIGHT			= 600;
    public static final String	TITLE			= "Tanks";
    public static final int		CLEAR_COLOR		= 0xff6B8BFE;
    public static final int		NUM_BUFFERS		= 3;

    public static final float	UPDATE_RATE		= 60.0f;
    public static final float	UPDATE_INTERVAL	= Time.SECOND / UPDATE_RATE;
    public static final long	IDLE_TIME		= 1;

    public static final String	ATLAS_FILE_NAME	= "lvl_textures.png";

    private boolean				running;
    private Thread				gameThread;
    private Graphics2D			graphics;
    private Input				input;
    private TextureAtlas		atlas;
    private Player				player;
    private Level               level;

    public Game() {
        running = false;
        System.out.println(new Color(0x6B8BFE).getRGB());
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        player = new Player(300, 300, 2, 3, 5, 10, atlas);
        level = new Level(atlas);
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
        level.update();
        player.update(input);
    }

    private void render() {
        Display.clear();
        level.render(graphics);
        player.render(graphics);
        Display.swapBuffers();
    }

    public void run() {

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

    private void cleanUp() {
        Display.destroy();
    }

}