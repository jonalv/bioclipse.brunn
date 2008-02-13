package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.MasterPlate;

public interface IMasterPlateDAO extends IGenericDAO<MasterPlate>{

	public List<MasterPlate> findAll();

	public List<MasterPlate> findAllNotDeleted();
}
