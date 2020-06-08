package com.personal.service;

public interface RedisOpsService {
    /**
     * redis发布订阅
     */
    void subscribe();

    void transaction();

    void watch();

    void sentinel();
}
