package org.tubez.algo;

public class NumberGame {
	
	/**
	 * 获得任意一个整数的阶乘 n!
	 * 
	 * 递归实现
	 */
	public int factorial1(int n) {
		// 递归
		if (n == 1) {
			return 1;
		}
		return n * factorial1(n - 1);
	}
	
	/**
	 * 获得任意一个整数的阶乘 n!
	 * 
	 * 非递归实现
	 */
	public int factorial2(int n) {
		int multi = 1;
		for (int i = 2; i <= n; i++) {
			multi *= i;
		}
		return multi;
	}
}
