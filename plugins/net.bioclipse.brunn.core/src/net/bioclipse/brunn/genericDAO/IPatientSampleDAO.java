package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.PatientSample;

public interface IPatientSampleDAO extends IGenericDAO<PatientSample> {
	
	public List<PatientSample> findAll(); 

}
