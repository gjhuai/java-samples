package org.tubez.lang.array;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 数组的复制方法现在看来至少有四个思路：
 * 
 * 1 使用循环结构 这种方法最灵活。唯一不足的地方可能就是代码较多
 * 2 使用Object类的clone() 方法， 这种方法最简单，得到原数组的一个副本。灵活形也最差。效率最差，尤其是在数组元素很大或者复制对象数组时。
 * 3 使用Systems.arraycopy()。这种方法被告之速度最快，并且灵活性也较好，可以指定原数组名称、以及元素的开始位置、复制的元素的个数，目标数组名称、目标数组的位置。
 * 4 Arrarys类的copyOf()方法与copyOfRange()方法可实现对数组的复制（内部使用Systems.arraycopy()实现）。
 * 
 * 定义一个数组int[] a={3,1,4,2,5}; int[] b=a;
 * 数组b只是对数组a的又一个引用，即浅拷贝。如果改变数组b中元素的值，其实是改变了数组a的元素的值
 * 
 * 要实现深度复制，可以用clone或者System.arraycopy
 * 
 * @author guanjianghuai
 */
public class ArrayCopy {

	/**
	 * Array 的 clone() 实现的是 deep copy
	 */
	@Test
	public void cloneCopy(){
		int[] a={3,1,4,2,5};
		int[] b=a.clone();
		b[0]=10;
		
		assertEquals(10, b[0]);
		assertEquals(3, a[0]);
	}
	
	/**
	 * clone和System.arraycopy都是对一维数组的深度复制。clone并不能直接作用于二维数组.
	 *  
	 * java中没有二维数组的概念，只有数组的数组。
	 * 所以二维数组a中存储的实际上是两个一维数组的引用。
	 * 当调用clone函数时，是对这两个引用进行了复制。
	 */
	@Test
	public void cloneCopy2(){
		int[][] a={{3,1,4,2,5},{4,2}};
		int[][] b=a.clone();
		b[0][0]=10;
		
		assertEquals(10, b[0][0]);
		assertEquals(10, a[0][0]);
		
		assertTrue(a[0]==b[0]);
	}
	
	/**
	 * 用clone对二维数组进行复制，要在每一维上调用clone函数
	 */
	@Test
	public void coneCopy3(){
		int[][] a={{3,1,4,2,5},{4,2}};
		int[][] b=new int[a.length][];
		
		for(int i=0;i<a.length;i++){
			b[i]=a[i].clone();
		}
		
		b[0][0]=10;
		
		assertEquals(10, b[0][0]);
		assertEquals(3, a[0][0]);
		
		assertFalse(a[0]==b[0]);
	}
	
	/**
	 * 使用java.util.Arrays.copyOf() 和 copyOfRange() 方法复制（内部实现是调用System.arraycopy）
	 */
	@Test
	public void coneCopy4(){
		int[] a={3,1,4,2,5};
		int[] b= new int[a.length];
		
		b = Arrays.copyOf(a,a.length);
		
		b[0]=10;
		
		assertEquals(10, b[0]);
		assertEquals(3, a[0]);
	}
}
