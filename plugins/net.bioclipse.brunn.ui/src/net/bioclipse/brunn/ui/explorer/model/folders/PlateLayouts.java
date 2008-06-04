package net.bioclipse.brunn.ui.explorer.model.folders;


import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;

public class PlateLayouts extends AbstractUniqueFolder {

	public PlateLayouts(ITreeObject parent, View explorer) {
		super(parent, "plateLayouts", explorer);
	}
}
