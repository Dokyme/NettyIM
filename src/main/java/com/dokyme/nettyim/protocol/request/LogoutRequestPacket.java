package com.dokyme.nettyim.protocol.request;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
