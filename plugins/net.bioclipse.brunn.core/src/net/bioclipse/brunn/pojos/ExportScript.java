package net.bioclipse.brunn.pojos;



public class ExportScript extends AbstractAuditableObject {
	
	
	private String description;
	private String type;
	private String body;

	public ExportScript() {
	    super();
    }

	public ExportScript( User creator, 
	                     String name, 
	                     String description, 
	                     String type, 
	                     String body ) {
	    super(creator, name);
	    this.description = description;
	    this.type = type;
	    this.body = body;
    }

	public String getDescription() {
    	return description;
    }
	public void setDescription(String description) {
    	this.description = description;
    }
	public String getType() {
    	return type;
    }
	public void setType(String type) {
    	this.type = type;
    }
	public String getBody() {
    	return body;
    }
	public void setBody(String body) {
    	this.body = body;
    }
}
