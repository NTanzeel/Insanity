package com.insanity.rs2.net.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Builder {

    private final int opCode;

    private final Type type;

    private ByteBuf buffer = Unpooled.buffer();

    public Builder() {
        this(-1);
    }

    public Builder(int opCode) {
        this(opCode, Type.FIXED);
    }

    public Builder(int opCode, Type type) {
        this.opCode = opCode;
        this.type = type;
    }

    public Builder writeByte(byte b) {
        this.buffer.writeByte(b);
        return this;
    }

    public Builder writeBytes(byte[] bytes) {
        this.buffer.writeBytes(bytes);
        return this;
    }

    public Builder writeInt(int i) {
        this.buffer.writeInt(i);
        return this;
    }

    public Builder writeLong(long l) {
        this.buffer.writeLong(l);
        return this;
    }

    public Builder writeString(String s) {
        return this.writeBytes(s.getBytes());
    }

    public Packet toPacket() {
        return new Packet(opCode, type, buffer);
    }
}
