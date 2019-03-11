package pers.zhoulingbo.algorithm.ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
    private int maxGainRatioIndex(String[][] data)
    {
        double max = 0;
        int index = 0;
        for (int i = 0; i < data[0].length - 1; i++)
        {
            double a = gainRatio(data, i);
            if (max < a)
            {
                max = a;
                index = i;
            }
        }
        return index;
    }
    
    protected Node buildDecisionTree(String[][] data)
    {
        int index = maxGainRatioIndex(data);
        Node node = new Node(labels[index]);

        Set<String> set = new HashSet<>();
        for (int j = 0; j < data.length; j++)
        {
            String[] item = data[j];
            String key = item[index];
            set.add(key);
        }

        for (String label : set)
        {
            node.labelList.add(label);

            List<String[]> subList = new ArrayList<>(); // 根据标签分组数据
            for (int i = 0; i < data.length; i++)
            {
                String[] item = data[i];
                if (label.equals(item[index]))
                    subList.add(item);
            }

            String[][] subData = new String[subList.size()][data[0].length];
            for (int i = 0; i < subList.size(); i++)
                subData[i] = subList.get(i);

            boolean isLeaf = true;
            String policy = null;
            for (int k = 0; k < subList.size(); k++)
            {
                if (policy == null)
                {
                    policy = subList.get(k)[data[0].length - 1];
                    continue;
                }

                if (!policy.equals(subList.get(k)[data[0].length - 1]))
                {// 存在不相同的结果值，则非叶节点
                    isLeaf = false;
                    break;
                }
            }

            if (isLeaf)
            {
                Node leaf = new Node(policy);
                node.childList.add(leaf);
            }
            else
            {
                Node child = buildDecisionTree(subData);
                node.childList.add(child);
            }
        }

        return node;
    
    }
}
