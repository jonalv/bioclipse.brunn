/**
 * 
 */
package net.bioclipse.brunn.pojos;

import java.sql.Timestamp;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class AuditLog implements ILISObject {

	private long id;
	private User user;
	private Timestamp timeStamp;
	private boolean deleted;
	private AuditType auditType;
	private AbstractAuditableObject auditedObject;
	private String postAuditRepresentation;
	private int hashCode;
	
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted  the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public AuditLog() {
		super();
	}

	public AuditLog( User user, 
			          AuditType auditType,
			          AbstractAuditableObject auditedObject,
			          String postAuditRepresentation, 
			          Timestamp timestamp) {
		
		this.user                    = user;
		this.auditType               = auditType;
		this.auditedObject           = auditedObject;
		this.postAuditRepresentation = postAuditRepresentation;
		this.timeStamp               = timestamp;
		this.deleted                 = false;
		this.hashCode                = super.hashCode();
		user.getDoneAuditings().add(this);
		auditedObject.getAuditLogs().add(this);
    }

	public int hashCode() {
		return hashCode;
	}

	
	/**
	 * Needed by Hibernate
	 * 
	 * @return
	 */
	int getHashCode() {
    	return hashCode;
    }

	/**
	 * Needed by Hibernate
	 * 
	 * @param hashCode
	 */
	void setHashCode(int hashCode) {
    	this.hashCode = hashCode;
    }

	public boolean equals(Object o){
		if (!(o instanceof AuditLog))
			return false;
		AuditLog auditLog = (AuditLog)o;
		return this.user.equals(auditLog.user) && 
				((int)Math.floor(this.timeStamp.getTime()/1000.0)) == 
					((int)Math.floor(auditLog.getTimeStamp().getTime()/1000.0)) &&
				this.postAuditRepresentation.equals(auditLog.getPostAuditRepresentation()) &&
				this.auditType == auditLog.getAuditType() &&
				this.deleted   == auditLog.isDeleted()  &&
				this.hashCode  == auditLog.hashCode();
	}
	
	public String toString(){
		return  "Auditlog id: " + this.id
		       + " Timestamp: "   + this.timeStamp
		       + " User: "        + this.getUser() + "\n";
	}

	/**
	 * @return  the auditType
	 */
    public AuditType getAuditType() {
    	return auditType;
    }

	/**
	 * @param auditType  the auditType to set
	 */
    public void setAuditType(AuditType auditType) {
    	this.auditType = auditType;
    }

	/**
	 * @return  the id
	 */
    public long getId() {
    	return id;
    }

	/**
	 * @param id  the id to set
	 */
    public void setId(long id) {
    	this.id = id;
    }

	/**
	 * @return  the timeStamp
	 */
    public Timestamp getTimeStamp() {
    	return timeStamp;
    }

	/**
	 * @param timeStamp  the timeStamp to set
	 */
    public void setTimeStamp(Timestamp timeStamp) {
    	this.timeStamp = timeStamp;
    }

	/**
	 * @return  the user
	 */
    public User getUser() {
    	return user;
    }

	/**
	 * @param user  the user to set
	 */
    public void setUser(User user) {
    	this.user = user;
    }

	public void delete() {
	    this.deleted = true;
    }

	public User getCreator() {
	   throw new UnsupportedOperationException("Creator can't be retrieved. AuditLog has no creator");
    }

	public String getName() {
		throw new UnsupportedOperationException("Name can't be retrieved. AuditLog has no name");
    }

	public void setCreator(User creator) {
		throw new UnsupportedOperationException("Creator can't be set. AuditLog has no creator");
    }

	public void setName(String name) {
		throw new UnsupportedOperationException("Name can't be set. AuditLog has no name");
    }

	public void unDelete() {
		this.deleted=false;
    }

	/**
	 * @return  the auditedObject
	 */
	public AbstractAuditableObject getAuditedObject() {
    	return auditedObject;
    }

	/**
	 * @param auditedObject  the auditedObject to set
	 */
	public void setAuditedObject(AbstractAuditableObject auditedObject) {
    	this.auditedObject = auditedObject;
    }

	/**
	 * @return  the postAuditRepresentation
	 */
	public String getPostAuditRepresentation() {
    	return postAuditRepresentation;
    }

	/**
	 * @param postAuditRepresentation  the postAuditRepresentation to set
	 */
	public void setPostAuditRepresentation(String postAuditRepresentation) {
    	this.postAuditRepresentation = postAuditRepresentation;
    }

	public void accept(LisObjectVisitor extractFolderObjects) {
	    // TODO Auto-generated method stub
	    
    }
}
