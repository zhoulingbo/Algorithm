/*
 * 版权所有 (C) 2018 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2018-7-23，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.similarity;

public class CosineSimiliarity
{

    /**
     * 计算两个向量的相似度
     * @param v1
     * @param v2
     * @return
     */
    public static double compute(int[] v1, int[] v2)
    {
        double sum = 0, sum1 = 0, sum2 = 0;
        for (int i = 0; i < v1.length; i++)
        {
            sum += v1[i] * v2[i];
            sum1 += v1[i] * v1[i];
            sum2 += v2[i] * v2[i];
        }

        if (sum == 0)
            return 0;
        
        return sum / (Math.sqrt(sum1) * Math.sqrt(sum2));
    }
}
