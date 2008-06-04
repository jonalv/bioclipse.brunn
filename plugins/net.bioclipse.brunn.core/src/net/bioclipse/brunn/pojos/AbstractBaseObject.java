package net.bioclipse.brunn.pojos;


/**
 * Persistent data storing class.
 * 
 * @author jonathan
 *
 */
public abstract class AbstractBaseObject implements ILISObject {

	protected long id;
	protected User creator;
	protected String name;
	protected boolean deleted;
	protected int hashCode;
	
//	private static int objectsCreated = 0;
	
	public AbstractBaseObject(){
		this.hashCode = super.hashCode();
//		System.out.println(++objectsCreated);
	}
	
	public AbstractBaseObject(User creator, String name) {
		super();
		this.creator = creator;
		this.name = name;
		this.deleted = false;
		this.hashCode = super.hashCode();
//		System.out.println(++objectsCreated);
	}
	
	public void delete(){
		this.deleted = true;
	}
	
	public void unDelete(){
		this.deleted = false;
	}
	
	/**
	 * @param deleted  the deleted to set
	 */
	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}
	
	public boolean isDeleted(){
		return this.deleted;
	}
	
	/**
	 * @return  the creator
	 */
	public User getCreator() {
		return creator;
	}

	/**
	 * @param creator  the creator to set
	 */
	public void setCreator(User creator) {
		this.creator = creator;
	}

	/**
	 * @return  the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @param id  the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return  the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name  the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Needed by Hibernate.
	 * 
	 * @return
	 */
	int getHashCode() {
    	return hashCode;
    }

	/**
	 * Needed by Hibernate.
	 * 
	 * @param hashCode
	 */
	void setHashCode(int hashCode) {
    	this.hashCode = hashCode;
    }

	/* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode() {
	    return this.hashCode;
    }

	/* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if ( !(obj instanceof AbstractBaseObject) )
		    return false;
	    final AbstractBaseObject other = (AbstractBaseObject) obj;
	    if (creator == null) {
		    if (other.getCreator() != null)
			    return false;
	    }
	    else if (!creator.equals(other.getCreator()))
		    return false;
	    if (deleted != other.isDeleted())
		    return false;
	    if (hashCode != other.getHashCode())
		    return false;
	    if (name == null) {
		    if (other.getName() != null)
			    return false;
	    }
	    else if (!name.equals(other.getName()))
		    return false;
	    return true;
    }
    
    public void accept(LisObjectVisitor extractFolderObjects) {
//    	System.out.println(getClass().getSimpleName() + ": " + name + " visited");
    }
}
