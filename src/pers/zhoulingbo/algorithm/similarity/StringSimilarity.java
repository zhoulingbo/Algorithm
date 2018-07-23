/*
 * 版权所有 (C) 2016 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2018-7-18，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.similarity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 相似性算法
 * 
 * @version v1.0.0 @author zhoulingbo 2018-7-18 新建与整理
 */
public class StringSimilarity
{

    public static void main(String[] args)
    {
        String s1 = "湖南省长沙市";
        String s2 = "湖南长沙";
        System.out.println(jaccardDistance(s1, s2));
    }

    /**
     * 统计字符个数
     * @param str
     * @return
     */
    public static HashMap<Character, Integer> count(String str)
    {
        char[] chars = str.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();

        for (char c : chars)
        {
            if (!map.containsKey(c))
            {
                map.put(c, 1);
            }
            else
            {
                map.put(c, map.get(c) + 1);
            }
        }

        return map;
    }

    /**
     * 余弦相似度计算
     * @param s1
     * @param s2
     * @return
     */
    public static double cosine(String s1, String s2)
    {
        Set<Character> set = new HashSet<>();

        char[] chars1 = s1.toCharArray();
        for (char c : chars1)
        {
            set.add(c);
        }

        char[] chars2 = s2.toCharArray();
        for (char c : chars2)
        {
            set.add(c);
        }

        int[] v1 = new int[set.size()];
        int[] v2 = new int[set.size()];
        HashMap<Character, Integer> map1 = count(s1);
        HashMap<Character, Integer> map2 = count(s2);
        
        int index = 0;
        for (char ch : set)
        {
            v1[index] = map1.containsKey(ch) ? map1.get(ch) : 0;
            v2[index] = map2.containsKey(ch) ? map2.get(ch) : 0;
            index ++;
        }

        return CosineSimiliarity.compute(v1, v2);
    }
    
    /**
     * Levenshtein Distance (Edit Distance) 算法，是指两个字符串之间，由一个转成另一个所需的最少编辑操作次数
     * @param s1
     * @param s2
     * @return
     */
    public static double editDistance(String s1, String s2)
    {
        int m = s1.length(), n = s2.length();
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        
        int[][] matrix = new int[m+1][n+1];
        
        for (int i=0; i<m+1; i++)
        {
            matrix[i][0] = i;
        }
        
        for (int i=0; i<n+1; i++)
        {
            matrix[0][i] = i;
        }

        for (int j=1; j<m+1; j++)
        {
            for (int k=1; k<n+1; k++)
            {
                int w = chars1[j-1] == chars2[k-1] ? 0 : 1;
                int x = Math.min(matrix[j-1][k]+1, matrix[j][k-1]+1);
                matrix[j][k] = Math.min(x, matrix[j-1][k-1]+w);
            }
        }
        
        int dis = matrix[m][n];
        double similarity = 1 - ((double)dis / Math.max(m, n));
        
        return similarity;
    }
    
    /**
     * dice系数
     * @param s1
     * @param s2
     * @return
     */
    public static double diceDistance(String s1, String s2)
    {
        int m = s1.length(), n = s2.length();
        char[] chars1 = s1.toCharArray();
        char[] chars2 = s2.toCharArray();
        
        HashMap<Character, Integer> map1 = count(s1);
        HashMap<Character, Integer> map2 = count(s2);
        
        double intersection = 0;
        if (m < n)
        {
            for (char ch : chars1)
            {
                if (map2.containsKey(ch))
                {
                    intersection ++;
                }
            }
        }
        else
        {
            for (char ch : chars2)
            {
                if (map1.containsKey(ch))
                {
                    intersection ++;
                }
            }
        }
        
        double similarity = 2 * intersection / (m + n);
        
        return similarity;
    }

    /**
     * 杰卡德系数
     * @param s1
     * @param s2
     * @return
     */
    public static double jaccardDistance(String s1, String s2)
    {
        double d = diceDistance(s1, s2);
        double similarity = d / (2 - d);
        return similarity;
    }
}
