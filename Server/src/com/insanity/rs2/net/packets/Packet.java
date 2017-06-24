package com.insanity.rs2.net.packets;

import io.netty.buffer.ByteBuf;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Packet {

    private int opcode;
    private Type type;
    private ByteBuf payload;

    public Packet(int opcode, Type type, ByteBuf payload) {
        this.opcode = opcode;
        this.type = type;
        this.payload = payload;
    }

    public boolean isRaw() {
        return opcode == -1;
    }

    public int getOpcode() {
        return opcode;
    }

    public Type getType() {
        return type;
    }

    public ByteBuf getPayload() {
        return payload;
    }

    public int getLength() {
        return payload.readableBytes();
    }
}
