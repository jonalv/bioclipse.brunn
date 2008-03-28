package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Annotation;

/**
 * Definition of the IMasterAnnotationTypeDAO specific methods.
 * 
 * @author jonathan
 *
 */
public interface IAnnotationDAO  extends IGenericDAO<Annotation>{
	public List<Annotation> findAll();
}