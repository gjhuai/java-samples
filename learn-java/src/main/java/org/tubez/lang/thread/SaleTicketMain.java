package org.tubez.lang.thread;

/**
 * 用Java多线程实现火车站售票问题
 * 
 * @author guanjianghuai
 * 
 */
public class SaleTicketMain {
	public static void main(String[] args) {
		SaleTicket st = new SaleTicket();

		new Thread(st, "一号窗口").start();
		new Thread(st, "二号窗口").start();
		new Thread(st, "三号窗口").start();
		new Thread(st, "四号窗口").start();

	}
}

class SaleTicket implements Runnable {
	int tickets = 100;

	public void run() {
		while (tickets > 0) {
			synchronized (this) {
				if (tickets > 0) {
					System.out.println(Thread.currentThread().getName() + "卖第"
							+ (100 - tickets + 1) + "张票");
					tickets--;
				}
			}
		}
	}

}