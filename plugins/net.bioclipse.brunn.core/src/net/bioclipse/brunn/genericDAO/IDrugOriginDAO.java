package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.DrugOrigin;

public interface IDrugOriginDAO extends IGenericDAO<DrugOrigin> {
	
	public List<DrugOrigin> findAll();

	public List<DrugOrigin> findAllNotDeleted(); 
}
