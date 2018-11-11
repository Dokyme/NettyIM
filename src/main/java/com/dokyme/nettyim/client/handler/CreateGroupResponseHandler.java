package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        clientLog("已加入群聊：" + msg.getGroupId());
        clientLog("群成员列表：");
        for (int i = 0; i < msg.getUsernameList().size(); i++) {
            clientLog("\t" + i + "." + msg.getUsernameList().get(i));
        }
    }
}
