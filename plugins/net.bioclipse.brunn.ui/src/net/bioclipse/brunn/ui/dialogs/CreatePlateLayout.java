package net.bioclipse.brunn.ui.dialogs;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.pojos.PlateType;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class CreatePlateLayout extends TitleAreaDialog {

	private Combo combo;
	private Text text;
	
	private PlateType[] plateTypes = new PlateType[0];
	private String name;
	private PlateType plateType;
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreatePlateLayout(Shell parentShell) {
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

		final Label label = new Label(container, SWT.NONE);
		label.setText("Name:");
		label.setBounds(97, 38, 45, 20);

		final Label label_1 = new Label(container, SWT.NONE);
		label_1.setText("Plate type:");
		label_1.setBounds(72, 75, 70, 20);

		text = new Text(container, SWT.BORDER);
		text.setBounds(148, 33, 80, 25);

		combo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
		combo.setBounds(148, 70, 85, 25);
		
		
		//populate the combobox
		IPlateLayoutManager plm  = (IPlateLayoutManager) Springcontact.getBean("plateLayoutManager");
		try {
		    plateTypes = plm.getAllPlateTypesNotDeleted().toArray(plateTypes);
		}
		catch (Exception e) {
		    MessageDialog.openError( 
		        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
		        "An error has occured", 
		        "Could not load plate types. Maybe a missing refresh. "
		            + "If nothing else works please restart." );
        }
		
		String[] items = new String[plateTypes.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = plateTypes[i].getName();
		}
		combo.setItems(items);
		
		setTitle("Create Plate Layout");
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
		return new Point(357, 302);
	}

	public String getName() {
		return name;
	}

	public PlateType getPlateType() {
		return plateType;
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			name = text.getText();
			plateType = plateTypes[combo.getSelectionIndex()];
		}
		super.buttonPressed(buttonId);
	}
}
