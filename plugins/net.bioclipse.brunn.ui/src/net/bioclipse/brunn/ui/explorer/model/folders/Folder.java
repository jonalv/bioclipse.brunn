package net.bioclipse.brunn.ui.explorer.model.folders;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.images.IconFactory;
import net.bioclipse.brunn.ui.explorer.View;

public class Folder extends AbstractFolder {

	net.bioclipse.brunn.pojos.Folder folder;
	private View explorer;
	
	public Folder(ITreeObject parent, ILISObject object, View explorer) {

		super(parent);
		folder = (net.bioclipse.brunn.pojos.Folder)object;
		this.explorer = explorer;
	}

	public List<ITreeObject> getChildren() {
		
		if(children == null) {
			buildChildrenList(folder.getObjects(), explorer);
		}
		return children;
	}

	public ITreeObject getFolder() {
		return this;
	}

	public String getName() {
		return folder.getName();
	}

	public ILISObject getPOJO() {
		return folder;
	}

	public void refresh() {
		folder = fm.getFolder( folder );
		children = null;
	}

	public Image getIconImage() {
		return IconFactory.getImage("folder.gif");
	}
	
	public void changeName(String name) {
		folder.setName(name);
		fm.edit(Activator.getDefault().getCurrentUser(), folder);
	}
}
