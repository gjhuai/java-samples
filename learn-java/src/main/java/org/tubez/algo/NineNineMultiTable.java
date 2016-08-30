package org.tubez.algo;

import org.junit.Test;

public class NineNineMultiTable {
	/**
	 * 一个for循环打印九九乘法表
	 */
	@Test
	public void nineNineMultiTable() {
		for (int i = 1, j = 1; j <= 9; i++) {
			System.out.print(i + "*" + j + "=" + i * j + " ");
			if (i == j) {
				i = 0;
				j++;
				System.out.println();
			}
		}
	}
	
}
