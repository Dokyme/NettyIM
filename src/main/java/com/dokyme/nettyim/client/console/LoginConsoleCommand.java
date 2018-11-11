package com.dokyme.nettyim.client.console;

import com.dokyme.nettyim.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

import static com.dokyme.nettyim.Log.clientLog;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        clientLog("登陆用户名：");
        String username = scanner.nextLine();
        clientLog("登陆密码：");
        String pwd = scanner.nextLine();
        LoginRequestPacket packet = new LoginRequestPacket();
        packet.setUsername(username);
        packet.setPassword(pwd);
        channel.writeAndFlush(packet);
    }
}
