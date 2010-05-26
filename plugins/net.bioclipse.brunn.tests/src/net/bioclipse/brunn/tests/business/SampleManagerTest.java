package net.bioclipse.brunn.tests.business;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.TestConstants;

/**
 * Tests all the methods of the SampleManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted happends
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *    are able to call the methods
 * 
 * @author jonathan
 */
public class SampleManagerTest extends BaseTest {

	public SampleManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

//	public void testAddNewCellSampleToContainer() {
//		
//		ISampleManager sm = (ISampleManager)context.getBean("sampleManager");
//		IOriginManager om = (IOriginManager)context.getBean("originManager");
//		
//		CellOrigin cellOrigin = om.getCellOrigin(om.createCellOrigin(tester, "test", cellTypes));
//		
//		assertEquals(0, sampleContainer.getSamples().size());
//		
//		sm.addNewCellSampleToContainer(
//					tester, "test", cellOrigin, new Timestamp(234), sampleContainer
//		);
//		
//		boolean hasBeenCreated = false;
//		
//		CellSample cellSample = (CellSample)sampleContainer.getSamples().toArray()[0];
//		assertTrue( cellSample.getAuditLogs().size() == 1 );
//		
//		for (AuditLog auditLog : cellSample.getAuditLogs()) {
//	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
//	        	hasBeenCreated = true;
//        }
//		
//		assertTrue(hasBeenCreated);
//		assertTrue(sampleContainer.getSamples().size() == 1);
//	}

//	public void testAddNewDrugSampleToContainer() throws FileNotFoundException, IOException {
//			
//		ISampleManager sm = (ISampleManager)context.getBean("sampleManager");
//		IOriginManager om = (IOriginManager)context.getBean("originManager");
//		
//		DrugOrigin drugOrigin = om.getDrugOrigin(om.createDrugOrigin(tester, "test", new FileInputStream(TestConstants.getTestMolFile()), 23.0, compounds));
//		
//		assertEquals(0, sampleContainer.getSamples().size());
//		
//		sm.addNewDrugSampleToContainer(
//					tester, "test", drugOrigin, 23.0, sampleContainer, null
//		);
//		
//		DrugSample drugSample = (DrugSample)sampleContainer.getSamples().toArray()[0];
//		
//		boolean hasBeenCreated = false;
//		
//		assertTrue( drugSample.getAuditLogs().size() == 1 );
//		
//		for (AuditLog auditLog : drugSample.getAuditLogs()) {
//	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
//	        	hasBeenCreated = true;
//        }
//		
//		assertTrue(hasBeenCreated);
//		assertTrue(sampleContainer.getSamples().size() == 1);
//	}

	@Test
	public void testGetSampleContainer() {
		
		ISampleManager sm = (ISampleManager)context.getBean("sampleManager");
		
		session.flush();
		session.clear();
		
		SampleContainer fetched = sm.getSampleContainer(sampleContainer.getId());
		
		assertEquals(  sampleContainer, fetched );
		assertNotSame( sampleContainer, fetched );
	}
}
