package net.bioclipse.brunn.ui.explorer.model.folders;


import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;

public class DataSets extends AbstractUniqueFolder {

	public DataSets(ITreeObject parent, View explorer) {
		super(parent, "data sets", explorer);
	}
	
	public Image getIconImage() {
		return IconFactory.getImage("plates.gif");
	}
}