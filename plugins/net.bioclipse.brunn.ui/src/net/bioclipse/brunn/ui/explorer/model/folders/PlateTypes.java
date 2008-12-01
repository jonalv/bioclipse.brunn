package net.bioclipse.brunn.ui.explorer.model.folders;


import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class PlateTypes extends AbstractUniqueFolder {
	
	public PlateTypes(ITreeObject parent, View explorer) {
		
		super(parent, "plateTypes", explorer);
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("platetypes.gif");
	}
}
