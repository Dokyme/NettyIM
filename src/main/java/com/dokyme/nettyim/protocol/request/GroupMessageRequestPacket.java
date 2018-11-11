package com.dokyme.nettyim.protocol.request;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class GroupMessageRequestPacket extends Packet {
    private String groupId;
    private String msg;

    @Override
    public Byte getCommand() {
        return Command.GROUP_MESSAGE_REQUEST;
    }
}
