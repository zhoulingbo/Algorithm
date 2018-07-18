/*
 * 版权所有 (C) 2001-2016 周凌波。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2016-10-25，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.sort;

public class Shell {

	public static <T> void sort(Comparable<T>[] a) 
	{
		int n = a.length;
		int h = 1;
		while (h < n/3)
		{
			h = 3*h + 1;
		}
		
		while (h >= 1)
		{
			for(int i=h; i<n; i++) 
			{
				for (int j=i; j>=h && less(a[j], a[j-h]); j-=h) 
				{
					exch(a, j, j-h);
				}
			}
			
			h = h/3;
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
			System.out.print(a[i]+" ");
		
		System.out.println();
	}
	
	public static <T> boolean isSorted(Comparable<T>[] a) 
	{
		for(int i=1;i<a.length;i++)
			if (less(i, i-1))
				return false;
		return true;
	}
}
