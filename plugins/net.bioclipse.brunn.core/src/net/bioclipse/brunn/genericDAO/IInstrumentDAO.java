package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Instrument;

/**
 * Definition of the InstrumentDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IInstrumentDAO extends IGenericDAO<Instrument> {

	public List<Instrument> findAll();
	public List<Instrument> findByName(String name); 
	
}
