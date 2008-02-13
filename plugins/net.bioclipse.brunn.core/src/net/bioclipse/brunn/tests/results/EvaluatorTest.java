package net.bioclipse.brunn.tests.results;

import static org.junit.Assert.assertEquals;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.results.PlateResults;
import net.bioclipse.brunn.tests.BaseTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EvaluatorTest extends BaseTest {

	public EvaluatorTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		PlateLayout plateLayout = plm.getPlateLayout( 
			plm.createPlateLayout( tester,
                                   "name",
                                   plm.getPlateType(
                                		  plm.createPlateType( 
                                				  tester, 
                                				  2, 
                                				  2, 
                                				  "name", 
                                				  plateTypes) ),
                                   plateLayouts)); 
		
		MasterPlate masterPlate = pm.getMasterPlate(
				pm.createMasterPlate(tester, "name", plateLayout, masterPlates, 1) );
		
		plate = pm.getPlate( pm.createPlate(tester, "name", "", folder, masterPlate, cellOrigin, null) );
		
		IOperationManager om = (IOperationManager)context.getBean("operationManager");
		
		Instrument instrument = om.getInstrument(om.createInstrument(tester, "instrument"));
		ResultType resultType = om.getResultType(om.createResultType(tester, "resultType", 1));
		
		for(Well well : plate.getWells()) {
			
			Measurement measurement = om.getMeasurement( om.createMeasurement( tester, 
					                                                            "name", 
					                                                            well
					                                                            .getSampleContainer()
					                                                            .getWorkList(), 
					                                                            instrument, 
					                                                            resultType ) );
			double[] data = {1.2};
			om.addResult(tester, measurement, data);
		}
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testRawData() {
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		
		assertEquals( 1.2, plateResults.getRawValue(1, 'a') );
		assertEquals( 1.2, plateResults.getRawValue(1, 'b') );
		assertEquals( 1.2, plateResults.getRawValue(2, 'a') );
		assertEquals( 1.2, plateResults.getRawValue(2, 'b') );
	}
	
	@Test
	public void testMultiplyByTwoInWellFunction() {
		
		for(Well well : plate.getWells()){
		
			pm.createWellFunction( tester, "x2", well, ""+well.getRow() + well.getCol() + "*2" );
		}
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		assertEquals( 2.4, plateResults.getValue(1, 'a', "x2") );
		assertEquals( 2.4, plateResults.getValue(1, 'b', "x2") );
		assertEquals( 2.4, plateResults.getValue(2, 'a', "x2") );
		assertEquals( 2.4, plateResults.getValue(2, 'b', "x2") );
	}
	
	@Test
	public void testSumFourWellsInPlateFunction() {
		
		pm.createPlateFunction(tester, "addition", plate, "a1+a2+b1+b2");
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		assertEquals( 4.8, plateResults.getValue("addition"));
	}
	
	@Test
	public void testSumFunction() {
		pm.createPlateFunction(tester, "sumTest", plate, "sum(a1:b2)");
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		assertEquals( 4.8, plateResults.getValue("sumTest") );
	}
	
	@Test
	public void testAvgFunction() {
		pm.createPlateFunction(tester, "avgTest", plate, "avg(a1:b2)");
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		assertEquals( 1.2, plateResults.getValue("avgTest") );
	}
	
	@Test
	public void testStddevFunction() {
		pm.createPlateFunction(tester, "stddevTest", plate, "stddev(a1:b2)");
		
		PlateResults plateResults = pm.getPlateResults(plate, null);
		assertEquals( 0.0, plateResults.getValue("stddevTest") );
	}
}
