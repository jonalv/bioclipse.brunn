package net.bioclipse.brunn.pojos;

import java.util.HashSet;
import java.util.Set;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public class User extends AbstractAuditableObject {

	private Set<AuditLog> doneAuditings;
	private String encryptedPassword;
	private BasicPasswordEncryptor encryptor;
	private boolean admin;
	
	public boolean isAdmin() {
    	return admin;
    }

	public void setAdmin(boolean admin) {
    	this.admin = admin;
    }

	public User() {
		encryptor = new BasicPasswordEncryptor();
		this.encryptedPassword = encryptor.encryptPassword("");
	}
	
	public User( User creator, String name){
		super(creator, name);
		this.doneAuditings = new HashSet<AuditLog>();
		encryptor = new BasicPasswordEncryptor();
		this.encryptedPassword = encryptor.encryptPassword("");
	}
	
	public User(String name) {
		super(null, name);
		this.setCreator(this);
		this.setDeleted(false);
		this.doneAuditings = new HashSet<AuditLog>();
		encryptor = new BasicPasswordEncryptor();
		this.encryptedPassword = encryptor.encryptPassword("");
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
    	return encryptor.checkPassword( password, this.encryptedPassword );
    }

	public void setPassword(String password) {
		this.encryptedPassword = encryptor.encryptPassword(password);
    }

	public String getEncryptedPassword() {
    	return encryptedPassword;
    }

	public void setEncryptedPassword(String encryptedPassWord) {
    	this.encryptedPassword = encryptedPassWord;
    }
}