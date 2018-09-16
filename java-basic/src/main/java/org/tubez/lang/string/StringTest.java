package org.tubez.lang.string;

import org.junit.Assert;
import org.junit.Test;

public class StringTest {

	@Test
	public void test() {
		String greeting = "Hello";
		int n = greeting.length(); // is 5.
		
		char first = greeting.charAt(0); // first is 'H'
		char last = greeting.charAt(4); // last is 'o'
		
		int index = greeting.offsetByCodePoints(0, 3);
		Assert.assertEquals(3, index);
		int cp = greeting.codePointAt(index);
		Assert.assertEquals(108, cp);
		
		Assert.assertEquals(false, Character.isSupplementaryCodePoint(cp));
	}

}
