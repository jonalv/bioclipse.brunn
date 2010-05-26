package net.bioclipse.brunn.tests.pojos;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import net.bioclipse.brunn.pojos.AbstractPlate;
import net.bioclipse.brunn.pojos.LayoutMarker;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.SampleMarker;
import net.bioclipse.brunn.pojos.Well;
import net.bioclipse.brunn.tests.BaseTest;

public class AbstractPlateTest extends BaseTest {

	public AbstractPlateTest() {
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
	public void testCreatePlate() {

		Plate plate = AbstractPlate.createPlate(tester, "plate", masterPlate,"df", folder);
				
		Set<String> maserPlateMarkerNames = new HashSet<String>();
		for(Well well : masterPlate.getWells()) {
			for(SampleMarker sm : well.getSampleMarkers()) {
				maserPlateMarkerNames.add(sm.getName());
			}
		}
		
		Set<String> plateMarkerNames = new HashSet<String>();
		for(Well well : plate.getWells()) {
			for(SampleMarker sm : well.getSampleMarkers()) {
				plateMarkerNames.add(sm.getName());
			}
		}
		assertEquals( masterPlate.getPlateFunctions().size(), plate.getPlateFunctions().size() );
		
		assertEquals(plateMarkerNames, maserPlateMarkerNames);
	}

	@Test
	public void testCreateMasterPlate() {

		MasterPlate masterPlate = AbstractPlate.createMasterPlate(tester, "masterPlate", plateLayout, folder, 1);
		
		Set<String> layoutMarkernames = new HashSet<String>(); 
		for(LayoutWell lw : plateLayout.getLayoutWells() ) {
			for(LayoutMarker lm : lw.getLayoutMarkers()) {
				layoutMarkernames.add(lm.getName());
			}
		}
		
		Set<String> sampleMarkerNames = new HashSet<String>();
		for(Well well : masterPlate.getWells()) {
			for(SampleMarker sm : well.getSampleMarkers()) {
				sampleMarkerNames.add(sm.getName());
			}
		}
		
		assertEquals( plateLayout.getPlateFunctions().size(), masterPlate.getPlateFunctions().size() );
		
		assertEquals(layoutMarkernames, sampleMarkerNames);
	}
}
