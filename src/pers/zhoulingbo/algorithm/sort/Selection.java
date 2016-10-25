/*
 * 版权所有 (C) 2001-2016 WWW.ZHOULINGBO.CN。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2016-10-25，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.sort;

public class Selection 
{

	public static <T> void sort(Comparable<T>[] a) 
	{
		for(int i=0;i<a.length;i++) 
		{
			int min = i;
			for (int j=i+1;j<a.length;j++)
			{
				if (less(a[j], a[min]))
					min = j;
			}
			
			exch(a, i, min);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean less(Comparable v, Comparable w) 
	{
		return v.compareTo(w) < 0;
	}
	
	public static <T> void exch(Comparable<T>[] a, int i, int j) 
	{
		Comparable<T> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static <T> void show(Comparable<T>[] a) 
	{
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]);
		
		System.out.println();
	}
}
