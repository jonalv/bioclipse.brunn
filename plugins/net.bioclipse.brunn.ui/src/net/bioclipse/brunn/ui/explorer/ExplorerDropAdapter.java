package net.bioclipse.brunn.ui.explorer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.folders.Resources;
import net.bioclipse.brunn.ui.transferTypes.BrunnTransfer;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;


/**
 * Drop adapter for the Brunn explorer view
 * @author jonalv
 *
 */
public class ExplorerDropAdapter extends ViewerDropAdapter {

	protected ExplorerDropAdapter(Viewer viewer) {
		super(viewer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean performDrop(Object data) {

		List<ILISObject> drops = new ArrayList<ILISObject>();

		if (data instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) data;
			for ( Object obj : sel.toList() ) {
				if (obj instanceof ILISObject) {
					drops.add( (ILISObject)obj );
				}
			}
		}
		else if (data instanceof ILISObject[]) {
			for(ILISObject domainObject : (ILISObject[])data) {
				drops.add(domainObject);
			}
		}
		else if (data instanceof ILISObject) {
			drops.add( (ILISObject)data );
		}

		Object target = getCurrentTarget();

		if( dropAttemptBetweenSorts( target, drops) ) {
			return false;
		}
		
		if (target instanceof AbstractFolder) {

			Folder targetFolder = (Folder) ( (AbstractFolder)target )
                                                             .getPOJO();

			//abort drop if dropping on self or subfolder of self
			for( ILISObject domainObject : drops ) {
				if( isSelfOrSubfolderOfSelf(domainObject, targetFolder) ) {
					return false;
				}
			}
			
			Set<AbstractFolder> toBeRefreshed 
				= new HashSet<AbstractFolder>();
			for( ILISObject domainObject : drops) {
				targetFolder.getObjects().add(domainObject);
				if ( ((AbstractFolder) target).getParent() 
					 instanceof AbstractFolder ) {
					toBeRefreshed.add( (AbstractFolder) target );	
				}
				if( ((AbstractFolder) target).getParent() instanceof 
						                                  Resources ) {
					final AbstractFolder folderToBeRefreshed 
						= (AbstractFolder) target;
				
					refreshFolders( new HashSet<AbstractFolder>() {
						{ add(folderToBeRefreshed); }
					} );
					
					return true;
				}
			}
			refreshFolders(toBeRefreshed);
		}
		else {
			System.out.println("Could not drop on target: " + target);
			return false;
		}

		return true;
	}

	private boolean dropAttemptBetweenSorts( Object target,
			                                 List<ILISObject> drops ) {

		//TODO FIXME!
		if ( target instanceof AbstractFolder ) {
			AbstractFolder f = (AbstractFolder) target;
			for( ILISObject obj : drops ) {
			}
		}
		return false;
	}

	private boolean isSelfOrSubfolderOfSelf( ILISObject domainObject, 
			                                 Folder targetFolder ) {
		if( domainObject.getId() == targetFolder.getId() ) {
			return true;
		}
		if( domainObject instanceof Folder) {
			return false;
		}
		for( ILISObject o : targetFolder.getObjects() ) {
			if( o instanceof Folder 
				&& isSelfOrSubfolderOfSelf( domainObject, (Folder) o) ) {
				return true;
			}
		}
		return false;
	}

	private void refreshFolders(Set<AbstractFolder> toBeRefreshed) {
		IFolderManager folderManager 
			= (IFolderManager) Springcontact.getBean("folderManager");
		for(AbstractFolder f : toBeRefreshed) {
			folderManager.edit( Activator.getDefault().getCurrentUser(), 
					            (Folder) f.getPOJO() );
		}
		for(AbstractFolder af : toBeRefreshed) {
			af.getParent().fireUpdate();
		}
	}

	@Override
	public boolean validateDrop( Object target, 
			                     int operation,
			                     TransferData transferType ) {
		
		if( target instanceof AbstractFolder ) {
			return true;
		}
		
		return false;
	}
}

