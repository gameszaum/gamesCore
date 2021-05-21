package com.gameszaum.core.other.database.redis;

import com.gameszaum.core.other.database.redis.packet.RedisPacket;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

public interface Redis {

    void connect(RedisPubSubListener<String, String> listener, String... channels);

    StatefulRedisPubSubConnection<String, String> getMainConnection();

    RedisClient getClient();

    String writePacket(RedisPacket packet);

    void shutdown();

    void sendPacket(RedisPacket packet, String channel);

}
