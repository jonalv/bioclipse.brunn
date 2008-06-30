package net.bioclipse.brunn.ui.explorer.model;


import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

public abstract class Model implements ITreeObject, IAdaptable {
	
	protected ITreeModelListener listener = NullTreeModelListener.INSTANCE;
	protected IPropertySource2 propertySource;
	
	public void fireUpdate() {
		listener.treeModelUpdated( new TreeEvent(this) );
	}
	
	public void fireUpdate(IProgressMonitor monitor) {
		listener.treeModelUpdated(new TreeEvent(this), monitor);
	}
	
	public void removeListener(ITreeModelListener listener) {
		
		if( this.listener.equals(listener) ) {
			this.listener = NullTreeModelListener.INSTANCE;
		}
	}
	
	public void addListener(ITreeModelListener listener) {
		this.listener = listener;
	}
	
	/**
	* For properties
	* May be overridden by subclasses
	*/
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class){
			if (propertySource == null){
				propertySource = new ModelPropertySource(this);
			}
			return propertySource;
		}
		return null;
	}
}
