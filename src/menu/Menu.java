package menu;

import levelEditor.LevelEditor;
import utils.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Menu extends JFrame {
    private JPanel root;
    private JButton playBtn;
    private JButton settingsBtn;
    private JButton createLvlBtn;
    private JButton resumeBtn;
    private JLabel exitLabel;
    private JPanel backgroundPanel;
    private JLabel titleLabel;
    private static ArrayList<String> levelNames = new ArrayList<>();

    public Menu() {
        setUp();
        exitLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                        "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == 0)
                    System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitLabel.setIcon(new ImageIcon("res/icons/exitIconOver.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitLabel.setIcon(new ImageIcon("res/icons/exitIcon.png"));
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
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Play.getInstance();
            }
        });

        add(root);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    private void setUp() {
        try (Stream<Path> paths = Files.walk(Paths.get("res/levels"))) {
            paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(String::valueOf)
                    .collect(Collectors.toCollection(() -> levelNames));
        } catch (IOException e) {
            e.printStackTrace();
        }

        exitLabel.setIcon(new ImageIcon("res/icons/ExitIcon.png"));

        Font fontMario = ResourceLoader.loadFont();
        Font fontForBtn = fontMario.deriveFont(Font.PLAIN, 60);
        Font titleFont = fontMario.deriveFont(Font.PLAIN, 90);

        playBtn.setFont(fontForBtn);
        settingsBtn.setFont(fontForBtn);
        createLvlBtn.setFont(fontForBtn);
        resumeBtn.setFont(fontForBtn);
        titleLabel.setFont(titleFont);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }

    private void createUIComponents() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("res/backgrounds/MenuBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundPanel = new MyPanel(myImage);
    }

    public static void deleteLevel(String name) {
        levelNames.remove(name);
    }

    public static ArrayList<String> getLevelNames() {
        return levelNames;
    }

}