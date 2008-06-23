package net.bioclipse.brunn.business.folder;

import java.util.Collection;

import net.bioclipse.brunn.business.IAuditService;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.pojos.User;

public interface IFolderManager {

	public void setAuditService(IAuditService auditService);
	
	public Collection<UniqueFolder> getAllUniqueFolders();
	public Collection<Folder>       getAllFolders();
	
	public long createFolder( User creator,
	                           String name,
	                           Folder parent );
	
	public void edit( User user, Folder folder );
	
	public void delete( User user, Folder folder );
	
	public UniqueFolder getUniqueFolder( long id );
	public Folder       getFolder( long id );
	
	public UniqueFolder getUniqueFolder( String uniqueName );

	public void editMerging(User currentUser, Folder pojo);

	public Folder getFolder(Folder folder);

	public UniqueFolder getUniqueFolder(UniqueFolder folder);

	public void evict(UniqueFolder folder);
}
