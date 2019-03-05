package pers.zhoulingbo.algorithm.ml;

import java.util.HashMap;
import java.util.Map;

public class C4_5 extends ID3
{

    public C4_5(String path)
    {
        super(path);
    }

    /**
     * 信息增益率
     * @param data
     * @param index
     * @return
     */
    public double gainRatio(String[][] data, int index)
    {
        return gain(data, index)/splitInf(data, index);
    }

    /**
     * 分裂信息熵
     * @param data
     * @param index
     * @return
     */
    public double splitInf(String[][] data, int index)
    {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length; i++)
        {
            String[] item = data[i];
            String attrValue = item[index];     // 属性值
            if (!map.containsKey(attrValue))
            {
                map.put(attrValue, 1);
            }
            else
            {
                int count = map.get(attrValue) + 1;
                map.put(attrValue, count);
            }
        }

        return calcEntropy(map);
    }
}
