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

public class CreatePlateFunction extends TitleAreaDialog {

	private Text textGoodTo;
	private Text textGoodFrom;
	private Text textExpression;
	private Text textName;
	
	private double goodTo;
	private double goodFrom;
	private String expression;
	private String name;
	private boolean hasSpecifiedValues;
	private Calculator calculator;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 * @param calculator 
	 */
	public CreatePlateFunction(Shell parentShell, Calculator calculator) {
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

		final Label nameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 60);
		formData.top = new FormAttachment(0, 40);
		formData.right = new FormAttachment(0, 85);
		formData.left = new FormAttachment(0, 45);
		nameLabel.setLayoutData(formData);
		nameLabel.setText("Name");

		final Label expressionLabel = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(0, 97);
		formData_1.top = new FormAttachment(0, 77);
		formData_1.right = new FormAttachment(0, 85);
		formData_1.left = new FormAttachment(0, 15);
		expressionLabel.setLayoutData(formData_1);
		expressionLabel.setText("Expression");

		final Label goodFromlLabel = new Label(container, SWT.NONE);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(0, 140);
		formData_2.top = new FormAttachment(0, 120);
		formData_2.right = new FormAttachment(0, 85);
		formData_2.left = new FormAttachment(0, 15);
		goodFromlLabel.setLayoutData(formData_2);
		goodFromlLabel.setText("Good from");

		final Label goodToLabel = new Label(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(0, 185);
		formData_3.top = new FormAttachment(0, 165);
		formData_3.right = new FormAttachment(0, 85);
		formData_3.left = new FormAttachment(0, 30);
		goodToLabel.setLayoutData(formData_3);
		goodToLabel.setText("Good to");

		textName = new Text(container, SWT.BORDER);
		final FormData formData_4 = new FormData();
		formData_4.right = new FormAttachment(100, -5);
		formData_4.bottom = new FormAttachment(0, 60);
		formData_4.top = new FormAttachment(0, 35);
		formData_4.left = new FormAttachment(0, 91);
		textName.setLayoutData(formData_4);

		textExpression = new Text(container, SWT.BORDER);
		final FormData formData_5 = new FormData();
		formData_5.left = new FormAttachment(expressionLabel, 5, SWT.RIGHT);
		formData_5.right = new FormAttachment(textName, 0, SWT.RIGHT);
		formData_5.top = new FormAttachment(expressionLabel, -25, SWT.BOTTOM);
		formData_5.bottom = new FormAttachment(expressionLabel, 0, SWT.BOTTOM);
		textExpression.setLayoutData(formData_5);

		textGoodFrom = new Text(container, SWT.BORDER);
		final FormData formData_6 = new FormData();
		formData_6.right = new FormAttachment(textName, 0, SWT.RIGHT);
		formData_6.bottom = new FormAttachment(0, 140);
		formData_6.top = new FormAttachment(0, 115);
		formData_6.left = new FormAttachment(0, 91);
		textGoodFrom.setLayoutData(formData_6);

		textGoodTo = new Text(container, SWT.BORDER);
		final FormData formData_7 = new FormData();
		formData_7.right = new FormAttachment(textGoodFrom, 0, SWT.RIGHT);
		formData_7.bottom = new FormAttachment(0, 185);
		formData_7.top = new FormAttachment(0, 160);
		formData_7.left = new FormAttachment(0, 91);
		textGoodTo.setLayoutData(formData_7);
		setTitle("Create Plate Function");
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

	public String getExpression() {
		return expression;
	}

	public double getGoodFrom() {
		return goodFrom;
	}

	public double getGoodTo() {
		return goodTo;
	}

	public String getName() {
		return name;
	}
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			
			try {
				calculator.safeValueOf( textExpression.getText() );
			}
			catch (CalculatorException e) {
				MessageDialog.openInformation( 
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Could not parse expression", 
						e.getMessage() );
				return;
			}
			try {
                calculator.checkIdentifierName( textName.getText() );
            }
            catch (Exception e) {
                MessageDialog.openInformation( 
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
                        "Can not use that plate function name", 
                        e.getMessage() );
                return;
            }
			
			this.expression = textExpression.getText();
			this.name       = textName.getText();
			
			if( "".equals( textGoodFrom.getText() ) && 
				"".equals(   textGoodTo.getText() ) ) {

				hasSpecifiedValues = false;
			}
			else {
				try{
					this.goodFrom = Double.parseDouble(textGoodFrom.getText()); 
				}
				catch( NumberFormatException e) {
					MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						                           "Info",
						                           "Good from must be a double" );
					return;
				}
				try{
					this.goodTo = Double.parseDouble(textGoodTo.getText()); 
				}
				catch( NumberFormatException e) {
					MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						                           "Info",
						                           "Good to must be a double" );
					return;
				}
				hasSpecifiedValues = true;
			}
			
		}
		super.buttonPressed(buttonId);
	}

	public boolean hasSpecifiedValues() {
		return hasSpecifiedValues;
	}
}
