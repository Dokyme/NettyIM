package com.dokyme.nettyim;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import static com.dokyme.nettyim.Log.serverLog;

public class ServerFirstHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        serverLog("Channel read " + byteBuf.toString(Charset.forName("utf-8")));
        serverLog("Server echo data.");
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc().buffer();
        byte[] bytes = "Hello, this is server.".getBytes(CharsetUtil.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
