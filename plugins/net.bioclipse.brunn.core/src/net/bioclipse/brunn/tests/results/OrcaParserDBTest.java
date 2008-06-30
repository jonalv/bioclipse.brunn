package net.bioclipse.brunn.tests.results;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.genericDAO.IPlateLayoutDAO;
import net.bioclipse.brunn.genericDAO.IPlateTypeDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.results.PlateRead;
import net.bioclipse.brunn.results.orcaParser.OrcaParser;
import net.bioclipse.brunn.results.orcaParser.OrcaParser.OrcaPlateRead;
import net.bioclipse.brunn.tests.BaseTest;

import org.apache.tools.ant.Project;
import org.eclipse.swt.graphics.Point;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class OrcaParserDBTest extends BaseTest {
	
	
	public OrcaParserDBTest() {
		super();
	}
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		PlateType plateType = new PlateType(tester, 16, 24, "384 wells");
		IPlateTypeDAO plateTypeDAO = (IPlateTypeDAO)context.getBean("plateTypeDAO");
		plateTypeDAO.save(plateType);
		
		PlateLayout plateLayout = new PlateLayout(tester, "a plateLayout", plateType);
		IPlateLayoutDAO plateLayoutDAO = (IPlateLayoutDAO)context.getBean("plateLayoutDAO");
		plateLayoutDAO.save(plateLayout);

		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "a masterPlate", plateLayout, folder, 1));
		IMasterPlateDAO masterPlateDAO = (IMasterPlateDAO)context.getBean("masterPlateDAO");
		masterPlate = masterPlateDAO.merge(masterPlate);
		masterPlateDAO.save(masterPlate);
		
		plate = pm.getPlate(pm.createPlate(tester, "a plate", "2708", folder,  masterPlate, cellOrigin, null));
		IPlateDAO plateDAO = (IPlateDAO)context.getBean("plateDAO");
		plate = plateDAO.merge(plate);
		plateDAO.save(plate);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testImportData() {
		OrcaParser parser = null;
		try {
			String path = ClassLoader.getSystemResource("TestFiles" + File.separator + "threeplateswithbarcodes.log").getPath();
	        parser = new OrcaParser(new File(path));
        }
        catch (FileNotFoundException e) {
	        fail( e.getMessage() );
        }
        List<PlateRead> plateReads = parser.getPlatesInFile();
        System.out.println("ORCAPARSER SLURPED " + plateReads.size() + " PLATES IN FILE");
        /*
         * Check that we have one PlateRead with the correct barcode and the correct intensity
         */
        assertEquals( 1, plateReads.size() );
        assertEquals( plate.getBarcode(), plateReads.get(0).getBarCode() );
        assertEquals( 800, ((OrcaPlateRead)plateReads.get(0)).getIntensity() );
        
        List<Plate> plates = new LinkedList<Plate>();
        plates.add(plate);
        plates = parser.addResultsTo(tester, plates);
        
        System.out.println("ORCAPARSER ADDED RESULTS TO PLATES");
        
        assertEquals( 1 , plates.size() );
        plate = plates.get(0);
        /*
         * Check a result value on the Plate
         */
        assertEquals( 173.0, plate.getWell( new Point(1, 1)).getSampleContainer().getWorkList().
        		                          getAbstractOperations().toArray(new Measurement[0])[0].
        		                          getResults().toArray(new Result[0])[0].getResultValue()[0] );
        
        for(Plate plate : plates) {
        	pm.edit(tester, plate);
        }
        
        assertEquals( plate, pm.getPlate(plate.getId()));
	}
}
