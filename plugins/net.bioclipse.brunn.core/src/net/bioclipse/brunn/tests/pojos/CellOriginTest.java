package net.bioclipse.brunn.tests.pojos;

import java.sql.Timestamp;

import org.junit.Test;

import static org.junit.Assert.*;

import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.CellSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;
import junit.framework.TestCase;

public class CellOriginTest {

	@Test
	public void testDeepDelete() {
		
		User tester = new User("tester");
		CellOrigin cellOrigin1 = new CellOrigin(tester, "cellOrigin1");
		
		assertFalse( cellOrigin1.isDeleted() );
		
		cellOrigin1.delete();
		
		assertTrue( cellOrigin1.isDeleted() );
		
		CellOrigin cellOrigin2 = new CellOrigin(tester, "cellOrigin2");
		new CellSample(tester, "cellSample", cellOrigin2, new Timestamp(0), new SampleContainer());
		
		try{
			cellOrigin2.delete();
			fail("should have thrown IllegalStateException");
		}
		catch(IllegalStateException e){
			
		}
	}
	
	@Test
	public void testEqualsAndHashCode() {
		
		User tester = new User("tester");
		
		CellOrigin cellOrigin1 = new CellOrigin( tester, 
												 "cellorigin" );
		CellOrigin cellOrigin2 = cellOrigin1.deepCopy();
		CellOrigin cellOrigin3 = new CellOrigin( tester, 
				                                 "cellorigin3" );
		
		assertTrue( cellOrigin1.equals(cellOrigin2) );
		assertTrue( cellOrigin2.equals(cellOrigin1) );
		
		assertFalse( cellOrigin1.equals(cellOrigin3) );
		assertFalse( cellOrigin3.equals(cellOrigin1) );
		
		assertTrue(  cellOrigin1.hashCode() == cellOrigin2.hashCode() );
		assertFalse( cellOrigin1.hashCode() == cellOrigin3.hashCode() );
	}
}
