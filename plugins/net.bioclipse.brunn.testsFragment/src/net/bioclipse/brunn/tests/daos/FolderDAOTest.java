package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IFolderDAO;
import net.bioclipse.brunn.pojos.Folder;

import org.junit.Test;

/**
 * Tests all the functionality of the <code>FolderDAO</code>
 * 
 * @author jonathan
 *
 */
public class FolderDAOTest extends AbstractGenericDAOTest {
	
	public FolderDAOTest() {
		super("folderDAO");
	}
	
	public IFolderDAO getDAO(){
		return (IFolderDAO)dao;
	}

	@Test
	public void testDelete() {
		
		Folder folder = new Folder(tester, "folder", projects);
		assertFalse(folder.isDeleted());
		getDAO().save(folder);
		
		session.flush();
		session.clear();
		
		getDAO().delete(folder);
		
		session.flush();
		session.clear();
		
		Folder deleted = getDAO().getById(new Long(folder.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.FolderDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		Folder folder1 = new Folder(tester, "folder1", projects);
		Folder folder2 = new Folder(tester, "folder2", projects);
		getDAO().save(folder1);
		getDAO().save(folder2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(folder1));
		assertTrue(list.contains(folder2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.FolderDAO#save(net.bioclipse.brunn.pojos.Folder)}.
	 */
	@Test
	public void testSave() {
		
		Folder folder = new Folder(tester, "folder", projects);
		getDAO().save(folder);
		
		session.flush();
		session.clear();
		
		Folder savedFolder = getDAO().getById(folder.getId());
		assertEquals(folder, savedFolder);
		assertNotSame(folder, savedFolder);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.FolderDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		Folder folder    = new Folder(tester, "folder", projects);
		Folder subFolder = new Folder(tester, "subFolder", folder);
		getDAO().save(folder);
		
		session.flush();
		session.clear();
		
		Folder savedFolder = getDAO().getById(folder.getId());
		Folder savedSubFolder = getDAO().getById(subFolder.getId());
		
		assertEquals(folder, savedFolder);
		assertEquals(subFolder, savedSubFolder);
		assertNotSame(folder, savedFolder);
		assertTrue( folder.getObjects().contains(subFolder) );
	}
}
