package net.bioclipse.brunn.ui.images;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.bioclipse.brunn.ui.Activator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public abstract class IconFactory {

	public static Image getImage(String name) {
		
		URL url = null;
		try {
			url = new URL(Activator.getPluginURL() + "icons" + File.separator + name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromURL(url); 
		return imageDescriptor.createImage();
	}
	
	public static ImageDescriptor getImageDescriptor(String name) {
		URL url = null;
		try {
			url = new URL(Activator.getPluginURL() + "icons" + File.separator + name);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ImageDescriptor.createFromURL(url);
	}
}
