package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String groupId;
    private String fromUserId;
    private String fromUsername;
    private String msg;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_RESPONSE;
    }
}
