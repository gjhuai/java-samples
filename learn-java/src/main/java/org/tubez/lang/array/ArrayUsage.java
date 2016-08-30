package org.tubez.lang.array;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * java中见一个数组赋值给另外一个数组,真正做的只是复制一个引用.
 * 
 * 在java 5 以后,可以用可变参数列表代替数组作为参数. 0个参数传递给可变参数列表是可行的.
 * 
 * 对象数组保存的是引用,而基本类型数组保存的是基本类型的值.
 * 
 * @author guanjianghuai
 * 
 */
public class ArrayUsage {
	
	/**
	 * 尽管不能创建实际的持有泛型的数组对象,但是你可以创建非泛型数组,然后将其转型. 
	 */
	@Test
	public void test1(){
		List<String>[] ls;
	    // List<String>[] ls1 = new ArrayList<String>[10];  编译出错
		List[] la = new List[10];
		ls = (List<String>[])la;     //"Unchecked" warning
		ls[0] = new ArrayList<String>();
	}

	/**
	 * 自动增加数组的长度，实现动态分配
	 * 
	 * System.arraycopy拷贝数组比for循环快得多,但它不能Auto-Boxing,所以两个数组必须具有相同的类型.
	 */
	@Test
	public void autoIncreasing(){
		  final int STEP_SIZE = 5;
		  
		  Integer[] nums = new Integer[]{0,1,2,3,4,5,6,7,8,9}; 
		  
		  Integer[] tmp= new Integer[nums.length+STEP_SIZE];
		  System.arraycopy(nums,0,tmp,0,nums.length);
		  
		  nums = tmp;
		  
		  Assert.assertEquals(15, nums.length);
		  Assert.assertEquals(Integer.valueOf(9), nums[9]);
		  Assert.assertEquals(null, nums[10]);
		  nums[10] = 10;
		  Assert.assertEquals(Integer.valueOf(10), nums[10]);
	}
	
	/**
	 * 求数组的最大和最小值
	 */
	@Test public void max(){
		  int[] arr = new int[] { 6, 3, 2, 7, 9, 4, 5, 8, 0 };
		  
		  int max = arr[0];
		  for (int j = 1; j < arr.length; j++)
		    if (max < arr[j])
		      max = arr[j];
		  
		  Assert.assertEquals(9, max);
	}

}
