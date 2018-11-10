package com.dokyme.nettyim;

import java.util.Date;

public class Log {
    public static void clientLog(String msg) {
        System.out.println("[Client]" + new Date() + ":" + msg);
    }

    public static void serverLog(String msg) {
        System.out.println("[Server]" + new Date() + ":" + msg);
    }
}
