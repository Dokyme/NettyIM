package com.dokyme.nettyim.client.console;

import com.dokyme.nettyim.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

import static com.dokyme.nettyim.Log.clientLog;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITTER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket requestPacket = new CreateGroupRequestPacket();
        clientLog("输入用户id列表，用" + USER_ID_SPLITTER + "分隔：");
        String userIds = scanner.next();
        requestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITTER)));
        channel.writeAndFlush(requestPacket);
    }
}
