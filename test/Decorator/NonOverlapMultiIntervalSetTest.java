package Decorator;

import static org.junit.Assert.*;

import org.junit.Test;

import BasicInterface.MultiIntervalSet;

public class NonOverlapMultiIntervalSetTest {

	@Test
	public void testinsert() {
		MultiIntervalSet<String>temp1=MultiIntervalSet.empty();
		NonOverlapMultiIntervalSet<String>temp=new NonOverlapMultiIntervalSet<>(temp1);
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
