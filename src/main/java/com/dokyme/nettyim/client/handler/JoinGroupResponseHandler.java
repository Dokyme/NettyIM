package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            clientLog("加入群聊：" + msg.getGroupId() + " 成功");
        } else {
            clientLog("加入群聊：" + msg.getGroupId() + " 失败");
        }
    }
}
