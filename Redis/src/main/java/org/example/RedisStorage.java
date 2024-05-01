package org.example;

import org.redisson.Redisson;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.api.RKeys;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

public class RedisStorage {
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> registeredUsers;
    private final static String KEY = "REGISTERED_USERS";

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException ex) {
            System.out.println("failed to connect to Redis");
            System.out.println(ex.getMessage());
        }
        rKeys = redisson.getKeys();
        registeredUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }
}
