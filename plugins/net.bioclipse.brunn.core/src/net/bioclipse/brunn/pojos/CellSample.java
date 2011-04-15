/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.sql.Timestamp;

/**
 * @author jonathan
 *
 */
public class CellSample extends AbstractSample {

	private CellOrigin cellOrigin;
	private Timestamp defrostingDate;
	
	public CellSample() {
	    super();
    }
	
	public CellSample( User creator, 
	                   String name, 
	                   CellOrigin cellOrigin, 
	                   Timestamp defrostingDate, 
	                   SampleContainer sampleContainer ) {
		
	    super(creator, name, sampleContainer);
	    this.cellOrigin = cellOrigin;
	    this.defrostingDate = defrostingDate;
	    cellOrigin.getCellSamples().add(this);
	    sampleContainer.getSamples().add(this);
    }

	public CellOrigin getCellOrigin() {
    	return cellOrigin;
    }
	public void setCellOrigin(CellOrigin cellOrigin) {
    	this.cellOrigin = cellOrigin;
    }
	public Timestamp getDefrostingDate() {
    	return defrostingDate;
    }
	public void setDefrostingDate(Timestamp defrostingDate) {
    	this.defrostingDate = defrostingDate;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (!super.equals(obj))
		    return false;
	    if ( !(obj instanceof CellSample) )
		    return false;
	    final CellSample other = (CellSample) obj;
	    if (cellOrigin == null) {
		    if (other.getCellOrigin() != null)
			    return false;
	    }
	    else if (!cellOrigin.equals(other.getCellOrigin()))
		    return false;
	    if (defrostingDate == null) {
		    if (other.getDefrostingDate() != null)
			    return false;
	    }
	    else if (( (int)Math.floor(defrostingDate.getTime()/1000.0) ) != 
			( (int)Math.floor(other.getDefrostingDate().getTime()/1000.0) ))
		    return false;
	    return true;
    }
	
	public CellSample deepCopy(){
		
		CellSample cellSample = new CellSample();
		
		cellSample.setCreator(creator);
		cellSample.setName(name);
		cellSample.setCellOrigin(cellOrigin);
		cellSample.setDefrostingDate(defrostingDate);
		cellSample.setSampleMarker(sampleMarker);

		cellSample.setHashCode(hashCode);
		cellSample.setId(id);
		
		cellSample.cellOrigin.getCellSamples().add(cellSample);
		
		return cellSample;
	}
	
	public CellSample makeNewCopy(User creator) {
		
		CellSample cellSample = new CellSample();
		cellSample.setCreator(creator);
		cellSample.setName(name);
		cellSample.setCellOrigin(cellOrigin);
		cellSample.setDefrostingDate(defrostingDate);
		
		cellSample.cellOrigin.getCellSamples().add(cellSample);
		
		return cellSample;
	}
}
