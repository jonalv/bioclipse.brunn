package net.bioclipse.brunn.tests.daos;

import java.util.List;

import net.bioclipse.brunn.genericDAO.ILayoutWellDAO;
import net.bioclipse.brunn.pojos.LayoutWell;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests all the functionality of the <code>LayoutWellDAO</code>
 * 
 * @author jonathan
 *
 */

public class LayoutWellDAOTest extends AbstractGenericDAOTest {

	public LayoutWellDAOTest() {
		super("layoutWellDAO");
	}
	
	public ILayoutWellDAO getDAO(){
		return (ILayoutWellDAO)dao;
	}

	@Test
	public void testDelete() {
		
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 1, 'a', plateLayout);
		assertFalse(layoutWell.isDeleted());
		getDAO().save(layoutWell);
		
		session.flush();
		session.clear();
		
		getDAO().delete(layoutWell);
		
		session.flush();
		session.clear();
		
		LayoutWell deleted = getDAO().getById(new Long(layoutWell.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Test method for {@link net.bioclipse.brunn.daos.LayoutWellDAO#getAll()}.
	 */
	@Test
	public void testGetAll() {
		
		LayoutWell layoutWell1 = new LayoutWell(tester, "layoutWell1", 1, 'a', plateLayout);
		LayoutWell layoutWell2 = new LayoutWell(tester, "layoutWell2", 1, 'b', plateLayout);
		getDAO().save(layoutWell1);
		getDAO().save(layoutWell2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(layoutWell1));
		assertTrue(list.contains(layoutWell2));
	}

	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.LayoutWellDAO#save(net.bioclipse.brunn.pojos.LayoutWell)}.
	 */
	@Test
	public void testSave() {
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 1, 'a', plateLayout);
		getDAO().save(layoutWell);
		
		session.flush();
		session.clear();
		
		LayoutWell savedLayoutWell = getDAO().getById(layoutWell.getId());
		assertEquals(layoutWell, savedLayoutWell);
		assertNotSame(layoutWell, savedLayoutWell);
	}
	
	/**
	 * Test method for {@link net.bioclipse.brunn.daos.LayoutWellDAO#getById(long)}.
	 */
	@Test
	public void testGetById() {
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 1, 'a', plateLayout);
		getDAO().save(layoutWell);
		
		session.flush();
		session.clear();
		
		LayoutWell savedLayoutWell = getDAO().getById(new Long(layoutWell.getId()));
		assertEquals(layoutWell, savedLayoutWell);
		assertNotSame(layoutWell, savedLayoutWell);		
	}
}
