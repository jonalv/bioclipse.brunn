package net.bioclipse.brunn.ui.explorer.model.folders;


import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class PlateLayouts extends AbstractUniqueFolder {

	public PlateLayouts(ITreeObject parent, View explorer) {
		super(parent, "plateLayouts", explorer);
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("platelayouts.gif");
	}
}
