package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.UniqueFolder;

public interface IUniqueFolderDAO extends IGenericDAO<UniqueFolder> {
			
	public List<UniqueFolder> findAll();
	public List<UniqueFolder> findByUniqueName( String uniqueName );
}
