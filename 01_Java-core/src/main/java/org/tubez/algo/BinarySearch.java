package org.tubez.algo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.tubez.Logger;
import org.tubez.LoggerFactory;

public class BinarySearch {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 写一个方法，用二分查找法判断任意整数在任意整数数组里面是否存在，
	 * 若存在就返回它在数组中的索引位置，不存在返回-1
	 * 
	 * 二分查找特定整数在整型数组中的位置(递归)
	 * 
	 * @param dataset
	 * @param data
	 * @param beginIndex
	 * @param endIndex
	 */
	public int binarySearch_recursion(int[] dataset, int data, int beginIndex, int endIndex) {
		int midIndex = (beginIndex + endIndex) / 2;
		if (data < dataset[beginIndex] || data > dataset[endIndex]
				|| beginIndex > endIndex)
			return -1;
		if (data < dataset[midIndex]) {
			return binarySearch_recursion(dataset, data, beginIndex, midIndex - 1);
		} else if (data > dataset[midIndex]) {
			return binarySearch_recursion(dataset, data, midIndex + 1, endIndex);
		} else {
			return midIndex;
		}
	}

	/**
	 * 二分查找特定整数在整型数组中的位置(非递归)
	 * 
	 * @param dataset
	 * @param data
	 */
	public int binarySearch_loop(int[] dataset, int data, int beginIndex, int endIndex) {
		int midIndex = -1;
		if (data < dataset[beginIndex] || data > dataset[endIndex]
				|| beginIndex > endIndex)
			return -1;
		while (beginIndex <= endIndex) {
			midIndex = (beginIndex + endIndex) / 2;
			if (data < dataset[midIndex]) {
				endIndex = midIndex - 1;
			} else if (data > dataset[midIndex]) {
				beginIndex = midIndex + 1;
			} else {
				return midIndex;
			}
		}
		return -1;
	}
	
	@Test
	public void binarySearch_recursion(){
		int num = 3;
		int[] dataset = new int[]{1,3,2,5,4,8};
		int found = binarySearch_recursion(dataset, num, 0, dataset.length-1);
		assertEquals(-1, found);

		dataset = new int[]{1,3,4,5,6,7,8};
		found = binarySearch_recursion(dataset, num, 0, dataset.length-1);
		assertEquals(1, found);
	}
	
	@Test
	public void binarySearch_loop(){
		int num = 3;
		int[] dataset = new int[]{1,3,2,5,4,8};
		int found = binarySearch_loop(dataset, num, 0, dataset.length-1);
		assertEquals(-1, found);
		
		dataset = new int[]{1,3,4,5,6,7,8};
		found = binarySearch_loop(dataset, num, 0, dataset.length-1);
		assertEquals(1, found);
	}
}
