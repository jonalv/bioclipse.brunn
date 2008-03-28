package net.bioclipse.brunn.tests.daos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.pojos.MasterPlate;


public class MasterPlateDAOTest extends AbstractGenericDAOTest {
	
	public IMasterPlateDAO getDAO(){
		return (IMasterPlateDAO)dao;
	}
	
	public MasterPlateDAOTest(){
		super("masterPlateDAO");
	}
	
	/**
	 * Special case because a masterPlate can't be created without the plateManager and a PlateLayout... 
	 */
	@Test
	public void testSave() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1));

		session.flush();
		session.clear();
		
		MasterPlate savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		assertEquals(masterPlate, savedMasterPlate);
		
		LazyLoadingSessionHolder.getInstance().clear();
		savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		assertNotSame(masterPlate, savedMasterPlate);
		
	}
	
	/**
	 * Special case because a masterPlate can't be created without the plateManager and a PlateLayout... 
	 */
	@Test
	public void testDelete() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1));
		assertFalse(masterPlate.isDeleted());
		masterPlate = getDAO().merge(masterPlate);
		getDAO().save(masterPlate);
		
		session.flush();
		session.clear();
		
		getDAO().delete(masterPlate);
		
		session.flush();
		session.clear();
//		LazyLoadingSessionHolder.getInstance().clear();
		
		MasterPlate deleted = getDAO().getById(new Long(masterPlate.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Special case because a masterPlate can't be created without the plateManager and a PlateLayout... 
	 */
	@Test
	public void testGetAll() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		MasterPlate masterPlate2 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate2", plateLayout, masterPlates, 1));
		MasterPlate masterPlate1 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate1", plateLayout, masterPlates, 1));
		
		session.flush();
		session.clear();
	
		List<MasterPlate> list = getDAO().findAll();
		
		assertTrue(list.contains(masterPlate2));
		assertTrue(list.contains(masterPlate1));
		
	}
	
	@Test
	public void testGetById() {

		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1));
		
		session.flush();
		session.clear();
		
		MasterPlate savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		assertEquals(masterPlate, savedMasterPlate);
		
		LazyLoadingSessionHolder.getInstance().clear();
		savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		assertNotSame(masterPlate, savedMasterPlate);
	}
	
	@Test
	public void testFindAllNotDeleted() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		MasterPlate masterPlate2 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate2", plateLayout, masterPlates, 1));
		MasterPlate masterPlate1 = pm.getMasterPlate(pm.createMasterPlate(tester, "masterPlate1", plateLayout, masterPlates, 1));
		MasterPlate masterPlate3 = pm.getMasterPlate(pm.createMasterPlate(tester, "deleted",      plateLayout, masterPlates, 1));
		masterPlate3.delete();
		pm.edit(tester, masterPlate3);
		
		session.flush();
		session.clear();
	
		List<MasterPlate> list = getDAO().findAllNotDeleted();

		assertTrue(  list.contains(masterPlate2) );
		assertTrue(  list.contains(masterPlate1) );
		assertFalse( list.contains(masterPlate3) );
	}
	
	public void testGetPlates() {
		assertNotNull(masterPlate.getPlates());
		masterPlate.getPlates().clear();
		
		getDAO().save(masterPlate);
		session.flush();
		session.clear();
		
		MasterPlate savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		savedMasterPlate.getPlates().add(plate);
		
		getDAO().save(savedMasterPlate);
		session.flush();
		session.clear();
		
		savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		savedMasterPlate.getPlates().add(plate);
		
		getDAO().save(savedMasterPlate);
		session.flush();
		session.clear();
		
		savedMasterPlate = getDAO().getById(new Long(masterPlate.getId()));
		assertTrue( savedMasterPlate.getPlates().contains(plate));
	}
}
