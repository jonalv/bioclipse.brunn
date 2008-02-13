package net.bioclipse.brunn.tests;

import java.io.FileInputStream;
import java.sql.Timestamp;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.annotation.IAnnotationManager;
import net.bioclipse.brunn.business.audit.IAuditManager;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.ICellSampleDAO;
import net.bioclipse.brunn.genericDAO.IDrugOriginDAO;
import net.bioclipse.brunn.genericDAO.IDrugSampleDAO;
import net.bioclipse.brunn.genericDAO.IInstrumentDAO;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.genericDAO.IMeasurementDAO;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.genericDAO.IPlateLayoutDAO;
import net.bioclipse.brunn.genericDAO.IPlateTypeDAO;
import net.bioclipse.brunn.genericDAO.IResultTypeDAO;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.AbstractPlate;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.pojos.WorkList;
import net.bioclipse.brunn.tests.daos.CellSampleDAOTest;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tools.ant.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class BaseTest {

	protected static ApplicationContext context;
	
	static {
		Springcontact.getBean("userDAO");
		context = Springcontact.CONTEXT;
	}

	protected Session session;
	protected SessionFactory sessionFactory;
	
	protected User tester;
	protected Project project;
	protected MasterPlate masterPlate;
	protected Plate plate;
	protected PlateLayout plateLayout;
	protected PlateType plateType;
	protected SampleContainer sampleContainer;
	protected WorkList workList;
	protected Instrument instrument;
	protected ResultType resultType;
	protected Measurement measurement;
	protected DrugOrigin drugOrigin;
	protected CellOrigin cellOrigin;
	protected Folder folder;
	protected LayoutWell layoutWell;
	protected DrugSample drugSample;
	protected CellSample cellSample;
	
	protected UniqueFolder projects;
	protected UniqueFolder plateTypes;
	protected UniqueFolder plateLayouts;
	protected UniqueFolder compounds;
	protected UniqueFolder cellTypes;
	protected UniqueFolder masterPlates;
	
	protected ISampleManager sm;
	protected IPlateManager pm;
	protected IPlateLayoutManager plm;
	protected IAnnotationManager am;
	protected IOperationManager om;
	protected IAuditManager aum;
	protected IOriginManager orm;
	protected IFolderManager fm;
	
	public BaseTest() {
 		sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		session = SessionFactoryUtils.getSession(sessionFactory,true);
	}

	@BeforeClass
	public static void setBeanStuff() {
		BasicDataSource dataSource = (BasicDataSource) context.getBean("dataSource");
		dataSource.setUrl("jdbc:mysql://localhost:3306/lis");
	}
	
	protected void setUp() throws Exception {

		LazyLoadingSessionHolder.getInstance().clear();
		net.bioclipse.brunn.tests.Tools.newCleanDatabase();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
		pm  = (IPlateManager)context.getBean("plateManager");
		plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		sm  = (ISampleManager)context.getBean("sampleManager");
		am  = (IAnnotationManager)context.getBean("annotationManager");
		om  = (IOperationManager)context.getBean("operationManager");
		aum = (IAuditManager)context.getBean("auditManager");
		orm = (IOriginManager)context.getBean("originManager");
		fm  = (IFolderManager) context.getBean("folderManager"); 
		
		tester = new User("tester");
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		userDAO.save(tester);
		
		IUniqueFolderDAO uniqueFolderDAO = (IUniqueFolderDAO)context.getBean("uniqueFolderDAO");
		
		projects = new UniqueFolder(tester, "Projects", "projects");
		uniqueFolderDAO.save(projects);
		
		plateTypes = new UniqueFolder(tester, "Plate Types", "plateTypes");
		uniqueFolderDAO.save(plateTypes);
		
		plateLayouts = new UniqueFolder(tester, "Plate Layouts", "plateLayouts");
		uniqueFolderDAO.save(plateLayouts);
		
		compounds = new UniqueFolder(tester, "Compounds", "compounds");
		uniqueFolderDAO.save(compounds);
		
		cellTypes = new UniqueFolder(tester, "Cell Types", "cellTypes");
		uniqueFolderDAO.save(cellTypes);
		
		masterPlates = new UniqueFolder(tester, "Master Plates", "masterPlates");
		uniqueFolderDAO.save(masterPlates);
		
		folder = fm.getFolder(fm.createFolder(tester, "name", projects));
		
		plateType = new PlateType(tester, 8, 12, "96 wells");
		IPlateTypeDAO plateTypeDAO = (IPlateTypeDAO)context.getBean("plateTypeDAO");
		plateTypeDAO.save(plateType);
		
		plateLayout = new PlateLayout(tester, "a plateLayout", plateType);
		IPlateLayoutDAO plateLayoutDAO = (IPlateLayoutDAO)context.getBean("plateLayoutDAO");
		plateLayoutDAO.save(plateLayout);
		
		masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "a masterPlate", plateLayout, folder, 5));
		IMasterPlateDAO masterPlateDAO = (IMasterPlateDAO)context.getBean("masterPlateDAO");
		masterPlate = masterPlateDAO.merge(masterPlate);
		masterPlateDAO.save(masterPlate);
		
		cellOrigin = new CellOrigin(tester, "cellOrigin");
		ICellOriginDAO cellOriginDAO = (ICellOriginDAO) context.getBean("cellOriginDAO");
		cellOriginDAO.save(cellOrigin);
		
		drugOrigin = new DrugOrigin( tester, 
                "drugOrigin", 
                new FileInputStream(TestConstants.getTestMolFile()), 
                12.0 );
		IDrugOriginDAO drugOriginDAO = (IDrugOriginDAO)context.getBean("drugOriginDAO");
		drugOriginDAO.save(drugOrigin);
		
		drugSample = new DrugSample( tester, 
				                     "drugSample", 
				                     23.0, 
				                     drugOrigin, 
				                     masterPlate.getWell(1, 'a').getSampleContainer(), 
				                     ConcUnit.UNIT );
		
		SampleMarker sampleMarker = new SampleMarker( tester,
                                                      "sampleMArker", 
                                                      drugSample, 
                                                      masterPlate.getWell(1, 'a') );
		
		IDrugSampleDAO drugSampleDAO = (IDrugSampleDAO) context.getBean("drugSampleDAO");
		drugSampleDAO.save(drugSample);
		
		plm.createPlateFunction(tester, "a test Function", plateLayout, "2345");
		pm.edit(tester, masterPlate);
		pm.createPlateFunction(tester, "testPlateFunction", masterPlate, "2*3");
		plate = pm.getPlate(pm.createPlate(tester, "a plate", "a barcode", folder,  masterPlate, cellOrigin, null));
		IPlateDAO plateDAO = (IPlateDAO)context.getBean("plateDAO");
		plate = plateDAO.merge(plate);
		plateDAO.save(plate);
		
		sampleContainer = ((Well)plate.getWells().toArray()[0]).getSampleContainer();
		ISampleContainerDAO sampleContainerDAO = (ISampleContainerDAO) context.getBean("sampleContainerDAO");
//		sampleContainderDAO.save(sampleContainer);
		
		workList = sampleContainer.getWorkList();
		
		instrument = new Instrument(tester, "instrument");
		IInstrumentDAO instrumentDAO = (IInstrumentDAO)context.getBean("instrumentDAO");
		instrumentDAO.save(instrument);
		
		resultType = new ResultType(tester, "resultType", 2);
		IResultTypeDAO resultTypeDAO = (IResultTypeDAO)context.getBean("resultTypeDAO");
		resultTypeDAO.save(resultType);
		
//		Plate p = (Plate) workList.getSampleContainer().getWell().getPlate();
//		p = plateDAO.merge(p);
//		plateDAO.save(p);
		
		measurement = new Measurement(tester, "measurement", workList, instrument, resultType);
		IMeasurementDAO measurementDAO = (IMeasurementDAO)context.getBean("measurementDAO");
		measurementDAO.save(measurement);
		
		sampleContainer = sampleContainerDAO.merge(workList.getSampleContainer());
		sampleContainerDAO.save(sampleContainer);
		
		layoutWell = (LayoutWell) plateLayout.getLayoutWells().toArray()[0];
	}

	protected void tearDown() throws Exception {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}
}
