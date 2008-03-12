package net.bioclipse.brunn.ui.explorer.model.folders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.folder.IFolderManager;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.DrugOrigin;
import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.pojos.LisObjectVisitor;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.Plate;
import net.bioclipse.brunn.pojos.PlateLayout;
import net.bioclipse.brunn.pojos.PlateType;
import net.bioclipse.brunn.pojos.UniqueFolder;
import net.bioclipse.brunn.ui.explorer.View;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.Model;

import org.eclipse.swt.graphics.Image;

public abstract class AbstractFolder extends Model 
                                     implements ITreeObject {
	
	protected ITreeObject parent;
	protected IFolderManager fm;
	protected List<ITreeObject> children;
	
	public AbstractFolder(ITreeObject parent) {
		super();
		this.parent = parent;
		fm = (IFolderManager) Springcontact.getBean("folderManager");
	}

	protected void buildChildrenList(Set<ILISObject> pojos, View explorer) {
	
		final List<ITreeObject> finalChildren = new ArrayList<ITreeObject>(); 
		final View finalExplorer = explorer;
		final AbstractFolder abstractFolder = this;
		
		LisObjectVisitor extractFolderObjects = new LisObjectVisitor() {

			public void visit(PlateType plateType) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateType( abstractFolder, plateType ) );
			}

			public void visit(PlateLayout plateLayout) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.PlateLayout( abstractFolder, plateLayout ) );
			}

			public void visit(MasterPlate masterPlate) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate( abstractFolder, masterPlate ) );
			}

			public void visit(DrugOrigin drugOrigin) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.Compound( abstractFolder, drugOrigin ) );			
			}

			public void visit(CellOrigin cellOrigin) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.CellType( abstractFolder, cellOrigin ) );			
			}

			public void visit(Plate plate) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.nonFolders.Plate( abstractFolder, plate ) );
			}

			public void visit(net.bioclipse.brunn.pojos.Folder folder) {
				finalChildren.add( 
					new net.bioclipse.brunn.ui.explorer.model.folders.Folder(abstractFolder, folder, finalExplorer) );
			}

			public void visit(UniqueFolder folder) {
				System.out.println("found a Uniquefolder in the tree. This is a bit strange...");
			}
			
		};
		
		for( ILISObject object : pojos ){
		
			if( !explorer.showPojosMarkedAsDeleted() && object.isDeleted() ) {
				continue;  //skip object if it is deleted and we are not browsing deleted objects
			}
			
			object.accept(extractFolderObjects);
		}
		Comparator<ITreeObject> comparator = new Comparator<ITreeObject>() {
			public int compare(ITreeObject o1, ITreeObject o2) {
				if( o1 != null && o1.getName() != null &&
				    o2 != null && o2.getName() != null ) {
					return o1.getName().compareTo( o2.getName() );
				}
				else return 0;
			}
		};
		children = finalChildren;
		Collections.sort(children, comparator);
		for( ITreeObject t : children) {
			t.addListener(listener);
		}
	}
	
	public ITreeObject getUniqueFolder() {
		return parent.getUniqueFolder();
	}
	
	public ITreeObject getParent() {
		return parent;
	}
	
	public Image getIconImage() {
		return null;
	}
}
