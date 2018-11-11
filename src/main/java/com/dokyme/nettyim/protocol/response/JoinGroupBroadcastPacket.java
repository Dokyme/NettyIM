package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class JoinGroupBroadcastPacket extends Packet {

    private String newMemberId;
    private String newMemberName;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.JOIN_GROUP_BROADCAST;
    }
}
