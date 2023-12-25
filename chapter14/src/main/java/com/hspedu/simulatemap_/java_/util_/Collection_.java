package com.hspedu.simulatemap_.java_.util_;


/**
 * @author wing
 * @create 2023/12/24
 * 模拟Collection接口
 */
public interface Collection_<E> {

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
     * 返回包含此集合中所有元素的数组
     * */
    Object[] toArray();

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
     * 比较指定对象与此集合是否相等。
     * 而Collection接口没有为Object的通用契约添加任何规定。因此，“直接”实现Collection接口的
     * 程序员(换句话说，创建一个是Collection但不是Set或List的类)在选择覆盖Object.equals时必须非常小心。
     * 没有必要这样做，最简单的做法是依赖Object的实现，但实现者可能希望实现“值比较”来代替默认的“引用比较”。
     * (List和Set接口要求进行这样的值比较。)
     * */
    boolean equals(Object o);
    /**
     * 返回此集合的哈希码值。而Collection接口没有为Object的通用契约添加任何规定。
     * hashCode方法，程序员应该注意任何覆盖Object的类。equals方法也必须覆盖对象。
     * hashCode方法，以满足对象的一般契约。hashCode方法。
     * 特别地，c1.equals(c2)意味着c1.hashCode()==c2. hashcode()。
     * */
    int hashCode();

    /** 下面是暂时不研究的方法 */
    //Iterator<E> iterator();
    //Object[] toArray();
    //<T> T[] toArray(T[] a);
    //default boolean removeIf(Predicate<? super E> filter) {...}
    //default Spliterator<E> spliterator() {...}
    //default Stream<E> stream() {...}
    //default Stream<E> parallelStream() {...}

}
