package net.bioclipse.brunn.tests.pojos;

import org.junit.Test;

import junit.framework.TestCase;
import net.bioclipse.brunn.pojos.PickList;
import net.bioclipse.brunn.pojos.Picking;
import net.bioclipse.brunn.pojos.User;
import static org.junit.Assert.*;

public class PickListTest {

	public PickListTest() {
		super();
	}

	@Test
	public void testDeepDelete() {
		
		User tester = new User("tester");
		PickList pickList1 = new PickList( tester, 
				                           "pickList1",
				                           null );
		
		assertFalse( pickList1.isDeleted() );
		
		pickList1.delete();
		
		assertTrue( pickList1.isDeleted() );
		
		pickList1.unDelete();
		
		assertFalse( pickList1.isDeleted() );
		
		pickList1.getPickings().add(new Picking());
		
		pickList1.delete();
		
		for( Picking p : pickList1.getPickings()) {
			assertTrue(p.isDeleted());
		}
	}
	
	@Test
	public void testEqualsAndHashCode() {
		
		User tester = new User("tester");
		
		PickList pickList1 = new PickList( tester,
										   "pickList",
										   null );
		PickList pickList2 = pickList1.deepCopy();
		PickList pickList3 = new PickList( tester,
				                           "pickList3",
				                           null );
		
		assertTrue( pickList1.equals(pickList2) );
		assertTrue( pickList2.equals(pickList1) );
		
		assertFalse( pickList1.equals(pickList3) );
		assertFalse( pickList3.equals(pickList1) );
		
		assertTrue(  pickList1.hashCode() == pickList2.hashCode() );
	}
}
