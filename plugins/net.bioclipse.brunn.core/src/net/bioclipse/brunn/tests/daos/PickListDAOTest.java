package net.bioclipse.brunn.tests.daos;

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.genericDAO.IPickListDAO;
import net.bioclipse.brunn.pojos.PickList;
import net.bioclipse.brunn.pojos.Picking;

/**
* Tests all the functionality of the <code>LayoutWellDAO</code>
* 
* @author jonathan
*
*/
public class PickListDAOTest extends AbstractGenericDAOTest {

	public PickListDAOTest() {
		super("pickListDAO");
	}
	
	public IPickListDAO getDAO(){
		return (IPickListDAO)dao;
	}

	@Test
	public void testDelete() {
		
		PickList pickList = new PickList(tester, "pickList", plateLayout);
		assertFalse(pickList.isDeleted());
		getDAO().save(pickList);
		
		session.flush();
		session.clear();
		
		getDAO().delete(pickList);
		
		session.flush();
		session.clear();
		
		PickList deleted = getDAO().getById(new Long(pickList.getId()));
		assertTrue(deleted.isDeleted());
	}

	@Test
	public void testGetAll() {
		
		PickList pickList1 = new PickList(tester, "pickList1", plateLayout);
		PickList pickList2 = new PickList(tester, "pickList2", plateLayout);
		getDAO().save(pickList1);
		getDAO().save(pickList2);
		
		session.flush();
		session.clear();
		
		List list = getDAO().findAll();
		assertTrue(list.contains(pickList1));
		assertTrue(list.contains(pickList2));
	}

	@Test
	public void testSave() {
		PickList pickList = new PickList(tester, "pickList", plateLayout);
		getDAO().save(pickList);
		
		session.flush();
		session.clear();
		
		PickList savedPickList = getDAO().getById(pickList.getId());
		assertEquals(pickList, savedPickList);
		assertNotSame(pickList, savedPickList);
	}
	
	@Test
	public void testGetById() {
		PickList pickList = new PickList(tester, "pickList", plateLayout);
		getDAO().save(pickList);
		
		session.flush();
		session.clear();
		
		PickList savedPickList = getDAO().getById(new Long(pickList.getId()));
		assertEquals(pickList, savedPickList);
		assertNotSame(pickList, savedPickList);		
	}
	
	@Test
	public void testGetByIdWithPicking() {
		PickList pickList = new PickList(tester, "pickList", plateLayout);
		pickList.getPickings().add(new Picking(tester, "picking", "1a1", "a2"));
		getDAO().save(pickList);
		
		session.flush();
		session.clear();
		
		PickList savedPickList = getDAO().getById(new Long(pickList.getId()));
		assertEquals(pickList, savedPickList);
		assertNotSame(pickList, savedPickList);		
	}
}
