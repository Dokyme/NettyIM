package com.dokyme.nettyim;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import static com.dokyme.nettyim.Log.clientLog;

public class ClientFirstHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        clientLog("Channel active.");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        clientLog("Channel read:" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byte[] bytes = "Hello World".getBytes(CharsetUtil.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
