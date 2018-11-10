package com.dokyme.nettyim.protocol;

import com.dokyme.nettyim.protocol.request.LoginRequestPacket;
import com.dokyme.nettyim.protocol.request.MessageRequestPacket;
import com.dokyme.nettyim.protocol.response.LoginResponsePacket;
import com.dokyme.nettyim.protocol.response.MessageResponsePacket;
import com.dokyme.nettyim.serialize.JSONSerializer;
import com.dokyme.nettyim.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.dokyme.nettyim.protocol.Command.*;

public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    public static final int MAGIC = 0x22223333;

    private final Map<Byte, Class<? extends Packet>> packetTypeMap = new HashMap<>();
    private final Map<Byte, Serializer> serializerMap = new HashMap<>();

    private PacketCodeC() {
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap.put(Serializer.JSON, new JSONSerializer());
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public ByteBuf encode(ByteBufAllocator alloc, Packet packet) {
        ByteBuf byteBuf = alloc.ioBuffer();
        encode(byteBuf, packet);

        return byteBuf;
    }

    public ByteBuf encode(Packet packet) {
        return encode(ByteBufAllocator.DEFAULT, packet);
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serializeAlgorithm = byteBuf.readByte();
        byte cmd = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = getRequestType(cmd);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(byte cmd) {
        return packetTypeMap.get(cmd);
    }

    private Serializer getSerializer(byte type) {
        return serializerMap.get(type);
    }
}
