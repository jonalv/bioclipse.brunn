package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class PickList extends AbstractNonFolder implements IEditorInput {
	
	public PickList(ITreeObject parent, net.bioclipse.brunn.pojos.PickList pickList) {
		
		super(parent, pickList);
	}

	public net.bioclipse.brunn.pojos.PickList getPickList() {
		return (net.bioclipse.brunn.pojos.PickList)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	public Image getIconImage() {
		return IconFactory.getImage("pickList.gif");
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return IconFactory.getImageDescriptor("pickList.gif");
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
