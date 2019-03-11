package pers.zhoulingbo.algorithm.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 多元线性回归(波士顿房价)
 * 模型: y = w0 + w1*x1 + w2*x2 + ... + wn*xn;
 *
 */
public class MultiLinearRegression
{
    private double[][] data_xs;     // 训练数据集
    private double[] data_ys;       // 训练结果集
    private double[][] test_xs;     // 测试数据集
    private double[] test_ys;       // 测试结果集
    
    private double learn_rate = 0.0003; // 学习率
    private double[] w;
    private int step = 200000; // 步数

    public static void main(String[] args)
    {
        MultiLinearRegression lr = new MultiLinearRegression("housing.data");
        lr.gradientDescent();
        lr.predict();
    }

    public MultiLinearRegression(String path)
    {
        init(path);
    }
    
    private void init(String path)
    {
        try (FileReader input = new FileReader(path))
        {
            List<double[]> xList = new ArrayList<>();
            List<Double> yList = new ArrayList<>();
            
            BufferedReader bufferReader = new BufferedReader(input);
            String line = bufferReader.readLine();
            while (line != null)
            {
                line = line.trim();
                if (line != null)
                {
                    String[] item = line.split("\\s+");
                    double[] ditem = new double[item.length];
                    ditem[0] = 1.0;
                    for (int i=1; i<item.length; i++)
                    {
                        ditem[i] = Double.parseDouble(item[i-1]);
                    }
                    xList.add(ditem);
                    yList.add(Double.parseDouble(item[item.length-1]));
                }
                line = bufferReader.readLine();
            }

            xList = normalization(xList);

            int size = xList.size() * 4 / 5;    // 划分训练与测试数据
            data_xs = new double[size][xList.get(0).length];
            data_ys = new double[size];
            test_xs = new double[xList.size()-size][xList.get(0).length];
            test_ys = new double[xList.size()-size];
            
            xList.toArray(data_xs);
            for (int j=0; j<xList.size(); j++)
            {
                if (j < size)
                {
                    data_xs[j] = xList.get(j);
                    data_ys[j] = yList.get(j);
                }
                else
                {
                    test_xs[j-size] = xList.get(j);
                    test_ys[j-size] = yList.get(j);
                }
            }
            w = new double[xList.get(0).length];
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 归一化处理
     * @param list
     */
    private List<double[]> normalization(List<double[]> list)
    {
        double mins[] = new double[list.get(0).length];
        double maxs[] = new double[list.get(0).length];
        
        for (int i=0; i<list.get(0).length; i++)
        {
            double min = list.get(0)[i];
            double max = list.get(0)[i];
            for (int j=0; j<list.size(); j++)
            {
                double[] item = list.get(j);
                if (item[i] > max)
                {
                    max = item[i];
                }
                if (item[i] < min)
                {
                    min = item[i];
                }
            }
            mins[i] = min;
            maxs[i] = max;
        }

        for (int i=0; i<mins.length; i++)
        {
            if (mins[i] < -3 || maxs[i] > 3)
            {
                for (int j=0; j<list.size(); j++)
                {
                    double[] item = list.get(j);
                    double val = item[i] / (1 + maxs[i] - mins[i]);
                    item[i] = val;
                }
            }
        }
        
        return list;
    }

    /**
     * 梯度下降算法
     */
    public void gradientDescent()
    {
        double[] t = Arrays.copyOf(w, w.length);
        for (int i = 0; i < step; i++)
        {
            for (int j=0; j<w.length; j++)
            {
                w[j] = t[j] - learn_rate * partialDerivative(data_xs, data_ys, j, t);
            }

            t = Arrays.copyOf(w, w.length);

            if (i %10 == 0)
            {
                System.out.println("step:" + i + "  loss:" + meanSquareError(data_xs, data_ys, t));
                for (int k=0; k<w.length; k++)
                    System.out.print(w[k] + ",");
                System.out.println();
            }
        }
    }

    /**
     * 求偏导
     * @return
     */
    public double partialDerivative(double[][] x_s, double[] y_s, int index, double[] as)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            double b = 0.0;
            for (int j = 0; j < x_s[0].length; j++)
            {
                b += as[j]*x_s[i][j];
            }
            
            b -= y_s[i];
            b *= x_s[i][index];
            a += b;
        }
        return a;
    }

    /**
     * 均方误差
     * @param x_s
     * @param y_s
     * @param a1
     * @param a2
     * @return
     */
    public double meanSquareError(double[][] x_s, double[] y_s, double[] as)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            double b = 0.0;
            for (int j = 0; j < x_s[0].length; j++)
            {
                b += as[j]*x_s[i][j];
            }
            b -= y_s[i];
            b *= b;
            a += b;
        }

        a = a / (2 * x_s.length);
        return a;
    }

    /**
     * 预测
     * @return
     */
    public void predict()
    {
        //double[] w = new double[]{0.3466787643361681,  -0.20165341883719037,  0.06517784388663479,  0.04290911878504748,  0.43782563669154545,  0.17312686768172994,  3.9793477772298633,  0.031770279099284135,  -0.8108285635233324,  0.2766789554027901,  -0.010640982819620335,  -0.27015387714307687,  0.03416740797305704,  -0.6575973385655476};
        for (int k=0; k<test_xs.length; k++)
        {
            double a = 0.0;
            for (int i=0; i<w.length; i++)
                a += w[i]*test_xs[k][i];
            System.out.println("predict:" + a + " test_y:" + test_ys[k]);
        }
    }

}
