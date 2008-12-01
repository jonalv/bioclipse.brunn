package net.bioclipse.brunn.ui.explorer.model.folders;

import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class PatientCells extends AbstractUniqueFolder {

	public PatientCells(ITreeObject parent, View explorer) {
		super(parent, "patientCells", explorer);
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("patientcells.gif");
	}
}
