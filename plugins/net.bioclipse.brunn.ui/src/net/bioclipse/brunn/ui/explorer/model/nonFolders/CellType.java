package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.sample.ISampleManager;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class CellType extends AbstractNonFolder implements IEditorInput {
	
	public CellType( ITreeObject parent, CellOrigin cellOrigin ) {
		
		super(parent, cellOrigin);
	}

	public CellOrigin getCellOrigin() {
		return (CellOrigin)object;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void changeName(String name) {

		IOriginManager om = (IOriginManager) Springcontact.getBean("originManager");
		object = om.getCellOrigin(object.getId());
		object.setName(name);
		om.edit(Activator.getDefault().getCurrentUser(), (CellOrigin)object);
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return IconFactory.getImageDescriptor("cellLine.gif");
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("cellLine.gif");
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return "celltype";
	}
}
