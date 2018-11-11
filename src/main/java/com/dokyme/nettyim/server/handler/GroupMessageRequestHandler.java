package com.dokyme.nettyim.server.handler;

import com.dokyme.nettyim.protocol.request.GroupMessageRequestPacket;
import com.dokyme.nettyim.protocol.response.GroupMessageResponsePacket;
import com.dokyme.nettyim.session.Session;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import static com.dokyme.nettyim.Log.serverLog;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        serverLog("用户" + session.getUserId() + "向群" + msg.getGroupId() + "发送消息：" + msg.getMsg());

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getGroupId());
        if (channelGroup != null) {
            GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
            groupMessageResponsePacket.setFromUserId(session.getUserId());
            groupMessageResponsePacket.setFromUsername(session.getUsername());
            groupMessageResponsePacket.setGroupId(msg.getGroupId());
            groupMessageResponsePacket.setMsg(msg.getMsg());
            channelGroup.writeAndFlush(groupMessageResponsePacket);
        }
    }
}
