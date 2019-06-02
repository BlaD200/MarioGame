package Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    //    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private Settings() {
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                instance = null;
            }
        });
        soundSpinner.setModel(new SpinnerNumberModel(50, 0, 100, 1));
        add(root);
//        setPreferredSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    static void getInstance() {
        if (instance == null) {
            instance = new Settings();
        } else
            instance.toFront();
    }

    private void createUIComponents() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("res/SettingsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new MyPanel(myImage);
    }
}
