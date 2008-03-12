package net.bioclipse.brunn.pojos;

/**
 * @author  jonathan
 */
public interface ILISObject {

	public void delete();
	public void unDelete();
	public void setDeleted(boolean deleted);
	public boolean isDeleted();
	
	public User getCreator();
	public void setCreator(User creator);

	public long getId();
	public void setId(long id);
	
	public String getName();

	/**
	 * @param name  the name to set
	 */
	public void setName(String name);
	public void accept(LisObjectVisitor extractFolderObjects);
}
