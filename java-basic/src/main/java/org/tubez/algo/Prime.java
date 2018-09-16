package org.tubez.algo;

import static org.junit.Assert.assertFalse;

/**
 * 质数即大于1的一个自然数，这个数可以被1和自身整除，如算出20之内的质数，它们有2，3，5，7，11，13，17，19这样的数字。这道题也是面试过程中笔试常问的一道题。
这道题的其目的在于：
1. 看笔试者的数学还记不记得
2. 看笔试者平时的算法
 * @author gjh
 *
 */
public class Prime {

	public static void main(String[] args) {
		int num = 10;
		assertFalse(isPrimes(num));
	}

	/**
	 * 判断任意一个整数是否素数
	 * 
	 * 质数又称素数。指在一个大于1的自然数中，除了1和此整数自身外，不能被其他自然数整除的数。
	 * 
	 * @paramn
	 * @return boolean
	 */
	public static boolean isPrimes(int n) {
		// Math.sqrt 计算正平方根
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
}
