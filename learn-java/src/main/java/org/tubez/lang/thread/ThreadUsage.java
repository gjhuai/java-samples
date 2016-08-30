package org.tubez.lang.thread;

/**
 * 设计4个线程，其中两个线程每次对j增加1，另外两个线程对j每次减少1。
 * 
 * @author guanjianghuai
 *
 */
public class ThreadUsage {
	  private int j;
	  public static void main(String args[]) {
		  ThreadUsage tt = new ThreadUsage();

	    for (int i = 0; i < 2; i++) {
	       tt.inc();
	       tt.dec();
	    }
	  } 

	  private void inc() {    
	    new Thread(){
	      public void run() {
	        for (int i = 0; i < 100; i++) {
	          synchronized(this){ j++; }
	          System.out.println(Thread.currentThread().getName() + "-inc:" + j);
	        }
	      }      
	    }.start();
	    
	  }

	  private void dec() {
	    new Thread(){
	      public void run() {
	        for (int i = 0; i < 100; i++) {
	          synchronized(this){ j--; }
	          System.out.println(Thread.currentThread().getName() + "-dec:" + j);
	        }
	      }      
	    }.start();
	  }
}
