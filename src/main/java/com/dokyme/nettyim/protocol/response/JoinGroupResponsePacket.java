package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_RESPONSE;
    }
}
