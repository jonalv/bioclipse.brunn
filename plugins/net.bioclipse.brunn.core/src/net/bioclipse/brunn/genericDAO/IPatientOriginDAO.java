package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.PatientOrigin;

public interface IPatientOriginDAO extends IGenericDAO<PatientOrigin> {
	
	public List<PatientOrigin> findAll();

	public List<PatientOrigin> findAllNotDeleted(); 
}
