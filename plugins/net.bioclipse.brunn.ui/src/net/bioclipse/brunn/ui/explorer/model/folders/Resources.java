package net.bioclipse.brunn.ui.explorer.model.folders;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.folders.CellTypes;
import net.bioclipse.brunn.ui.explorer.model.folders.Compounds;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.folders.MasterPlates;
import net.bioclipse.brunn.ui.explorer.model.Model;
import net.bioclipse.brunn.ui.explorer.model.folders.PatientCells;
import net.bioclipse.brunn.ui.explorer.model.folders.PlateLayouts;
import net.bioclipse.brunn.ui.explorer.model.folders.PlateTypes;
import net.bioclipse.brunn.ui.images.IconFactory;


public class Resources extends Model 
                       implements ITreeObject {

	private String name;
	private ArrayList<ITreeObject> children;
	private ITreeObject parent;
	private View explorer;
	
	public Resources(ITreeObject parent, View explorer) {
		
		this.name = "Resources";
		this.parent = parent;
		this.explorer = explorer;
		refresh();
	}

	public ArrayList<ITreeObject> getChildren() {
		return children;
	}

	public String getName() {
		return name;
	}

	public ITreeObject getParent() {
		return parent;
	}
	
	public void refresh() {
		
		if(children == null) {
			children = new ArrayList<ITreeObject>();

			children.add( new PlateTypes(this, explorer)   );
			children.add( new PlateLayouts(this, explorer) );
			children.add( new MasterPlates(this, explorer) );
			children.add( new Compounds(this, explorer)    );
			children.add( new CellTypes(this, explorer)    );
			children.add( new PatientCells(this, explorer) );
			
			for(ITreeObject t : children) {
				t.addListener(explorer.getTreeModelListener());
			}
		}
		else {
			for( ITreeObject treeObject : children) {
				treeObject.refresh();
			}
		}
	}

	public ILISObject getPOJO() {
		return null;
	}

	public ITreeObject getFolder() {
		throw new UnsupportedOperationException("There is no <code>AbstractFolder</code> above the Resources Folder");
	}

	public Image getIconImage() {
		return IconFactory.getImage("folder.gif");
	}

	public void changeName(String name) {
		throw new UnsupportedOperationException("Can not change name of resources folder");
	}

	public ITreeObject getUniqueFolder() {
		return this;
	}
}
