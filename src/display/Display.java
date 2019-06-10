package display;

import IO.Input;
import game.Game;
import menu.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Display {

    private static boolean			created	= false;
    private static JFrame			window;
    private static Canvas			content;

    private static BufferedImage	buffer;
    private static int[]			bufferData;
    private static Graphics			bufferGraphics;
    private static int				clearColor;

    private static BufferStrategy	bufferStrategy;
    private static Game game;
    private static int lives;
    private static BufferedImage heartImage;
    private static BufferedImage gameOver;
    private static BufferedImage victory;

    public static void create(int width, int height, String title, int _clearColor, int numBuffers,
                              Game game, Menu menu, int lives) {

        Display.game = game;
        Display.lives = lives;

        try {
            heartImage = ImageIO.read(new File("res/heartImage.png"));
            gameOver = ImageIO.read(new File("res/gameOver.jpg"));
            victory = ImageIO.read(new File("res/victory.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        window = new JFrame(title);
//        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        content = new Canvas();

        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);

//        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();
        ((Graphics2D) bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    game.setPaused(true);
                    menu.setVisible(true);
                    menu.getResumeBtn().setEnabled(true);
                }
            }
        });

        created = true;

    }

    public static void gameUnPause() {
        game.setPaused(false);
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }

    public static void swapBuffers() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        int x = 10;
        int y = 10;
        for (int i = 0; i < lives; i++) {
            g.drawImage(heartImage, x, y, 50, 50, null);
            x += 60;
        }
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }

    public static void destroy() {

        if (!created)
            return;

        window.dispose();

    }

    public static void setTitle(String title) {

        window.setTitle(title);

    }

    public static void addInputListener(Input inputListener) {
        window.add(inputListener);
    }

    public static void gameOver() {
        Graphics g = content.getGraphics();
        g.drawImage(gameOver, 0, 0, window.getWidth(), window.getHeight(), null);
    }

    public static void dispose() {
        window.dispose();
    }

    public static void victory() {
        Graphics g = content.getGraphics();
        g.drawImage(victory, 0, 0, window.getWidth(), window.getHeight(), null);
    }
}