package net.bioclipse.brunn.ui.dialogs.createMasterPlateFromSDF;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class WizardPage1 extends WizardPage {

	private Combo combo_1;
	private Text text;
	/**
	 * Create the wizard
	 */
	public WizardPage1() {
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
		//
		setControl(container);

		text = new Text(container, SWT.BORDER);
		text.setBounds(89, 40, 145, 25);

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("Browse");
		browseButton.setBounds(240, 35, 60, 30);

		final Label selectSdfileAsLabel = new Label(container, SWT.NONE);
		selectSdfileAsLabel.setText("SD-file");
		selectSdfileAsLabel.setBounds(38, 45, 45, 20);

		combo_1 = new Combo(container, SWT.NONE);
		combo_1.setBounds(89, 105, 145, 25);

		final Label pickListLabel = new Label(container, SWT.NONE);
		pickListLabel.setText("Pick list");
		pickListLabel.setBounds(33, 110, 50, 20);
	}

}
