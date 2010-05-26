package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.tests.BaseTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StrangeBugsTest extends BaseTest {

	public StrangeBugsTest() {
	    super();
    }
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testCreateLayoutWellFunction() {
		
		int numberofwells = plateLayout.getLayoutWells().size();
		
		PlateLayout fetched = plm.getPlateLayout(plateLayout.getId());
		
		for( LayoutWell well : fetched.getLayoutWells() ) {
			plm.createWellFunction(tester, "double", well, well.getName()+"*"+well.getName());
		}
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		plm = (IPlateLayoutManager) context.getBean("plateLayoutManager");
		fetched = plm.getPlateLayout(plateLayout.getId());
		
		assertEquals(numberofwells, fetched.getLayoutWells().size());
	}
	
	@Test
	public void testFindAndChange() {
		
		List<Plate> plates = new ArrayList<Plate>();
		IPlateDAO plateDAO = (IPlateDAO) context.getBean("plateDAO");
		
		plates.addAll( plateDAO.findByBarcode(plate.getBarcode()) );

		for(Plate plate : plates) {
			plate.setName("edited");
			plateDAO.save(plateDAO.merge(plate));
		}
		Plate fetchedPlate = plateDAO.getById(plate.getId());
		assertEquals("edited", fetchedPlate.getName());
	}
}
