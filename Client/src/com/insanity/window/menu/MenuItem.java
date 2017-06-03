package com.insanity.window.menu;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 03/06/2017.
 */
abstract class MenuItem extends JMenuItem implements ActionListener {

    MenuItem(String text) {
        super(text);
        this.addActionListener(this);
    }
}
