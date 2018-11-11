package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket msg) throws Exception {
        clientLog("收到来自群" + msg.getGroupId() + "的消息，发送人：" + msg.getFromUsername() + "(" + msg.getFromUserId() + ")，消息内容：" + msg.getMsg());
    }
}
