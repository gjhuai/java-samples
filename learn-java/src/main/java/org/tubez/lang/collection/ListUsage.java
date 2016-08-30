package org.tubez.lang.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class ListUsage {
	/**
	 * List的常见用法，背下来就行。P223
	 */
	@Test
	public void listFeatures() {
		Random rand = new Random(47);
		List<String> pets = new ArrayList<String>();
		Collections.addAll(pets, "Rat", "Manx", "Cymric", "Mutt", "Pug",
				"Cymric", "Pug");

		assertEquals("[Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug]",
				pets.toString());

		pets.add("Hamster"); // Automatically resizes
		assertEquals("[Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug, Hamster]",
				pets.toString());
		assertTrue(pets.contains("Hamster"));

		pets.remove("Hamster"); // Remove by object
		String p = pets.get(2);
		assertEquals("Cymric", p);
		assertEquals(2, pets.indexOf(p));

		assertEquals(2, pets.indexOf("Cymric"));
		assertTrue(pets.remove("Cymric"));

		// Must be the exact object:
		assertTrue(pets.remove(p));
		assertEquals("[Rat, Manx, Mutt, Pug, Pug]", pets.toString());

		pets.add(3, "Mouse"); // Insert at an index
		assertEquals("[Rat, Manx, Mutt, Mouse, Pug, Pug]", pets.toString());

		List<String> sub = pets.subList(1, 4);
		assertEquals("[Manx, Mutt, Mouse]", sub.toString());
		assertTrue(pets.containsAll(sub));

		Collections.sort(sub); // In-place sort
		assertEquals("[Manx, Mouse, Mutt]", sub.toString());
		assertEquals("[Rat, Manx, Mouse, Mutt, Pug, Pug]", pets.toString());
		// Order is not important in containsAll():
		assertTrue(pets.containsAll(sub));

		Collections.shuffle(sub, rand); // Mix it up
		assertEquals("[Mouse, Manx, Mutt]", sub.toString());
		assertEquals("[Rat, Mouse, Manx, Mutt, Pug, Pug]", pets.toString());
		assertTrue(pets.containsAll(sub));

		List<String> copy = new ArrayList<String>(pets);
		sub = Arrays.asList(pets.get(1), pets.get(4));
		assertEquals("[Mouse, Pug]", sub.toString());

		copy.retainAll(sub);
		assertEquals("[Mouse, Pug, Pug]", copy.toString());
		assertEquals("[Rat, Mouse, Manx, Mutt, Pug, Pug]", pets.toString());

		copy = new ArrayList<String>(pets); // Get a fresh copy
		copy.remove(2); // Remove by index
		assertEquals("[Rat, Mouse, Mutt, Pug, Pug]", copy.toString());

		copy.removeAll(sub); // Only removes exact objects
		assertEquals("[Rat, Mutt]", copy.toString());

		copy.set(1, "Mouse"); // Replace an element
		assertEquals("[Rat, Mouse]", copy.toString());

		copy.addAll(2, sub); // Insert a list in the middle
		assertEquals("[Rat, Mouse, Mouse, Pug]", copy.toString());

		pets.clear(); // Remove all elements
		assertEquals("[]", pets.toString());
		assertTrue(pets.isEmpty());

		Collections.addAll(pets, "Manx", "Cymric", "Rat", "EgyptianMau");
		Object[] o = pets.toArray();
		assertEquals("EgyptianMau", o[3]);
		String[] pa = pets.toArray(new String[0]);
		assertEquals("EgyptianMau", pa[3]);
	}
	
	/**
	 * 这个程序是关于 Arrays.asList,Collections.addAll 填充集合的例子，在书上P220。第二个例子说明了集合的元素的子父类关系
	 */
	@Test
	public void addingGroups() {
		Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(
				1, 2, 3, 4, 5)); // asList 接受一个数组或可变参数列表，将其转换为一个List对象
		Integer[] moreInts = { 6, 7, 8, 9, 10 };

		// addAll 接受一个数组或可变参数列表，将元素添加到Collection中
		collection.addAll(Arrays.asList(moreInts));
		// Runs significantly faster, but you can't
		// construct a Collection this way: Collections.addAll
		// 方法运行快得多，而且方便，所以它是首选
		Collections.addAll(collection, 11, 12, 13, 14, 15);
		Collections.addAll(collection, moreInts);
		// Produces a list "backed by" an array:
		List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);

		list.set(1, 99); // OK -- modify an element
		// list.add(21); // Runtime error because the //
		// Arrays.asList产生的list不能改变尺寸，所以add和delete都会出错
		// underlying array cannot be resized.
	}
	
	/**
	 * 在P229页
	 */
	@Test
	public void linkedListFeatures() {
		LinkedList<String> pets = new LinkedList<String>();
		Collections.addAll(pets, "Rat", "Manx", "Cymric", "Mutt", "Pug");

		// Identical:
		assertEquals("Rat", pets.getFirst());
		assertEquals("Rat", pets.element());
		
		// Only differs in empty-list behavior:
		assertEquals("Rat", pets.peek());
		
		// Identical; remove and return the first element:
		assertEquals("Rat", pets.remove());
		assertEquals("Manx", pets.removeFirst());
		
		// Only differs in empty-list behavior:
		assertEquals("Cymric", pets.poll());
		assertEquals("[Mutt, Pug]", pets.toString());
		
		pets.addFirst("Rat");
		assertEquals("[Rat, Mutt, Pug]", pets.toString());
		
		pets.offer("Cymric");
		assertEquals("[Rat, Mutt, Pug, Cymric]", pets.toString());
		
		pets.add("Pug");
		assertEquals("[Rat, Mutt, Pug, Cymric, Pug]", pets.toString());
		
		pets.addLast("Hamster");
		assertEquals("[Rat, Mutt, Pug, Cymric, Pug, Hamster]", pets.toString());
		assertEquals("Hamster", pets.removeLast());
	}

}
