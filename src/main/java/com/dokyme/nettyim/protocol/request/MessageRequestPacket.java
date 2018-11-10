package com.dokyme.nettyim.protocol.request;

import com.dokyme.nettyim.protocol.Command;
import com.dokyme.nettyim.protocol.Packet;
import lombok.Data;

@Data
public class MessageRequestPacket extends Packet {

    private String message;
    private String toUserId;

    public MessageRequestPacket(String toUserId,String message) {
        this.message = message;
        this.toUserId=toUserId;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
