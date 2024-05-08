-- 使用Lua脚本的目的是让判断锁和释放锁步骤为原子性，如果在代码中实现，就不是原子性了，可能存在判断到一致后，线程的切换，然后刚好锁失效了，这样另一个线程就能
-- 获取到锁，等线程再切换回来，因为已经判断过一致了，所以直接执行释放锁，这样就相当于把别人的锁给释放了（自己的锁过期失效了，释放的是别人的锁）
-- 比较线程标示与锁中的标示是否一致
if(redis.call('get', KEYS[1]) ==  ARGV[1]) then
    -- 释放锁 del key
    return redis.call('del', KEYS[1])
end
return 0