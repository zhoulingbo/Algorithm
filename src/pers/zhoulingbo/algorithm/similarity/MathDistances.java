/*
 * 版权所有 (C) 2018 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2018-7-25，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.similarity;

/**
 * 
 * 各种数学距离计算
 *
 * @version v1.0.0 @author zhoulingbo 2018-7-25 新建与整理
 */
public class MathDistances
{
    
    /**
     * 计算明可夫斯基距离距离
     * @param x
     * @param y
     * @return
     */
    public static double minkowskiDistance(int[] x, int[] y, double p)
    {
        if (x.length != y.length)
            return -1;
        
        double sum = 0;
        for (int i = 0; i < x.length; i++)
        {
            sum += Math.pow(x[i] - y[i], p);
        }

        return Math.pow(sum, 1/p);
    }

    /**
     * 计算欧几里得距离
     * @param x
     * @param y
     * @return
     */
    public static double eucledianDistance(int[] x, int[] y)
    {
        if (x.length != y.length)
            return -1;
        
        double sum = 0;
        for (int i = 0; i < x.length; i++)
        {
            sum += Math.pow(x[i] - y[i], 2);
        }

        return Math.sqrt(sum);
    }
    
    /**
     * 计算曼哈顿距离
     * @param x
     * @param y
     * @return
     */
    public static double manhattanDistance(int[] x, int[] y)
    {
        if (x.length != y.length)
            return -1;
        
        double sum = 0;
        for (int i = 0; i < x.length; i++)
        {
            sum += Math.abs(x[i] - y[i]);
        }

        return sum;
    }
    
    /**
     * 计算切比雪夫距离
     * @param x
     * @param y
     * @return
     */
    public static double chebyshevDistance(int[] x, int[] y)
    {
        if (x.length != y.length)
            return -1;
        
        double max = 0;
        for (int i = 0; i < x.length; i++)
        {
            max = Math.max(max, Math.abs(x[i] - y[i]));
        }

        return max;
    }
    
    /**
     * 计算汉明距离
     * @param x
     * @param y
     * @return
     */
    public static int hammingDistance(int[] x, int[] y)
    {
        if (x.length != y.length)
            return -1;
        
        int d = 0;
        for (int i = 0; i < x.length; i++)
        {
            if (x[i] != y[i])
                d ++;
        }

        return d;
    }
}
