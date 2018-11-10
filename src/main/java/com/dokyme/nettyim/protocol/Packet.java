package com.dokyme.nettyim.protocol;

import lombok.Data;

@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    private byte version = 1;

    public abstract Byte getCommand();
}
