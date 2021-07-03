package CommonADT;

import static org.junit.Assert.*;

import org.junit.Test;


public class DateTest {

	@Test
	public void testbetweentime() {
		Date d1=new Date();
		Date d2=new Date();
		d1.setdate(2021, 1, 10);
		d2.setdate(2021, 1, 12);
		assertEquals(2,d1.betweenday(d2));
	}
	
	@Test
	public void testaddday() {
		Date d1=new Date();
		d1.setdate(2021, 1, 1);
		Date d2=new Date(d1.getdata());
		d2.addday(12);
		assertEquals(12,d1.betweenday(d2));
	}
	
	@Test 
	public void testtoString() {
		Date d1=new Date();
		d1.setdate(2021, 1, 1);
		assertEquals("2021/1/1",d1.toString());
	}
}
