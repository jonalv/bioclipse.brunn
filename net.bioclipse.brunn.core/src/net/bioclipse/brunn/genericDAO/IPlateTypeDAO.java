package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.PlateType;

/**
 * Definition of the PlateTypeDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IPlateTypeDAO extends IGenericDAO<PlateType> {
	public List<PlateType> findAll();

	public List<PlateType> findAllNotDeleted();
}
