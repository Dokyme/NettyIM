package com.dokyme.nettyim.client.console;

import com.dokyme.nettyim.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static com.dokyme.nettyim.Log.clientLog;

public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner scanner, Channel channel) {
        clientLog("发送消息给用户：");
        String userId = scanner.next();
        clientLog("发送消息：");
        String msg = scanner.nextLine();
        channel.writeAndFlush(new MessageRequestPacket(userId, msg));
    }
}
