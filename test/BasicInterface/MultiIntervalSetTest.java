package BasicInterface;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import CommonADT.MyMap;

public class MultiIntervalSetTest {

	@Test
	public void testlabels() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(20, 30, "A");
		assertTrue(temp.labels().contains("A"));
		assertTrue(temp.labels().contains("B"));
		assertTrue(temp.labels().contains("C"));
	}
	
	@Test
	public void testremove() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(20, 30, "A");
		temp.remove("A");
		assertFalse(temp.remove("D"));
		assertFalse(temp.labels().contains("A"));
		temp.remove("B");
		assertFalse(temp.labels().contains("B"));
		assertTrue(temp.labels().contains("C"));
	}
	
	@Test
	public void testIntervalSet() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(30, 50, "A");
		IntervalSet<Integer> s=temp.intervals("A");
		assertTrue(s.labels().contains(0));
		assertTrue(s.labels().contains(1));
		assertEquals(10,s.start(0));
		assertEquals(20,s.end(0));
		assertEquals(30,s.start(1));
		assertEquals(50,s.end(1));
	}
	
	@Test
	public void testgetmin() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(30, 50, "A");
		assertEquals(10,temp.getmin());
	}
	
	@Test
	public void testgetmax() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(30, 50, "A");
		assertEquals(50,temp.getmax());
	}
	
	@Test
	public void testcontaintime() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(10, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(30, 50, "A");
		assertTrue(temp.containtime(15).contains("A"));
		assertTrue(temp.containtime(15).contains("B"));
		assertFalse(temp.containtime(15).contains("C"));
		assertTrue(temp.containtime(45).contains("A"));
	}
	
	@Test
	public void testarrangestart() {
		MultiIntervalSet<String>temp=MultiIntervalSet.empty();
		temp.insert(10, 20, "A");
		temp.insert(15, 20, "B");
		temp.insert(30, 40, "C");
		temp.insert(40, 50, "A");
		List<MyMap<String>>array=temp.arrangestart();
		assertEquals("A",array.get(0).label);
		assertEquals("B",array.get(1).label);
		assertEquals("C",array.get(2).label);
		assertEquals("A",array.get(3).label);
	}
}
