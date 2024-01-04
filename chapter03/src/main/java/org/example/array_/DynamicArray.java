package org.example.array_;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * @author wing
 * @create 2024/1/4
 */
public class DynamicArray {

    @Test
    @DisplayName("array 测试通过")
    public void test1() {
        int rows = 1000000;
        int columns = 14;
        int[][] a = new int[rows][columns];

        long start1 = System.currentTimeMillis();
        ij(a,rows,columns);
        long end1 = System.currentTimeMillis();
        System.out.println("ij耗时：" + (end1 - start1));

        long start2 = System.currentTimeMillis();
        ji(a,rows,columns);
        long end2 = System.currentTimeMillis();
        System.out.println("ji耗时：" + (end2 - start2));
    }

    /* 先遍历行，再遍历列 */
    public static void ij(int[][] a, int rows, int columns){
        long sum = 0L;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum += a[i][j];
            }
        }
    }
    /* 先遍历列，再遍历行 */
    public static void ji(int[][] a, int rows, int columns){
        long sum = 0L;
        for (int j = 0; j < columns; j++) {
            for (int i = 0; i < rows; i++) {
                sum += a[i][j];
            }
        }
    }
}
