package net.bioclipse.brunn.ui.explorer;

import net.bioclipse.brunn.pojos.ILISObject;
import net.bioclipse.brunn.ui.explorer.model.ITreeObject;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Font;

public class DecoratingExplorerLabelProvider extends DecoratingLabelProvider{

	private FontRegistry fr = new FontRegistry();
	
	public DecoratingExplorerLabelProvider(ILabelProvider provider, ILabelDecorator decorator) {
		super(provider, decorator);
	}

	@Override
	public Font getFont(Object element) {
		if(element instanceof ITreeObject) {
			try {
				ILISObject pojo = ( (ITreeObject)element ).getPOJO();
				if( pojo != null && pojo.isDeleted() ) {
					return fr.getItalic("");
				}
			}
			catch(UnsupportedOperationException e) {
				
			}
		}
		return super.getFont(element);
	}
}
