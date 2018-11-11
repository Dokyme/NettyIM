package com.dokyme.nettyim.client.handler;

import com.dokyme.nettyim.protocol.response.QuitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket msg) throws Exception {
        if (msg.isSuccess()) {
            clientLog("退出群聊：" + msg.getGroupId() + " 成功");
        } else {
            clientLog("退出群聊：" + msg.getGroupId() + " 失败，原因：" + msg.getReason());
        }
    }
}
