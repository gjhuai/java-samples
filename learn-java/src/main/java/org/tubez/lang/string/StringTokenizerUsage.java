package org.tubez.lang.string;

import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

import org.junit.Assert;
import org.junit.Test;

public class StringTokenizerUsage {

	@Test
	public void split1(){
		// 默认不带参数时，以空格作为分割符来分割字符串
		StringTokenizer st = new StringTokenizer("Hello World of Java");
		String[] result = new String[4];
		for (int i=0;st.hasMoreTokens();) {
			result[i++] = st.nextToken();
		}
		Assert.assertEquals("[Hello, World, of, Java]",Arrays.toString(result));
	}
	
	@Test
	public void split2(){
		//指定多种分割符
		StringTokenizer st = new StringTokenizer("Hello, World|of|Java !", ", |");
		
		String[] result = new String[5];
		for (int i=0;st.hasMoreTokens();) {
			result[i++] = st.nextToken();
		}
		Assert.assertEquals("[Hello, World, of, Java, !]",Arrays.toString(result));
	}
	
	@Test
	public void split3(){
		//第三个参数设置为true时，表示连分隔符也作为分隔结果的一部分
		StringTokenizer st = new StringTokenizer("Hello, World|of|Java !", ", |", true);
		
		String[] result = new String[10];
		for (int i=0;st.hasMoreTokens();) {
			result[i++] = st.nextToken();
		}
		//System.out.print(Arrays.toString(result));
		Assert.assertEquals("[Hello, ,,  , World, |, of, |, Java,  , !]",Arrays.toString(result));
	}
	
	/**
	 * 字符或字符串（单词）倒转
	 */
	@Test
	public void charAndWordReserve(){
		//字符倒转
		String sh = "FCGDAEB";
		//使用StringBuilder的reverse()方法
		StringBuilder rsh = new StringBuilder(sh).reverse();
		Assert.assertEquals("BEADGCF", rsh.toString());
		
		// 单词倒转
		// 使用栈Stack的push和pop方法
		String s = "Father Charles Goes Down And Ends Battle";

		// Put it in the stack frontwards（LinkedList作为栈的实现更好）
		// 可用 LinkedList<String> myStack = new LinkedList<String>();替代
		Stack<String> myStack = new Stack<String>();
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
		  myStack.push(st.nextToken());
		}

		StringBuilder rs = new StringBuilder(s.length());

		// Print the stack backwards
		while (!myStack.empty()) { // 可用LinkedList.isEmpty()替代
		  rs.append(myStack.pop());
		  rs.append(' ');
		}

		System.out.println(rs);
	}
}
