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
import net.bioclipse.brunn.results.PlateRead;
import net.bioclipse.brunn.results.ResultParser;

public class OrcaParser implements ResultParser {

	private Scanner scanner;
	private ArrayList<OrcaPlateRead> plateReads;
	
	public OrcaParser(File file) throws FileNotFoundException {
		scanner    = new Scanner(file);
		plateReads = new ArrayList<OrcaPlateRead>();
	}

	/**
	 * @return information about the plates in the file
	 */
	public List<OrcaPlateRead> getPlatesInFile() {

		System.out.println("OrcaParser.getPlatesInFile()");
		ArrayList<OrcaPlateRead> plateReads = new ArrayList<OrcaPlateRead>();
		while( scanner.hasNextLine() ) {
			try {
				plateReads.add( parseAPlateRead() );
			}
			catch( Exception e ) {
				plateReads.add( new OrcaPlateRead(e.getMessage()) );
			}
		}
		this.plateReads = pickTheBestVersions(plateReads); 
		
		return this.plateReads;
	}

	private ArrayList<OrcaPlateRead> pickTheBestVersions(ArrayList<OrcaPlateRead> input) {
	    
		System.out.println("OrcaParser.pickTheBestVersions()");
		HashMap<String, List<OrcaPlateRead>> map = new HashMap<String,List<OrcaPlateRead>>();
		ArrayList<OrcaPlateRead> result = new ArrayList<OrcaPlateRead>();  
			
		for(OrcaPlateRead pr : input) {
			List<OrcaPlateRead> list = map.get(pr.name);
			if(list == null) {
				list = new LinkedList<OrcaPlateRead>();
			}
			list.add(pr);
			map.put(pr.name, list);
		}
		
		Comparator c = new Comparator<OrcaPlateRead>() {
			public int compare(OrcaPlateRead o1, OrcaPlateRead o2) {
				return ( (Integer)o1.getNumbersOfMaxedWells() ).compareTo( o2.getNumbersOfMaxedWells() );
			}
		};
		
		for(List<OrcaPlateRead> l : map.values()) {
			Collections.sort(l, c);
			result.add( l.get(0) );
		}
		
		Comparator resultSorter = new Comparator<OrcaPlateRead>() {

			public int compare(OrcaPlateRead arg0, OrcaPlateRead arg1) {
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
		HashMap<String, OrcaPlateRead> plateReadMap = new HashMap<String, OrcaPlateRead>();
		for(OrcaPlateRead p : plateReads) {
			plateReadMap.put(p.getBarCode(), p);
		}
		
		IOperationManager om = (IOperationManager) Springcontact.getBean("operationManager");
		List<Plate> results = new ArrayList<Plate>();
		for(Plate p : plates) {
			om.addResult( activeUser, plateReadMap.get(p.getBarcode()), p );
			results.add(p);
		}
		return plates;
	}

	private OrcaPlateRead parseAPlateRead() {
		
		String line = nextNonEmptyLine(); 		
		/*
		 * Parse the headLine of a plateRead
		 */
		String[] headLine = line.split(",");
		String[] readMethod = headLine[3].split(" ");
		if( !readMethod[1].equals("384") ) {
			throw new OrcaParseException("This doesn't seem to be a plate with 384 wells");
		}
		OrcaPlateRead p = new OrcaPlateRead( headLine[0], 
				                     headLine[1],
				                     Integer.parseInt( readMethod[2]) );
		
		/*
		 * Parse the values
		 */
		ArrayList<double[]> data = new ArrayList<double[]>();
		while( scanner.hasNextLine() ) {
			
			line = scanner.nextLine();
			
			if( "".equals(line) ) 
				break;
			
			String[] row = line.split(",");
			double[] values = new double[row.length]; 
			for (int i = 0; i < row.length; i++) {
				values[i] = Double.parseDouble( row[i] );
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
	
	public static class OrcaPlateRead implements PlateRead {
		
		double[][] values;
		int intensity;
		String name;
		String barCode;
		int numbersOfMaxedWells;
		String error;
		
		public OrcaPlateRead( String error ) {
			
			this.error   = error;
			this.values  = new double[0][0];
			this.name    = "";
			this.barCode = "";
		}
		
		public OrcaPlateRead( String name, String barCode, int intensity ) {
			
			this.name      = name;
			this.barCode   = barCode;
			this.intensity = intensity;
			this.values    = new double[0][0];
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

		public double[][] getValues() {
        	return values;
        }

		public int getNumberOfValues() {
			return values.length * values[0].length;
		}
		
		public void setValues(double[][] values) {
        	this.values = values;
        }
	}
	
	public class OrcaParseException extends RuntimeException {

		public OrcaParseException(String string) {
			super(string);
        }
    }
}
