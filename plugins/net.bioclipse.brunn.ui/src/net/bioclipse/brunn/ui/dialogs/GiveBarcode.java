package net.bioclipse.brunn.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GiveBarcode extends TitleAreaDialog {

	private String barcode;
	
	private Text text;
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public GiveBarcode(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayout(new FormLayout());
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label barcodeLabel = new Label(container, SWT.NONE);
		final FormData fd_barcodeLabel = new FormData();
		fd_barcodeLabel.top = new FormAttachment(0, 60);
		fd_barcodeLabel.left = new FormAttachment(0, 45);
		barcodeLabel.setLayoutData(fd_barcodeLabel);
		barcodeLabel.setText("Barcode:");

		text = new Text(container, SWT.BORDER);
		final FormData fd_text = new FormData();
		fd_text.right = new FormAttachment(100, -100);
		fd_text.bottom = new FormAttachment(barcodeLabel, 0, SWT.BOTTOM);
		fd_text.left = new FormAttachment(barcodeLabel, 5, SWT.RIGHT);
		text.setLayoutData(fd_text);
		setTitle("Enter Barcode");
		setMessage("Enter the barcode for the plate");
		//
		return area;
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
		return new Point(500, 375);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Give Barcode");
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.barcode = text.getText();
		}
		super.buttonPressed(buttonId);
	}

	public String getBarcode() {
		return barcode;
	}

}
