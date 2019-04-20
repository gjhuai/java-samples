package org.tubez.lang.thread.producer_consumer;

/**
 * 用Java多线程实现生产者和消费者问题
 * 
 * @author guanjianghuai
 *
 */
public class ProducerConsumer {
	  public static void main(String[] args) {    
		    ProductBox pb = new ProductBox();
		    Producer p = new Producer(pb);
		    Consumer c = new Consumer(pb);
		    
		    Thread pThread = new Thread(p);
		    Thread cThread = new Thread(c);
		 
		    pThread.setPriority(Thread.MAX_PRIORITY);
		    
		    pThread.start();
		    cThread.start();
		  }

}
