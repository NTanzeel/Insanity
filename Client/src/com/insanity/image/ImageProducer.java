package com.insanity.image;

import rs2.DrawingArea;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageObserver;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 11/06/2017.
 */

public final class ImageProducer implements java.awt.image.ImageProducer, ImageObserver {

    private final int[] anIntArray315;
    private final int anInt316;
    private final int anInt317;
    private final ColorModel aColorModel318;
    private final Image anImage320;
    private ImageConsumer anImageConsumer319;

    public ImageProducer(int i, int j, Component component) {
        anInt316 = i;
        anInt317 = j;
        anIntArray315 = new int[i * j];
        aColorModel318 = new DirectColorModel(32, 0xff0000, 65280, 255);
        anImage320 = component.createImage(this);
        method239();
        component.prepareImage(anImage320, this);
        method239();
        component.prepareImage(anImage320, this);
        method239();
        component.prepareImage(anImage320, this);
        initDrawingArea();
    }

    public void initDrawingArea() {
        DrawingArea.initDrawingArea(anInt317, anInt316, anIntArray315);
    }

    public void drawGraphics(int i, Graphics g, int k) {
        method239();
        g.drawImage(anImage320, k, i, this);
    }

    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        anImageConsumer319 = imageconsumer;
        imageconsumer.setDimensions(anInt316, anInt317);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(aColorModel318);
        imageconsumer.setHints(14);
    }

    public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
        return anImageConsumer319 == imageconsumer;
    }

    public synchronized void removeConsumer(ImageConsumer imageconsumer) {
        if (anImageConsumer319 == imageconsumer) anImageConsumer319 = null;
    }

    public void startProduction(ImageConsumer imageconsumer) {
        addConsumer(imageconsumer);
    }

    public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
        System.out.println("TDLR");
    }

    private synchronized void method239() {
        if (anImageConsumer319 != null) {
            anImageConsumer319.setPixels(0, 0, anInt316, anInt317, aColorModel318, anIntArray315, 0, anInt316);
            anImageConsumer319.imageComplete(2);
        }
    }

    public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
        return true;
    }
}
