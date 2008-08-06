package net.bioclipse.brunn.business.folder;

import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.pojos.AuditType;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;

public class FolderManager extends AbstractDAOBasedFolderManager implements IFolderManager {
 
	public long createFolder(User creator, String name, Folder parent ) {
		
		parent = folderDAO.merge(parent);
		
		Folder folder = new Folder(creator, name, parent );
		
		if( parent instanceof UniqueFolder) {
			uniqueFolderDAO.save( (UniqueFolder)parent );
		}
		else {
			folderDAO.save( parent );
		}
		
		folderDAO.save( folder );
		
		auditService.audit(creator, AuditType.CREATE_EVENT, folder);
		auditService.audit(creator, AuditType.UPDATE_EVENT, parent);
		
		return folder.getId();
	}

	public void delete( User user, Folder folder) {
		
		folderDAO.delete(folder);
		
		auditService.audit(user, AuditType.DELETE_EVENT, folder);

	}

	public void edit( User user, Folder folder ) {

		folder = folderDAO.merge(folder);
		folderDAO.save(folder);
		
		auditService.audit(user, AuditType.UPDATE_EVENT, folder);
	}
	
	public void editMerging( User user, Folder folder ) {
		
		folder = folderDAO.merge(folder);
		folderDAO.save(folder);
		
		auditService.audit(user, AuditType.UPDATE_EVENT, folder);
	}

	public Collection<Folder> getAllFolders() {

		return folderDAO.findAll();
	}

	public Collection<UniqueFolder> getAllUniqueFolders() {

		return uniqueFolderDAO.findAll();
	}

	public Folder getFolder(long id) {

//		return (Folder) LazyLoadingSessionHolder.getInstance().createQuery("from Folder where id = " + id).list().get(0);
		
		return folderDAO.getById(id);
	}

	public UniqueFolder getUniqueFolder(long id) {
		
//		return (UniqueFolder) LazyLoadingSessionHolder.getInstance().createQuery("from UniqueFolder where id = " + id).list().get(0);
		return uniqueFolderDAO.getById(id);
	}

	public void setAuditService(IAuditService auditService) {
		
		this.auditService = auditService;
	}

	public UniqueFolder getUniqueFolder(String uniqueName) {
//		UniqueFolder folder = 
//			(UniqueFolder) LazyLoadingSessionHolder
//			               .getInstance()
//			               .createQuery("from UniqueFolder where uniqueName = '" + uniqueName + "'").list().get(0);
//		
//		LazyLoadingSessionHolder.getInstance().setReadOnly(folder, true);
//		
//		return folder;
	    return uniqueFolderDAO.findByUniqueName(uniqueName).get(0);
    }

	public Folder getFolder(Folder folder) {
		LazyLoadingSessionHolder.getInstance().evict(folder);
		folder = 
			(Folder) LazyLoadingSessionHolder
			         .getInstance()
			         .createQuery("from Folder where id = '" + folder.getId() + "'").list().get(0);

		LazyLoadingSessionHolder.getInstance().setReadOnly(folder, true);
		return folder;
    }

	public UniqueFolder getUniqueFolder(UniqueFolder folder) {
		LazyLoadingSessionHolder.getInstance().evict(folder);
//		folder = 
//			(UniqueFolder) LazyLoadingSessionHolder
//			               .getInstance()
//			               .createQuery("from UniqueFolder where id = '" + folder.getId() + "'").list().get(0);
		folder = uniqueFolderDAO.getById(folder.getId());
		LazyLoadingSessionHolder.getInstance().setReadOnly(folder, true);
		
		return folder;
    }

	public void evict(UniqueFolder folder) {
	    LazyLoadingSessionHolder.getInstance().evict(folder);
    }
}
