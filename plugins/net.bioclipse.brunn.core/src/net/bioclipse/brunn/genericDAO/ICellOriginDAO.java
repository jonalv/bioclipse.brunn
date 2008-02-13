package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.CellOrigin;

public interface ICellOriginDAO extends IGenericDAO<CellOrigin> {
	
	public List<CellOrigin> findAll();

	public List<CellOrigin> findAllNotDeleted(); 
}