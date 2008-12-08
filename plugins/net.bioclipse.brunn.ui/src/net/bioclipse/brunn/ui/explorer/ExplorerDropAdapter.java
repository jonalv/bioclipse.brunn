package net.bioclipse.brunn.ui.explorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.folders.Resources;
import net.bioclipse.brunn.ui.transferTypes.BrunnTransfer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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

		final Object target = getCurrentTarget();
		if( !(target instanceof AbstractFolder)) {
			return false;
		}
		
		final List<ILISObject> drops = new ArrayList<ILISObject>();

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

		if( dropAttemptBetweenSorts( (AbstractFolder)target, drops) ) {
			return false;
		}
		
		if (target instanceof AbstractFolder) {

			final Folder targetFolder = (Folder) ( (AbstractFolder)target )
                                                                   .getPOJO();

			//abort drop if dropping on self or subfolder of self
			for( ILISObject domainObject : drops ) {
				if( isSelfOrSubfolderOfSelf(domainObject, targetFolder) ) {
					return false;
				}
			}
			
			Job job = new Job("Brunn-drop") {

                @Override
                protected IStatus run( IProgressMonitor monitor ) {

                    Set<AbstractFolder> toBeRefreshed 
                        = new HashSet<AbstractFolder>();
                    monitor.beginTask( "dropping", drops.size()+1 );
                    for ( ILISObject domainObject : drops) {
                        targetFolder.getObjects().add(domainObject);
                        if ( ((AbstractFolder) target).getParent() 
                                instanceof AbstractFolder ) {
                            toBeRefreshed.add( (AbstractFolder) target );   
                        }
                        if ( ((AbstractFolder) target).getParent() instanceof 
                                Resources ) {
                            final AbstractFolder folderToBeRefreshed 
                            = (AbstractFolder) target;

                            refreshFolders( new HashSet<AbstractFolder>() {
                                { add(folderToBeRefreshed); }
                            } );

                        }
                        monitor.worked( 1 );
                    }
                    refreshFolders(toBeRefreshed);
                    monitor.done();
                    return Status.OK_STATUS;
                }
			};
			job.schedule();
			try {
                job.join();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            }
		}
		else {
			System.out.println("Could not drop on target: " + target);
			return false;
		}

		return true;
	}

	private boolean dropAttemptBetweenSorts( AbstractFolder target,
			                                 List<ILISObject> drops ) {

		Map<ILISObject, Boolean> dropsFound 
			= new HashMap<ILISObject, Boolean>();
		for(ILISObject drop : drops) {
			dropsFound.put(drop, false);
		}
		
		AbstractFolder f = (AbstractFolder) 
		                 ( (AbstractFolder) target ).getUniqueFolder();
		
		for( ILISObject drop : drops ) {
				dropAttempBetweenSortsHelper( f, 
						                      dropsFound,
						                      drop );
		}
		for( boolean found : dropsFound.values() ) {
			if( !found ) {
				return true;
			}
		}
		return false;
	}

	private void dropAttempBetweenSortsHelper( 
			AbstractFolder folder,
		    Map<ILISObject, Boolean> dropsFound, 
		    ILISObject drop ) {
		
		for( ITreeObject child : folder.getChildren() ) {
			if ( child.getPOJO().equals(drop) ) {
				dropsFound.put(drop, true);
				return;
			}
			if( child instanceof AbstractFolder ) {
				dropAttempBetweenSortsHelper( (AbstractFolder)child, 
						                       dropsFound,
						                       drop );
			}
		}
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

