package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.*;

import java.util.List;

import net.bioclipse.brunn.genericDAO.IWellDAO;
import net.bioclipse.brunn.pojos.Well;

import org.junit.Test;

/**
 * Tests all the functionality of the <code>WellDAO</code>
 * 
 * @author jonathan
 *
 */
public class WellDAOTest extends AbstractGenericDAOTest {
	
	public IWellDAO getDAO(){
		return (IWellDAO)dao;
	}
	
	public WellDAOTest() {
		super("wellDAO");
	}

	@Test
	public void testDelete() {
		Well well   = new Well();
		assertFalse(well.isDeleted());
		getDAO().save(well);
		
		session.flush();
		session.clear();
		
		getDAO().delete(well);
		
		session.flush();
		session.clear();
		
		Well deleted = getDAO().getById(new Long(well.getId()));
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		Well well1 = new Well();
		Well well2 = new Well();
		getDAO().save(well1);
		getDAO().save(well2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(well1));
		assertTrue(list.contains(well2));
	}

	@Test
	public void testSave() {
		Well well = new Well();
		getDAO().save(well);
		
		session.flush();
		session.clear();
		
		Well savedWell = getDAO().getById(well.getId());
		assertEquals(well, savedWell);
		assertNotSame(well, savedWell);
	}
	
	@Test
	public void testGetById() {
		Well well = new Well();
		getDAO().save(well);
		
		session.flush();
		session.clear();
		
		Well savedWell = getDAO().getById(new Long(well.getId()));
		assertEquals(well, savedWell);
		assertNotSame(well, savedWell);		
	}
	
	@Test
	public void testPersistingOutlierFlag() {
		Well well = new Well();
		boolean before = well.isOutlier();
		getDAO().save(well);
		
		session.flush();
		session.clear();
		
		Well savedWell = getDAO().getById(well.getId());
		savedWell.setOutlier(!before);
		savedWell = getDAO().merge(savedWell);
		getDAO().save(savedWell);
		
		session.flush();
		session.clear();
		
		savedWell = getDAO().getById( well.getId() );
		assertTrue( before != savedWell.isOutlier() );
	}
}