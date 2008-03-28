package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;

import junit.framework.TestCase;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.WorkList;
import static org.junit.Assert.*;

public class InstrumentTest {

	@Test
	public void testDeepDelete(){

		User tester = new User("tester");
		Instrument instrument1 = new Instrument(tester, "instrument1");
		
		assertFalse( instrument1.isDeleted() );
		
		instrument1.delete();
		
		assertTrue( instrument1.isDeleted() );
		
		Instrument instrument2 = new Instrument(tester, "instrument2");
		new Measurement(tester, "measurement", new WorkList(), instrument2, new ResultType());
		
		try{
			instrument2.delete();
			fail("should have thrown IllegalStateException");
		}
		catch(IllegalStateException e){
			
		}
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		Instrument instrument = new Instrument(tester, "instrument1");
		Measurement measureMent = new Measurement(tester, "", new WorkList(), instrument, new ResultType());
			
		assertTrue(measureMent.getInstrument() == instrument);
		assertTrue(instrument.getMeasurements().contains(measureMent));
		
	}
}
