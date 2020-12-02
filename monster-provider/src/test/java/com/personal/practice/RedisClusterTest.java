package com.personal.practice;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisNoReachableClusterNodeException;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;

@SpringBootTest
public class RedisClusterTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisClusterConnecttionTest(){
        for (int i=0;i<100;i++){
            String keys="lcc"+i;
            redisTemplate.opsForValue().set(keys,i);
//            redisTemplate.delete(keys);
            System.out.println("success");
        }

    }

    @Test
    void redisMgetTest(){
        //测试redis-cluster Mget
        List<String> list=new ArrayList();
        list.add("lcc");
        list.add("ccl");
        list.add("clc");
        List<Object> res=redisTemplate.opsForValue().multiGet(list);
        for (Object o:res){
            System.out.println(o);
        }
    }

    @Test
    void redisPiplineTest(){
        //测试redis-cluster pipline
        List<Object> res=redisTemplate.executePipelined(new SessionCallback<Integer>() {
            @Override
            public <K, V> Integer execute(RedisOperations<K, V> operations) throws DataAccessException {
                operations.opsForValue().get("lcc");
                operations.opsForValue().get("clc");
                operations.opsForValue().get("ccl");
                return 0;
            }
        });
        for (Object ob:res){
            System.out.println(ob);
        }
    }

    @Test
    void pipelineTestUsingJedis() {
        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        HostAndPort hostAndPort1 = new HostAndPort("hostA", 7000);
        HostAndPort hostAndPort2 = new HostAndPort("hostB", 7001);
        HostAndPort hostAndPort3 = new HostAndPort("hostC", 7002);
        jedisClusterNode.add(hostAndPort1);
        jedisClusterNode.add(hostAndPort2);
        jedisClusterNode.add(hostAndPort3);

        JedisClusterPlus jedisClusterPlus = new JedisClusterPlus(jedisClusterNode, 2000, 2000, new JedisPoolConfig());
        JedisSlotAdvancedConnectionHandler jedisSlotAdvancedConnectionHandler = jedisClusterPlus.getConnectionHandler();

        String[] testKeys = {"foo", "bar", "xyz"};

        Map<JedisPool, List<String>> poolKeys = new HashMap<>();

        for (String key : testKeys) {
            int slot = JedisClusterCRC16.getSlot(key);
            JedisPool jedisPool = jedisSlotAdvancedConnectionHandler.getJedisPoolFromSlot(slot);
            if (poolKeys.keySet().contains(jedisPool)) {
                List<String> keys = poolKeys.get(jedisPool);
                keys.add(key);
            } else {
                List<String> keys = new ArrayList<>();
                keys.add(key);
                poolKeys.put(jedisPool, keys);
            }
        }

        for (JedisPool jedisPool : poolKeys.keySet()) {
            Jedis jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            List<String> keys = poolKeys.get(jedisPool);

            keys.forEach(key -> pipeline.get(key));

            List result = pipeline.syncAndReturnAll();

            System.out.println(result);

            jedis.close();
        }
    }

    @Test
void redisClusterTractionTest() {
    Object res=redisTemplate.execute(new SessionCallback<Object>() {
        @Override
        public Object execute(RedisOperations operations) throws DataAccessException {
            operations.multi();
            operations.opsForValue().get("lcc");
            operations.opsForValue().get("clc");
            operations.opsForValue().get("ccl");
            operations.exec();
            return null;
        }
    });
    System.out.println(res);

}
}

    class JedisClusterPlus extends JedisCluster {
        public JedisClusterPlus(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout, final GenericObjectPoolConfig poolConfig) {
            super(jedisClusterNode);
            super.connectionHandler = new JedisSlotAdvancedConnectionHandler(jedisClusterNode, poolConfig,
                    connectionTimeout, soTimeout);
        }

        public JedisSlotAdvancedConnectionHandler getConnectionHandler() {
            return (JedisSlotAdvancedConnectionHandler)this.connectionHandler;
        }
    }
    class JedisSlotAdvancedConnectionHandler extends JedisSlotBasedConnectionHandler {

        public JedisSlotAdvancedConnectionHandler(Set<HostAndPort> nodes, GenericObjectPoolConfig poolConfig, int connectionTimeout, int soTimeout) {
            super(nodes, poolConfig, connectionTimeout, soTimeout);
        }

        public JedisPool getJedisPoolFromSlot(int slot) {
            JedisPool connectionPool = cache.getSlotPool(slot);
            if (connectionPool != null) {
                // It can't guaranteed to get valid connection because of node
                // assignment
                return connectionPool;
            } else {
                renewSlotCache(); //It's abnormal situation for cluster mode, that we have just nothing for slot, try to rediscover state
                connectionPool = cache.getSlotPool(slot);
                if (connectionPool != null) {
                    return connectionPool;
                } else {
                    throw new JedisNoReachableClusterNodeException("No reachable node in cluster for slot " + slot);
                }
            }
        }
    }
