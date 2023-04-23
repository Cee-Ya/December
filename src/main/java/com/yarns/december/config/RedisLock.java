package com.yarns.december.config;

import com.yarns.december.support.lang.ActionVoid;
import com.yarns.december.support.lang.FuncVoid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class RedisLock {

    private final RedissonClient redissonClient;

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public void tryLock(String key, ActionVoid action) {
        tryLock(key, -1, action);
    }

    public void tryLock(String key, long waitTime, ActionVoid action) {
        tryLock(key, waitTime, -1, TimeUnit.MILLISECONDS, action);
    }

    public void tryLock(String key, long waitTime, long leaseTime, ActionVoid action) {
        tryLock(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, action);
    }

    public void tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit, ActionVoid action) {
        if (null == action) {
            return;
        }
        RLock lock = redissonClient.getLock(key);
        try {
            if (tryLock(lock, waitTime, leaseTime, timeUnit)) {
                action.invoke();
            }
        } catch (InterruptedException ex) {
            log.warn("获取分布式锁失败：{}", ex.getLocalizedMessage());
        } finally {
            lock.unlock();
        }
    }

    public <T> T tryLockT(String key, FuncVoid<T> func) {
        return tryLockT(key, -1, func);
    }

    public <T> T tryLockT(String key, long waitTime, FuncVoid<T> func) {
        return tryLockT(key, waitTime, -1, TimeUnit.MILLISECONDS, func);
    }

    public <T> T tryLockT(String key, long waitTime, long leaseTime, FuncVoid<T> func) {
        return tryLockT(key, waitTime, leaseTime, TimeUnit.MILLISECONDS, func);
    }

    public <T> T tryLockT(String key, long waitTime, long leaseTime, TimeUnit timeUnit, FuncVoid<T> func) {
        if (null == func) {
            return null;
        }
        RLock lock = redissonClient.getLock(key);
        try {
            if (tryLock(lock, waitTime, leaseTime, timeUnit)) {
                return func.invoke();
            }
        } catch (InterruptedException ex) {
            log.warn("获取分布式锁失败：{}", ex.getLocalizedMessage());
        } finally {
            lock.unlock();
        }
        return null;
    }

    private boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit timeUnit) throws InterruptedException {
        if (null == timeUnit) {
            timeUnit = TimeUnit.MILLISECONDS;
        }
        if (waitTime > 0 && leaseTime <= 0) {
            return lock.tryLock(waitTime, timeUnit);
        } else if (waitTime > 0 && leaseTime > 0) {
            return lock.tryLock(waitTime, leaseTime, timeUnit);
        } else {
            return lock.tryLock();
        }
    }

}