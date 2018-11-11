package com.dokyme.nettyim.protocol.request;

import com.dokyme.nettyim.protocol.command.Command;
import com.dokyme.nettyim.protocol.Packet;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet {

    private String username;
    private String password;
    private String userId;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
