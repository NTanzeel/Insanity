package com.insanity.rs2.net.encoder;

import com.insanity.rs2.net.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class Encoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf out) throws Exception {
        if (packet.isRaw()) {
            out.writeBytes(packet.getPayload());
        } else {
            out.writeByte(packet.getOpcode());

            switch (packet.getType()) {
                case VARIABLE:
                    out.writeByte(packet.getLength());
                    break;

                case VARIABLE_SHORT:
                    out.writeShort(packet.getLength());
                    break;
            }

            out.writeBytes(packet.getPayload());
        }
    }
}
