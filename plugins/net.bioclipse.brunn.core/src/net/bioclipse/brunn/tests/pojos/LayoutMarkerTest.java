package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;

import net.bioclipse.brunn.pojos.LayoutMarker;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.User;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class LayoutMarkerTest {

	@Test
	public void testDeepCopy() {
		
		User tester = new User("tester");
		LayoutWell layoutWell = new LayoutWell();
		LayoutMarker layoutMarker = new LayoutMarker(tester, "layoutemarker", layoutWell);
		
		LayoutMarker layoutMarker2 = layoutMarker.deepCopy();
		
		assertEquals(layoutMarker, layoutMarker2);
		assertNotSame(layoutMarker, layoutMarker2);
		
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		LayoutWell layoutWell = new LayoutWell();
		LayoutMarker layoutMarker = new LayoutMarker(tester, "layoutMarker", layoutWell);
		
		assertTrue(layoutMarker.getLayoutWell() == layoutWell);
		assertTrue(layoutWell.getLayoutMarkers().contains(layoutMarker));
	}
}
