package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import net.bioclipse.brunn.pojos.Diagnosis;
import net.bioclipse.brunn.pojos.User;

import org.junit.*;

public class DiagnosisTest {

	@Test
	public void testEqualsAndHashCode()  {
		
		User tester = new User("tester");
		Diagnosis diagnosis1 = new Diagnosis( tester,
		                                      "diagnosis1" );
		Diagnosis diagnosis2 = diagnosis1.deepCopy();
		Diagnosis diagnosis3 = new Diagnosis( tester,
				                              "diagnosis3" );
		
		assertTrue( diagnosis1.equals(diagnosis2) );
		assertTrue( diagnosis2.equals(diagnosis1) );
		
		assertFalse( diagnosis1.equals(diagnosis3) );
		assertFalse( diagnosis3.equals(diagnosis1) );
		
		assertTrue(  diagnosis1.hashCode() == diagnosis2.hashCode() );
		assertFalse( diagnosis1.hashCode() == diagnosis3.hashCode() );
	}
}
