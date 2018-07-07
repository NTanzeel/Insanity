package com.insanity.rs2.net.server;

import com.insanity.Insanity;
import com.insanity.rs2.engine.task.LogoutTask;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class RS2ChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Insanity.getInstance().getEngine().pushTask(new LogoutTask(ctx.channel()));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.channel().close();
    }
}
