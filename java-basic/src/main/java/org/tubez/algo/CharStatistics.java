package org.tubez.algo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

public class CharStatistics {
	/**
	 * 给你一个字符串，包含了空格等标点符号，要你计算出出现次数最多的字母和该字母出现的次数。
	 * 
	 * 借助Java 的 Map 来完成：
	 * 
	 * 1、分离出char， 将char及其出现的次数存入Map中
	 * 2、从Map中找出出现次数最多的字母
	 */
	@Test
	public void statUseMap() {
		// 1、分离出char， 将char及其出现的次数存入Map中
		char c;
		String str = "A public house which was recently bought "
				+ "by Mr. Ian Thompson is up for sale. ";
		Map<Character, Integer> map = new HashMap<Character, Integer>();

		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(c = str.charAt(i))) {
				Integer freq = map.get(c);
				map.put(c, freq == null ? 1 : freq + 1);
			}
		}

		assertEquals("{f=1, g=1, e=4, b=3, A=1, c=3, a=3, n=3, M=1, o=5, l=3, m=1, I=1, h=5, i=3, w=2, T=1, u=4, t=2, s=5, r=3, p=3, y=2}",
				map.toString());
		//System.out.println(map.toString());
		
		
		// 2、从Map中找出出现次数最多的字母
		int maxValue = 0;
		Character maxKey = null;
		
		for (Map.Entry<Character, Integer> kv : map.entrySet()){
			if (kv.getValue() > maxValue) {
				maxValue = kv.getValue();
				maxKey = kv.getKey();
			}
		}
		
		assertEquals('o',maxKey.charValue());
		assertEquals(5,maxValue);
	}
	
	/**
	 * 上面这个例子采用Java 的数据结构 Map来完成的，下面看看，不使用Java数据结构的例子：
	 */
	@Test
	public void stat2() {
		String str = "A public house which was recently bought "
				+ "by Mr. Ian Thompson is up for sale. ";

		int max = 0;
		char maxChar = 0;
		int[] its = new int[26 * 2 + 6];

		char[] chars = str.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 65 && chars[i] <= 90)
					|| (chars[i] >= 97 && chars[i] <= 122)) {
				its[chars[i] - 65]++; // 统计出现次数
				// 记录出现最多的字符
				if (its[chars[i] - 65] > max) {
					max = its[chars[i] - 65];
					maxChar = chars[i];
				}
			}
		}
		System.out.println("出现最的字符是:" + maxChar + "   出现了" + max + "次");
	}
	
	
	/**
	 * 一个数组，“支配者”是在数组中出现频率超过一半的整数；
	 * 例如[3,4,3,2,-1,3,3,3]数值“3”出现过5次，5除以8大于0.5
	 * 所以数值“3”是一个支配者； 而在这个数组中的支配者出现在数组下标[0,2,4,6,7] 
	 * 写一个函数，在给定的整数数组中找出支配者所在的任意一个数组下标， 如果一个数组中没有支配者返回-1；
	 */
	@Test
	public void chooseMost() {
		Integer arr[] = { 2, 4, 3, 5, 3, 3, 2, 4, 3, 3, 4, 3 };
		List<Integer> list = Arrays.asList(arr);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int a : list) {
			Integer freq = map.get(a);
			map.put(a, freq == null ? 1 : freq + 1);
		}

		Integer maxPos = -1;
		for (Map.Entry<Integer, Integer> kv : map.entrySet()) {
			if ((float) kv.getValue() / list.size() >= 0.5) {
				maxPos = list.indexOf(kv.getKey());
				break;
			}
		}
		
		assertEquals(2, maxPos.intValue());		// 支配者的小标为 2

	}

}