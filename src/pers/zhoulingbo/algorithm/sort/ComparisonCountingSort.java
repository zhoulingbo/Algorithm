/*
 * 版权所有 (C) 2016 周凌波。保留所有权利。
 * 
 */
package pers.zhoulingbo.algorithm.sort;

public class ComparisonCountingSort
{

    public static int[] sort(int[] a)
    {
        int[] count = new int[a.length];
        for (int i = 0; i < a.length - 1; i++)
        {
            for (int j = i + 1; j < a.length; j++)
            {
                if (a[i] > a[j])
                    count[i]++;
                else
                    count[j]++;
            }
        }

        int[] s = new int[a.length];
        for (int k = 0; k < a.length; k++)
        {
            s[count[k]] = a[k];
        }

        return s;
    }
}
