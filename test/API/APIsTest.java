package API;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import BasicInterface.IntervalSet;
import BasicInterface.MultiIntervalSet;
import BasicInterfaceImpl.CommonIntervalSet;
import BasicInterfaceImpl.CommonMultiIntervalSet;
import CommonADT.MyMap;

public class APIsTest {

	@Test
	public void testSimilarity() {
		APIs<String> api=new APIs<>();
		MultiIntervalSet<String>s1=new CommonMultiIntervalSet<>();
		MultiIntervalSet<String>s2=new CommonMultiIntervalSet<>();
		s1.insert(0, 5, "A");
		s1.insert(20, 25, "A");
		s1.insert(10, 20, "B");
		s1.insert(25, 30, "B");
		s2.insert(20, 35, "A");
		s2.insert(10, 20, "B");
		s2.insert(0, 5, "C");
		assertTrue((api.Similarity(s1, s2)-15.0/35)<1e-10);
	}
	
	@Test
	public void testcalcConflictRatio() {
		IntervalSet<String>s1=new CommonIntervalSet<>();
		APIs<String>api=new APIs<>();
		s1.insert(10, 20, "A");
		s1.insert(10, 20, "B");
		s1.insert(10, 20, "D");
		s1.insert(30, 40, "C");
		assertTrue((api.calcConflictRatio(s1,40)-10.0/40)<1e-10);
		MultiIntervalSet<String>s2=new CommonMultiIntervalSet<>();
		s2.insert(10, 20, "A");
		s2.insert(10, 20, "B");
		s2.insert(10, 20, "D");
		s2.insert(30, 40, "C");
		s2.insert(35, 40, "A");
		assertTrue((api.calcConflictRatio(s2,40)-15.0/40)<1e-10);
	}
	
	@Test
	public void testcalcFreeTimeRatio() {
		IntervalSet<String>s1=new CommonIntervalSet<>();
		APIs<String>api=new APIs<>();
		s1.insert(10, 20, "A");
		s1.insert(10, 20, "B");
		s1.insert(10, 20, "D");
		s1.insert(30, 40, "C");
		assertTrue((api.calcFreeTimeRatio(s1,40)-20.0/40)<1e-10);
		MultiIntervalSet<String>s2=new CommonMultiIntervalSet<>();
		s2.insert(5, 20, "A");
		s2.insert(10, 20, "B");
		s2.insert(10, 20, "D");
		s2.insert(30, 40, "C");
		s2.insert(35, 45, "A");
		assertTrue((api.calcFreeTimeRatio(s2,45)-15.0/45)<1e-10);
	}
	
	@Test
	public void testgetfreetime() {
		IntervalSet<String>s1=new CommonIntervalSet<>();
		APIs<String>api=new APIs<>();
		s1.insert(10, 20, "A");
		s1.insert(10, 20, "B");
		s1.insert(10, 20, "D");
		s1.insert(30, 40, "C");
		List<MyMap<String>>array=api.getfreetime(s1.arrangestart(), 0, 40);
		assertTrue(0==array.get(0).start);
		assertTrue(10==array.get(0).end);
		assertTrue(20==array.get(1).start);
		assertTrue(30==array.get(1).end);
	}
}
