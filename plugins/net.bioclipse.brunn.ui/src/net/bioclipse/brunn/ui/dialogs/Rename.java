package net.bioclipse.brunn.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Rename extends Dialog {

	private Text text;
	private String name;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public Rename(Shell parentShell, String name) {
		super(parentShell);
		this.name = name;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new FormLayout());

		final Label newNameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.top = new FormAttachment(0, 14);
		formData.bottom = new FormAttachment(0, 30);
		formData.left = new FormAttachment(0, 5);
		newNameLabel.setLayoutData(formData);
		newNameLabel.setText("New name:");

		text = new Text(container, SWT.BORDER);
		final FormData formData_1 = new FormData();
		formData_1.right = new FormAttachment(100, -5);
		formData_1.top = new FormAttachment(0, 9);
		formData_1.bottom = new FormAttachment(0, 35);
		formData_1.left = new FormAttachment(newNameLabel, 5, SWT.RIGHT);
		text.setLayoutData(formData_1);
		text.setText(name);
		//
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(268, 135);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Rename");
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			name = text.getText();
		}
		super.buttonPressed(buttonId);
	}

	public String getName() {
		return name;
	}

}
