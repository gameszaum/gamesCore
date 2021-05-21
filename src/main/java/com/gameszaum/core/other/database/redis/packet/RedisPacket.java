package com.gameszaum.core.other.database.redis.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Copyright (C) gameszaum, all rights reserved, unauthorized
 * utlization or copy of this file, is strictly prohibited and
 * liable to civil and criminal penalties, the project 'legit-evento'
 * is privated and the re-sale without contact with me (gameszaum) is not allowed.
 */
public abstract class RedisPacket {

    public RedisPacket() {
    }

    public abstract void read(ByteArrayDataInput in);

    public abstract void write(ByteArrayDataOutput out);

    public abstract void process();

    public String getId() {
        return getClass().getSimpleName();
    }

    public Gson getGson() {
        return new GsonBuilder().create();
    }

}
