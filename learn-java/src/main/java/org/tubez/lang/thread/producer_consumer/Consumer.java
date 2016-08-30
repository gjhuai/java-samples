package org.tubez.lang.thread.producer_consumer;

/**
 * 消费者
 * 
 * @author guanjianghuai
 *
 */

class Consumer implements Runnable {

	ProductBox productbox = null;

	public Consumer(ProductBox productbox) {
		super();
		this.productbox = productbox;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Product p = productbox.pop();

			System.out.println("consume:" + p);

			try {
				Thread.sleep((int) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}