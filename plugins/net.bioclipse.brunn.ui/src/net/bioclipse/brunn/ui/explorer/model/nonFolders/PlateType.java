package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class PlateType extends AbstractNonFolder implements IEditorInput {

	public PlateType(ITreeObject parent, net.bioclipse.brunn.pojos.PlateType plateType) {
		
		super(parent, plateType);
	}

	public net.bioclipse.brunn.pojos.PlateType getPlateType() {
		return (net.bioclipse.brunn.pojos.PlateType)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	public Image getIconImage() {
		return IconFactory.getImage("plateType.gif");
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return IconFactory.getImageDescriptor("plateType.gif");
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return "PlateType";
	}
	
	public void changeName(String name) {
		
		object.setName(name);
		( (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager") ).edit( Activator.getDefault().getCurrentUser(), 
				                                                                    (net.bioclipse.brunn.pojos.PlateType)object );
	}
}
