package net.bioclipse.brunn.ui.explorer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.Model;
import net.bioclipse.brunn.ui.explorer.model.folders.DataSets;
import net.bioclipse.brunn.ui.explorer.model.folders.Resources;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.NotLoggedIn;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

public class TreeRoot extends Model implements ITreeObject {

	private ArrayList<ITreeObject> children;
	private String name;
	private View explorer;
	
	public TreeRoot(View explorer) {
		
		this.name = "LIS Explorer";
		this.explorer = explorer;
	}
	
	public synchronized void refresh() {
		if(children == null) {
			this.children = buildChildrenList();
		}
		else {
			for(ITreeObject t : children) {
				t.refresh();
			}
		}
	}
	
	private ArrayList<ITreeObject> buildChildrenList() {

		final ArrayList<ITreeObject> children = new ArrayList<ITreeObject>();
		
		children.add( new Resources( this, explorer ) );
		children.add( new DataSets(  this, explorer ) );
		
		return children;
	}
	
	public ArrayList<ITreeObject> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public ITreeObject getParent() {
		return null;
	}
	public ILISObject getPOJO() {
		throw new UnsupportedOperationException("TreeRoot has no persistant POJO");
	}

	public ITreeObject getFolder() {
		throw new UnsupportedOperationException("There is no <code>AbstractFolder</code> above the Tree Root");
	}

	public ITreeObject getUniqueFolder() {
		throw new UnsupportedOperationException("There is no <code>AbstractUniqueFolder</code> above the Tree Root");
	}
	
	public Image getIconImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void changeName(String name) {
		throw new UnsupportedOperationException("Can not change name of TreeRoot");
	}

	public void setLoggedOut() {
		this.children = new ArrayList<ITreeObject>();
		children.add( new NotLoggedIn() );
	}
	
	public void setLoggedIn() {
		children = buildChildrenList();
	}
}
