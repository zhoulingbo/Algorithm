/*
 * 版权所有 (C) 2001-2016 WWW.ZHOULINGBO.CN。保留所有权利。
 * 版本：
 * 修改记录：
 *      1、2016-10-25，zhoulingbo创建。 
 */
package pers.zhoulingbo.algorithm.sort;

public class ExampleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Integer[] b = new Integer[]{7,8,4,2,3,1,6,5,9};
		String s = "sortexampletest";
		Character[] a = new Character[s.length()];
		for (int i=0;i<s.length();i++)
			a[i] = s.toCharArray()[i];
		Shell.sort(a);
		Shell.show(a);
	}

}
