package com.personal.service;

import java.util.List;

public interface RedisOpsService {
    /**
     * redis发布订阅
     */
    void subscribe();

    void transaction();

    void watch();

    void sentinel();

     <T> T lua(String fileClasspath, Class<T> returnType, List<String> keys, Object ... values);
}
