package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Measurement;

/**
 * Definition of the MeasurementDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IMeasurementDAO extends IGenericDAO<Measurement> {

	public List<Measurement> findAll(); 
	
}
