package net.bioclipse.brunn.tests.daos;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.genericDAO.IAnnotationDAO;
import net.bioclipse.brunn.genericDAO.ICellOriginDAO;
import net.bioclipse.brunn.genericDAO.IDrugOriginDAO;
import net.bioclipse.brunn.genericDAO.IGenericDAO;
import net.bioclipse.brunn.genericDAO.IInstrumentDAO;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.genericDAO.IPatientOriginDAO;
import net.bioclipse.brunn.genericDAO.IPlateDAO;
import net.bioclipse.brunn.genericDAO.IPlateLayoutDAO;
import net.bioclipse.brunn.genericDAO.IPlateTypeDAO;
import net.bioclipse.brunn.genericDAO.IResultTypeDAO;
import net.bioclipse.brunn.genericDAO.ISampleContainerDAO;
import net.bioclipse.brunn.genericDAO.IUniqueFolderDAO;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.TestConstants;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.tools.ant.Project;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author jonathan
 *
 *Abstract class for keeping a DAO object for the class under test. It handles the Session handling
 *for that DAO, binds it during setUp and releases it during tearDown. This ensures that the lazy-loading works. 
 *
 *The Session needs to be flushed and cleared betweem save and load by <code>session.flush(); session.clear(); </code> 
 *in the tests. Otherwise the objects will be loaded from the cashe and not from the persistant storing.
 *
 *This class also persists a lot of objects and keeps references to them which easily can be used when testing.
 */
public abstract class AbstractGenericDAOTest {

	static {
		System.setProperty(
            "javax.xml.parsers.SAXParserFactory", 
            "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl"
        );
        System.setProperty(
            "javax.xml.parsers.DocumentBuilderFactory", 
            "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"
        );
        System.setProperty(
        	"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl",
        	"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"
        );
	}
	
	protected IGenericDAO dao;
	protected Session     session;

	protected static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	protected User            tester;
	protected MasterPlate     masterPlate;
	protected Plate           plate;
	protected PlateLayout     plateLayout;
	protected PlateType       plateType;
	protected Instrument      instrument;
	protected DrugOrigin      drugOrigin;
	protected CellOrigin      cellOrigin;
	protected SampleContainer sampleContainer;
	protected ResultType      resultType;
	protected PatientOrigin   patientOrigin;
	
	protected UniqueFolder projects;
	protected UniqueFolder plateTypes;
	protected UniqueFolder plateLayouts;
	protected UniqueFolder compounds;
	protected UniqueFolder cellTypes;
	protected UniqueFolder masterPlates;
	
	protected Annotation enumAnnotation;
	protected Annotation floatAnnotation;
	protected Annotation textAnnotation;
	
	protected static final int timeout = 5000;
	
	/**
	 * @param DAOBeanName The Bean name of the DAO that should be loaded from Springs container.
	 */
	public AbstractGenericDAOTest(String DAOBeanName){

		dao = (IGenericDAO)context.getBean(DAOBeanName);
		session = SessionFactoryUtils.getSession(dao.getSessionFactory(),true);
	}
	
	@BeforeClass
	public static void setBeanStuff() {
		BasicDataSource dataSource = (BasicDataSource) context.getBean("dataSource");
		dataSource.setUrl("jdbc:mysql://localhost:3306/brunn");
	}
	
	/* 
	 * Creates a clean database
	 * opens a session
	 * creates already persistent objects for easy testing.
	 */
	@Before
	public void setUp() throws Exception {

		LazyLoadingSessionHolder.getInstance().clear();
		net.bioclipse.brunn.tests.Tools.newCleanDatabase();
		TransactionSynchronizationManager.bindResource(dao.getSessionFactory(), new SessionHolder(session));

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
		
		masterPlates = new UniqueFolder( tester, "Master Plates", "masterPlates");
		uniqueFolderDAO.save(masterPlates);
			
		plateType = new PlateType(tester, 8, 12, "96 wells");
		IPlateTypeDAO plateTypeDAO = (IPlateTypeDAO)context.getBean("plateTypeDAO");
		plateTypeDAO.save(plateType);
		
		plateLayout = new PlateLayout(tester, "a plateLayout", plateType);
		IPlateLayoutDAO plateLayoutDAO = (IPlateLayoutDAO)context.getBean("plateLayoutDAO");
		plateLayoutDAO.save(plateLayout);
		
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		masterPlate = pm.getMasterPlate(pm.createMasterPlate(tester, "a masterPlate", plateLayout, projects, 5));
		IMasterPlateDAO masterPlateDAO = (IMasterPlateDAO)context.getBean("masterPlateDAO");
		masterPlate = masterPlateDAO.merge(masterPlate);
		masterPlateDAO.save(masterPlate);
			
		instrument = new Instrument(tester, "instrument");
		IInstrumentDAO instrumentDAO = (IInstrumentDAO)context.getBean("instrumentDAO");
		instrumentDAO.save(instrument);
		
		drugOrigin = new DrugOrigin( tester, 
				                     "drugOrigin", 
				                     new FileInputStream( TestConstants.getTestMolFile() ), 567.67);
		IDrugOriginDAO drugOriginDAO = (IDrugOriginDAO)context.getBean("drugOriginDAO");
		drugOriginDAO.save(drugOrigin);
		
		cellOrigin = new CellOrigin(tester, "cellOrigin");
		ICellOriginDAO cellOriginDAO = (ICellOriginDAO)context.getBean("cellOriginDAO");
		cellOriginDAO.save(cellOrigin);
		
		plate = pm.getPlate(pm.createPlate(tester, "a plate", "a barcode", projects, masterPlate, cellOrigin, null));
		IPlateDAO plateDAO = (IPlateDAO)context.getBean("plateDAO");
		plate = plateDAO.merge(plate);
		plateDAO.save(plate);
		
		//grabs some well
		sampleContainer = new SampleContainer(tester, "sampleContainer", (Well)plate.getWells().toArray()[0]);
		ISampleContainerDAO sampleContainerDAO = (ISampleContainerDAO)context.getBean("sampleContainerDAO");
		sampleContainerDAO.save(sampleContainer);
		
		resultType = new ResultType(tester, "resultType", 2);
		IResultTypeDAO resultTypeDAO = (IResultTypeDAO)context.getBean("resultTypeDAO");
		resultTypeDAO.save(resultType);
		
		patientOrigin = new PatientOrigin(tester, "patient origin", "abcdefg");
		IPatientOriginDAO patientOriginDAO = (IPatientOriginDAO)context.getBean("patientOriginDAO");
		patientOriginDAO.save(patientOrigin);
		
		Set<String> possibleValues = new HashSet<String>();
		possibleValues.add("blue");
		possibleValues.add("red");
		possibleValues.add("green");
		
		this.enumAnnotation = 
			new Annotation(tester, "enumtest", possibleValues, AnnotationType.ENUM_ANNOTATION);
		this.floatAnnotation = 
			new Annotation(tester, "floattest", new HashSet<String>(), AnnotationType.FLOAT_ANNOTATION);
		this.textAnnotation =
			new Annotation(tester, "texttest", new HashSet<String>(), AnnotationType.TEXT_ANNOTATION);
		
		IAnnotationDAO masterAnnotationTypeDAO = 
			(IAnnotationDAO)this.context.getBean("annotationDAO");
		
		masterAnnotationTypeDAO.save(enumAnnotation);
		masterAnnotationTypeDAO.save(textAnnotation);
		masterAnnotationTypeDAO.save(floatAnnotation);
	}
	
	
	/* 
	 * closes the session
	 */
	@After
	public void tearDown(){
	    session.cancelQuery();
		TransactionSynchronizationManager.unbindResource(dao.getSessionFactory());
		SessionFactoryUtils.releaseSession(session, dao.getSessionFactory());
	}
}
