package pers.zhoulingbo.algorithm.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogisticRegression
{

    private double[][] data_xs;     // 训练数据集
    private double[] data_ys;       // 训练结果集
    private double[][] test_xs;     // 测试数据集
    private double[] test_ys;       // 测试结果集

    private double[] w;
    private double threshold = 0.5;     // 阈值，情况不同可设定不同的值
    private double learn_rate = 0.3;    // 学习率
    private int step = 40000;           // 步数

    public static void main(String[] args)
    {
        LogisticRegression lr = new LogisticRegression("sample.data");
        lr.train();
        lr.predict();
    }

    public LogisticRegression(String path)
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
                    for (int i = 1; i < item.length; i++)
                    {
                        ditem[i] = Double.parseDouble(item[i - 1]);
                    }
                    xList.add(ditem);
                    yList.add(Double.parseDouble(item[item.length - 1]));
                }
                line = bufferReader.readLine();
            }

            xList = normalization(xList);

            int size = xList.size() * 4 / 5; // 划分训练与测试数据
            data_xs = new double[size][xList.get(0).length];
            data_ys = new double[size];
            test_xs = new double[xList.size() - size][xList.get(0).length];
            test_ys = new double[xList.size() - size];

            for (int j = 0; j < xList.size(); j++)
            {
                if (j < size)
                {
                    data_xs[j] = xList.get(j);
                    data_ys[j] = yList.get(j);
                }
                else
                {
                    test_xs[j - size] = xList.get(j);
                    test_ys[j - size] = yList.get(j);
                }
            }
            w = new double[xList.get(0).length];
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void train()
    {
        gradientDescent();
    }

    /**
     * 归一化处理
     * @param list
     */
    private List<double[]> normalization(List<double[]> list)
    {
        double mins[] = new double[list.get(0).length];
        double maxs[] = new double[list.get(0).length];

        for (int i = 0; i < list.get(0).length; i++)
        {
            double min = list.get(0)[i];
            double max = list.get(0)[i];
            for (int j = 0; j < list.size(); j++)
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

        for (int i = 0; i < mins.length; i++)
        {
            if (mins[i] < -3 || maxs[i] > 3)
            {
                for (int j = 0; j < list.size(); j++)
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
    protected void gradientDescent()
    {
        double[] t = Arrays.copyOf(w, w.length);
        for (int i = 0; i < step; i++)
        {
            for (int j = 0; j < w.length; j++)
            {
                w[j] = t[j] - learn_rate * partialDerivative(data_xs, data_ys, j, t);
            }

            t = Arrays.copyOf(w, w.length);

            if (i % 10 == 0)
            {
                System.out.println("step:" + i + "  loss:" + loss(data_xs, data_ys, t));
                for (int k = 0; k < w.length; k++)
                    System.out.print(w[k] + ",");
                System.out.println();
            }
        }
    }

    /**
     * 求偏导
     * @return
     */
    private double partialDerivative(double[][] x_s, double[] y_s, int index, double[] as)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            double b = sumOfProduct(x_s[i], as);
            double h = sigmod(b);
            h = (h - y_s[i])*x_s[i][index];
            a += h;
        }
        return a;
    }

    /**
     * 向量相乘Y=WX
     * @param x
     * @param w
     * @return
     */
    private double sumOfProduct(double[] x, double[] w)
    {
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
        {
            sum += x[i] * w[i];
        }
        return sum;
    }

    private double sigmod(double z)
    {
        double h = 1.0 / (1.0 + Math.exp(-z));
        return h;
    }

    /**
     * 损失函数
     * @param x_s
     * @param y_s
     * @param as
     * @return
     */
    private double loss(double[][] x_s, double[] y_s, double[] as)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            double b = sumOfProduct(x_s[i], as);
            double h = sigmod(b);
            a += y_s[i] * Math.log(h) + (1 - y_s[i]) * Math.log(1 - h);
        }

        a = - a / x_s.length;
        return a;
    }

    /**
     * 预测
     * @return
     */
    public void predict()
    {
        for (int k = 0; k < test_xs.length; k++)
        {
            double sum = sumOfProduct(test_xs[k], w);
            double h = sigmod(sum);
            double p = 0;
            if (h >= threshold)
                p = 1;
            System.out.println("predict:" + p + " test_y:" + test_ys[k]);
        }
    }

}
