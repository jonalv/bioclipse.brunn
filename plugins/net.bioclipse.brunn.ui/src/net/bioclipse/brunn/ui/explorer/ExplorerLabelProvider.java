package net.bioclipse.brunn.ui.explorer;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.bioclipse.brunn.ui.Activator;
import net.bioclipse.brunn.ui.explorer.model.folders.Folder;
import net.bioclipse.brunn.ui.explorer.model.folders.CellTypes;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

public class ExplorerLabelProvider implements ILabelProvider {

	public Image getImage(Object element) {
		
		if(element instanceof ITreeObject) {
			return ((ITreeObject)element).getIconImage();
		}
		return null;
	}

	public String getText(Object element) {

		if(element instanceof CellTypes) {
			return "Cell Lines";
		}
		if(element instanceof ITreeObject){
			return ( (ITreeObject) element ).getName();
		}
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	public void dispose() {
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
