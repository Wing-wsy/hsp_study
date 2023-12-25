package com.hspedu.simulatemap_.java_.util_;

import java.util.List;

/**
 * @author wing
 * @create 2023/12/24
 * 模拟List接口
 */
public interface List_<E> extends Collection_<E>{
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
    /**
     * 添加元素
     * */
    boolean add(E e);
    /**
     * 删除元素
     * */
    boolean remove(Object o);
    /**
     * 如果此集合包含指定集合中的所有元素，则返回true
     * */
    boolean containsAll(Collection_<?> c);
    /**
     * 将指定集合中的所有元素添加到此集合
     * */
    boolean addAll(Collection_<? extends E> c);
    /**
     * 【List特有】将指定集合中的所有元素插入到该列表的指定位置
     * */
    boolean addAll(int index, Collection_<? extends E> c);
    /**
     * 元素中也包含的此集合的所有元素
     * 指定集合(可选操作)。调用返回后，
     * 此集合将不包含与指定的元素相同的元素
     * */
    boolean removeAll(Collection_<?> c);
    /**
     * 仅保留此集合中包含在指定集合中的元素(可选操作)。换句话说，从此集合中删除未包含在指定集合中的所有元素。
     * */
    boolean retainAll(Collection_<?> c);
    /**
     * 清除集合
     * */
    void clear();
    /**
     *
     * */
    boolean equals(Object o);
    /**
     *
     * */
    int hashCode();
    /**
     * 根据索引获取元素
     * */
    E get(int index);
    /**
     * 将此列表中指定位置的元素替换为指定元素(可选操作)。
     * 参数:
     * index-要替换元素的元素的索引
     * element-要存储在指定位置的元素
     * 返回:
     * 先前在指定位置的元素
     * */
    E set(int index, E element);
    /**
     * 将指定元素插入此列表中的指定位置(可选操作)。将当前在该位置的元素(如果有的话)和任何后续元素向右移动(在它们的索引上加1)。
     * 参数:
     * 索引-要插入指定元素的索引。元素-要插入的元素
     * */
    void add(int index, E element);
    /**
     * 移除列表中指定位置的元素(可选操作)。将所有后续元素向左移动(从它们的索引中减去1)。返回从列表中删除的元素。
     * 参数:
     * 索引-要删除的元素的索引
     * 返回:
     * 先前在指定位置的元素
     * */
    E remove(int index);
    /**
     * 指定元素在此列表中第一次出现的索引，如果此列表不包含该元素，则为-1
     * */
    int indexOf(Object o);
    /**
     * 指定元素在此列表中最后出现的索引，如果此列表不包含该元素，则为-1
     * */
    int lastIndexOf(Object o);
    /**
     * fromIndex -子列表的低端点(包括)toIndex -子列表的高端点(不包括)
     * 返回:
     * 此列表中指定范围的视图
     * */
    List_<E> subList(int fromIndex, int toIndex);

    /**
     * 返回包含此集合中所有元素的数组
     * */
    Object[] toArray();

    //Iterator<E> iterator();
    //<T> T[] toArray(T[] a);
    //default void replaceAll(UnaryOperator<E> operator) {}
    //default void sort(Comparator<? super E> c) {}
    //ListIterator<E> listIterator();
    //ListIterator<E> listIterator(int index);
    //default Spliterator<E> spliterator() {}

}
