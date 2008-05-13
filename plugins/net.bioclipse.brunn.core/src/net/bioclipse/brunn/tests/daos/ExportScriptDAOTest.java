package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import net.bioclipse.brunn.genericDAO.IExportScriptDAO;
import net.bioclipse.brunn.pojos.ExportScript;

public class ExportScriptDAOTest extends AbstractGenericDAOTest {

	public ExportScriptDAOTest() {
		super("exportScriptDAO");
	}
	
	public IExportScriptDAO getDAO(){
		return (IExportScriptDAO)dao;
	}

	@Test
	public void testDelete() {
		
		ExportScript exportScript = new ExportScript( tester, 
				                                      "name", 
				                                      "description", 
				                                      "type",
				                                      "body" );
		assertFalse( exportScript.isDeleted() );
		getDAO().save(exportScript);
		
		session.flush();
		session.clear();
		
		getDAO().delete(exportScript);
		
		session.flush();
		session.clear();
		
		ExportScript deleted = getDAO().getById( new Long(exportScript.getId()) );
		assertTrue( deleted.isDeleted() );
	}

	@Test
	public void testGetAll() {
		
		ExportScript exportScript1 = new ExportScript( tester, 
										               "name", 
										               "description", 
										               "type",
										               "body" ); 
		ExportScript exportScript2 = new ExportScript( tester, 
                                                       "name", 
                                                       "description", 
                                                       "type",
                									   "body" );
		getDAO().save(exportScript1);
		getDAO().save(exportScript2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue( list.contains(exportScript1) );
		assertTrue( list.contains(exportScript2) );
	}

	@Test
	public void testSave() {
		
		ExportScript exportScript = new ExportScript( tester, 
                                                      "name", 
                                                      "description", 
                                                      "type",
				   							          "body" );
		getDAO().save(exportScript);
		
		session.flush();
		session.clear();
		
		ExportScript savedExportScript = getDAO().getById(exportScript.getId());
		assertEquals(exportScript, savedExportScript);
		assertNotSame(exportScript, savedExportScript);
	}
	
	@Test
	public void testGetById() {
		
		ExportScript exportScript = new ExportScript( tester, 
                                                      "name", 
                                                      "description", 
                                                      "type",
			                                          "body" );
		getDAO().save(exportScript);
		
		session.flush();
		session.clear();
		
		ExportScript savedExportScript = getDAO().getById( new Long(exportScript.getId()) );
		assertEquals(exportScript, savedExportScript);
		assertNotSame(exportScript, savedExportScript);		
	}
}
