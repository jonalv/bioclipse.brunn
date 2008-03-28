package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class PlateLayout extends AbstractNonFolder implements IEditorInput {

	public PlateLayout(ITreeObject parent, net.bioclipse.brunn.pojos.PlateLayout object) {
		super(parent, object);
	}

	public net.bioclipse.brunn.pojos.PlateLayout getPlateLayout() {
		return (net.bioclipse.brunn.pojos.PlateLayout)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("plateLayout.gif");
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
		return "plateLayout";
	}
	
	public void changeName(String name) {
		
		object.setName(name);
		( (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager") ).edit( Activator.getDefault().getCurrentUser(), 
				                                                                    (net.bioclipse.brunn.pojos.PlateLayout)object );
	}
}
