package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;

import net.bioclipse.brunn.pojos.LayoutMarker;
import net.bioclipse.brunn.pojos.LayoutWell;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.WellFunction;
import net.bioclipse.brunn.tests.Tools;
import junit.framework.TestCase;
import static org.junit.Assert.*;


public class LayOutWellTest {

	@Test
	public void testDeepCopy() {
		
		User tester = new User("tester");
		PlateType plateType = new PlateType(tester, 4, 4, "plateType");
		PlateLayout plateLayout = new PlateLayout(tester, "lpateLayout", plateType);
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 4, 'a', plateLayout);
		
		LayoutWell layoutWell2 = layoutWell.deepCopy();
		
		assertEquals(layoutWell, layoutWell2);
		assertNotSame(layoutWell, layoutWell2);
		assertEquals(layoutWell.getLayoutMarkers(), layoutWell2.getLayoutMarkers());
		assertFalse( Tools.inludesSame(layoutWell.getLayoutMarkers(), layoutWell2.getLayoutMarkers()) );
	}
	
	@Test
	public void testDeepDelete(){
		
		User tester = new User("tester");
		PlateType plateType = new PlateType(tester, 4, 4, "plateType");
		PlateLayout plateLayout = new PlateLayout(tester, "lpateLayout", plateType);
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 4, 'a', plateLayout);
		layoutWell.getLayoutMarkers().add(new LayoutMarker(tester, "layuotMarker", layoutWell));
		
		assertFalse(layoutWell.isDeleted());
		for (LayoutMarker marker : layoutWell.getLayoutMarkers()) {
	        assertFalse(marker.isDeleted());
        }
		
		layoutWell.delete();
		
		assertTrue(layoutWell.isDeleted());
		for (LayoutMarker marker : layoutWell.getLayoutMarkers()) {
	        assertTrue(marker.isDeleted());
        }
	}
	
	/**
	 *  Tests that the double references are double.
	 */
	@Test
	public void testDoubleReferences() {
		
		User tester = new User("tester");
		
		//platelayout
		PlateLayout plateLayout = new PlateLayout(tester, "plateLayout", new PlateType(tester, 6, 6, "plateType"));
		LayoutWell layoutWell = new LayoutWell(tester, "layoutWell", 4, 'a', plateLayout);
		
		assertTrue(layoutWell.getPlateLayout() == plateLayout);
		assertTrue(plateLayout.getLayoutWells().contains(layoutWell));
		
		//layoutMarker
		LayoutMarker layoutMarker = new LayoutMarker(tester, "layuotMarker", layoutWell);
		
		assertTrue(layoutWell.getLayoutMarkers().contains(layoutMarker));
		assertTrue(layoutMarker.getLayoutWell() == layoutWell);
		
		//wellfunction
		WellFunction wellFunction = new WellFunction(tester, "wellFunction", "", layoutWell);
		
		assertTrue(layoutWell.getWellFunctions().contains(wellFunction));
		assertTrue(wellFunction.getWell() == layoutWell);
	}
}
