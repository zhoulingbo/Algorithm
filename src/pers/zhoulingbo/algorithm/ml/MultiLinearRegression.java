package pers.zhoulingbo.algorithm.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 多元线性回归
 * 模型: y = w0 + w1*x1 + w2*x2 + ... + wn*xn;
 *
 */
public class MultiLinearRegression
{
    private double[][] data_xs;
    private double[] data_ys;
    private double learn_rate = 0.00000001; // 学习率
    private double[] w;
    private int step = 1000000; // 步数

    public static void main(String[] args)
    {
        MultiLinearRegression lr = new MultiLinearRegression("housing.data");
        lr.gradientDescent();
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

            data_xs = new double[xList.size()][xList.get(0).length];
            data_ys = new double[xList.size()];
            
            xList.toArray(data_xs);
            for (int j=0; j<yList.size(); j++)
                data_ys[j] = yList.get(j);
            w = new double[xList.get(0).length];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
                    System.out.print(w[k] + "  ");
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

    public double predict(double[] x)
    {
        double a = 0.0;
        for (int i=0; i<w.length; i++)
            a += w[i]*x[i];
        return a;
    }

}
