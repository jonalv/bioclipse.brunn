package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.DrugSample;
import net.bioclipse.brunn.pojos.SampleContainer;
import net.bioclipse.brunn.pojos.User;

import org.junit.Test;

public class DrugSampleTest {

	/**
	 *  Tests that the double references are double.
	 * @throws IOException 
	 */
	@Test
	public void testDoubleReferences() throws IOException {
		
		User tester = new User("tester");
		DrugOrigin drugOrigin = new DrugOrigin(tester, "", null, 23);
		DrugSample drugSample = new DrugSample(tester, "", 23.5, drugOrigin, new SampleContainer(), ConcUnit.UNIT);
		
		assertTrue(drugOrigin.getDrugSamples().contains(drugSample));
		assertTrue(drugSample.getDrugOrigin() == drugOrigin);
	}
	
	@Test
	public void testDeepCopy() throws IOException {
		
		User tester = new User("tester");
		DrugOrigin drugOrigin = new DrugOrigin(tester, "", null, 23);
		DrugSample drugSample = new DrugSample(tester, "", 23.5, drugOrigin, new SampleContainer(), ConcUnit.UNIT);
		
		DrugSample copy = drugSample.deepCopy();
		
		assertEquals(copy, drugSample);
		assertNotSame(copy, drugSample);
		
		assertSame(copy.getDrugOrigin(), drugSample.getDrugOrigin());
		assertTrue(drugOrigin.getDrugSamples().contains(copy));
	}
}
