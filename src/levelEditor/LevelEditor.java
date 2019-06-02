package levelEditor;

import game.Game;
import game.level.Level;
import game.level.Tile;
import game.level.TileType;
import graphics.TextureAtlas;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class LevelEditor extends JFrame {
    private JPanel levelPanel;
    private JTextField widthTextField;
    private JTextField heightTextField;
    private JPanel texturesPanel;
    private JPanel rootPanel;
    private JButton saveLevelButton;
    private JTextField levelNameTextField;
    private JScrollPane levelScrollPane;
    private JButton resizeButton;
    private JButton exitButton;
    private static LevelEditor instance;

    private int levelWidth;
    private int levelHeight;

    private Map<TileType, Tile> tiles;

    private TileType selectedTile;

    private int widthIndex;
    private int heightIndex;

    private int[][] tileMap;


    private LevelEditor() {
        getContentPane().add(rootPanel);

        levelWidth = Integer.parseInt(widthTextField.getText());
        levelHeight = Integer.parseInt(heightTextField.getText());
        tileMap = new int[levelHeight / Level.TILE_SCALE][levelWidth / Level.TILE_SCALE];

        resizeButton.addActionListener(e -> {
            levelWidth = Integer.parseInt(widthTextField.getText());
            levelHeight = Integer.parseInt(heightTextField.getText());

            tileMap = Arrays.copyOf(tileMap, levelHeight / Level.TILE_SCALE);

            for (int i = 0; i < tileMap.length; i++) {
                if (tileMap[i] == null) {
                    tileMap[i] = new int[levelWidth / Level.TILE_SCALE];
                }
            }

            for (int i = 0; i < tileMap.length; i++) {
                tileMap[i] = Arrays.copyOf(tileMap[i], levelWidth / Level.TILE_SCALE);
            }

            levelPanel.setPreferredSize(new Dimension(levelWidth, levelHeight));
            levelPanel.setMaximumSize(new Dimension(levelWidth, levelHeight));
            levelScrollPane.revalidate();
        });

        saveLevelButton.addActionListener(e -> {
            if (saveLevel(levelNameTextField.getText()))
                JOptionPane.showMessageDialog(this, "Level created successful.", "Level create",
                        JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Level created failed.", "Level create",
                        JOptionPane.ERROR_MESSAGE);
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "<html>Are you sure you want to exit? <br>" +
                        " You won't be able to edit this level in future</html>", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == 0)
                    dispose();

            }
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
//        try {
//            SwingUtilities.invokeAndWait(LevelEditor::new);
//        } catch (InvocationTargetException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    private void createUIComponents() {
        Level level = new Level(new TextureAtlas(Game.LEVEL_ATLAS_FILE_NAME), new TextureAtlas(Game.OBJECT_ATLAS_FILE_NAME));
        tiles = level.getTiles();

        int iconIndent = 2;

        texturesPanel = new JPanel(new GridBagLayout());

        GridBagLayout gbl = (GridBagLayout) texturesPanel.getLayout();

        int x = -1, y = 0;
        GridBagConstraints c = new GridBagConstraints();
        Font font = new Font("Arial", Font.PLAIN, 10);
        for (TileType value : TileType.values()) {
            JButton button = new JButton();
            BufferedImage image = new BufferedImage(36, 36, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = (Graphics2D) image.getGraphics();
            graphics2D.drawImage(Utils.resize(tiles.get(value).getImage(), 32, 32), iconIndent, iconIndent, null);
            button.setIcon(new ImageIcon(image));

            button.addActionListener(new ActionListener() {
                private TileType type = value;


                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedTile = type;
                }
            });

            JLabel label = new JLabel(value.toString());
            label.setFont(font);

            c.weightx = 0.5;
            c.gridy = (x == 3) ? y += 2 : y;
            c.gridx = (x == 3) ? x = 0 : ++x;

            gbl.setConstraints(label, c);
            texturesPanel.add(label, c);

            c.gridy = ++y;
            c.insets = new Insets(2, 2, 2, 2);
            gbl.setConstraints(button, c);
            texturesPanel.add(button, c);
            --y;
        }

        levelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawLine(0, 0, levelWidth, 0);
                g.drawLine(0, 0, 0, levelHeight);
                g.drawLine(levelWidth - 1, levelHeight - 1, levelWidth - 1, 0);
                g.drawLine(levelWidth - 1, levelHeight - 1, 0, levelHeight - 1);

                addTiles((Graphics2D) g);
            }
        };

        int width = 1000;
        int height = 800;
        levelPanel.setPreferredSize(new Dimension(width, height));
        levelPanel.setBackground(new Color(0x6B8BFE));

        levelPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                widthIndex = e.getX() / Level.TILE_SCALE;
                heightIndex = e.getY() / Level.TILE_SCALE;
                addTile();
            }
        });

    }


    private void addTile() {
        if (selectedTile != null) {
            if (widthIndex * 16 < levelWidth / 16 * 16 && heightIndex * 16 < levelHeight / 16 * 16 &&
                    widthIndex >= 0 && heightIndex >= 0) {
                Graphics2D graphics = (Graphics2D) levelPanel.getGraphics();
                if (selectedTile == TileType.Empty) {
                    graphics.setColor(levelPanel.getBackground());
                    graphics.fillRect(widthIndex * Level.TILE_SCALE, heightIndex * Level.TILE_SCALE, Level.TILE_SCALE,
                            Level.TILE_SCALE);
                } else
                    graphics.drawImage(tiles.get(selectedTile).getImage(), widthIndex * Level.TILE_SCALE,
                            heightIndex * Level.TILE_SCALE, null);
                tileMap[heightIndex][widthIndex] = selectedTile.numeric();
            }
        }
    }


    private void addTiles(Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                g.drawImage(tiles.get(TileType.fromNumeric(tileMap[i][j])).getImage(), j * Level.TILE_SCALE,
                        i * Level.TILE_SCALE, null);
            }
        }
    }


    private boolean saveLevel(String levelName) {
        String lvlPath = "res\\levels\\";
        if (levelName.isEmpty())
            levelName = "newLevel";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(lvlPath + levelName + ".lvl")))) {
            for (int[] ints : tileMap) {
                for (int anInt : ints) {
                    writer.write(anInt + " ");
                }
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void getInstance() {
        if (instance == null) {
            instance = new LevelEditor();
        }
    }
}
