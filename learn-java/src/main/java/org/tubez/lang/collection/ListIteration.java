package org.tubez.lang.collection;

//: holding/ListIteration.lang


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

/**
 * ListIterator 只能用于各种List类的访问。可以双向移动，可以得到前后元素的index； 可以使用set()
 * 方法改变元素的值；listIterator(index) 创建指向index的ListIterator; 可以删除元素
 * 
 * @author guanjianghuai
 * 
 */
public class ListIteration {
	@Test
	public void listIteration() {
		StringBuilder sb = new StringBuilder();

		List<String> pets = Arrays.asList("Rat", "Manx", "Cymric", "Mutt",
				"Pug", "Cymric", "Pug", "Manx");
		ListIterator<String> it = pets.listIterator();
		
		// Forwards:
		while (it.hasNext())
			sb.append(it.next()).append(", ")
			  .append(it.nextIndex()).append(", ")
			  .append(it.previousIndex()).append("; ");
		assertEquals("Rat, 1, 0; Manx, 2, 1; Cymric, 3, 2; Mutt, 4, 3; Pug, 5, 4; Cymric, 6, 5; Pug, 7, 6; Manx, 8, 7; ",
				sb.toString());

		sb.delete(0, sb.length());
		
		// Backwards:
		while (it.hasPrevious())
			sb.append(it.previous() + " ");
		
		// [Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug, Manx]
		assertEquals("Manx Pug Cymric Pug Mutt Cymric Manx Rat ",sb.toString());

		it = pets.listIterator(3);
		while (it.hasNext()) {
			it.next();
			it.set("Rat");
		}
		assertEquals("[Rat, Manx, Cymric, Rat, Rat, Rat, Rat, Rat]",pets.toString());
	}

	@Test
	public void removeElement() {

		// create an object of ArrayList
		List<String> aList = new ArrayList<String>();
		// Add elements to ArrayList object
		Collections.addAll(aList,"1","2","3","4","5");

		// Get an object of ListIterator using listIterator() method
		ListIterator<String> listIterator = aList.listIterator();
		
		for (;listIterator.hasNext();){
			if ("2".equals(listIterator.next()))
				listIterator.remove();
		}
		
		assertEquals("[1, 3, 4, 5]",aList.toString());
	}
} 