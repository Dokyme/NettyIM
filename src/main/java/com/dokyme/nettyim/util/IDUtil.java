package com.dokyme.nettyim.util;

import java.util.UUID;

public class IDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
