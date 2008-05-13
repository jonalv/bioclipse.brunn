package net.bioclipse.brunn.ui.explorer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.model.folders.AbstractFolder;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.AbstractNonFolder;
import net.bioclipse.brunn.ui.explorer.model.folders.DataSets;
import net.bioclipse.brunn.ui.explorer.model.folders.Folder;
import net.bioclipse.brunn.ui.explorer.model.ITreeModelListener;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;
import net.bioclipse.brunn.ui.explorer.model.Model;
import net.bioclipse.brunn.ui.explorer.model.folders.Resources;
import net.bioclipse.brunn.ui.explorer.model.TreeEvent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class ExplorerContentProvider implements ITreeContentProvider, ITreeModelListener, ITreePathLabelProvider {
	
	private TreeViewer viewer;
	
	public ExplorerContentProvider() {
		
		super();
	}

	public Object[] getChildren(Object parentElement) {

		if(parentElement instanceof ITreeObject) {
			return ( (ITreeObject)parentElement ).getChildren().toArray();
		}
		
		return new Object[0];
	}

	public Object getParent(Object element) {

		if(element instanceof ITreeObject){
			return ( (ITreeObject)element ).getParent();
		}
		
		return null;
	}

	public boolean hasChildren(Object element) {
		if( element instanceof AbstractFolder || 
			element instanceof Resources      ||
			element instanceof DataSets ) {
			return true;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof ITreeObject) {
			return ((ITreeObject)inputElement).getChildren().toArray();
		}
		return null;
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TreeViewer)viewer;
		if(oldInput != null) {
			removeListenerFrom( (Model)oldInput );
		}
		if(newInput != null) {
			addListenerTo( (Model)newInput );
		}
	}

	private void addListenerTo(ITreeObject treeObject) {
		
		treeObject.addListener(this);
		
		/*
		 * add listeners to the children and if the children
		 * has children add to them aswell
		 */
		for( ITreeObject m : treeObject.getChildren() ){

			m.addListener(this);
//			if(m instanceof AbstractNonFolder) {
//				m.addListener(this);
//			}
//			else {
//				addListenerTo(m);
//			}
		}
	}

	private void removeListenerFrom(ITreeObject treeObject) {

		treeObject.removeListener(this);
		
		/* 
		 * remove listeners from the children and if the children
		 * has children from them them aswell
		 */
		for( ITreeObject m : treeObject.getChildren() ){
//			if(m instanceof AbstractNonFolder) {
//				m.removeListener(this);
//			}
//			else {
//				m.removeListener(this);
//				removeListenerFrom(m);
//			}
		}		
	}

	public synchronized void treeModelUpdated(TreeEvent event) {
		treeModelUpdated(event, null);
	}

	public synchronized void treeModelUpdated(TreeEvent event, IProgressMonitor monitor) {
		
		ITreeObject t = (ITreeObject)event.receiver();
		
		if(monitor != null) {
			monitor.beginTask("refreshing objects from database", 3);
		}
		try {
			if( (t instanceof AbstractNonFolder) ){
				// refresh a folder
				t = t.getFolder();
			}
			else if( (t instanceof Folder) ) {
				// unsure about why this is needed but it seem to work
				t = t.getParent();
			}
			if( monitor != null) {
				monitor.worked(1);
			}
			
			final ITreeObject refreshFrom = t;
			refreshFrom.refresh();
			if( monitor != null) {
				monitor.worked(1);
			}
			addListenerTo(refreshFrom);
			if( monitor != null) {
				monitor.worked(1);
			}
			Display.getDefault().asyncExec(new Runnable() {
				 public void run() {
//					 viewer.refresh(refreshFrom.getParent(), false);
					 Object[] expanded = viewer.getExpandedElements();
					 viewer.refresh(refreshFrom, false);
					 Object[] newlyExpanded = viewer.getExpandedElements();
					 List<Object> foundExpanded = Arrays.asList(attemptToFindExpanded(expanded, newlyExpanded));
					 foundExpanded = new ArrayList<Object>(foundExpanded);
					 Collections.addAll(foundExpanded, newlyExpanded);
					 viewer.setExpandedElements(foundExpanded.toArray());
					 viewer.refresh();
				 }

				private Object[] attemptToFindExpanded(Object[] expanded, Object[] newlyExpanded) {
					List<Object> toBeExpanded = new ArrayList<Object>();
					for( Object newlyExpandedObject : newlyExpanded ) {
						if(newlyExpandedObject instanceof AbstractFolder) {
							for( Object expandedObject : expanded ) {
								for(ITreeObject newO : ((AbstractFolder)newlyExpandedObject).getChildren() ) {
									if( (  (ITreeObject)expandedObject).getPOJO()!= null && 
											newO.getPOJO().getId() == ((ITreeObject)expandedObject).getPOJO().getId() ) {
										toBeExpanded.add(newO);
									}
								}
							}
						}
					}
					return toBeExpanded.toArray(); 
				}
			});
		}
		finally {
			if( monitor != null) {
				monitor.done();
			}
		}
	}
	
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		if( elementPath.getLastSegment() instanceof ITreeObject) {
			
			FontData[] fontData = Display.getDefault().getSystemFont().getFontData();
			
		}
		
		
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	
}
