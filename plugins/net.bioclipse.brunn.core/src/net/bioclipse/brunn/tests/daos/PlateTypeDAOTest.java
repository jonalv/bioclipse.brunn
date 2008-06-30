package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IPlateTypeDAO;
import net.bioclipse.brunn.pojos.PlateType;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>PlateTypeDAO</code>
 * 
 * @author jonathan
 *
 */

public class PlateTypeDAOTest extends AbstractGenericDAOTest {

	public PlateTypeDAOTest() {
		super("plateTypeDAO");
	}
	
	public IPlateTypeDAO getDAO(){
		return (IPlateTypeDAO)dao;
	}

	@Test
	public void testDelete() {
		
		PlateType plateType = new PlateType(tester, 8, 12, "96 wells");
		assertFalse(plateType.isDeleted());
		getDAO().save(plateType);
		
		session.flush();
		session.clear();
		
		getDAO().delete(plateType);
		
		session.flush();
		session.clear();
		
		PlateType deleted = getDAO().getById(new Long(plateType.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PlateTypeDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		PlateType plateType1 = new PlateType(tester, 8, 12, "96 wells");
		PlateType plateType2 = new PlateType(tester, 10, 14, "bigger plate");
		getDAO().save(plateType1);
		getDAO().save(plateType2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(plateType1));
		assertTrue(list.contains(plateType2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PlateTypeDAO#save(net.bioclipse.brunn.pojos.PlateType)}.
	 */
	@Test
	public void testSave() {
		
		PlateType plateType = new PlateType(tester, 8, 12, "96 wells");;
		getDAO().save(plateType);
		
		session.flush();
		session.clear();
		
		PlateType savedPlateType = getDAO().getById(plateType.getId());
		assertEquals(plateType, savedPlateType);
		assertNotSame(plateType, savedPlateType);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.PlateTypeDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		
		PlateType plateType = new PlateType(tester, 8, 12, "96 wells");;
		getDAO().save(plateType);
		
		session.flush();
		session.clear();
		
		PlateType savedPlateType = getDAO().getById(new Long(plateType.getId()));
		assertEquals(plateType, savedPlateType);
		assertNotSame(plateType, savedPlateType);		
	}
	
	@Test
	public void testFindAllNotDeleted() {
		PlateType plateType1 = new PlateType(tester, 8, 12, "96 wells");
		PlateType plateType2 = new PlateType(tester, 10, 14, "bigger plate");
		PlateType plateType3 = new PlateType(tester, 20, 16, "deleted");
		plateType3.delete();
		
		getDAO().save(plateType1);
		getDAO().save(plateType2);
		getDAO().save(plateType3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(plateType1) );
		assertTrue(  list.contains(plateType2) );
		assertFalse( list.contains(plateType3) );
	}
}
