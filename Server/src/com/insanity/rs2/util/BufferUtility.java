package com.insanity.rs2.util;

import io.netty.buffer.ByteBuf;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class BufferUtility {

    /**
     * Reads a RuneScape string from a buffer.
     *
     * @param buf The buffer.
     * @return The string.
     */
    public static String getRS2String(ByteBuf buf) {
        StringBuilder bldr = new StringBuilder();
        byte b;
        while (buf.isReadable() && (b = buf.readByte()) != 10) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }

    /**
     * Writes a RuneScape string to a buffer.
     *
     * @param buf    The buffer.
     * @param string The string.
     */
    public static void putRS2String(ByteBuf buf, String string) {
        for (char c : string.toCharArray()) {
            buf.writeByte((byte) c);
        }
        buf.writeByte((byte) 10);
    }
}
