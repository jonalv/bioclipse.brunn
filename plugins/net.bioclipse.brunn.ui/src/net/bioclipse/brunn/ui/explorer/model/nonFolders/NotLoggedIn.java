package net.bioclipse.brunn.ui.explorer.model.nonFolders;

import java.util.ArrayList;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.model.ITreeModelListener;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

public class NotLoggedIn implements ITreeObject, IAdaptable {

	public void addListener(ITreeModelListener listener) {
		// TODO Auto-generated method stub
		
	}

	public void changeName(String name) {
		// TODO Auto-generated method stub
		
	}

	public void fireUpdate() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<ITreeObject> getChildren() {

		return new ArrayList<ITreeObject>();
	}

	public ITreeObject getFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getIconImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return "Not Logged In";
	}

	public ILISObject getPOJO() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITreeObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITreeObject getUniqueFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void removeListener(ITreeModelListener listener) {
		// TODO Auto-generated method stub
		
	}

	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void fireUpdate(IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		
	}
}