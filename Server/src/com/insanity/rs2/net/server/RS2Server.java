package com.insanity.rs2.net.server;

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

    private ChannelFuture channelFuture;

    public RS2Server(int port) {
        this.port = port;
    }

    public void bind() throws InterruptedException {
        this.bootstrap = new RS2ServerBootstrap(128, true).init();
        this.channelFuture = bootstrap.bind(this.port).sync();
    }

    public void start() throws InterruptedException {
        try {
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
