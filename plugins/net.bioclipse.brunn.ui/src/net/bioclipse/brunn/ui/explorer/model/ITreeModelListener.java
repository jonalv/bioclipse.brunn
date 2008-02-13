package net.bioclipse.brunn.ui.explorer.model;

import org.eclipse.core.runtime.IProgressMonitor;


public interface ITreeModelListener {

	public void treeModelUpdated( TreeEvent event );
	public void treeModelUpdated( TreeEvent event, IProgressMonitor monitor );
}
