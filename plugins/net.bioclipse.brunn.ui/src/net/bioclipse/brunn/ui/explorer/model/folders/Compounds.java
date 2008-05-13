package net.bioclipse.brunn.ui.explorer.model.folders;

import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractUniqueFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;

public class Compounds extends AbstractUniqueFolder {

	public Compounds(ITreeObject parent, View explorer) {
		super(parent, "compounds", explorer);
	}
}
