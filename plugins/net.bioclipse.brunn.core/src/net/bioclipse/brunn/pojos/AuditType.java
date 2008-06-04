package net.bioclipse.brunn.pojos;

/**
 * @author jonathan
 *
 */
public enum AuditType {
	
	CREATE_EVENT("Create Event"),
	DELETE_EVENT("Delete Event"),
	UPDATE_EVENT("Update Event");
	
	private String type;
	
	private AuditType(String type){
		this.type=type;
	}
	
	public String toString(){
		return type;
	}	
}
