package net.bioclipse.brunn.ui.explorer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.pojos.Folder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;


/**
 * Drop adapter for the Brunn explorer view
 * @author jonalv
 *
 */
public class ExplorerDropAdapter extends ViewerDropAdapter {

	protected ExplorerDropAdapter(Viewer viewer) {
		super(viewer);
	}

	@Override
	public boolean performDrop(Object data) {

		List<ITreeObject> inputs = new ArrayList<ITreeObject>();

		if (data instanceof ITreeObject) {
			IStructuredSelection sel = (IStructuredSelection) data;
			for ( Object obj : sel.toList() ) {
				if (obj instanceof ITreeObject) {
					inputs.add( (ITreeObject)obj );
				}
			}
		}
		else if (data instanceof ITreeObject) {
			inputs.add( (ITreeObject)data );
		}

		Object target = getCurrentTarget();

		if (target instanceof AbstractFolder) {
			Set<AbstractFolder> toBeRefreshed 
				= new HashSet<AbstractFolder>();
			AbstractFolder type = (AbstractFolder)target;
			for( ITreeObject input : inputs) {
				ILISObject domainObject = input.getPOJO();
				Folder parentFolder = (Folder) input.getParent().getPOJO();
				parentFolder.getObjects().remove(domainObject);
				Folder targetFolder = (Folder) ( (AbstractFolder)target )
				                                                 .getPOJO();
				targetFolder.getObjects().add(domainObject);
				toBeRefreshed.add((AbstractFolder) target);
				toBeRefreshed.add((AbstractFolder) input.getParent());
			}
			IFolderManager folderManager 
				= (IFolderManager) Springcontact.getBean("folderManager");
			for(AbstractFolder f : toBeRefreshed) {
				folderManager.edit( Activator.getDefault().getCurrentUser(), 
						            (Folder) f.getPOJO() );
			}
			for(AbstractFolder af : toBeRefreshed) {
				af.fireUpdate();
			}
		}
		else {
			System.out.println("Could not drop on target: " + target);
			return false;
		}

		return true;
	}

	/**
	 * TODO: update from accepting all
	 */
	@Override
	public boolean validateDrop( Object target, 
			                     int operation,
			                     TransferData transferType ) {
		return true;
	}
}

