package com.dokyme.nettyim.server;

import com.dokyme.nettyim.protocol.request.MessageRequestPacket;
import com.dokyme.nettyim.protocol.response.MessageResponsePacket;
import com.dokyme.nettyim.session.Session;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.serverLog;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        serverLog("收到客户端消息：" + messageRequestPacket.getMessage());

        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket toUserResponsePacket = new MessageResponsePacket();
        toUserResponsePacket.setFromUserId(session.getUserId());
        toUserResponsePacket.setFromUsername(session.getUsername());
        toUserResponsePacket.setMessage(messageRequestPacket.getMessage());

        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(toUserResponsePacket);
        } else {
            serverLog(messageRequestPacket.getToUserId() + " 不在线，发送消息失败");
        }
    }
}
