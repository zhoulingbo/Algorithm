package pers.zhoulingbo.algorithm.ml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * 决策树ID3算法
 * 
 * @author zhoulingbo 2019-2-28 新建与整理
 */
public class ID3
{

    String[] labels;
    String[][] data; // 数据集,最后一列为决策值
    double dataEnt; // 信息熵
    String[] decisions; // 决策值集合

    public ID3(String path)
    {
        init(path);
        this.dataEnt = entropy(data);
    }

    /**
     * 初始化
     * @param path
     */
    private void init(String path)
    {
        try (FileReader input = new FileReader(path))
        {
            List<String[]> list = new ArrayList<>();

            BufferedReader bufferReader = new BufferedReader(input);
            String line = bufferReader.readLine();
            this.labels = line.split(","); // 第一行为标签

            Set<String> set = new HashSet<>();
            while (line != null)
            {
                line = bufferReader.readLine();
                if (line != null)
                {
                    String[] item = line.split(",");
                    list.add(item);
                    set.add(item[item.length - 1]);
                }
            }

            this.data = new String[list.size()][this.labels.length];
            list.toArray(this.data);

            decisions = new String[set.size()];
            int index = 0;
            for (String s : set)
                decisions[index++] = s;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 计算数据集的信息熵
     * @param data
     * @return
     */
    private double entropy(String[][] data)
    {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < data.length; i++)
        {
            String[] item = data[i];
            String key = item[item.length - 1];
            if (!map.containsKey(key))
            {
                map.put(key, 1);
            }
            else
            {
                int count = map.get(key) + 1;
                map.put(key, count);
            }
        }

        return calcEntropy(map);
    }

    /**
     * ind标签属性的条件熵
     * @param data
     * @param index
     * @return
     */
    protected double conditionEntropy(String[][] data, int index)
    {
        Map<String, Map<String, Integer>> map = getAttributes(data, index);

        double amount = 0.0;
        for (String key : map.keySet())
        {
            Map<String, Integer> cmap = map.get(key);
            int count = 0;
            for (String ckey : cmap.keySet())
            {
                count += cmap.get(ckey);
            }
            amount += calcEntropy(cmap) * count / data.length;
        }

        return amount;
    }

    /**
     * 获取属性分类
     * @param data
     * @param index
     * @return
     */
    private Map<String, Map<String, Integer>> getAttributes(String[][] data, int index)
    {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        for (int j = 0; j < data.length; j++)
        {
            String[] item = data[j];
            String key = item[index];
            String type = item[item.length - 1];
            if (!map.containsKey(key))
            {
                Map<String, Integer> cmap = new HashMap<>();
                for (String str : decisions)
                {
                    if (str.equals(type))
                        cmap.put(str, 1);
                    else
                        cmap.put(str, 0);
                }
                map.put(key, cmap);
            }
            else
            {
                Map<String, Integer> cmap = map.get(key);
                int count = cmap.get(type) + 1;
                cmap.put(type, count);
            }
        }

        return map;
    }

    /**
     * 计算条件熵
     * @param map
     * @return
     */
    protected double calcEntropy(Map<String, Integer> map)
    {
        int total = 0;
        for (String key : map.keySet())
        {
            total += map.get(key);
        }

        double amount = 0.0;
        for (String key : map.keySet())
        {
            int count = map.get(key);
            double p = (double) count / total;
            if (p == 0 || p == 1)
                continue;
            amount -= p * (Math.log(p) / Math.log(2));
        }

        return amount;
    }

    /**
     * 信息增益 = 信息熵 - 条件熵
     * @param data
     * @param index
     * @return
     */
    protected double gain(String[][] data, int index)
    {
        return dataEnt - conditionEntropy(data, index);
    }

    /**
     * 获取最大信息增益索引值
     * @return
     */
    private int maxGainIndex(String[][] data)
    {
        double max = 0;
        int index = 0;
        for (int i = 0; i < data[0].length - 1; i++)
        {
            double a = gain(data, i);
            if (max < a)
            {
                max = a;
                index = i;
            }
        }
        return index;
    }

    public Node buildDecisionTree()
    {
        return buildDecisionTree(data);
    }

    /**
     * 构建决策树
     * @param data
     * @return
     */
    protected Node buildDecisionTree(String[][] data)
    {
        int index = maxGainIndex(data);
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

    /**
     * 批量决策
     * @param data
     * @return
     */
    public String[] makeDecisions(String[][] data)
    {
        String[] ds = new String[data.length];
        for (int i = 0; i < data.length; i++)
        {
            ds[i] = makeDecision(data[i]);
        }
        return ds;
    }

    /**
     * 做决策
     * @param data
     * @return
     */
    public String makeDecision(String[] data)
    {
        Node node = buildDecisionTree();
        while (node != null)
        {
            if (node.labelList == null || node.labelList.size() == 0)
                break;

            String label = node.getName();
            int index = -1;
            for (int i = 0; i < labels.length; i++)
            {
                if (label.equals(labels[i]))
                {
                    index = i;
                    break;
                }
            }

            if (index == -1)
                return null;

            String labelValue = data[index];
            index = -1;
            for (int j = 0; j < node.labelList.size(); j++)
            {
                if (labelValue.equals(node.labelList.get(j)))
                {
                    index = j;
                    break;
                }
            }

            if (index == -1)
                return null;

            node = node.childList.get(index);
        }

        return node.getName();
    }

}
