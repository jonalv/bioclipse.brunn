package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jonathan
 *
 */
public class Folder extends AbstractAuditableObject {
	
	protected Set<ILISObject> objects;

	public Folder() {
	    super();
	    objects = new HashSet<ILISObject>();
    }

	public Folder(User creator, String name, Folder parent) {
	    super(creator, name);
	    this.objects = new HashSet<ILISObject>();
	    parent.getObjects().add(this);
    }

	public Set<ILISObject> getObjects() {
    	return objects;
    }

	public void setObjects(Set<ILISObject> objects) {
    	this.objects = objects;
    }

	public void delete() {
		
		super.delete();
		
		for( ILISObject object : objects ) {
			object.delete();
		}
	}

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if (!(obj instanceof Folder) )
		    return false;
	    final Folder other = (Folder) obj;
	    if (objects == null) {
		    if (other.getObjects() != null)
			    return false;
	    }
	    
	    final Set<ILISObject> visitedObjects = new HashSet<ILISObject>();
	    
	    LisObjectVisitor proxyVisitor = new LisObjectVisitor() {

			public void visit(PlateType plateType) {
				visitedObjects.add(plateType);
            }

			public void visit(PlateLayout plateLayout) {
				visitedObjects.add(plateLayout);
            }

			public void visit(MasterPlate masterPlate) {
				visitedObjects.add(masterPlate);
            }

			public void visit(DrugOrigin drugOrigin) {
				visitedObjects.add(drugOrigin);
            }

			public void visit(CellOrigin cellOrigin) {
				visitedObjects.add(cellOrigin);
            }

			public void visit(Plate plate) {
				visitedObjects.add(plate);
            }

			public void visit(Folder folder) {
				visitedObjects.add(folder);
            }

			public void visit(UniqueFolder folder) {
				visitedObjects.add(folder);
            }

			@Override
            public void visit(PatientOrigin patientOrigin) {
				visitedObjects.add(patientOrigin);
            }
	    };
	    for( ILISObject o : other.getObjects() ) {
	    	o.accept(proxyVisitor);
	    }
	    
	    Set<ILISObject> otherObjects = new HashSet<ILISObject>(visitedObjects);
	    visitedObjects.clear();
	    
	    for( ILISObject o : this.getObjects() ) {
	    	o.accept(proxyVisitor);
	    }
	    Set<ILISObject> thisObjects  = new HashSet<ILISObject>(visitedObjects);
	    
	    return otherObjects.equals(thisObjects);
    }
	
	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}
}