package net.bioclipse.brunn.tests.daos;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.pojos.UniqueFolder;

/**
 * Tests all the functionality of the <code>UniqueFolderDAO</code>
 * 
 * @author jonathan
 *
 */
public class UniqueFolderDAOTest extends AbstractGenericDAOTest {
	
	public UniqueFolderDAOTest() {
		super("uniqueFolderDAO");
	}

	public IUniqueFolderDAO getDAO(){
		return (IUniqueFolderDAO)dao;
	}

	@Test
	public void testDelete() {
		
		UniqueFolder uniqueFolder = new UniqueFolder(tester, "uniqueFolder", "uniqueFolder");
		assertFalse(uniqueFolder.isDeleted());
		getDAO().save(uniqueFolder);
		
		session.flush();
		session.clear();
		
		getDAO().delete(uniqueFolder);
		
		session.flush();
		session.clear();
		
		UniqueFolder deleted = getDAO().getById(new Long(uniqueFolder.getId()));
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		UniqueFolder uniqueFolder1 = new UniqueFolder(tester, "uniqueFolder1", "uniqueFolder1");
		UniqueFolder uniqueFolder2 = new UniqueFolder(tester, "uniqueFolder2", "uniqueFolder2");
		getDAO().save(uniqueFolder1);
		getDAO().save(uniqueFolder2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(uniqueFolder1));
		assertTrue(list.contains(uniqueFolder2));
	}

	@Test
	public void testSave() {
		
		UniqueFolder uniqueFolder = new UniqueFolder(tester, "uniqueFolder", "uniqueFolder");
		uniqueFolder.getObjects().add(masterPlate);
		getDAO().save(uniqueFolder);
		
		session.flush();
		session.clear();
		
		UniqueFolder savedUniqueFolder = getDAO().getById(uniqueFolder.getId());
		assertEquals(uniqueFolder, savedUniqueFolder);
		assertNotSame(uniqueFolder, savedUniqueFolder);
	}
	
	@Test
	public void testGetById() {
		
		UniqueFolder uniqueFolder = new UniqueFolder(tester, "uniqueFolder", "uniqueFolder");
		UniqueFolder subFolder    = new UniqueFolder(tester, "uniqueSubFolder", "uniqueSubFolder");
		uniqueFolder.getObjects().add(subFolder);
		
		getDAO().save(uniqueFolder);
		getDAO().save(subFolder);
		
		session.flush();
		session.clear();
		
		UniqueFolder savedUniqueFolder = getDAO().getById(uniqueFolder.getId());
		UniqueFolder savedSubFolder = getDAO().getById(subFolder.getId());
		
		assertEquals(subFolder, savedSubFolder);
		assertTrue(savedUniqueFolder.getObjects().contains(savedSubFolder));
		assertEquals(uniqueFolder, savedUniqueFolder);
		assertNotSame(uniqueFolder, savedUniqueFolder);
	}
	
	@Test
	public void testFindByUniqueName() {
		
		UniqueFolder uniqueFolder = new UniqueFolder(tester, "Unique Folder", "uniqueFolder");
		getDAO().save(uniqueFolder);
		
		session.flush();
		session.clear();
		
		UniqueFolder savedUniqueFolder = getDAO().findByUniqueName("uniqueFolder").get(0);
		assertEquals(uniqueFolder, savedUniqueFolder);
		assertNotSame(uniqueFolder, savedUniqueFolder);		
	}
}
