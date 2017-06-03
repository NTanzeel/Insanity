package com.insanity.window;

import com.insanity.window.menu.MainMenu;

import javax.swing.*;
import java.awt.*;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 03/06/2017.
 */
public class Window extends JFrame {

    private JPanel contentPanel;

    public Window(String title) {
        super(title);
        this.initialize();
    }

    public JPanel getContentPanel() {
        return this.contentPanel;
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            this.createFrame();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private void createFrame() {
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.getContentPane().add(new MainMenu(), BorderLayout.NORTH);

        this.getContentPane().add(this.createContentPanel(), BorderLayout.CENTER);

        this.pack();

        this.setVisible(true);
        this.setResizable(false);
    }

    private JPanel createContentPanel() {
        this.contentPanel = new JPanel();

        this.contentPanel.setLayout(new BorderLayout());
        this.contentPanel.setPreferredSize(new Dimension(765, 503));

        return this.contentPanel;
    }

    @Override
    public void show() {
    }
}
