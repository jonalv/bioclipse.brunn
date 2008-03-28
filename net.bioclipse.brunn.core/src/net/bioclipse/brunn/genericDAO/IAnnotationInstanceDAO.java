package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.AbstractAnnotationInstance;

/**
 * Definition of the AnnotationInstanceDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IAnnotationInstanceDAO extends IGenericDAO<AbstractAnnotationInstance>{
	public List<AbstractAnnotationInstance> findAll();
}
