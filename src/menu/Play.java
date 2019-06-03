package menu;

import game.Game;
import game.level.Level;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Play extends JFrame {
    private JPanel root;
    private JScrollPane scrollPane;
    private JLabel backLabel;
    private JPanel mainPanel;
    private static Play instance;

    public Play() {
        setUp();
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                instance = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backLabel.setIcon(new ImageIcon("res/icons/backIconOver.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backLabel.setIcon(new ImageIcon("res/icons/backIcon.png"));
            }
        });
        add(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    @SuppressWarnings("Duplicates")
    private void setUp() {
        backLabel.setIcon(new ImageIcon("res/icons/backIcon.png"));

        Font fontMario = ResourceLoader.loadFont();
        Font fontForLabel = fontMario.deriveFont(Font.PLAIN, 120);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        ArrayList<String> levelNames = Menu.getLevelNames();
        int y = -1;
        int x = 0;
        for (int i = 0; i < levelNames.size(); i++) {
            if (i % 2 == 0) {
                x = 0;
                y++;
            }
            drawLevelLabel(panel, x, y, levelNames.get(i), fontForLabel);
            x++;
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        mainPanel.add(panel, gbc);
    }

    private void drawLevelLabel(JPanel panel, int x, int y, String name, Font font) {
        GridBagConstraints gbcForLabels = new GridBagConstraints();
        gbcForLabels.gridx = x;
        gbcForLabels.gridy = y;
        gbcForLabels.weightx = 1.0;
        gbcForLabels.weighty = 1.0;

        JLabel label = new JLabel(name);
        label.setFont(font);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String level = label.getText();
                Level.setLevel(level + ".lvl");
                Game mario = new Game();
                mario.start();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLACK);
            }
        });

        panel.add(label, gbcForLabels);
    }

    static void getInstance() {
        if (instance == null) {
            instance = new Play();
        } else {
            instance.toFront();
        }
    }


    private void createUIComponents() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("res/backgrounds/PlayBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainPanel = new MyPanel(myImage);
    }
}
