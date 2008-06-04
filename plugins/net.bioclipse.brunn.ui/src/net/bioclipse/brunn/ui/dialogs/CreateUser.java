package net.bioclipse.brunn.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreateUser extends TitleAreaDialog {

	private Text repeatPasswordText;
	private Text passwordText;
	private Text usernameText;
	
	private String passWord;
	private String username;
	private boolean admin;
	private Button administratorRightsButton;
	
	public boolean isAdmin() {
		return admin;
	}

	public String getPassWord() {
		return passWord;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreateUser(Shell parentShell) {
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
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label usernameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 46);
		formData.top = new FormAttachment(0, 29);
		formData.left = new FormAttachment(0, 31);
		usernameLabel.setLayoutData(formData);
		usernameLabel.setText("Username:");

		final Label passwordLabel = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 92);
		formData_1.top = new FormAttachment(0, 75);
		formData_1.left = new FormAttachment(0, 31);
		passwordLabel.setLayoutData(formData_1);
		passwordLabel.setText("Password:");

		usernameText = new Text(container, SWT.BORDER);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(usernameLabel, 0, SWT.BOTTOM);
		usernameText.setLayoutData(formData_2);

		passwordText = new Text(container, SWT.BORDER|SWT.PASSWORD);
		formData_2.right = new FormAttachment(passwordText, 0, SWT.RIGHT);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(passwordLabel, 0, SWT.BOTTOM);
		passwordText.setLayoutData(formData_3);

		administratorRightsButton = new Button(container, SWT.CHECK);
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(0, 167);
		formData_4.bottom = new FormAttachment(0, 189);
		formData_4.left = new FormAttachment(0, 111);
		formData_4.right = new FormAttachment(0, 265);
		administratorRightsButton.setLayoutData(formData_4);
		administratorRightsButton.setText("Administrator Rights");

		Label repeatPasswordLabel;
		repeatPasswordLabel = new Label(container, SWT.NONE);
		final FormData formData_5 = new FormData();
		formData_5.bottom = new FormAttachment(0, 138);
		formData_5.top = new FormAttachment(0, 121);
		formData_5.left = new FormAttachment(passwordLabel, 0, SWT.LEFT);
		repeatPasswordLabel.setLayoutData(formData_5);
		repeatPasswordLabel.setText("Repeat Password:");

		repeatPasswordText = new Text(container, SWT.BORDER | SWT.PASSWORD);
		formData_3.right = new FormAttachment(repeatPasswordText, 0, SWT.RIGHT);
		formData_2.left = new FormAttachment(repeatPasswordText, 0, SWT.LEFT);
		formData_3.left = new FormAttachment(repeatPasswordText, 0, SWT.LEFT);
		final FormData formData_6 = new FormData();
		formData_6.right = new FormAttachment(100, -80);
		formData_6.bottom = new FormAttachment(repeatPasswordLabel, 0, SWT.BOTTOM);
		formData_6.left = new FormAttachment(repeatPasswordLabel, 5, SWT.RIGHT);
		repeatPasswordText.setLayoutData(formData_6);
		setTitle("Create Brunn User");
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
		newShell.setText("Create Brunn User");
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if( !passwordText.getText().equals(repeatPasswordText.getText()) ) {
				MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "passwords differ", "Repeat the same password in the repeat password field");
				return;
			}
			passWord = passwordText.getText();
			username = usernameText.getText();
			admin = administratorRightsButton.getSelection();
		}
		super.buttonPressed(buttonId);
	}
}
