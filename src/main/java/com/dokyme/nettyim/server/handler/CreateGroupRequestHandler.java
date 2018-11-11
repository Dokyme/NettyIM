package com.dokyme.nettyim.server.handler;

import com.dokyme.nettyim.protocol.request.CreateGroupRequestPacket;
import com.dokyme.nettyim.protocol.response.CreateGroupResponsePacket;
import com.dokyme.nettyim.util.IDUtil;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

import static com.dokyme.nettyim.Log.serverLog;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        List<String> usernameList = new ArrayList<>();

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                usernameList.add(SessionUtil.getSession(channel).getUsername());
            }
        }

        String groupId = IDUtil.uuid();

        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUsernameList(usernameList);
        channelGroup.writeAndFlush(createGroupResponsePacket);

        serverLog("创建群聊成功，群id：" + groupId);
        serverLog("群成员有：");
        for (int i = 0; i < userIdList.size(); i++) {
            serverLog("\t" + usernameList.get(i) + "(" + userIdList.get(i) + ")");
        }

        //在map中绑定groupId和channelGroup
        SessionUtil.bindChannelGroup(channelGroup, groupId);
    }
}
