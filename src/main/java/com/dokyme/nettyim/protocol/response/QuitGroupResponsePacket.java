package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class QuitGroupResponsePacket extends Packet {
    private boolean success;
    private String groupId;
    private String reason;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_RESPONSE;
    }
}
