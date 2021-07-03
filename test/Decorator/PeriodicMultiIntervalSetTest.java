package Decorator;

import static org.junit.Assert.*;

import org.junit.Test;

import BasicInterfaceImpl.CommonMultiIntervalSet;

public class PeriodicMultiIntervalSetTest {

	@Test
	public void testinsert() {
		PeriodicMultiIntervalSet<String>temp=new PeriodicMultiIntervalSet<>(new CommonMultiIntervalSet<>());
		temp.setloopperiod(7);
		temp.setlooptimes(2);
		temp.insert(0, 10, "A");
		assertEquals(2,temp.arrangestart().size());
		assertEquals(1,temp.labels().size());
		assertTrue(temp.arrangestart().get(0).start==0);
		assertTrue(temp.arrangestart().get(1).start==7);
		assertTrue(temp.arrangestart().get(0).end==10);
		assertTrue(temp.arrangestart().get(1).end==17);
	}

}
