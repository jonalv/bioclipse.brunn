package net.bioclipse.brunn.ui.dialogs.createMasterPlateFromSDF;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class WizardPage3 extends WizardPage {

	private Combo plate4;
	private Combo plate3;
	private Combo plate1;
	private Combo plate2;
	/**
	 * Create the wizard
	 */
	public WizardPage3() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new FormLayout());
		//
		setControl(container);

		final Label label = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.right = new FormAttachment(100, -5);
		formData.top = new FormAttachment(0, 10);
		formData.left = new FormAttachment(0, 10);
		label.setLayoutData(formData);
		label.setText("There are N plates.");

		final Label label_1 = new Label(container, SWT.NONE);
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 33);
		formData_1.bottom = new FormAttachment(0, 50);
		formData_1.right = new FormAttachment(label, 412, SWT.LEFT);
		formData_1.left = new FormAttachment(label, 0, SWT.LEFT);
		label_1.setLayoutData(formData_1);
		label_1.setText("Which four do you want to import as one 384 wells master plate?");

		Label plate4Label;

		final Group group = new Group(container, SWT.NONE);
		final FormData formData_10 = new FormData();
		formData_10.left = new FormAttachment(label_1, -412, SWT.RIGHT);
		formData_10.right = new FormAttachment(label_1, 0, SWT.RIGHT);
		formData_10.bottom = new FormAttachment(0, 201);
		formData_10.top = new FormAttachment(0, 65);
		group.setLayoutData(formData_10);
		group.setLayout(new FormLayout());

		plate4 = new Combo(group, SWT.NONE);
		FormData formData_9;
		formData_9 = new FormData();
		plate4.setLayoutData(formData_9);

		plate3 = new Combo(group, SWT.NONE);
		formData_9.top = new FormAttachment(plate3, -25, SWT.BOTTOM);
		formData_9.bottom = new FormAttachment(plate3, 0, SWT.BOTTOM);
		FormData formData_8;
		formData_8 = new FormData();
		formData_8.bottom = new FormAttachment(0, 100);
		plate3.setLayoutData(formData_8);

		plate1 = new Combo(group, SWT.NONE);
		FormData formData_7;
		formData_7 = new FormData();
		plate1.setLayoutData(formData_7);

		plate2 = new Combo(group, SWT.NONE);
		formData_9.right = new FormAttachment(plate2, 0, SWT.RIGHT);
		final FormData formData_6 = new FormData();
		formData_6.right = new FormAttachment(0, 360);
		plate2.setLayoutData(formData_6);

		Label plate3Label;
		plate3Label = new Label(group, SWT.NONE);
		formData_8.left = new FormAttachment(plate3Label, 5, SWT.RIGHT);
		final FormData formData_5 = new FormData();
		formData_5.top = new FormAttachment(0, 81);
		formData_5.right = new FormAttachment(0, 55);
		plate3Label.setLayoutData(formData_5);
		plate3Label.setText("Plate 3");
		plate4Label = new Label(group, SWT.NONE);
		formData_9.left = new FormAttachment(plate4Label, 5, SWT.RIGHT);
		formData_8.right = new FormAttachment(plate4Label, -5, SWT.LEFT);
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(plate3Label, -15, SWT.BOTTOM);
		formData_4.bottom = new FormAttachment(plate3Label, 0, SWT.BOTTOM);
		formData_4.right = new FormAttachment(0, 235);
		plate4Label.setLayoutData(formData_4);
		plate4Label.setText("Plate 4");

		Label plate1Label;
		plate1Label = new Label(group, SWT.NONE);
		formData_7.left = new FormAttachment(plate1Label, 5, SWT.RIGHT);
		formData_7.bottom = new FormAttachment(plate1Label, 0, SWT.BOTTOM);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(0, 32);
		formData_3.left = new FormAttachment(0, 11);
		plate1Label.setLayoutData(formData_3);
		plate1Label.setText("Plate 1");

		Label plate2Label;
		plate2Label = new Label(group, SWT.NONE);
		formData_7.right = new FormAttachment(plate2Label, -5, SWT.LEFT);
		formData_6.top = new FormAttachment(plate2Label, -25, SWT.BOTTOM);
		formData_6.bottom = new FormAttachment(plate2Label, 0, SWT.BOTTOM);
		formData_6.left = new FormAttachment(plate2Label, 5, SWT.RIGHT);
		final FormData formData_2 = new FormData();
		formData_2.top = new FormAttachment(plate1, -17, SWT.BOTTOM);
		formData_2.bottom = new FormAttachment(plate1, 0, SWT.BOTTOM);
		formData_2.left = new FormAttachment(plate4Label, -44, SWT.RIGHT);
		formData_2.right = new FormAttachment(plate4Label, 0, SWT.RIGHT);
		plate2Label.setLayoutData(formData_2);
		plate2Label.setText("Plate 2");
	}

}
