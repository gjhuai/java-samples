package org.tubez.algo;

/**
 * 在JAVA中对数组[K,q,a,F,j,A,f]排序，要求排序结果为：[A,a,F,f,K,q].
 */
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class CharCaseSort {

	@Test
	public void testCharSort() {
		char[] array = new char[] { 'K', 'q', 'a', 'F', 'j', 'A', 'f' };
		doSort(array);
		Assert.assertEquals("[A, a, F, f, j, K, q]",Arrays.toString(array));
	}

  /**
   *  排序,采用最简单的冒泡排序
   * @param array
   * @return
   */
  public char[] doSort(char[] array) {
    char ch;
    for (int i = 0; i < array.length; ++i) {
      for (int j = 0; j < array.length - i - 1; ++j) {
        if (this.compare(array[j], array[j + 1]) > 0) {
          ch = array[j];
          array[j] = array[j + 1];
          array[j + 1] = ch;
        }
      }
    }
    return array;
  }

  // 比较 a 和 b[忽略大小写,同时同一个大小写字母,大写的小于小写的]
  private int compare(char a, char b) {
    char aa = (char) (a | 32);
    char bb = (char) (b | 32);

    if (aa == bb) {
      return a < b ? -1 : 1;
    } else if (aa < bb) {
      return -1;
    } else {
      return 1;
    }
  }
}