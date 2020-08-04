# boot-cache （SpringBoot缓存支持）

## 缓存基本概念

### 缓存命中率

缓存命中率 = 缓存中读取次数 / 总读取次数

总读取次数 = 从缓存中读取次数 + 从慢速设备上读取的次数

### 缓存移除策略（Eviction policy）

移除策略，即如果缓存满了，从缓存中移除数据的策略，常见的有LFU、LRU、FIFO

| 名称 | 说明 | 
| ------ | ------ |  
| FIFO（First In First Out）先进先出 | 即先放入缓存的先被移除 |
| LRU（Least Recently Used）最久未使用 | 使用时间距离现在最久的那个被移除 |
| LFU（Least Frequently Used）最近最少使用 | 一定时间段内使用次数（频率）最少的那个被移除 |
| TTL（Time To Live ）存活期 | 即从缓存中创建时间点开始直到它到期的一个时间段（不管在这个时间段内有没有访问都将过期） |
| TTI（Time To Idle）空闲期 | 即一个数据多久没被访问将从缓存中移除的时间 |


### 缓存架构基本概念

| 名称 | 说明 | 
| ------ | ------ | 
| Cache | 缓存接口,定义缓存操作,实现：RedisCache, EhCacheCache, ConcurrentMapCache等 |
| CacheManager | 缓存管理器，管理各种缓存（Cache）组件 |
| @Cacheable | 主要针对方法配置，可对方法请求的参数对结果进行缓存 |
| @CacheEvict | 清空缓存 |
| @CachePut | 保证方法被调用有希望结果被缓存 |
| @EnableCaching | 开启基于注解的缓存 |
| keyGenerator | 缓存数据时，key的生成策略 |
| serialize | 缓存数据时value序列化策略 |


### SpringBoot 各种缓存整合默认配置类：

| 缓存配置项 | 缓存类型 | 说明 |
| ------ | ------ | ------ |
| GenericCacheConfiguration | Collection | 默认使用SimpleCacheManager
| JCacheCacheConfiguration | JCache | |
| EhCacheCacheConfiguration | EhCache | |
| HazelcastCacheConfiguration | Hazelcast | |
| InfinispanCacheConfiguration | Infinispan | |
| CouchbaseCacheConfiguration | Couchbase | |
| RedisCacheConfiguration | Redis | |
| CaffeineCacheConfiguration | Caffeine | |
| SimpleCacheConfiguration | ConcurrentMap | |
| NoOpCacheConfiguration | 无缓存配置 | |

#### SpringBoot整合Redis缓存

`com.wowjoy.boot.cache.config.EmpRedisConfig`

默认保存数据`[k-v]`都是Object类型，RedisCacheManager操作redis时使用RedisTemplate<Object, Object>默认使用JDK的序列化方式，可自定义配置序列化方式

## 分布式锁

### 常见实现方式

- 数据库乐观锁
- 基于Redis的分布式锁
- 基于ZooKeeper的分布式锁

### 分布式锁可用的几个条件

- 互斥性：任何时刻只有一个客户端可以持有锁
- 不会发生死锁：即使有一个客户端在持有锁的期间奔溃儿没有主动解锁，也可以保证后续其他客户端可以加锁
- 容错性：只要大部分redis节点可以正常运行，客户端可以加锁解锁
- 解铃还须系铃人：加锁和解锁必须是同一客户端，客户端不可以解锁其他客户端的锁

### 基于Redis分布式锁实现

`com.wowjoy.boot.cache.utils.RedisLockUtil`

### 基于数据库分布式锁实现

`com.wowjoy.boot.cache.utils.DBLockUtil`

## 拦截器/过滤器/AOP

`com.wowjoy.boot.cache.interceptor.TimeInterceptor`

`com.wowjoy.boot.cache.filter.TimeFilter`

`com.wowjoy.boot.cache.aop.TimeAspect`

mongo启动命令配置项

``
./bin/mongod --bind_ip_all --logpath /data/log/mongod.log --fork --dbpath /data/db
``

---

redis分布式锁实现：Redisson（Redlock算法）
以有效方式使用分布式锁所需的最低保证：

互斥性：任何给定时刻，只有一个客户端可以持有锁

无死锁：使锁定资源的客户端崩溃或被分区，也始终可以获取锁定

容错：只要大多数Redis节点启动，客户端就能够获取和释放锁

基于故障转移的实现还不够的原因：

大多数基于Redis的分布式锁库的当前状态：

使用Redis锁定资源的最简单方法是在实例中创建key。key通常使用Redis expires参数指定key的有效时间，key最终会被删除释放。

上述方式存在单点故障，可以添加一个从节点解决单点问题。但是样是不可行的。因为无法实现互斥的安全属性，因为Redis复制是异步的。

这个模型有明显的竞争条件：

客户端A获取主节点中的锁，在写入key之前，主节点挂掉并发送到从节点，从节点被提升为主节点，客户端B获取对已经拥有锁的相同资源的锁，安全违规

SET resource_name my_random_value NX PX 30000

该命令仅在key尚不存在时才设置key（NX选项），过期时间为30000毫秒（PX选项）。key设置为“我的随机值”值。此值必须在所有客户端和所有锁定请求中都是唯一的

基本上使用随机值是为了以安全的方式释放锁，使用一个告诉Redis的脚本：仅当密钥存在且存储在密钥上的值恰好是我期望的值时才删除密钥。这是通过以下Lua脚本完成的

```lua
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```

这样可以避免删除由另一个客户端创建的锁，例如：客户端可能获得锁，在某些操作中被阻止的时间超过锁有效时间（密钥将到期的时间），并且稍后移除已经由某个其他客户端获取的锁

仅使用DEL是不安全的，因为客户端可能会删除另一个客户端的锁，使用上面的脚本而不是每个锁都使用随机字符串“签名”，因此只有在客户端尝试删除锁时，锁才会被删除。
