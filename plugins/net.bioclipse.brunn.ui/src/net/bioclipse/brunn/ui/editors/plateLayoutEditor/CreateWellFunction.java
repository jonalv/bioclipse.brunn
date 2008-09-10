package net.bioclipse.brunn.ui.editors.plateLayoutEditor;

import net.bioclipse.expression.Calculator;
import net.bioclipse.expression.CalculatorException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreateWellFunction extends TitleAreaDialog {

	private Label expressionLabel;
	private Label nameLabel;
	private Text textExpression;
	private Text textName;
	
	private String name;
	private String expression;
	private Calculator calculator;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param calculator 
	 */
	public CreateWellFunction(Shell parentShell, Calculator calculator) {
		super(parentShell);
		this.calculator = calculator;
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

		nameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 42);
		formData.top = new FormAttachment(0, 22);
		formData.right = new FormAttachment(0, 110);
		formData.left = new FormAttachment(0, 70);
		nameLabel.setLayoutData(formData);
		nameLabel.setText("Name");

		expressionLabel = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 80);
		formData_1.top = new FormAttachment(0, 60);
		formData_1.right = new FormAttachment(0, 110);
		formData_1.left = new FormAttachment(0, 40);
		expressionLabel.setLayoutData(formData_1);
		expressionLabel.setText("Expression");

		textExpression = new Text(container, SWT.BORDER);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(0, 80);
		formData_2.top = new FormAttachment(0, 55);
		formData_2.left = new FormAttachment(0, 116);
		textExpression.setLayoutData(formData_2);

		textName = new Text(container, SWT.BORDER);
		formData_2.right = new FormAttachment(textName, 0, SWT.RIGHT);
		final FormData formData_3 = new FormData();
		formData_3.right = new FormAttachment(100, -5);
		formData_3.bottom = new FormAttachment(0, 42);
		formData_3.top = new FormAttachment(0, 17);
		formData_3.left = new FormAttachment(0, 116);
		textName.setLayoutData(formData_3);
		container.setTabList(new Control[] {textName, textExpression, nameLabel, expressionLabel});
		setTitle("Create Well Function");
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
		return new Point(497, 265);
	}
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	public String getExpression() {
		return expression;
	}

	public String getName() {
		return name;
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			name       = textName.getText();
			expression = textExpression.getText();
			try {
				calculator.addVariable("well", Double.NaN);
				calculator.safeValueOf(textExpression.getText());
				calculator.removeVariable("well");
			}
			catch (CalculatorException e) {
				MessageDialog.openInformation( 
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Could not parse expression", 
						e.getMessage() );
				return;
			}
	        try {
	            calculator.checkIdentifierName( name );
            }
            catch (Exception e) {
                MessageDialog.openInformation( 
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
                        "Can not use that well function name", 
                        e.getMessage() );
                return;
            }
		}
		super.buttonPressed(buttonId);
	}
}
