package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.bioclipse.encryption.EncryptedPassword;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class User extends AbstractAuditableObject {

	private Set<AuditLog> doneAuditings;
	private EncryptedPassword encryptedPassword;
	private boolean admin;
	
	public boolean isAdmin() {
    	return admin;
    }

	public void setAdmin(boolean admin) {
    	this.admin = admin;
    }

	public User() {
		this.encryptedPassword = EncryptedPassword.fromPlaintextPassword("");
	}
	
	public User( User creator, String name){
		super(creator, name);
		this.doneAuditings = new HashSet<AuditLog>();
		this.encryptedPassword = EncryptedPassword.fromPlaintextPassword("");
	}
	
	public User(String name) {
		super(null, name);
		this.setCreator(this);
		this.setDeleted(false);
		this.doneAuditings = new HashSet<AuditLog>();
		this.encryptedPassword = EncryptedPassword.fromPlaintextPassword("");
	}
	
	/**
	 * @param name
	 * @return
	 * @deprecated
	 */
	public static User createUser(String name) {
		return new User(name);
	}
	
	public Set<AuditLog> getDoneAuditings() {
		return doneAuditings;
	}

	public void setDoneAuditings(Set<AuditLog> auditLogs) {
		this.doneAuditings = auditLogs;
	}
	
	/* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	    if(!(obj instanceof User))
	    	return false;
	    User user = (User)obj;
	    return user.getName().equals(this.getName()) && 
	           user.isAdmin() == this.admin;
    }

	public String toString() {
		return "User id:" + this.getId()
			   +" name: "   + this.getName()
		       +" deleted: " + this.isDeleted() + "\n";
	}

	public boolean passwordMatch( String password ) {
    	return encryptedPassword.matches(password);
    }

	public void setPassword(String password) {
		this.encryptedPassword 
			= EncryptedPassword.fromPlaintextPassword(password);
    }

	public String getEncryptedPassword() {
    	return encryptedPassword.toString();
    }

	public void setEncryptedPassword(String encryptedPassWord) {
    	this.encryptedPassword 
    		= EncryptedPassword.fromAlreadyEncryptedPassword(
    				encryptedPassWord );
    }
}