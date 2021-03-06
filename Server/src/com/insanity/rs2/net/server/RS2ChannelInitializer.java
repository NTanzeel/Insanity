package com.insanity.rs2.net.server;

import com.insanity.rs2.net.decoder.LoginDecoder;
import com.insanity.rs2.net.encoder.Encoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class RS2ChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(10));
        channel.pipeline().addLast("encoder", new Encoder());
        channel.pipeline().addLast("loginDecoder", new LoginDecoder());
        channel.pipeline().addLast("channelHandler", new RS2ChannelHandler());
    }
}
