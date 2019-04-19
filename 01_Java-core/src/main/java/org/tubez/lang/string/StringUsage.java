package org.tubez.lang.string;

import static org.junit.Assert.assertEquals;

import java.util.Formatter;
import java.util.Random;
import java.util.Stack;
import java.util.StringTokenizer;

import org.junit.Test;

public class StringUsage {

	/**
	 * 字符倒转
	 */
	@Test
	public void reserveChar(){
		//使用StringBuilder的reverse()方法
		String sh = "FCGDAEB";
		StringBuilder rsh =new StringBuilder(sh).reverse();
		
		assertEquals("BEADGCF", rsh.toString());
	}
	
	/**
	 * 字符串（单词）倒转
	 */
	@Test
	public void reserveString(){
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

		assertEquals("Battle Ends And Down Goes Charles Father ",rs.toString());
	}
	
	
	public void usingStringBuilder() { 
        Random rand = new Random(47); 
        StringBuilder result = new StringBuilder("["); 

        for (int i = 0; i < 25; i++) { 
            result.append(rand.nextInt(100)); 
            result.append(", "); 
        } 
        result.delete(result.length() - 2, result.length()); 
        result.append("]"); 
        System.out.println(result.toString()); 
    }

	/**
	 * 字符串的格式化
	 */
    public void formatOutput() { 
        int x = 5; 
        double y = 5.332542; 

        // The old way: 
        System.out.println("Row 1: [" + x + " " + y + "]"); 
        // The new way: 
        System.out.format("Row 1: [%d %f]\n", x, y); 
        // or 
        System.out.printf("Row 1: [%d %f]\n", x, y); 
    } 

    public void useFormatter() { 
        Formatter f = new Formatter(System.out);

        f.format("%s  is at (%d,%d)\n", "Tom", 3, 2); 
        f.format("%-15s %5s %10s\n", "Item", "Qty", "Price"); 
        f.format("%-15s %5s %10s\n", "----", "---", "-----"); 
        f.format("%-15.15s %5d %10.2f\n", "flowers", 55, 4.56); 
    }

    public void useStringformat() {
        System.out.println(String.format("(t%d, q%d) %s", 3, 5, "right"));
        char u = 'a';

        System.out.println(String.format("s: %s\n", u)); 
        System.out.println(String.format("c: %c\n", u)); 
        System.out.println(String.format("b: %b\n", u)); 
        System.out.println(String.format("h: %h\n", u)); 
    }
    
}
