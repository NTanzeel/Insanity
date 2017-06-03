package com.insanity;

import com.insanity.window.Window;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 03/06/2017.
 */
public class Client extends Window {

    private Client(String title) {
        super(title);
    }

    public static void main(String[] args) {
        Client client = new Client("Project Insanity");
    }
}
