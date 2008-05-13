package net.bioclipse.brunn.business.exportScript;

import java.util.Collection;

import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.ExportScript;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;

public class ExportScriptManager extends AbstractDAOBasedExportScriptManager implements IExportScriptManager {

	public long createExportScript(User creator, Folder parent, ExportScript script) {

		parent = folderDAO.merge(parent);
		
		parent.getObjects().add(script);
		
		if( parent instanceof UniqueFolder) {
			uniqueFolderDAO.save( (UniqueFolder)parent );
		}
		else {
			folderDAO.save( parent );
		}
		
		folderDAO.save( parent );
		exportScriptDAO.save( script );
		
		auditService.audit( creator, AuditType.CREATE_EVENT, script );
		auditService.audit( creator, AuditType.UPDATE_EVENT, parent );
		
		return script.getId();
    }

	public void delete(User user, ExportScript exportScript) {
		
		exportScriptDAO.delete(exportScript);
		auditService.audit( user, AuditType.DELETE_EVENT, exportScript);
    }

	public void edit(User user, ExportScript exportScript) {
		exportScript = exportScriptDAO.merge(exportScript);
	    exportScriptDAO.save(exportScript);
	    
	    auditService.audit(user, AuditType.UPDATE_EVENT, exportScript);
    }

	public Collection<ExportScript> getAllExportScripts() {
		
	    return exportScriptDAO.findAll();
    }

	public ExportScript getExportScriptById(long id) {
	    return exportScriptDAO.getById(id);
    }
}
