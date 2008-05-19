package net.bioclipse.brunn.results;

import java.util.List;

import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.User;

public interface ResultParser {

	/**
	 * Adds results to the plates. 
	 * 
	 * @param plates to be addedResults to
	 * @return plates with results added
	 * @throws IllegalArguementException if results for a given Plate does not exist 
	 * (identified by barcode)
	 */
	public List<Plate> addResultsTo( User activeUser,  List<Plate> plates ) throws IllegalArgumentException;
}
