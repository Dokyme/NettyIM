package com.dokyme.nettyim.server.handler;

import com.dokyme.nettyim.protocol.request.JoinGroupRequestPacket;
import com.dokyme.nettyim.protocol.response.JoinGroupBroadcastPacket;
import com.dokyme.nettyim.protocol.response.JoinGroupResponsePacket;
import com.dokyme.nettyim.session.Session;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import static com.dokyme.nettyim.Log.serverLog;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        Session session = SessionUtil.getSession(ctx.channel());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            channelGroup.add(ctx.channel());
            joinGroupResponsePacket.setSuccess(true);

            serverLog(session.getUsername() + "(" + session.getUserId() + ")加入群聊：" + groupId);

            //新用户加入群聊时向所有群成员广播之
            JoinGroupBroadcastPacket joinGroupBroadcastPacket = new JoinGroupBroadcastPacket();
            joinGroupBroadcastPacket.setNewMemberId(session.getUserId());
            joinGroupBroadcastPacket.setNewMemberName(session.getUsername());
            joinGroupBroadcastPacket.setGroupId(groupId);
            channelGroup.writeAndFlush(joinGroupBroadcastPacket);
        } else {
            joinGroupResponsePacket.setSuccess(false);
        }

        joinGroupResponsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
