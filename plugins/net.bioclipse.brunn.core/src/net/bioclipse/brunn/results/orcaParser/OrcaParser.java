package net.bioclipse.brunn.results.orcaParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.operation.IOperationManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.pojos.Instrument;
import net.bioclipse.brunn.pojos.Measurement;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.Result;
import net.bioclipse.brunn.pojos.ResultType;
import net.bioclipse.brunn.pojos.User;
import net.bioclipse.brunn.pojos.Well;

public class OrcaParser {

	private Scanner scanner;
	private ArrayList<PlateRead> plateReads;
	
	public OrcaParser(File file) throws FileNotFoundException {
		scanner    = new Scanner(file);
		plateReads = new ArrayList<PlateRead>();
	}

	/**
	 * Get a list of the barcodes of the plates in the file and a 
	 * String representing the sucess of the parsing of those 
	 * values (to be humanly read)
	 * 
	 * Example: [NOREAD, OK]
	 * 
	 * @return information about the plates in the file
	 */
	public List<PlateRead> getPlatesInFile() {

		System.out.println("OrcaParser.getPlatesInFile()");
		ArrayList<PlateRead> plateReads = new ArrayList<PlateRead>();
		while( scanner.hasNextLine() ) {
			try {
				plateReads.add( parseAPlateRead() );
			}
			catch( Exception e ) {
				plateReads.add( new PlateRead(e.getMessage()) );
			}
		}
		this.plateReads = pickTheBestVersions(plateReads); 
		
		return this.plateReads;
	}

	private ArrayList<PlateRead> pickTheBestVersions(ArrayList<PlateRead> input) {
	    
		System.out.println("OrcaParser.pickTheBestVersions()");
		HashMap<String, List<PlateRead>> map = new HashMap<String,List<PlateRead>>();
		ArrayList<PlateRead> result = new ArrayList<PlateRead>();  
			
		for(PlateRead pr : input) {
			List<PlateRead> list = map.get(pr.name);
			if(list == null) {
				list = new LinkedList<PlateRead>();
			}
			list.add(pr);
			map.put(pr.name, list);
		}
		
		Comparator c = new Comparator<PlateRead>() {
			public int compare(PlateRead o1, PlateRead o2) {
				return ( (Integer)o1.getNumbersOfMaxedWells() ).compareTo( o2.getNumbersOfMaxedWells() );
			}
		};
		
		for(List<PlateRead> l : map.values()) {
			Collections.sort(l, c);
			result.add( l.get(0) );
		}
		
		Comparator resultSorter = new Comparator<PlateRead>() {

			public int compare(PlateRead arg0, PlateRead arg1) {
				return arg0.name.compareTo(arg1.name);
            }
			
		};
		
		Collections.sort(result, resultSorter);
		
	    return result;
    }

	/**
	 * Adds results to the plates. 
	 * 
	 * @param plates to be addedResults to
	 * @return plates with results added
	 * @throws IllegalArguementException if results for a given Plate does not exist 
	 * (identified by barcode)
	 */
	public List<Plate> addResultsTo( User activeUser,  List<Plate> plates ) throws IllegalArgumentException {
		
		System.out.println("OrcaParser.addResultsTo()");
		HashMap<String, PlateRead> plateReadMap = new HashMap<String, PlateRead>();
		for(PlateRead p : plateReads) {
			plateReadMap.put(p.getBarCode(), p);
		}
		
		IOperationManager om = (IOperationManager) Springcontact.getBean("operationManager");
		IPlateManager     pm = (IPlateManager) Springcontact.getBean("plateManager");
		List<Plate> results = new ArrayList<Plate>();
		for(Plate p : plates) {
			om.addResult( activeUser, plateReadMap.get(p.getBarcode()), p );
			results.add(p);
		}
		return plates;
	}


	private PlateRead parseAPlateRead() {
		
		String line = nextNonEmptyLine(); 		
		/*
		 * Parse the headLine of a plateRead
		 */
		String[] headLine = line.split(",");
		String[] readMethod = headLine[3].split(" ");
		if( !readMethod[1].equals("384") ) {
			throw new OrcaParseException("This doesn't seem to be a plate with 384 wells");
		}
		PlateRead p = new PlateRead( headLine[0], 
				                     headLine[1],
				                     Integer.parseInt( readMethod[2]) );
		
		/*
		 * Parse the values
		 */
		ArrayList<int[]> data = new ArrayList<int[]>();
		while( scanner.hasNextLine() ) {
			
			line = scanner.nextLine();
			
			if( "".equals(line) ) 
				break;
			
			String[] row = line.split(",");
			int[] values = new int[row.length]; 
			for (int i = 0; i < row.length; i++) {
				values[i] = Integer.parseInt( row[i] );
			}
			data.add(values);
		}
		p.values = data.toArray(p.values);
		return p;
    }

	private String nextNonEmptyLine() {
		String line = scanner.nextLine();
		while( "".equals(line) && scanner.hasNextLine() ) {
			line = scanner.nextLine();
		}
	    return line;
    }
	
	public static class PlateRead {
		
		int[][] values;
		int intensity;
		String name;
		String barCode;
		int numbersOfMaxedWells;
		String error;
		
		public PlateRead( String error ) {
			
			this.error   = error;
			this.values  = new int[0][0];
			this.name    = "";
			this.barCode = "";
		}
		
		public PlateRead( String name, String barCode, int intensity ) {
			
			this.name      = name;
			this.barCode   = barCode;
			this.intensity = intensity;
			this.values    = new int[0][0];
			this.error     = "OK";
		}

		public String getBarCode() {
        	return barCode;
        }

		public void setBarCode(String barCode) {
        	this.barCode = barCode;
        }

		public String getError() {
        	if(getNumberOfValues() != 384) {
        		return "Did not read 384 values";
        	}
			return error;
        }

		public void setError(String error) {
        	this.error = error;
        }

		public int getIntensity() {
        	return intensity;
        }

		public void setIntensity(int intensity) {
        	this.intensity = intensity;
        }

		public String getName() {
        	return name;
        }

		public void setName(String name) {
        	this.name = name;
        }

		public int getNumbersOfMaxedWells() {
        	return numbersOfMaxedWells;
        }

		public void setNumbersOfMaxedWells(int numbersOfMaxedWells) {
        	this.numbersOfMaxedWells = numbersOfMaxedWells;
        }

		public int[][] getValues() {
        	return values;
        }

		public int getNumberOfValues() {
			return values.length * values[0].length;
		}
		
		public void setValues(int[][] values) {
        	this.values = values;
        }
	}
	
	public class OrcaParseException extends RuntimeException {

		public OrcaParseException(String string) {
			super(string);
        }
    }
}
