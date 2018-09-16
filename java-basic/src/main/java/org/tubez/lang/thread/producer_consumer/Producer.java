package org.tubez.lang.thread.producer_consumer;

/**
 * 生产者
 * 
 * @author guanjianghuai
 *
 */
class Producer implements Runnable {

	ProductBox productbox = null;

	public Producer(ProductBox productbox) {
		this.productbox = productbox;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			Product p = new Product(i);

			productbox.push(p);
			System.out.println("produce:" + p);

			try {
				Thread.sleep((int) (Math.random() * 200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}