package menu;

import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Settings extends JFrame {
    private JPanel root;
    private JLabel difficultyLabel;
    private JLabel soundLabel;
    private JComboBox difficultyComboBox;
    private JSpinner soundSpinner;
    private JLabel backLabel;
    private JPanel backgroundPanel;
    private static Settings instance;
    private FloatControl gainControl = (FloatControl) Menu.getClip().getControl(FloatControl.Type.MASTER_GAIN);


    private Settings(String difficulty, int volume) {
        setUp();
        gainControl.setValue(gainControl.getMaximum());
        difficultyComboBox.setSelectedItem(difficulty);
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
        difficultyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Menu.setDifficulty((String) difficultyComboBox.getSelectedItem());
            }
        });
        soundSpinner.setModel(new SpinnerNumberModel(volume, 0, 100, 1));
        Component[] comps = soundSpinner.getEditor().getComponents();
        for (Component component : comps) {
            component.setFocusable(false);
        }
        soundSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float previousVolume = Menu.getVolume();
                int newVolume = (int) soundSpinner.getValue();
                if (newVolume > previousVolume)
                    gainControl.setValue(gainControl.getValue() + 0.6f);
                else
                    gainControl.setValue(gainControl.getValue() - 0.6f);
                System.out.println(gainControl.getValue());
                Menu.setVolume(newVolume);
            }
        });
        add(root);
//        setPreferredSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    private void setUp() {
        backLabel.setIcon(new ImageIcon("res/icons/backIcon.png"));

        Font fontMario = ResourceLoader.loadFont();
        Font fontForLabel = fontMario.deriveFont(Font.PLAIN, 80);
        Font fontForOther = fontMario.deriveFont(Font.PLAIN, 70);

        difficultyLabel.setFont(fontForLabel);
        difficultyLabel.setForeground(Color.WHITE);
        soundLabel.setFont(fontForLabel);
        soundLabel.setForeground(Color.WHITE);
        soundSpinner.setFont(fontForOther);
        difficultyComboBox.setFont(fontForOther);
    }

    static void getInstance(String difficulty, int volume) {
        if (instance == null) {
            instance = new Settings(difficulty, volume);
        } else
            instance.toFront();
    }

    private void createUIComponents() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("res/backgrounds/SettingsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new MyPanel(myImage);
    }
}
