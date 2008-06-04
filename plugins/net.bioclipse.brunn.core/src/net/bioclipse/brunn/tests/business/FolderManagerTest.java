package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.pojos.AuditLog;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.tests.BaseTest;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tests all the methods of the FolderManager. 
 * 
 * Things that should be checked for:
 * 1. If something should be created/edited/deleted, 
 *    that thing gets created/edited/deleted happends
 * 2. If something should be audited, 
 *    there is a audit trail for it
 * 3. That only users that should be able to call the methods,
 *    are able to call the methods
 * @author jonathan
 */
public class FolderManagerTest extends BaseTest {
	
	private IFolderManager fm;

	public FolderManagerTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		fm = (IFolderManager)context.getBean("folderManager");
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testCreateFolder() {
		
		Folder folder = fm.getFolder( fm.createFolder(tester, "my folder", projects) );

		assertTrue( folder.getAuditLogs().size() == 1 );
		
		boolean hasBeenCreated = false;
		
		for (AuditLog auditLog : folder.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.CREATE_EVENT)
	        	hasBeenCreated = true;
        }
		
		assertTrue( hasBeenCreated );
		
		session.flush();
		session.clear();
		
		assertTrue( projects.getObjects().contains(folder) );
		assertTrue( projects.equals( fm.getUniqueFolder(projects.getId()) ) );
	}

	@Test
	public void testEdit() {
		
		Folder folder = fm.getFolder( fm.createFolder(tester, "my folder", projects) );
		
		folder.setName("edited");
		
		fm.edit(tester, folder);
		
		Folder edited = fm.getFolder( folder.getId() );
		
		assertTrue( edited.getAuditLogs().size() == 2 );
		
		boolean hasBeenEdited = false;
		
		for (AuditLog auditLog : folder.getAuditLogs()) {
	        if(auditLog.getAuditType() == AuditType.UPDATE_EVENT)
	        	hasBeenEdited = true;
        }
		
		assertTrue( hasBeenEdited );
		assertEquals( "edited", edited.getName() );
	}

	@Test
	public void testGetAllFolders() {
		
		Folder folder1 = fm.getFolder(fm.createFolder(tester, "my folder1", projects));
		Folder folder2 = fm.getFolder(fm.createFolder(tester, "my folder2", projects));
		
		Collection folders = fm.getAllFolders();
		assertTrue(folders.contains(folder1));
		assertTrue(folders.contains(folder2));
	}

	@Test
	public void testGetAllUniqueFolders() {
		
		Collection uniqueFolders = fm.getAllUniqueFolders();
		projects = fm.getUniqueFolder(projects.getId());
		assertTrue(uniqueFolders.contains(projects));
	}

	@Test
	public void testGetFolder() {
		
		Folder folder = fm.getFolder(fm.createFolder(tester, "my folder1", projects));
		
		session.flush();
		session.clear();
		
		Folder fetched = fm.getFolder(folder.getId());
		
		assertEquals(fetched, folder);
	}

	@Test
	public void testGetUniqueFolder() {
		
		session.flush();
		session.clear();
		
		Folder fetched = fm.getFolder(masterPlates.getId());
		
		assertEquals(  masterPlates, fetched );
		assertNotSame( masterPlates, fetched );
		
		fetched = fm.getFolder(cellTypes.getId());
		
		assertEquals(  cellTypes, fetched );
		assertNotSame( cellTypes, fetched );
	}
	
	/*
	 * Some test before seems to have broken something needed by the 
	 * testGetUniqueFolderByName test. So we recreate the applicationcontext 
	 * once for these tests 
	 */
	@BeforeClass
	public static void recreateContext() {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testGetUniqueFolderByName() {
		
		session.flush();
		session.clear();
		
		Folder fetched = fm.getUniqueFolder( masterPlates.getUniqueName() );
		
		assertEquals( masterPlates, fetched  );
		assertNotSame( masterPlates, fetched );
	}
}
