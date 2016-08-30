package org.tubez.lang.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

public class SetUsage {
	/**
	 *  List转换成Set
	 */
	@Test
	public void list2set() {
	  List<String> list = new ArrayList<String>();
	  Collections.addAll(list, "one", "two", "three", "four", "two", "four");
	  Set<String> set = new HashSet<String>();
	  set.addAll(list);

	  Assert.assertEquals("[two, one, three, four]", set.toString());
	}

	/**
	 *  Set转换为List
	 */
	@Test
	public void set2list() {
	  Set<String> set = new HashSet<String>();
	  Collections.addAll(set, "one", "two", "three", "four");
	  List<String> list = new ArrayList<String>();
	  list.addAll(set);

	  Assert.assertEquals("[two, one, three, four]", list.toString());
	}
	
	/**
	 *  剔除数组的重复元素
	 */
	@Test
	public void uniqueAndDups() {
	  String[] words = new String[] { "one", "one", "two", "3", "3", "4" };
	  Set<String> uniques = new HashSet<String>();
	  Set<String> dups = new HashSet<String>();

	  for (String a : words) {
	    if (!uniques.add(a)) {
	      dups.add(a);
	    }
	  }

	  uniques.removeAll(dups);

	  Assert.assertEquals("[two, 4]", uniques.toString());
	  Assert.assertEquals("[3, one]", dups.toString());
	}
}
