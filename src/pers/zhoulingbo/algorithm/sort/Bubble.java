package pers.zhoulingbo.algorithm.sort;

public class Bubble {

	public static <T> void sort(Comparable<T>[] a){
		for(int i=0;i<a.length;i++) {
			for (int j=i+1;j<a.length;j++)
				if (less(a[j], a[i]))
					exch(a, i, j);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean less(Comparable v, Comparable w){
		return v.compareTo(w) < 0;
	}
	
	public static <T> void exch(Comparable<T>[] a, int i, int j){
		Comparable<T> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static <T> void show(Comparable<T>[] a) {
		for(int i=0;i<a.length;i++)
			System.out.print(a[i]);
		
		System.out.println();
	}
}
