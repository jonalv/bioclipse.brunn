package net.bioclipse.brunn.ui.dialogs;

import java.sql.Timestamp;

import net.bioclipse.brunn.Springcontact;
import net.bioclipse.brunn.business.origin.IOriginManager;
import net.bioclipse.brunn.business.plate.IPlateManager;
import net.bioclipse.brunn.business.plateLayout.IPlateLayoutManager;
import net.bioclipse.brunn.genericDAO.IMasterPlateDAO;
import net.bioclipse.brunn.pojos.CellOrigin;
import net.bioclipse.brunn.pojos.MasterPlate;
import net.bioclipse.brunn.ui.explorer.model.nonFolders.CellType;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CreatePlate extends TitleAreaDialog {

	private Text text;
	private Text  barcodeText;
	private Combo cellTypeCombo;
	private Combo masterPlateCombo;
	private Text  nameText;
	
	private String        barCode;
	private String        name;
	private CellOrigin    cellOrigin;
	private MasterPlate   masterPlate;
	private MasterPlate[] masterPlates = new MasterPlate[0];
	private CellOrigin[]  cellOrigins  = new  CellOrigin[0];
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public CreatePlate(Shell parentShell) {
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
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final Label nameLabel = new Label(container, SWT.NONE);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(0, 47);
		formData.top = new FormAttachment(0, 30);
		nameLabel.setLayoutData(formData);
		nameLabel.setText("Name");

		Label masterPlateLabel;
		masterPlateLabel = new Label(container, SWT.NONE);
		formData.right = new FormAttachment(masterPlateLabel, 0, SWT.RIGHT);
		final FormData formData_1 = new FormData();
		formData_1.top = new FormAttachment(0, 83);
		formData_1.bottom = new FormAttachment(0, 100);
		formData_1.left = new FormAttachment(0, 35);
		masterPlateLabel.setLayoutData(formData_1);
		masterPlateLabel.setText("Master Plate");

		final Label cellSampleLabel = new Label(container, SWT.NONE);
		final FormData formData_2 = new FormData();
		formData_2.top = new FormAttachment(0, 133);
		formData_2.bottom = new FormAttachment(0, 150);
		formData_2.right = new FormAttachment(0, 115);
		cellSampleLabel.setLayoutData(formData_2);
		cellSampleLabel.setText("Cell Type");

		final Label nameLabel_1 = new Label(container, SWT.NONE);
		final FormData formData_3 = new FormData();
		formData_3.top = new FormAttachment(0, 183);
		formData_3.bottom = new FormAttachment(0, 200);
		formData_3.right = new FormAttachment(0, 115);
		nameLabel_1.setLayoutData(formData_3);
		nameLabel_1.setText("Defrosting date");

		final Label barcodeLabel = new Label(container, SWT.NONE);
		final FormData formData_4 = new FormData();
		formData_4.top = new FormAttachment(0, 233);
		formData_4.bottom = new FormAttachment(0, 250);
		formData_4.right = new FormAttachment(0, 115);
		barcodeLabel.setLayoutData(formData_4);
		barcodeLabel.setText("Barcode");

		nameText = new Text(container, SWT.BORDER);
		final FormData formData_5 = new FormData();
		formData_5.right = new FormAttachment(100, -5);
		formData_5.top = new FormAttachment(nameLabel, -27, SWT.BOTTOM);
		formData_5.bottom = new FormAttachment(nameLabel, 0, SWT.BOTTOM);
		formData_5.left = new FormAttachment(nameLabel, 5, SWT.RIGHT);
		nameText.setLayoutData(formData_5);

		masterPlateCombo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
		final FormData formData_6 = new FormData();
		formData_6.left = new FormAttachment(nameText, 1, SWT.LEFT);
		formData_6.right = new FormAttachment(nameText, 0, SWT.RIGHT);
		formData_6.top = new FormAttachment(masterPlateLabel, -25, SWT.BOTTOM);
		formData_6.bottom = new FormAttachment(masterPlateLabel, 0, SWT.BOTTOM);
		masterPlateCombo.setLayoutData(formData_6);
	
		cellTypeCombo = new Combo(container, SWT.NONE|SWT.READ_ONLY);
		final FormData formData_7 = new FormData();
		formData_7.left = new FormAttachment(nameText, 1, SWT.LEFT);
		formData_7.right = new FormAttachment(masterPlateCombo, 0, SWT.RIGHT);
		formData_7.top = new FormAttachment(cellSampleLabel, -25, SWT.BOTTOM);
		formData_7.bottom = new FormAttachment(cellSampleLabel, 0, SWT.BOTTOM);
		cellTypeCombo.setLayoutData(formData_7);
		
		barcodeText = new Text(container, SWT.BORDER);
		final FormData formData_8 = new FormData();
		formData_8.left = new FormAttachment(nameText, 1, SWT.LEFT);
		formData_8.right = new FormAttachment(cellTypeCombo, 0, SWT.RIGHT);
		formData_8.top = new FormAttachment(barcodeLabel, -27, SWT.BOTTOM);
		formData_8.bottom = new FormAttachment(barcodeLabel, 0, SWT.BOTTOM);
		barcodeText.setLayoutData(formData_8);
		setTitle("Create Plate");
		
		/*
		 * populate the comboboxes
		 */
		IPlateManager pm  = (IPlateManager) Springcontact.getBean("plateManager");
		masterPlates = pm.getAllMasterPlatesNotDeleted().toArray(masterPlates);
		String[] items = new String[masterPlates.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = masterPlates[i].getName();
		}
		masterPlateCombo.setItems(items);
		masterPlateCombo.select(0);
		
		IOriginManager om  = (IOriginManager) Springcontact.getBean("originManager");
		cellOrigins = om.getAllCellOriginsNotDeleted().toArray(cellOrigins);
		items = new String[cellOrigins.length];
		for (int i = 0; i < items.length; i++) {
			items[i] = cellOrigins[i].getName();
		}
		cellTypeCombo.setItems(items);
		cellTypeCombo.select(0);

		text = new Text(container, SWT.BORDER);
		text.setEnabled(false);
		text.setText("For now, set to current time, later here will be "
                   + "some sort of datetime picker.");
		final FormData formData_9 = new FormData();
		formData_9.left = new FormAttachment(nameText, 1, SWT.LEFT);
		formData_9.right = new FormAttachment(barcodeText, 0, SWT.RIGHT);
		formData_9.top = new FormAttachment(0, 175);
		text.setLayoutData(formData_9);
		
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
		return new Point(635, 444);
	}

	public String getBarCode() {
		return barCode;
	}

	public CellOrigin getCellOrigin() {
		return cellOrigin;
	}

	public MasterPlate getMasterPlate() {
		return masterPlate;
	}

	public String getName() {
		return name;
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.barCode     = barcodeText.getText();
			this.name        = nameText.getText();
			this.masterPlate = masterPlates[ masterPlateCombo.getSelectionIndex() ]; 
			this.cellOrigin  = cellOrigins[     cellTypeCombo.getSelectionIndex() ];
		}
		super.buttonPressed(buttonId);
	}

	public Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis()); //TODO change inte value from a datatimepicker
	}
}
