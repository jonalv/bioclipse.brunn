package net.bioclipse.brunn.ui.dialogs;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.pojos.ConcUnit;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.MasterPlate;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;


public class AddDrugToMasterPlate extends TitleAreaDialog {

	private Combo concUnitCombo;
	private Text dilutionFactorText;
	private Text concentrationText;
	private String[] markers;
	private ConcUnit concUnit;
	
	private double dilutionFactor = lastDilutionFactor;
	private double concentration = lastConcentration;
	private final ConcUnit[] concUnits = new ConcUnit[] {ConcUnit.UNIT, 
			                                               ConcUnit.MICRO_MOLAR,
			                                               ConcUnit.MICRO_GRAM_PER_MILLI_LITER};
	
	private static double lastDilutionFactor = 1;
	private static double lastConcentration = 100;
	private static int lastConcentrationIndex = 1;
	
	public double getConcentration() {
		return concentration;
	}

	public double getDilutionFactor() {
		return dilutionFactor;
	}

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public AddDrugToMasterPlate(Shell parentShell) {
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
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label startingConcentrationLabel = new Label(container, SWT.NONE);
		startingConcentrationLabel.setBounds(46, 15,145, 20);
		startingConcentrationLabel.setText("Starting concentration:");

		concentrationText = new Text(container, SWT.BORDER);
		concentrationText.setBounds(197, 10,80, 25);
		concentrationText.setText(concentration + "");

		final Label dilutionFactorLabel = new Label(container, SWT.NONE);
		dilutionFactorLabel.setBounds(101, 73,90, 20);
		dilutionFactorLabel.setText("Dilution factor:");

		dilutionFactorText = new Text(container, SWT.BORDER);
		dilutionFactorText.setText(dilutionFactor + "");
		dilutionFactorText.setBounds(197, 68,80, 25);

		concUnitCombo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
		concUnitCombo.setBounds(195, 110, 100, 27);
		
		String[] items = new String[concUnits.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = concUnits[i].toString();
		}
		concUnitCombo.setItems(items);
		concUnitCombo.select(lastConcentrationIndex);

		final Label concentrationUnitLabel = new Label(container, SWT.NONE);
		concentrationUnitLabel.setText("Concentration unit:");
		concentrationUnitLabel.setBounds(66, 117, 125, 20);
		setTitle("Add dilution series to masterPlate");
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
		return new Point(359, 305);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			try {
				concentration  = Double.parseDouble( concentrationText.getText()  );
				dilutionFactor = Double.parseDouble( dilutionFactorText.getText() );
				lastConcentration = concentration;
				lastDilutionFactor = dilutionFactor;
				lastConcentrationIndex = concUnitCombo.getSelectionIndex();
			}
			catch(NumberFormatException e) {
				MessageDialog.openInformation( PlatformUI
						                       .getWorkbench()
						                       .getActiveWorkbenchWindow()
						                       .getShell(), 
						                       "Could not parse double", 
						                       "Concentration and dilution factor should be doubles (e.g. 3.14)" );
				return;
			}
			concUnit = concUnits[concUnitCombo.getSelectionIndex()];
		}
		super.buttonPressed(buttonId);
	}

	public ConcUnit getConcUnit() {
		return concUnit;
	}
}
