package net.bioclipse.brunn.tests.pojos;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;

import junit.framework.TestCase;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;

public class CellSampleTest {

	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		SampleContainer sampleContainer = new SampleContainer(tester, "sampleContainer", new Well());
		CellSample cellSample = new CellSample(tester, "cellSample", cellOrigin, new Timestamp(1), sampleContainer);
		
		assertTrue(cellSample.getSampleContainer() == sampleContainer);
		assertTrue(sampleContainer.getSamples().contains(cellSample));
		
		assertTrue(cellSample.getCellOrigin() == cellOrigin);
		assertTrue(cellOrigin.getCellSamples().contains(cellSample));
		
	}

	@Test
	public void testDeepCopy() {
		
		User tester = new User("tester");
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		CellSample cellSample = new CellSample(tester, "cellsample", cellOrigin, new Timestamp(2), new SampleContainer());
		
		CellSample copy = cellSample.deepCopy();
		
		assertEquals(cellSample, copy);
		assertNotSame(cellSample, copy);
		
		assertSame(cellSample.getCellOrigin(), copy.getCellOrigin());
		assertTrue(cellOrigin.getCellSamples().contains(cellSample));
	}
	
	@Test
	public void testEqualsAndHashCode() {
		
		User tester = new User("tester");
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		
		CellSample cellSample1 = new CellSample( tester, 
				                                 "cellsample", 
				                                 cellOrigin, 
				                                 new Timestamp(2), 
				                                 new SampleContainer() );
		CellSample cellSample2 = cellSample1.deepCopy();
		CellSample cellSample3 = new CellSample( tester, 
                                                 "cellsample3", 
                                                 cellOrigin, 
                                                 new Timestamp(20), 
                                                 new SampleContainer() );
		
		assertTrue( cellSample1.equals(cellSample2) );
		assertTrue( cellSample2.equals(cellSample1) );
		
		assertFalse( cellSample1.equals(cellSample3) );
		assertFalse( cellSample3.equals(cellSample1) );
		
		assertTrue(  cellSample1.hashCode() == cellSample2.hashCode() );
		assertFalse( cellSample1.hashCode() == cellSample3.hashCode() );
	}
	
	@Test
	public void testInHashSet() {
		
		User tester = new User("tester");
		CellOrigin cellOrigin = new CellOrigin(tester, "cellOrigin");
		
		CellSample cellSample1 = new CellSample( tester, 
				                                 "cellsample", 
				                                 cellOrigin, 
				                                 new Timestamp(2), 
				                                 new SampleContainer() );
		CellSample cellSample2 = cellSample1.deepCopy();
		
		Set<CellSample> cellSamples = new HashSet<CellSample>();
		cellSamples.add(cellSample1);
		
		assertTrue(cellSample2.hashCode() == cellSample1.hashCode());
		assertTrue(cellSample1.equals(cellSample2));
		assertTrue(cellSample2.equals(cellSample1));
		
		assertTrue(cellSamples.contains(cellSample1));
		assertTrue(cellSamples.contains(cellSample2));
	}
}
