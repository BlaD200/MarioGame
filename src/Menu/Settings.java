package Menu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends JFrame {
    private JPanel root;
    private JLabel difficultyLabel;
    private JLabel soundLabel;
    private JComboBox difficultyComboBox;
    private JSpinner soundSpinner;
    private JLabel backLabel;
    private static Settings instance;
    //    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private Settings() {
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
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
        }
    }
}
