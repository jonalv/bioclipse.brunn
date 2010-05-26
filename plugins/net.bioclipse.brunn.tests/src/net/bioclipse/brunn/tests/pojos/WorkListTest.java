package net.bioclipse.brunn.tests.pojos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import net.bioclipse.brunn.pojos.AbstractOperation;
import net.bioclipse.brunn.pojos.WorkList;
import net.bioclipse.brunn.tests.BaseTest;

public class WorkListTest extends BaseTest {

	public WorkListTest() {
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
	public void testDeepDelete(){
		
		assertFalse(workList.isDeleted());
		for (AbstractOperation operation : workList.getAbstractOperations()) {
	        assertFalse(operation.isDeleted());
        }
		assertFalse(workList.getSampleContainer().isDeleted());
		
		workList.delete();
		
		assertTrue(workList.isDeleted());
		for (AbstractOperation operation : workList.getAbstractOperations()) {
	        assertTrue(operation.isDeleted());
        }
		assertFalse(workList.getSampleContainer().isDeleted());
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		assertTrue(sampleContainer.getWorkList().getSampleContainer() == sampleContainer);
		
		assertTrue(measurement.getWorkList() == workList);
		assertTrue(sampleContainer.getWorkList().getAbstractOperations().contains(measurement));
		
	}
	
	@Test
	public void testDeepCopy(){
		
		WorkList copy = workList.deepCopy();
		
		assertEquals(copy, workList);
		assertNotSame(copy, workList);
	}
}
