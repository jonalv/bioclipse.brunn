package net.bioclipse.brunn.pojos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;

/**
 * @author jonathan
 *
 */
public class DrugOrigin extends AbstractAuditableObject {

	private double          molecularWeight;
	private Set<DrugSample> drugSamples;
	private Blob            structure;
	private String          uniqueName;
	
	public DrugOrigin() {
	    super();
	    this.drugSamples = new HashSet<DrugSample>();
    }

	public DrugOrigin( User creator, 
	                   String name, 
	                   InputStream structureStream,
	                   double molecularWeight ) throws IOException {
	    
		super(creator, name);
		this.uniqueName = name;
		if(structureStream != null ){
			this.structure = Hibernate.createBlob( structureStream );
		}
	    this.molecularWeight = molecularWeight;
	    this.drugSamples = new HashSet<DrugSample>();
    }

	public Set<DrugSample> getDrugSamples() {
    	return drugSamples;
    }

	public void setDrugSamples(Set<DrugSample> drugSamples) {
    	this.drugSamples = drugSamples;
    }

	public double getMolecularWeight() {
    	return molecularWeight;
    }

	public void setMolecularWeight(double molecularWeight) {
    	this.molecularWeight = molecularWeight;
    }

	public InputStream getStructureInputStream() throws SQLException {
    	if( structure != null )
    		return structure.getBinaryStream();
    	return null; 
    }

	public void setStructureInputStream(InputStream structureStream) throws IOException {
    	this.structure = Hibernate.createBlob( structureStream );
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof DrugOrigin) )
		    return false;
	    final DrugOrigin other = (DrugOrigin) obj;
	    if (Double.doubleToLongBits(molecularWeight) != Double.doubleToLongBits(other.getMolecularWeight()))
		    return false;
	    if (structure == null) {
		    try {
	            if (other.getStructureInputStream() != null)
	                return false;
            }
            catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return false;
            }
	    }
	    return uniqueName.equals(other.getUniqueName());
//	    FIXME: This is what would be nice if it happend but Blob.equals doesn't seem to work as 
//	           expected (haven't been able to get it to return true)
//        else
//	        if (!getStructure().equals(other.getStructure()))
//	        	return false;
//	    return true;
    }
	
	public void delete() {
		
		if( drugSamples.size() != 0 ) {
			throw new IllegalStateException("Delete operation not allowed, drugorigin " + this + " has drugsamples");
		}
		super.delete();
	}

	/**
	 * getter needed by Spring
	 * 
	 * @return the structure Blob
	 */
	public Blob getStructure() {
    	return structure;
    }

	/**
	 * setter needed by Spring
	 * 
	 * @param structure the structure Blob
	 */
	public void setStructure(Blob structure) {
    	this.structure = structure;
    }
	
	public String toString() {
		return "Compund " + this.name + "id: " + this.id;
	}

	public DrugOrigin deepCopy() {

		DrugOrigin result = new DrugOrigin();
		result.setCreator(creator);
		result.setName(name);
		result.setDeleted(deleted);
		result.setMolecularWeight(molecularWeight);
		//TODO: This might not work but I hope. (preferably find a way to test whether it works)
		result.setStructure(structure);

		result.setHashCode(hashCode);
		result.setId(id);
		
		HashSet<DrugSample> drugSamples = new HashSet<DrugSample>();
		for(DrugSample drugSample : this.getDrugSamples()){
			drugSamples.add(drugSample.deepCopy());
		}
		result.setDrugSamples(drugSamples);
		
	    return result;
    }
	
	public DrugOrigin makeNewCopy() {

		DrugOrigin result = new DrugOrigin();
		result.setCreator(creator);
		result.setName(name);
		result.setDeleted(deleted);
		result.setMolecularWeight(molecularWeight);
		//TODO: This might not work but I hope. (preferably find a way to test whether it works)
		result.setStructure(structure);

		HashSet<DrugSample> drugSamples = new HashSet<DrugSample>();
		for(DrugSample drugSample : this.getDrugSamples()){
			drugSamples.add(drugSample.makeNewCopy());
		}
		result.setDrugSamples(drugSamples);
		
	    return result;
    }

	@Override
	public void accept(LisObjectVisitor extractFolderObjects) {
		super.accept(extractFolderObjects);
		extractFolderObjects.visit(this);
	}
	
	@Override
	public String getName() {
	    return uniqueName;
	}
	
	@Override
	public void setName(String name) {
	    super.setName(name);
	    this.uniqueName = name;
	}

	String getUniqueName() {
    	return uniqueName;
    }

	void setUniqueName(String uniqueName) {
    	this.uniqueName = uniqueName;
    }
}
