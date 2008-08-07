package net.bioclipse.brunn.tests.business;

import static org.junit.Assert.assertNotNull;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.tests.BaseTest;

import org.junit.Test;

public class LazyLoadingSessionHolderTest extends BaseTest {

	@Test
	public void testGetInstance() {
		assertNotNull(LazyLoadingSessionHolder.getInstance());
	}

}
