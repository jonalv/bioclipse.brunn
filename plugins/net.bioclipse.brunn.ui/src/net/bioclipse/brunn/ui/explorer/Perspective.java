package net.bioclipse.brunn.ui.explorer;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public static final String PERSPECTIVE_ID = "net.bioclipse.brunn.ui.explorer.Perspective";

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.setFixed(true);
		
		layout.addStandaloneView(View.ID,  false, IPageLayout.LEFT, 1.0f, editorArea);
	}

}
