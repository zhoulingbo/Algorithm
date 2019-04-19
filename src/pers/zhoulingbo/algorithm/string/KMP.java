/*
 * 版权所有 (C) 2016 周凌波。保留所有权利。
 * 
 */
package pers.zhoulingbo.algorithm.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * KMP字符串查找算法
 * 
 */
public class KMP
{

    public static void main(String[] args)
    {
        String s = "aabaabac";
        System.out.println(getPartialMatchKMP(s));
    }

    /**
     * 在t中查找p
     * @param p
     * @param t
     * @return
     */
    public static int kmpSearch(String p, String t)
    {
        char[] ps = p.toCharArray();
        char[] ts = t.toCharArray();
        int[] pi = getPartialMatch(p);
        int m = pi.length;
        int n = t.length();
        int begin = 0;
        int matched = 0;
        while (begin <= n - m)
        {
            if (matched < m && ts[begin+matched] == ps[matched])
            {
                matched ++;
                if (matched == m)
                    return begin;
            }
            else
            {
                if (matched == 0)
                    begin ++;
                else
                {
                    begin += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }
        
        return -1;
    }

    /**
     * 在t中查找所有p
     * @param p
     * @param t
     * @return
     */
    public static List<Integer> kmpSearchAll(String p, String t)
    {
        List<Integer> list = new ArrayList<>();
        char[] ps = p.toCharArray();
        char[] ts = t.toCharArray();
        int[] pi = getPartialMatch(p);
        int m = pi.length;
        int n = t.length();
        int begin = 0;
        int matched = 0;
        while (begin <= n - m)
        {
            if (matched < m && ts[begin+matched] == ps[matched])
            {
                matched ++;
                if (matched == m)
                    list.add(begin);
            }
            else
            {
                if (matched == 0)
                    begin ++;
                else
                {
                    begin += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }
        
        return list;
    }
    
    /**
     * 获取字符串部分匹配表
     * @param s
     * @return
     */
    public static int[] getPartialMatch(String s)
    {
        char[] a = s.toCharArray();
        int[] pi = new int[a.length];
        for (int begin = 1; begin < pi.length; begin++)
        {
            for (int i = 0; i < pi.length; i++)
            {
                if (a[begin + i] != a[i])
                    break;
                pi[begin + i] = Math.max(pi[begin + i], i + 1);
            }
        }

        return pi;
    }
    
    /**
     * 获取字符串部分匹配表(kmp)
     * @param s
     * @return
     */
    public static int[] getPartialMatchKMP(String s)
    {
        char[] a = s.toCharArray();
        int[] pi = new int[a.length];
        int begin = 1;
        int matched = 0;
        while (begin + matched < a.length)
        {
            if (a[begin+matched] == a[matched])
            {
                matched ++;
                pi[begin+matched-1] = matched;
            }
            else
            {
                if (matched == 0)
                    begin ++;
                else
                {
                    begin += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }

        return pi;
    }

}
