package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertNotNull;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;

import org.junit.Test;

public class LazyLoadingSessionHolderTest {

	@Test
	public void testGetInstance() {
		assertNotNull(LazyLoadingSessionHolder.getInstance());
	}

}
