package net.bioclipse.brunn.tests.business;

import java.sql.Timestamp;
import java.util.Collection;

import org.apache.commons.dbcp.BasicDataSource;
import org.eclipse.swt.graphics.Point;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.genericDAO.IWellDAO;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;

/**
 * Tests all the methods of the PlateManager. 
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
public class PlateManagerTest extends BaseTest {

	private IPlateManager pm;
	
	public PlateManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		pm = (IPlateManager) context.getBean("plateManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetAllPlates() {
		
		Plate plate1 = pm.getPlate(pm.createPlate(tester, "plate1", "1234", folder, masterPlate, cellOrigin, null));
		Plate plate2 = pm.getPlate(pm.createPlate(tester, "plate2", "1235", folder, masterPlate, cellOrigin, null));
		
		Collection<Plate> plates = pm.getAllPlates();
		
		assertTrue(plates.contains(plate1));
		assertTrue(plates.contains(plate2));
	}
	
	@Test
	public void testGetAllPlatesNotDeleted() {
		
		Plate plate1 = pm.getPlate(pm.createPlate(tester, "plate1", "1234", folder, masterPlate, cellOrigin, null));
		Plate plate2 = pm.getPlate(pm.createPlate(tester, "plate2", "1235", folder, masterPlate, cellOrigin, null));
		Plate plate3 = pm.getPlate(pm.createPlate(tester, "deleted", "234", folder, masterPlate, cellOrigin, null));
		plate3.delete();
		pm.edit(tester, plate3);
		
		Collection<Plate> plates = pm.getAllPlatesNotDeleted();
		
		assertTrue(  plates.contains(plate1) );
		assertTrue(  plates.contains(plate2) );
		assertFalse( plates.contains(plate3) );
	}

	@Test
	public void testGetPlate() {
		
		Plate plate = pm.getPlate(pm.createPlate(tester, "plate", "123", folder, masterPlate, cellOrigin, null));
		
		session.flush();
		session.clear();
		
		Plate fetched = pm.getPlate(plate.getId());
		
		assertEquals(plate, fetched);
	}

	@Test
	public void testCreatePlate() {
		
		int platesBefore = masterPlate.getPlatesLeft();
		
		Plate plate = pm.getPlate(pm.createPlate(tester, "plate", "1234", folder, masterPlate, cellOrigin, null));
		
		assertTrue( plate.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}
		
		assertTrue(hasBeenCreated);	
		
		session.flush();
		session.clear();
		
		assertTrue(folder.getObjects().contains(plate));
		
		Folder loadedFolder = fm.getFolder(folder.getId());
		
		assertEquals(folder, loadedFolder);
		assertEquals( platesBefore, masterPlate.getPlatesLeft() + 1);
	}

	@Test
	public void testCreateMasterPlate() {
		
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1));
		
		assertTrue( masterPlate.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : masterPlate.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}
		
		assertTrue(hasBeenCreated);
		
		session.flush();
		session.clear();
		
		assertTrue(masterPlates.getObjects().contains(masterPlate));
		
		Folder loadedFolder = fm.getUniqueFolder(masterPlates.getId());
		
		assertEquals(masterPlates, loadedFolder);
	}

	@Test
	public void testCreatePlateFunctionUserStringAbstractPlateStringDoubleDouble() {

		Plate plate = pm.getPlate(pm.createPlate(tester, "plate", "1234", folder, masterPlate, cellOrigin, null));
		
		pm.createPlateFunction(tester, 
		                       "function", 
		                       plate, 
		                       "", 
		                       3, 
		                       5
		);

		assertTrue( plate.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}

		assertTrue(hasBeenUpdated);	
	}

	@Test
	public void testCreatePlateFunctionUserStringAbstractPlateString() {

		Plate plate = pm.getPlate(pm.createPlate(tester, "plate", "1234", folder, masterPlate, cellOrigin, null));
		
		pm.createPlateFunction(tester, 
		                       "function", 
		                       plate, 
		                       "" 
		);

		assertTrue( plate.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}

		assertTrue(hasBeenUpdated);	
	}

	@Test
	public void testCreateWellFunction() {

		Plate plate = pm.getPlate(pm.createPlate(tester, "plate", "1234", folder, masterPlate, cellOrigin, null));
		
		Well well = (Well)plate.getWells().toArray()[0];
		
		pm.createWellFunction(tester, "function", well, "");
		
		assertTrue( plate.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : plate.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}

		assertTrue(hasBeenUpdated);	
		
		IWellDAO wellDAO = (IWellDAO) context.getBean("wellDAO");
		Well loaded = wellDAO.getById(well.getId());
		assertEquals( well, loaded );
		assertEquals( well.getWellFunctions().size(), loaded.getWellFunctions().size() );
	}

	@Test
	public void testGetAllMasterPlates() {
		
		MasterPlate masterPlate1 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate1", plateLayout, masterPlates, 1)); 
		MasterPlate masterPlate2 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate2", plateLayout, masterPlates, 1));
		
		Collection<MasterPlate> masterPlates = pm.getAllMasterPlates();
		
		assertTrue(masterPlates.contains(masterPlate1));
		assertTrue(masterPlates.contains(masterPlate2));
	}
	
	@Test
	public void testGetAllMasterPlatesNotDeleted() {
		
		MasterPlate masterPlate1 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate1", plateLayout, masterPlates, 1)); 
		MasterPlate masterPlate2 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate2", plateLayout, masterPlates, 1));
		MasterPlate masterPlate3 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate3", plateLayout, masterPlates, 1));
		masterPlate3.delete();
		pm.edit(tester, masterPlate3);
		
		Collection<MasterPlate> masterPlates = pm.getAllMasterPlatesNotDeleted();
		
		assertTrue(  masterPlates.contains(masterPlate1) );
		assertTrue(  masterPlates.contains(masterPlate2) );
		assertFalse( masterPlates.contains(masterPlate3) );
	}

	@Test
	public void testGetMasterPlate() {
		
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1));
		
		session.flush();
		session.clear();
		
		MasterPlate fetched = pm.getMasterPlate(masterPlate.getId());
		
		assertEquals(masterPlate, fetched);
	}
	
	@Test
	public void testEditMasterPlate() {
		
		int rows = masterPlate.getRows();
		int cols = masterPlate.getCols();
		
		masterPlate.setRows(rows+5);
		masterPlate.setCols(cols+5);
		masterPlate.setName("edited");
		Point aPoint = new Point(1,1);
		int initialNumberOfSamples = masterPlate.getWell(aPoint).getSampleContainer().getSamples().size();
		int initialNumberOfMarkers = masterPlate.getWell(aPoint).getSampleMarkers().size();
		
		CellSample cs = new CellSample();
		cs.setSampleContainer(masterPlate.getWell(aPoint).getSampleContainer());
		masterPlate.getWell(aPoint).getSampleContainer().getSamples().add(cs);
		
		SampleMarker sm = new SampleMarker();
		sm.setSample(cs);
		sm.setWell(masterPlate.getWell(aPoint));
		
		masterPlate.getWell(aPoint).getSampleMarkers().add(sm);
		
		pm.edit(tester, masterPlate);
		
		MasterPlate loadedMasterPlate = pm.getMasterPlate(masterPlate.getId());

		assertEquals(loadedMasterPlate.getName(), "edited");
		assertEquals(loadedMasterPlate.getRows(), rows+5);
		assertEquals(loadedMasterPlate.getCols(), cols+5);
		assertEquals( initialNumberOfSamples + 1, 
				      loadedMasterPlate.getWell(aPoint).getSampleContainer().getSamples().size() );
		assertEquals( initialNumberOfMarkers + 1, 
				loadedMasterPlate.getWell(aPoint).getSampleMarkers().size() );
	}
	
	@Test
	public void testEditPlate() {
		
		plate.setName("edited");
		
		pm.edit(tester, plate);
		
		Plate loadedPlate = pm.getPlate(plate.getId());
		
		assertEquals( "edited", loadedPlate.getName() );
		
	}
	
	@Test
	public void testCreatingToManyPlates() {
		masterPlate.setPlatesLeft(0);
		try {
			pm.createPlate(tester, "test", "test", folder, masterPlate, cellOrigin, new Timestamp(0));
			fail("should throw exception since no plates left");
		}
		catch(IllegalStateException e) {
			//this is what we want
		}
	}
}
