package com.wowjoy.boot.cache.utils;

import com.wowjoy.boot.cache.mapper.MethodlockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 简单实现有几个问题：
 * <p>
 * 　　1、这把锁依赖数据库的可用性，如果数据库是一个单点，一旦挂掉，会导致业务系统不可用；
 * 　　2、这把锁没有失效时间，一旦解锁操作失败，会导致锁一直存留在数据库中，其它线程无法获得锁；
 * 　　3、这把锁只能是非阻塞的，因为数据的insert操作一旦插入失败就直接报错，没有获得锁的线程不会进入排队队列，想要再次获得锁就要再次触发获得锁的操作；
 * 　　4、这把锁是非重入的，同一线程在没有释放锁之前无法再次获得该锁，因为表中数据已经存在了。
 * <p>
 * 当然上面的问题也是可以解决的：
 * <p>
 * 　　1、单点问题，两个数据库，双向同步，一旦挂掉切换到另一个上；
 * 　　2、失效时间，做一个定时任务，每隔多长时间清理超时数据；
 * 　　3、非阻塞问题，程序写for循环多次尝试，直至获取到锁为止；
 * 　　4、非重入，增加一个字段，记录获取所的ip跟线程信息，下次查询的时候如果有，则直接给锁；
 */
@Component
public class DBLockUtil {
    @Autowired
    private MethodlockMapper methodlockMapper;

    /**
     * 加锁
     *
     * @param methodName  被调方法名称（方法名唯一）
     * @param description 方法描述
     */
    public boolean lock(String methodName, String description) {
        int result = methodlockMapper.addMethodLock(methodName, description, new Date());
        return result == 1;
    }

    /**
     * 释放锁
     *
     * @param methodName 被调方法名称（方法名唯一）
     */
    public boolean unLock(String methodName) {
        int result = methodlockMapper.deleteMethodLock(methodName);
        return result == 1;
    }
}
