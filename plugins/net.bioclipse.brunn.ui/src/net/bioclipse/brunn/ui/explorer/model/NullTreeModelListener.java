package net.bioclipse.brunn.ui.explorer.model;

import org.eclipse.core.runtime.IProgressMonitor;

public class NullTreeModelListener implements ITreeModelListener {

	public static final NullTreeModelListener INSTANCE = new NullTreeModelListener();
	
	private NullTreeModelListener() {
		
	}
	
	public void treeModelUpdated(TreeEvent event) {
				
	}

	public void treeModelUpdated(TreeEvent event, IProgressMonitor monitor) {
		
	}
}
