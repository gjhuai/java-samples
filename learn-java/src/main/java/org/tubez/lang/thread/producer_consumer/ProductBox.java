package org.tubez.lang.thread.producer_consumer;

/**
 * 产品盒对象
 * 
 * @author guanjianghuai
 *
 */
public class ProductBox {

	Product[] productbox = new Product[6];
	int index = 0;

	public ProductBox() {
	}

	public synchronized void push(Product p) {
		while (index == productbox.length) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notify();
		productbox[index] = p;
		index++;
	}

	public synchronized Product pop() {
		while (index == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.notify();
		index--;
		return productbox[index];

	}
}
