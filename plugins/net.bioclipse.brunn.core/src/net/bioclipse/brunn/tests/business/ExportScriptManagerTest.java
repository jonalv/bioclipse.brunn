package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.business.exportScript.ExportScriptManager;
import net.bioclipse.brunn.business.exportScript.IExportScriptManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.ExportScript;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.tests.BaseTest;

public class ExportScriptManagerTest extends BaseTest {

	private IExportScriptManager esm;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		esm = (IExportScriptManager)context.getBean("exportScriptManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testCreateExportScript() {

		ExportScript exportScript = new ExportScript( tester,
				                                      "name", 
				                                      "description",
				                                      "type",
				                         			  "body" );
		
		exportScript = esm.getExportScriptById( esm.createExportScript(tester, folder, exportScript) );
		
		assertTrue( exportScript.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : exportScript.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue(hasBeenCreated);
		
		session.flush();
		session.clear();
		
		assertTrue(folder.getObjects().contains(exportScript));
	}
	
	@Test
	public void testDeleteExportScript() {
		
		ExportScript exportScript = new ExportScript( tester,
                                                      "name", 
                                                      "description",
                                                      "type",
   			                                          "body" );
		
		exportScript = esm.getExportScriptById( esm.createExportScript(tester, folder, exportScript) );
		
		int before = exportScript.getAuditLogs().size(); 
		
		esm.delete(tester, exportScript);
		
		assertTrue( exportScript.getAuditLogs().size() == before +1 );
		
		boolean hasBeenDeleted = false;
		
		for (AuditLog auditLog : exportScript.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.DELETE_EVENT)
	        	hasBeenDeleted = true;
        }
		
		assertTrue(hasBeenDeleted);
		
		session.flush();
		session.clear();
		
		assertTrue(exportScript.isDeleted());
	}
	
	@Test
	public void testEditExportScript() {
		
		ExportScript exportScript = new ExportScript( tester,
                                                      "name", 
                                                      "description",
                                                      "type",
                                                      "body" );

		exportScript = esm.getExportScriptById( esm.createExportScript(tester, folder, exportScript) );
		
		exportScript.setDescription("edited description");
		exportScript.setType("edited type");
		exportScript.setName("edited");
		
		esm.edit(tester, exportScript);
		
		ExportScript loadedExportScript = esm.getExportScriptById( exportScript.getId() );
		
		assertEquals( loadedExportScript.getDescription(), "edited description" );
		assertEquals( loadedExportScript.getType(), "edited type"               );
		assertEquals( loadedExportScript.getName(), "edited"                    );
	}
}
