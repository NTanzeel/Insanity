package com.insanity.window.menu;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 03/06/2017.
 */
public class MainMenu extends JMenuBar {

    public MainMenu() {
        this.init();
    }

    private void init() {
        this.add(this.getFileMenu());
    }

    private JMenu getFileMenu() {
        JMenu menu = new JMenu("File");

        menu.add(new MenuItem("Project Insanity") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        menu.addSeparator();

        menu.add(new MenuItem("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return menu;
    }
}
