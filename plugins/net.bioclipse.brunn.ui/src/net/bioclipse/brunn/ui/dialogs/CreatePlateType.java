package net.bioclipse.brunn.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreatePlateType extends TitleAreaDialog {

	private Text txt_name;
	private Text txt_numberOfRows;
	private Text txt_numberOfColumns;
	
	private String name;
	private int numberOfRows;
	private int numberOfColumns;

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreatePlateType(Shell parentShell) {
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
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label label = new Label(container, SWT.NONE);
		label.setText("Number of rows:");
		label.setBounds(90, 60, 105, 20);

		final Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("Number of columns:");
		label_1.setBounds(65, 100, 130, 20);

		final Label label_2 = new Label(container, SWT.NONE);
		label_2.setText("Name:");
		label_2.setBounds(150, 140, 45, 20);

		txt_numberOfRows = new Text(container, SWT.BORDER);
		txt_numberOfRows.setBounds(201, 55, 80, 25);

        txt_numberOfColumns = new Text(container, SWT.BORDER);
        txt_numberOfColumns.setBounds(201, 95, 80, 25);

		txt_name = new Text(container, SWT.BORDER);
		txt_name.setBounds(201, 135, 80, 25);
		setTitle("Create plate type");
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
		return new Point(401, 361);
	}
	public String getName() {
		return name;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			 
			name = txt_name.getText();
			numberOfColumns = Integer.parseInt(txt_numberOfColumns.getText());
			numberOfRows = Integer.parseInt(txt_numberOfRows.getText());
			
		}
		super.buttonPressed(buttonId);
	}
}
