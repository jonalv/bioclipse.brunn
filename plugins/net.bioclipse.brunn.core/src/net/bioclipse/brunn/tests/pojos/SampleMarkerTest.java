package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;

import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import static org.junit.Assert.*;

public class SampleMarkerTest {

	public SampleMarkerTest() {
	}

	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester"); 
		Well well = new Well(tester, "", 4, 'a', null);
		
		SampleMarker sampleMarker = new SampleMarker(tester, "", null, well);
		
		assertTrue(well.getSampleMarkers().contains(sampleMarker));
		assertSame(sampleMarker.getWell(), well);
		
	}
}
