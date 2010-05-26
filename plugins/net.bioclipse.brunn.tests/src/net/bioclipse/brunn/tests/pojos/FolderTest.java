package net.bioclipse.brunn.tests.pojos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.User;

import org.junit.Before;
import org.junit.Test;

public class FolderTest {

	private Folder folder;
	private User user;
	
	@Before
	public void setUp() throws Exception{
		
		user = new User("tester");
		HashSet<ILISObject> objects = new HashSet<ILISObject>();
		objects.add( new PlateType( user, 4, 5, "plateType" ) );
		objects.add( new Folder( user, "another folder", new Folder() ) );
		
		folder = new Folder(user, "folder", new Folder());
		folder.setObjects(objects);
	}
	
	@Test
	public void testDeepDelete(){
		
		assertFalse(folder.isDeleted());
		folder.delete();
		assertTrue(folder.isDeleted());
		
		for( ILISObject object : folder.getObjects() ) {
			assertTrue(object.isDeleted());
		}
	}
}
