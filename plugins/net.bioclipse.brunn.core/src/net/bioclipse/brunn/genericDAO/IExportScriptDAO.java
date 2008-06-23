package net.bioclipse.brunn.genericDAO;

import java.util.List;

import net.bioclipse.brunn.pojos.ExportScript;

public interface IExportScriptDAO extends IGenericDAO<ExportScript> {
	
	public List<ExportScript> findAll();

}
