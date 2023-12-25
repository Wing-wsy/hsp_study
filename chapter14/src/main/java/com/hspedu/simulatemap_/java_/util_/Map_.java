package com.hspedu.simulatemap_.java_.util_;

/**
 * @author wing
 * @create 2023/12/24
 * 模拟Map接口
 */
public interface Map_<K,V> {

    /**
     * 返回此映射中键值映射的个数
     * */
    int size();
    /**
     * 如果此映射不含任何键-值映射，则返回true
     * */
    boolean isEmpty();
    /**
     * 如果此映射包含指定键的映射，则为True
     * */
    boolean containsKey(Object key);
    /**
     * 如果此映射将一个或多个键映射到指定值，则为True
     * */
    boolean containsValue(Object value);
    /**
     * 根据指定的key获取value
     * */
    V get(Object key);
    /**
     * 存储k-v
     * */
    V put(K key, V value);;
    /**
     * 移除指定的 k-v，并返回v
     * */
    V remove(Object key);
    /**
     * k和v，设置上限
     * */
    void putAll(Map_<? extends K, ? extends V> m);
    /**
     * 从该映射中删除所有映射
     * */
    void clear();
}
