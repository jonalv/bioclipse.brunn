package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.pojos.AbstractOperation;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.results.orcaParser.OrcaParser;
import net.bioclipse.brunn.results.orcaParser.OrcaParser.OrcaPlateRead;
import net.bioclipse.brunn.tests.BaseTest;

import org.eclipse.swt.graphics.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all the methods of the OperationManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *    are able to call the methods
 * 
 * @author jonathan
 */
public class OperationManagerTest extends BaseTest {

	private IOperationManager om;

	public OperationManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		om = (IOperationManager)context.getBean("operationManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		Springcontact.rebuildContext();
	}

	@Test
	public void testAddResultPlateRead() {
		
		OrcaPlateRead plateRead = new OrcaPlateRead("plateRead", "barcodde", 1200);
		plateRead.setValues(new double[8][12]);
		om.addResult( tester, plateRead, plate );
		
		Measurement m = (Measurement)plate.getWells().toArray( 
				           new Well[0])[0].getSampleContainer().getWorkList().getAbstractOperations().toArray(
				        		   new AbstractOperation[0])[0];
		
		System.out.println(m.getResults().size());
		assertTrue( m.getResults().size() != 0 );
	}
	
	@Test
	public void testAddResult() {
		
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, "", workList, instrument, resultType));
		
		double[] result = {2, 3};
		om.addResult(tester, measurement, result);
		
		LazyLoadingSessionHolder.getInstance().clear();
		
		measurement = om.getMeasurement(measurement.getId());

		assertTrue(measurement.getAuditLogs().size() == 2);
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : measurement.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenUpdated = true;
        }
		
		assertTrue(hasBeenUpdated);
		
		//should be only one...
		for( Result r : measurement.getResults() ) {
			for (int i = 0; i < result.length; i++) {
	            assertEquals( result[i], r.getResultValue()[i] );
            }
		}
	}

	@Test
	public void testDeleteUserMeasurement() {
		
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, "", workList, instrument, resultType));
		
		om.delete(tester, measurement);
		
		assertTrue(measurement.getAuditLogs().size() == 2);
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : measurement.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenDeleted);
	}

	@Test
	public void testDeleteUserInstrument() {
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument")); 
		
		om.delete(tester, instrument);
		
		assertTrue(instrument.getAuditLogs().size() == 2);
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : instrument.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenDeleted);
	}

	@Test
	public void testDeleteUserResultType() {

		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2)); 
		
		om.delete(tester, resultType);
		
		assertTrue(resultType.getAuditLogs().size() == 2);
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : resultType.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenDeleted);
	}

	@Test
	public void testEditUserMeasurement() {

		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, "", workList, instrument, resultType));
		measurement.setName("edited");
		
		om.edit(tester, measurement);
		
		assertTrue(measurement.getAuditLogs().size() == 2);
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : measurement.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testEditUserInstrument() {
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument")); 
		instrument.setName("edited");
		
		om.edit(tester, instrument);
		
		assertTrue(instrument.getAuditLogs().size() == 2);
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : instrument.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testEditUserResultType() {
		
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2)); 
		resultType.setName("edited");
		
		om.edit(tester, resultType);
		
		assertTrue(resultType.getAuditLogs().size() == 2);
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : resultType.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue(hasBeenEdited);
	}

	@Test
	public void testGetAllInstruments() {
		
		Instrument instrument1 = om.getInstrument(om.createInstrument(tester, "instrument1"));
		Instrument instrument2 = om.getInstrument(om.createInstrument(tester, "instrument2"));
		
		Collection<Instrument> instruments = om.getAllInstruments();
		
		assertTrue(instruments.contains(instrument1));
		assertTrue(instruments.contains(instrument2));
	}

	@Test
	public void testGetAllMeasurements() {
		
		Measurement measurement1 = 
			om.getMeasurement(om.createMeasurement(tester, "measurement1", workList, instrument, resultType)); 
		Measurement measurement2 = 
			om.getMeasurement(om.createMeasurement(tester, "measurement2", workList, instrument, resultType));
		
		Collection<Measurement> measurements = om.getAllMeasurements();
		
		assertTrue(measurements.contains(measurement1));
		assertTrue(measurements.contains(measurement2));
	}

	@Test
	public void testNewInstrument() {
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		
		assertTrue( instrument.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : instrument.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
	}

	@Test
	public void testNewMeasurement() {
		
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, "measurement", workList, instrument, resultType));
		
		assertTrue( measurement.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : measurement.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
	}

	@Test
	public void testNewResultType() {

		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2)); 
		
		assertTrue( resultType.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : resultType.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
	}

	@Test
	public void testGetAllResultTypes() {
		
		ResultType resultType1 = 
			om.getResultType(om.createResultType(tester, "resultType1", 2));
		ResultType resultType2 = 
			om.getResultType(om.createResultType(tester, "resultType2", 2));
		
		Collection<ResultType> resultTypes = om.getAllResultTypes();
		
		assertTrue(resultTypes.contains(resultType1));
		assertTrue(resultTypes.contains(resultType2));
	}

	@Test
	public void testGetInstrument() {
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		
		session.flush();
		session.clear();
		
		Instrument fetched = om.getInstrument(instrument.getId());
		
		assertEquals(instrument, fetched);
	}

	@Test
	public void testGetResultType() {
		
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2));
		
		session.flush();
		session.clear();
		
		ResultType fetched = om.getResultType(resultType.getId());
		
		assertEquals(resultType, fetched);
	}

	@Test
	public void testGetMeasurement() {

		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, "measurement", workList, instrument, resultType));
		
		session.flush();
		session.clear();
		
		Measurement fetched = om.getMeasurement(measurement.getId());
		
		assertEquals(measurement, fetched);
	}
}
