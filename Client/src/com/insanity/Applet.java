package com.insanity;

import java.awt.*;
import java.awt.event.*;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 11/06/2017.
 */
public class Applet extends java.applet.Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener, FocusListener, WindowListener {

    private int width = 765;
    private int height = 503;

    private boolean shouldClearScreen = true;

    public void init() {
        Thread thread = new Thread(this);
        thread.start();
        thread.setPriority(1);
    }

    @Override
    public void run() {
        System.out.println("Loading...");
        try {
            this.drawLoadingScreen("Loading...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawLoadingScreen(String text) {
        Font font = new Font("Helvetica", 1, 13);
        FontMetrics fontmetrics = this.getFontMetrics(font);
        Font font1 = new Font("Helvetica", 0, 13);
        this.getFontMetrics(font1);
        if (shouldClearScreen) {
            this.getGraphics().setColor(Color.black);
            this.getGraphics().fillRect(0, 0, this.width, this.height);
            shouldClearScreen = false;
        }
        Color color = new Color(140, 17, 17);
        int j = this.height / 2 - 18;
        this.getGraphics().setColor(color);
        this.getGraphics().drawRect(this.width / 2 - 152, j, 304, 34);
        this.getGraphics().fillRect(this.width / 2 - 150, j + 2, 0, 30);
        this.getGraphics().setColor(Color.black);
        this.getGraphics().fillRect((this.width / 2 - 150) * 3, j + 2, 300 * 3, 30);
        this.getGraphics().setFont(font);
        this.getGraphics().setColor(Color.white);
        this.getGraphics().drawString(text, (this.width - fontmetrics.stringWidth(text)) / 2, j + 22);
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
