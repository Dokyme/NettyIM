package com.dokyme.nettyim.client;

import com.dokyme.nettyim.codec.PacketDecoder;
import com.dokyme.nettyim.codec.PacketEncoder;
import com.dokyme.nettyim.codec.Splitter;
import com.dokyme.nettyim.protocol.request.LoginRequestPacket;
import com.dokyme.nettyim.protocol.request.MessageRequestPacket;
import com.dokyme.nettyim.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.dokyme.nettyim.Log.clientLog;

public class ChatClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Splitter())
                                .addLast(new PacketDecoder())
                                .addLast(new MessageResponseHandler())
                                .addLast(new LoginResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "127.0.0.1", 10000, MAX_RETRY);
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)) {
                    clientLog("请输入用户名登陆：");
                    String username = scanner.nextLine();
                    loginRequestPacket.setUsername(username);
                    loginRequestPacket.setPassword("pwd");
                    channel.writeAndFlush(loginRequestPacket);
                    waitForLoginResponse();
                } else {
                    System.out.print("请输入对方id：");
                    String toUserId = scanner.nextLine();
                    System.out.print("请输入消息内容：");
                    String message = scanner.nextLine();
                    channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }
    }

    /**
     * 指数退避重试连接
     *
     * @param bootstrap
     * @param host
     * @param port
     * @param retry
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    clientLog("连接成功");
                    startConsoleThread(((ChannelFuture) future).channel());
                } else if (retry == 0) {
                    clientLog("连接失败");
                } else {
                    int order = MAX_RETRY - retry + 1;
                    clientLog("剩余尝试连接次数：" + retry);
                    bootstrap.config().group().schedule(new Runnable() {
                        @Override
                        public void run() {
                            connect(bootstrap, host, port, retry - 1);
                        }
                    }, 1 << order, TimeUnit.SECONDS);
                }
            }
        });
    }
}
