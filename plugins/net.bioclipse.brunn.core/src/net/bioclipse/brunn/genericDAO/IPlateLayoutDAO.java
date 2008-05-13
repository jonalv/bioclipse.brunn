package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.PlateLayout;

/**
 * Definition of the PlateLayoutDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IPlateLayoutDAO extends IGenericDAO<PlateLayout> {

	public List<PlateLayout> findAll();

	public List<PlateLayout> findAllNotDeleted();
}
