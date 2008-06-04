package net.bioclipse.brunn.ui.dialogs.createMasterPlateFromSDF;

import org.eclipse.jface.wizard.Wizard;

public class CreateMasterPlateFromSDF extends Wizard {

	public void addPages()  {
		WizardPage1 page1 = new WizardPage1();
		addPage(page1);
		WizardPage2 page2 = new WizardPage2();
		addPage(page2);
		WizardPage3 page3 = new WizardPage3();
		addPage(page3);
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}
}
