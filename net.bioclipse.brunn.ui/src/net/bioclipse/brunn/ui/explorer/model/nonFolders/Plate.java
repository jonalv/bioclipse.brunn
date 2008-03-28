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
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.results.PlateResults;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class Plate extends AbstractNonFolder implements IEditorInput {

	private PlateResults plateResults;
	
	public Plate(ITreeObject parent, ILISObject object) {
		super(parent, object);
	}

	public void refresh() {
		// TODO Auto-generated method stub

	}
	
	public Image getIconImage() {
		return IconFactory.getImage("plate.gif");
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
		return "Plate";
	}
	
	public void changeName(String name) {
		
		IPlateManager pm = (IPlateManager) Springcontact.getBean("plateManager");
		object = pm.getPlate(object.getId());
		object.setName(name);
		
		pm.edit( Activator.getDefault().getCurrentUser(), (net.bioclipse.brunn.pojos.Plate)object );
	}

	public PlateResults getPlateResults() {
		return plateResults;
	}

	public void setPlateResults(PlateResults plateResults) {
		this.plateResults = plateResults;
	}
}
