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
		List< List<Double> > rows = new ArrayList< List<Double> >();
		
		while( !plateReadParsed ) {
			String row = scanner.nextLine();
			List<Double> rowValues = new ArrayList<Double>();
			if( row.matches("^[A-H](\\s+\\S+)+$") ) {
				for ( String s : row.split("\\s+") ) {
					try {
						rowValues.add( Double.parseDouble( s.trim() ) );
					}
					catch (NumberFormatException e) {
					}
				}
			}
			else if ( row.matches("^(\\s+\\d+,)+.*") ) {
				for ( String s : row.split(",\\s") ) {
					try {
						rowValues.add( Double.parseDouble( s.trim() ) );
					}
					catch (NumberFormatException e) {
					}
				}
			}
			else {
				continue;
			}
			if( rowValues.size() != 12 ) {
				plateRead.setError( "found " + rowValues.size() + "cols" );
				return plateRead;
			}
			rows.add(rowValues);
		}
		if( rows.size() != 8 ) {
			plateRead.setError( "found " + rows.size() + "rows" );
			return plateRead;
		}
		double[][] values = new double[12][8];
		for (int i = 0; i < rows.size(); i++) {
			List<Double> row = rows.get(i);
			for (int j = 0; j < row.size(); j++) {
				values[i][j] = row.get(j);
			}
        }
		plateRead.setValues(values);
		plateRead.setError("OK");
		return plateRead;
    }

	@Override
    public List<Plate> addResultsTo(User activeUser, List<Plate> plates)
            throws IllegalArgumentException {
	    // TODO Auto-generated method stub
	    return null;
    }

	static class PlateRead96 implements PlateRead {

		private String error;
		private double[][] values;
		private String barcode;
		
		public PlateRead96(String message) {
			this.error = message;
        }

		public PlateRead96() {
			this.error = "OK";
        }

		@Override
        public double[][] getValues() {
			return values;
        }

		@Override
        public String getBarCode() {
			return barcode;
		}

		@Override
        public void setBarCode(String barcode) {
			this.barcode = barcode;
        }

		@Override
        public String getError() {
			return error;
        }

		@Override
        public void setError(String error) {
			this.error = error;
        }

		@Override
        public void setValues(double[][] values) {
			this.values = values;
        }
	}
}
