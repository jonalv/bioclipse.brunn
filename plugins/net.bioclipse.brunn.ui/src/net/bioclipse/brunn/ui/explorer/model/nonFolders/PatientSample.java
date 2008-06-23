package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.pojos.PatientOrigin;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class PatientSample extends AbstractNonFolder implements IEditorInput {

	public PatientSample( 
			ITreeObject parent, 
			PatientOrigin patientOrigin) {
		
		super(parent, patientOrigin);
	}

	public net.bioclipse.brunn.pojos.PlateType getPlateType() {
		return (net.bioclipse.brunn.pojos.PlateType)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	public Image getIconImage() {
		return IconFactory.getImage("patientcell.gif");
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return IconFactory.getImageDescriptor("patientcell.gif");
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return "Patient Sample";
	}
	
	public void changeName(String name) {
		
		object.setName(name);
		( (IOriginManager) Springcontact.getBean("originManager") ).edit( 
				Activator.getDefault().getCurrentUser(), 
				(net.bioclipse.brunn.pojos.PatientOrigin)object );
	}
}
