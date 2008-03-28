package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.Folder;

public interface IFolderDAO extends IGenericDAO<Folder> {
			
	public List<Folder> findAll();
}
