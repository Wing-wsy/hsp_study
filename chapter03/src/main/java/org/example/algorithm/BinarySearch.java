package org.example.algorithm;

/**
 * @author wing
 * @create 2024/1/3
 */
public class BinarySearch {
    /**
      二分查找基础版
      Params: a-待查找的升序数组
              target-待查找的目标值
      Returns:
             找到则返回索引
             找不到返回-1
     * */
    public static int binarySearchBasic(int[] a, int target){
        int i = 0, j = a.length - 1;       // 设置指针和初值
        while (i <= j){                    // i ~ j范围内有数据
            //int m = (i + j) / 2;
            int m = (i + j) >>> 1;         // 改进后
            if(target < a[m]){             // 目标在左边
                j = m - 1;
            }else if(a[m] < target){       // 目标在右边
                i = m + 1;
            }else {
                return m;                  // 找到了
            }
        }
        return -1;
    }

    /**
     二分查找改动版
     Params: a-待查找的升序数组
     target-待查找的目标值
     Returns:
     找到则返回索引
     找不到返回-1
     * */
    public static int binarySearchAlternative(int[] a, int target){
        int i = 0, j = a.length;            // 第一处改动，j当成一个边界，不参与比较
        while (i < j){                      // 第二处改动
            int m = (i + j) >>> 1;
            if(target < a[m]){
                j = m;                 // 第三处改动
            }else if(a[m] < target){
                i = m + 1;
            }else {
                return m;
            }
        }
        return -1;
    }
    /**
     二分查找平衡版
     Params: a-待查找的升序数组
     target-待查找的目标值
     Returns:
     找到则返回索引
     找不到返回-1
     * */
    public static int binarySearch3(int[] a, int target){
        int i = 0, j = a.length;
        while (1 < j - i){
            int m = (i + j) >>> 1;
            if(target < a[m]){
                j = m;
            }else{
                i = m;
            }
        }
        if(a[i] == target){
            return i;
        }
        return -1;
    }
    /**
     二分查找Leftmost
     Params: a-待查找的升序数组
     target-待查找的目标值
     Returns:
     找到则返回索引，如果存在重复的目标值，那么返回最左侧索引
     找不到返回-1
     * */
    public static int binarySearchLeftmost(int[] a, int target){
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j){
            int m = (i + j) >>> 1;
            if(target < a[m]){
                j = m - 1;
            }else if(a[m] < target){
                i = m + 1;
            }else {
                candidate = m; // 记录候选位置
                j = m - 1;     // 继续向左
            }
        }
        return candidate;
    }
    /**
     二分查找Rightmost
     Params: a-待查找的升序数组
     target-待查找的目标值
     Returns:
     找到则返回索引，如果存在重复的目标值，那么返回最右侧索引
     找不到返回-1
     * */
    public static int binarySearchRightmost(int[] a, int target){
        int i = 0, j = a.length - 1;
        int candidate = -1;
        while (i <= j){
            int m = (i + j) >>> 1;
            if(target < a[m]){
                j = m - 1;
            }else if(a[m] < target){
                i = m + 1;
            }else {
                candidate = m; // 记录候选位置
                i = m + 1;     // 继续向右
            }
        }
        return candidate;
    }

    /**
     二分查找Leftmost
     Params: a-待查找的升序数组
     target-目标值
     Returns:
     返回 >= target 最靠左的索引值
     * */
    public static int binarySearchLeftmost1(int[] a, int target){
        int i = 0, j = a.length - 1;
        while (i <= j){
            int m = (i + j) >>> 1;
            if(target <= a[m]){
                j = m - 1;
            }else{
                i = m + 1;
            }
        }
        return i;
    }

    /**
     二分查找Rightmost
     Params: a-待查找的升序数组
     target-待查找的目标值
     Returns:
     返回 <= target 最靠右的索引值
     * */
    public static int binarySearchRightmost1(int[] a, int target){
        int i = 0, j = a.length - 1;
        while (i <= j){
            int m = (i + j) >>> 1;
            if(target < a[m]){
                j = m - 1;
            }else {
                i = m + 1;
            }
        }
        return i - 1;
    }
}
