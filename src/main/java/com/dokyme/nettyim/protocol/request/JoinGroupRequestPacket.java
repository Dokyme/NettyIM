package com.dokyme.nettyim.protocol.request;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_REQUEST;
    }
}
