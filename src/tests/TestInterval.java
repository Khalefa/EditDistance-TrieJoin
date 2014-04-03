package tests;
import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

import edu.alexu.util.Interval;


public class TestInterval {

	@Test
	public void testInterval() {
		Interval i=new Interval();
		Interval j=new Interval();
		Interval x=new Interval();
		i.add(1);
		i.add(2);
		
		j.add(0);
		j.add(4);
		x.add(3);
		x.add(4);
		
		Assert.assertTrue(i.intersect(i, 0));
		Assert.assertTrue(j.intersect(j, 0));
		Assert.assertTrue(x.intersect(x, 0));
				
		Assert.assertTrue(i.intersect(j, 0));
		Assert.assertTrue(j.intersect(i, 0));
		Assert.assertTrue(x.intersect(j, 0));
		Assert.assertFalse(i.intersect(x, 0));
		Assert.assertTrue(i.intersect(x, 1));
		Assert.assertTrue(x.intersect(i, 1));
		
		Assert.assertTrue(i.intersect(x, 2));
		Assert.assertTrue(x.intersect(i, 2));
		
		i.add(3);
		Assert.assertTrue(i.intersect(x));
	}

}
