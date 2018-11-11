package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.QuitGroupBroadcastPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class QuitGroupBroadcastHandler extends SimpleChannelInboundHandler<QuitGroupBroadcastPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupBroadcastPacket msg) throws Exception {
        clientLog("用户" + msg.getLeaveUsername() + "(" + msg.getLeaveUserId() + ")退出群聊" + msg.getGroupId());
    }
}
