package Decorator;
import static org.junit.Assert.*;

import org.junit.Test;

import BasicInterface.IntervalSet;

public class NonOverlapIntervalSetTest {

	@Test
	public void testinsert() {
		IntervalSet<String>temp1=IntervalSet.empty();
		NonOverlapIntervalSet<String>temp=new NonOverlapIntervalSet<>(temp1);
		temp.insert(10, 20, "A");
		assertTrue(temp.labels().contains("A"));
		temp.insert(10, 20, "B");
		assertFalse(temp.labels().contains("B"));
		temp.insert(15, 30, "B");
		assertFalse(temp.labels().contains("B"));
		temp.insert(30, 40, "C");
		assertTrue(temp.labels().contains("C"));
	}
}
