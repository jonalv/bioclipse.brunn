package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.CellSample;

public interface ICellSampleDAO extends IGenericDAO<CellSample> {
	
	public List<CellSample> findAll(); 
}