package net.bioclipse.brunn.ui.explorer.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;

import net.bioclipse.brunn.pojos.ILISObject;

/**
 * @author jonathan
 *
 */
public interface ITreeObject {
	
	/**
	 * @return the object above in the tree
	 */
	public ITreeObject getParent();
	
	
	/**
	 * @return the childrens of this node in the tree
	 */
	public List<ITreeObject> getChildren();
	
	/**
	 * @return returns the namde of this node
	 */
	public String getName();
	
	/**
	 *  refetches the data for this node and all it's children from the database
	 */
	public void refresh();
	
	/**
	 * @return the persistant object corresponding to this node or null 
	 * if there is no such object
	 */
	public ILISObject getPOJO();
	
	/**
	 * Returns the first folder <code>ITreeObject</code> above or including 
	 * this one that is an instance of <code>AbstractFolder</code>. 
	 *  
	 * @return 
	 */
	public ITreeObject getFolder();
	
	/**
	 * Returns the first folder <code>ITreeObject</code> above or including 
	 * this one that is an instance of <code>AbstractUniqueFolder</code>. If no
	 * <code>AbstractUniqueFolder</code> exists above it returns self.
	 * 
	 * @return
	 */
	public ITreeObject getUniqueFolder();
		
	/**
	 * Removes a listener from the <code>ITreeObject</code>
	 * 
	 * @param listener to remove
	 */
	public void removeListener(ITreeModelListener listener); 

	/**
	 * Adds a listener to the <code>ITreeObject</code>
	 * 
	 * @param listener
	 */
	public void addListener(ITreeModelListener listener);
	
	
	/**
	 * Fires the update event on the <code>ITreeObject</code>.
	 * Signals that the model has been changed in the database and needs to be 
	 * reloaded and updated by all listeners. 
	 */
	public void fireUpdate();
	
	/**
	 * Fires the update event on the <code>ITreeObject</code> including a 
	 * status bar to inform the user of the progress.
	 * Signals that the model has been changed in the database and needs to be 
	 * reloaded and updated by all listeners. 
	 * 
	 * @param monitor a monitor used to update the progressbar
	 */
	public void fireUpdate(IProgressMonitor monitor);
	
	/**
	 * @return the Image that is to be shown with the <code>ITreeObject</code> 
	 * in the TreeView. If none is defined it will return <code>null</code>.
	 */
	public Image getIconImage();


	/**
	 * Changes the name of the <code>ITreeObject</code> and persists the name
	 * change to the database.
	 * 
	 * @param name to be changed into
	 */
	public void changeName(String name);
	
	
}
