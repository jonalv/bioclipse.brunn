package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class MasterPlate extends AbstractNonFolder implements IEditorInput {

	public MasterPlate(ITreeObject parent, net.bioclipse.brunn.pojos.MasterPlate masterPlate) {
		super(parent, masterPlate);
	}

	public net.bioclipse.brunn.pojos.MasterPlate getMasterPlate() {
		return (net.bioclipse.brunn.pojos.MasterPlate)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("masterPlate.gif");
	}

	public void changeName(String name) {
		
		IPlateManager pm = (IPlateManager) Springcontact.getBean("plateManager");
		object = pm.getMasterPlate(object.getId());
		object.setName(name);
		pm.edit( Activator.getDefault().getCurrentUser(), (net.bioclipse.brunn.pojos.MasterPlate)object );
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return "masterPlate";
	}
}
