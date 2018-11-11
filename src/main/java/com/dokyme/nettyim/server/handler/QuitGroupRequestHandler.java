package com.dokyme.nettyim.server.handler;

import com.dokyme.nettyim.protocol.request.QuitGroupRequestPacket;
import com.dokyme.nettyim.protocol.response.QuitGroupBroadcastPacket;
import com.dokyme.nettyim.protocol.response.QuitGroupResponsePacket;
import com.dokyme.nettyim.session.Session;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtil.getChannelGroup(groupId);
        Session session = SessionUtil.getSession(ctx.channel());
        group.remove(ctx.channel());

        //回复，退出群聊成功
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(groupId);
        quitGroupResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(quitGroupResponsePacket);

        //向群聊中用户广播有人退出的消息
        QuitGroupBroadcastPacket quitGroupBroadcastPacket = new QuitGroupBroadcastPacket();
        quitGroupBroadcastPacket.setGroupId(groupId);
        quitGroupBroadcastPacket.setLeaveUserId(session.getUserId());
        quitGroupBroadcastPacket.setLeaveUsername(session.getUsername());
        group.writeAndFlush(quitGroupBroadcastPacket);
    }
}
