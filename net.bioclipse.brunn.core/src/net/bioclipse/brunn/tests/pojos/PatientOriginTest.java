package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;

import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.PatientSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

public class PatientOriginTest {

	@Test
	public void testDeepDelete() throws FileNotFoundException, IOException{
		
		User tester = new User("tester");
		PatientOrigin patientOrigin1 = new PatientOrigin( tester, 
				                                          "patientOrigin", 
				                                          "lidnumber" ); 
		
		assertFalse( patientOrigin1.isDeleted() );
		
		patientOrigin1.delete();
		
		assertTrue( patientOrigin1.isDeleted() );
		
		PatientOrigin patientOrigin2 = new PatientOrigin(tester, "patientOrigin2", "lid");
		new PatientSample(tester, "patientSample", new SampleContainer(), patientOrigin2, new Timestamp(0));
		
		try{
			patientOrigin2.delete();
			fail("should have thrown IllegalStateException");
		}
		catch(IllegalStateException e){
			
		}
	}

	@Test
	public void testEqualsAndHashCode() throws IOException {
		
		User tester = new User("tester");
		
		PatientOrigin patientOrigin1 = new PatientOrigin(tester, "name", "lid");
		PatientOrigin patientOrigin2 = patientOrigin1.deepCopy();
		PatientOrigin patientOrigin3 = new PatientOrigin(tester, "name2", "lid2");
		
		assertTrue( patientOrigin1.equals(patientOrigin2) );
		assertTrue( patientOrigin2.equals(patientOrigin1) );
		
		assertFalse( patientOrigin1.equals(patientOrigin3) );
		assertFalse( patientOrigin3.equals(patientOrigin1) );
		
		assertTrue(  patientOrigin1.hashCode() == patientOrigin2.hashCode() );
		assertFalse( patientOrigin1.hashCode() == patientOrigin3.hashCode() );
	}
}
