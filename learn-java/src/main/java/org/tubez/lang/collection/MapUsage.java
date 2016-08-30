package org.tubez.lang.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

public class MapUsage {
	@Test
	public void statistics() {
		Random rand = new Random(47);
		Map<Integer, Integer> m = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10000; i++) {
			// Produce a number between 0 and 20:
			int r = rand.nextInt(20);
			Integer freq = m.get(r);
			m.put(r, freq == null ? 1 : freq + 1);
		}
		System.out.println(m);
	}
	
	
}
