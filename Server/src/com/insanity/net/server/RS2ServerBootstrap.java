package com.insanity.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class RS2ServerBootstrap extends ServerBootstrap {

    private int backlog;
    private boolean keepAlive;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public RS2ServerBootstrap(int backlog, boolean keepAlive) {
        this.backlog = backlog;
        this.keepAlive = keepAlive;
    }

    public RS2ServerBootstrap init() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        this.group(bossGroup, workerGroup);
        this.channel(NioServerSocketChannel.class);
        this.childHandler(new RS2ChannelInitializer());

        this.option(ChannelOption.SO_BACKLOG, this.backlog);
        this.childOption(ChannelOption.SO_KEEPALIVE, this.keepAlive);

        return this;
    }

    public void shutdownGracefully() {
        this.workerGroup.shutdownGracefully();
        this.bossGroup.shutdownGracefully();
    }
}
