package net.bioclipse.brunn.tests;

import java.io.File;
import java.net.URL;

public class TestConstants {

	public static File getTestMolFile() {
		URL url = TestConstants.class.getClassLoader().getResource("TestFiles/polycarpol.mol");
		return new File( url.getPath() );
	}
	
}
