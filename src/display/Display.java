package display;

import IO.Input;
import game.Game;
import menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
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
    private static Menu menu;

    public static void create(int width, int height, String title, int _clearColor, int numBuffers,
                              Game game, Menu menu) {

        if (created)
            return;

        Display.game = game;
        Display.menu = menu;

        window = new JFrame(title);
//        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        window.setUndecorated(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
}