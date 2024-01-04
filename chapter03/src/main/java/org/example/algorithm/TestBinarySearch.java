package org.example.algorithm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author wing
 * @create 2024/1/2
 */
public class TestBinarySearch {

    @Test
    @DisplayName("binarySearchBasic 找到")
    public void test1() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(0, BinarySearch.binarySearchBasic(a,2));
        assertEquals(1, BinarySearch.binarySearchBasic(a,5));
        assertEquals(2, BinarySearch.binarySearchBasic(a,8));
        assertEquals(3, BinarySearch.binarySearchBasic(a,14));
        assertEquals(4, BinarySearch.binarySearchBasic(a,19));
        assertEquals(5, BinarySearch.binarySearchBasic(a,89));
        assertEquals(6, BinarySearch.binarySearchBasic(a,102));
    }

    @Test
    @DisplayName("binarySearchBasic 没找到")
    public void test2() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(-1, BinarySearch.binarySearchBasic(a,0));
        assertEquals(-1, BinarySearch.binarySearchBasic(a,6));
        assertEquals(-1, BinarySearch.binarySearchBasic(a,100));
    }

    public static void main(String[] args) {
        int i = 0;
        int j = Integer.MAX_VALUE - 1;
        int m = (i + j) / 2;  //01011111111111111111111111111111
        System.out.println(m);  // 1073741823
        //假如目标在右侧
        i = m + 1;
        System.out.println(i);  // 1073741824 -> 01000000 00000000 00000000 00000000
        System.out.println(j);  // 2147483646 -> 01111111 11111111 11111111 11111110
        m = (i + j) / 2;        // 1073741824 + 2147483646 = 3221225470 -> 10111111 11111111 11111111 11111110（java将第一位作为符号位：-1073741822）
        System.out.println(m);  // -536870913 = -1073741822 / 2 出现了负数，原因是(i + j)的值大于了Integer的最大值

        // 改进,使用无符号向右移动1位
        m = (i + j) >>> 1;  // 10111111 11111111 11111111 11111110 右移动一位 -> 01011111 11111111 11111111 11111111
        System.out.println(m); // 1610612735  正确
    }

    @Test
    @DisplayName("binarySearchAlternative 找到")
    public void test3() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(0, BinarySearch.binarySearchAlternative(a,2));
        assertEquals(1, BinarySearch.binarySearchAlternative(a,5));
        assertEquals(2, BinarySearch.binarySearchAlternative(a,8));
        assertEquals(3, BinarySearch.binarySearchAlternative(a,14));
        assertEquals(4, BinarySearch.binarySearchAlternative(a,19));
        assertEquals(5, BinarySearch.binarySearchAlternative(a,89));
        assertEquals(6, BinarySearch.binarySearchAlternative(a,102));
    }

    @Test
    @DisplayName("binarySearchAlternative 没找到")
    public void test4() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(-1, BinarySearch.binarySearchAlternative(a,0));
        assertEquals(-1, BinarySearch.binarySearchAlternative(a,6));
        assertEquals(-1, BinarySearch.binarySearchAlternative(a,100));
    }

    @Test
    @DisplayName("binarySearch3 找到")
    public void test5() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(0, BinarySearch.binarySearch3(a,2));
        assertEquals(1, BinarySearch.binarySearch3(a,5));
        assertEquals(2, BinarySearch.binarySearch3(a,8));
        assertEquals(3, BinarySearch.binarySearch3(a,14));
        assertEquals(4, BinarySearch.binarySearch3(a,19));
        assertEquals(5, BinarySearch.binarySearch3(a,89));
        assertEquals(6, BinarySearch.binarySearch3(a,102));
    }

    @Test
    @DisplayName("binarySearch3 没找到")
    public void test6() {
        // 有序数组
        int[] a = {2,5,8,14,19,89,102};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(-1, BinarySearch.binarySearch3(a,0));
        assertEquals(-1, BinarySearch.binarySearch3(a,6));
        assertEquals(-1, BinarySearch.binarySearch3(a,100));
    }

    @Test
    @DisplayName("binarySearchLeftmost 找到")
    public void test7() {
        // 有序数组
        int[] a = {1,2,4,4,4,5,6,7};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(0, BinarySearch.binarySearchLeftmost(a,1));
        assertEquals(1, BinarySearch.binarySearchLeftmost(a,2));
        assertEquals(2, BinarySearch.binarySearchLeftmost(a,4));
        assertEquals(5, BinarySearch.binarySearchLeftmost(a,5));
        assertEquals(6, BinarySearch.binarySearchLeftmost(a,6));
        assertEquals(7, BinarySearch.binarySearchLeftmost(a,7));
    }

    @Test
    @DisplayName("binarySearchLeftmost 没找到")
    public void test8() {
        // 有序数组
        int[] a = {1,2,4,4,4,5,6,7};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(-1, BinarySearch.binarySearchLeftmost(a,0));
        assertEquals(-1, BinarySearch.binarySearchLeftmost(a,8));
        assertEquals(-1, BinarySearch.binarySearchLeftmost(a,100));
    }

    @Test
    @DisplayName("binarySearchRightmost 找到")
    public void test9() {
        // 有序数组
        int[] a = {1,2,4,4,4,5,6,7};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(0, BinarySearch.binarySearchRightmost(a,1));
        assertEquals(1, BinarySearch.binarySearchRightmost(a,2));
        assertEquals(4, BinarySearch.binarySearchRightmost(a,4));
        assertEquals(5, BinarySearch.binarySearchRightmost(a,5));
        assertEquals(6, BinarySearch.binarySearchRightmost(a,6));
        assertEquals(7, BinarySearch.binarySearchRightmost(a,7));
    }

    @Test
    @DisplayName("binarySearchRightmost 没找到")
    public void test10() {
        // 有序数组
        int[] a = {1,2,4,4,4,5,6,7};
        // 断言，期待返回结果是0，如果返回值和期待值不相等，那么断言失败
        assertEquals(-1, BinarySearch.binarySearchRightmost(a,0));
        assertEquals(-1, BinarySearch.binarySearchRightmost(a,8));
        assertEquals(-1, BinarySearch.binarySearchRightmost(a,100));
    }

    @Test
    @DisplayName("binarySearchLeftmost1 找到找不到都会返回索引值")
    public void test11() {
        int[] a = {1,2,4,4,4,5,6,7};
        assertEquals(0, BinarySearch.binarySearchLeftmost1(a,1));
        assertEquals(1, BinarySearch.binarySearchLeftmost1(a,2));
        assertEquals(2, BinarySearch.binarySearchLeftmost1(a,4));
        assertEquals(5, BinarySearch.binarySearchLeftmost1(a,5));
        assertEquals(6, BinarySearch.binarySearchLeftmost1(a,6));
        assertEquals(7, BinarySearch.binarySearchLeftmost1(a,7));
        assertEquals(2, BinarySearch.binarySearchLeftmost1(a,3));
        assertEquals(8, BinarySearch.binarySearchLeftmost1(a,8));
    }

    @Test
    @DisplayName("binarySearchRightmost1 找到找不到都会返回索引值")
    public void test13() {
        int[] a = {1,2,4,4,4,5,6,7};
        assertEquals(0, BinarySearch.binarySearchRightmost1(a,1));
        assertEquals(1, BinarySearch.binarySearchRightmost1(a,2));
        assertEquals(4, BinarySearch.binarySearchRightmost1(a,4));
        assertEquals(5, BinarySearch.binarySearchRightmost1(a,5));
        assertEquals(6, BinarySearch.binarySearchRightmost1(a,6));
        assertEquals(7, BinarySearch.binarySearchRightmost1(a,7));
        assertEquals(1, BinarySearch.binarySearchRightmost1(a,3));
        assertEquals(7, BinarySearch.binarySearchRightmost1(a,8));
    }

}
