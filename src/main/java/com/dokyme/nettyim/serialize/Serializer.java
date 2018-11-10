package com.dokyme.nettyim.serialize;

public interface Serializer {

    byte JSON = 1;

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> cls, byte[] bytes);
}
