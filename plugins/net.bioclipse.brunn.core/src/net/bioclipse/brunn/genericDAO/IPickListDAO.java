package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.PickList;

/**
 * Definition of the PickListDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IPickListDAO extends IGenericDAO<PickList> {
	public List<PickList> findAll();
}
