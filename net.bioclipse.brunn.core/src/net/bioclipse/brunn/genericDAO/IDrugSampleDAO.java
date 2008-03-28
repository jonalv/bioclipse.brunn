package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.DrugSample;

public interface IDrugSampleDAO extends IGenericDAO<DrugSample> {
	
	public List<DrugSample> findAll(); 
}