package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Command;
import com.dokyme.nettyim.protocol.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;
    private String reason;
    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
