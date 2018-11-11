package com.dokyme.nettyim.protocol.response;

import com.dokyme.nettyim.protocol.Packet;
import com.dokyme.nettyim.protocol.command.Command;
import lombok.Data;

@Data
public class QuitGroupBroadcastPacket extends Packet {

    private String leaveUsername;
    private String leaveUserId;
    private String groupId;

    @Override
    public Byte getCommand() {
        return Command.QUIT_GROUP_BROADCAST;
    }
}
