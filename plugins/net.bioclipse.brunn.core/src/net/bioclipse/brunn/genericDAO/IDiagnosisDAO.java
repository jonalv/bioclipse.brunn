package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Diagnosis;

public interface IDiagnosisDAO extends IGenericDAO<Diagnosis> {
	
	public List<Diagnosis> findAll();

	public List<Diagnosis> findAllNotDeleted(); 
}