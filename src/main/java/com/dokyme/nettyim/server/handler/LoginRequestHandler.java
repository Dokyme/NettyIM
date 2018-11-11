package com.dokyme.nettyim.server.handler;

import com.dokyme.nettyim.protocol.PacketCodeC;
import com.dokyme.nettyim.protocol.request.LoginRequestPacket;
import com.dokyme.nettyim.protocol.response.LoginResponsePacket;
import com.dokyme.nettyim.session.Session;
import com.dokyme.nettyim.util.IDUtil;
import com.dokyme.nettyim.util.LoginUtil;
import com.dokyme.nettyim.util.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.dokyme.nettyim.Log.serverLog;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        serverLog("收到客户端登陆请求，" + loginRequestPacket.getUsername());
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        if (isValid(loginRequestPacket)) {
            serverLog("账号密码校验成功");
            String userId = IDUtil.uuid();
            loginResponsePacket.setUserId(userId);
            SessionUtil.bindSession(new Session(loginRequestPacket.getUsername(), userId), ctx.channel());
            LoginUtil.markAsLogin(ctx.channel());
            loginResponsePacket.setSuccess(true);
        } else {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason("账号密码校验失败");
            serverLog("账号密码校验失败");
        }
        ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
        ctx.channel().writeAndFlush(responseByteBuf);
    }

    private boolean isValid(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket.getUsername().equals(loginRequestPacket.getPassword());
    }
}
