package com.dokyme.nettyim.client.console;

import com.dokyme.nettyim.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LogoutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogoutRequestPacket packet = new LogoutRequestPacket();
        channel.writeAndFlush(packet);
    }
}
