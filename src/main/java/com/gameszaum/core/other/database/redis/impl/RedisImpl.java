package com.gameszaum.core.other.database.redis.impl;

import com.gameszaum.core.other.database.DatabaseCredentials;
import com.gameszaum.core.other.database.redis.Redis;
import com.gameszaum.core.other.database.redis.packet.RedisPacket;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;

import java.util.Base64;

public class RedisImpl implements Redis {

    private final String prefix;
    private final RedisClient client;
    private StatefulRedisPubSubConnection<String, String> mainConnection;

    public RedisImpl(String prefix, DatabaseCredentials credentials) {
        this.prefix = prefix;
        this.client = RedisClient.create("redis://" + credentials.getPass() + "@" + credentials.getHost() + ":" + credentials.getPort() + "/0");
    }

    @Override
    public void connect(RedisPubSubListener<String, String> listener, String... channels) {
        mainConnection = client.connectPubSub();
        mainConnection.addListener(listener);
        mainConnection.async().subscribe(channels);

        System.out.println("[" + prefix + "] Redis connected.");
    }

    @Override
    public void shutdown() {
        mainConnection.closeAsync();
        client.shutdown();

        System.out.println("[" + prefix + "] Redis closed.");
    }

    @Override
    public void sendPacket(RedisPacket packet, String channel) {
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
        RedisPubSubAsyncCommands<String, String> async = connection.async();

        async.publish(channel, writePacket(packet));
        connection.closeAsync();
    }

    @Override
    public StatefulRedisPubSubConnection<String, String> getMainConnection() {
        return mainConnection;
    }

    @Override
    public RedisClient getClient() {
        return client;
    }

    @Override
    public String writePacket(RedisPacket packet) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
        byteArrayDataOutput.writeUTF(packet.getId());

        packet.write(byteArrayDataOutput);
        return Base64.getEncoder().encodeToString(byteArrayDataOutput.toByteArray());
    }
}
