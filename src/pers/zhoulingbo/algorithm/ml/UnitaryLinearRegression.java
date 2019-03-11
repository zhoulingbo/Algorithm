package pers.zhoulingbo.algorithm.ml;

/**
 * 
 * 一元线性回归 模型：y = w0+w1*x;
 * 
 */
public class UnitaryLinearRegression
{

    private double[] xs = new double[] { 58, 59, 60, 61, 62 };
    private double[] ys = new double[] { 115, 117, 120, 123, 126 };
    private double learn_rate = 0.00003; // 学习率
    private double w0 = 0.0;
    private double w1 = 0.0;
    private int step = 100; // 步数

    public static void main(String[] args)
    {
        UnitaryLinearRegression lr = new UnitaryLinearRegression();
        lr.gradientDescent();

        System.out.println(lr.getW0() + " " + lr.getW1());
    }

    public UnitaryLinearRegression()
    {

    }

    /**
     * 梯度下降算法
     */
    public void gradientDescent()
    {
        double t0 = w0;
        double t1 = w1;
        for (int i = 0; i < step; i++)
        {
            w0 = t0 - learn_rate * partialDerivative(xs, ys, 0, t0, t1);
            w1 = t1 - learn_rate * partialDerivative(xs, ys, 1, t0, t1);

            t0 = w0;
            t1 = w1;

            System.out.println("step:" + i + "  loss:" + meanSquareError(xs, ys, t0, t1));
        }
    }

    /**
     * 求偏导
     * @return
     */
    public double partialDerivative(double[] x_s, double[] y_s, double index, double a1, double a2)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            if (index > 0)
                a += (a1 + a2 * x_s[i] - y_s[i]) * x_s[i];
            else
                a += a1 + a2 * x_s[i] - y_s[i];
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
    public double meanSquareError(double[] x_s, double[] y_s, double a1, double a2)
    {
        double a = 0.0;
        for (int i = 0; i < x_s.length; i++)
        {
            double s = a1 + a2 * x_s[i] - y_s[i];
            s *= s;
            a += s;
        }

        a = a / (2 * x_s.length);
        return a;
    }

    public double predict(double x)
    {
        return w0 + w1 * x;
    }

    public double getW0()
    {
        return w0;
    }

    public double getW1()
    {
        return w1;
    }

}
