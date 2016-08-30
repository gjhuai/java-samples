package org.tubez.lang.collection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * LinkedList实现了Queue接口，支持队列行为，因此LinkedList可以用作Queue的一种实现。
 * 通过将LinkedList向上转型为Queue。
 * 
 * @author guanjianghuai
 * 
 */
public class QueueDemo {
	public static void printQ(Queue queue) {
		// peek和element都是返回队头，peek在队列为空时返回null
		while (queue.peek() != null) {
			// remove和poll都是移除并返回队头，poll在队列为空时返回null
			System.out.print(queue.remove() + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Queue<Integer> queue = new LinkedList<Integer>();
		Random rand = new Random(47);

		for (int i = 0; i < 10; i++) {
			queue.offer(rand.nextInt(i + 10));// 插入队尾或返回false
		}
		printQ(queue);
		Queue<Character> qc = new LinkedList<Character>();

		for (char c : "Brontosaurus".toCharArray()) {
			qc.offer(c);
		}
		printQ(qc);
	}
}
