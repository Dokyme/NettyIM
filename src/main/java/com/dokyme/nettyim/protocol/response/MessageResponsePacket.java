package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Command;
import com.dokyme.nettyim.protocol.Packet;
import lombok.Data;

@Data
public class MessageResponsePacket extends Packet {

    private String message;
    private String fromUserId;
    private String fromUsername;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
