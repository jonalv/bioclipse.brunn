package net.bioclipse.brunn.tests.pojos;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.bioclipse.brunn.pojos.AbstractSample;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;
import net.bioclipse.brunn.tests.Tools;
import static org.junit.Assert.*;

public class SampleContainerTest extends BaseTest {

	public SampleContainerTest() {
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
	public void testDeepCopy() {
		
		SampleContainer sampleContainer2 = sampleContainer.deepCopy();
		
		assertEquals(  sampleContainer, sampleContainer2 );
		assertNotSame( sampleContainer, sampleContainer2 );
		
		assertEquals(  sampleContainer.getWorkList(), sampleContainer2.getWorkList() );
		assertNotSame( sampleContainer.getWorkList(), sampleContainer2.getWorkList() );
		
		assertEquals(  sampleContainer.getSamples(), sampleContainer2.getSamples() );
		assertFalse( Tools.inludesSame(sampleContainer.getSamples(), sampleContainer2.getSamples()) );
	}
	
	@Test
	public void testDeepDelete(){
		
		assertFalse(sampleContainer.isDeleted());
		assertFalse(sampleContainer.getWorkList().isDeleted());
		for (AbstractSample sample : sampleContainer.getSamples()) {
	        assertFalse(sample.isDeleted());
        }
		assertFalse(sampleContainer.getWell().isDeleted());
		
		sampleContainer.delete();
		
		assertTrue(sampleContainer.isDeleted());
		assertTrue(sampleContainer.getWorkList().isDeleted());
		for (AbstractSample sample : sampleContainer.getSamples()) {
	        assertTrue(sample.isDeleted());
        }
		assertFalse(sampleContainer.getWell().isDeleted());
	}
	
//	/**
//	 *  Tests that the double references are double.
//	 */
//	public void testDoubleReferences() {
//		
//		sm.addNewDrugSampleToContainer(tester, "sample", drugOrigin, 34.0, sampleContainer, null);
//		
//		assertTrue(sampleContainer.getSamples().size() != 0);
//		for (AbstractSample sample : sampleContainer.getSamples()) {
//			assertTrue(sample.getSampleContainer().equals(sampleContainer));
//        }
//		
//		assertNotNull(sampleContainer.getWorkList());
//		assertTrue(sampleContainer.getWorkList().getSampleContainer().equals(sampleContainer));
//		
//		assertNotNull(sampleContainer.getWell());
//		assertTrue(sampleContainer.getWell().getSampleContainer().equals(sampleContainer));
//	}
	
	@Test
	public void testEqualsAndHashCode() {
		
		SampleContainer sampleContainer1 = new SampleContainer(tester, "sampleContainer", new Well());
		
		SampleContainer sampleContainer3 = new SampleContainer(tester, "sampleContainer3", new Well());
		
		sampleContainer1.getSamples().add(new CellSample( tester, 
				                                          "name", 
				                                          new CellOrigin(),
				                                          new Timestamp(0), 
				                                          sampleContainer1) );
		sampleContainer3.getSamples().add(new DrugSample( tester, 
				                                          "name",
				                                          23.0, 
				                                          new DrugOrigin(), 
				                                          sampleContainer3, 
				                                          ConcUnit.UNIT) );
		
		SampleContainer sampleContainer2 = sampleContainer1.deepCopy();
		
		assertTrue(sampleContainer1.equals(sampleContainer2));
		assertTrue(sampleContainer2.equals(sampleContainer1));
		
		assertTrue(!sampleContainer1.equals(sampleContainer3));
		assertTrue(!sampleContainer3.equals(sampleContainer1));
		
		assertTrue(sampleContainer1.hashCode() == sampleContainer2.hashCode());
		assertTrue(sampleContainer1.hashCode() != sampleContainer3.hashCode());
	}
}
