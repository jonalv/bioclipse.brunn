package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.ResultType;

/**
 * Definition of the ResultTypeDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IResultTypeDAO extends IGenericDAO<ResultType> {
    public List<ResultType> findAll();
	public List<ResultType> findByName(String name);
}