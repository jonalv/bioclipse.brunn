package net.bioclipse.brunn.tests.daos;


import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IPlateLayoutDAO;
import net.bioclipse.brunn.pojos.PlateLayout;

/**
 * Tests all the functionality of the <code>PlateLayoutDAO</code>
 * 
 * @author jonathan
 *
 */

public class PlateLayoutDAOTest extends AbstractGenericDAOTest {
	
	public IPlateLayoutDAO getDAO(){
		return (IPlateLayoutDAO)dao;
	}
	
	public PlateLayoutDAOTest() {
		super("plateLayoutDAO");
	}

	@Test
	public void testDelete() {
		PlateLayout plateLayout = new PlateLayout(tester, "a plateLayout", plateType);
		assertFalse(plateLayout.isDeleted());
		getDAO().save(plateLayout);
		
		session.flush();
		session.clear();
		
		getDAO().delete(plateLayout);
		
		session.flush();
		session.clear();
		
		PlateLayout deleted = getDAO().getById(new Long(plateLayout.getId()));
		assertTrue(deleted.isDeleted());
	}
	
	@Test
	public void testGetAll() {
		PlateLayout plateLayout1 = new PlateLayout(tester, "a platelayout", plateType);
		PlateLayout plateLayout2 = new PlateLayout(tester, "a platelayout", plateType);
		getDAO().save(plateLayout1);
		getDAO().save(plateLayout2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(plateLayout1));
		assertTrue(list.contains(plateLayout2));
	}

	@Test
	public void testGetById() {
		PlateLayout plateLayout = new PlateLayout(tester, " a plateLayout", plateType);;
		getDAO().save(plateLayout);
		
		session.flush();
		session.clear();
		
		PlateLayout savedPlateLayout = getDAO().getById(new Long(plateLayout.getId()));
		assertEquals(plateLayout, savedPlateLayout);
		assertNotSame(plateLayout, savedPlateLayout);
		assertTrue(plateLayout.getId() == savedPlateLayout.getId());
	}

	@Test
	public void testSave() {
		PlateLayout plateLayout = new PlateLayout(tester, "a plateLayout", plateType);		
		getDAO().save(plateLayout);

		session.flush();
		session.clear();
		
		PlateLayout savedPlateLayout = getDAO().getById(new Long(plateLayout.getId()));
		assertEquals(plateLayout, savedPlateLayout);
		assertNotSame(plateLayout, savedPlateLayout);
	}
	
	@Test
	public void testfindAllNotDeleted() {
		PlateLayout plateLayout1 = new PlateLayout(tester, "a platelayout", plateType);
		PlateLayout plateLayout2 = new PlateLayout(tester, "a platelayout", plateType);
		PlateLayout plateLayout3 = new PlateLayout(tester, "deleted", plateType);
		plateLayout3.delete();
		
		getDAO().save(plateLayout1);
		getDAO().save(plateLayout2);
		getDAO().save(plateLayout3);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(plateLayout1) );
		assertTrue(  list.contains(plateLayout2) );
		assertFalse( list.contains(plateLayout3) );
	}
}
