package guiTestEnvironment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.genericDAO.IDrugSampleDAO;
import net.bioclipse.brunn.genericDAO.IResultTypeDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.LayoutMarker;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.TestConstants;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
 
public abstract class SetupTestEnvironment {
	
	private static ApplicationContext context = 
		new ClassPathXmlApplicationContext("applicationContext.xml");
	
	private static Session session;
	private static SessionFactory sessionFactory;
	
	private static User user;

	private static UniqueFolder dataSets;
	private static UniqueFolder plateTypes;
	private static UniqueFolder plateLayouts;
	private static UniqueFolder compounds;
	private static UniqueFolder cellTypes;
	private static UniqueFolder masterPlates;
	private static UniqueFolder patientCells;
	
	private static IPlateManager       pm;
	private static IPlateLayoutManager plm;
	private static IOriginManager      orm;
	private static ISampleManager      sm;
	private static IFolderManager      fm;
	
	public static void main(String[] args) {
		
		System.out.println("setting up test environment...");
		setupTestEnvironment();
		System.out.println("done");
	}

	public static void setupTestEnvironment() {
	
		System.setProperty(
	            "javax.xml.parsers.SAXParserFactory", 
	            "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
	        );
	        System.setProperty(
	            "javax.xml.parsers.DocumentBuilderFactory", 
	            "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"
	        );
		
		sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		session = SessionFactoryUtils.getSession(sessionFactory,true);
		
		net.bioclipse.brunn.tests.Tools.newCleanDatabase();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
		pm  = (IPlateManager)context.getBean("plateManager");
		plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		orm = (IOriginManager)context.getBean("originManager");
		sm  = (ISampleManager)context.getBean("sampleManager");
		fm  = (IFolderManager)context.getBean("folderManager"); 
		
		user = new User("Administrator");
		user.setPassword("masterkey");
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		user.setAdmin(true);
		userDAO.save(user);
		user = userDAO.getById(user.getId());
		
		//SET UP UNIQUE FOLDERS		
		IUniqueFolderDAO uniqueFolderDAO = (IUniqueFolderDAO)context.getBean("uniqueFolderDAO");
		
		dataSets = new UniqueFolder(user, "Data sets", "data sets");
		uniqueFolderDAO.save(dataSets);
		
		plateTypes = new UniqueFolder(user, "Plate Types", "plateTypes");
		uniqueFolderDAO.save(plateTypes);
		
		plateLayouts = new UniqueFolder(user, "Plate Layouts", "plateLayouts");
		uniqueFolderDAO.save(plateLayouts);
		
		compounds = new UniqueFolder(user, "Compounds", "compounds");
		uniqueFolderDAO.save(compounds);
		
		cellTypes = new UniqueFolder(user, "Cell Types", "cellTypes");
		uniqueFolderDAO.save(cellTypes);
		
		patientCells = new UniqueFolder(user, "Patient Cells", "patientCells");
		uniqueFolderDAO.save(patientCells);
		
		masterPlates = new UniqueFolder( user, "Master Plates", "masterPlates");
		uniqueFolderDAO.save(masterPlates);
		
		IDrugSampleDAO  drugSampleDAO = (IDrugSampleDAO) Springcontact.getBean("drugSampleDAO");
		
		PlateType plateType = plm.getPlateType(plm.createPlateType(user, 24, 16, "384 wells", plateTypes));		
		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(user, "example plateLayout", plateType, plateLayouts));
		
		for( LayoutWell well : plateLayout.getLayoutWells() ) {
			if( well.getCol() != 1 && well.getRow() != 'a') {
				if(well.getCol() == 2) {
					well.getLayoutMarkers().add( new LayoutMarker(user, "p", well) );
				}
				if(well.getCol() < 7 && well.getCol() > 2) {
					well.getLayoutMarkers().add( new LayoutMarker(user, "M"+(well.getRow()-'a'), well) );
					if(well.getRow() == 'c') {
						well.getLayoutMarkers().add( new LayoutMarker(user, "M"+(well.getRow()-'a'-1), well) );
					}
				}
			}
		}
		
		long cellOriginId = orm.createCellOrigin(user, "example cell line", cellTypes);
		CellOrigin cellOrigin = orm.getCellOrigin(cellOriginId);
		DrugOrigin drugOrigin = null;
		try {
	        long id = orm.createDrugOrigin(user, "compound", new FileInputStream(TestConstants.getTestMolFile()), 23.4, compounds);
	        drugOrigin = orm.getDrugOrigin(id);
        }
        catch (FileNotFoundException e) {
	        e.printStackTrace();
        }
        catch (IOException e) {
	        e.printStackTrace();
        }
        
        Folder exampleFolder = fm.getFolder(fm.createFolder(user, "example data", dataSets));
        
        /*
         * CREATE WELL AND PLATE FUNCTIONS
         */
        for( LayoutWell well : plateLayout.getLayoutWells() ) {
			plm.createWellFunction(user, "square", well, well.getName()+"*"+well.getName());
		}
		
		plateLayout = plm.getPlateLayout(plateLayout.getId());
//		plm.createPlateFunction(user, "testFunction", plateLayout, "(a1+a2)/2");
//		plm.createPlateFunction(user, "testFunction2", plateLayout, "(a1+a2)/2", 0.2, 12);
        
        /*
         * CREATE MASTERPLATE
         */
        MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(user, "a masterPlate", plateLayout, masterPlates, 5));

        /*
         * ADD DRUGSAMPLES TO MASTERPLATE
         */
		LinkedList<SampleMarker> sampleMarkersM1 = new LinkedList<SampleMarker>();
		for(Well well : masterPlate.getWells()) {
			for(SampleMarker sm : well.getSampleMarkers()) {
				if( "M1".equals(sm.getName()) ){
					sampleMarkersM1.add(sm);
				}
			}
		}
		int value = 1;
		for(SampleMarker sm : sampleMarkersM1) {
//			DrugSample ds = new DrugSample( user, 
//					                        "compound 1", 
//					                        23.0*value++, 
//					                        drugOrigin, 
//					                        sm.getWell().getSampleContainer(), 
//					                        ConcUnit.UNIT );
//			ds.setSampleMarker(sm);
//			sm.setSample(ds);
		}

		pm.edit(user, masterPlate);
        
		/*
		 * CREATE PLATES
		 */
		pm.createPlate(user,"example plate", "2708", exampleFolder, masterPlate, cellOrigin, null);
		
		long plateId = pm.createPlate(user,"another plate", "T0001", exampleFolder, masterPlate, cellOrigin, null);
		Plate plate = pm.getPlate(plateId);

		/*
		 * CREATE PLATE RESULTS 
		 */
		/*
		 * get orcaResultType and if it doesn't exist create it
		 */
		IOperationManager om = (IOperationManager) Springcontact.getBean("operationManager");
		String orcaResultTypeName = "orcaResultType";
		List<ResultType> resultTypes = om.getResultTypeByName(orcaResultTypeName);
		ResultType orcaResultType;
		if(resultTypes.size() == 0) {
			orcaResultType = new ResultType(user, orcaResultTypeName, 1 );
			IResultTypeDAO resultDAO = (IResultTypeDAO) context.getBean("resultTypeDAO");
			resultDAO.save(orcaResultType);
		}
		else {
			orcaResultType = resultTypes.get(0);
		}
		
		/*
		 * get orcaInstrument and if it doesn't exist create it
		 */
		String orcaInstruementName = "ORCA";
		List<Instrument> instruments = om.getInstrumentByName(orcaInstruementName);
		Instrument orca;
		if(instruments.size() == 0) {
			orca = new Instrument(user, orcaInstruementName);
		}
		else {
			orca = instruments.get(0);
		}
		
		for( Well well : plate.getWells() ) {
			
			Measurement measurement = new Measurement( user,
					                                   "exampleMeasurement", 
					                                   well.getSampleContainer().getWorkList(), 
					                                   orca, 
					                                   orcaResultType );
			double[] resultValue = new double[1];
			resultValue[0] = Math.random()*65000;
			Result result = new Result(user, "exampleResult", resultValue, 1 );  //TODO: deal with version numbers
			measurement.addResult(result);
		}
		
		pm.edit(user, plate);

		plm.edit(user, plateLayout);
		plateLayout = plm.getPlateLayout(plateLayout.getId());

		
	}
}
