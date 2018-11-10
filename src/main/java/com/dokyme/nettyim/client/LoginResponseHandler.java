package com.dokyme.nettyim.client;

import com.dokyme.nettyim.protocol.response.LoginResponsePacket;
import com.dokyme.nettyim.util.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.clientLog;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            clientLog("客户端登陆成功，id：" + loginResponsePacket.getUserId());
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            clientLog("客户端登陆失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
