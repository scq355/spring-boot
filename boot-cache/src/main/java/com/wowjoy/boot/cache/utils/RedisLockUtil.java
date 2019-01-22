package com.wowjoy.boot.cache.utils;

import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * 基于redis的客户端jedis分布式锁实现，以及一些常见的典型问题案例
 *
 * 只考虑Redis单机部署的场景，所以容错性暂不考虑
 * 果项目中Redis是多机部署的，可以尝试使用Redisson实现分布式锁
 */
public class RedisLockUtil {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    /***************************** 加锁 **********************************/

    /**
     * 获取分布式锁
     *
     * @param jedis      redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超时时间
     * @return 是否获取成功
     */
    public static boolean getDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        /**
         * 只考虑Redis单机部署的场景
         * 使用key来当锁，因为key是唯一的
         * 通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成
         * SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作，也就是只有一个客户端能持有锁，满足互斥性
         * 给这个key加一个过期的设置，具体时间由第五个参数决定，即使锁的持有者后续发生崩溃而没有解锁，锁也会因为到了过期时间而自动解锁（即key被删除），不会发生死锁
         * key的过期时间
         *
         * 1. 当前没有锁（key不存在），那么就进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端
         * 2. 已有锁存在，不做任何操作
         */
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    /**
     * jedis.setnx()和jedis.expire()组合实现加锁
     * @param jedis
     * @param lockKey
     * @param requestId
     * @param expireTime
     */
    @Deprecated
    public static void getLockI(Jedis jedis, String lockKey, String requestId, int expireTime) {
        /**
         * setnx()方法作用就是SET IF NOT EXIST
         * expire()方法就是给锁加一个过期时间
         * 然而由于这是两条Redis命令，不具有原子性，如果程序在执行完setnx()之后突然崩溃，导致锁没有设置过期时间。那么将会发生死锁
         * 原因：低版本的jedis并不支持多参数的set()方法
         */
        Long result = jedis.setnx(lockKey, requestId);
        if (result == 1) {
            jedis.expire(lockKey, expireTime);
        }
    }


    /**
     * @param jedis
     * @param lockKey
     * @param expireTime
     * @return
     *
     * 1. 通过setnx()方法尝试加锁，如果当前锁不存在，返回加锁成功。
     * 2. 如果锁已经存在则获取锁的过期时间，和当前时间比较，如果锁已经过期，则设置新的过期时间，返回加锁成功。
     *
     * 1. 由于是客户端自己生成过期时间，所以需要强制要求分布式下每个客户端的时间必须同步
     * 2. 当锁过期的时候，如果多个客户端同时执行jedis.getSet()方法，那么虽然最终只有一个客户端可以加锁，但是这个客户端的锁的过期时间可能被其他客户端覆盖。
     * 3. 锁不具备拥有者标识，即任何客户端都可以解锁。
     */
    @Deprecated
    public static boolean getLockII(Jedis jedis, String lockKey, int expireTime) {
        Long expires = System.currentTimeMillis() + expireTime;
        String expireStr = String.valueOf(expires);
        // 如果当前锁不存在,返回加锁成功
        if (jedis.setnx(lockKey, expireStr) == 1) {
            return true;
        }
        // 如果锁存在，获取锁的过期时间
        String currentValueStr = jedis.get(lockKey);
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            // 锁已过期，获取上锁时间，并且设置现在锁的过期时间
            String oldValueStr = jedis.getSet(lockKey, expireStr);
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                // 考虑多线程并发的情况，只有一个线程的设置值和当前值相同，它才有权利加锁
                return true;
            }
        }
        // 其他情况，一律返回加锁失败
        return false;
    }

    /***************************** 解锁 **********************************/

    /**
     * 简单来说，就是在eval命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到eval命令执行完成，Redis才会执行其他命令。
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        /**
         * 为什么要使用Lua语言实现：因为要确保操作原子性
         *
         * 首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁(解锁)
         */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        /**
         * Lua代码传到jedis.eval()方法里，并使参数KEYS[1]赋值为lockKey，ARGV[1]赋值为requestId。eval()方法是将Lua代码交给Redis服务端执行
         */
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        return RELEASE_SUCCESS.equals(result);
    }

    /**
     * 这种不先判断锁的拥有者而直接解锁的方式，会导致任何客户端都可以随时进行解锁，即使这把锁不是它的
     *
     * @param jedis
     * @param lockKey
     */
    public static void releaseLockI(Jedis jedis, String lockKey) {
        jedis.del(lockKey);
    }

    /**
     * 如果调用jedis.del()方法的时候，这把锁已经不属于当前客户端的时候会解除他人加的锁
     * 场景：客户端A加锁，一段时间之后客户端A解锁，在执行jedis.del()之前，锁突然过期了，
     * 此时客户端B尝试加锁成功，然后客户端A再执行del()方法，则将客户端B的锁给解除了
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     */
    public static void releaseLockI(Jedis jedis, String lockKey, String requestId) {
        if (requestId.equals(jedis.get(lockKey))) {
            jedis.del(lockKey);
        }
    }

}
