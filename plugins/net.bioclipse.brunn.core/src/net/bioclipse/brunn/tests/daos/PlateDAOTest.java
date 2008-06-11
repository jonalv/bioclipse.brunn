package net.bioclipse.brunn.tests.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;

import org.junit.Test;

/**
 * Tests all the functionality of the <code>PlateDAO</code>
 * 
 * @author jonathan
 *
 */
public class PlateDAOTest extends AbstractGenericDAOTest {
	
	public IPlateDAO getDAO(){
		return (IPlateDAO)dao;
	}
	
	public PlateDAOTest(){
		super("plateDAO");
	}

	/**
	 * Special case because a Plate can't be created without the plateManager, a masterPlate and a lot off stuff... 
	 */
	@Test
	public void testSave() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "12345678", projects, masterPlate, cellOrigin, null));

		session.flush();
		session.clear();

		Plate savedPlate = getDAO().getById(plate.getId());
		assertEquals(plate, savedPlate);
		
		LazyLoadingSessionHolder.getInstance().flush();
		LazyLoadingSessionHolder.getInstance().clear();
		savedPlate = getDAO().getById(plate.getId());
		
		assertNotSame(plate, savedPlate);
		assertTrue(plate.getId() == savedPlate.getId());
	}
	
	/**
	 * Special case because a Plate can't be created without the plateManager, a masterPlate and a lot off stuff...
	 */
	@Test
	public void testDelete() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "12345678", projects, masterPlate, cellOrigin, null));
		assertFalse(plate.isDeleted());
		plate = getDAO().merge(plate);
		getDAO().save(plate);
		
		session.flush();
		session.clear();
		
		getDAO().delete(plate);
		
		session.flush();
		session.clear();
		LazyLoadingSessionHolder.getInstance().clear();
		
		Plate deleted = getDAO().getById(new Long(plate.getId()));
		assertTrue(deleted.isDeleted());
	}

	/**
	 * Special case because a Plate can't be created without the plateManager, a masterPlate and a lot off stuff...
	 */
	@Test
	public void testGetAll() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate1 = pm.getPlate(pm.createPlate(tester, "a plate", "34567", projects, masterPlate, cellOrigin, null));
		Plate plate2 = pm.getPlate(pm.createPlate(tester, "a plate", "34767", projects, masterPlate, cellOrigin, null));
		
		session.flush();
		session.clear();
	
		List list = getDAO().findAll();
		assertTrue(list.contains(plate1));
		assertTrue(list.contains(plate2));
	}

	@Test
	public void testGetById() {

		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "35678567", projects, masterPlate, cellOrigin, null));
		
		session.flush();
		session.clear();
		
		Plate savedPlate = getDAO().getById(plate.getId());
		assertEquals(plate, savedPlate);
		
		LazyLoadingSessionHolder.getInstance().flush();
		LazyLoadingSessionHolder.getInstance().clear();
		savedPlate = getDAO().getById(plate.getId());
		
		assertNotSame(plate, savedPlate);
		assertTrue(plate.getId() == savedPlate.getId());
	}
	
	@Test
	public void testGetByIdWithSampleMarker() {

		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "123456789", projects, masterPlate, cellOrigin, null));
		Well well = plate.getWells().toArray(new Well[0])[0];
		well.getSampleMarkers().add(new SampleMarker(tester, "sampleMarker", null, well));
		
		plate = getDAO().merge(plate);
		getDAO().save(plate);
		
		session.flush();
		session.clear();
		
		Plate savedPlate = getDAO().getById(new Long(plate.getId()));
		assertEquals(plate, savedPlate);
		assertNotSame(plate, savedPlate);
		assertTrue(plate.getId() == savedPlate.getId());
	}
	
	@Test
	public void testFindAllPlateBarcodes() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "barcode", projects, masterPlate, cellOrigin, null));
		
		plate = getDAO().merge(plate);
		getDAO().save(plate);
		
		session.flush();
		session.clear();
		
		List<String> barcodes = getDAO().findAllPlateBarcodes();
		assertTrue( barcodes.contains( "barcode" ) );
	}
	
	@Test
	public void testFindByBarcode() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		Plate plate = pm.getPlate(pm.createPlate(tester, "a plate", "123456789", projects, masterPlate, cellOrigin, null));
		Well well = plate.getWells().toArray(new Well[0])[0];
		well.getSampleMarkers().add(new SampleMarker(tester, "sampleMarker", null, well));
		
		plate = getDAO().merge(plate);
		getDAO().save(plate);
		
		session.flush();
		session.clear();
		
		Plate savedPlate = getDAO().getById(plate.getId());
		assertEquals(plate, savedPlate);
		
		LazyLoadingSessionHolder.getInstance().flush();
		LazyLoadingSessionHolder.getInstance().clear();
		savedPlate = getDAO().getById(plate.getId());
		
		assertNotSame(plate, savedPlate);
		assertTrue(plate.getId() == savedPlate.getId());
	}
	
	@Test
	public void testFindAllNotDeleted() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		Plate plate1 = pm.getPlate(pm.createPlate(tester, "a plate", "34567", projects, masterPlate, cellOrigin, null));
		Plate plate2 = pm.getPlate(pm.createPlate(tester, "a plate", "34767", projects, masterPlate, cellOrigin, null));
		Plate plate3 = pm.getPlate(pm.createPlate(tester, "deleted", "12213", projects, masterPlate, cellOrigin, null));
		plate3.delete();
		pm.edit(tester, plate3);
		
		session.flush();
		session.clear();
	
		List list = getDAO().findAllNotDeleted();
		assertTrue(  list.contains(plate1) );
		assertTrue(  list.contains(plate2) );
		assertFalse( list.contains(plate3) );
	}
	
	@Test
	public void testGetMasterPlate() {
			
		plate.setMasterPlate(masterPlate);
		
		getDAO().save(plate);
		session.flush();
		session.clear();
		plate = getDAO().getById(plate.getId());
		
		assertEquals( masterPlate, plate.getMasterPlate() );
	}
	
	@Test
	public void testEditingOutlier() {
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		boolean before = plate.getWell(1, 'a').getOutlier();
		plate.getWell(1, 'a').setOutlier(!before);
		
		pm.edit(tester, plate);
		
		session.flush();
		session.clear();
		
		Plate loadedPlate = pm.getPlate( plate.getId() );
		assertTrue( loadedPlate.getWell(1, 'a').isOutlier() != before );
	}
}
