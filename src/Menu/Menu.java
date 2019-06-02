package Menu;

import levelEditor.LevelEditor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame {
    private JPanel root;
    private JButton playBtn;
    private JButton settingsBtn;
    private JButton createLvlBtn;
    private JButton resumeBtn;
    private JLabel exitBtn;
    private JPanel backgroundPanel;

    public Menu() {
        setUp();
        exitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                        "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0)
                    System.exit(0);
            }
        });
        settingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.getInstance();
            }
        });
        createLvlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LevelEditor.getInstance();
            }
        });

        add(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    private void setUp() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }

    private void createUIComponents() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("res/MenuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new MyPanel(myImage);
    }
}
