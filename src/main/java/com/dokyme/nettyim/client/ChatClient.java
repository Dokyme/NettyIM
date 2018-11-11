package com.dokyme.nettyim.client;

import com.dokyme.nettyim.client.console.ConsoleCommandManager;
import com.dokyme.nettyim.client.console.LoginConsoleCommand;
import com.dokyme.nettyim.client.handler.*;
import com.dokyme.nettyim.codec.PacketDecoder;
import com.dokyme.nettyim.codec.PacketEncoder;
import com.dokyme.nettyim.codec.Splitter;
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
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new LogoutResponseHandler())
                                .addLast(new JoinGroupResponseHandler())
                                .addLast(new QuitGroupResponseHandler())
                                .addLast(new JoinBroadcastResponseHandler())
                                .addLast(new GroupMessageResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "127.0.0.1", 10001, MAX_RETRY);
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!LoginUtil.hasLogin(channel)) {
                    new LoginConsoleCommand().exec(scanner, channel);
                    waitForLoginResponse();
                } else {
                    consoleCommandManager.exec(scanner, channel);
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
