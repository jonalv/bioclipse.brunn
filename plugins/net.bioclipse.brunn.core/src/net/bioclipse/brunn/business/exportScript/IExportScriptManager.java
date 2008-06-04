package net.bioclipse.brunn.business.exportScript;

import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.ExportScript;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.User;

public interface IExportScriptManager {

	public void setAuditService(IAuditService auditService);
	
	public Collection<ExportScript> getAllExportScripts();
	
	public long createExportScript( User creator,
	                                Folder parent,
	       	                     	ExportScript script );
	
	public void edit( User user, ExportScript exportScript );
	
	public void delete( User user, ExportScript exportScript );
	
	public ExportScript getExportScriptById( long id );
}
