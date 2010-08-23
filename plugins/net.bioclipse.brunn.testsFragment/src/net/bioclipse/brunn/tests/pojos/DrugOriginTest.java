package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

public class DrugOriginTest {

	@Test
	public void testDeepDelete() throws FileNotFoundException, IOException{
		
		User tester = new User("tester");
		DrugOrigin drugOrigin1 = new DrugOrigin( tester, 
				                                 "drugOrigin", 
				                                 null, 
				                                 12.01 );
		
		assertFalse( drugOrigin1.isDeleted() );
		
		drugOrigin1.delete();
		
		assertTrue( drugOrigin1.isDeleted() );
		
		DrugOrigin drugOrigin2 = new DrugOrigin(tester, "drugOrigin2", null, 14.0);
		new DrugSample(tester, "cellSample", 12, drugOrigin2, new SampleContainer(), ConcUnit.UNIT);
		
		try{
			drugOrigin2.delete();
			fail("should have thrown IllegalStateException");
		}
		catch(IllegalStateException e){
			
		}
	}

	@Test
	public void testEqualsAndHashCode() throws IOException {
		
		User tester = new User("tester");
		
		DrugOrigin drugOrigin1 = new DrugOrigin( tester,
												 "drugOrigin",
												 null, 
												 23.0 );
		DrugOrigin drugOrigin2 = drugOrigin1.deepCopy();
		DrugOrigin drugOrigin3 = new DrugOrigin( tester,
				                                 "drugOrigin3",
				                                 null, 
				                                 35.0 );
		
		assertTrue( drugOrigin1.equals(drugOrigin2) );
		assertTrue( drugOrigin2.equals(drugOrigin1) );
		
		assertFalse( drugOrigin1.equals(drugOrigin3) );
		assertFalse( drugOrigin3.equals(drugOrigin1) );
		
		assertTrue(  drugOrigin1.hashCode() == drugOrigin2.hashCode() );
		assertFalse( drugOrigin1.hashCode() == drugOrigin3.hashCode() );
	}
}
