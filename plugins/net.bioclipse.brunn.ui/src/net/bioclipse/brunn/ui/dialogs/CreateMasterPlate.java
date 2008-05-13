package net.bioclipse.brunn.ui.dialogs;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.AbstractBasePlate;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.pojos.PlateLayout;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreateMasterPlate extends TitleAreaDialog {

	private Text numOfPlatesText;
	private Combo comboMasterPlate;
	private Combo comboPlateLayout;
	private Text text;
	private PlateLayout selectedPlateLayout;
	
	private String name;
	private int numOfPlates;
	
	private PlateLayout[] plateLayouts = new PlateLayout[0];

	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreateMasterPlate(Shell parentShell) {
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

		final Group basedUponGroup = new Group(container, SWT.NONE);
		basedUponGroup.setText("Based upon");
		basedUponGroup.setBounds(50, 22, 314, 127);

		comboPlateLayout = new Combo(basedUponGroup, SWT.NONE|SWT.READ_ONLY);
		comboPlateLayout.setBounds(147, 32, 124, 25);
		
		//populate the comboMasterPlate
		IPlateLayoutManager plm  = (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager");
		plateLayouts = plm.getAllPlateLayoutsNotDeleted().toArray(plateLayouts);
		String[] items = new String[plateLayouts.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = plateLayouts[i].getName();
		}
		comboPlateLayout.setItems(items);

		comboMasterPlate = new Combo(basedUponGroup, SWT.NONE|SWT.READ_ONLY);
		comboMasterPlate.setEnabled(false);
		comboMasterPlate.setBounds(147, 78, 124, 25);

		final Button plateLayoutButton = new Button(basedUponGroup, SWT.RADIO);
		plateLayoutButton.setText("Plate Layout:");
		plateLayoutButton.setBounds(31, 32, 110, 25);

		final Button masterPlateButton = new Button(basedUponGroup, SWT.RADIO);
		masterPlateButton.setEnabled(false);
		masterPlateButton.setText("Master Plate:");
		masterPlateButton.setBounds(31, 78, 110, 25);
		
		plateLayoutButton.setSelection(true);

		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name:");
		nameLabel.setBounds(151, 233, 45, 20);

		text = new Text(container, SWT.BORDER);
		text.setBounds(202, 228, 124, 30);

		numOfPlatesText = new Text(container, SWT.BORDER);
		numOfPlatesText.setBounds(202, 185, 124, 30);

		final Label nameLabel_1 = new Label(container, SWT.NONE);
		nameLabel_1.setBounds(26, 190, 170, 20);
		nameLabel_1.setText("Number of plates in batch:");
		setTitle("Create Master Plate");
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
		return new Point(397, 427);
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			name = text.getText();
			selectedPlateLayout = plateLayouts[comboPlateLayout.getSelectionIndex()];
			try {
				numOfPlates = Integer.parseInt(numOfPlatesText.getText());
			}
			catch (NumberFormatException e) {
				MessageDialog.openInformation( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						                       "Could not parse number of plates",
						                       "Number of plates needs to be an int \n" + 
						                       e.getMessage() );
				return;
			}
		}
		super.buttonPressed(buttonId);
	}

	public String getName() {
		return name;
	}

	public PlateLayout getSelectedPlateLayout() {
		return selectedPlateLayout;
	}

	public int getNumOfPlates() {
		return numOfPlates;
	}
}
