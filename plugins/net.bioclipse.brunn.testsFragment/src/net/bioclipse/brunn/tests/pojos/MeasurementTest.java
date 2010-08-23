package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;
import static org.junit.Assert.*;

import junit.framework.TestCase;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.WorkList;

public class MeasurementTest {
	
	@Test
	public void testDeepDelete() {
		
		User           tester         = new User("tester");
		Instrument     instrument     = new Instrument(tester, "instrument");
		ResultType     resultType     = new ResultType(tester, "resultType", 1);
		Measurement    measurement    = new Measurement(tester, "measurement", new WorkList(), instrument, resultType);
		
		double[] data = {0.5};
		measurement.getResults().add(new Result(tester, "result", data, 0));
		
		assertFalse(measurement.isDeleted());
		assertFalse(measurement.getInstrument().isDeleted());
		assertFalse(measurement.getResultType().isDeleted());
		for (Result result : measurement.getResults()) {
	        assertFalse(result.isDeleted());
        }
		
		measurement.delete();
		
		assertTrue(measurement.isDeleted());
		assertFalse(measurement.getInstrument().isDeleted());
		assertFalse(measurement.getResultType().isDeleted());
		for (Result result : measurement.getResults()) {
	        assertTrue(result.isDeleted());
        }
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		User           tester         = new User("tester");
		Instrument     instrument     = new Instrument(tester, "instrument");
		ResultType     resultType     = new ResultType(tester, "resultType", 1);
		WorkList       workList       = new WorkList();
		Measurement    measurement    = new Measurement(tester, "measurement", workList, instrument, resultType);
		
		assertTrue(measurement.getInstrument() == instrument);
		assertTrue(instrument.getMeasurements().contains(measurement));
		
		assertTrue(measurement.getWorkList() == workList);
		assertTrue(workList.getAbstractOperations().contains(measurement));
	}
	
	@Test
	public void testDeepCopy() {
		User           tester         = new User("tester");
		Instrument     instrument     = new Instrument(tester, "instrument");
		ResultType     resultType     = new ResultType(tester, "resultType", 1);
		WorkList       workList       = new WorkList();
		Measurement    measurement    = new Measurement(tester, "measurement", workList, instrument, resultType);
		
		Measurement copy = measurement.deepCopy();
		
		assertEquals(measurement, copy);
		assertNotSame(measurement, copy);
	}
}
