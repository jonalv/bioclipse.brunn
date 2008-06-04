package net.bioclipse.brunn.genericDAO;

import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.pojos.SampleContainer;

/**
 * Definition of the SampleContainerDAO specific methods.
 * 
 * @author jonathan
 */

public interface ISampleContainerDAO extends IGenericDAO<SampleContainer>{
	public List<SampleContainer> findAll();

}
