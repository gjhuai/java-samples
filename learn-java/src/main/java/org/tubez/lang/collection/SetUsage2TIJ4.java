package org.tubez.lang.collection;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;
import org.tubez.lang.util.TextFile;

public class SetUsage2TIJ4 {
	/**
	 *  
	 */
	@Test
	public void setOfInteger() {
	    Random rand = new Random(47);
	    Set<Integer> intset = new HashSet<Integer>();
	    for(int i = 0; i < 10000; i++)
	      intset.add(rand.nextInt(30));
	    System.out.println(intset);
	    // output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 16, 19, 18, 21, 20, 23, 22, 25, 24, 27, 26, 29, 28]
	}

	/**
	 *  
	 */
	@Test
	public void sortedSetOfInteger() {
	    Random rand = new Random(47);
	    SortedSet<Integer> intset = new TreeSet<Integer>();
	    for(int i = 0; i < 10000; i++)
	      intset.add(rand.nextInt(30));
	    assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]",
	    		intset.toString());
	}
	
	/**
	 *  
	 */
	@Test
	public void setOperations() {
		Set<String> set1 = new HashSet<String>();
		Collections.addAll(set1, "A B C D E F G H I J K L".split(" "));
		set1.add("M");
		System.out.println("H: " + set1.contains("H"));
		System.out.println("N: " + set1.contains("N"));
		Set<String> set2 = new HashSet<String>();
		Collections.addAll(set2, "H I J K L".split(" "));
		System.out.println("set2 in set1: " + set1.containsAll(set2));
		set1.remove("H");
		System.out.println("set1: " + set1);
		System.out.println("set2 in set1: " + set1.containsAll(set2));
		set1.removeAll(set2);
		System.out.println("set2 removed from set1: " + set1);
		Collections.addAll(set1, "X Y Z".split(" "));
		System.out.println("'X Y Z' added to set1: " + set1);
	}
	
	@Test
	public void uniqueWords() {
		Set<String> words = new TreeSet<String>(new TextFile(
				"SetUsage.lang", "\\W+"));
		System.out.println(words);
	}
}
