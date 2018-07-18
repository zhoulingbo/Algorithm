/*
 * 版权所有 (C) 2018 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2018-7-18，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.sort;

public class Heapsort
{

    public static void main(String[] args)
    {
        int[] array = new int[] { 6, 5, 3, 1, 8, 7, 2, 4 };
        
        Heapsort heap = new Heapsort();
        heap.sort(array);
        
        for (int i=0; i<array.length; i++)
            System.out.print(array[i]);
    }

    /**
     * 堆排序
     * @param data
     */
    public void sort(int[] data)
    {
        buildMaxHeap(data);
        
        for (int i = data.length - 1; i >= 0; i--)
        {
            swap(data, 0, i);
            heapify(data, i, 0);
        }
    }
    
    /**
     * 建立最大堆
     */
    public void buildMaxHeap(int[] data)
    {
        int startIndex = ((data.length - 1) - 1) / 2;
        for (int i = startIndex; i >= 0; i--)
        {
            heapify(data, data.length, i);
        }
    }

    /**
     * 堆调整
     * @param data
     * @param length
     * @param parent
     */
    private void heapify(int[] data, int length, int parent)
    {
        int left = getLeftChild(parent);
        int right = getRightChild(parent);

        int largest = parent;
        if (left < length && data[left] > data[parent])
        {
            largest = left;
        }

        if (right < length && data[right] > data[largest])
        {
            largest = right;
        }

        if (largest != parent)
        {
            swap(data, largest, parent);
            heapify(data, length, largest);
        }
    }

    /**
     * 交换数组元素
     * @param data
     * @param i
     * @param j
     */
    public void swap(int[] data, int i, int j)
    {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * 获取左子节点
     * @param parent
     * @return
     */
    private int getLeftChild(int parent)
    {
        return 2 * parent + 1;
    }

    /**
     * 获取右子节点
     * @param parent
     * @return
     */
    private int getRightChild(int parent)
    {
        return 2 * parent + 2;
    }

}
