package com.hspedu.simulatemap_.java_.util_;


/**
 * @author wing
 * @create 2023/12/24
 * 模拟Set接口
 */
public interface Set_<E> extends Collection_<E> {
    /**
     * 返回此集合中的元素数量
     * */
    int size();
    /**
     * 如果此集合不包含任何元素，则为True
     * */
    boolean isEmpty();
    /**
     * 如果此集合包含指定元素，则为True
     * */
    boolean contains(Object o);
}
