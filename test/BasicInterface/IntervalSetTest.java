package BasicInterface;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import CommonADT.MyMap;

public class IntervalSetTest {

	@Test
	public void testlabels() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertTrue(temp.labels().contains("A"));
		assertTrue(temp.labels().contains("B"));
		assertTrue(temp.labels().contains("C"));
	}
	
	@Test
	public void testremove() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.remove("A");
		assertFalse(temp.labels().contains("A"));
		temp.remove("C");
		assertFalse(temp.labels().contains("C"));
		assertTrue(temp.labels().contains("B"));
	}
	
	@Test
	public void testgetvalue() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertEquals(10,temp.start("A"));
		assertEquals(20,temp.end("A"));
		assertEquals(30,temp.start("C"));
		assertEquals(40,temp.end("C"));
	}
	
	@Test
	public void testgetmin() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertEquals(10,temp.getmin());
	}
	
	@Test
	public void testgetmax() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertEquals(40,temp.getmax());
	}
	
	@Test
	public void testcontaintime() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		assertTrue(temp.containtime(15).contains("A"));
		assertTrue(temp.containtime(15).contains("B"));
		assertFalse(temp.containtime(15).contains("C"));
	}
	
	@Test
	public void testarrangestart() {
		IntervalSet<String>temp=IntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(30, 40, "B");
		temp.insert(20, 40, "C");
		List<MyMap<String>>array=temp.arrangestart();
		assertEquals("A",array.get(0).label);
		assertEquals("B",array.get(2).label);
		assertEquals("C",array.get(1).label);
	}
}
