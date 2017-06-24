package com.insanity.net.server;

import io.netty.channel.ChannelFuture;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class RS2Server {

    public static final int DEFAULT_PORT = 43594;

    private final int port;

    private RS2ServerBootstrap bootstrap;

    public RS2Server(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        try {
            this.bootstrap = new RS2ServerBootstrap(128, true).init();
            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            this.bootstrap.shutdownGracefully();
        }
    }

    public void shutdown() {
        this.bootstrap.shutdownGracefully();
    }

    public void reboot() throws InterruptedException {
        this.shutdown();
        this.start();
    }
}
