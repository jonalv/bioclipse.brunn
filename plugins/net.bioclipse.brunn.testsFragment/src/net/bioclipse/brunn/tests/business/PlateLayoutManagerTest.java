package net.bioclipse.brunn.tests.business;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.PlateFunction;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.tests.BaseTest;

/**
 * Tests all the methods of the PlateLayoutManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *	  are able to call the methods
 * 
 * @author jonathan
 */
public class PlateLayoutManagerTest extends BaseTest {

	private IPlateLayoutManager plm;
	
	public PlateLayoutManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testCreatePlateLayout() {	
		
		PlateLayout plateLayout = plm.getPlateLayout(
				                      plm.createPlateLayout( tester, 
				                                             "name", 
				                                             plm.getPlateType(
				                                                 plm.createPlateType( tester, 
				                                        		                      3, 
				                                        		                      3, 
				                                        		                     "name",
				                                        		                     plateTypes )
				                                                              ),
				                                             plateLayouts
				                                            )
				                                            
		                                            );
		
		assertTrue( plateLayout.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : plateLayout.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
		
		session.flush();
		session.clear();
		
		assertTrue(plateLayouts.getObjects().contains(plateLayout));
		
		Folder loadedFolder = fm.getUniqueFolder(plateLayouts.getId());
		
		assertEquals(plateLayouts, loadedFolder);
	}
	
	@Test
	public void testCreatePlateLayoutInFolder() {
		
		
		PlateLayout plateLayout = plm.getPlateLayout( plm.createPlateLayout(tester, 
				                                      "name", 
				                                      plm.getPlateType( plm.createPlateType( tester, 
				                                        		                             3, 
				                                        		                             3, 
				                                        		                             "name",
				                                        		                             plateTypes)
				                                        		      ),
				                                        plateLayouts)
		                                            );
		
		assertTrue( plateLayout.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : plateLayout.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue( hasBeenCreated );
		assertTrue( plateLayouts.getObjects().contains( plateLayout ) );

	}

	@Test
	public void testCreatePlateTypeInFolder() {

		PlateType plateType = plm.getPlateType( plm.createPlateType( tester,
				                                                     3,
				                                                     4,
				                                                     "plateType",
				                                                     folder ) 
				                               );
		
		assertTrue( plateType.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : plateType.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}

		assertTrue( hasBeenCreated );	
		assertTrue( folder.getObjects().contains(plateType) );
	}
	
	@Test
	public void testCreatePlateType() {

		PlateType plateType = plm.getPlateType(plm.createPlateType(tester,
				                                  3,
				                                  4,
				                                  "a plateType",
				                                  plateTypes));
		
		assertTrue( plateType.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : plateType.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}

		assertTrue(hasBeenCreated);	
		
		session.flush();
		session.clear();
		
		assertTrue(plateTypes.getObjects().contains(plateType));
		
		Folder loadedFolder = fm.getUniqueFolder(plateTypes.getId());
		
		assertEquals(plateTypes, loadedFolder);
	}

	@Test
	public void testGetAllPlateLayouts() {
		
		PlateType plateType = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType", plateTypes));
		
		PlateLayout plateLayout1 = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout1", plateType, plateLayouts));
		PlateLayout plateLayout2 = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout2", plateType, plateLayouts));
		
		Collection<PlateLayout> plateLayouts = plm.getAllPlateLayouts();
		
		assertTrue(plateLayouts.contains(plateLayout1));
		assertTrue(plateLayouts.contains(plateLayout2));		
	}

	@Test
	public void testGetAllPlateTypes() {
		
		PlateType plateType1 = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType1", plateTypes));
		PlateType plateType2 = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType2", plateTypes));
		
		Collection<PlateType> plateTypes = plm.getAllPlateTypes();
		
		assertTrue(plateTypes.contains(plateType1));
		assertTrue(plateTypes.contains(plateType2));
	}

	@Test
	public void testGetAllPlateTypesNotDeleted() {
		
		PlateType plateType1 = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType1", plateTypes));
		PlateType plateType2 = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType2", plateTypes));
		PlateType plateType3 = plm.getPlateType(plm.createPlateType(tester, 3, 4, "deleted",    plateTypes));
		plateType3.delete();
		plm.edit(tester, plateType3);
		
		Collection<PlateType> plateTypes = plm.getAllPlateTypesNotDeleted();
		
		assertTrue(  plateTypes.contains(plateType1) );
		assertTrue(  plateTypes.contains(plateType2) );
		assertFalse( plateTypes.contains(plateType3) );
	}

	@Test
	public void testGetAllPlateLayoutsNotDeleted() {
		
		PlateType plateType = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType", plateTypes));
		
		PlateLayout plateLayout1 = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout1", plateType, plateLayouts));
		PlateLayout plateLayout2 = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout2", plateType, plateLayouts));
		PlateLayout plateLayout3 = plm.getPlateLayout(plm.createPlateLayout(tester, "deleted",      plateType, plateLayouts));
		plateLayout3.delete();
		plm.edit(tester, plateLayout3);
		
		Collection<PlateLayout> plateLayouts = plm.getAllPlateLayoutsNotDeleted();
		
		assertTrue(  plateLayouts.contains(plateLayout1) );
		assertTrue(  plateLayouts.contains(plateLayout2) );
		assertFalse( plateLayouts.contains(plateLayout3) );
	}
	
	@Test
	public void testGetPlateLayout() {

		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLAyout", plateType, plateLayouts));
		
		session.flush();
		session.clear();
		
		PlateLayout fetched = plm.getPlateLayout(plateLayout.getId());
		
		assertEquals(fetched, plateLayout);
	}

	@Test
	public void testGetPlateType() {
		
		PlateType plateType = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType", plateTypes));
		
		session.flush();
		session.clear();
		
		PlateType fetched = plm.getPlateType(plateType.getId());
		
		assertEquals(fetched, plateType);
	}

	@Test
	public void testCreatePlateFunctionUserStringAbstractBasePlateStringDoubleDouble() {
			
		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout", plateType, plateLayouts));
		
		assertEquals(0, plateLayout.getPlateFunctions().size());
		
		plm.createPlateFunction(tester, 
		                        "function", 
		                        plateLayout, 
		                        "", 
		                        3,
		                        5
		);
	
		assertTrue( plateLayout.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : plateLayout.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}

		assertTrue(hasBeenUpdated);	
	}

	@Test
	public void testCreatePlateFunctionUserStringAbstractBasePlateString() {

		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout", plateType, plateLayouts));
		
		plm.createPlateFunction(tester, 
		                        "function", 
		                        plateLayout, 
		                        "" 
		);

		assertTrue( plateLayout.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		for (AuditLog auditLog : plateLayout.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}

		assertTrue(hasBeenUpdated);	

	}

	@Test
	public void testCreateWellFunction() {

		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLayout", plateType, plateLayouts));
		
		LayoutWell layoutWell = (LayoutWell)plateLayout.getLayoutWells().toArray()[0];
		
		plm.createWellFunction(tester, "function", layoutWell, "");

		assertTrue( plateLayout.getAuditLogs().size() == 2 );
		
		boolean hasBeenUpdated = false;
		
		session.flush();
		session.clear();
		
		plateLayout = plm.getPlateLayout(plateLayout.getId());
		
		for (AuditLog auditLog : plateLayout.getAuditLogs()) {
		
			if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
				hasBeenUpdated = true;
		}
		assertTrue(hasBeenUpdated);
		
		for(LayoutWell well : plateLayout.getLayoutWells()) {
			if(well.getId() == layoutWell.getId()) {
				assertTrue( well.getWellFunctions().size() == 2);
			}
		}
	}
	
	@Test
	public void testEditPlateType() {
			
		int rows = plateType.getRows();
		int cols = plateType.getCols();
		
		plateType.setRows(rows+5);
		plateType.setCols(cols+5);
		plateType.setName("edited");
		
		plm.edit(tester, plateType);
		
//		session.flush();
//		session.clear();
		
		PlateType loadedPlateType = plm.getPlateType(plateType.getId());
		
		assertEquals(loadedPlateType.getRows(), rows+5);
		assertEquals(loadedPlateType.getCols(), cols+5);
		assertEquals(loadedPlateType.getName(), "edited");
		
	}
	
	@Test
	public void testEditPlateLayout() {
		
		int rows = plateLayout.getRows();
		int cols = plateLayout.getCols();
		
		plateLayout.setRows(rows+5);
		plateLayout.setCols(cols+5);
		plateLayout.setName("edited");
		
		plm.edit(tester, plateLayout);
		
		PlateLayout loadedPlateLayuot = plm.getPlateLayout(plateLayout.getId());
		
		assertEquals(loadedPlateLayuot.getRows(), rows+5);
		assertEquals(loadedPlateLayuot.getCols(), cols+5);
		assertEquals(loadedPlateLayuot.getName(), "edited");
		
	}
}
