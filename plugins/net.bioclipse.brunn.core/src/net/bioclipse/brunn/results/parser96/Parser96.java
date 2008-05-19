package net.bioclipse.brunn.results.parser96;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.results.PlateRead;
import net.bioclipse.brunn.results.ResultParser;

public class Parser96 implements ResultParser {

	private Scanner scanner;
	private List<PlateRead> plateReads;

	public Parser96(File file) throws FileNotFoundException {
		scanner = new Scanner(file);
		plateReads = new ArrayList<PlateRead>();
	}

	public List<PlateRead> getPlatesInFile() {
		while( scanner.hasNextLine() ) {
			try {
				plateReads.add( parseAPlateRead() );
			}
			catch( Exception e ) {
				plateReads.add( new PlateRead96(e.getMessage()) );
			}
		}
		return plateReads;
	}
	
	
	private PlateRead parseAPlateRead() {
		PlateRead plateRead = new PlateRead96();
		boolean plateReadParsed = false;
		while( !plateReadParsed ) {
			String row = scanner.nextLine();
			if( row.matches("^[A-H](\\s+\\S+)+$") ) {
				
			}
			else if ( row.matches("^(\\s+\\d+,)+.*") ) {
				row.split(",\\s");
			}
		}
		
	    return null;
    }

	@Override
    public List<Plate> addResultsTo(User activeUser, List<Plate> plates)
            throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
    }

	static class PlateRead96 implements PlateRead {

		public PlateRead96(String message) {
	        // TODO Auto-generated constructor stub
        }

		public PlateRead96() {
	        // TODO Auto-generated constructor stub
        }

		@Override
        public double[][] getValues() {

	        return null;
        }
		
	}
}
