package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashSet;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.business.annotation.AnnotationManager;
import net.bioclipse.brunn.business.annotation.IAnnotationManager;
import net.bioclipse.brunn.business.audit.AuditManager;
import net.bioclipse.brunn.business.audit.IAuditManager;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.business.operation.OperationManager;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.origin.OriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plate.PlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.business.plateLayout.PlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IUserDAO;
import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;
import net.bioclipse.brunn.pojos.AbstractAuditableObject;
import net.bioclipse.brunn.pojos.Annotation;
import net.bioclipse.brunn.pojos.AnnotationType;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.pojos.WorkList;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.LightBaseTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Tests that the different transactions in the system takes place as they should
 * 
 * @author jonathan
 *
 */
public class TransactionTest extends BaseTest {

	public TransactionTest() {
	    super();
    }

	private User tester;
	private IAuditService mockAuditService = new MockAuditService();
	
	@Before
	public void setUp() throws Exception {
		
		super.setUp();
		
		tester = new User("tester");
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		userDAO.save(tester);
		tester = userDAO.getById(tester.getId());
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
/*
 * ANNOTATIONSMANAGERS METHODS
 */	
	/**
	 * Tests the transactions that should take place when creating an <code>Annotation</code>
	 */
	@Test
	public void testNewAnnotation() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
				
		int countBefore = am.getAllAnnotations().size();
		
		am.setAuditService(mockAuditService);
		
		try {
			am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>());
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = am.getAllAnnotations().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when performing an annotation
	 */
	@Test
	public void testAnnotate() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
			
		Annotation textAnnotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>()));
		
		int countBefore = am.getAllAnnotationInstances().size();
		am.setAuditService(mockAuditService);
		try {
			am.annotate(tester, new Instrument(), textAnnotation, "blue");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = am.getAllAnnotationInstances().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when deleting an annotationInstance
	 */
	@Test
	public void testDeleteAbstractAnnotationInstance() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
		
		Annotation textAnnotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>()));
		AbstractAnnotationInstance ai =  
			am.getAnnotationInstance(am.annotate(tester, instrument, textAnnotation, "blue"));
		am.setAuditService(mockAuditService);
		try {
			am.delete(tester, ai);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		ai = am.getAnnotationInstance(ai.getId());	
		assertFalse("The delete should not have been performed", ai.isDeleted());
	}

	/**
	 * Tests the transactions that should take place when deleting an <code>Annotation</code>
	 */
	@Test
	public void testDeleteAnnotation() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
		
		Annotation textAnnotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>()));
		
		am.setAuditService(mockAuditService);
		
		try{
			am.delete(tester, textAnnotation);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		textAnnotation = am.getAnnotation(textAnnotation.getId());
			
		assertFalse("The delete should not have been performed", textAnnotation.isDeleted());
	}
	
	/**
	 * Tests the transactions that should take place when editing an <code>AnnotationInstance</code>
	 */
	@Test
	public void testEditAbstractAnnotationInstance() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
		
		Annotation textAnnotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>()));
		AbstractAnnotationInstance ai =  
			am.getAnnotationInstance(am.annotate(tester, instrument, textAnnotation, "blue"));
		
		am.setAuditService(mockAuditService);
		
		try{
			ai.setName("edited");
			am.edit(tester, ai);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		ai = am.getAnnotationInstance(ai.getId());
			
		assertFalse("The edit should not have been performed", ai.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when editing an <code>Annotation</code>
	 */
	@Test
	public void testEditAnnotation() {
		  
		IAnnotationManager am = (IAnnotationManager)context.getBean("annotationManager");
		
		Annotation textAnnotation = 
			am.getAnnotation(am.createAnnotation(tester, AnnotationType.TEXT_ANNOTATION, "", new HashSet<String>()));
		
		am.setAuditService(mockAuditService);
		
		try{
			textAnnotation.setName("edited");
			am.edit(tester, textAnnotation);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		textAnnotation = am.getAnnotation(textAnnotation.getId());
			
		assertFalse("The edit should not have been performed", textAnnotation.getName().equals("edited"));
	}
	
/*
 * AUDITMANAGERS METHODS
 */
	/**
	 * Tests the transactions that should take place when creating an <code>User</code>
	 */
	@Test
	public void testNewUser() {
		  
		IAuditManager am = (IAuditManager)context.getBean("auditManager");
		
		int countBefore = am.getAllUsers().size();
		
		am.setAuditService(mockAuditService);
		
		try{
			am.createUser(tester, "newUser", "", false);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = am.getAllUsers().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when editing an <code>User</code>
	 */
	@Test
	public void testEditUser() {
		  
		IAuditManager am = (IAuditManager)context.getBean("auditManager");
		
		User user = am.getUser(am.createUser(tester, "newUser", "", false));
		am.setAuditService(mockAuditService);
		try{
			user.setName("edited");
			am.edit(user);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		user = am.getUser(user.getId());
		
		assertFalse("The edit should not have been saved", user.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when deleting an <code>User</code>
	 */
	@Test
	public void testDeleteUser() {
		  
		IAuditManager am = (IAuditManager)context.getBean("auditManager");
		
		User user = am.getUser(am.createUser(tester, "newUser", "", false));
		
		am.setAuditService(mockAuditService);
		
		try{
			am.delete(user);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		user = am.getUser(user.getId());
		
		assertFalse("The edit should not have been saved", user.isDeleted());
	}
/*
 * OPERATIONMANAGERS METHODS
 */
	/**
	 * Tests the transactions that should take place when creating an <code>Instrument</code>
	 */
	@Test
	public void testNewInstrument() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		int countBefore = om.getAllInstruments().size();
		
		om.setAuditService(mockAuditService);
		
		try{
			om.createInstrument(tester, "instrument");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = om.getAllInstruments().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>ResultType</code>
	 */
	@Test
	public void testNewResultType() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		int countBefore = om.getAllResultTypes().size();
		
		om.setAuditService(mockAuditService);
		
		try{
			om.createResultType(tester, "resultType", 2);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = om.getAllResultTypes().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>Measurement</code>
	 */
	@Test
	public void testNewMeasurement() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2));
		
		int countBefore = om.getAllMeasurements().size();
		
		om.setAuditService(mockAuditService);
		
		try{
			om.createMeasurement(tester, "measurement", new WorkList(), instrument, resultType);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = om.getAllMeasurements().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when editing an <code>Instrument</code>
	 */
	@Test
	public void testEditInstrument() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		
		om.setAuditService(mockAuditService);
		
		try{
			instrument.setName("edited");
			om.edit(tester, instrument);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		instrument = om.getInstrument(instrument.getId());
		
		assertFalse("The edit should not have been saved", instrument.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when editing a <code>ResultType</code>
	 */
	@Test
	public void testEditResultType() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2));
		
		om.setAuditService(mockAuditService);
		
		try{
			resultType.setName("edited");
			om.edit(tester, resultType);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		resultType = om.getResultType(resultType.getId());
			
		assertFalse("The edit should not have been saved", resultType.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when editing a <code>Measurement</code>
	 */
	@Test
	public void testEditMeasurement() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument   = om.getInstrument(om.createInstrument(tester, "instrument"));
		ResultType resultType   = om.getResultType(om.createResultType(tester, "resultType", 2));
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, 
				                                   "measurement", 
				                                   workList, 
				                                   instrument, 
				                                   resultType));
		
		om.setAuditService(mockAuditService);
		
		try {
			measurement.setName("edited");
			om.edit(tester, measurement);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		measurement = om.getMeasurement(measurement.getId());
				
		assertFalse("The edit should not have been saved", measurement.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when deleting an <code>Instrument</code>
	 */
	@Test
	public void testDeleteInstrument() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		
		om.setAuditService(mockAuditService);
		
		try{
			om.delete(tester, instrument);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		instrument = om.getInstrument(instrument.getId());
		
		assertFalse("The edit should not have been saved", instrument.isDeleted());
	}
	
	/**
	 * Tests the transactions that should take place when deleting a <code>ResultType</code>
	 */
	@Test
	public void testDeleteResultType() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 2));
		
		om.setAuditService(mockAuditService);
		
		try{
			om.delete(tester, resultType);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		resultType = om.getResultType(resultType.getId());
			
		assertFalse("The edit should not have been saved", resultType.isDeleted());
	}
	
	/**
	 * Tests the transactions that should take place when editing a <code>Measurement</code>
	 */
	@Test
	public void testDeleteMeasurement() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument   = om.getInstrument(om.createInstrument(tester, "instrument"));
		ResultType resultType   = om.getResultType(om.createResultType(tester, "resultType", 2));
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, 
				                                   "measurement", 
				                                   workList, 
				                                   instrument, 
				                                   resultType));
		
		om.setAuditService(mockAuditService);
		
		try{
			om.delete(tester, measurement);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		measurement = om.getMeasurement(measurement.getId());
				
		assertFalse("The edit should not have been saved", measurement.isDeleted());
	}
	
	/**
	 * Tests the transactions that should take place when adding a <code>Result</code>
	 */
	@Test
	public void testAddResult() {
		  
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument   = om.getInstrument(om.createInstrument(tester, "instrument"));
		ResultType resultType   = om.getResultType(om.createResultType(tester, "resultType", 2));
		Measurement measurement = om.getMeasurement(om.createMeasurement(tester, 
				                                   "measurement", 
				                                   workList, 
				                                   instrument, 
				                                   resultType));
		
		double[] result = {0.22, 3.34};
		
		om.setAuditService(mockAuditService);
		
		try{
			om.addResult(tester, measurement, result);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		measurement = om.getMeasurement(measurement.getId());
				
		assertTrue("The edit should not have been saved", measurement.getResults().size() == 0);
	}
	
/*
 * ORIGINMANAGERS METHODS
 */
	/**
	 * Tests the transactions that should take place when creating a <code>DrugOrigin</code>
	 * @throws IOException 
	 */
	@Test
	public void testNewDrugOrigin() throws IOException {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		
		int countBefore = om.getAllDrugOrigins().size();
		
		om.setAuditService(mockAuditService);
		
		try{
			om.createDrugOrigin(tester, "drugOrigin", null, 23.0, compounds);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = om.getAllDrugOrigins().size();
				
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>CellOrigin</code>
	 */
	@Test
	public void testNewCellOrigin() {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		
		int countBefore = om.getAllCellOrigins().size();
		
		om.setAuditService(mockAuditService);
		
		try{
			om.createCellOrigin(tester, "cellOrigin", cellTypes);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = om.getAllCellOrigins().size();
				
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when editing a <code>DrugOrigin</code>
	 * @throws IOException 
	 */
	@Test
	public void testEditDrugOrigin() throws IOException {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		DrugOrigin drugOrigin = om.getDrugOrigin(om.createDrugOrigin(tester, "drugOrigin1", null, 23.0, compounds));

		om.setAuditService(mockAuditService);
		
		try{
			drugOrigin.setName("edited");
			om.edit(tester, drugOrigin);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}

		drugOrigin = om.getDrugOrigin(drugOrigin.getId());
		assertFalse("The new entry should not have been added", drugOrigin.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when editing a <code>CellOrigin</code>
	 */
	@Test
	public void testEditCellOrigin() {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		CellOrigin cellOrigin = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin", cellTypes));
		
		om.setAuditService(mockAuditService);
		
		try{
			cellOrigin.setName("edited");
			om.edit(tester, cellOrigin);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		cellOrigin = om.getCellOrigin(cellOrigin.getId());
				
		assertFalse("The new entry should not have been added", cellOrigin.getName().equals("edited"));
	}
	
	/**
	 * Tests the transactions that should take place when delting a <code>DrugOrigin</code>
	 * @throws IOException 
	 */
	@Test
	public void testDeleteDrugOrigin() throws IOException {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		DrugOrigin drugOrigin = om.getDrugOrigin(om.createDrugOrigin(tester, "drugOrigin1", null, 23.0, compounds));
		
		om.setAuditService(mockAuditService);
		
		try{
			om.delete(tester, drugOrigin);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}

		drugOrigin = om.getDrugOrigin(drugOrigin.getId());
		assertFalse("The new entry should not have been added", drugOrigin.isDeleted());
	}
	
	/**
	 * Tests the transactions that should take place when deleting a <code>CellOrigin</code>
	 */
	@Test
	public void testDeleteCellOrigin() {
		  
		IOriginManager om = (IOriginManager)context.getBean("originManager");
		CellOrigin cellOrigin = om.getCellOrigin(om.createCellOrigin(tester, "cellOrigin", cellTypes));
		
		om.setAuditService(mockAuditService);
		
		try{
			om.delete(tester, cellOrigin);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
				
		assertFalse("The new entry should not have been added", cellOrigin.getName().equals("edited"));
	}
/*
 * PLATEMANAGERS METHODS
 */
	
	/**
	 * Tests the transactions that should take place when creating a <code>Plate</code>
	 */
	@Test
	public void testCreatePlate() {
		
//		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");   
//		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
//		
//		User creator            = new User("tester");
//		userDAO.save(creator);
//		
//		PlateType plateType     = new PlateType(creator, 9, 6, "test");
//		PlateLayout plateLayout = new PlateLayout(creator, "test", plateType);
//		MasterPlate masterPlate = pm.getMasterPlate(pm.createMasterPlate(creator, "test", plateLayout, masterPlates)); 
//		
		int countBefore = pm.getAllPlates().size();
		
		pm.setAuditService(mockAuditService);
		
		try{
			pm.createPlate(tester, "Testplate", "|34|) |>14T3", new Folder(), masterPlate, cellOrigin, null);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = pm.getAllPlates().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>MasterPlate</code>
	 */
	@Test
	public void testCreateMasterPlate() {
		
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");   
		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
		
		User creator            = new User("tester");
		userDAO.save(creator);
		
		PlateType plateType     = new PlateType(creator, 9, 6, "test");
		PlateLayout plateLayout = new PlateLayout(creator, "test", plateType); 
		
		int countBefore = pm.getAllMasterPlates().size();
		
		pm.setAuditService(mockAuditService);
		
		try{
			pm.createMasterPlate(tester, "masterPlate", plateLayout, masterPlates, 1);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = pm.getAllMasterPlates().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>WellFunction</code>
	 */
	@Test
	public void testCreateWellFunctionPlateManager() {
		
		Well well = (Well)masterPlate.getWells().toArray()[0];
		
		int countBefore = well.getWellFunctions().size();
		
		pm.setAuditService(mockAuditService);
		
		try{
			pm.createWellFunction(tester, "test", well, "");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		well = (Well)pm.getMasterPlate(masterPlate.getId()).getWells().toArray()[0];
		
		int countAfter = well.getWellFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>PlateFunction</code> 
	 * with good between values
	 */
	@Test
	public void testPlatemanagersCreatePlateFunctionWith() {
		
		int countBefore = masterPlate.getPlateFunctions().size();
		
		pm.setAuditService(mockAuditService);
		
		try{
			pm.createPlateFunction(tester, "plateFunction", masterPlate, "", 0, 12);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		masterPlate = pm.getMasterPlate(masterPlate.getId());
		
		int countAfter = masterPlate.getPlateFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>PlateFunction</code> 
	 * without good between values
	 */
	@Test
	public void testPlatemanagersCreatePlateFunctionWithout() {
		
		int countBefore = masterPlate.getPlateFunctions().size();
		
		pm.setAuditService(mockAuditService);
		
		try{
			pm.createPlateFunction(tester, "plateFunction", masterPlate, "");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		masterPlate = pm.getMasterPlate(masterPlate.getId());
		
		int countAfter = masterPlate.getPlateFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
/*
 * PLATELAYOUTMANAGERS METHODS
 */
	/**
	 * Tests the transactions that should take place when creating a <code>PlateType</code>
	 */
	@Test
	public void testCreatePlateType() {
		
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		
		User creator = new User("tester");
		userDAO.save(creator);
		
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		
		int countBefore = plm.getAllPlateTypes().size();
		
		plm.setAuditService(mockAuditService);
		
		try{
			plm.createPlateType(tester, 3, 3, "plateType", plateTypes);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = plm.getAllPlateTypes().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}

	/**
	 * Tests the transactions that should take place when creating a <code>PlateLayout</code>
	 */
	@Test
	public void testCreatePlateLayout() {
		
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		
		int countBefore = plm.getAllPlateLayouts().size();
		
		PlateType plateType = new PlateType(tester, 8, 12, "96 wells");
		
		plm.setAuditService(mockAuditService);
		
		try{
			plm.createPlateLayout(User.createUser("creator"),"my plate layout", plateType, plateLayouts);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		int countAfter = plm.getAllPlateLayouts().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transaction that should take place when creating a <code>WellFunction</code>
	 */
	@Test
	public void testCreateWellFunctionPlateLayoutManager() {
		
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");   
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		
		User creator            = new User("tester");
		userDAO.save(creator);
		
		PlateType plateType     = plm.getPlateType(plm.createPlateType(tester, 3, 4, "plateType", plateTypes));
		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "test", plateType, plateLayouts)); 
		
		LayoutWell well = (LayoutWell)plateLayout.getLayoutWells().toArray()[0];
		
		int countBefore = well.getWellFunctions().size();
		
		plm.setAuditService(mockAuditService);
		
		try{
			plm.createWellFunction(tester, "test", well, "");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		well = (LayoutWell)plm.getPlateLayout(plateLayout.getId()).getLayoutWells().toArray()[0];
		
		int countAfter = well.getWellFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>PlateFunction</code>
	 * with good between values
	 */
	@Test
	public void testCreatePlateLayoutManagersPlateFunctionWith() {
		
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		
		User creator = new User("tester");
		userDAO.save(creator);
		
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
		
		PlateType plateType = plm.getPlateType(plm.createPlateType(tester, 3, 3, "", plateTypes));
		PlateLayout plateLayout =plm.getPlateLayout( plm.createPlateLayout(tester, "plateLAyout", plateType, plateLayouts)); 
		
		int countBefore = plateLayout.getPlateFunctions().size();
		
		plm.setAuditService(mockAuditService);
		
		try{
			plm.createPlateFunction(tester, "plateFunction", plateLayout, "", 0, 34);
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		plateLayout = plm.getPlateLayout(plateLayout.getId());
		
		int countAfter = plateLayout.getPlateFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
	/**
	 * Tests the transactions that should take place when creating a <code>PlateFunction</code>
	 * without good between values
	 */
	@Test
	public void testCreatePlateLayoutManagersPlateFunctionWithout() {
		
		IUserDAO userDAO = (IUserDAO)context.getBean("userDAO");
		
		User creator = new User("tester");
		userDAO.save(creator);
		
		IPlateLayoutManager plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
	
		PlateType plateType = plm.getPlateType(plm.createPlateType(tester, 3, 4, "", plateTypes));
		PlateLayout plateLayout = plm.getPlateLayout(plm.createPlateLayout(tester, "plateLAyout", plateType, plateLayouts)); 
		
		int countBefore = plateLayout.getPlateFunctions().size();

		plm.setAuditService(mockAuditService);
		
		try{
			plm.createPlateFunction(tester, "plateFunction", plateLayout, "");
			fail("should have thrown RuntimeException");
		}
		catch (RuntimeException expected) {
		}
		
		plateLayout = plm.getPlateLayout(plateLayout.getId());
		
		int countAfter = plateLayout.getPlateFunctions().size();
		
		assertEquals("The new entry should not have been added", countBefore, countAfter);
	}
	
/*
 * SAMPLEMANAGERS METHODS
 */
//	/**
//	 * Tests the transactions that should take place when adding a new <code>CellSample</code> 
//	 * to a <code>SampleContainer</code>
//	 */
//	public void testAddNewCellSampleToContainer() {
//
//		ISampleManager sm = (ISampleManager)context.getBean("sampleManager");
//		IPlateLayoutManager  plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
//		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
//		
//		MasterPlate masterPlate = 
//			pm.getMasterPlate(pm.createMasterPlate(tester, 
//					             "masterPlate", 
//					             plm.getPlateLayout(plm.createPlateLayout(tester, 
//					            		               "", 
//					            		               plm.getPlateType(plm.createPlateType( tester, 
//					            		            		                                 4, 
//					            		            		                                 4, 
//					            		            		                                 "",
//					            		            		                                 plateTypes )
//					            		            		            
//					            		            		            ), 
//					            		            	plateLayouts)
//					            		            ),
//					           masterPlates)); 
//		SampleContainer sampleContainer = 
//			((Well)masterPlate.getWells().toArray()[0]).getSampleContainer();
//		
//		sm.setAuditService(new MockAuditService());
//		
//		int countBefore = sampleContainer.getSamples().size();
//		
//		try{
//			sm.addNewCellSampleToContainer(tester, "", new CellOrigin(), new Timestamp(234), sampleContainer);
//			fail("should have thrown RuntimeException");
//		}
//		catch (RuntimeException expected) {
//		}
//		
//		sampleContainer = sm.getSampleContainer(sampleContainer.getId());
//		
//		int countAfter = sampleContainer.getSamples().size();
//		
//		assertEquals("The new entry should not have been added", countBefore, countAfter);
//	}
	/**
	 * Tests the transactions that should take place when adding a new <code>CellSample</code> 
	 * to a <code>SampleContainer</code>
	 */
//	public void testAddNewDrugSampleToContainer() {
//
//		ISampleManager sm = (ISampleManager)context.getBean("sampleManager");
//		IPlateLayoutManager  plm = (IPlateLayoutManager)context.getBean("plateLayoutManager");
//		IPlateManager pm = (IPlateManager)context.getBean("plateManager");
//		
//		MasterPlate masterPlate = 
//			pm.getMasterPlate(pm.createMasterPlate(tester, 
//					             "masterPlate", 
//					             plm.getPlateLayout(plm.createPlateLayout(tester, 
//					            		               "", 
//					            		               plm.getPlateType(plm.createPlateType( tester, 
//					            		            		                                 4, 
//					            		            		                                 4, 
//					            		            		                                 "",
//					            		            		                                 plateTypes )
//					            		            		            
//					            		            		            ), 
//					            		            	plateLayouts)
//					            		            ),
//					           masterPlates));  
//		SampleContainer sampleContainer = 
//			((Well)masterPlate.getWells().toArray()[0]).getSampleContainer();
//		
//		sm.setAuditService(new MockAuditService());
//		
//		int countBefore = sampleContainer.getSamples().size();
//		
//		try{
//			sm.addNewDrugSampleToContainer(tester, "", new DrugOrigin(), 23.0, sampleContainer, null);
//			fail("should have thrown RuntimeException");
//		}
//		catch (RuntimeException expected) {
//		}
//		
//		sampleContainer = sm.getSampleContainer(sampleContainer.getId());
//		
//		int countAfter = sampleContainer.getSamples().size();
//		
//		assertEquals("The new entry should not have been added", countBefore, countAfter);
//	}
	/**
	 * A mockimplementation of the AuditService which throws a <code>RuntimeException</code> 
	 * which should make the transaction rollback.
	 * 
	 * @author jonathan
	 *
	 */
	class MockAuditService implements IAuditService{

		public void audit(User user, AuditType auditType, AbstractAuditableObject auditedObject) {
			throw new RuntimeException("peculiar error");
        }
	}
}
