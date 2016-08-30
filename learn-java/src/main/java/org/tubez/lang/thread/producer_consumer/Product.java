package org.tubez.lang.thread.producer_consumer;

/**
 * 产品对象
 * 
 * @author guanjianghuai
 *
 */
public class Product {
	  int id;

	  public Product(int id) {
	    this.id = id;
	  }
	  
	  public String toString() {
	    return "Product:" + id;
	  }
}
