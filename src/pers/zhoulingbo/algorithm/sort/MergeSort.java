/*
 * 版权所有 (C) 2016 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2018-12-12，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.sort;

/**
 * 
 * 归并排序
 *
 * @version v1.0.0 @author zhoulingbo 2018-12-12 新建与整理
 */
public class MergeSort
{

    public static void sort(int[] a)
    {
        int[] t = new int[a.length];
        sort(a, 0, a.length - 1, t);
    }

    public static void sort(int[] a, int left, int right, int[] t)
    {
        if (left < right)
        {
            int middle = (right + left) / 2;
            sort(a, left, middle, t);
            sort(a, middle + 1, right, t);

            merge(a, left, middle, right, t);
        }
    }

    public static void merge(int[] a, int left, int middle, int right, int[] t)
    {
        int i = 0, l = left, r = middle + 1;
        while (l <= middle && r <= right)
        {
            if (a[l] < a[r])
                t[i++] = a[l++];
            else
                t[i++] = a[r++];
        }

        while (l <= middle)
            t[i++] = a[l++];
        while (r <= right)
            t[i++] = a[r++];
        
        i = 0;
        while (left <= right)
            a[left++] = t[i++];
    }
}
