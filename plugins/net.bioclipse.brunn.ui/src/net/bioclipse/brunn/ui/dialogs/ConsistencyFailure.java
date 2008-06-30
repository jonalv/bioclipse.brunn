package net.bioclipse.brunn.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class ConsistencyFailure extends Dialog {

	private StyledText someoneHasEditedStyledText;
	
	public static final int OVERRIDE = 900;
	public static final int RELOAD   = 901;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public ConsistencyFailure(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);

        String message = "Someone has edited the object you were editing and saved \n"
                       + "it before you. You can load the changes and do your work \n"
                       + "again starting from the changed object or override and \n"
                       + "save your changes.\n"
                       + "(NOTE: this will undo the other changes)";
        
		someoneHasEditedStyledText = new StyledText(container, SWT.BORDER|SWT.MULTI);
		someoneHasEditedStyledText.setWordWrap(true);
		someoneHasEditedStyledText.setText(message);
		someoneHasEditedStyledText.setEditable(false);
		someoneHasEditedStyledText.setBounds(34, 42, 423, 165);
		//
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, OVERRIDE, "Override",
				false);
		createButton(parent, RELOAD,
				"Load changes", true);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
