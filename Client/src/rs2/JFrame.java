package rs2;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class JFrame extends client {

    JFrame() {
        super();
        try {
            rs2.sign.signlink.startpriv(InetAddress.getByName(server));
            initUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);

            javax.swing.JFrame frame = new javax.swing.JFrame("Project Insanity");

            frame.setLayout(new BorderLayout());
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JPanel gamePanel = new JPanel();

            gamePanel.setLayout(new BorderLayout());
            gamePanel.add(this);
            gamePanel.setPreferredSize(new Dimension(765, 503));

            frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
            frame.pack();

            frame.setVisible(true);
            frame.setResizable(false);

            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}