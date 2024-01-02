package org.example.generic.test;

/**
 * @author wing
 * @create 2024/1/2
 */
public class ComparablePair <T extends Comparable<T>> implements Comparable<ComparablePair<T>> {
    private T first;
    private T second;
    public ComparablePair(T first, T second) {
        this.first = first;
        this.second = second;
    }
    @Override
    public int compareTo(ComparablePair<T> other) {
        // 比较第一个元素
        int firstComparison = this.first.compareTo(other.first);
        if (firstComparison != 0) {
            return firstComparison;
        }
        // 如果第一个元素相等，则比较第二个元素
        return this.second.compareTo(other.second);
    }
    @Override
    public String toString() {
        return "ComparablePair{first=" + first + ", second=" + second + '}';
    }
}
