package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.TestConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests all the methods of the OriginManager. 
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
public class OriginManagerTest extends BaseTest {

	private IOriginManager om;
	
	public OriginManagerTest() {
	    super();
    }

	@Before
	public void setUp() throws Exception {
		super.setUp();
		om = (IOriginManager) context.getBean("originManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testCreateCellOriginUserString() {
		
		CellOrigin cellOrigin = om.getCellOrigin( om.createCellOrigin( tester, 
				                                                       "cellOrigin",
				                                                       cellTypes ) 
				                                );
		
		assertTrue( cellOrigin.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : cellOrigin.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue( hasBeenCreated );
		
		session.flush();
		session.clear();
		
		assertTrue(cellTypes.getObjects().contains(cellOrigin));
		
		Folder loadedFolder = fm.getUniqueFolder(cellTypes.getId());
		
		assertEquals( cellTypes, loadedFolder );
	}

	@Test
	public void testCreateDrugOriginUserStringStringDouble() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin = om.getDrugOrigin( om.createDrugOrigin( tester, 
	                                                                   "cellOrigin",
	                                                                   new FileInputStream(TestConstants.getTestMolFile()),
	                                                                   23.0,
	                                                                   compounds ) 
		                                        );
		
		assertTrue( drugOrigin.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : drugOrigin.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}

		assertTrue( hasBeenCreated );
		
		session.flush();
		session.clear();
		
		assertTrue(compounds.getObjects().contains(drugOrigin));
		
		Folder loadedFolder = fm.getUniqueFolder(compounds.getId());
		
		assertEquals(compounds, loadedFolder);
	}

	@Test
	public void testDeleteUserCellOrigin() {
		
		CellOrigin cellOrigin = om.getCellOrigin( om.createCellOrigin( tester, 
                                                                       "cellOrigin",
                                                                       cellTypes ) 
                                                );
		om.delete(tester, cellOrigin);

		assertTrue( cellOrigin.getAuditLogs().size() == 2 );
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : cellOrigin.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
				hasBeenDeleted = true;
		}

		assertTrue( hasBeenDeleted );
		assertTrue( cellOrigin.isDeleted() );
	}

	@Test
	public void testDeleteUserDrugOrigin() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin = om.getDrugOrigin( om.createDrugOrigin( tester,
				                                                       "drug origin",
				                                                       new FileInputStream(TestConstants.getTestMolFile()),
				                                                       34.0,
				                                                       compounds )
		                                        );
		om.delete(tester, drugOrigin);
		
		assertTrue( drugOrigin.getAuditLogs().size() == 2 );
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : drugOrigin.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
				hasBeenDeleted = true;
		}
		
		assertTrue( hasBeenDeleted );
		assertTrue( drugOrigin.isDeleted() );
	}

	@Test
	public void testEditUserCellOrigin() {
		
		CellOrigin cellOrigin = om.getCellOrigin( om.createCellOrigin( tester, 
                                                                       "cellOrigin", 
                                                                       cellTypes ) 
		                                        );
		
		cellOrigin.setName("edited");
		
		om.edit(tester, cellOrigin);
		
		assertTrue( cellOrigin.getAuditLogs().size() == 2 );
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : cellOrigin.getAuditLogs()) {
			if( auditLog.getAuditType() == AuditType.UPDATE_EVENT )
				hasBeenEdited = true;
		}
		
		assertTrue( hasBeenEdited );
	}

	@Test
	public void testEditUserDrugOrigin() throws FileNotFoundException, IOException {

		DrugOrigin drugOrigin = om.getDrugOrigin( om.createDrugOrigin( tester,
				                                                       "drugOriginx",
				                                                       new FileInputStream(TestConstants.getTestMolFile()),
				                                                       23.0, 
				                                                       compounds )
		                                        );
		
		drugOrigin.setName("editedx");
		
		om.edit(tester, drugOrigin);
		
		assertTrue( drugOrigin.getAuditLogs().size() == 2 );
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : drugOrigin.getAuditLogs()) {
			if( auditLog.getAuditType() == AuditType.UPDATE_EVENT )
				hasBeenEdited = true;
		}
		
		assertTrue( hasBeenEdited );
	}

	@Test
	public void testGetAllCellOrigins() {
		
		CellOrigin cellOrigin1 = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin1", cellTypes)); 
		CellOrigin cellOrigin2 = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin2", cellTypes));
		
		Collection<CellOrigin> cellOrigins = om.getAllCellOrigins();
		
		assertTrue( cellOrigins.contains(cellOrigin1) );
		assertTrue( cellOrigins.contains(cellOrigin2) );
	}
	
	@Test
	public void testGetAllCellOriginsNotDeleted() {
		
		CellOrigin cellOrigin1 = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin1", cellTypes)); 
		CellOrigin cellOrigin2 = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin2", cellTypes));
		CellOrigin cellOrigin3 = om.getCellOrigin(om.createCellOrigin(tester, "deleted",     cellTypes));
		cellOrigin3.delete();
		om.edit(tester, cellOrigin3);
		
		Collection<CellOrigin> cellOrigins = om.getAllCellOriginsNotDeleted();
		
		assertTrue(  cellOrigins.contains(cellOrigin1) );
		assertTrue(  cellOrigins.contains(cellOrigin2) );
		assertFalse( cellOrigins.contains(cellOrigin3) );
	}

	@Test
	public void testGetAllDrugOrigins() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin1 = om.getDrugOrigin(
				om.createDrugOrigin(
						tester, "drugOrigin1", new FileInputStream(TestConstants.getTestMolFile()), 23.0, compounds)); 
		DrugOrigin drugOrigin2 = om.getDrugOrigin(
				om.createDrugOrigin(
						tester, "drugOrigin2", new FileInputStream(TestConstants.getTestMolFile()), 34.0, compounds));
		
		Collection<DrugOrigin> drugOrigins = om.getAllDrugOrigins();
		
		assertTrue( drugOrigins.contains(drugOrigin1) );
		assertTrue( drugOrigins.contains(drugOrigin2) );
	}
	
	@Test
	public void testGetAllDrugOriginsNotDeleted() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin1 = om.getDrugOrigin(
				om.createDrugOrigin(
						tester, "drugOrigin1", new FileInputStream(TestConstants.getTestMolFile()), 23.0, compounds)); 
		DrugOrigin drugOrigin2 = om.getDrugOrigin(
				om.createDrugOrigin(
						tester, "drugOrigin2", new FileInputStream(TestConstants.getTestMolFile()), 34.0, compounds));
		DrugOrigin drugOrigin3 = om.getDrugOrigin(
				om.createDrugOrigin(
						tester, "deleted",     new FileInputStream(TestConstants.getTestMolFile()), 50.0, compounds));
		drugOrigin3.delete();
		om.edit(tester, drugOrigin3);
		
		Collection<DrugOrigin> drugOrigins = om.getAllDrugOriginsNotDeleted();
		
		assertTrue(  drugOrigins.contains(drugOrigin1) );
		assertTrue(  drugOrigins.contains(drugOrigin2) );
		assertFalse( drugOrigins.contains(drugOrigin3) );
	}

	@Test
	public void testGetDrugOrigin() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin = om.getDrugOrigin( 
				om.createDrugOrigin(tester, "drug origin", new FileInputStream(TestConstants.getTestMolFile()), 12.0, compounds) );
		
		session.flush();
		session.clear();
		
		DrugOrigin fetched = om.getDrugOrigin( drugOrigin.getId() );
		
		assertEquals(  drugOrigin, fetched );
	}

	@Test
	public void testGetCellOrigin() {
		
		CellOrigin cellOrigin = om.getCellOrigin( om.createCellOrigin(tester, "cellOrigin", cellTypes) );
		
		session.flush();
		session.clear();
		
		CellOrigin fetched = om.getCellOrigin( cellOrigin.getId() );
		
		assertEquals(  cellOrigin, fetched );
	}

	@Test
	public void testCreateCellOriginUserStringFolder() {
		
		CellOrigin cellOrigin = om.getCellOrigin( om.createCellOrigin( tester, 
                                                                       "cellOrigin",
                                                                       folder ) 
                                                );

		assertTrue( cellOrigin.getAuditLogs().size() == 1 );

		boolean hasBeenCreated = false;
	
		for (AuditLog auditLog : cellOrigin.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}

		assertTrue( hasBeenCreated );
		assertTrue( folder.getObjects().contains(cellOrigin) );
	}

	@Test
	public void testCreateDrugOriginUserStringStringDoubleFolder() throws FileNotFoundException, IOException {
		
		DrugOrigin drugOrigin = om.getDrugOrigin( om.createDrugOrigin( tester, 
                                                                       "cellOrigin",
                                                                       new FileInputStream(TestConstants.getTestMolFile()),
                                                                       23.0,
                                                                       folder ) 
		);

		assertTrue( drugOrigin.getAuditLogs().size() == 1 );

		boolean hasBeenCreated = false;

		for (AuditLog auditLog : drugOrigin.getAuditLogs()) {
			if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
				hasBeenCreated = true;
		}

		assertTrue( hasBeenCreated );
		assertTrue( folder.getObjects().contains(drugOrigin) );
	}
	
	@Test
	public void testEditCellOrigin() {
		
		cellOrigin.setName("edited");
		
		om.edit(tester, cellOrigin);
		
		CellOrigin loadedOrigin = om.getCellOrigin(cellOrigin.getId());
		
		assertEquals(loadedOrigin.getName(), "edited");
	}
	
	@Test
	public void testEditDrugOrigin() {
		
		drugOrigin.setName("editedx");
		
		om.edit(tester, drugOrigin);
		
		DrugOrigin loadedOrigin = om.getDrugOrigin(drugOrigin.getId());
		
		assertEquals(loadedOrigin.getName(), "edited");
	}
}
