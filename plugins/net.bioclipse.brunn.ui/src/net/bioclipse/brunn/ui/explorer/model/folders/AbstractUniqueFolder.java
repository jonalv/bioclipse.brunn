package net.bioclipse.brunn.ui.explorer.model.folders;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.brunn.business.LazyLoadingSessionHolder;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;


public abstract class AbstractUniqueFolder extends AbstractFolder {

	protected UniqueFolder folder;
	private View explorer;
	
	public AbstractUniqueFolder( ITreeObject parent, 
			                     String folderName, 
			                     View explorer) {
	
		super(parent);
		folder = fm.getUniqueFolder(folderName);
		this.explorer = explorer;
	}
	
	public List<ITreeObject> getChildren() {
		
		if(children == null) {
			buildChildrenList( folder.getObjects(), explorer );
		}
		return children;
	}
	
	public String getName() {
		return folder.getName();
	}
	
	public void refresh() {
		
		fm.evict( folder );
		folder = fm.getUniqueFolder( folder );
		children = null;
	}
	
	
	
	public ILISObject getPOJO() {
		return folder;
	}
	
	public ITreeObject getFolder() {
		return this;
	}
	
	public void changeName(String name) {
		throw new UnsupportedOperationException("Can not change name of unique folder");
	}
	
	public ITreeObject getUniqueFolder() {
		return this;
	}
}
