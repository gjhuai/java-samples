package org.tubez.algo;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class StringCaseSort {
  @Test
  public void testCompare(){
	    String[] str = { "dad", "bOod", "bada", "Admin", "Good", "aete", "cc",
	            "Ko", "Beta", "Could" };
        Arrays.sort(str, new Sort2Comparator());
        Assert.assertEquals("[Admin, aete, Beta, bada, bOod, Could, cc, dad, Good, Ko]", Arrays.toString(str));
  }
}


/**
 * 1.对字符串进行排序，用任意一种编程语言来实现，不能使用现有的类，
 * 在排序中， 字符串“Bc”，“Ad”，“aC”,“Hello”，“Xman”，“little”，“During”,“day” 
 * 能够排序成
 * “Ad”，"aC"，“Bc”，“During”，“day”，“Hello”，“little”，“Hello”，
 * 也就是说，在排序的过程并不是传统的按照字符串排序，在排序中还需要将小写字母一并排序，
 * 也就是说a字符串要在B或b之前。
 */

/** 
 * 关于对象的比较,有两种办法,
 * 第一种:实现Comparable接口的compareTo方法,
 * 第二种:创建一个实现Comparator接口的对象,调用Arrays.sort()方法
 */
class Sort2Comparator implements Comparator<String> {
  public int compare(String s1, String s2) {
    char[] arr1 = s1.toCharArray();
    char[] arr2 = s2.toCharArray();

    // 取得循环次数，为两个字符串的长度的最小值
    int iterate = arr1.length < arr2.length ? arr1.length : arr2.length;
    /*
     * 循环字符串，对长度(短字符串的长度)相同的部分做比较. 
     * 如果ret的值为零,则不一定能分出大小,要看他们的长度.
     */
    for (int i = 0; i < iterate; i++) {
      int ret = compareChar(arr1[i], arr2[i]);
      if (ret != 0)
        return ret;
    }
    /*
     * 如果长度(短字符串的长度)相同部分分不出大小，要看他们的长度. 
     * 长度相同,则相等.长度小的排在前面,长度大的排在后面.
     */
    return arr2.length - arr1.length;
  }

  /**
   *  比较 a 和 b。
   *  1、忽略大小写,
   *  2、同一个字母的大小写,大写的小于小写的
   *  
   * @return	a==b 返回0， a<b 返回-1， a>b 返回 1
   */
  private int compareChar(char a, char b) {
    char aa = (char) (a | 32);		//转为小写
    char bb = (char) (b | 32);		//转为小写
    if (aa == bb) {
      return a < b ? -1 : 1;
    } else if (aa < bb) {
      return -1;
    } else {
      return 1;
    }
  }
}