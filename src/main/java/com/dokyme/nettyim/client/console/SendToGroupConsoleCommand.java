package com.dokyme.nettyim.client.console;

import com.dokyme.nettyim.protocol.request.GroupMessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static com.dokyme.nettyim.Log.clientLog;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        clientLog("发送消息的群id：");
        String groupId = scanner.next();
        clientLog("发送群消息：");
        String msg = scanner.next();
        GroupMessageRequestPacket groupMessageRequestPacket = new GroupMessageRequestPacket();
        groupMessageRequestPacket.setGroupId(groupId);
        groupMessageRequestPacket.setMsg(msg);
        channel.writeAndFlush(groupMessageRequestPacket);
    }
}
