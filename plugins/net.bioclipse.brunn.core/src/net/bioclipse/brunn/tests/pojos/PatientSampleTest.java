package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.pojos.PatientSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

public class PatientSampleTest {
	
	/**
	 *  Tests that the double references are double.
	 * @throws IOException 
	 */
	@Test
	public void testDoubleReferences() {

		User tester = new User("tester");
		PatientOrigin patientOrigin = new PatientOrigin();
		PatientSample patientSample = new PatientSample( tester, 
				                                          "pateientSample", 
				                                          new SampleContainer(), 
				                                          patientOrigin, new Timestamp(0) );

		assertTrue(patientOrigin.getPatientSamples().contains(patientSample));
		assertTrue(patientSample.getPatientOrigin() == patientOrigin);
	}

	@Test
	public void testDeepCopy() {

		User tester = new User("tester");
		PatientOrigin patientOrigin = new PatientOrigin();
		PatientSample patientSample = new PatientSample( tester, 
				                                          "pateientSample", 
				                                          new SampleContainer(), 
				                                          patientOrigin, 
				                                          new Timestamp(0) );

		PatientSample copy = patientSample.deepCopy();

		assertEquals(copy, patientSample);
		assertNotSame(copy, patientSample);

		assertSame(copy.getPatientOrigin(), patientSample.getPatientOrigin());
		assertTrue(patientOrigin.getPatientSamples().contains(copy));
	}
}
