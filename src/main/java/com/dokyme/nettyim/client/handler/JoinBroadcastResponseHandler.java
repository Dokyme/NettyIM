package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.JoinGroupBroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class JoinBroadcastResponseHandler extends SimpleChannelInboundHandler<JoinGroupBroadcastPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupBroadcastPacket msg) throws Exception {
        clientLog(msg.getNewMemberName() + "(" + msg.getNewMemberId() + ")加入群聊：" + msg.getGroupId());
    }
}
