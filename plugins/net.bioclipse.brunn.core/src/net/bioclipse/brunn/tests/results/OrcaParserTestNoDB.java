package net.bioclipse.brunn.tests.results;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.bioclipse.brunn.results.orcaParser.OrcaParser;
import net.bioclipse.brunn.results.orcaParser.OrcaParser.OrcaPlateRead;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class OrcaParserTestNoDB {

	@Test
	public void testParseOneCorrectPlateRead() {
		OrcaParser parser = null;
		try { 
			String path = ClassLoader.getSystemResource("TestFiles" + File.separator + "oneCorrectPlateRead.log").getPath();
	        parser = new OrcaParser(new File(path));
        }
        catch (FileNotFoundException e) {
	        fail( e.getMessage() );
        }
        List<OrcaPlateRead> plateReads = parser.getPlatesInFile();
        assertEquals( "NOREAD", plateReads.get(0).getBarCode() );
        assertEquals( "OK",     plateReads.get(0).getError()   );
	}
	
	@Test
	public void testParseOneBrokenPlateRead() {
		OrcaParser parser = null;
		try {
			String path = ClassLoader.getSystemResource("TestFiles" + File.separator + "oneBrokenPlateRead").getPath();
	        parser = new OrcaParser(new File(path));
        }
        catch (FileNotFoundException e) {
	        fail( e.getMessage() );
        }
        List<OrcaPlateRead> plateReads = parser.getPlatesInFile();
        assertEquals( "NOREAD", plateReads.get(0).getBarCode() );
        assertEquals( "OK",     plateReads.get(0).getError()   );
        assertEquals( "NOREAD", plateReads.get(1).getBarCode() );
        assertTrue( "The second plate should not be OK", !"OK".equals(plateReads.get(1).getError()) );
	}
}
