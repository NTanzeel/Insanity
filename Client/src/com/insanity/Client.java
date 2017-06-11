package com.insanity;

import com.insanity.window.Window;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 03/06/2017.
 */
public class Client extends Window implements Runnable {

    private Applet applet = new Applet();

    private Client(String title) {
        super(title);
        this.getContentPanel().add(this.applet);
    }

    public static void main(String[] args) {
        Client client = new Client("Project Insanity");
        client.run();
    }

    public Applet getApplet() {
        return this.applet;
    }

    @Override
    public void run() {
        this.display();
        this.applet.init();
    }
}
