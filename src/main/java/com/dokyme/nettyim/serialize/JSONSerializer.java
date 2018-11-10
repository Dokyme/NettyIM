package com.dokyme.nettyim.serialize;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return com.alibaba.fastjson.JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> cls, byte[] bytes) {
        return com.alibaba.fastjson.JSON.parseObject(bytes, cls);
    }
}
