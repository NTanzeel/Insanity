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

    public Builder writeByte(byte val) {
        buffer.writeByte(val);
        return this;
    }

    public Builder writeByteA(int val) {
        buffer.writeByte(val + 128);
        return this;
    }

    public Builder writeBytes(byte[] bytes) {
        buffer.writeBytes(bytes);
        return this;
    }

    /**
     * Writes a short.
     *
     * @param s The short.
     * @return The PacketBuilder instance, for chaining.
     */
    public Builder writeShort(int s) {
        buffer.writeShort((short) s);
        return this;
    }

    /**
     * Writes a type-A short.
     *
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public Builder writeShortA(int val) {
        return writeByte((byte) (val >> 8)).writeByte((byte) (val + 128));
    }

    /**
     * Writes a little endian type-A short.
     *
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public Builder writeLEShortA(int val) {
        buffer.writeByte((byte) (val + 128));
        buffer.writeByte((byte) (val >> 8));
        return this;
    }

    public Builder writeInt(int val) {
        this.buffer.writeInt(val);
        return this;
    }

    public Builder writeLong(long val) {
        buffer.writeLong(val);
        return this;
    }

    public Builder writeString(String s) {
        return writeBytes(s.getBytes()).writeByte((byte) 10);
    }

    public Packet toPacket() {
        return new Packet(opCode, type, buffer);
    }
}
